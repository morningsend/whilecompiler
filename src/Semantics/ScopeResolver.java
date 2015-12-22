package Semantics;

import Model.DataType;
import Tree.*;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by Zaiyang on 14/12/2015.
 */
public class ScopeResolver extends SemanticsAnalyzer{
    protected Scope globalScope;
    protected Stack<Scope> scopeStack;
    protected TypeChecker typeChecker;
    public ScopeResolver(CompilationUnit node){
        this.unit = node;
    }

    public ScopeResolver(){

    }
    public void resolve(){
        globalScope = new Scope();
        unit.setGlobalScope(globalScope);
        typeChecker = new TypeChecker(globalScope);
        scopeStack = new Stack<Scope>();
        scopeStack.push(globalScope);
        StatementList statements = unit.getProgramBody();
        process(statements);
        unit.setGlobalScope(scopeStack.peek());
    }

    public void process(Statement statement) {
        if(statement instanceof FunctionDefinition){
            resolveFunctionDefinition((FunctionDefinition) statement);
        }else if(statement instanceof ExpressionStatement){
            processExpression(((ExpressionStatement)statement).getExpression());
        }else if(statement instanceof WriteStatement){
            processExpression(((WriteStatement) statement).getExpression());
        }else if(statement instanceof ReadStatement){
            processRdStmt((ReadStatement) statement);
        }else if(statement instanceof ReturnStatement){
            processExpression(((ReturnStatement) statement).getExpression());
        }else if(statement instanceof BlockStatement){
            BlockStatement block = (BlockStatement) statement;
            Scope newScope = new Scope();
            block.setInnerScope(newScope);
            block.setParentScope(currentScope());
            newScope.setParentScope(currentScope());
            newScope.setBoundedToNode(block);
            scopeStack.push(newScope);
            process(block.getStatements());
            scopeStack.pop();
        }else if(statement instanceof IfStatement){
            processIfStmt((IfStatement) statement);
        }else if(statement instanceof WhileStatement){
            processWhStmt((WhileStatement) statement);
        }
    }

    private void processWhStmt(WhileStatement statement) {
        processExpression(statement.getCondition());
        process(statement.getWhileBody());
    }

    private void processIfStmt(IfStatement statement) {
        processExpression(statement.getCondition());
        process(statement.getThenBranch());
        process(statement.getElseBranch());
    }

    private void processRdStmt(ReadStatement statement) {
        Identifier id = (Identifier) statement.getLocation();
        if(id instanceof ArrayAccess){
            resolveArrayAccess((ArrayAccess) id);
        }else if(id instanceof FunctionCall){

        }else{
            Element e = currentScope().lookupSymbol(id.getIdentierName());
            if(e==null){
                e = processVarDefinition(id);
                e.setType(DataType.Integer);
                id.setDataType(DataType.Integer);

            }
        }


    }

    public void processExpression(Expression e) {
        if(e instanceof FunctionCall){
            resolveFuncCall((FunctionCall) e);
        }else if(e instanceof ArrayAccess){
            resolveArrayAccess((ArrayAccess) e);
        }else if(e instanceof Identifier){
            resolveIden((Identifier) e);
        }else if(e instanceof BooleanUnaryExpression){
            resolveBoolUnExp((BooleanUnaryExpression) e);
        }else if(e instanceof BooleanBinaryExpression){
            resolveBoolBinExp((BooleanBinaryExpression) e);
        }else if(e instanceof NumericalBinaryExpression){
            resolveNumBinExp((NumericalBinaryExpression) e);
        }else if(e instanceof NumericalUnaryExpression){
            resolveNumUnExp((NumericalUnaryExpression) e);
        }else if(e instanceof RelationExpression){
            resolveRelExp((RelationExpression) e);
        }else if(e instanceof AssignmentExpression){
            resolveAssExp((AssignmentExpression) e);
        }else if(e instanceof StringLiteral){
            resolveString((StringLiteral) e);
        }else if(e instanceof IntegerLiteral){

        }else if(e instanceof BooleanLiteral){

        }
    }

    private void resolveFuncCall(FunctionCall e) {
        FunctionElement element = currentScope().lookupFuncDefInScopeChain(e.getName());
        String message = "function '"+e.getName()+"' is not defined in scope chain";
        if(assertCondition(element!=null, new SemanticsException(e, message))) {


            message = "function with name " + e.getName() + " is not defined in scope";
            assertCondition(element != null, new SemanticsException(e, message));

            validateFuncCallParams(e, (FunctionDefinition) element.getNode());
        }
    }

