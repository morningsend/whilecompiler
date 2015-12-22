package Code;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class CodeGen {

    protected PrintStream out;
    protected boolean storeInstructionInBuffer;
    protected List<Instruction> instructionBuffer = new ArrayList<Instruction>();
    public CodeGen(PrintStream out){
        this.out = out;
    }
    public void emitCode(Instruction instruction){
        if(storeInstructionInBuffer){
            instructionBuffer.add(instruction);
        }else {
            out.print(instruction.toString() + "\n");
        }
    }
    public void writeMemory(Memory memory){
        List<Memory.Object> allObjects = memory.getObjects();
        for(Memory.Object obj : allObjects){
            byte[] bytes = obj.toBytes();
            writeBytes(bytes);
        }
    }
    public void beginProgram(){

    }
    public void beginProgram(Instruction ins){
        emitCode(ins);
    }

    public void endProgram(){
        emitCode(new Instruction(OpCode.HALT));
    }
    public void endProgram(Instruction i){
        emitCode(i);
    }

    public void writeBytes(byte[] bytes){
        for(int i = 0; i< bytes.length;i++){
            emitCode(new Instruction(OpCode.DATA, new Literal<Byte>(bytes[i])));
        }
    }
    public void setOutStream(PrintStream out){
        this.out = out;
    }
    public void writeInstructionBufferToStream(){
        CodeGen.writeInstructionBufferToStream(out, instructionBuffer);
    }

    public void setStoreIntructionInBuffer(boolean flag){
        storeInstructionInBuffer = flag;
    }

    public void optimizeRegisterAllocation(){

    }
    public List<Instruction> getInstructionBuffer(){
        return instructionBuffer;
    }
    public static void writeInstructionBufferToStream(PrintStream out, List<Instruction> buffer){
        if(buffer.size()==0) return;
        for(Instruction i : buffer){
            out.println(i);
        }
        out.close();
    }
}
