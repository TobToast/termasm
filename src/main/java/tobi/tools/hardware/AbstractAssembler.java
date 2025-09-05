package tobi.tools.hardware;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public interface AbstractAssembler {
    public String assemble(String asm) throws AssemblerException, FileNotFoundException, URISyntaxException;
}
