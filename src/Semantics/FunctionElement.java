package Semantics;

import Model.DataType;

/**
 * Created by Zaiyang on 20/12/2015.
 */
public class FunctionElement extends Element{
    public DataType getReturnType() {
        return returnType;
    }

    public void setReturnType(DataType returnType) {
        this.returnType = returnType;
    }

    public DataType returnType = DataType.Unresolved;

}
