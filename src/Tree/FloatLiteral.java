package Tree;

import Model.DataType;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class FloatLiteral extends NumericalLiteral {
    public float floatValue;

    public FloatLiteral(String text){
        super();
        floatValue = Float.parseFloat(text);
        dataType = DataType.Real;
    }

    public float getFloatValue() {
        return floatValue;
    }

    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }
}
