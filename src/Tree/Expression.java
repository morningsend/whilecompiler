package Tree;

import Model.DataType;
import Model.Kind;
import Model.ValueType;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class Expression extends SyntaxTreeNode{
    public ValueType valueType;
    public DataType dataType = DataType.Unresolved;
    public Kind kind;

    public Expression(){

    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }
}
