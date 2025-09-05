package tobi.tools.hardware;


import java.io.*;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.commons.cli.*;

import tobi.tools.hardware.AssemblerException;

public class App {

    public static void main(String[] args) {
        if(args.length > 0) {
            String result = "";
            String format = "";
            
            CommandLineParser parser = new DefaultParser();


            Options options = new Options();
            options.addOption("h", "help", false, "Show help");
            options.addOption("if", "input-file", true, "File to process");
            options.addOption("of", "output-file", true, "File to put the Machine Code (or OPs) in.");
            options.addOption("f", "format", true, "Set the output format (valid: \"hex\", \"bin\", \"oct\", \"ihex\")");
            options.addOption("op", "operations", false, "Generate Operations instead of machine code.");
            options.addOption("optSImm", null, false, "Optimize for signed inline immediates (10 bit, -512 to 511). Makes assembling less predictable but decreases code size. Default: true");
            
            try {
                CommandLine cli = parser.parse(options, args);

                if(cli.hasOption("h")) {
                    HelpFormatter hf = new HelpFormatter();
                    hf.printHelp("termasm {if=INPUT_FILE,OPTION*}", options);
                    return;
                }
                if(!cli.hasOption("if")) {
                    System.out.println("Input file is required!");
                    System.exit(1);
                }
                String content = Files.readString(Path.of(cli.getOptionValue("if")));
                format = cli.hasOption("f") ? cli.getOptionValue("f") : "hex";
                if(cli.hasOption("op")) {
                    String[] lines = content.split("\n");
                    lines[0] = "";
                    for(int i = 0; i < lines.length; i++) {
                        String line = lines[i];
                        if(line.contains(";")) {
                            line = line.substring(0, line.indexOf(";"));
                        } else if(line.contains("//")) {
                            line = line.substring(0, line.indexOf("//"));
                        }
                        
                        if(line.isBlank()) {
                            continue;
                        }
                        
                        String[] parts = line.split("\s+");
                        ALUMode alumode = ALUMode.valueOf(parts[1]);
                        boolean increment = parts[2].equals("1") ? true : false;
                        boolean ramwe = parts[3].equals("1") ? true : false;
                        boolean regwe = parts[4].equals("1") ? true : false;
                        boolean op_a_en = parts[5].equals("1") ? true : false;
                        boolean op_b_en = parts[6].equals("1") ? true : false;
                        boolean flags_we = parts[7].equals("1") ? true : false;

                        String op = InstructionGenerator.generateInstruction(alumode, increment, ramwe, regwe, op_a_en, op_b_en, flags_we);

                        op = switch(format) {
                            case "hex" -> Long.toHexString(Long.parseUnsignedLong(op, 2));
                            case "ihex" -> Long.toHexString(Long.parseUnsignedLong(op, 2));
                            case "bin" -> {
                                yield createStringFromBinaryString(op);
                            }
                            case "oct" -> Long.toOctalString(Long.parseUnsignedLong(op,2));
                            default -> throw new IllegalArgumentException("Invalid format: " + format);
                        };
                        result = result + "\n" + op;
                    }
                    Files.writeString(Path.of(cli.getOptionValue("of")), (format.equals("ihex") ? "v2.0 raw\n" : "") + result);
                } else {
                    AbstractAssembler assembler = new AssemblerVisitor(cli.hasOption("optSImm"));
                    System.out.println("Format: '" + format + "'");
                    String mc = switch(format) {
                        case "hex" -> convert(assembler.assemble(content), 2, 16);
                        case "ihex" -> convert(assembler.assemble(content), 2, 16);
                        case "bin" -> createStringFromBinaryString(assembler.assemble(content));
                        case "oct" -> convert(assembler.assemble(content), 2, 8);
                        case "symbin" -> assembler.assemble(content);
                        default -> throw new IllegalArgumentException("Invalid format: " + format);
                    };
                    
                    // Assembler.java Test for Comparison
                    // Assembler.collectLabels(content);
                    // for(int i = 0; i < content.split("\n").length; i++) {
                    //     String line = content.split("\n")[i];
                    //     if(line.isBlank() || line.startsWith(";")) 
                    //         continue;
                    //     if(line.contains(";")) {
                    //         line = line.substring(0, line.indexOf(";"));
                    //     }
                    //     line = line.trim();
                    //     System.out.print(Assembler.assemble(line, i).equals("skip") ? "" : Assembler.assemble(line, i) + "\n");
                    // }


                    Files.writeString(Path.of(cli.getOptionValue("of")), (format.equals("ihex") ? "v2.0 raw\n" : "") + mc);
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            } catch (AssemblerException asme) {
                System.out.println("Error while Assembling: " + asme.getMessage());
            } catch (NumberFormatException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            // } catch (URISyntaxException e) {
            //     // TODO Auto-generated catch block
            //     e.printStackTrace();
            }
        }
    }
    
    private static String convert(String num, int from, int to) {
        if (num == null || num.isEmpty()) {
            throw new IllegalArgumentException("Input number string is null or empty");
        }
        if (from < Character.MIN_RADIX || from > Character.MAX_RADIX ||
            to < Character.MIN_RADIX || to > Character.MAX_RADIX) {
            throw new IllegalArgumentException("Unsupported radix: Must be between 2 and 36");
        }
        StringBuilder out = new StringBuilder();
        for(String numLine : num.split("\n")) {
            // Interpretieren des Strings als BigInteger mit Basis 'from'
            BigInteger value = new BigInteger(numLine, from);
            out.append(value.toString(to)).append("\n");
        }

        // Rückgabe als String im Ziel-Zahlensystem 'to'
        return out.toString();
    }


    private static String createStringFromBinaryString(String binaryStr) {
        byte[] binaryBytes = new byte[binaryStr.length()];

        for (int i = 0; i < binaryStr.length(); i++) {
            binaryBytes[i] = (byte) (binaryStr.charAt(i) == '1' ? 1 : 0);
        }
        return new String(binaryBytes, java.nio.charset.StandardCharsets.UTF_8);
    }
}