package Tree;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class AssignmentExpression extends Expression {
    public Expression variable;
    public Expression value;
    public AssignmentExpression(Expression dest, Expression source){
        variable = dest;
        value = source;
    }

    public Expression getVariable() {
        return variable;
    }

    public void setVariable(Expression variable) {
        this.variable = variable;
    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
