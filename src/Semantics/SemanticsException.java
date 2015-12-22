package Semantics;

import Tree.SyntaxTreeNode;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by Zaiyang on 18/12/2015.
 */
public class SemanticsException extends RuntimeException{
    public ParserRuleContext context;
    public SemanticsException(SyntaxTreeNode node, String message){
        super(message);
        context = node.getParserRuleContext();
    }
    public int getLine(){
        return context.getStart().getLine();
    }
    public int getCharPosition(){
        return context.getStart().getCharPositionInLine();
    }
    public String errorMessage(){
        StringBuilder sb = new StringBuilder();
        sb.append(getLine())
                .append(":")
                .append(getCharPosition())
                .append(" Error, " )
                .append(getMessage());
        return sb.toString();
    }
    public void printErrorMessage(){
        System.err.println(errorMessage());
    }
}
