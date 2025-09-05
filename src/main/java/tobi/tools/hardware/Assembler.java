package tobi.tools.hardware;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import tobi.tools.hardware.AssemblerException.*;

public class Assembler {
    private static Map<String, Integer> labels = new HashMap<>();

    private static Map<Integer,String> mayorForLines = new HashMap<>();

    private static final String MAYOR_LABEL_REGEX = "[a-zA-Z_][a-zA-Z_0-9]*";
    private static final String MINOR_LABEL_REGEX = "[a-zA-Z_.][a-zA-Z_0-9.]*";
    private static Map<String, String> mnemonics = new HashMap<>();

    public static void collectLabels(String input) throws IOException, URISyntaxException, NumberFormatException, BinaryLengthException {
        String ops = Files.readString(new File(Assembler.class.getResource("/ops.txt").toURI()).toPath());
        ops = ops.substring(ops.indexOf("\n")+1);
        // Get Mnemonics & OP-codes.
        for(String line : ops.split("\n")) {
            String[] parts = line.split("\\s+");
            String opcode = parts[0];
            String[] mnems = opcode.substring(opcode.indexOf('(')+1, opcode.lastIndexOf(')')).split("/");
            for(String mnem : mnems) {
                mnemonics.put(mnem.toUpperCase(Locale.ROOT), checkLength(Integer.toBinaryString(Integer.valueOf(processPrefix(opcode.substring(0, opcode.indexOf("("))))), 8, true));
            }
        }
        int pc = 0;
        String _currentMayor = "root";
        for(int i = 0; i < input.split("\n").length; i++) {
            String line = input.split("\n")[i].trim().split("->")[0].trim();
            if(line.contains(";")) {
                line = line.substring(0, line.indexOf(";"));
            }
            if(line.contains("//")) {
                line = line.substring(0, line.indexOf("//"));
            }
            if(line.isBlank()) {
                continue;
            }
            line = line.trim();
            if(line.matches("^" + MAYOR_LABEL_REGEX + ":")) {
                String labelName = line.substring(0, line.lastIndexOf(":"));
                _currentMayor = labelName;
                labels.put(labelName, pc);
                pc--;
            } else if(line.matches("^" + MINOR_LABEL_REGEX + ":")) {
                String labelName = line.substring(0, line.lastIndexOf(":"));
                labels.put(relativeLabelToAbsoluteLabel(labelName, _currentMayor), pc);
                pc--;
            } else {
                // System.out.println("Line: " + line);
                if(line.split("\\s+").length > 1) System.out.println("\tOp A: " + line.split("\\s+")[1]);
                    
                if(line.split("\\s+").length > 1 && line.split("\\s+")[1].matches("(#)?(-)?(\\$|0x|0X|%|0b|0B)?[0-9A-Fa-f]+")) {
                    if(!line.split("\\s+")[1].matches("(#)?(-)?(\\$|0x|0X|%|0b|0B)?(0)+")) {
                        System.out.println("\t\tNot zero Payload");
                        pc++;
                    }
                } else if(line.split("\\s+").length > 1 && line.split("\\s+")[1].matches("<" + MINOR_LABEL_REGEX + ">")) {
                    System.out.println("\t\tNot zero Payload");
                    pc++;
                }
                
                if(line.split("\\s+").length > 2) System.out.println("\tOp B: " + line.split("\\s+")[2]);
                
                if(line.split("\\s+").length > 2 && line.split("\\s+")[2].matches("(#)?(-)?(\\$|0x|0X|%|0b|0B)?[0-9A-Fa-f]+")) {
                    if(!line.split("\\s+")[2].matches("(#)?(-)?(\\$|0x|0X|%|0b|0B)?(0)+")) {
                        System.out.println("\t\tNot zero Payload");
                        pc++;
                    }
                } else if(line.split("\\s+").length > 2 && line.split("\\s+")[2].matches("<" + MINOR_LABEL_REGEX + ">")) {
                    System.out.println("\t\tNot zero Payload");
                        
                    pc++;
                }
            }
            pc++;
            mayorForLines.put(i, _currentMayor);
        }
        System.out.println("Final PC: " + pc);
    }

    private static String relativeLabelToAbsoluteLabel(String labelName, String currentMayor) {
        if(labelName.startsWith(".")) {
            return currentMayor + labelName;
        } else {
            return labelName;
        }
    }

