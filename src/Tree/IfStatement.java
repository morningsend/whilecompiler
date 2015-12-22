package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class IfStatement extends FlowControlStatement {
    private Statement thenBranch;

    public Statement getElseBranch() {
                return elseBranch;
    }

    public void setElseBranch(Statement elseBranch) {
        this.elseBranch = elseBranch;
    }

    public Statement getThenBranch() {
        return thenBranch;
    }

    public void setThenBranch(Statement thenBranch) {
        this.thenBranch = thenBranch;
    }

    private Statement elseBranch;

    public IfStatement(){
        super();
    }
    public IfStatement(Expression cond, Statement thenBranch, Statement elseBranch){
        super(cond);
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

}
