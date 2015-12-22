package Tree;

import Model.DataType;

/**
 * Created by Zaiyang on 10/12/2015.
 */
public class ArrayDefinition extends Expression{
    public IntegerLiteral length;
    public DataType elementType;

    public DataType getElementType() {
        return elementType;
    }

    public void setElementType(DataType elementType) {
        this.elementType = elementType;
    }

    public ArrayDefinition(){
        this(null);
    }
    public ArrayDefinition(IntegerLiteral length){
        this.length = length;
        dataType = DataType.Array;
        elementType = DataType.Integer;
    }

    public IntegerLiteral getLength() {
        return length;
    }

    public void setLength(IntegerLiteral length) {
        this.length = length;
    }
}
