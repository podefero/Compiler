import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.KxiSimplifyVisitor;
import compilers.visitor.intermediate.KxiToIntermediateVisitor;
import compilers.visitor.kxi.invalid_break.InvalidBreakVisitor;
import compilers.visitor.kxi.invalid_write.InvalidWriteVisitor;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Stack;

public class GraphKxiSimpleTest {
    @Test
    void graphIntermediateViz() {
        AbstractKxiNode rootNode = kxiRootNode("simp.kxi");

        validateAST(rootNode);

        KxiSimplifyVisitor kxiSimplifyVisitor = new KxiSimplifyVisitor();
        rootNode.accept(kxiSimplifyVisitor);

//        validateAST(rootNode);

        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(rootNode);

        OutputHandler outputHandler = new OutputHandler("[simp.dot]");
        try {
            outputHandler.outputAST(graphVizVisitor.getGraph());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void validateAST(AbstractKxiNode rootNode) {
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        rootNode.accept(symbolTableVisitor);
        symbolTableVisitor.dumpErrorStack();

        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(symbolTableVisitor.getScopeHandler(), new Stack<>());
        rootNode.accept(typeCheckerVisitor);
        typeCheckerVisitor.dumpErrorStack();

        InvalidWriteVisitor invalidWriteVisitor = new InvalidWriteVisitor(new Stack<>(), symbolTableVisitor.getScopeHandler());
        rootNode.accept(invalidWriteVisitor);
        invalidWriteVisitor.dumpErrorStack();

        InvalidBreakVisitor invalidBreakVisitor = new InvalidBreakVisitor();
        rootNode.accept(invalidBreakVisitor);
        invalidBreakVisitor.dumpErrorStack();

    }

    @Test
    void testSymbolTableVisitorStressTest() {
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        kxiRootNode("StressTest.kxi").accept(symbolTableVisitor);
    }


    KxiParser kxiParser(String input) {
        CharStream charStream = CharStreams.fromString(input);
        KxiLexer kxiLexer = new KxiLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(kxiLexer);
        return new KxiParser(tokenStream);
    }

    KxiParser kxiParser(CharStream input) {
        KxiLexer kxiLexer = new KxiLexer(input);
        CommonTokenStream tokenStream = new CommonTokenStream(kxiLexer);
        return new KxiParser(tokenStream);
    }

    KxiParser.CompilationUnitContext compilationUnitContext(String classes, String mainBlock) {
        String main = classes + "void main(){" + mainBlock + "}";
        KxiParser parser = kxiParser(main);
        return parser.compilationUnit();
    }

    KxiParser.CompilationUnitContext compilationUnitContext(String file) {
        String filepath = getClass().getResource(file).getPath();
        CharStream charStream;
        charStream = new InputHandler('[' + filepath + ']').fileToCharStream();
        KxiParser parser = kxiParser(charStream);
        parser.getNumberOfSyntaxErrors();
        return parser.compilationUnit();
    }

    AbstractKxiNode kxiRootNode(String file) {
        KxiParser.CompilationUnitContext compilationUnitContext = compilationUnitContext(file);
        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
        compilationUnitContext.accept(antlrToKxiVisitor);
        return antlrToKxiVisitor.getRootNode();
    }

}
