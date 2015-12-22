package Tree;

import Model.DataType;
import Semantics.Scope;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class Variable extends SyntaxTreeNode{
    public DataType dataType;
    public String value;
    public String identifierName;
    public int storageSize ;
    public boolean initialized=false;
    public Scope boundedScope;

    public Variable(String name){
        dataType = DataType.Void;
        value = "";
        identifierName = name;
        storageSize = -1;
        initialized = false;
    }
    public Variable(String name, DataType type, String value, boolean init){
        this.identifierName = name;
        this.dataType = type;
        this.value = value;
        this.initialized = init;
    }
    public Variable(){
        this("");
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }

    public int getStorageSize() {
        return storageSize;
    }

    public void setStorageSize(int storageSize) {
        this.storageSize = storageSize;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}
