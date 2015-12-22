package Tree;

import Model.Node;
import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by Zaiyang on 08/12/2015.
 */
public class SyntaxTreeNode extends Node{
    ParserRuleContext parserRuleContext;

    public ParserRuleContext getParserRuleContext() {
        return parserRuleContext;
    }

    public void setParserRuleContext(ParserRuleContext parserRuleContext) {
        this.parserRuleContext = parserRuleContext;
    }
}
