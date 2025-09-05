grammar Assembly;

@header {
package tobi.tools.hardware;


import java.nio.charset.StandardCharsets;
import java.util.*;
}


@members {
  private static final java.util.Set<String> MNEMONICS = loadMnemonics();
  private static java.util.Set<String> loadMnemonics() {
    java.util.Set<String> set = new java.util.HashSet<>();
    try (java.io.InputStream is = AssemblyLexer.class.getResourceAsStream("/ops.txt");
         java.util.Scanner sc = new java.util.Scanner(is, java.nio.charset.StandardCharsets.UTF_8)) {
      sc.nextLine(); // Header skip
      while (sc.hasNextLine()) {
        String token = sc.nextLine().split("\\s+")[0];
        int l = token.indexOf('('), r = token.indexOf(')');
        if (l>=0 && r>l) {
          for (String m : token.substring(l+1,r).split("/")) {
            set.add(m.toLowerCase());
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return set;
  }
}

assemblyprog
    : (statement | labeldef)* EOF
    ;

statement
    : MNEMONIC (opa=operand)? (opb=operand)?
      (ARROW REGISTER)? ('{' predicate=number '}')?
    ;

labeldef 
    : label ':'
    ;

label
    : ('.')? ID ('.' ID)*
    ;

operand
    : directOperand
    | indirectOperand
    ;

indirectOperand
    : '#' directOperand
    ;

directOperand
    : val=literal
    | reg=REGISTER
    | io=ioOperand
    ;

ioOperand
    : '§' literal
    ;

literal
    : constantExpression
    | label
    | '<' label '>'
    | '"' .*? '"' 
    | '\'' .*? '\''
    ;

constantExpression
    : '(' constantExpression ')'
    | constantExpression op=('*'|'/') constantExpression
    | constantExpression op=('+'|'-') constantExpression
    | constantExpression op='^' constantExpression
    | number
    ;

number
    : '-'? (HEX_NUMBER | BIN_NUMBER | DEC_NUMBER)
    ;


HEX_NUMBER
    : '0x' [0-9A-Fa-f]+
    ;

BIN_NUMBER
    : '0b' [01]+
    ;

DEC_NUMBER
    : [0-9]+
    ;


ARROW
    : '->'
    ;
    

REGISTER
    : 'r' [0-9]+
    | 'pc' | 'ip'
    ;

MNEMONIC
    : [a-zA-Z]+ ? { MNEMONICS.contains(getText().toLowerCase()) }?
    ;
    
ID
    : [a-zA-Z_][0-9A-Za-z_]*
    ;

WS
    : [ \n\r\t]+ -> skip
    ;

COMMENT
    : ('//' | ';') ~[\r\n]* -> channel(HIDDEN)
    ;

MULTI_COMMENT
    : '/*' .*? '*/' -> channel(HIDDEN)
    ;