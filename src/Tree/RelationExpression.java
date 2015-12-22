package Tree;

import Model.Operator;

/**
 * Created by Zaiyang on 10/12/2015.
 */
public class RelationExpression extends BooleanExpression implements BinaryOperation {

    public Operator op;
    public Expression leftOperand;
    public Expression rightOperand;
    public RelationExpression(Operator op, Expression left, Expression right){
        this.op=op;
        this.leftOperand = left;
        this.rightOperand = right;
    }
    @Override
    public Operator getOperator() {
        return op;
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
