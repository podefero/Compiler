import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.util.InputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class TypeCheckTest {
    @Test
    void testTypeCheckVisitor() {
        AbstractKxiNode rootNode = kxiRootNode("testKXI.kxi");
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        rootNode.accept(symbolTableVisitor);
        symbolTableVisitor.dumpErrorStack();

        TypeCheckerVisitor typeCheckerVisitor =
                new TypeCheckerVisitor(symbolTableVisitor.getScopeHandler()
                        , new Stack<>(), symbolTableVisitor.getGlobalScope().getMainScope().getBlockScope());
        rootNode.accept(typeCheckerVisitor);
        typeCheckerVisitor.dumpErrorStack();
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
