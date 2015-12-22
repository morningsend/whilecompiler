package Tree;

import org.antlr.v4.runtime.tree.*;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class BottomUpTreeWalker extends ParseTreeWalker{
    Stack<ParseTree> nodeStack;
    Set<ParseTree> childrenTraversed;
    @Override
    public void walk(ParseTreeListener listener, ParseTree parseTree){
        nodeStack = new Stack<ParseTree>();
        childrenTraversed = new HashSet<ParseTree>();
        nodeStack.add(parseTree);
        ParseTree currentNode;

        int childrenCount;
        while(!nodeStack.isEmpty()){
            currentNode = nodeStack.peek();
            childrenCount = currentNode.getChildCount();
            if(!childrenTraversed.contains(currentNode) && childrenCount > 0 ){
                for(int i = childrenCount-1; i>= 0; i--){
                    nodeStack.add(currentNode.getChild(i));
                }
                childrenTraversed.add(currentNode);
                currentNode = nodeStack.peek();
            }else {
                nodeStack.pop();
                if(currentNode instanceof TerminalNode){
                    listener.visitTerminal((TerminalNode) currentNode);
                }else if(currentNode instanceof ErrorNode){
                    listener.visitErrorNode((ErrorNode) currentNode);
                }else {
                    RuleNode rule = (RuleNode) currentNode;
                    this.enterRule(listener, rule);
                    this.exitRule(listener, rule);
                }
            }
        }

    }
}
