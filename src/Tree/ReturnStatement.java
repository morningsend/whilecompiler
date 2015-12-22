package Tree;

import Model.DataType;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class ReturnStatement extends Statement{
    public Expression expression = null;
    public DataType getReturnType(){
        return DataType.Void;
    }
    public Expression getExpression(){
        return expression;
    }
    public boolean isEmptyExpression(){
        return getReturnType()==DataType.Void && expression == null;
    }
    public ReturnStatement(Expression exp){
        super();
        this.expression = exp;
    }
    public ReturnStatement(){
        super();
    }
}
