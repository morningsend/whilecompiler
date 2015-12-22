package Tree;

/**
 * Created by Zaiyang on 13/12/2015.
 */
public class ExpressionStatement extends Statement {
    public Expression expression;

    public ExpressionStatement(Expression expression){
        this.expression = expression;
    }
    public ExpressionStatement(){}
    public Expression getExpression(){
        return expression;
    }
    public void setExpression(Expression expression){
        this.expression = expression;
    }
}
