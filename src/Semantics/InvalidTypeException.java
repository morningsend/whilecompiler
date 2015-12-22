package Semantics;

import Tree.SyntaxTreeNode;

/**
 * Created by Zaiyang on 14/12/2015.
 */
public class InvalidTypeException extends RuntimeException{

    public InvalidTypeException(SyntaxTreeNode node, String message) {
        super(message);
    }
}
