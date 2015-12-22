package Tree;

import Model.Operator;

/**
 * Created by Zaiyang on 10/12/2015.
 */
public class NumericalUnaryExpression extends NumericalExpression implements UnaryOperation{
    public Operator operator;
    public NumericalExpression operand;

    public NumericalUnaryExpression(Operator op, NumericalExpression operand){
        this.operator = op;
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
