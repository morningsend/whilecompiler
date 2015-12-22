package Semantics;

import Model.DataType;
import Model.Kind;
import Tree.*;

import java.util.List;
import java.util.Stack;

/**
 * Created by Zaiyang on 20/12/2015.
 */
public class ThreeAddressTypeAnalyzer extends SemanticsAnalyzer{
    public CompilationUnit unit;
    public Stack<Scope> scopeStack = new Stack<>();


    public ThreeAddressTypeAnalyzer(CompilationUnit unit) {
        this.unit = unit;
    }

    public void analyzeType() {
        List<Statement> list = unit.getProgramBody().getStatements();
        pushScope(unit.getGlobalScope());
        for(Statement stmt: list){
            analyze(stmt);
        }
    }

    private void analyze(Statement statement) {

        if(statement instanceof ExpressionStatement){
            analyzeExpStmt((ExpressionStatement) statement);
        }else if(statement instanceof WriteStatement){
            analyzeWrStmt((WriteStatement) statement);
        }else if(statement instanceof WriteLineStatement){
            analyzeWrLnStmt((WriteLineStatement) statement);
        }else if(statement instanceof ReadStatement){
            analyzeRdStmt((ReadStatement) statement);
        }else if(statement instanceof SkipStatement){
            analyzeSkipStmt((SkipStatement) statement);
        }else if(statement instanceof ReturnStatement){
            analyzeRnStmt((ReturnStatement) statement);
        }else if(statement instanceof BlockStatement) {
            analyzeBlockStmt((BlockStatement) statement);
        }else if(statement instanceof ConditionGotoStatement){
            analyzeCondGotoStmt((ConditionGotoStatement) statement);
        }else if(statement instanceof GotoStatement){
            analyzeGotoStmt((GotoStatement) statement);
        }
    }

    private void analyzeGotoStmt(GotoStatement statement) {

    }

    private void analyzeCondGotoStmt(ConditionGotoStatement statement) {

    }

    private void analyzeBlockStmt(BlockStatement statement) {
        pushScope(statement.getInnerScope());
        analyzeStmtList(statement.getStatements());
        popScope();
    }

    private void analyzeRnStmt(ReturnStatement statement) {
        return;
    }

    private void analyzeSkipStmt(SkipStatement statement) {
        return;
    }

    private void analyzeRdStmt(ReadStatement statement) {
        statement.getLocation().setDataType(DataType.Integer);
    }

    private void analyzeWrLnStmt(WriteLineStatement statement) {
        return;
    }

    private void analyzeExpStmt(ExpressionStatement statement) {
        analyzeExpression(statement.getExpression());
    }

    private void analyzeWrStmt(WriteStatement statement) {
        analyzeExpression( statement.getExpression() );
    }

    private void analyzeStmtList(StatementList list){
        for(Statement s:list.getStatements()){
            analyze(s);
        }
    }
    private void analyzeExpression(Expression e){
        if(e instanceof AssignmentExpression){

            DataType type = inferExpressionType(((AssignmentExpression) e).getValue());
            e.setDataType(type);
            ((AssignmentExpression) e).getVariable().setDataType(type);
            transferType((Identifier) ((AssignmentExpression) e).getVariable(), ((AssignmentExpression) e).getValue());
            insertTypeInfoToScope((Identifier) ((AssignmentExpression) e).getVariable());
        }
    }

    private void insertTypeInfoToScope(Identifier variable) {
        Element e = new Element();
        e.setKind(SymbolKind.Name);
        e.setType(variable.getDataType());
        e.setName(variable.getName());
        e.setNode(variable);
        scopeStack.peek().insertSymbol(e);
    }

    private void transferType(Identifier variable, Expression value) {
        DataType valType = inferExpressionType(value);
        variable.setDataType(valType);
    }

    private DataType inferExpressionType(Expression e) {
        if(e instanceof Identifier){
            inferIdenType((Identifier) e);
        }else if(e instanceof RelationExpression){
            return DataType.Boolean;
        }else if(e instanceof BooleanExpression){
            return DataType.Boolean;
        }else if(e instanceof StringLiteral){
            return DataType.String;
        }else if(e instanceof IntegerLiteral){
            e.setKind(Kind.Constant);
            return DataType.Integer;
        }else if(e instanceof NumericalExpression){
            return inferNumExpType((NumericalExpression) e);
        }
        return e.getDataType();
    }

    private DataType inferIdenType(Identifier e) {
        if(e instanceof ArrayAccess){
            return inferArrayElementType((ArrayAccess) e);
        }else if(e instanceof FunctionCall){
            return inferFuncReturnType((FunctionCall) e);
        }else if(e != null){
            Element el = currentScope().lookupSymbolInScopeChain(e.getName());
            Element.copyElementAttributeToIdentifier(e, el);
            return el.getType();
        }
        return e.getDataType();
    }

    private DataType inferArrayElementType(ArrayAccess e) {
        ArrayElement el = (ArrayElement) currentScope().lookupSymbolInScopeChain(e.getIdentierName());
        if(el!=null){
            return el.getDefinition().getElementType();
        }else {
            throw new InvalidTypeException(e, "error checking array element type");
        }
    }

    private DataType inferFuncReturnType(FunctionCall e) {
        FunctionElement el = currentScope().lookupFuncDefInScopeChain(e.getName());
        if (el != null){
            return el.getReturnType();
        }else {
            throw new InvalidTypeException(e, "function " + e.getName()+" is not found");
        }
    }

    private DataType inferNumExpType(NumericalExpression e) {
        if(e instanceof NumericalBinaryExpression){

            Expression e1 =  ((NumericalBinaryExpression) e).getLeftOperand();
            Expression e2= ((NumericalBinaryExpression) e).getRightOperand();
            if(e1 instanceof Identifier)
                propagateType((Identifier) e1);
            if(e2 instanceof Identifier)
                propagateType((Identifier) e2);
            DataType left = e1.getDataType();
            DataType right = e2.getDataType();


            return TypeRules.numericalBinopType(left, right);

        }else if(e instanceof NumericalUnaryExpression){
            return ((NumericalUnaryExpression) e).getOperand().getDataType();
        }
        return e.getDataType();
    }

    private void propagateType(Identifier id) {
        Element e = scopeStack.peek().lookupSymbolInScopeChain(id.getName());
        if(e!=null)
            id.setDataType(e.getType());
    }

    private Scope currentScope(){
        return scopeStack.peek();
    }
    private void pushScope(Scope scope){
        scopeStack.push(scope);
    }
    private Scope popScope(){
        return scopeStack.pop();
    }
}
