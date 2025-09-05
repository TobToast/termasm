package tobi.tools.hardware;

import java.io.FileNotFoundException;

public interface AbstractAssembler {
    public String assemble(String asm) throws AssemblerException, FileNotFoundException;
}
