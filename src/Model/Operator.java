package Model;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public enum Operator {
    Assign(":="),
    Equals("="),
    NotEquals("!="),
    GreaterThan(">"),
    LessThan("<"),
    GreatEqualThan(">="),
    LessEqualThan("<="),
    BooleanOr("|"),
    BooleanNot("!"),
    BooleanAnd("&"),
    Add("+"),
    Sub("-"),
    Mul("*"),
    Div("/");

    private final String text;
    private Operator(String t){
        this.text = t;
    }
    public String getText(){
        return text;
    }
    public static Operator parseOperator(String text){
        Operator[] ops = Operator.values();
        for(Operator op :ops){
            if(op.getText().equals(text)){
                return op;
            }
        }
        return null;
    }
}
