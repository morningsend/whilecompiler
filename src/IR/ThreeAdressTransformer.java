package IR;

import Tree.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class ThreeAdressTransformer {
    protected CompilationUnit unit;
    protected List<Statement> quadStmts;
    protected ExpressionLinearizer linearizer;
    protected static Random random = new Random();
    public ThreeAdressTransformer(CompilationUnit unit){
        this.unit = unit;
    }
    public void transform(){
        List<Statement> stmts = unit.getProgramBody().getStatements();
        quadStmts = new ArrayList<Statement>();
        linearizer = new ExpressionLinearizer();
        for(int i = 0; i< stmts.size(); i++){
            Statement statement = stmts.get(i);
            quadStmts.addAll(transformStmt(statement));
        }
        StatementList list = new StatementList(quadStmts);
        unit.setProgramBody(list);
    }
    public CompilationUnit getCompilationUnit(){
        return unit;
    }
    private List<Statement> transformStmt(Statement statement) {
        List<Statement> result = null;
        if(statement instanceof ExpressionStatement){
            result=transformExpStmt((ExpressionStatement) statement);
        }else if(statement instanceof WriteStatement){
            result = transformWrStmt((WriteStatement) statement);
        }else if(statement instanceof WriteLineStatement){
            result =transformWrLnStmt((WriteLineStatement) statement);
        }else if(statement instanceof ReadStatement){
            result = transformRdStmt((ReadStatement) statement);
        }else if(statement instanceof SkipStatement){
            result = transformSkipStmt((SkipStatement) statement);
        }else if(statement instanceof FunctionDefinition){
            result = transformFuncDef((FunctionDefinition) statement);
        }else if(statement instanceof ReturnStatement){
            result = transformRnStmt((ReturnStatement) statement);
        }else if(statement instanceof IfStatement){
            result = transformIfStmt((IfStatement) statement);
        }else if(statement instanceof WhileStatement){
            result = transformWhStmt((WhileStatement) statement);
        }else if(statement instanceof BlockStatement){
            result = transformBlockStmt((BlockStatement) statement);
        }
        if (result == null) result = new ArrayList<Statement>(0);
        return result;
    }

    private List<Statement> transformBlockStmt(BlockStatement statement) {
        List<Statement> result = new ArrayList<Statement>();
        List<Statement> body = statement.getBlockBody();
        for(Statement stmt: body){
            result.addAll(transformStmt(stmt));
        }
        statement.setStatements(new StatementList(result));
        return wrapObjectInList((Statement)statement);
    }

    private List<Statement> transformIfStmt(IfStatement statement) {
        int id = getRandomId();
        String ifLabel = "if"+id;

        List<Statement> cond = new ArrayList<Statement>();
        cond.addAll(wrapLinearizeExpression(statement.getCondition()));

        ExpressionStatement es = (ExpressionStatement) getLastOfList(cond);
        AssignmentExpression ae = (AssignmentExpression) es.getExpression();
        Identifier condVar = (Identifier) ae.getVariable();



        List<Statement> thenStmts = transformStmt(statement.getThenBranch());
        List<Statement> elseStmts = transformStmt(statement.getElseBranch());

        thenStmts.get(0).setLabel(ifLabel+"A");

        elseStmts.get(0).setLabel(ifLabel+"B");

        ConditionGotoStatement gotoThenIfTrue = new ConditionGotoStatement(
                ConditionGotoStatement.JumpCondition.JumpIfNonZero,condVar,thenStmts.get(0)
        );
        ConditionGotoStatement gotoElseIfFalse = new ConditionGotoStatement(
                ConditionGotoStatement.JumpCondition.JumpIfZero,condVar,elseStmts.get(0)
        );

        cond.add(gotoThenIfTrue);
        cond.add(gotoElseIfFalse);

        SkipStatement ifEnd = new SkipStatement();
        ifEnd.setLabel(ifLabel+"ed");
        GotoStatement gotoIfEnd = new GotoStatement(ifEnd);
        thenStmts.add(gotoIfEnd);
        elseStmts.add(ifEnd);
        cond.addAll(thenStmts);
        cond.addAll(elseStmts);
        cond.get(0).setLabel(ifLabel+"cd");
        return cond;
    }

    private List<Statement> transformRnStmt(ReturnStatement statement) {
        if(statement.isEmptyExpression()){
            return wrapObjectInList((Statement)statement);
        }
        List<Statement> list = new ArrayList<Statement>();
        list.addAll(wrapLinearizeExpression(statement.getExpression()));
        AssignmentExpression ae = (AssignmentExpression)(((ExpressionStatement) getLastOfList(list)).getExpression());
        Identifier returnVar = (Identifier) ae.getVariable();
        ReturnStatement rn = new ReturnStatement(returnVar);

        list.add(rn);
        return list;
    }

    private List<Statement> transformFuncDef(FunctionDefinition statement) {
        String funcName = "f" + statement.getName();

        List<Statement> stmts = transformBlockStmt(statement.getBody());
        BlockStatement body = (BlockStatement) stmts.get(0);

        statement.setLabel(funcName);
        body.getStatements().getStatement(0).setLabel(statement.getLabel());

        statement.setBody(body);
        SkipStatement functionEnd = new SkipStatement();
        GotoStatement skipFuncBody = new GotoStatement(functionEnd);
        functionEnd.setLabel(funcName+"se");
        skipFuncBody.setLabel(funcName+"s");
        List<Statement> sts = body.getBlockBody();
        sts.add(0, skipFuncBody);
        sts.add(functionEnd);
        return wrapObjectInList((Statement)statement);
    }

    private List<Statement> transformSkipStmt(SkipStatement statement) {
        return wrapObjectInList((Statement)statement);
    }
    private List<Statement> transformWhStmt(WhileStatement statement) {
        int id = getRandomId();
        String whileLabel = "w"+id;

        List<Statement> cond = new ArrayList<Statement>();
        cond.addAll(wrapLinearizeExpression(statement.getCondition()));

        List<Statement> body = transformStmt(statement.getWhileBody());

        body.get(0).setLabel(whileLabel+"D");

        GotoStatement gotoWhileBeginning = new GotoStatement(cond.get(0));

        SkipStatement whileEnd = new SkipStatement();
        whileEnd.setLabel(whileLabel+"ed");
        body.add(gotoWhileBeginning);
        body.add(whileEnd);
        AssignmentExpression ae = (AssignmentExpression)(((ExpressionStatement) getLastOfList(cond)).getExpression());
        Identifier condVar = (Identifier) ae.getVariable();
        ConditionGotoStatement condGoto =
                new ConditionGotoStatement(ConditionGotoStatement.JumpCondition.JumpIfZero,
                        condVar,whileEnd);
        cond.add(condGoto);
        cond.addAll(body);
        cond.get(0).setLabel(whileLabel+"cd");
        return cond;
    }
    private List<Statement> transformRdStmt(ReadStatement statement) {
        return wrapObjectInList((Statement)statement);
    }
    private List<Statement> transformWrLnStmt(WriteLineStatement statement) {
        return wrapObjectInList((Statement)statement);
    }

    private List<Statement> transformWrStmt(WriteStatement statement) {
        List<Statement> result = new ArrayList<Statement>();
        if(statement.getExpression() instanceof StringLiteral || statement.getExpression() instanceof BooleanLiteral){
            result.add(statement);
            return result;
        }
        List<ExpressionStatement> expList = wrapLinearizeExpression(statement.getExpression());
        result.addAll(expList);
        Expression out = getLastOfList(expList).getExpression();
        if (out instanceof AssignmentExpression)
            out = ((AssignmentExpression) out).getVariable();
        WriteStatement ws = new WriteStatement(out);
        result.add(ws);
        return result;
    }

    private List<Statement> transformExpStmt(ExpressionStatement statement) {
        List<Statement>result = new ArrayList<Statement>();
        result.addAll(wrapLinearizeExpression(statement.getExpression()));
        return result;
    }

    private ExpressionStatement wrapExpression(Expression expression){
        return new ExpressionStatement(expression);
    }
    private List<ExpressionStatement> wrapLinearizeExpression(Expression exp){
        List<ExpressionStatement> ret = new ArrayList<ExpressionStatement>();
        List<AssignmentExpression> subexps = linearizer.makeLinear(exp);

        for (AssignmentExpression subexp : subexps) {
            ret.add(wrapExpression(subexp));
        }
        return ret;
    }
    public static <T> T getLastOfList(List<T> list){
        if(list.size()<1) return null;
        return list.get(list.size()-1);
    }
    public static <T> List<T> wrapObjectInList(T o){
        List<T> b = new ArrayList<T>(1);
        b.add(o);
        return b;
    }
    public static int getRandomId(){
        return ((random.nextInt(10000))*37 + random.nextInt(10000))%10000;
    }
}
