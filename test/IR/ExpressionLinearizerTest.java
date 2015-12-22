package IR;

import Tree.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.util.List;

public class ExpressionLinearizerTest extends TreeBuilderTestRig {

    public void setUp() throws Exception {
        super.setUp();
    }
    @Test
    public void testNumericalExpression(){
        ParseTree tree = parseString("a:=1+2*3-4");
        CompilationUnit unit = buildTree(tree);
        ExpressionLinearizer expLin = new ExpressionLinearizer();
        Statement stmt = unit.getProgramBody().getStatement(0);
        Expression e = ((ExpressionStatement) stmt).getExpression();
        List<AssignmentExpression> quad = expLin.makeLinear(e);
        assertEquals("a:=1+2*3-4 is split into 4 sub expression", quad.size(), 4);
        AssignmentExpression last = quad.get(quad.size()-1);
        assertEquals("last expression is assigned to a", "a", ((Identifiable)last.getVariable()).getIdentierName());
    }
    @Test
    public void testBooleanExpression(){
        ParseTree tree = parseString("a:=(4-5)<(3+2)");
        CompilationUnit unit = buildTree(tree);
        ExpressionLinearizer expLin = new ExpressionLinearizer();
        Statement stmt = unit.getProgramBody().getStatement(0);
        Expression e = ((ExpressionStatement) stmt).getExpression();
        List<AssignmentExpression> quad = expLin.makeLinear(e);
        assertEquals("a:=(4-5)<(3+2) is split into  sub expression", quad.size(), 4);
        AssignmentExpression last = quad.get(quad.size()-1);
        assertEquals("last expression is assigned to a", "a", ((Identifiable)last.getVariable()).getIdentierName());
    }

    @Test
    public void testArrayAccess(){
        ParseTree tree = parseString("a[4-2]:=5");
        CompilationUnit unit = buildTree(tree);
        ExpressionLinearizer expLin = new ExpressionLinearizer();
        Statement stmt = unit.getProgramBody().getStatement(0);
        Expression e = ((ExpressionStatement) stmt).getExpression();
        List<AssignmentExpression> quad = expLin.makeLinear(e);
        assertEquals("a:=(4-5)<(3+2) is split into  sub expression", 3, quad.size());
        AssignmentExpression last = quad.get(quad.size()-1);
        assertEquals("last expression is assigned to a", "a", ((Identifiable)last.getVariable()).getIdentierName());
    }

    @Test
    public void testFunctionCall(){
        ParseTree tree = parseString("b:=multiply(3+5, 5-2)");
        CompilationUnit unit = buildTree(tree);
        ExpressionLinearizer linearizer = new ExpressionLinearizer();
        Statement stmt = unit.getProgramBody().getStatement(0);
        Expression e = ((ExpressionStatement)stmt).getExpression();
        List<AssignmentExpression> quad = linearizer.makeLinear(e);

        assertNotNull(quad);
    }
}