    private void validateFuncCallParams(FunctionCall funcCal, FunctionDefinition node) {

        String message = "function '"+funcCal.getName()+"' is called with wrong number of arguments";
        assertCondition(funcCal.getArgs().getParameters().size() == node.getParameters().getParameters().size(),
                new SemanticsException(funcCal, message));
    }

    private void resolveArrayAccess(ArrayAccess e) {
        Element ei = resolveIden(e);
        String message ="";
        if(ei!=null){
            message = "variable with name "+ e.getName()+ " is not an array";
            SyntaxTreeNode node = (SyntaxTreeNode) ei.getNode();
            assertCondition(ei.getType()==DataType.Array, new SemanticsException(e,message));
            assertCondition(ei instanceof ArrayElement, new SemanticsException(e, message));

        }
    }

    private Element resolveIden(Identifier e) {
        Element ei = currentScope().lookupSymbolInScopeChain(e.getName());
        String message = "variable with name "+ e.getName()+ " cannot be found in scope chain";
        assertCondition(ei!=null, new SemanticsException(e, message));
        if(ei!=null){
            message = "variable with name "+e.getName()+" is uninitialized";
            if(assertCondition(ei.getType()!=DataType.Unresolved, new SemanticsException(e, message))){
                e.setBoundScope(e.getBoundScope());
            }
        }
        return ei;
    }

    private void resolveBoolUnExp(BooleanUnaryExpression e) {
        processExpression(e.getOperand());
    }

    private void resolveBoolBinExp(BooleanBinaryExpression e) {
        processExpression(e.getLeftOperand());
        processExpression(e.getRightOperand());
    }

    private void resolveNumBinExp(NumericalBinaryExpression e) {
        processExpression(e.getLeftOperand());
        processExpression(e.getRightOperand());
    }

    private void resolveNumUnExp(NumericalUnaryExpression e) {
        processExpression(e.getOperand());
    }

    private void resolveRelExp(RelationExpression e) {
        processExpression(e.getLeftOperand());
        processExpression(e.getRightOperand());
    }

    private void resolveAssExp(AssignmentExpression e) {
        Expression exp = e.getValue();
        Identifier iden = (Identifier) e.getVariable();
        Element el = currentScope().lookupSymbolInScopeChain(iden.getName());
        if(el==null){
            if (exp instanceof ArrayDefinition){
                el = processArrayDefinition(iden, (ArrayDefinition) exp);
            }else {
                el = processVarDefinition(iden);

                resolveVarAssignment(iden, exp);
            }
            currentScope().insertSymbol(el);
        }
        else {
            if(iden instanceof ArrayAccess){
                resolveArrayElementAssignment((ArrayAccess)iden, exp);
            }else if(iden instanceof FunctionCall){

            }else {
                resolveVarAssignment(iden, exp);
            }
        }
    }

    private void resolveString(StringLiteral e) {
        String s = e.getString();
        Element el = currentScope().getGlobalScope().lookupString(s);

        if(el==null){
            el = makeStringElement(s);
            el.setBoundScope(globalScope);
            el.setNode(e);
            el.setType(DataType.String);
            currentScope().getGlobalScope().insertString(el);
        }
    }

    private Element makeStringElement(String s) {
        Element el = new Element();
        el.setType(DataType.String);
        el.setKind(SymbolKind.Literal);
        return el;
    }

