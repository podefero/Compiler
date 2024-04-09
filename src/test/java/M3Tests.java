import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.util.InputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.kxi.invalid_break.InvalidBreakVisitor;
import compilers.visitor.kxi.invalid_write.InvalidWriteVisitor;
import compilers.visitor.kxi.symboltable.SymbolTableVisitor;
import compilers.visitor.kxi.typecheck.TypeCheckerVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;


public class M3Tests {

    @Test
    void testSymbolTableVisitorStressTest() {
        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        kxiRootNode("StressTest.kxi").accept(symbolTableVisitor);
    }

    @Test
    void duplicateLocalVar() {
        test("void main() {\n" +
                "      int x = 5;\n" +
                "      int x = 2;\n" +
                "    };", true);
    }

    @Test
    void invalidIfBoolExpScope() {
        test(" void main() {\n" +
                "      if (x = 3) {\n" +
                "        int x = 5;\n" +
                "      }\n" +
                "    };", true);
    }

    @Test
    void invalidIfBoolExp() {
        test("void main() {\n" +
                "      int x = 3;\n" +
                "      if (x) {\n" +
                "        int b = 5;\n" +
                "      }\n" +
                "    }", true);
    }

    @Test
    void validIfBoolExp() {
        test("void main() {\n" +
                "      bool x = true;\n" +
                "      if (x) {\n" +
                "        int b = 5;\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void validIfConditional() {
        test("void main() {\n" +
                "      bool x = true;\n" +
                "      if (x && x || x && x) {\n" +
                "        int c = 5;\n" +
                "      }\n" +
                "      if (x || x) {\n" +
                "        int b = 5;\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void invalidIfAssignment() {
        test("void main() {\n" +
                "      bool x = true;\n" +
                "      if (x - x && x) {\n" +
                "        int b = 5;\n" +
                "      }\n" +
                "    }", true);
    }

    @Test
    void validMultipleConditional() {
        test("void main() {\n" +
                "      bool x = true;\n" +
                "      if (x && x || x && x) {\n" +
                "        int c = 5;\n" +
                "      }\n" +
                "      if (x || x) {\n" +
                "        int b = 5;\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void invalidIfWrongDatatypeComparison() {
        test("void main() {\n" +
                "  int x = 5;\n" +
                "  char y = 'y';\n" +
                "  if (x < 5 && y == 5) {\n" +
                "    cout << \"x is less than 5 and y\";\n" +
                "  }\n" +
                "}", true);
    }

    @Test
    void validIfDifferentDatatypes() {
        test("void main() {\n" +
                "  int x = 5;\n" +
                "  char y = 'y';\n" +
                "  if (x < 5 && y == 'a') {\n" +
                "    cout << \"x is less than 5 and y\";\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void breakOutsideLoop() {
        test("void main() {\n" +
                "      break;\n" +
                "    }", true);
    }

    @Test
    void validBreakInsideLoops() {
        test("void main() {\n" +
                "      int i;\n" +
                "      while(true) {\n" +
                "        break;\n" +
                "      }\n" +
                "      for (i = 0; i < 5; i += 1) {\n" +
                "        break;\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void validBreakInsideLoopsWithEmbeddedIf() {
        test("void main() {\n" +
                "        int i;\n" +
                "        while(true) {\n" +
                "          if (true) {\n" +
                "            break;\n" +
                "            }\n" +
                "            }\n" +
                "            for (i = 0; i < 5; i += 1) {\n" +
                "              if (true) { if (true) {\n" +
                "                break;\n" +
                "              }\n" +
                "                }\n" +
                "                }\n" +
                "                }", false);
    }

    @Test
    void invalidBreakInsideIfNotLoop() {
        test("void main() {\n" +
                "      if (true) {\n" +
                "        break;\n" +
                "      }\n" +
                "    }", true);
    }

    @Test
    void breakOutsideSwitch() {
        test("void main() {\n" +
                "        switch (x) {\n" +
                "          case 1:\n" +
                "            break;\n" +
                "          default:\n" +
                "            break;\n" +
                "        }\n" +
                "        break;\n" +
                "      }", true);
    }

    @Test
    void validBreakInsideSwitch() {
        test("void main() {\n" +
                "        switch (1) {\n" +
                "          case 1:\n" +
                "            break;\n" +
                "          default:\n" +
                "            break;\n" +
                "        }\n" +
                "      }", false);
    }

    @Test
    void invalidCinStringBool() {
        test("void main() {\n" +
                "        string x;\n" +
                "        cin >> x;\n" +
                "        bool y;\n" +
                "        cin >> y;\n" +
                "      }", true);
    }

    @Test
    void validCinIntChar() {
        test("void main() {\n" +
                "        int x;\n" +
                "        cin >> x;\n" +
                "        char y;\n" +
                "        cin >> y;\n" +
                "      }", false);
    }

    @Test
    void invalidCinExpressions() {
        test("void main() {\n" +
                "        int x;\n" +
                "        cin >> x + 5;\n" +
                "        cin >> x + 235134;\n" +
                "        cin >> x + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5;\n" +
                "      }", true);
    }

    @Test
    void invalidCinStatements() {
        test("void main() {\n" +
                "        int x;\n" +
                "         cin >> x + true;\n" +
                "        cin >> x + \"hello\";\n" +
                "        cin >> x + 'a';\n" +
                "        cin >> x + 5 + 5 + true;\n" +
                "        cin >> x + 5 + \"hello\";\n" +
                "      }", true);
    }

    @Test
    void validRelationalComparisonChars() {
        test("void main() {\n" +
                "      char x = 'a';\n" +
                "      char y = 'b';\n" +
                "      if (x < y) {\n" +
                "        cout << \"x is less than y\";\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void invalidRelationalComparisonDifferentTypes() {
        test("void main() {\n" +
                "      char x = 'a';\n" +
                "      int y = 5;\n" +
                "      if (x < y) {\n" +
                "        cout << \"x is less than y\";\n" +
                "      }\n" +
                "    }", true);
    }

    @Test
    void invalidUnaryOperators() {
        test("void main() {\n" +
                "      bool x = true;\n" +
                "      x = -x;\n" +
                "      int y = 5;\n" +
                "      y = !y;\n" +
                "    }", true);
    }

    @Test
    void validUnaryOperators() {
        test("void main() {\n" +
                "      int x = 5;\n" +
                "      x = -x;\n" +
                "      bool y = true;\n" +
                "      y = !y;\n" +
                "      char z = 'a';\n" +
                "      x = +z;\n" +
                "    }", false);
    }

    @Test
    void invalidNullAssignment() {
        test("void main() {\n" +
                "      int x = null;\n" +
                "    }", true);
    }

    @Test
    void validUnaryNotOperatorEquality() {
        test("void main() {\n" +
                "      int x = 5;\n" +
                "      bool y = true;\n" +
                "      if (!(x == 5)) {\n" +
                "        cout << \"x is not equal to 5\";\n" +
                "      }\n" +
                "      if (!(y == true)) {\n" +
                "        cout << \"y is not equal to true\";\n" +
                "      }\n" +
                "    }", false);
    }

    @Test
    void invalidUnaryNotOperatorNonBooleanOne() {
        test("void main() {\n" +
                "      int x = 5;\n" +
                "      if (!(x + 3)) {\n" +
                "        cout << \"x is not equal to 5\";\n" +
                "      }\n" +
                "    }", true);
    }

    @Test
    void typeCheckingAddCharToInt() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        char y = 'a';\n" +
                "        int z = x + y;\n" +
                "      }", true);
    }

    @Test
    void typeCheckingAddBoolToInt() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        bool y = true;\n" +
                "        int z = x + y;\n" +
                "      }", true);
    }

    @Test
    void typeCheckingAddInts() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        int y = 5;\n" +
                "        int z = x + y;\n" +
                "      }", false);
    }

