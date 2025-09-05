package tobi.tools.hardware;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

import tobi.tools.hardware.AssemblerException.BinaryLengthException;
import tobi.tools.hardware.AssemblerException.UnknownMnemonicException;
import tobi.tools.hardware.AssemblyParser.DirectOperandContext;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

public class AssemblerVisitor
    extends AssemblyBaseVisitor<String>
    implements AbstractAssembler {

    private static final ExpressionSolver EXP_SOLVER = new ExpressionSolver();
    private static final String PROG_COUNTER_ALIAS = "pc|ip";
    private static final String PROG_COUNTER = "r1023";
    private boolean OPT_SIMM = true;

    private final Map<String,Long> labels       = new HashMap<>();
    private final Map<Long,String> mayorForLine = new HashMap<>();
    private List<AssemblyParser.StatementContext> statements;
    private Map<String,String> mnemonics;
    private Map<String,String> aliases;
    private String currentMayor;

    public AssemblerVisitor(boolean optSImm) {
        super();
        OPT_SIMM = optSImm;
    }

    @Override
    public String assemble(String code) throws AssemblerException {
        // 1) Prepare
        statements = new ArrayList<>();
        labels.clear();
        mayorForLine.clear();
        currentMayor = "root";

        // 2) Parse
        CharStream input = CharStreams.fromString(code);
        AssemblyLexer lexer = new AssemblyLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AssemblyParser parser = new AssemblyParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new ThrowingErrorListener());
        AssemblyParser.AssemblyprogContext prog = parser.assemblyprog();

        // 3) First pass: collect labels + statements + PC/mayor tracking
        collectLabelsAndStatements(prog);

        // 4) Load op‐codes → binary map (from /ops.txt on classpath)
        loadMnemonics();

        // 5) Second pass: assemble each statement in sequence
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < statements.size(); i++) {
            String bin = visitStatement(statements.get(i), i);
            if (!"skip".equals(bin)) {
                out.append(bin);
                if (i < statements.size() - 1) out.append("\n");
            }
        }
        return out.toString();
    }

    private void collectLabelsAndStatements(AssemblyParser.AssemblyprogContext prog)
            throws AssemblerException {
        long pc = 0, stmtIdx = 0;

        for (ParseTree child : prog.children) {
            if(child.getText().isBlank()) continue;
            // System.out.println("Line: '" + child.getText() + "'");
            if (child instanceof AssemblyParser.LabeldefContext childLD) {
                String lbl = childLD.label().getText();
                // major label?
                if (lbl.matches("[a-zA-Z_][A-Za-z0-9_]*")) {
                    currentMayor = lbl;
                    // System.out.println("Major label: " + lbl);
                    labels.put(lbl, pc);
                } else {
                    // minor, relative to currentMayor
                    String abs = lbl.startsWith(".") ? currentMayor + lbl : lbl;
                    // System.out.println("Minor Label: " + lbl + " = " + abs);
                    labels.put(abs, pc);
                }
            }
            if (child instanceof AssemblyParser.StatementContext) {
                var st = (AssemblyParser.StatementContext) child;
                statements.add(st);

                // mimic your PC‐count logic: count payload words A & B
                if(st.opa != null) {
                    // System.out.println("Operand A: " + st.opa.getText());
                    boolean hasOperand = false;
                    DirectOperandContext doctx = null;
                    if(st.opa.directOperand() != null) {
                        doctx = st.opa.directOperand();
                        hasOperand = true;
                    } else if(st.opa.indirectOperand() != null) {
                        doctx = st.opa.indirectOperand().directOperand();
                        hasOperand = true;
                    }
                    if(hasOperand && doctx.literal() != null) {
                        if(doctx.literal().constantExpression() != null) {
                            if(!(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText()).matches("(0x|0b)?0+"))) {
                                // System.out.println("\thas non-zero Payload.");
                                // System.out.println("\t\tvalue: " + EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText()));
                                if(OPT_SIMM && (Long.valueOf(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText())) >= -512 && Long.valueOf(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText())) <= 511)) {
                                    // System.out.println("\t\tfits into 10 bit signed immediate. PC unchanged.");
                                } else {
                                    // System.out.println("\t\tgreater than 10 bit or opt_SImm is not activated. PC now: " + (pc+1));
                                    pc++;
                                }
                            }
                        } else if(doctx.literal().label() != null) {
                                // System.out.println("\thas non-zero label Payload. PC unchanged");
                        }
                    }
                }
                if(st.opb != null) {
                    // System.out.println("Operand B: " + st.opb.getText());
                    boolean hasOperand = false;
                    DirectOperandContext doctx = null;
                    if(st.opb.directOperand() != null) {
                        doctx = st.opb.directOperand();
                        hasOperand = true;
                    } else if(st.opb.indirectOperand() != null) {
                        doctx = st.opb.indirectOperand().directOperand();
                        hasOperand = true;
                    }
                    if(hasOperand && doctx.literal() != null) {
                        if(doctx.literal().constantExpression() != null) {
                            if(!(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText()).matches("(0x|0b)?0+"))) {
                                // System.out.println("\thas non-zero Payload.");
                                    // System.out.println("\t\tvalue: " + EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText()));
                                if(OPT_SIMM && (Long.valueOf(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText())) >= -512 && Long.valueOf(EXP_SOLVER.evaluate(doctx.literal().constantExpression().getText())) <= 511)) {
                                    // System.out.println("\t\tfits into 10 bit signed immediate. PC unchanged.");
                                } else {
                                    // System.out.println("\t\tgreater than 10 bit or opt_SImm is not activated. PC now: " + (pc+1));
                                    pc++;
                                }
                            }
                        } else if(doctx.literal().label() != null) {
                                // System.out.println("\thas non-zero label Payload. PC unchanged");
                        }
                    }
                }
                pc++;
            }
            // System.out.println("Instrution finished. PC: " + pc);
            mayorForLine.put(stmtIdx++, currentMayor);
        }
        // System.out.println("Final PC: " + pc);
    }

    private void loadMnemonics() throws AssemblerException {
        mnemonics = new HashMap<>();
        aliases = new HashMap<>();
        try (Scanner sc = new Scanner(getClass().getResourceAsStream("/ops.txt"))) {
            sc.nextLine();
            while (sc.hasNextLine()) {
                String opname = sc.nextLine().split("\s+")[0];
                int code     = Integer.parseUnsignedInt(opname.substring(0, opname.indexOf("(")));
                String bin   = checkLength(Integer.toBinaryString(code), 8, true);
                String[] ms  = opname.substring(opname.indexOf('(')+1, opname.indexOf(')')).split("/");
                for (String m : ms) {
                    mnemonics.put(m.toUpperCase(Locale.ROOT), bin);
                }
            }
        }
        try {
            if(new File(getClass().getResource("/aliases.txt").toURI()).exists()) {
                try (Scanner sc = new Scanner(getClass().getResourceAsStream("/aliases.txt"))) {
                    while (sc.hasNextLine()) {
                        String line = sc.nextLine().toUpperCase().trim();
                        if(line.isBlank()) continue;
                        String target = line.split("=")[0].trim().replaceAll("\s", " ");
                        if(target.startsWith("\"") && target.endsWith("\"")) {
                            target = target.substring(1,target.length()-1);
                        }
                        String replacement = line.split("=")[1].trim().replaceAll("\s", " ");
                        if(replacement.startsWith("\"") && replacement.endsWith("\"")) {
                            replacement = replacement.substring(1,replacement.length()-1);
                        }
                        aliases.put(target,replacement);
                    }
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String visitStatement(AssemblyParser.StatementContext ctx, long idx)
            throws AssemblerException {

        // label‐only
        if (ctx.MNEMONIC() == null) {
            return "skip";
        }
        
        String mayor = mayorForLine.get(idx);
        String mnem  = ctx.MNEMONIC().getText().toUpperCase();
        String opa   = ctx.opa   != null ? ctx.opa.getText()   : "0";
        String opb   = ctx.opb   != null ? ctx.opb.getText()   : "0";
        String dest  = ctx.REGISTER() != null
                     ? resolveDestinationRegister(ctx.REGISTER().getText().toLowerCase())
                     : "0";
        String pred  = ctx.predicate != null
                     ? ctx.predicate.getText()
                     : "0";


        String aliasKey = mnem + (ctx.opa != null ? " " : "") + (ctx.opb != null ? " " : "") + (ctx.REGISTER() != null ? " -> " : "") + (ctx.predicate != null ? " {}" : "");
        if(aliases.containsKey(aliasKey)) {
            String replacement = aliases.get(aliasKey).replace("", opa).replace("", opb).replace("",dest).replace("", pred);
            // System.out.println("Alias matches: " + aliasKey + ", Replacement: " + replacement);
            String preArrow = replacement.split("->")[0].trim();
            mnem = preArrow.split(" ")[0];
            opa = preArrow.split(" ").length > 1 ? preArrow.split(" ")[1] : "0";
            opb = preArrow.split(" ").length > 2 ? preArrow.split(" ")[2] : "0";
            replacement = replacement.toLowerCase();
            dest = replacement.contains("->") ? resolveDestinationRegister(replacement.split("->")[1].trim().split("\s+")[0]) : "0";

            pred = replacement.contains("{") ? replacement.substring(replacement.indexOf("{")+1, replacement.indexOf("}")).trim() : pred;



        }

        // embedded instruction‐literal `_…_`
        // if (opa.startsWith("(") && opa.endsWith(")")) {
        //     opa = assemble(opa.substring(1, opa.length()-1));
        // }
        // if (opb.startsWith("(") && opb.endsWith(")")) {
        //     opb = assemble(opb.substring(1, opb.length()-1));
        // }

        // resolve label references
        boolean aIsInd = opa.startsWith("#");
        boolean bIsInd = opb.startsWith("#");
        opa = resolveOperand(opa, mayor);
        opb = resolveOperand(opb, mayor);

        // opcode bits
        String oc = mnemonics.get(mnem);
        if (oc == null) throw new UnknownMnemonicException("Unknown mnemonic “" + mnem + "\"");

        // operand‐type flags
        boolean aIsReg = isRegister(opa);
        boolean bIsReg = isRegister(opb);

        // register indices
        String regA = checkLength(aIsReg ?
            Long.toBinaryString(Long.parseLong(processPrefix(opa.replaceAll("[#r]","")))) : "0", 
            10, true);
        String regB = checkLength(bIsReg ?
            Long.toBinaryString(Long.parseLong(processPrefix(opb.replaceAll("[#r]","")))) : "0", 
            10, true);
        String regD = checkLength(
            Long.toBinaryString(Long.parseLong(processPrefix(dest.replaceAll("r","")))), 
            10, true);

        // condition field
        String cond = checkLength(
            Long.toBinaryString(Long.parseLong(processPrefix(pred))), 
            16, true);

        // instruction‐indicator bits: B‐payload? A‐payload?
        String insnInd = (!bIsReg && !opb.matches("0+") ? "1":"0") + (!aIsReg && !opa.matches("0+") ? "1":"0");

        if(OPT_SIMM) {
            if(opa.matches("(-)?[0-9]+") && Long.valueOf(opa) >= -512 && Long.valueOf(opa) <= 511) {
                insnInd = insnInd.charAt(0) + "0";
                regA = checkLength(Long.toBinaryString(Long.valueOf(opa) & 0x3FF),10,true);
                opa = "0";
            }

            if(opb.matches("(-)?[0-9]+") && Long.valueOf(opb) >= -512 && Long.valueOf(opb) <= 511) {
                insnInd = "0" + insnInd.charAt(1);
                regB = checkLength(Long.toBinaryString(Long.valueOf(opb) & 0x3FF),10,true);
                opb = "0";
            }
        }

        // selectors
        String selA = (aIsInd ? "1" : "0") + select(opa);
        String selB = (bIsInd ? "1" : "0") + select(opb);

        if(opa.startsWith("§")) {
            opa = opa.substring(1);
        }
        if(opb.startsWith("§")) {
            opb = opb.substring(1);
        }

        // data payloads
        String dataA = (insnInd.charAt(1) == '0')
            ? "" 
            : "\n" + padData(opa);
        String dataB = (insnInd.charAt(0) == '0')
            ? "" 
            : "\n" + padData(opb);

        // assemble final
        return "00" + cond + regD + regB + regA + insnInd + selB + selA + oc + dataA + dataB;
    }

    // ——— Helpers —————————————————————————————

    private String resolveDestinationRegister(String dest) throws AssemblerException {
        if(dest.matches("r[0-9]+")) {
            return dest.substring(1);
        } else if(dest.matches(PROG_COUNTER_ALIAS)) {
            return PROG_COUNTER.substring(1);
        } else {
            throw new AssemblerException("Unknown (destination) Register: " + dest);
        }
        
    }

    private String resolveOperand(String op, String mayor) throws AssemblerException {
       String o = op.startsWith("#") ? op.substring(1) : op;
       
        // System.out.println("o: " + o);

        if(o.matches(PROG_COUNTER_ALIAS)) {
            // System.out.println("\tRecognized as PC");
            return PROG_COUNTER;
        }

        if(isRegister(o)) {
            // System.out.println("\tRecognized as Register");
            return o;
        }
        if (isIOOp(o)) {
            // System.out.println("\tRecognized as IO Operand");
            return o;
        }
        // How to find out if it's an expression?
        if (o.matches("((0x|0b)?[0-9A-F]*[+\\-*/^()]?(0x|0b)?[0-9A-F]*)+")) {
            // System.out.println("\tRecognized as Expression: " + o + " = " + EXP_SOLVER.evaluate(o));
            return EXP_SOLVER.evaluate(o);
        }
        if(o.startsWith("<") && o.endsWith(">")) {
            o = o.substring(1,o.length()-1);
        }

        // System.out.println("\tRecognized as Label. Mayor: " + mayor);

        // neuer Code: nur wenn das Label mit '.' beginnt, ist es relativ
        String key;
        if (o.startsWith(".")) {
            key = mayor + o;
        } else {
            key = o;
        }

        Long addr = labels.get(key);
        if (addr == null) {
            throw new AssemblerException("Label \"" + o + "\" not found");
        }
        // System.out.println(key + "=" + addr);
        return addr.toString();
    }

    private boolean isRegister(String op) {
        return op.matches("r[0-9]+") || op.matches(PROG_COUNTER_ALIAS);
    }
    private boolean isIOOp(String op) {
        return op.matches("§(0x|0b)?[0-9A-Fa-f]+");
    }

    private String processPrefix(String s) {
        if (s.startsWith("0x")) return Long.toString(Long.parseLong(s.substring(2),16));
        if (s.startsWith("0b")) return Long.toString(Long.parseLong(s.substring(2),2));
        return s;
    }

    private String checkLength(String bin, int width, boolean fit) throws BinaryLengthException {
        if (bin.length() > width) {
            if (fit) return bin.substring(0, width);
            throw new BinaryLengthException("Binary “" + bin + "\" too long");
        }
        while (bin.length() < width) bin = "0" + bin;
        return bin;
    }

    private String select(String op) throws AssemblerException {
        String base = op;
        String code;
        if (base.matches("[-]?(0x|0b)?[0-9A-Fa-f]+"))   code = "00";
        else if (base.matches("r(0x|0b)?[0-9A-Fa-f]+")) code = "01";
        else if (base.matches("§(0x|0b)?[0-9A-Fa-f]+")) code = "10";
        else throw new AssemblerException("Could not select opsel for '" + base + "'");
        return code;
    }

    private String padData(String op) {
        String val = op.replaceFirst("#", "").replaceFirst("IO", "");
        String bin = toBinary(val);
        return checkLen64(bin);
    }

    private String toBinary(String v) {
        long n = v.startsWith("0x")
            ? Long.parseLong(v.substring(2),16)
            : (v.startsWith("0b")
            ? Long.parseLong(v.substring(2),2)
            : Long.parseLong(v));
        return Long.toBinaryString(n);
    }

    private String checkLen64(String b) {
        if (b.length() > 64) return b.substring(0,64);
        while (b.length() < 64) b = "0" + b;
        return b;
    }

    private static class ThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(
            Recognizer<?,?> r, Object o, int line,
            int pos, String msg, RecognitionException e) {
            throw new ParseCancellationException("line " + line + ":" + pos + " " + msg);
        }
    }
}