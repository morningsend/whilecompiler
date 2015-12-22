package Tree;

import Semantics.LocalScope;
import Semantics.Scope;

import java.util.List;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class BlockStatement extends Statement implements LocalScope{
    private StatementList statements;

    protected Scope innerScope;
    protected Scope parentScope;

    public Statement getBody(){
        return this;
    }
    public List<Statement> getBlockBody(){
        return statements.getStatements();
    }
    public StatementList getStatements(){ return statements; }
    public void setStatements(StatementList stmts){
        this.statements = stmts;
    }
    public int getStatementCount(){
        return statements.getStatements().size();
    }
    public BlockStatement(){

    }
    public BlockStatement(StatementList list){
        this.statements = list;
    }
    public void setLabel(int i, String label){
        statements.getStatement(i).setLabel(label);
    }
    public String getLabel(int i ){
        return statements.getStatement(i).getLabel();
    }
    @Override
    public Scope getParentScope() {
        return parentScope;
    }
    public void setParentScope(Scope parent){
        this.parentScope = parent;
    }
    @Override
    public Scope getInnerScope() {
        return innerScope;
    }
    public void setInnerScope(Scope inner){
        this.innerScope = inner;
    }
}
