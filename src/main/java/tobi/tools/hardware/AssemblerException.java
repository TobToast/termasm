package tobi.tools.hardware;

public class AssemblerException extends Exception {
    public AssemblerException(String msg) {
        super(msg);
    }
    public AssemblerException() {
        super();
    }
    public static class UnknownMnemonicException extends AssemblerException {
        public UnknownMnemonicException(String msg) {
            super(msg);
        }
    }
    public static class BinaryLengthException extends AssemblerException {
        public BinaryLengthException(String msg) {
            super(msg);
        }
}
}