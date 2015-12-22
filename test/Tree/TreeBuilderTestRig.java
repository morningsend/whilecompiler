package Tree;

import Parser.WhileLexer;
import Parser.WhileParser;
import junit.framework.TestCase;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.IOException;

/**
 * Created by Zaiyang on 16/12/2015.
 */
public class TreeBuilderTestRig extends TestCase {
    protected CharStream stream;
    protected WhileLexer lexer;
    protected WhileParser parser;
    protected CommonTokenStream tokenStream;

    public ParseTree parseString(String s){
        stream = new ANTLRInputStream(s);
        return parseStream();
    }
    public ParseTree parseFile(String filename) throws IOException {
        stream = new ANTLRFileStream(filename);
        return parseStream();
    }
    public ParseTree parseStream(){
        lexer = new WhileLexer(stream);
        tokenStream = new CommonTokenStream(lexer);
        parser = new WhileParser(tokenStream);
        return parser.program();
    }

    public CompilationUnit buildTree(ParseTree tree){
        SyntaxTreeBuilder builder = new SyntaxTreeBuilder();
        builder.build(tree);
        return (CompilationUnit) builder.getRoot();
    }
}
