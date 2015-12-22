package Tree;

import Model.DataType;
import Model.Operator;
import Parser.SyntaxErrorHandler;
import Parser.WhileLexer;
import Parser.WhileParser;
import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class SyntaxTreeBuilderTest extends TreeBuilderTestRig {

    @Before
    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }

    @Test
    public void testTreeVariable(){
        ParseTree tree = parseString("i:=1");
        CompilationUnit unit = buildTree(tree);
        assertTrue("Tree is not empty", unit!=null);
        assertTrue("Tree has one statement", unit.getProgramBody().getStatements().size()==1);
        assertTrue("statement is of type assignmentExpression", unit.getProgramBody().getStatement(0) instanceof ExpressionStatement);
        ExpressionStatement stmt = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression exp = (AssignmentExpression) stmt.getExpression();
        assertTrue("left-hand side is type identifier", exp.getVariable() instanceof Identifier);
    }
    @Test
    public void testTreeAddidtion(){
        ParseTree tree = parseString("i:=1; j:=2; h:=i+j");
        CompilationUnit unit = buildTree(tree);
        ExpressionStatement stmt = (ExpressionStatement) unit.getProgramBody().getStatement(2);
        AssignmentExpression exp = (AssignmentExpression) stmt.getExpression();
        Expression exp2 = exp.getValue();
        assertTrue("statement 2 is type numericalbinaryexpression", exp2 instanceof NumericalBinaryExpression);
        NumericalBinaryExpression binexp = (NumericalBinaryExpression) exp2;
        assertEquals("left operand is i", ((Identifier) binexp.getLeftOperand()).getName(), "i");
        assertEquals("right operand is j", ((Identifier)binexp.getRightOperand()).getName(), "j");

    }
    @Test
    public void testTreeBoolean(){
        ParseTree tree = parseString("h:=(5=5)");
        CompilationUnit unit = buildTree(tree);
        ExpressionStatement est = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression ase = (AssignmentExpression) est.getExpression();
        Expression var = ase.getVariable();
        Expression val = ase.getValue();
        assertTrue("value is of type boolean literal", val instanceof BooleanExpression);
        assertEquals("value has type Boolean", val.getDataType(), DataType.Boolean);
    }
    @Test
    public void testTreeBooleanLiteral(){
        ParseTree tree = parseString("h:=true");
        CompilationUnit unit = buildTree(tree);
        ExpressionStatement est = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression ase = (AssignmentExpression) est.getExpression();
        Expression var = ase.getVariable();
        Expression val = ase.getValue();
        assertTrue("value is of type boolean literal", val instanceof BooleanLiteral);
    }

    @Test
    public void testRunTest0W() throws IOException {
        ParseTree tree = parseFile("example\\test0.w");
        CompilationUnit unit = buildTree(tree);
        assertNotNull("unit is not null", unit);
    }
    @Test
    public void testIf(){
        ParseTree tree = parseString("if ! 3=9 then ( write(i+5) ) else ( writeln ) ");
        CompilationUnit unit = buildTree(tree);
        assertNotNull("unit is not null", unit);
        Statement stmt = unit.getProgramBody().getStatement(0);
        assertTrue("Statement is if statement", stmt instanceof IfStatement);
        IfStatement ifstmt = (IfStatement) stmt;
        Statement thenstmt = ((BlockStatement) ifstmt.getThenBranch()).getBlockBody().get(0);
        Statement elsestmt = ((BlockStatement) ifstmt.getElseBranch()).getBlockBody().get(0);
        Expression condition = ifstmt.getCondition();
        assertTrue("then part is of type writestatement", thenstmt instanceof WriteStatement );
        assertTrue("else part is of type writelinestatement", elsestmt instanceof WriteLineStatement);
        assertTrue("condition is a boolean unary expression", condition instanceof BooleanUnaryExpression);
        assertTrue("inner condition is a relation expression", ((BooleanUnaryExpression)condition).getOperand() instanceof RelationExpression );
    }

    @Test public void testWhile(){
        ParseTree tree = parseString("while i <= 6 do ( i= i+1 )");
        CompilationUnit unit = buildTree(tree);
        assertNotNull(unit);
        WhileStatement ws = (WhileStatement) unit.getProgramBody().getStatement(0);


    }
    @Test public void testFunctionDefinition(){
        ParseTree tree = parseString("function multiply (a,b) ( a+b )");
        CompilationUnit unit = buildTree(tree);
        assertNotNull(unit);
        FunctionDefinition f = (FunctionDefinition) unit.getProgramBody().getStatement(0);
        //ExpressionStatement callstmt = (ExpressionStatement) unit.getProgramBody().getStatement(1);

    }

    @Test public void testArrayAccess(){
        ParseTree tree = parseString("a[1]:=1");
        CompilationUnit unit = buildTree(tree);
        assertNotNull(unit);
        ExpressionStatement es = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        //ExpressionStatement es2 = (ExpressionStatement) unit.getProgramBody().getStatement(1);

        assertTrue("Expression is of type assignmentexpression", es.getExpression() instanceof AssignmentExpression);
        AssignmentExpression ae = (AssignmentExpression) es.getExpression();
        assertTrue("assignemnt variable is of type array access", (ae.getVariable()) instanceof ArrayAccess);
    }
    @Test public void testTestskW() throws IOException {
        ParseTree tree = parseFile("example\\testsk.w");
        CompilationUnit unit = buildTree(tree);
        assertNotNull(unit);
        int stmtCount = 0;
        stmtCount = unit.getProgramBody().getStatements().size();
        assertEquals("number of statements in testsk.w is equal to 8", 8, stmtCount);
    }
    @Test
    public void testArrayDefinition(){
        ParseTree tree = parseString("a:=array(10); a[1]:=1 ");
        CompilationUnit unit = buildTree(tree);

        assertNotNull(unit);

        List<Statement> stmts = unit.getProgramBody().getStatements();
        AssignmentExpression ae1 = (AssignmentExpression) ((ExpressionStatement) stmts.get(0)).getExpression();
        assertTrue("a is assigned to array definition", ae1.getValue() instanceof ArrayDefinition);
        assertEquals("array definition defines array of length 10", ((ArrayDefinition) ae1.getValue()).getLength().getIntValue(),10);
    }

    @Test
    public void testEqualsNotEquals(){
        ParseTree tree = parseString("true=true; false!=true");
        CompilationUnit unit = buildTree(tree);
        List<Statement> stmts = unit.getProgramBody().getStatements();
        assertTrue("true=true is a relation expression", ((ExpressionStatement)stmts.get(0)).getExpression() instanceof RelationExpression);
        assertTrue("true!=false is a relation expression", ((ExpressionStatement)stmts.get(1)).getExpression() instanceof RelationExpression);

    }
    public void testErrorHandler(){
        try {
            CharStream ss = new ANTLRInputStream("if then write(1) e");
            WhileLexer lexer = new WhileLexer(ss);
            CommonTokenStream ts = new CommonTokenStream(lexer);
            WhileParser parser = new WhileParser(ts);
            //parser.removeErrorListeners();
            parser.addErrorListener(new SyntaxErrorHandler());
            parser.setErrorHandler(new BailErrorStrategy());
            parser.program();
            this.fail();
        }catch(ParseCancellationException e){

        }

    }

    public void testBooleanUnary(){
        ParseTree tree = parseString("write(!true)");
        CompilationUnit unit = buildTree(tree);
        WriteStatement ws = (WriteStatement) unit.getProgramBody().getStatement(0);
        BooleanUnaryExpression boolun = (BooleanUnaryExpression) ws.getExpression();
        assertNotNull(boolun.getOperator());
        assertEquals("bool unary expression has operator !", Operator.BooleanNot, boolun.getOperator());
    }

}