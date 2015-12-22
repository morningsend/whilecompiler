package Tree;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class GotoStatement extends Statement{
    public Statement destination;

    public GotoStatement(){

    }
    public GotoStatement(Statement dest){
        this.destination = dest;
    }

    public Statement getDestination() {
        return destination;
    }

    public void setDestination(Statement destination) {
        this.destination = destination;
    }
}