    private void resolveVarAssignment(Identifier iden, Expression e){
        processExpression(e);
        typeChecker.setDefaultScope(currentScope());
        DataType expType = null;
            expType = typeChecker.inferExpressionType(e);
        String message = "expression '"+e.getParserRuleContext().getText()+"' type cannot be determined, possibly contains uninitialized variable or incompatible types";
        if(assertCondition(expType!= DataType.Unresolved, new SemanticsException(e, message))){
            Element el = currentScope().lookupSymbolInScopeChain(iden.getIdentierName());
            DataType varType = el.getType();
            message = "variable '"+iden.getName()+
                    "' and expression '"
                    +e.getParserRuleContext().getText()
                    +"' have incompatible types.";
            if(assertCondition(TypeRules.canAssign(varType, expType), new SemanticsException(e, message))){
                if(varType==DataType.Unresolved){
                    el.setType(expType);
                }
            }
        }
    }
    private void resolveArrayElementAssignment(ArrayAccess arrElement, Expression e){
        Element el = currentScope().lookupSymbolInScopeChain(arrElement.getName());
        typeChecker.setDefaultScope(currentScope());
        DataType expType = null;

        expType = typeChecker.inferExpressionType(e);


        if(el!=null){

            ArrayElement ae = (ArrayElement)el;
            String message = "expression's type cannot be determined, possibly contains uninitialized variable";
            if(assertCondition(expType!= DataType.Unresolved, new SemanticsException(e, message))){
                DataType varType = ae.getElementType();
                message = "array element in '"+ae.getName()+
                        "' and expression '"
                        +e.getParserRuleContext().getText()
                        +"' have incompatible types.";
                if(assertCondition(TypeRules.canAssign(varType, expType), new SemanticsException(e, message))){
                    if(varType == DataType.Unresolved){
                        ae.setElementType(expType);
                    }
                }
            }
        }
    }
    private Element processVarDefinition(Identifier iden) {
        Element el = new Element();
        el.setType(DataType.Unresolved);
        el.setKind(SymbolKind.Name);
        el.setName(iden.getName());
        el.setNode(iden);
        el.setBoundScope(currentScope());
        currentScope().insertSymbol(el);
        Element.copyElementAttributeToIdentifier(iden, el);
        return el;
    }
    private Element processArrayDefinition(Identifier iden, ArrayDefinition arrayDef){
        ArrayElement el = new ArrayElement();
        el.setBoundScope(currentScope());
        el.setKind(SymbolKind.Name);
        el.setType(DataType.Array);
        el.setName(iden.getName());
        el.setNode(iden);
        el.setDefinition(arrayDef);
        el.setElementType(DataType.Unresolved);
        Element.copyElementAttributeToIdentifier(iden, el);
        return el;
    }

    public void process(StatementList statements){
        Iterator<Statement> iter = statements.getStatements().iterator();
        while(iter.hasNext()){
            process(iter.next());
        }
    }
    public void resolveFunctionDefinition(FunctionDefinition funcDef) {
        //find if there is another function defined by the same name in the current name
        //if so signal error
        //else add function to current scope
        FunctionElement e = currentScope().lookupFuncDef(funcDef.getName());
        String message = "function with name "+funcDef.getName()+" is already defined in scope";
        assertCondition(e == null, new SemanticsException(funcDef, message));
        if(e==null) {
            FunctionElement element = new FunctionElement();
            element.setName(funcDef.getName());
            element.setKind(SymbolKind.Name);
            element.setNode(funcDef);
            element.setBoundScope(currentScope());
            element.setType(DataType.Definition);
            element.setReturnType(DataType.Integer);
            funcDef.setReturnType(DataType.Integer);
            funcDef.getIdentifier().setBoundScope(currentScope());
            funcDef.setParentScope(currentScope());
            currentScope().insertFuncDef(element);
            processFunctionDefinitionInner(funcDef);
        }
    }

    private void processFunctionDefinitionInner(FunctionDefinition funcDef) {
        Scope functionScope = new Scope();
        funcDef.setInnerScope(functionScope);
        functionScope.setBoundedToNode(funcDef);
        functionScope.setParentScope(currentScope());
        ParameterList list = funcDef.getParameters();
        injectParametersIntoScope(list, functionScope);
        funcDef.setInnerScope(functionScope);
        scopeStack.push(functionScope);
        process(funcDef.getBody().getStatements());
        scopeStack.pop();
    }

    private void injectParametersIntoScope(ParameterList list, final Scope scope) {
        List<Expression> vars = list.getParameters();
        for(Expression expression : vars){
            String message = "expression '"+expression.getParserRuleContext().getText() +"' is not a valid function parameter";
            assertCondition(expression instanceof AssignmentExpression, new SemanticsException(expression, message) );
            if(expression instanceof AssignmentExpression) {
                AssignmentExpression ae = (AssignmentExpression) expression;
                Identifier iden = (Identifier) ae.getVariable();
                Expression defaultValue = ae.getValue();

                try {
                    DataType type = typeChecker.inferExpressionType(defaultValue);
                    Element e = new Element();
                    e.setType(type);
                    e.setBoundScope(scope);
                    e.setKind(SymbolKind.Name);
                    e.setName(iden.getName());
                    e.setNode(iden);
                    Element.copyElementAttributeToIdentifier(iden, e);
                    scope.insertSymbol(e);
                } catch (InvalidTypeException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    public Element makeElement(Identifier iden){
        Element element = new Element();
        element.setName(iden.getName());
        element.setKind(SymbolKind.Name);
        element.setNode(iden);
        element.setType(iden.getDataType());
        return element;
    }

    public Scope currentScope(){
        return scopeStack.peek();
    }

    public Scope getGlobalScope(){
        return globalScope;
    }
}
