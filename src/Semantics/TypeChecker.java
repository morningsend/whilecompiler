package Semantics;

import Model.DataType;
import Tree.*;

import java.util.Stack;

/**
 * Created by Zaiyang on 14/12/2015.
 */
public class TypeChecker extends SemanticsAnalyzer{

    protected Scope defaultScope;
    protected CompilationUnit unit;
    protected Stack<Scope> scopeStack;
    public TypeChecker(Scope scope){
        this.defaultScope = scope;
    }

    public Scope getDefaultScope() {
        return defaultScope;
    }

    public void setDefaultScope(Scope defaultScope) {
        this.defaultScope = defaultScope;
    }

    public DataType inferExpressionType(Expression expression) throws InvalidTypeException {
        if (expression instanceof BooleanLiteral){
            return DataType.Boolean;
        }else
        if(expression instanceof StringLiteral){
            return DataType.String;
        }else if(expression instanceof IntegerLiteral){
            return DataType.Integer;
        }else if(expression instanceof FloatLiteral){
            return DataType.Real;
        }else if(expression instanceof RelationExpression){
            return inferRelationExpression((RelationExpression) expression);
        }else if(expression instanceof NumericalBinaryExpression){
            return inferNumericalBinaryType((NumericalBinaryExpression) expression);
        }else if(expression instanceof NumericalUnaryExpression){
            return inferNumericalUnaryType((NumericalUnaryExpression) expression);
        }else if(expression instanceof BooleanBinaryExpression){
            return inferBooleanBinaryType((BooleanBinaryExpression) expression);
        }else if(expression instanceof AssignmentExpression){
            return inferAssignmentExpressionType((AssignmentExpression) expression);
        }else if (expression instanceof BooleanUnaryExpression){
            return inferBooleanUnaryType((BooleanUnaryExpression) expression);
        }else if(expression instanceof ArrayAccess) {
            return inferArrayType((ArrayAccess) expression);
        }
        else if(expression instanceof FunctionCall){
            return inferFunctionReturnType((FunctionCall) expression);
        }else if(expression instanceof VariableAccess){
            return inferVariableType((Identifier) expression);
        }else if(expression instanceof Identifier){
            return inferVariableType((Identifier) expression);
        }
        return DataType.Unresolved;
    }

    public DataType inferArrayElementType(ArrayAccess access,Scope scope){
        Element array= scope.lookup(access.getIdentierName());
        return DataType.Unresolved;
    }
    public DataType inferArrayElementType(ArrayAccess access){
        return inferArrayElementType(access, defaultScope);
    }
    public DataType inferRelationExpression(RelationExpression expression) throws InvalidTypeException {
        Expression left = expression.getLeftOperand();
        Expression right = expression.getRightOperand();

        DataType leftType = inferExpressionType(left);
        DataType rightType = inferExpressionType(right);

        if(TypeRules.canConvertType(leftType, rightType)){
            return DataType.Boolean;
        }else {
            return DataType.Unresolved;
        }

    }

    public DataType inferAssignmentExpressionType(AssignmentExpression expression) throws InvalidTypeException {
        DataType rhsType = inferExpressionType(expression.getValue());
        DataType lhsType = inferVariableType((Identifier) expression.getVariable());
        boolean canAssign = TypeRules.canAssign(lhsType, rhsType);
        boolean varTypeDefined = !TypeRules.isUndefinedType(lhsType);
        if(canAssign && varTypeDefined){
            return lhsType;
        }else if(canAssign && !varTypeDefined){
            return rhsType;
        }
        else return DataType.Unresolved;
    }

    public DataType inferBooleanUnaryType(BooleanUnaryExpression expression) throws InvalidTypeException {
        Expression exp = expression.getOperand();
        DataType type = inferExpressionType(exp);
        if(type!=DataType.Boolean)
            throw new InvalidTypeException(expression, "type of boolean expression cannot be analyzed");
        return type;
    }


    public DataType inferNumericalBinaryType(NumericalBinaryExpression binexp) throws InvalidTypeException {
        DataType left = inferExpressionType(binexp.getLeftOperand());
        DataType right = inferExpressionType(binexp.getRightOperand());
        return TypeRules.numericalBinopType(left, right);
    }
    public DataType inferNumericalUnaryType(NumericalUnaryExpression binexp) throws InvalidTypeException {
        return inferExpressionType(binexp.getOperand());
    }
    public DataType inferBooleanBinaryType(BooleanBinaryExpression boolexp) throws InvalidTypeException {
        Expression left = boolexp.getLeftOperand();
        Expression right = boolexp.getRightOperand();
        DataType leftType = inferExpressionType(left);
        DataType rightType = inferExpressionType(right);
        String message;
        if(leftType!= rightType){
            message ="expression '"+ left.getParserRuleContext().getText()+
                    "' and '"+right.getParserRuleContext().getText()+"' both need to have boolean type";
            throw new InvalidTypeException(boolexp, message);
        }else if(leftType != DataType.Boolean){
            message = "expression '" + left.getParserRuleContext().getText()
                    +"' and must have type boolean";
            throw new InvalidTypeException(left, message);
        }
        return DataType.Boolean;
    }
    public DataType inferVariableType(Identifier expression) {
        Element e = defaultScope.lookupSymbolInScopeChain(expression.getName());
        if (e == null) {
            return DataType.Unresolved;
        }
        if (expression instanceof FunctionCall)
            return inferFunctionReturnType((FunctionCall) expression);
        else if (expression instanceof ArrayAccess)
            return inferArrayType((ArrayAccess) expression);
        else if (expression instanceof Identifier) {
            return e.getType();
        }

        return DataType.Unresolved;
    }

    public DataType inferArrayType(ArrayAccess arrayAccess) {
        return null;
    }

    public DataType inferFunctionReturnType(FunctionCall expression) {
        String funcName = expression.getFunctionName();
        FunctionElement el = defaultScope.lookupFuncDefInScopeChain(funcName);
        if(el!=null){
            return el.getReturnType();
        }
        return DataType.Unresolved;
    }

    public boolean isExpressionOfType(Expression expression, DataType type) throws InvalidTypeException {
        DataType expType = inferExpressionType(expression);
        if(expType!=type)
            return false;
        else
            return true;
    }


}
