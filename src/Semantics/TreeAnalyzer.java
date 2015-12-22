package Semantics;

import Model.Operator;
import Tree.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class TreeAnalyzer extends SemanticsAnalyzer{

    public TreeAnalyzer(CompilationUnit unit){
        super(unit);
    }

    public void analyzerCorrectness(){
        List<Statement> stmts = unit.getProgramBody().getStatements();
        for(Statement stmt : stmts){
            validate(stmt);
        }
    }

    private void validate(Statement statement) {
        List<Statement> result = null;
        if(statement instanceof ExpressionStatement){
            validateExpStmt((ExpressionStatement)statement);
        }else if(statement instanceof WriteStatement){
            validateWrStmt((WriteStatement) statement);
        }else if(statement instanceof WriteLineStatement){
            validateWrLnStmt((WriteLineStatement) statement);
        }else if(statement instanceof ReadStatement){
            validateRdStmt((ReadStatement) statement);
        }else if(statement instanceof SkipStatement){
            validateSkipStmt((SkipStatement) statement);
        }else if(statement instanceof FunctionDefinition){
            validateFuncDef((FunctionDefinition) statement);
        }else if(statement instanceof ReturnStatement){
            validateRnStmt((ReturnStatement) statement);
        }else if(statement instanceof IfStatement){
            validateIfStmt((IfStatement) statement);
        }else if(statement instanceof WhileStatement){
            validateWhStmt((WhileStatement) statement);
        }else if(statement instanceof BlockStatement) {
            validateBlockStmt((BlockStatement) statement);
        }else if(statement instanceof ConditionGotoStatement){
            validateCondGotoStmt((ConditionGotoStatement)statement);
        }else if(statement instanceof GotoStatement){
            validateGotoStmt((GotoStatement) statement);
        }
    }
    private void validateStmtList(StatementList list){
        for(Statement s:list.getStatements()){
            validate(s);
        }
    }
    private void validateExpression(Expression e){
        if(e instanceof FunctionCall){
            validateFuncCall((FunctionCall) e);
        }else if(e instanceof ArrayAccess){
            validateArrayAccess((ArrayAccess)e);
        }else if(e instanceof Identifier){
            validateIden((Identifier)e);
        }else if(e instanceof BooleanUnaryExpression){
            validateBoolUnExp((BooleanUnaryExpression)e);
        }else if(e instanceof BooleanBinaryExpression){
            validateBoolBinExp((BooleanBinaryExpression) e);
        }else if(e instanceof NumericalBinaryExpression){
            validateNumBinExp((NumericalBinaryExpression)e);
        }else if(e instanceof NumericalUnaryExpression){
            validateNumUnExp((NumericalUnaryExpression)e);
        }else if(e instanceof RelationExpression){
            validateRelExp((RelationExpression)e);
        }else if(e instanceof AssignmentExpression){
            validateAssExp((AssignmentExpression) e);
        }else if(e instanceof StringLiteral){

        }else if(e instanceof IntegerLiteral){

        }else if(e instanceof BooleanLiteral){

        }
    }

    private void validateIden(Identifier e) {
        String s = e.getName();
        String message ="identifier with name '"+ e.getName()+"' has length too long(>8)";
        assertCondition(s.length() <=8, new SemanticsException (e, message));
    }

    private void validateAssExp(AssignmentExpression e) {
        validateExpression(e.getValue());
        validateIden((Identifier) e.getVariable());
        Expression var = e.getVariable();
        String message = "expression "
                + var.getParserRuleContext().getText()
                +" cannot be assigned to.";
        Class<?> clazz = var.getClass();
        assertCondition(clazz.equals(Identifier.class) || clazz.equals(ArrayAccess.class),
                new SemanticsException(e, message));
    }

    private void validateVariable(Expression e) {
        return;
    }

    private void validateCondGotoStmt(ConditionGotoStatement statement) {

    }

    private void validateGotoStmt(GotoStatement statement) {

    }

    private void validateBlockStmt(BlockStatement statement) {
        List<Statement> list = statement.getBlockBody();
        for(Statement s:list){
            validate(s);
        }
    }

    private void validateWhStmt(WhileStatement statement) {

        Expression cond = statement.getCondition();
        String message = "while loop condition must be a boolean or logical expression";
        assertCondition(cond instanceof BooleanExpression, new SemanticsException(statement, message));

        if(cond instanceof BooleanExpression) validateBoolExp((BooleanExpression) statement.getCondition());

        validate(statement.getWhileBody());
    }

    private void validateIfStmt(IfStatement statement) {
        Expression cond = statement.getCondition();
        String message = "if condition must be a boolean or logical expression";
        assertCondition(cond instanceof BooleanExpression, new SemanticsException(statement, message));

        if(cond instanceof BooleanExpression) validateBoolExp((BooleanExpression) statement.getCondition());
        validate(statement.getThenBranch());
        validate(statement.getElseBranch());
    }
    private void validateRnStmt(ReturnStatement statement) {
        if(!statement.isEmptyExpression()){
            validateExpression(statement.getExpression());
        }
    }

    private void validateFuncDef(FunctionDefinition statement) {
        List<Expression> params = statement.getParameters().getParameters();
        for(Expression e: params){
            String message = e.getParserRuleContext().getText()
                    + " need to have the format [variable]:=[default value]";
            assertCondition(e instanceof AssignmentExpression , new SemanticsException(statement, message));
            if(e instanceof AssignmentExpression){
                AssignmentExpression ae = (AssignmentExpression) e;
                Expression defaultValue = ae.getValue();
                Expression param = ae.getVariable();
                String message1 = "expression '"+param.getParserRuleContext().getText()+"' is not a valid variable";
                String message2 = "default value of parameter '"+defaultValue.getParserRuleContext().getText()+"' must be a constant";
                assertCondition(param.getClass().equals(Identifier.class), new SemanticsException(e, message1));
                assertCondition(TypeRules.isExpressionLiteral(defaultValue), new SemanticsException(defaultValue, message2));
            }
        }
        validateBlockStmt(statement.getBody());
    }

    private void validateSkipStmt(SkipStatement statement) {
        //do nothing
    }

    private void validateRdStmt(ReadStatement statement) {
        Class<?> clazz = statement.getLocation().getClass();
        String message = statement.getLocation().getParserRuleContext().getText()
                + " cannot be used as storage.";
        assertCondition(clazz.equals(Identifier.class) || clazz.equals(ArrayAccess.class),
                new SemanticsException(statement, message));
    }

    private void validateBoolExp(BooleanExpression e){
        if(e instanceof BooleanBinaryExpression){
            validateBoolBinExp((BooleanBinaryExpression) e);
        }else if(e instanceof BooleanUnaryExpression){
            validateBoolUnExp((BooleanUnaryExpression) e);
        }else if(e instanceof RelationExpression){
            validateRelExp((RelationExpression) e);
        }else
            error(new SemanticsException(e, "unrecognized boolean expression type: "+ e.getClass().getName()));
    }

    private void validateWrLnStmt(WriteLineStatement statement) {
        //do nothing
    }

    private void validateWrStmt(WriteStatement statement) {
        validateExpression(statement.getExpression());
    }

    private void validateExpStmt(ExpressionStatement statement) {
        validateExpression(statement.getExpression());
    }


    private void validateRelExp(RelationExpression e) {
        List<Operator> relops = Arrays.asList(Operator.Equals,
                Operator.NotEquals,
                Operator.LessEqualThan,
                Operator.GreatEqualThan,
                Operator.LessThan,
                Operator.GreaterThan);
        Operator op=e.getOperator();
        String message = String.format("operator %s is not a valid logical operator", op.getText());
        assertCondition(relops.contains(op), new SemanticsException(e, message));
        Expression left = e.getLeftOperand();
        Expression right = e.getRightOperand();
        if(left instanceof Identifier)
            validateIden((Identifier) left);
        if(right instanceof Identifier)
            validateIden((Identifier) right);
        validateExpression(left);
        validateExpression(right);
    }
    private void validateNumBinExp(NumericalBinaryExpression e){
        List<Operator> binops = Arrays.asList(Operator.Add,Operator.Sub,Operator.Mul, Operator.Div);

        Operator op = e.getOperator();
        Expression left = e.getLeftOperand();
        Expression right = e.getRightOperand();

        if(left instanceof Identifier)
            validateIden((Identifier) left);
        if(right instanceof Identifier)
            validateIden((Identifier) right);
        String message = "operator " +op.getText()+" cannot be used in numerical expressions";
        assertCondition(binops.contains(op), new SemanticsException(e, message));

        message = String.format("expression '%s' is not a valid numerical expression",
                left.getParserRuleContext().getText());
        assertCondition(left instanceof NumericalExpression || left instanceof Identifier, new SemanticsException(left,message));

        message = String.format("expression '%s' is not a valid numerical expression",
                right.getParserRuleContext().getText());

        assertCondition(right instanceof NumericalExpression || right instanceof Identifier, new SemanticsException(right,message));
        validateExpression(left);
        validateExpression(right);
    }
    private void validateNumUnExp(NumericalUnaryExpression e){
        Operator op = e.getOperator();
        Expression opr = e.getOperand();
        String message = "operator "+op.getText()
                + " cannot be applied to expression '"
                + opr.getParserRuleContext().getText()
                +"'";
        assertCondition(op == Operator.Add || op == Operator.Sub, new SemanticsException(e, message));

        message = opr.getParserRuleContext().getText()
                + " is not a numerical expression";
        assertCondition(opr instanceof NumericalExpression, new SemanticsException(e, message));
        validateExpression(opr);
        if(opr instanceof Identifier){
            validateIden((Identifier) opr);
        }
    }
    private void validateBoolBinExp(BooleanBinaryExpression e){
        Operator op = e.getOperator();

        String message = "operator "+op.getText() +" is not a valid boolean operator";
        assertCondition(op == Operator.BooleanOr || op == Operator.BooleanAnd,
                new SemanticsException(e, message));

        Expression left = e.getLeftOperand();
        Expression right= e.getRightOperand();
        if(left instanceof Identifier)
            validateIden((Identifier) left);
        if(right instanceof Identifier)
            validateIden((Identifier) right);
        message = left.getParserRuleContext().getText() + " is not a valid boolean or logical expression";
        assertCondition(left instanceof BooleanExpression || left instanceof Identifier, new SemanticsException(e, message));
        message = left.getParserRuleContext().getText() + " is not a valid boolean or logical expression";
        assertCondition(right instanceof BooleanExpression || left instanceof Identifier, new SemanticsException(e, message));

        validateExpression(left);
        validateExpression(right);
    }
    private void validateBoolUnExp(BooleanUnaryExpression e){
        Expression exp = e.getOperand();

        String message = "operator " + e.getOperator().getText()+" cannot be used here with expression: " + exp.getParserRuleContext().getText();
        assertCondition(e.getOperator()==Operator.BooleanNot, new SemanticsException(e, message));

        Expression opr = e.getOperand();
        message = "expression '"+ opr.getParserRuleContext().getText() +"' is not a boolean or logical expression";
        assertCondition((e.getOperand() instanceof BooleanExpression || e.getOperand() instanceof Identifier),
                new SemanticsException(e, message));
        validateExpression(exp);
        if(exp instanceof Identifier){
            validateIden((Identifier) exp);
        }
    }
    private void validateArrayAccess(ArrayAccess e){
        Expression indexExp = e.getIndexExpression();
        String message = "array index must be a numerical expression";
        assertCondition(indexExp instanceof NumericalExpression || indexExp instanceof Identifier, new SemanticsException(e,message));
        validateExpression(indexExp);

    }
    private void validateFuncCall(FunctionCall e){

    }

}
