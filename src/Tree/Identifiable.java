package Tree;

import Semantics.Scope;

/**
 * Created by Zaiyang on 14/12/2015.
 */
public interface Identifiable {
    public String getIdentierName();
    public Scope getBoundScope();
}