    public static String assemble(String input, int lnIndex) throws AssemblerException {
        input = input.trim();
        if(input.matches(MINOR_LABEL_REGEX + ":")) {
            return "skip";        
        }
        String result = "";


        // Process Input

        
        String cid = "0";
        
        if(input.contains("{")) {
            cid = input.substring(input.indexOf("{")+1, input.lastIndexOf("}")).trim();
            input = input.substring(0, input.indexOf("{"));
        }

        
        String operandA = input.split("->")[0].trim().split("\\s+").length > 1 ? input.split("->")[0].trim().split("\\s+")[1] : "0";
        String operandB = input.split("->")[0].trim().split("\\s+").length > 2 ? input.split("->")[0].trim().split("\\s+")[2] : "0";

        input = input.toUpperCase(Locale.ROOT);

        String destReg = input.split("->").length > 1 ? input.split("->")[1].trim().split("\\s+")[0] : "0";
        
        String[] parts = input.split("->")[0].trim().split("\\s+");
        
        String currentMayor = mayorForLines.get(lnIndex);

        if(operandA.matches("<" + MINOR_LABEL_REGEX + ">")) {
            operandA = String.valueOf(labels.get(relativeLabelToAbsoluteLabel(operandA.substring(1, operandA.length()-1), currentMayor)));
        } else {
            operandA = parts.length > 1 ? parts[1] : "0";
        }
        if(operandB.matches("<" + MINOR_LABEL_REGEX + ">")) {
            operandB = String.valueOf(labels.get(relativeLabelToAbsoluteLabel(operandB.substring(1, operandB.length()-1), currentMayor)));
        } else {
            operandB = parts.length > 2 ? parts[2] : "0";
        }
        
        String mnemonic = parts[0]; // MNEM OP OP

        if(!mnemonics.containsKey(mnemonic)) {
            throw new UnknownMnemonicException("Mnemonic \"" + mnemonic + "\" not found. (Out of: \"" + mnemonics.keySet() + "\"");
        }

        String opcode = checkLength(mnemonics.get(mnemonic), 8, false);

        boolean opAisReg = (operandA.startsWith("#") ? operandA.substring(1) : operandA).matches("R(\\$|0x|0X|%|0b|0B)?[0-9A-Fa-f]+");
        String regAIndex = checkLength(opAisReg ? Integer.toBinaryString(Integer.valueOf(processPrefix(operandA.split("R")[1]))) : "0", 10, true);

        
        boolean opBisReg = (operandB.startsWith("#") ? operandB.substring(1) : operandB).matches("R(\\$|0x|0X|%|0b|0B)?[0-9A-Fa-f]+");
        String regBIndex = checkLength(opBisReg ? Integer.toBinaryString(Integer.valueOf(processPrefix(operandB.split("R")[1]))) : "0", 10, true);

        String regDIndex = checkLength((destReg.equals("0") ? 
        "0" :
        Integer.toBinaryString(
            Integer.valueOf(
                processPrefix(
                    destReg.substring(1)
                )
            )
        )
        ), 10, true);
        
        String conditionID = checkLength(Integer.toBinaryString(Integer.valueOf(processPrefix(cid))), 16, true);

        String opAValue = operandA;
        if(opAValue.startsWith("#")) {
            opAValue.substring(1);
        }
        if(opAValue.startsWith("IO")) {
            opAValue = opAValue.substring(2);
        }
        opAValue = processPrefix(opAValue);
        
        String opBValue = operandB;
        if(opBValue.startsWith("#")) {
            opBValue.substring(1);
        }
        if(opBValue.startsWith("IO")) {
            opBValue = opBValue.substring(2);
        }
        opBValue = processPrefix(opBValue);
        
        String dataA = !opAisReg
            ? checkLength(
            toBinary(opAValue), 64, true
        ) : "0";
        
        String dataB = !opBisReg
            ? checkLength(
            toBinary(opBValue), 64, true
        ) : "0";
        
        String insnInd = checkLength((dataB.matches("0+") ? "0" : "1") + (dataA.matches("0+") ? "0" : "1"), 2, false);

        String opASel = checkLength(selectOperandSelector(operandA),3,false);
        String opBSel = checkLength(selectOperandSelector(operandB),3,false);
        
        result = "00" + conditionID + regDIndex + regBIndex + regAIndex + insnInd + opBSel + opASel + opcode +
        (dataA.matches("0+") ? "" : "\n" + dataA) +
        (dataB.matches("0+") ? "" : "\n" + dataB);
        return result;
    }

    private static String toBinary(String str) {
        boolean isFloating = str.contains(".");
        String result = "";
        if(!isFloating) {
            result = Long.toBinaryString(Long.valueOf(str));
        } else {
            // Decimal
            result = Long.toBinaryString(Double.doubleToRawLongBits(Double.valueOf(str)));
        }

        return result;
    }

    private static String processPrefix(String str) {
        if(str.startsWith("$")) {
            str = String.valueOf(Integer.parseInt(str.substring(1), 16));
        }
        if(str.startsWith("%")) {
            str = String.valueOf(Integer.parseInt(str.substring(1), 2));
        }
        if(str.startsWith("0x")|str.startsWith("0X")) {
            str = String.valueOf(Integer.parseInt(str.substring(2), 16));
        }
        if(str.startsWith("0b")|str.startsWith("0B")) {
            str = String.valueOf(Integer.parseInt(str.substring(2), 2));
        }
        return str;
    }

    private static String checkLength(String str, int targetWidth, boolean correctIfNotFitting) throws BinaryLengthException {

        String res = str;
        if(res.length() > targetWidth) {
            if(correctIfNotFitting) {
                res = res.substring(0, targetWidth);
            } else {
                throw new BinaryLengthException("Length of binary \"" + res + "\": " + res.length() + " does not match the target length: " + targetWidth);
            }
        }
        while(res.length() < targetWidth) {
            if(correctIfNotFitting) {
                res = "0" + res;
            } else {
                throw new BinaryLengthException("Length of binary \"" + res + "\": " + res.length() + " does not match the target length: " + targetWidth);
            }
        }
        return res;
    }

    private static String selectOperandSelector(String operand) {
        boolean isIndirect = false;
        String selector = "00";
        if(operand.startsWith("#")) {
            operand = operand.substring(1);
            isIndirect = true;
        }

        if(operand.matches("(-)?(\\$|%)?[0-9A-Fa-f]+")) {
            selector = "00";
        } else if(operand.matches("R(\\$|%)?[0-9A-Fa-f]+")) {
            selector = "01";
        } else if(operand.matches("IO(\\$|%)?[0-9A-Fa-f]+")) {
            selector = "10";
        }
        return (isIndirect ? "1" : "0") + selector;
    }

}