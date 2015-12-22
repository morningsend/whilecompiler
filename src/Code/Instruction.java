package Code;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class Instruction {
    public String label;
    public List<Operand> operands= new ArrayList<Operand>();
    public OpCode opCode;

    @Override public String toString(){
        StringBuilder sb = new StringBuilder(19);
        if(label !=null && !label.isEmpty()){
            sb.append(label).append(": ");
        }else
            sb.append("\t\t");
        sb.append(opCode).append(" ");
        int i;
        for(i = 0; i<operands.size()-1;i++){
            sb.append(operands.get(i)+", ");
        }
        if(operands.size() >0){
            sb.append(operands.get(i));
        }
        sb.append(";");
        return sb.toString();
    }
    public Instruction(){

    }
    public Instruction(OpCode code, Operand ... operands){
        this.opCode = code;
        if(operands!=null){
            for(Operand opr : operands){
                this.operands.add(opr);
            }
        }
    }
    public Instruction(String label, OpCode code, Operand...operands){
        this(code, operands);
        setLabel(label);
    }

    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
    public List<Operand> getOperands(){
        return operands;
    }
    public OpCode getOpCode(){
        return opCode;
    }
    public void setOpCode(OpCode op){
        this.opCode = op;
    }
}
