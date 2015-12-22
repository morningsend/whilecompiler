package Semantics;

import Tree.CompilationUnit;
import Tree.TreeBuilderTestRig;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class ScopeTestRig extends TreeBuilderTestRig {
    protected ScopeResolver resolver;

    public Scope resolveScope(CompilationUnit unit){
        resolver = new ScopeResolver(unit);
        resolver.resolve();
        return resolver.getGlobalScope();
    }
}
