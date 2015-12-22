package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class ParameterList extends SyntaxTreeNode{
    List<Expression> parameters = new ArrayList<Expression>();
    public boolean isEmpty(){
        return parameters.isEmpty();
    }
    public int getSize(){
        return parameters.size();
    }
    public ParameterList(){
    }
    public ParameterList(List<Expression> params){
        this.parameters.addAll(params);
    }
    public Expression getParameter(int i){
        return parameters.get(i);
    }
    public List<Expression> getParameters(){
        return parameters;
    }
}
