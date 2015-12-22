package Tree;

import Model.Operator;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public interface BinaryOperation  {
    public Operator getOperator();
    public Expression getLeftOperand();
    public Expression getRightOperand();
}
