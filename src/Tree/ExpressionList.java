package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zaiyang on 09/12/2015.
 */
public class ExpressionList extends StatementList{
    public List<Expression> expressions;
    public ExpressionList(){
        expressions = new ArrayList<Expression>();
    }
    public ExpressionList(List<Expression> expList){
        expressions = new ArrayList<Expression>(expList);
    }
    public void setExpressions(List<Expression> expressionList){
        this.expressions = new ArrayList<Expression>(expressionList);
    }
    public List<Expression> getExpressions(){
        return expressions;
    }
}
