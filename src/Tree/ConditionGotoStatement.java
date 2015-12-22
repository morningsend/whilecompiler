package Tree;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class ConditionGotoStatement extends GotoStatement {
    public enum JumpCondition {
        JumpIfNegative,
        JumpIfPositive,
        JumpIfZero,
        JumpIfNonZero
    }
    public JumpCondition condition;
    public Identifier variable;

    public JumpCondition getCondition() {
        return condition;
    }

    public void setCondition(JumpCondition condition) {
        this.condition = condition;
    }

    public Identifier getVariable() {
        return variable;
    }

    public void setVariable(Identifier variable) {
        this.variable = variable;
    }

    public ConditionGotoStatement(JumpCondition cond, Identifier testExpression, Statement destination){
        super(destination);
        this.condition = cond;
        this.variable = testExpression;

    }
}
