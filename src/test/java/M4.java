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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class M4 {


    @Test
    void validMethodExpressionTestM4() {
        test("//FUNCTION CALL\n" +
                "class A {\n" +
                "    static public int f() {\n" +
                "        int x = 1;\n" +
                "        return x;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    int k = 5;\n" +
                "    int x = 5;\n" +
                "    int i = (k + x) - A.f(); //i = 9\n" +
                "    cout << i; //expect 9\n" +
                "}", false);
    }

    @Test
    void validDotExpressionTestM4() {
        test("//DOT CALL\n" +
                "class A {\n" +
                "    static public int i = 1;\n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    int k = 5;\n" +
                "    int x = 5;\n" +
                "    int i = (k + x) - A.i; //i = 9\n" +
                "    cout << i; //expect 9\n" +
                "}", false);
    }

    @Test
    void validNotExpressionTestM4() {
        test("//NOT\n" +
                "void main() {\n" +
                "    bool i; //0 is false for assembly\n" +
                "    i = !i; //set to one\n" +
                "    if(i)\n" +
                "        cout << \"true\"; //should print\n" +
                "}\n" +
                "\n", false);
    }

    @Test
    void validUnaryPlusExpressionTestM4() {
        test("//UnaryPLUS\n" +
                "void main() {\n" +
                "    char i = 'k';\n" +
                "    int j = +i; //convert to int\n" +
                "    cout << j; //should print int value of k 107\n" +
                "}", false);
    }

    @Test
    void validUnaryMinusExpressionTestM4() {
        test("//Unary SUBTRACT\n" +
                "void main() {\n" +
                "    int i = -10;\n" +
                "    int j = i + 9; \n" +
                "    cout << i; //should be -10\n" +
                "    cout << j; // should be -1\n" +
                "}", false);
    }

    @Test
    void validArithmicExpressionsTestM4() {
        test("//ARITHMIC Expressions\n" +
                "void main() {\n" +
                "    int i = 10;\n" +
                "    int j = 10;\n" +
                "    int r = i * j; //100\n" +
                "    cout << r; \n" +
                "     r = i / j; //1\n" +
                "    cout << r;\n" +
                "     r = i * j; //100\n" +
                "    cout << r;\n" +
                "     r = i + j; //20\n" +
                "    cout << r;\n" +
                "     r = i - j; //0\n" +
                "    cout << r;\n" +
                "    r = i * j / i + j - i; //(((i * j)/i) + j) - i -> 10\n" +
                "    cout << i; //should be 10\n" +
                "    cout << j; // should be 10\n" +
                "}", false);
    }

    @Test
    void validRelationalExpressionsTestM4() {
        test("//RELATIONAL Expression. print 8 trues\n" +
                "void main() {\n" +
                "\tint i = 10;\n" +
                "\tint j = -1;\n" +
                "\tint l = 10;\n" +
                "\tif(i > j)\n" +
                "\t\tcout << \"true\";\n" +
                "\tif (i >= l)\n" +
                "\t\tcout << \"true\";\n" +
                "\tif (i < j)\n" +
                "\t\tcout << \"don't print\";\n" +
                "\tif (i <= l)\n" +
                "\t\tcout << \"true\";\n" +
                "\tif(!(i < j))\n" +
                "\t\tcout << \"true\";\n" +
                "\tif(i != j)\n" +
                "\t\tcout << \"true\";\n" +
                "\tif(i != l)\n" +
                "\t\tcout << \"dont print\";\n" +
                "\tif(i > j && i == l)\n" +
                "\t\tcout << \"true\";\n" +
                "\tif(i > j || i == l) //short circut\n" +
                "\t\tcout << \"true\";\n" +
                "\tif(i <= j || i == l) // short circut\n" +
                "\t\tcout << \"dont print\";\n" +
                "\tif(i <= j || i == l) //not short circut\n" +
                "\t\tcout << \"true\";\n" +
                "}", false);
    }

    @Test
    void validArithmicAssignmentExpressionsTestM4() {
        test("//ARITHMIC ASSIGNMENT EXPRESSION\n" +
                "void main() {\n" +
                "\tint i = 10;\n" +
                "\tint j = 10;\n" +
                "\tint r = i += j *= j / 10; // j = 20, i = 30, r = i\n" +
                "\tcout << r; // print 30;\n" +
                "}", false);
    }

    @Test
    void validStaticFunctionCallsM4() {
        test("class Test {\n" +
                "    static public int testInt = 1;\n" +
                "    static private B b;\n" +
                "    \n" +
                "    static public int intF() {\n" +
                "        return testInt;\n" +
                "    }\n" +
                "\n" +
                "    static public B getB() {\n" +
                "        b.bInt += intF();\n" +
                "        return b;\n" +
                "    }\n" +
                "    \n" +
                "}\n" +
                "\n" +
                "class B {\n" +
                "    static public int bInt = 2;\n" +
                "    \n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    int test = Test.testInt; //1\n" +
                "    int f = Test.intF(); //1\n" +
                "    int b = Test.getB().bInt; //3\n" +
                "    cout << test;\n" +
                "    cout << f;\n" +
                "    cout << b;\n" +
                "}", false);
    }


    void test(String input, boolean hasErrors) {
        KxiParser parser = kxiParser(input);
        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
        parser.compilationUnit().accept(antlrToKxiVisitor);
        AbstractKxiNode rootNode = antlrToKxiVisitor.getRootNode();

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

        KxiSimplifyVisitor kxiSimplifyVisitor = new KxiSimplifyVisitor();
        rootNode.accept(kxiSimplifyVisitor);
        KxiToIntermediateVisitor kxiToIntermediateVisitor = new KxiToIntermediateVisitor(symbolTableVisitor.getScopeHandler());
        rootNode.accept(kxiToIntermediateVisitor);

        InterSymbolTableVisitor interSymbolTableVisitor =
                new InterSymbolTableVisitor(new InterSymbolTable(new HashMap<>(), new HashMap<>()), null);
        InterGlobal interGlobal = kxiToIntermediateVisitor.getRootNode();
        interGlobal.accept(interSymbolTableVisitor);

        InterToAssemblyVisitor interToAssemblyVisitor = new InterToAssemblyVisitor(new ArrayList<>()
                , null
                , interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor. getInterSymbolTable().getFunctionDataMap().get("main$main"));

        interGlobal.accept(interToAssemblyVisitor);

        AssemblyAssembleVisitor assemblyAssembleVisitor = new AssemblyAssembleVisitor();
        AssemblyMain assemblyMain = interToAssemblyVisitor.getRootNode();

        assemblyMain.accept(assemblyAssembleVisitor);

        if (hasErrors)
            assertTrue((symbolTableVisitor.hasErrors()
                    || typeCheckerVisitor.hasErrors()
                    || invalidWriteVisitor.hasErrors()
                    || invalidBreakVisitor.hasErrors())
                    && hasErrors);
        else
            assertFalse((symbolTableVisitor.hasErrors()
                    || typeCheckerVisitor.hasErrors()
                    || invalidWriteVisitor.hasErrors()
                    || invalidBreakVisitor.hasErrors())
                    || hasErrors);

        symbolTableVisitor.dumpErrorStack();
        typeCheckerVisitor.dumpErrorStack();
        invalidWriteVisitor.dumpErrorStack();
        invalidBreakVisitor.dumpErrorStack();

        writeAsm(assemblyAssembleVisitor);
        drawGraph(kxiToIntermediateVisitor);
    }

    void writeAsm(AssemblyAssembleVisitor assemblyAssembleVisitor) {
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

    void drawGraph(KxiToIntermediateVisitor kxiToIntermediateVisitor) {
        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(kxiToIntermediateVisitor.getRootNode());

        OutputHandler outputHandler = new OutputHandler("[inter.dot]");
        try {
            outputHandler.outputAST(graphVizVisitor.getGraph());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
