package Semantics;


import Tree.StringLiteral;
import Tree.SyntaxTreeNode;

import java.util.HashMap;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class Scope {

    private Scope parentScope = null;
    private SyntaxTreeNode boundedToNode = null;

    private HashMap<String, Element> symbolTable = new HashMap<String, Element>();
    private HashMap<String, Element> stringConstants = new HashMap<String, Element>();
    private HashMap<String, FunctionElement> functionDefs = new HashMap<>();

    private TypeChecker typeChecker;

    public Scope getParentScope(){
        return parentScope;
    }
    public void setParentScope(Scope parent){
        this.parentScope = parent;
    }
    public Scope(Scope parent){
        this();
        this.parentScope = parent;
    }
    public Scope(){
        typeChecker = new TypeChecker(this);
    }
    public Element lookup(String name){
        return symbolTable.get(name);
    }
    public Element lookupScopeChain(String name){
        Element e = symbolTable.get(name);
        if(e==null){
            if(parentScope==null)
                return null;
            else
                return parentScope.lookup(name);
        }else
            return e;
    }

    public SyntaxTreeNode getBoundedToNode() {
        return boundedToNode;
    }

    public void setBoundedToNode(SyntaxTreeNode boundedToNode) {
        this.boundedToNode = boundedToNode;
    }

    public boolean isGlobal(){
        return parentScope == null;
    }

    public HashMap<String, Element> getStringConstantTable(){
        return stringConstants;
    }

    public Element lookupSymbol(String varname){
        return symbolTable.get(varname);
    }
    public Element lookupString(String s){
        return stringConstants.get(s);
    }
    public FunctionElement lookupFuncDef(String funcName){
        return functionDefs.get(funcName);
    }
    public void insertSymbol( Element e){
        symbolTable.put(e.getName(), e);
        e.setBoundScope(this);
    }

    public void insertString(Element e){
        stringConstants.put(((StringLiteral)e.getNode()).getString(), e);
        e.setBoundScope(this);
    }

    public void insertFuncDef(FunctionElement e){
        functionDefs.put(e.getName(), e);
        e.setBoundScope(this);
    }

    public Scope getGlobalScope(){
        if(parentScope==this){
            return this;
        }
        if(parentScope==null){
            return this;
        }else return parentScope.getGlobalScope();
    }
    public Element lookupSymbolInScopeChain(String name){
        Element e = lookupSymbol(name);
        if(e !=null){
            return e;
        }else if( parentScope ==null){
            return null;
        }else
            return parentScope.lookupSymbolInScopeChain(name);
    }
    public Element lookupStringInScopeChain(String s){
        Element e = lookupString(s);
        if(e !=null){
            return e;
        }else if( parentScope ==null){
            return null;
        }else
            return parentScope.lookupStringInScopeChain(s);
    }
    public FunctionElement lookupFuncDefInScopeChain(String name){
        FunctionElement e = lookupFuncDef(name);
        if(e !=null){
            return e;
        }else if( parentScope ==null){
            return null;
        }else
            return parentScope.lookupFuncDefInScopeChain(name);
    }

    public HashMap<String, Element> getSymbolTable(){
        return symbolTable;
    }
}
