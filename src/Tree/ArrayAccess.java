package Tree;

import Model.DataType;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class ArrayAccess extends VariableAccess {
    public Expression indexExpression;
    public DataType elementType;



    public ArrayAccess(){}
    public ArrayAccess(String name, Expression exp) {
        super(name);
        this.indexExpression = exp;
    }

    public Expression getIndexExpression() {
        return indexExpression;
    }

    public void setIndexExpression(Expression indexExpression) {
        this.indexExpression = indexExpression;
    }

    public DataType getElementType() {
        return elementType;
    }

    public void setElementType(DataType elementType) {
        this.elementType = elementType;
    }
}
