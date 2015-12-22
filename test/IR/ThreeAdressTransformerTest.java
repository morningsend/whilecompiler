package IR;

import Semantics.ScopeResolver;
import Tree.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

import java.util.List;

public class ThreeAdressTransformerTest extends TreeBuilderTestRig {
    protected ThreeAdressTransformer quadTransformer;
    @Test
    public void testIfQuadruple(){
        ParseTree tree = parseString("if a > 0 then (write(1)) else (write(2))");
        CompilationUnit unit = buildTree(tree);
        quadTransformer = new ThreeAdressTransformer(unit);
        quadTransformer.transform();
        List<Statement> stmts = unit.getProgramBody().getStatements();
        assertTrue("number of statements after transformation is greater then 0", unit.getProgramBody().getStatements().size() > 0);
        assertTrue("last statement is skip statement", ThreeAdressTransformer.getLastOfList(stmts) instanceof SkipStatement);
    }
    @Test
    public void testWhileQuadruple(){
        ParseTree tree = parseString(" while i > 0 do ( write(i); i=i-1 ) ");
        CompilationUnit unit = buildTree(tree);
        quadTransformer = new ThreeAdressTransformer(unit);
        quadTransformer.transform();
        List<Statement> stmts = unit.getProgramBody().getStatements();

        //last statement is a skipstatement
        assertTrue("last statement of while is a skip statement", ThreeAdressTransformer.getLastOfList(stmts) instanceof SkipStatement);
        //the statement before last statement is a unconditional goto statement;
        Statement stmt = (GotoStatement) stmts.get(stmts.size()-2);
        assertTrue(stmt instanceof GotoStatement);
        GotoStatement gotoStmt = (GotoStatement) stmt;

        assertEquals("unconditonal jump's destination is the while beginning", gotoStmt.getDestination(), stmts.get(0));

    }
    public void testNestedWhile(){

        ParseTree tree = parseString("while i > 0 do ( while j > 0 do ( z:=i*j; write(z) ); writeln )");
        CompilationUnit unit = buildTree(tree);
        quadTransformer = new ThreeAdressTransformer(unit);
        List<Statement> stmts = unit.getProgramBody().getStatements();

    }
    public void testScopeResolver(){
        ParseTree tree = parseString("a:=1; b:=2; c:=3*a-b; write(c)");
        CompilationUnit unit = buildTree(tree);
        quadTransformer = new ThreeAdressTransformer(unit);
        quadTransformer.transform();
        unit = quadTransformer.getCompilationUnit();
        ScopeResolver resolver = new ScopeResolver(unit);
        resolver.resolve();
        assertNotNull(unit);
        assertNotNull(resolver.getGlobalScope());
        assertNotNull(resolver.getGlobalScope().lookupSymbol("c"));
    }
    public void testFunctionScope(){

        ParseTree tree = parseString("function multiply(x:=0, y:=0)(return x*y); a:=multiply(1,2)");

        CompilationUnit unit = buildTree(tree);


        ScopeResolver resolver = new ScopeResolver(unit);
        resolver.resolve();
        assertNotNull(resolver.getGlobalScope().lookupFuncDef("multiply"));
    }
}