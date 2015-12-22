package Semantics;

import Model.DataType;
import Tree.CompilationUnit;
import Tree.FunctionDefinition;
import Tree.Identifier;
import org.antlr.v4.runtime.tree.ParseTree;
import org.junit.Test;

public class ScopeResolverTest extends ScopeTestRig {

    public void setUp() throws Exception {
        super.setUp();
    }
    @Test
    public void testVariableInScope(){
        ParseTree tree = parseString("a:=4");
        CompilationUnit unit = buildTree(tree);
        Scope scope = resolveScope(unit);
        Element element = scope.lookup("a");
        assertNotNull("a exist in scope", element);
        assertEquals("a is bound to the globalScope", ((Identifier)element.getNode()).getBoundScope(), resolver.getGlobalScope());
    }
    @Test
    public void testFunctionScope(){
        ParseTree tree = parseString("c:=0; function multiply(a,b) ( c:=2; return a*b); a:=1; b:=2; multiply(a,b)");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        Scope globalScope = resolver.getGlobalScope();
        assertNotNull(globalScope);
        Element function = globalScope.lookupFuncDefInScopeChain("multiply");
        assertNotNull(function);
        Scope functionScope = ((FunctionDefinition) function.getNode()).getInnerScope();
        assertNotNull(functionScope);
        Element aGlobal = globalScope.lookupSymbol("a");
        Element aFunction = functionScope.lookupSymbolInScopeChain("a");
        Element cFunction = functionScope.lookupSymbolInScopeChain("c");
        assertNotNull(cFunction);
        assertNotNull(aGlobal);
        assertNotNull(aFunction);
        assertNotSame("a in global scope is not in the same scope as function scope",((Identifier)aGlobal.getNode()).getBoundScope(),
                ((Identifier)aFunction.getNode()).getBoundScope()
                );
        assertEquals("global scope is parent of function scope", functionScope.getParentScope(), globalScope);
        assertEquals("c is in global scope, it can be seen from function scope", cFunction.getBoundScope(), globalScope);
    }

    @Test
    public void testBlockScope(){
        ParseTree tree = parseString("a:=1; b:=1; (a:=2; write(a))");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        Scope globalScope = resolver.getGlobalScope();
        Scope blockScope;
    }
    public void testVariableDefinition(){
        ParseTree tree = parseString("a:=1; b:=a");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        Element eb = unit.getGlobalScope().lookup("b");
        Element ea = unit.getGlobalScope().lookup("a");
        assertNotNull("variable b exists in global scope",eb);
        assertEquals("type of b equals type of a", ea.getType(), eb.getType());
    }
    public void testErrorCaseAssignToUninitializedVar(){
        ParseTree tree = parseString("c:=x");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        Element e = unit.getGlobalScope().lookupSymbol("c");
        assertNotNull(e);
        assertEquals(e.getType(), DataType.Unresolved);
        assertTrue("program has semantic errors", resolver.hasErrors());
        System.err.println(resolver.getErrors().get(0));
    }
    public void testErrorCaseIncompatibleType(){
        ParseTree tree = parseString("a:=1; a:=true");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        assertTrue(resolver.hasErrors());
    }
    public void testStringLitera(){
        ParseTree tree = parseString("write('helloword')");
        CompilationUnit unit = buildTree(tree);
        Scope scope = resolveScope(unit);
        assertTrue("Scope contains string helloword", scope.getStringConstantTable().containsKey("helloword"));


    }
    public void testErrorCaseIncompatibleType2(){
        ParseTree tree = parseString("a:=1; b:=true; c:= a*b");
        CompilationUnit unit = buildTree(tree);
        resolveScope(unit);
        assertTrue(resolver.hasErrors());
        resolver.getErrors().get(0).printErrorMessage();
    }
}