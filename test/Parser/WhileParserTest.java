package Parser;

import Parser.WhileLexer;
import Tree.BottomUpTreeWalker;
import Tree.CompilationUnit;
import Tree.SyntaxTreeBuilder;
import Tree.WriteStatement;
import junit.framework.TestCase;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Before;
import org.junit.Test;

import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.io.IOException;
import java.util.List;

public class WhileParserTest extends TestCase {
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

    @Before
    @Override
    public void setUp(){
        try {
            stream = new ANTLRFileStream("example\\test0.w");
            lexer = new WhileLexer (stream);
            parser = new WhileParser (new CommonTokenStream(lexer));
            parser.setBuildParseTree(false);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testLexer() throws IOException {
        List<? extends Token> tokens = lexer.getAllTokens();
        assertTrue("Number of tokens returned by lexer is greater than one", tokens.size() > 0);
    }

    @Test
    public void testParser(){
        parser = new WhileParser(new CommonTokenStream(lexer));
        ParseTree tree = parser.program();
        assertTrue("Parse Tree is not Null", tree != null);
    }

    @Test
    public void testExpressionTree() throws IOException {
        CharStream charSource = new ANTLRFileStream("example\\test0.w");
        CommonTokenStream stream = new CommonTokenStream(new WhileLexer(charSource));
        parser = new WhileParser(stream);
        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));
    }
    @Test
    public void testParseTest1() throws IOException {
        ParseTree tree = parseFile("example\\test1.w");
        System.out.println(tree.toStringTree(parser));
    }
    @Test
    public void testTreeBuilder() throws IOException {
        ParseTree tree = parseFile("example\\t.w");
        System.out.println(tree.toStringTree(parser));
        ParseTreeWalker walker = new ParseTreeWalker();
        SyntaxTreeBuilder builder = new SyntaxTreeBuilder();
        builder.build(tree);
        CompilationUnit unit = (CompilationUnit) builder.getRoot();
        return;

    }
    @Test
    public void testBottomUpTreeWalker() throws IOException {
        ParseTree tree = parseFile("example\\t.w");
        BottomUpTreeWalker walker = new BottomUpTreeWalker();
        System.out.println(tree.toStringTree(parser));
        WhileListener listener = new WhileBaseListener() {

        };
        walker.walk(listener,tree);

    }
    @Test
    public void testTreeBuilderOnRealProgram() throws IOException {
        ParseTree tree = parseFile("example\\test0.w");
        SyntaxTreeBuilder builder = new SyntaxTreeBuilder();
        System.out.println(tree.toStringTree(parser));
        builder.build(tree);

    }
    @Test
    public void testBooleanLiteral() throws IOException {
        ParseTree tree = parseString("write(true)");
    }
    @Test
    public void testArrayAccess() throws IOException {
        ParseTree tree = parseString("a:=array(4)");
    }

    public void testUnaryBooleanExp() throws IOException{
        ParseTree tree = parseString("write(!true)");
    }

}