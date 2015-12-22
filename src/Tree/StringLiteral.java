package Tree;

import Model.DataType;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class StringLiteral extends Expression{
    public String value;

    public StringLiteral(String text) {
        dataType=DataType.String;
        value = text;
    }
    public String getString(){
        return value;
    }

}
