package IR;

import Model.Kind;
import Tree.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Zaiyang on 17/12/2015.
 */
public class ExpressionLinearizer {
    private List<AssignmentExpression> results;
    private static int nextTempId = 0;

    public static Identifier createTemporaryVariable(){
        String name = "@Temp" + nextTempId;
        nextTempId++;
        Identifier id = new Identifier(name);
        id.setKind(Kind.Variable);
        return id;
    }
    public ExpressionLinearizer(){
    }
    public List<AssignmentExpression> makeLinear(Expression root){
        List<AssignmentExpression> result= new ArrayList<AssignmentExpression>();
        if(root instanceof NumericalBinaryExpression){
            result.addAll(linearNumBinOp((NumericalBinaryExpression) root));
        }else if(root instanceof NumericalUnaryExpression){
            result.addAll(linearBinUnOp((NumericalUnaryExpression) root));
        }else if (root instanceof RelationExpression){
            result.addAll(linearRelExp((RelationExpression)root));
        }else if(root instanceof BooleanBinaryExpression){
            result.addAll(linearBoolBinExp((BooleanBinaryExpression)root));
        }else if(root instanceof BooleanUnaryExpression){
            result.addAll(linearBoolUnExp((BooleanUnaryExpression)root));
        }else if(root instanceof AssignmentExpression){
            result.addAll(linearAssignExp((AssignmentExpression) root));
        }else if(root instanceof FunctionCall){
            result.addAll(linFunCall((FunctionCall) root));
        }else if(root instanceof ArrayAccess){
            result.addAll(linArrayAcc((ArrayAccess) root));
        }else if(root instanceof BooleanLiteral
                || root instanceof IntegerLiteral
                || root instanceof FloatLiteral
                || root instanceof StringLiteral
                || root instanceof Identifier)
        {
            result.addAll(wrapConstantInTempVariable(root));
        }
        return result;
    }
    private List<AssignmentExpression> wrapConstantInTempVariable(Expression exp){
        List<AssignmentExpression> list = new ArrayList<AssignmentExpression>();
        AssignmentExpression ae = new AssignmentExpression(createTemporaryVariable(), exp);
        list.add(ae);
        return list;
    }
    private Collection<? extends AssignmentExpression> linArrayAcc(ArrayAccess root) {
        List<AssignmentExpression> assList = new ArrayList<AssignmentExpression>();
        Expression indexExp = root.getIndexExpression();
        if(!isExpressionAtomic(indexExp)){
            assList.addAll(makeLinear(indexExp));
            indexExp = assList.get(assList.size()-1).getVariable();
        }
        ArrayAccess acc = new ArrayAccess(root.getName(),indexExp);
        AssignmentExpression ae = new AssignmentExpression(acc, acc);
        assList.add(ae);
        return assList;
    }

    private Collection<? extends AssignmentExpression> linearBoolUnExp(BooleanUnaryExpression root) {
        List<AssignmentExpression> unary = new ArrayList<AssignmentExpression>();
        Expression child = root.getOperand();
        if(!isExpressionAtomic(child)){
            unary.addAll(makeLinear(child));
            child = unary.get(unary.size()-1).getVariable();
        }
        BooleanUnaryExpression ret = new BooleanUnaryExpression(root.getOperator(),child);
        AssignmentExpression assign = new AssignmentExpression(createTemporaryVariable(), ret);
        unary.add(assign);
        return unary;
    }

    private Collection<? extends AssignmentExpression> linearBoolBinExp(BooleanBinaryExpression root) {
        List<AssignmentExpression> boolexplist = new ArrayList<AssignmentExpression>();
        List<AssignmentExpression> lhs = new ArrayList<AssignmentExpression> ();
        List<AssignmentExpression> rhs = new ArrayList<AssignmentExpression> ();
        Expression left = root.getLeftOperand();
        Expression right = root.getRightOperand();
        if(!isExpressionAtomic(left)){
            lhs.addAll(makeLinear(left));
            left = lhs.get(lhs.size()-1).getVariable();
        }
        if(!isExpressionAtomic(right)){
            rhs.addAll(makeLinear(right));
            right = rhs.get(rhs.size()-1).getVariable();
        }
        BooleanBinaryExpression boolExp = new BooleanBinaryExpression(root.getOperator(), left, right);
        AssignmentExpression ret = new AssignmentExpression(createTemporaryVariable(), boolExp);
        lhs.addAll(rhs);
        lhs.add(ret);
        return lhs;
    }

