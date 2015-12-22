package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class StatementList extends SyntaxTreeNode{
    List<Statement> statements = new ArrayList<Statement>();

    public List<Statement> getStatements(){
        return statements;
    }
    public Statement getStatement(int i ){
        return statements.get(i);
    }
    public StatementList(){

    }
    public StatementList(List<Statement> stmts){
        this.statements.addAll(stmts);
    }
    public StatementList(Statement stmt){
        this.statements.add(stmt);
    }
}
