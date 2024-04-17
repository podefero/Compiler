import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.assembly.AssemblyMain;
import compilers.ast.intermediate.InterGlobal;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.assembly.AssemblyAssembleVisitor;
import compilers.visitor.assembly.InterToAssemblyVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.InterSymbolTableVisitor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class GraphInterTest {
    @Test
    void stuff() {
        //boolean r = 'k' && 'k';
    }

    @Test
    void graphIntermediateViz() {
        AbstractKxiNode rootNode = kxiRootNode("inter.kxi");

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

        KxiSimplifyVisitor kxiSimplifyVisitor = new KxiSimplifyVisitor(new Stack<>());
        rootNode.accept(kxiSimplifyVisitor);
        KxiToIntermediateVisitor kxiToIntermediateVisitor = new KxiToIntermediateVisitor(symbolTableVisitor.getGlobalScope());
        rootNode.accept(kxiToIntermediateVisitor);

        InterSymbolTableVisitor interSymbolTableVisitor =
                new InterSymbolTableVisitor(new InterSymbolTable(new HashMap<>(), new HashMap<>()), null);
        InterGlobal interGlobal = kxiToIntermediateVisitor.getRootNode();
        interGlobal.accept(interSymbolTableVisitor);

        makeAssembly(interGlobal, interSymbolTableVisitor);


        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(kxiToIntermediateVisitor.getRootNode());

        OutputHandler outputHandler = new OutputHandler("[inter.dot,main.asm]");
        try {
            outputHandler.outputAST(graphVizVisitor.getGraph());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void makeAssembly(InterGlobal interGlobal, InterSymbolTableVisitor interSymbolTableVisitor) {
        InterToAssemblyVisitor interToAssemblyVisitor = new InterToAssemblyVisitor(new ArrayList<>()
                , null
                , interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main"));

        interGlobal.accept(interToAssemblyVisitor);

        AssemblyAssembleVisitor assemblyAssembleVisitor = new AssemblyAssembleVisitor(new ArrayList<>(), "");
        AssemblyMain assemblyMain = interToAssemblyVisitor.getRootNode();

        assemblyMain.accept(assemblyAssembleVisitor);

        String asm = "";
        for (String line : assemblyAssembleVisitor.getInstructions()) {
            // System.out.println(line);
            asm += line + "\n";
        }
        OutputHandler outputHandler = new OutputHandler("[main.asm]");
        try {
            outputHandler.outputAsm(asm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
