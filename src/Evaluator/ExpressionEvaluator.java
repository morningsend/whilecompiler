package Evaluator;

import Semantics.Scope;
import Tree.BooleanExpression;
import Tree.Expression;

/**
 * Created by Zaiyang on 07/12/2015.
 */
public class ExpressionEvaluator<T> {
    public boolean evaluateBooleanExpression(BooleanExpression boolExp,  Scope scope){
        return false;
    }
    public float evaluateFloatExpression(Expression exp, Scope scope){
        return 0.0f;
    }
    public <T> T evalueExpression(Expression exp, Scope scope){
        T a=null;
        return a;
    }
}
