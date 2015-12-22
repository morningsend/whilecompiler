package Semantics;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public interface LocalScope {
    public Scope getParentScope();
    public Scope getInnerScope();
}
