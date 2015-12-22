package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class ArgumentList extends ParameterList{
    List<Expression> args;
    ArgumentList(List<Expression> args){
        this.args = new ArrayList<Expression>(args);
    }

    public Expression getArgument(int i ){
        return args.get(i);
    }

}
