package Code;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public enum OpCode {
    ADD,
    SUB,
    MUL,
    DIV,
    XOR,
    ADDR,
    SUBR,
    MULR,
    DIVR,
    ADDI,
    SUBI,
    MULI,
    DIVI,
    XORI,
    MOVIR,
    ITOR,
    RTOI,
    RD,
    RDR,
    WR,
    WRR,
    WRS,
    LOAD,
    STORE,
    JMP,
    JUMP,
    IADDR,
    BEGZ,
    BGEZR,
    BLTZ,
    BLTZR,
    BEQZ,
    BNEZ,
    BNEZR,
    NOP,
    HALT,
    BGEZ, DATA;

    public static OpCode getFloatOpCode(OpCode op) {
        OpCode fop;
        switch(op){
            case ADD:
                fop = ADDR;
                break;
            case SUB:
                fop = SUBR;
                break;
            case MUL:
                fop = MULR;
                break;
            case DIV:
                fop = DIVR;
                break;
            default:
                fop = op;
        }
        return fop;
    }
}
