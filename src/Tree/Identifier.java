package Tree;

import Model.Kind;
import Semantics.Scope;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class Identifier extends Expression implements Identifiable{
    public String name;
    public Scope boundScope;
    public Identifier(String name){
        this.name = name;
    }
    public Identifier(){
        kind=Kind.Variable;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public void setBoundScope(Scope scope){
        boundScope = scope;
    }
    public Scope getBoundScope(){
        return boundScope;
    }

    @Override
    public String getIdentierName() {
        return name;
    }
}
