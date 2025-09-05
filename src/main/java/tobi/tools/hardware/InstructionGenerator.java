package tobi.tools.hardware;

public class InstructionGenerator {
    


    public static String generateInstruction(ALUMode alumode, boolean inc, boolean RAM_we, boolean Reg_we, boolean OP_A_en, boolean OP_B_en, boolean Flags_we) {

        String alumodebin = switch (alumode) {
            case ADD -> "0000";
            case SUB -> "0001";
            case MUL -> "0010";
            case DIV -> "0011";
            case AND -> "0100";
            case OR -> "0101";
            case XOR -> "0110";
            case CMP -> "0111";
            case SHIFT -> "1000";
            case COMP_A -> "1001";
            case MOD -> "1010";
            case ADDF -> "1011";
            case SUBF -> "1100";
            case MULF -> "1101";
            case DIVF -> "1110";
            case CMPF -> "1111";
            default -> throw new IllegalArgumentException("Unknown ALU mode: " + alumode);
        };

        String incbin = inc ? "1" : "0";

        String ramwebin = RAM_we ? "1" : "0";

        String regwebin = Reg_we ? "1" : "0";

        String opAenbin = OP_A_en ? "1" : "0";
        String opBenbin = OP_B_en ? "1" : "0";

        String modifyflagsbin = Flags_we ? "1" : "0";

        return modifyflagsbin + opBenbin + opAenbin + regwebin + ramwebin + incbin + alumodebin;

    }

}
