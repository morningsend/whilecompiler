package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class FlowControlStatement extends Statement {

    private Expression condition;

    public Expression getCondition(){
        return condition;
    }

    public void setCondition(BooleanExpression boolexp){
        condition = boolexp;
    }

    public FlowControlStatement(Expression condition){
        this.condition = condition;
    }
    public FlowControlStatement(){

    }
}
