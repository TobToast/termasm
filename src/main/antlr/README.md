lexer grammar AssemblyLexer;
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

// Ganz oben: → ‚->‘ muss vor dem ID-Matching stehen
