package Code;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class Literal<T> implements Operand {
    public T value;

    public Literal(T value){
        this.value = value;
    }
    public Literal(){
        value = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString(){
        if(value == null) return "";
        return value.toString();
    }
}
