package Tree;

import Model.Operator;

/**
 * Created by Zaiyang on 10/12/2015.
 */
public class NumericalBinaryExpression extends NumericalExpression implements BinaryOperation {
    public Operator operator;
    public Expression leftOperand;
    public Expression rightOperand;

    public NumericalBinaryExpression(Operator op, Expression left, Expression right){
        operator = op;
        leftOperand = left;
        rightOperand = right;
    }
    @Override
    public Operator getOperator() {
        return operator;
    }

    @Override
    public Expression getLeftOperand() {
        return leftOperand;
    }

    @Override
    public Expression getRightOperand() {
        return rightOperand;
    }
}
