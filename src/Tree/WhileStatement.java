package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class WhileStatement extends FlowControlStatement {

    public Statement whileBody;


    public WhileStatement(Expression condition, Statement doPart) {
        super(condition);
        this.whileBody = doPart;
    }

    public Statement getWhileBody(){
        return whileBody;
    }
    public void setWhileBody(Statement stmt){

        this.whileBody = stmt;
    }
}
