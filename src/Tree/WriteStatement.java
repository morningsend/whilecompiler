package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class WriteStatement extends Statement {
    public Expression expression;

    public WriteStatement(Expression exp){
        super();
        this.expression = exp;
    }

    public WriteStatement(){
        super();
    }
    public void setExpression(Expression exp){
        expression = exp;
    }
    public Expression getExpression(){
        return expression;
    }
}
