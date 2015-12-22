package Tree;

import Model.DataType;
import Model.Operator;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class BooleanUnaryExpression extends BooleanExpression implements UnaryOperation{
    public Operator operator;
    public Expression operand;
    public BooleanUnaryExpression(Operator op, Expression expr){
        dataType = DataType.Boolean;
        operand = expr;
        operator = op;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public void setOperand(Expression operand) {
        this.operand = operand;
    }

    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public Expression getOperand() {
        return operand;
    }
}
