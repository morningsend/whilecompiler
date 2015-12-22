package Semantics;

import Model.DataType;
import Tree.AssignmentExpression;
import Tree.CompilationUnit;
import Tree.ExpressionStatement;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class TypeCheckerTest extends TypeCheckerTestRig{

    @Test
    public void testIntegerLiteral(){
        ParseTree tree = parseString("a:=1");
        CompilationUnit unit = buildTree(tree);
        Scope scope = resolveScope(unit);
        checker = new TypeChecker(scope);
        ExpressionStatement es = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression ae = (AssignmentExpression) es.getExpression();
        DataType type = checker.inferExpressionType(ae.getValue());
        assertEquals("type of 1 equals integer", DataType.Integer, type);
        DataType expType = checker.inferExpressionType(ae);
        assertEquals("Type of expression a:=1 has type Integer", DataType.Integer, expType);
    }
    @Test
    public void testFloatLiteral(){
        ParseTree tree = parseString("a:=1.5");
        CompilationUnit unit = buildTree(tree);
        Scope scope = resolveScope(unit);
        checker = new TypeChecker(scope);
        ExpressionStatement es = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression ae = (AssignmentExpression) es.getExpression();
        DataType type = checker.inferExpressionType(ae.getValue());
        assertEquals("type of 1 equals Real", DataType.Real, type);
        DataType expType = checker.inferExpressionType(ae);
        assertEquals("Type of expression a:=1 has type real", DataType.Real, expType);
    }

    @Test
    public void testBooleanLiteral(){
        ParseTree tree = parseString("a:=(1=1)");
        CompilationUnit unit = buildTree(tree);
        Scope scope = resolveScope(unit);
        checker = new TypeChecker(scope);
        ExpressionStatement es = (ExpressionStatement) unit.getProgramBody().getStatement(0);
        AssignmentExpression ae = (AssignmentExpression) es.getExpression();
        DataType type = checker.inferExpressionType(ae.getValue());
        assertEquals("type of 1 equals boolean", DataType.Boolean, type);
        DataType expType = checker.inferExpressionType(ae);
        assertEquals("Type of expression a:=1 has type boolean", DataType.Boolean, expType);
    }

    @Test
    public void testQuadrupleTypeChecking(){
        ParseTree tree = parseString("a:=1; b:=2; c:=a*b");
        CompilationUnit unit = buildTree (tree);
        Scope scope = resolveScope(unit);
    }

}