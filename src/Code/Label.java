package Code;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class Label implements Operand{
    public String label;

    public Label(String label){
        setLabel(label);
    }

    public Label(){
        setLabel("");
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString(){
        return label;
    }
}
