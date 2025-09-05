package tobi.tools.hardware;

public class Operation {
    private String ALU_Mode; // 4
    private boolean Increment; // 1
    private boolean RAM_we; // 1
    private boolean Reg_we; // 1


    public String getALU_Mode() {
        return ALU_Mode;
    }

    public boolean isIncrement() {
        return Increment;
    }

    public boolean isRAM_we() {
        return RAM_we;
    }

    public boolean isReg_we() {
        return Reg_we;
    }

    public boolean isA_en() {
        return A_en;
    }

    public boolean isB_en() {
        return B_en;
    }

    public boolean isFlags_we() {
        return Flags_we;
    }

    private boolean A_en; // 1
    private boolean B_en; // 1
    private boolean Flags_we; // 1

    public Operation(String ooDef) {
        String[] parts = ooDef.split("\\s+");
        this.ALU_Mode = switch (parts[1]) {
            case "ADD" -> "0000";
            case "SUB" -> "0001";
            case "MUL" -> "0010";
            case "DIV" -> "0011";
            case "AND" -> "0100";
            case "OR" -> "0101";
            case "XOR" -> "0110";
            case "CMP" -> "0111";
            case "SHIFT" -> "1000";
            case "COMP_A" -> "1001";
            case "MOD" -> "1010";
            case "ADDF" -> "1011";
            case "SUBF" -> "1100";
            case "MULF" -> "1101";
            case "DIVF" -> "1110";
            case "CMPF" -> "1111";
            default -> throw new IllegalArgumentException("Unknown ALU mode: " + parts[2]);
        };
        this.Increment = parts[2].equals("1");
        this.RAM_we = parts[3].equals("1");
        this.Reg_we = parts[4].equals("1");
        this.A_en = parts[5].equals("1");
        this.B_en = parts[6].equals("1");
        this.Flags_we = parts[7].equals("1");
    }

    @Override
    public String toString() {

        return "Operation{" +
                "ALU_Mode='" + ALU_Mode + '\'' +
                ", Increment=" + Increment +
                ", RAM_we=" + RAM_we +
                ", Reg_we=" + Reg_we +
                ", A_en=" + A_en +
                ", B_en=" + B_en +
                ", Flags_we=" + Flags_we +
                '}';
    }
}