package Tree;

import Model.DataType;
import Model.Kind;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class NumericalLiteral extends NumericalExpression{
    public NumericalLiteral(){
        dataType = DataType.Void;
        kind = Kind.Constant;
    }
}
