import Code.AbstractMachine;
import Code.CodeGen;
import Code.RegisterAllocator;
import Common.BracketPrettifier;
import Common.OptionParser;
import IR.ThreeAdressTransformer;
import Parser.WhileLexer;
import Parser.WhileParser;
import Semantics.ScopeResolver;
import Semantics.SemanticsException;
import Semantics.ThreeAddressTypeAnalyzer;
import Semantics.TreeAnalyzer;
import Tree.CompilationUnit;
import Tree.SyntaxTreeBuilder;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by Zaiyang on 19/12/2015.
 */
public class whilec {
    public static void main(String[] args){
        OptionParser optionParser = new OptionParser(args);
        String inFileName="", outFileName="";
        try{
            if(!optionParser.hasInputFileName()){
                System.err.println("No input file is specified.");
                displayUsage();
                System.exit(0);
            }else {
                inFileName = optionParser.getInputFileName();
            }
            if(optionParser.hasOutFileName()){
                outFileName = optionParser.getOutFileName();
            }else {
                outFileName = changeFileNameExtension(inFileName, "ass");
            }
            CharStream stream = new ANTLRFileStream(optionParser.getInputFileName());

            WhileLexer lexer = new WhileLexer(stream);

            TokenStream tokenStream = new CommonTokenStream(lexer);

            WhileParser parser = new WhileParser(tokenStream);

            parser.setErrorHandler(new BailErrorStrategy());

            ParseTree tree = parser.program();

            if(optionParser.isTreeFlagSet()){
                printParseTree(tree, parser);
            }

            SyntaxTreeBuilder builder = new SyntaxTreeBuilder();
            builder.build(tree);
            CompilationUnit unit = (CompilationUnit) builder.getRoot();

            TreeAnalyzer analyzer = new TreeAnalyzer(unit);

            analyzer.analyzerCorrectness();

            if(analyzer.hasErrors()){

                displayErrors( analyzer.getErrors());

                System.exit(0);
            }
            ScopeResolver scopeResolver = new ScopeResolver(unit);
            scopeResolver.resolve();
            if ( scopeResolver.hasErrors()){
                displayErrors(scopeResolver.getErrors());
            }

            ThreeAdressTransformer transformer = new ThreeAdressTransformer(unit);
            transformer.transform();
            unit = transformer.getCompilationUnit();


            ThreeAddressTypeAnalyzer typeAnalyzer = new ThreeAddressTypeAnalyzer(unit);
            typeAnalyzer.analyzeType();
            if(typeAnalyzer.hasErrors()){
                displayErrors(typeAnalyzer.getErrors());
                System.exit(0);
            }

            PrintStream out = new PrintStream(new FileOutputStream(outFileName));

            CodeGen codeGenerator = new CodeGen(out);

            codeGenerator.setStoreIntructionInBuffer(true);

            AbstractMachine machine = new AbstractMachine();
            RegisterAllocator allocator = new RegisterAllocator( unit);
            machine.setGenerator(codeGenerator);
            machine.setUnit(unit);
            machine.setRegisterAllocator(allocator);
            machine.execute();

            codeGenerator.optimizeRegisterAllocation();
            codeGenerator.writeInstructionBufferToStream();
        }catch (ParseCancellationException e){

        } catch (IOException e) {
            System.err.println("Can't read input file: " + optionParser.getInputFileName());
        } catch(RuntimeException e){
            System.err.println(e.getMessage());
        }
    }

    private static void displayErrors(List<SemanticsException> errors) {
        System.err.println("Error compiling ");
        for(SemanticsException e:errors){
            e.printErrorMessage();
        }
    }

    private static void displayUsage() {
        System.out.println("While language compiler, version 0.1");
        System.out.println("Developed by Zaiyang Li");
        System.out.println("Usage: java while [-tree] <filename> [-out <filename>] ");
        System.out.println("-tree option will display the parse tree");
        System.out.println("-out option allows the user to specify a filename to write the assembly to");
    }
    private static String changeFileNameExtension(String filename, String ext){
        int indexOfDot = filename.lastIndexOf('.');
        if(indexOfDot<0){
            return filename+"."+ext;
        }else {
            return filename.substring(0,indexOfDot)+"."+ext;
        }
    }
    private static void printParseTree(ParseTree tree, WhileParser parser){
        String printableTree = tree.toStringTree(parser);
        BracketPrettifier prettifier = new BracketPrettifier(printableTree);
        System.out.println(prettifier.prettify());
    }
}
