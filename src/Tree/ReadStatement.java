package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class ReadStatement extends Statement {
    public Expression location;
    public ReadStatement(){
        super();
    }
    public ReadStatement(Expression exp){
        location = exp;
    }
    public Expression getLocation(){
        return location;
    }
    public void setLocation(Expression exp){
        location = exp;
    }
}
