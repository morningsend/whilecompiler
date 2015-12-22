package Tree;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class Statement extends SyntaxTreeNode{
    public String label;
    public SyntaxTreeNode getNode(){
        return null;
    }
    public void setLabel(String label){
        this.label = label;
    }
    public String getLabel(){
        return label;
    }
}
