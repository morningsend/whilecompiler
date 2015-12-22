package Tree;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class FunctionCall extends Identifier{

    public ParameterList getArgs() {
        return args;
    }

    public void setArgs(ParameterList args) {
        this.args = args;
    }

    public String getFunctionName() {
        return getName();
    }

    public void setFunctionName(String functionName) {
        setName(functionName);
    }

    public ParameterList args;

    public FunctionCall(String funcName, ParameterList args){
        super(funcName);
        this.args = args;
    }

}
