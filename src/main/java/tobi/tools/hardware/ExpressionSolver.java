package tobi.tools.hardware;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Runtime expression solver that parses and evaluates arithmetic expressions.
 *
 * Supported:
 *  - Numbers: integers, decimals, scientific notation (e.g. 1e-3)
 *  - Operators: +, -, *, /, ^ (power)
 *  - Parentheses: ( )
 *  - Unary prefix: +, - (e.g. -3, +2.5, -(1+2))
 *
 * Evaluation uses BigDecimal with configurable precision. Power (^) falls back
 * to double-based exponentiation when the exponent is not an integer.
 *
 * Usage:
 *   ExpressionSolver solver = new ExpressionSolver(new MathContext(34, RoundingMode.HALF_UP));
 *   String result = solver.evaluate("-3 + 4*(2-1)^3 / 7");
 *   System.out.println(result); // prints the exact decimal without scientific notation
 */
public final class ExpressionSolver {
    private final MathContext mc;
    private final int scale; // for division fallback

    public ExpressionSolver() {
        this(new MathContext(34, RoundingMode.HALF_UP));
    }

    public ExpressionSolver(MathContext mc) {
        this(mc, 50);
    }

    public ExpressionSolver(MathContext mc, int scale) {
        this.mc = mc;
        this.scale = Math.max(16, scale);
    }

    /** Evaluate expression and return the result as a plain string. */
    public String evaluate(String expression) {
        if (expression == null) throw new IllegalArgumentException("expression is null");
        List<Token> rpn = toRPN(tokenize(expression));
        BigDecimal value = evalRPN(rpn);
        return toPlain(value);
    }

    // ---------------- Tokenizer ----------------
    private enum TokType { NUMBER, OP, LPAREN, RPAREN }

    private static final class Token {
        final TokType type;
        final String text;
        Token(TokType type, String text) { this.type = type; this.text = text; }
        public String toString() { return type + ":" + text; }
    }

    private List<Token> tokenize(String s) {
        List<Token> list = new ArrayList<>();
        int i = 0; int n = s.length();
        Token prev = null;
        while (i < n) {
            char c = s.charAt(i);
            if (Character.isWhitespace(c)) { i++; continue; }

            if (s.length() > i + 1 && c == '0' && s.charAt(i+1) == 'x') {
                int start = i;
                i += 2;
                while (i < n && ((s.charAt(i) >= '0' && s.charAt(i) <= '9') ||
                                (s.charAt(i) >= 'a' && s.charAt(i) <= 'f') ||
                                (s.charAt(i) >= 'A' && s.charAt(i) <= 'F'))) {
                    i++;
                }
                list.add(prev = new Token(TokType.NUMBER, String.valueOf(Long.parseLong(s.substring(start, i).substring(2), 16))));
                continue;
            }
            if (s.length() > i + 1 && c == '0' && s.charAt(i+1) == 'b') {
                int start = i;
                i += 2;
                while (i < n && (s.charAt(i) == '0' || s.charAt(i) == '1')) {
                    i++;
                }
                list.add(prev = new Token(TokType.NUMBER, String.valueOf(Long.parseLong(s.substring(start, i).substring(2), 2))));
                continue;
            }
            // Number (supports decimal and scientific notation)
            if (Character.isDigit(c) || c == '.') {
                int start = i;
                i = readNumber(s, i);
                list.add(prev = new Token(TokType.NUMBER, s.substring(start, i)));
                continue;
            }

            // Parentheses
            if (c == '(') { list.add(prev = new Token(TokType.LPAREN, "(")); i++; continue; }
            if (c == ')') { list.add(prev = new Token(TokType.RPAREN, ")")); i++; continue; }

            // Operators
            if (isOperatorChar(c)) {
                String op = String.valueOf(c);
                // Distinguish unary +/-. If at start, after another operator, or after '(', treat as unary.
                if ((c == '+' || c == '-') && (prev == null || prev.type == TokType.OP || prev.type == TokType.LPAREN)) {
                    // absorb a following number if present to form a signed literal
                    int j = i + 1;
                    if (j < n) {
                        char d = s.charAt(j);
                        if (Character.isDigit(d) || d == '.') {
                            int start = i; // include sign
                            j = readNumber(s, j);
                            list.add(prev = new Token(TokType.NUMBER, s.substring(start, j)));
                            i = j;
                            continue;
                        }
                    }
                    // Otherwise, it's a unary operator applied to an expression: represent as 0 +/- x
                    if (c == '+') {
                        // unary plus is a no-op; represent as +0 to keep shunting-yard simple
                        list.add(new Token(TokType.NUMBER, "0"));
                        list.add(prev = new Token(TokType.OP, "+"));
                        i++;
                        continue;
                    } else {
                        list.add(new Token(TokType.NUMBER, "0"));
                        list.add(prev = new Token(TokType.OP, "-"));
                        i++;
                        continue;
                    }
                } else {
                    list.add(prev = new Token(TokType.OP, op));
                    i++;
                    continue;
                }
            }

            throw new IllegalArgumentException("Unerwartetes Zeichen an Position " + i + ": '" + c + "'");
        }
        return list;
    }

    private int readNumber(String s, int i) {
        int n = s.length();
        int j = i;
        boolean hasDot = false;
        while (j < n) {
            char ch = s.charAt(j);
            if (Character.isDigit(ch)) { j++; continue; }
            if (ch == '.') {
                if (hasDot) break; // second dot ends number
                hasDot = true; j++; continue;
            }
            // scientific notation: e or E, optional sign, must be followed by digits
            if ((ch == 'e' || ch == 'E')) {
                int k = j + 1;
                if (k < n && (s.charAt(k) == '+' || s.charAt(k) == '-')) k++;
                int startDigits = k;
                while (k < n && Character.isDigit(s.charAt(k))) k++;
                if (k == startDigits) break; // no digits after e -> not part of number
                j = k; continue;
            }
            break;
        }
        return j;
    }

