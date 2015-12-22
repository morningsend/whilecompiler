package Tree;

import Model.DataType;
import Semantics.LocalScope;
import Semantics.Scope;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class FunctionDefinition extends Statement implements LocalScope{
    public Identifier iden;
    public BlockStatement body;
    public ParameterList paramList;
    public DataType returnType;
    public Scope innerScope;
    public Scope parentScope;

    @Override
    public Scope getParentScope() {
        return parentScope;
    }

    public void setParentScope(Scope scope){
        this.parentScope = scope;
    }
    public Scope getInnerScope() {
        return innerScope;
    }

    public void setInnerScope(Scope innerScope) {
        this.innerScope = innerScope;
    }

    public FunctionDefinition(Identifier iden, ParameterList params, BlockStatement body) {
        super();
        this.iden = iden;
        paramList =params;
        this.body = body;
    }

    public String getName() {
        return iden.getName();
    }

    public void setName(String name) {
        this.iden.setName(name);
    }

    public Identifier getIdentifier() {
        return iden;
    }

    public void setIdentifier(Identifier iden) {
        this.iden = iden;
    }

    public BlockStatement getBody() {
        return body;
    }

    public void setBody(BlockStatement body) {
        this.body = body;
    }

    public ParameterList getParameters() {
        return paramList;
    }

    public void setParameters(ParameterList list) {
        this.paramList = list;
    }

    public DataType getReturnType() {
        return returnType;
    }

    public void setReturnType(DataType returnType) {
        this.returnType = returnType;
    }
}
