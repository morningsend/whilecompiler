package Code;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class Register implements Operand{
    public final int ID;
    private static int nextId = 0;
    public Register(int id){
        this.ID = id;
    }
    public String getRegisterName(){
        return "R"+ID;
    }

    @Override
    public String toString(){
        return getRegisterName();
    }
    public static Register create(){
        return new Register(nextId++);
    }
}
