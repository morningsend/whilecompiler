package Semantics;

import Model.DataType;
import Tree.ArrayDefinition;

/**
 * Created by Zaiyang on 20/12/2015.
 */
public class ArrayElement extends Element {
    public DataType elementType;
    public ArrayDefinition definition;

    public ArrayDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(ArrayDefinition definition) {
        this.definition = definition;
    }

    public DataType getElementType() {
        return elementType;
    }

    public void setElementType(DataType elementType) {
        this.elementType = elementType;
    }
}