    private boolean isOperatorChar(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    // ---------------- Shunting-yard to RPN ----------------
    private static final class OpInfo {
        final int prec; final boolean rightAssoc;
        OpInfo(int p, boolean r) { this.prec = p; this.rightAssoc = r; }
    }

    private static final java.util.Map<String, OpInfo> OPS = java.util.Map.of(
        "+", new OpInfo(1, false),
        "-", new OpInfo(1, false),
        "*", new OpInfo(2, false),
        "/", new OpInfo(2, false),
        "^", new OpInfo(3, true)
    );

    private List<Token> toRPN(List<Token> infix) {
        List<Token> out = new ArrayList<>();
        Deque<Token> stack = new ArrayDeque<>();
        for (Token t : infix) {
            switch (t.type) {
                case NUMBER -> out.add(t);
                case OP -> {
                    OpInfo oi = OPS.get(t.text);
                    if (oi == null) throw new IllegalArgumentException("Unbekannter Operator: " + t.text);
                    while (!stack.isEmpty() && stack.peek().type == TokType.OP) {
                        OpInfo top = OPS.get(stack.peek().text);
                        if (top == null) break;
                        boolean pop = (!oi.rightAssoc && oi.prec <= top.prec) || (oi.rightAssoc && oi.prec < top.prec);
                        if (pop) out.add(stack.pop()); else break;
                    }
                    stack.push(t);
                }
                case LPAREN -> stack.push(t);
                case RPAREN -> {
                    boolean found = false;
                    while (!stack.isEmpty()) {
                        Token x = stack.pop();
                        if (x.type == TokType.LPAREN) { found = true; break; }
                        out.add(x);
                    }
                    if (!found) throw new IllegalArgumentException("Klammern nicht ausgeglichen");
                }
            }
        }
        while (!stack.isEmpty()) {
            Token x = stack.pop();
            if (x.type == TokType.LPAREN || x.type == TokType.RPAREN) throw new IllegalArgumentException("Klammern nicht ausgeglichen");
            out.add(x);
        }
        return out;
    }

    // ---------------- Evaluate RPN ----------------
    private BigDecimal evalRPN(List<Token> rpn) {
        Deque<BigDecimal> st = new ArrayDeque<>();
        for (Token t : rpn) {
            switch (t.type) {
                case NUMBER -> st.push(parseBD(t.text));
                case OP -> {
                    if (st.size() < 2) throw new IllegalArgumentException("Fehlender Operand für Operator '" + t.text + "'");
                    BigDecimal b = st.pop();
                    BigDecimal a = st.pop();
                    st.push(applyOp(t.text, a, b));
                }
                default -> throw new IllegalStateException("Unerwartetes Token in RPN: " + t);
            }
        }
        if (st.size() != 1) throw new IllegalStateException("Interner Fehler: Stackgröße != 1 am Ende");
        return st.pop();
    }

    private BigDecimal parseBD(String num) {
        // BigDecimal handles scientific notation via string constructor
        try {
            return new BigDecimal(num, mc);
        } catch (NumberFormatException nfe) {
            throw new IllegalArgumentException("Ungültige Zahl: '" + num + "'");
        }
    }

    private BigDecimal applyOp(String op, BigDecimal a, BigDecimal b) {
        return switch (op) {
            case "+" -> a.add(b, mc);
            case "-" -> a.subtract(b, mc);
            case "*" -> a.multiply(b, mc);
            case "/" -> divide(a, b);
            case "^" -> pow(a, b);
            default -> throw new IllegalArgumentException("Unbekannter Operator: " + op);
        };
    }

    private BigDecimal divide(BigDecimal a, BigDecimal b) {
        if (b.compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division durch 0");
        // Try exact first; otherwise use configured scale and rounding
        try {
            return a.divide(b, mc);
        } catch (ArithmeticException rep) {
            return a.divide(b, scale, mc.getRoundingMode());
        }
    }

    private BigDecimal pow(BigDecimal a, BigDecimal b) {
        // If exponent is an integer within range, use BigDecimal.pow for precision
        try {
            int exp = b.intValueExact();
            return a.pow(exp, mc);
        } catch (ArithmeticException notInt) {
            // fallback to double power for non-integer exponents
            double base = a.doubleValue();
            double exp = b.doubleValue();
            if (base < 0 && Math.abs(exp - Math.rint(exp)) > 1e-12) {
                throw new ArithmeticException("Nicht-integer Exponent für negative Basis ist nicht definiert");
            }
            double val = Math.pow(base, exp);
            return new BigDecimal(val, mc);
        }
    }

    private String toPlain(BigDecimal bd) {
        bd = bd.stripTrailingZeros();
        String s = bd.toPlainString();
        // normalize -0
        if (s.equals("-0")) s = "0";
        if (s.equals("-0.0")) s = "0";
        return s;
    }

    // --- quick demo ---
    public static void main(String[] args) {
        ExpressionSolver solver = new ExpressionSolver();
        String[] tests = {
            "1+2*3",
            "(1+2)*3",
            "-3 + 4*(2-1)^3 / 7",
            "2^10 + 1",
            "1/3",
            "1e3 + 2.5e-1",
            "-(2^3)^2", // equals -64 if parsed as -( (2^3)^2 ) due to unary handling
        };
        for (String t : tests) {
            System.out.println(t + " = " + solver.evaluate(t));
        }
    }
}
