package Semantics;

import Tree.CompilationUnit;
import org.antlr.v4.runtime.tree.ParseTree;

public class TreeAnalyzerTest extends ScopeTestRig{

    public void testErrorInputIf(){
        ParseTree tree = parseString("if 3 then ( write(1-true)) else (3 := a)");
        CompilationUnit unit = buildTree(tree);
        TreeAnalyzer analyzer = new TreeAnalyzer(unit);
        analyzer.analyzerCorrectness();
        assertTrue("the source code has errors", analyzer.hasErrors());
    }
    public void testErrorInputEmptyBlockStmt(){
        ParseTree tree = parseString("()");
        if(parser.getErrorHandler().inErrorRecoveryMode(parser)){
            System.out.println("error");
        }else {
            CompilationUnit unit = buildTree(tree);
            TreeAnalyzer analyzer = new TreeAnalyzer(unit);
            analyzer.analyzerCorrectness();
            assertTrue("the source code has errors", analyzer.hasErrors());
        }
    }
}