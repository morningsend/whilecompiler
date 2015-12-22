package Tree;

import Model.DataType;
import Model.Operator;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class BooleanBinaryExpression extends BooleanExpression implements BinaryOperation {
    public Operator operator;
    public Expression leftOperand;
    public Expression rightOperand;

    public BooleanBinaryExpression(Operator op, Expression left, Expression right){
        this.operator = op;
        this.leftOperand = left;
        this.rightOperand = right;
        this.dataType = DataType.Boolean;
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