    private Collection<? extends AssignmentExpression> linFunCall(FunctionCall root) {
        List<AssignmentExpression> call = new ArrayList<AssignmentExpression>();
        List<Expression> paramsExp = root.getArgs().getParameters();
        List<Expression> paramsLin = new ArrayList<Expression>();
        for(int i = 0; i<paramsExp.size(); i++){
            Expression param = paramsExp.get(i);
            if(isExpressionAtomic(param)){
                paramsLin.add(param);
            }else {
                call.addAll(makeLinear(param));
                Expression paramRet = call.get(call.size()-1).getVariable();
                paramsLin.add(paramRet);
            }
        }
        ParameterList linList = new ParameterList(paramsLin);
        FunctionCall linCall = new FunctionCall(root.getFunctionName(), linList);
        AssignmentExpression ret = new AssignmentExpression(createTemporaryVariable(), linCall);
        call.add(ret);
        return call;
    }

    private Collection<? extends AssignmentExpression> linearAssignExp(AssignmentExpression root) {
        List<AssignmentExpression> unary = new ArrayList<AssignmentExpression>();
        List<AssignmentExpression> varList = new ArrayList<AssignmentExpression>();
        Expression child = root.getValue();
        Expression var = root.getVariable();
        if(!isExpressionAtomic(child)){
            unary.addAll(makeLinear(child));
            child = unary.get(unary.size()-1).getValue();
        }
        if(var instanceof ArrayAccess){
            varList.addAll(linArrayAcc((ArrayAccess) var));
            var = varList.get(varList.size()-1).getVariable();
            unary.addAll(varList);
        }
        AssignmentExpression ae = new AssignmentExpression(var, child);
        unary.add(ae);
        return unary;
    }

    private Collection<? extends AssignmentExpression> linearRelExp(RelationExpression root) {
        List<AssignmentExpression> lhs=new ArrayList<AssignmentExpression>();
        List<AssignmentExpression> rhs = new ArrayList<AssignmentExpression>();
        Expression left = root.getLeftOperand();
        Expression right = root.getRightOperand();
        if(!isExpressionAtomic(left)){
            lhs.addAll(makeLinear(left));
            left = lhs.get(lhs.size()-1).getVariable();
        }
        if(!isExpressionAtomic(right)){
            rhs.addAll(makeLinear(right));
            right = rhs.get(rhs.size()-1).getVariable();
        }
        RelationExpression relExp= new RelationExpression(root.getOperator(), left, right);
        AssignmentExpression ret = new AssignmentExpression(createTemporaryVariable(), relExp);
        lhs.addAll(rhs);
        lhs.add(ret);
        return lhs;
    }

    private Collection<? extends AssignmentExpression> linearBinUnOp(NumericalUnaryExpression root) {
        List<AssignmentExpression> unary = new ArrayList<AssignmentExpression>();
        Expression child = root.getOperand();
        if(!isExpressionAtomic(child)){
            unary.addAll(makeLinear(child));
            child = unary.get(unary.size()-1).getVariable();
        }
        NumericalUnaryExpression ret = new NumericalUnaryExpression(root.getOperator(), (NumericalExpression) child);
        AssignmentExpression assign = new AssignmentExpression(createTemporaryVariable(), ret);
        unary.add(assign);
        return unary;
    }

    private Collection<? extends AssignmentExpression> linearNumBinOp(NumericalBinaryExpression root) {
        List<AssignmentExpression> lhs=new ArrayList<AssignmentExpression>();
        List<AssignmentExpression> rhs = new ArrayList<AssignmentExpression>();
        Expression left = root.getLeftOperand();
        Expression right = root.getRightOperand();
        if(!isExpressionAtomic(left)){
            lhs.addAll(makeLinear(left));
            left = lhs.get(lhs.size()-1).getVariable();
        }
        if(!isExpressionAtomic(right)){
            rhs.addAll(makeLinear(right));
            right = rhs.get(rhs.size()-1).getVariable();
        }
        NumericalBinaryExpression newBinExp = new NumericalBinaryExpression(root.getOperator(), left, right);
        AssignmentExpression ret = new AssignmentExpression(createTemporaryVariable(), newBinExp);
        lhs.addAll(rhs);
        lhs.add(ret);
        return lhs;
    }
    public List<AssignmentExpression> getResults(){
        return results;
    }
    public boolean isExpressionAtomic(Expression exp){
        Class clazz = exp.getClass();
        boolean atom =
                clazz == Identifier.class ||
                 clazz == IntegerLiteral.class ||
                clazz == StringLiteral.class||
                clazz == FloatLiteral.class ||
                clazz == BooleanLiteral.class|| clazz==ArrayDefinition.class
                ;
        return atom;
    }
}
