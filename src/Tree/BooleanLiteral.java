package Tree;

import Model.DataType;
import Model.Kind;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class BooleanLiteral extends BooleanExpression{
    public boolean boolValue;
    public BooleanLiteral(String value) {
        super();
        String lowerCase = value.toLowerCase();
        if(lowerCase.equals("true")){
            boolValue = true;
        }else if(lowerCase.equals("false")){
            boolValue = false;
        }else {
            throw new RuntimeException("Error in Boolean String, not valid");
        }
        this.dataType = DataType.Boolean;
        this.kind = Kind.Constant;
    }
    public BooleanLiteral(){
        this("false");
    }
    public boolean getBoolValue(){
        return boolValue;
    }
    public void setBoolValue(boolean b){
        boolValue = b;
    }
    public void setBoolValue(String bStr){
        boolValue = Boolean.parseBoolean(bStr);
    }
}