    @Test
    void typeCheckingMultipleInts() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        int y = 5;\n" +
                "        int a = 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5;\n" +
                "        int b = x + y + x + y + a + a + x + y;\n" +
                "        int z = x + y + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5 + 5;\n" +
                "      }", false);
    }

    @Test
    void typeCheckingCompareInts() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        int y = 5;\n" +
                "        if (x < y) {\n" +
                "          cout << \"x is less than y\";\n" +
                "        }\n" +
                "      }", false);
    }

    @Test
    void typeCheckingCompareChars() {
        test("void main() {\n" +
                "        char x = 'a';\n" +
                "        char y = 'b';\n" +
                "        if (x < y) {\n" +
                "          cout << \"x is less than y\";\n" +
                "        }\n" +
                "      }", false);
    }

    @Test
    void typeCheckingCompareDifferentTypes() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        char y = 'a';\n" +
                "        string z = \"hello\";\n" +
                "        bool a = true;\n" +
                "        if (x < y) {\n" +
                "          cout << \"x is less than y\";\n" +
                "        }\n" +
                "        if (x < z) {\n" +
                "          cout << \"x is less than z\";\n" +
                "        }\n" +
                "        if (x < a) {\n" +
                "          cout << \"x is less than a\";\n" +
                "        }\n" +
                "        while (x < y) {\n" +
                "          cout << \"x is less than y\";\n" +
                "        }\n" +
                "      }", true);
    }

    @Test
    void validUnaryPlusOperatorChar() {
        test("void main() {\n" +
                "        char x = 'a';\n" +
                "        int y = +x;\n" +
                "      }", false);
    }

    @Test
    void invalidUnaryNotOperatorNonBooleanPart2() {
        test("void main() {\n" +
                "        int x = 5;\n" +
                "        bool y = !x;\n" +
                "        char z = 'a';\n" +
                "        bool a = !z;\n" +
                "        string b = \"hello\";\n" +
                "        bool c = !b;\n" +
                "      }", true);
    }

    @Test
    void validUnaryNotOperatorBooleanOne() {
        test("void main() {\n" +
                "        bool f = false;\n" +
                "        bool t = !f;\n" +
                "      }", false);
    }

    @Test
    void invalidUnaryMinusOperator() {
        test("void main() {\n" +
                "        char x = 'a';\n" +
                "        int y = -x;\n" +
                "        string z = \"hello\";\n" +
                "        int a = -z;\n" +
                "        bool b = true;\n" +
                "        int c = -b;\n" +
                "      }", true);
    }

    @Test
    void invalidConstructorVariableDeclaration() {
        test(" class Cheese {\n" +
                "            Cheese() {\n" +
                "                int Cheese;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {int x;}", true);
    }

    @Test
    void invalidClassReference() {
        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            Cheese c = new Lheese(1, 2, 3);\n" +
                "        }", true);
    }

    @Test
    void invalidGlobalVariable() {
        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            int Cheese;\n" +
                "        }", true);
    }

    @Test
    void invalidDuplicateConstructors() {
        test(" class Cheese{\n" +
                "            Cheese() {}\n" +
                "            Cheese() {}\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidDataMemberOverridingScope() {
        test(" class Cheese{\n" +
                "            static public int Cheese = 4;\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidDuplicateClassDeclarations() {
        test(" class Cheese{\n" +
                "            static public int cheese = 4;\n" +
                "        }\n" +
                "        class Cheese{\n" +
                "\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidFunctionOverrideClassName() {
        test(" class Cheese { \n" +
                "            public void Cheese() {\n" +
                "\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidDuplicateVariableDeclarations() {
        test(" void main() {\n" +
                "            int x = 5;\n" +
                "            int x = 2; \n" +
                "        }", true);
    }

    @Test
    void validConstructor() {
        test(" class Cheese{\n" +
                "            Cheese() {}\n" +
                "        }\n" +
                "        void main() {}", false);
    }

    @Test
    void validVariableDeclarationsWithinDifferentScopes() {
        test(" void main() {\n" +
                "            int x = 5;\n" +
                "            if (true) {\n" +
                "                int x = 2;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validThisKeywordAsDataMember() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            public int motz() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", false);
    }

    @Test
    void validThisKeywordInConstructor() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            Cheese() {\n" +
                "                this.x = 4;\n" +
                "                return;\n" +
                "            }\n" +
                "        } void main() {}", false);
    }

    @Test
    void validShadowingDeclarationNames() {
        test(" class Cheese {\n" +
                "            private int x;\n" +
                "            public void motz() {\n" +
                "                int x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            int x;\n" +
                "            while (true) {\n" +
                "                int x;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validRedeclaringMain() {
        test(" void main() {\n" +
                "            int main;\n" +
                "        }", false);
    }

    @Test
    void validDataMemberInitialization() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "        }    \n" +
                "        void main() {\n" +
                "           Cheese x = new Cheese(); \n" +
                "        }", false);
    }

    @Test
    void invalidUndeclaredVariableOne() {
        test(" void main() {\n" +
                "            int x = y;\n" +
                "        }", true);
    }

    @Test
    void invalidUndeclaredVariableAccessOne() {
        test(" void main() {\n" +
                "            x;\n" +
                "        }", true);
    }

    @Test
    void invalidDataMemberAccessOne() {
        test(" class Cheese {\n" +
                "            public int motz() {\n" +
                "                return x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDataMemberThisAccessOne() {
        test(" class Cheese {\n" +
                "            public int motz() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidScopeCheckOne() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            public int motz() {\n" +
                "                char inside_motz = 'a';\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            x;\n" +
                "        }", true);
    }

    @Test
    void invalidDuplicateConstructorsDoubleOne() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDuplicateConstructorsTripleOne() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDataMemberThisAccessShadowedOne() {
        test(" class Cheese {\n" +
                "        private int x;\n" +
                "            Cheese(char x) {\n" +
                "                this.x = x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidConstructorMismatchClassNameOne() {
        test(" class Cheese {\n" +
                "            private int x;\n" +
                "            Peese() {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            int x;\n" +
                "        }", true);
    }

    @Test
    void invalidLazyMatchingOne() {
        test(" class Foo {\n" +
                "            private int Cheese;\n" +
                "        }\n" +
                "        class Cheese{}\n" +
                "        void main(){}", true);
    }

    @Test
    void invalidUndeclaredVariable() {
        test(" void main() {\n" +
                "            int x = y;\n" +
                "        }", true);
    }

    @Test
    void invalidUndeclaredVariableAccess() {
        test(" void main() {\n" +
                "            x;\n" +
                "        }", true);
    }

    @Test
    void invalidDataMemberAccess() {
        test(" class Cheese {\n" +
                "            public int motz() {\n" +
                "                return x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDataMemberThisAccess() {
        test(" class Cheese {\n" +
                "            public int motz() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidScopeCheck() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            public int motz() {\n" +
                "                char inside_motz = 'a';\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            x;\n" +
                "        }", true);
    }

    @Test
    void invalidDuplicateConstructorsDouble() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDuplicateConstructorsTriple() {
        test(" class Cheese {\n" +
                "            public int x;\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "            Cheese() {\n" +
                "                return this.x;\n" +
                "            }\n" +
                "        } void main() {}", true);
    }

    @Test
    void invalidDataMemberThisAccessShadowed() {
        test(" class Cheese {\n" +
                "        private int x;\n" +
                "            Cheese(char x) {\n" +
                "                this.x = x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidConstructorMismatchClassName() {
        test(" class Cheese {\n" +
                "            private int x;\n" +
                "            Peese() {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            int x;\n" +
                "        }", true);
    }

    @Test
    void invalidLazyMatching() {
        test(" class Foo {\n" +
                "            private int Cheese;\n" +
                "        }\n" +
                "        class Cheese{}\n" +
                "        void main(){}", true);
    }

    // Additional test methods...

    @Test
    void invalidClassInstanceUndeclared() {
        test(" void main() {\n" +
                "            Cheese x = new Cheese();\n" +
                "        }", true);
    }

    @Test
    void invalidUnaryOperatorOnLiteral() {
        test(" void main() {\n" +
                "            int x = null;\n" +
                "        }", true);
    }

    @Test
    void invalidUnaryNotOperatorNonBoolean() {
        test(" void main() {\n" +
                "            int x = 5;\n" +
                "            bool y = !x;\n" +
                "            char z = 'a';\n" +
                "            bool a = !z;\n" +
                "            string b = \"hello\";\n" +
                "            bool c = !b;\n" +
                "        }", true);
    }

    @Test
    void validUnaryNotOperatorBoolean() {
        test(" void main() {\n" +
                "            bool f = false;\n" +
                "            bool t = !f;\n" +
                "        }", false);
    }

    @Test
    void invalidUnaryMinusOperatorOtherThanInt() {
        test(" void main() {\n" +
                "            char x = 'a';\n" +
                "            int y = -x;\n" +
                "            string z = \"hello\";\n" +
                "            int a = -z;\n" +
                "            bool b = true;\n" +
                "            int c = -b;\n" +
                "        }", true);
    }


    void test(String input, boolean hasErrors) {
        KxiParser parser = kxiParser(input);
        AntlrToKxiVisitor antlrToKxiVisitor = new AntlrToKxiVisitor();
        parser.compilationUnit().accept(antlrToKxiVisitor);
        AbstractKxiNode rootNode = antlrToKxiVisitor.getRootNode();

        SymbolTableVisitor symbolTableVisitor = new SymbolTableVisitor();
        rootNode.accept(symbolTableVisitor);


        TypeCheckerVisitor typeCheckerVisitor = new TypeCheckerVisitor(symbolTableVisitor.getScopeHandler(), new Stack<>());
        rootNode.accept(typeCheckerVisitor);

        InvalidWriteVisitor invalidWriteVisitor = new InvalidWriteVisitor(new Stack<>(), symbolTableVisitor.getScopeHandler());
        rootNode.accept(invalidWriteVisitor);

        InvalidBreakVisitor invalidBreakVisitor = new InvalidBreakVisitor();
        rootNode.accept(invalidBreakVisitor);

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
