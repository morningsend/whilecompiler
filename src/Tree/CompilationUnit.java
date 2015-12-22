package Tree;

import Semantics.Scope;

/**
 * Created by Zaiyang on 13/12/2015.
 */
public class CompilationUnit extends SyntaxTreeNode {

    public StatementList programBody;
    public Scope globalScope;
    public CompilationUnit(StatementList body){
        programBody = body;
        globalScope = new Scope();
    }
    public Scope getGlobalScope(){
        return globalScope;
    }
    public void setGlobalScope(Scope scope){
        this.globalScope = scope;
    }

    public void setProgramBody(StatementList list){
        this.programBody = list;
    }
    public StatementList getProgramBody(){
        return this.programBody;
    }
}
