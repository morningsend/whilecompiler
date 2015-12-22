package Tree;

import Model.Operator;
import Parser.WhileBaseListener;
import Parser.WhileParser;
import Parser.WhileParser.ParameterListContext;
import Semantics.Scope;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.*;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class SyntaxTreeBuilder extends WhileBaseListener {
    private BottomUpTreeWalker treeWalker;
    private ParseTree parseTree;
    private Stack<SyntaxTreeNode> nodeStack;
    private Stack<Scope> scopeStack;
    private SyntaxTreeNode root;

    public SyntaxTreeBuilder(){

    }
    public SyntaxTreeNode getRoot(){
        return root;
    }
    public void build(ParseTree tree){
        nodeStack = new Stack<SyntaxTreeNode>();
        try {
            treeWalker = new BottomUpTreeWalker();
            treeWalker.walk(this, tree);
            root = nodeStack.get(0);
            //insertProgramEnd((CompilationUnit) root);
            //insertProgramStart((CompilationUnit) root);
        }catch(EmptyStackException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void insertProgramEnd(CompilationUnit root) {
        StatementList list = root.getProgramBody();
        list.getStatements().add(new ProgramEndStatement());
    }
    private void insertProgramStart(CompilationUnit root){
        StatementList list = root.getProgramBody();
        list.getStatements().add(0,new ProgramStartStatement());
    }

    //=================================================================
    @Override
    public void enterFactor(WhileParser.FactorContext context){
        SyntaxTreeNode node=null;

        //constants or variables
        if(context.Integer()!=null){
            node = makeIntegerLiteral(context.Integer());

        }else if(context.Float()!=null){
            node = makeFloatLiteral(context.Float());
        }else if(context.booleanConstant()!=null){
            node = makeBooleanConstant(context.booleanConstant());
        }else if(context.functionCall()!=null){
            WhileParser.FunctionCallContext ctx = context.functionCall();
            ParameterList params = (ParameterList) nodeStack.pop();
            Identifier iden = makeIdentifier(ctx.Identifier());
            node = makeFunctionCall(iden, params);
        }else if(context.arrayAccess()!=null){
            WhileParser.ArrayAccessContext ctx = context.arrayAccess();
            Expression exp = (Expression) nodeStack.pop();
            Identifier iden = makeIdentifier(ctx.Identifier());
            node = makeArrayAccess(iden, exp);
        }else if(context.Identifier()!=null){
            node = makeIdentifier(context.Identifier());
        }else if (context.String()!=null){
            node = makeStringLiteral(context.String());
        }else if (context.arrayDefinition()!=null){
            IntegerLiteral intLiteral = (IntegerLiteral) nodeStack.pop();
            node = makeArrayDefinition(intLiteral);
        }
        if(node!=null){
            node.setParserRuleContext(context);
            nodeStack.push(node);
        }
    }
    @Override
    public void enterArrayDefinition(WhileParser.ArrayDefinitionContext ctx){
        SyntaxTreeNode node=null;
        if(ctx.Integer()!=null){
             node = makeIntegerLiteral(ctx.Integer());
        }
        if(node!=null){
            node.setParserRuleContext(ctx);
            nodeStack.push(node);
        }
    }
    private ArrayDefinition makeArrayDefinition(IntegerLiteral intLiteral) {
        return new ArrayDefinition(intLiteral);
    }

    private SyntaxTreeNode makeStringLiteral(TerminalNode string) {
        String s = string.getText().substring(1, string.getText().length()-1);
        return new StringLiteral(s);
    }

    private FunctionCall makeFunctionCall(Identifier iden, ParameterList args){
        FunctionCall fcall = new FunctionCall(iden.getName(), args);
        return fcall;
    }
    private ArrayAccess makeArrayAccess(Identifier iden, Expression exp) {
        ArrayAccess acc = new ArrayAccess(iden.getName(), exp);
        return acc;
    }

    private BooleanLiteral makeBooleanConstant(WhileParser.BooleanConstantContext ctx) {
        String text="true";
        if(ctx.True()!=null)
            text = ctx.True().getText();
        else if(ctx.False()!=null)
            text = ctx.False().getText();
        BooleanLiteral bool = new BooleanLiteral(text);
        return bool;
    }

    private Identifier makeIdentifier(TerminalNode identifier) {
        Identifier iden = new Identifier(identifier.getText());
        return iden;
    }

    private FloatLiteral makeFloatLiteral(TerminalNode aFloat) {
        FloatLiteral f = new FloatLiteral(aFloat.getText());
        return f;
    }

    private IntegerLiteral makeIntegerLiteral(TerminalNode integer) {
        IntegerLiteral i = new IntegerLiteral(integer.getText());
        return i;
    }

    //======================================================================

    @Override
    public void enterOperand(WhileParser.OperandContext context){

    }
    @Override
    public void enterBooleanAndExpression(WhileParser.BooleanAndExpressionContext context){
        SyntaxTreeNode node;
        if(context.BooleanAnd()!=null && context.expression().size()==2){
            Expression left = (Expression) nodeStack.pop();
            Expression right = (Expression) nodeStack.pop();
            Operator op = Operator.BooleanAnd;
            node = makeBooleanBinaryExpression(op, left, right);
            nodeStack.push(node);
            node.setParserRuleContext(context);
        }else {
            //raise error
        }
    }
    @Override
    public void enterBooleanOrExpression(WhileParser.BooleanOrExpressionContext ctx){
        SyntaxTreeNode node;
        if(ctx.BooleanOr() != null && ctx.expression().size() == 2){
            Expression left = (Expression) nodeStack.pop();
            Expression right = (Expression) nodeStack.pop();
            Operator op = Operator.BooleanOr;
            node = makeBooleanBinaryExpression(op, left, right);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }

    @Override
    public void enterRelationExpression(WhileParser.RelationExpressionContext ctx){
        SyntaxTreeNode node;
        if(ctx.expression().size()==2){
            Expression left = (Expression) nodeStack.pop();
            Expression right = (Expression) nodeStack.pop();
            Operator op=null;
            if(ctx.LessThan()!=null){
                op = Operator.LessThan;
            } else if(ctx.GreaterThan()!=null){
                op = Operator.GreaterThan;
            } else if(ctx.Equals()!=null){
                op = Operator.Equals;
            } else if(ctx.LessEqualThan()!=null){
                op = Operator.LessEqualThan;
            } else if(ctx.GreaterEqualThan()!=null){
                op = Operator.GreatEqualThan;
            } else if(ctx.NotEquals()!=null){
                op = Operator.NotEquals;
            }
            node = makeRelationExpression(op, left, right);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }

    @Override
    public void enterBooleanUnaryExpression(WhileParser.BooleanUnaryExpressionContext ctx){
        SyntaxTreeNode node;
        if(ctx.expression()!=null && ctx.BooleanNot()!=null){
            Operator op = Operator.BooleanNot;
            Expression boolExp = (Expression) nodeStack.pop();
            node = makeBooleanUnaryExpression(op, boolExp);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }
    @Override
    public void enterArithAddSubExpression(WhileParser.ArithAddSubExpressionContext ctx) {
        SyntaxTreeNode node;
        Operator op=null;
        if(ctx.expression().size()==2){
            Expression right = (Expression) nodeStack.pop();
            Expression left = (Expression) nodeStack.pop();
            if(ctx.Plus()!=null)
                op = Operator.Add;
            else if(ctx.Minus()!=null)
                op = Operator.Sub;
            node = makeArithBinaryExpression(op, left, right);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }
    @Override public void enterArithUnaryExpression(WhileParser.ArithUnaryExpressionContext ctx) {
        SyntaxTreeNode node;
        Operator op = null;
        if(ctx.expression()!=null){
            NumericalExpression term = (NumericalExpression) nodeStack.pop();
            if(ctx.Plus()!=null)
                op = Operator.Add;
            else if (ctx.Minus()!=null)
                op=Operator.Sub;

            node = makeArithUnaryExpression(op, term);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);

        }
    }
    @Override public void enterArithMulDivExpression(WhileParser.ArithMulDivExpressionContext ctx) {
        SyntaxTreeNode node;
        Operator op=null;
        if(ctx.expression().size()==2){
            Expression right = (Expression) nodeStack.pop();
            Expression left = (Expression) nodeStack.pop();
            if(ctx.Multiply()!=null)
                op = Operator.Mul;
            else if(ctx.Divide()!=null)
                op = Operator.Div;
            node = makeArithBinaryExpression(op, left, right);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }
    @Override public void enterAssignExpression(WhileParser.AssignExpressionContext ctx) {
        SyntaxTreeNode node;
        if(ctx.expression().size()==2){
            Expression sourceVal = (Expression) nodeStack.pop();
            Expression destVar = (Expression) nodeStack.pop();
            node = makeAssignExpression(destVar, sourceVal);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }

    private AssignmentExpression makeAssignExpression(Expression destVar, Expression sourceVal) {
        return new AssignmentExpression(destVar,sourceVal);
    }

    private NumericalUnaryExpression makeArithUnaryExpression(Operator op, NumericalExpression term) {
        return new NumericalUnaryExpression(op, term);
    }

    private Expression makeArithBinaryExpression(Operator op, Expression left, Expression right) {
        return new NumericalBinaryExpression(op, left, right);
    }

    private BooleanUnaryExpression makeBooleanUnaryExpression(Operator op, Expression boolExp) {
        return new BooleanUnaryExpression(op, boolExp);
    }

    private RelationExpression makeRelationExpression(Operator op, Expression left, Expression right) {
        RelationExpression rel = new RelationExpression(op, left, right);
        return rel;
    }


    private BooleanBinaryExpression makeBooleanBinaryExpression(Operator op, Expression left, Expression right) {
        BooleanBinaryExpression boolbinexp = new BooleanBinaryExpression(op, left, right);
        return boolbinexp;
    }
    //=====================================================================

    @Override public void enterStatements(WhileParser.StatementsContext ctx) {
        ArrayList<Statement> statementList = new ArrayList<Statement>();
        int statementCount;
        if(ctx.statement()!=null){
            statementCount = ctx.statement().size();
            for(int i =0; i< statementCount; i++){
                statementList.add((Statement) nodeStack.pop());
            }
            Collections.reverse(statementList);
            StatementList stmts = makeStatementList(statementList);

            nodeStack.push(stmts);
            stmts.setParserRuleContext(ctx);
        }


    }

    @Override public void enterBlockStatement(WhileParser.BlockStatementContext ctx){
        if(ctx.statements()!=null){
            StatementList list = (StatementList) nodeStack.pop();

            BlockStatement block = makeBlockStatement(list);

            nodeStack.push(block);
            block.setParserRuleContext(ctx);

        }
    }
    @Override public void enterReadStatement(WhileParser.ReadStatementContext ctx){
        if(ctx.parenExpression()!=null){
            Expression exp = (Expression) nodeStack.pop();
            ReadStatement rs = makeReadExpression(exp);
            nodeStack.push(rs);
            rs.setParserRuleContext(ctx);
        }
    }



    @Override public void enterParenExpression(WhileParser.ParenExpressionContext ctx){
        //do nothing, let paren expression fall through and handle Expression
    }

    @Override public void enterWriteStatement(WhileParser.WriteStatementContext ctx){
        if(ctx.parenExpression()!=null){
            Expression expression = (Expression) nodeStack.pop();
            WriteStatement ws = makeWriteStatement(expression);
            nodeStack.push(ws);
            ws.setParserRuleContext(ctx);
        }
    }
    @Override public void enterWriteLineStatement(WhileParser.WriteLineStatementContext ctx){
        if(ctx.WriteLn()!=null){
            SyntaxTreeNode node = makeWriteLineStatement();
            node.setParserRuleContext(ctx);
            nodeStack.push(node);
        }
    }


    @Override public void enterIfStatement(WhileParser.IfStatementContext ctx){
        if( (ctx.expression()!=null) && (ctx.statement().size()==2) ){
            Statement elsePart = (Statement) nodeStack.pop();
            Statement thenPart = (Statement) nodeStack.pop();
            Expression condition = (Expression) nodeStack.pop();
            IfStatement ifstmt = makeIfStatement(condition, thenPart, elsePart);
            nodeStack.push(ifstmt);
            ifstmt.setParserRuleContext(ctx);
        }
    }

    @Override public void enterWhileStatement(WhileParser.WhileStatementContext ctx){
        if( ctx.expression()!=null && ctx.statement()!=null){
            Statement doPart = (Statement) nodeStack.pop();
            BooleanExpression condition = (BooleanExpression) nodeStack.pop();
            WhileStatement ws = makeWhileStatement(condition, doPart);
            ws.setParserRuleContext(ctx);
            nodeStack.push(ws);
        }
    }
    @Override public void enterSkipStatement(WhileParser.SkipStatementContext ctx){
        if(ctx.Skip()!=null){
            SkipStatement skip = makeSkipStatement();
            skip.setParserRuleContext(ctx);
            nodeStack.push(skip);
        }
    }
    @Override public void enterStatement(WhileParser.StatementContext ctx){
        if(ctx.expression()!=null){
            Expression exp = (Expression) nodeStack.pop();
            ExpressionStatement es = makeExpressionStatement(exp);
            nodeStack.push(es);
            es.setParserRuleContext(ctx);
        }
    }
    @Override public void enterFunctionDefinition(WhileParser.FunctionDefinitionContext ctx){
        if(ctx !=null){
            BlockStatement body = (BlockStatement) nodeStack.pop();
            ParameterList params = (ParameterList) nodeStack.pop();
            Identifier iden = makeIdentifier(ctx.Identifier());
            SyntaxTreeNode node = makeFunctionDefinition(iden, params, body);
            nodeStack.push(node);
            node.setParserRuleContext(ctx);
        }
    }
    private SyntaxTreeNode makeFunctionDefinition(Identifier iden, ParameterList params, BlockStatement body) {
        return new FunctionDefinition(iden, params, body);
    }

    @Override public void enterParameterList(ParameterListContext ctx){
        int count = 0;
        if(ctx.expression()!=null){
            count = ctx.expression().size();
            List<Expression> params = new ArrayList<Expression> ();
            for(int i = 0; i < count ; i++){
                params.add((Expression) nodeStack.pop());
            }
            Collections.reverse(params);
            SyntaxTreeNode node = makeParameterList(params);
            nodeStack.add(node);
            node.setParserRuleContext(ctx);
        }
    }

    private SyntaxTreeNode makeParameterList(List<Expression> params) {
        return new ParameterList(params);
    }

    @Override public void enterReturnStatement(WhileParser.ReturnStatementContext ctx){
        Expression exp = null;
        if(ctx.expression()!=null){
            exp = (Expression) nodeStack.pop();
        }
        ReturnStatement rs = makeReturnStatement(exp);
        nodeStack.push(rs);
        rs.setParserRuleContext(ctx);
    }



    private ReturnStatement makeReturnStatement(Expression exp) {
        ReturnStatement rs;
        if(exp != null){
            rs = new ReturnStatement(exp);
        }
        else {
            rs = new ReturnStatement();
        }
        return rs;
    }

    private Tree.ExpressionStatement makeExpressionStatement(Expression exp) {
        return new ExpressionStatement(exp);
    }

    private SkipStatement makeSkipStatement() {
        return new SkipStatement();
    }

    private SyntaxTreeNode makeWriteLineStatement() {
        return new WriteLineStatement();
    }

    private WhileStatement makeWhileStatement(BooleanExpression condition, Statement doPart) {
        return new WhileStatement(condition, doPart);
    }

    private IfStatement makeIfStatement(Expression condition, Statement thenPart, Statement elsePart) {
        return new IfStatement(condition, thenPart, elsePart);
    }

    private WriteStatement makeWriteStatement(Expression exp){
        return new WriteStatement(exp);
    }
    private BlockStatement makeBlockStatement(StatementList list) {
        return new BlockStatement(list);
    }

    private StatementList makeStatementList(List<Statement> stmtList) {
        StatementList list = new StatementList(stmtList);
        return list;
    }

    private ReadStatement makeReadExpression(Expression exp) {
        return new ReadStatement(exp);
    }
    //==========================================================

    @Override
    public void enterProgram(WhileParser.ProgramContext ctx){
        if(ctx.statements()!=null){
            StatementList stmts = (StatementList) nodeStack.pop();
            CompilationUnit unit = makeCompilationUnit(stmts);
            unit.setParserRuleContext(ctx);
            nodeStack.push(unit);
            unit.setParserRuleContext(ctx);
        }
    }

    private CompilationUnit makeCompilationUnit(StatementList stmts) {
        CompilationUnit unit = new CompilationUnit(stmts);
        return unit;
    }

    @Override
    public void visitErrorNode(ErrorNode node){
        super.visitErrorNode(node);
        throw new RuntimeException("error building syntax tree");
    }

}
