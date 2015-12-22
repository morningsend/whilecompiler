package Tree;

import Model.DataType;
import Model.Kind;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class IntegerLiteral extends NumericalLiteral{

    public int intValue;

    public IntegerLiteral(String value) {
        super();
        this.intValue = Integer.parseInt(value);
        this.dataType = DataType.Integer;
        this.kind = Kind.Constant;
    }
    public int getIntValue(){
        return intValue;
    }
}
