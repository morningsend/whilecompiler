package Tree;

import Model.Operator;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public interface UnaryOperation {
    Operator getOperator();
    Expression getOperand();
}
