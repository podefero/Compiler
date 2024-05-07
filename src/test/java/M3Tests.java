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
    void whileLoopInf() {
        test("void main() {\n" +
                "   ix = 10;\n" +
                "   while (x > 0) {\n" +
                "    x = x - 1;\n" +
                "    cout << x;\n" +
                "    if(x > 5) break;\n" +
                "   }\n" +
                "}", true);
    }

    @Test
    void instanceMemberStaticContext() {
        test("class MyClass {\n" +
                "    static public int staticVariable = 10;\n" +
                "    public int instanceVariable;\n" +
                "\n" +
                "    static public void staticMethod() {\n" +
                "        // Attempting to assign a value to an instance variable within a static method\n" +
                "        instanceVariable = 20; // This line will cause an error\n" +
                "    }\n" +
                "\n" +
                "    public void instanceMethod() {\n" +
                "        // This is a non-static method where you can assign instance variables\n" +
                "        instanceVariable = 30; // This is valid\n" +
                "    }\n" +
                "}\n" +
                "void main() {}", true);
    }

    @Test
    void writeToLiteral() {
        test("void main() {\n" +
                "    3 = 1;\n" +
                "}", true);
    }

    @Test
    void breakStatements() {
        test("void main() {\n" +
                "    break;\n" +
                "}\n", true);

        test("void main() {\n" +
                "    char c;\n" +
                "    int i;\n" +
                "    while(true) break;\n" +
                "    for(; false;) break;\n" +
                "    switch(c) {default: break;}\n" +
                "    switch(i) {default: break;}\n" +
                "}\n", false);

        test("void main() {\n" +
                "    char c;\n" +
                "    int i;\n" +
                "    while(true) break;\n" +
                "    for(; false;) break;\n" +
                "    switch(c) {default: break;}\n" +
                "    switch(i) {default: break;}\n" +
                "    break;\n" +
                "}\n", true);
    }

    @Test
    void arrayMemberAccess() {
        test("class Thing {\n" +
                "        public int x = 4;\n" +
                "    }\n" +
                "    void main() {\n" +
                "        Thing[] x = new Thing[1];\n" +
                "        x[1].x;\n" +
                "    }\n" +
                "    \"\"\",\n" +
                "    \"\"\"\n" +
                "    class Sauce {\n" +
                "        public int x = 4;\n" +
                "    }\n" +
                "    void main() {\n" +
                "        Sauce[][] x = new Sauce[][1];\n" +
                "        Sauce[] y = x[1];\n" +
                "        y[1].x = 4;\n" +
                "    }", false);
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
    void validUnaryOperatorsOne() {
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

    @Test
    void invalidAssignmentTypeCheckLHS() {
        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            Cheese x = 4;\n" +
                "        }", true);
    }

    @Test
    void invalidAssignmentTypeCheckRHS() {
        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            Cheese c = true;\n" +
                "        }", true);
    }

    @Test
    void invalidIfStatementType() {
        test(" void main() {\n" +
                "            if (null) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", true);
    }

    @Test
    void invalidRelationalOperators() {
        test(" void main() {\n" +
                "            if (1 < 'a') {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            if (1 > \"lol\") {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            if (true >= 2) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            if (1 <= null) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", true);
    }

    @Test
    void invalidLogicalOperators() {
        test(" void main() {\n" +
                "            if (1 < 2 && 'a') {\n" +
                "                return;\n" +
                "            }\n" +
                "        }", true);
    }

    @Test
    void invalidArithmeticOperators() {
        test(" void main() {\n" +
                "            1 + 'a';\n" +
                "        }", true);

        test(" void main() {\n" +
                "            null - null;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            1 * true;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            1 / null;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            1 += true;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            1 -= null;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            1 *= true;\n" +
                "        }", true);
    }

    @Test
    void invalidUnaryOperatorsOne() {
        test(" void main() {\n" +
                "            +true;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            +\"string\";\n" +
                "        }", true);

        test(" void main() {\n" +
                "            -true;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            -\"string\";\n" +
                "        }", true);

        test(" void main() {\n" +
                "            !1;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            !'a';\n" +
                "        }", true);

        test(" void main() {\n" +
                "            !null;\n" +
                "        }", true);
    }

    @Test
    void invalidWhileStatement() {
        test(" void main() {\n" +
                "            while (1) {\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            while ('a') {\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            while (null) {\n" +
                "            }\n" +
                "        }", true);

        test(" void main() {\n" +
                "            while (true && 1) {\n" +
                "            }\n" +
                "        }", true);
    }

    @Test
    void invalidForStatement() {
        test(" void main() {\n" +
                "            int i;\n" +
                "            for (i = 4; \"CHEEEESE\" ; i += 5) {}\n" +
                "        }", true);

        test(" void main() {\n" +
                "            int i;\n" +
                "            for (i = 4; null ;) {}\n" +
                "        }", true);

        test(" void main() {\n" +
                "            for (; null ;) {}\n" +
                "        }", true);
    }

    @Test
    void invalidCoutStatements() {
        test(" void main() {\n" +
                "            cout << true;\n" +
                "            cout << false;\n" +
                "        }", true);
    }

    @Test
    void invalidSwitchStatement() {
        test(" void main() {\n" +
                "            switch (true) {\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", true);

        test(" void main() {\n" +
                "            switch (null) {\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", true);

        test(" void main() {\n" +
                "            switch (\"string\") {\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", true);

        test(" void main() {\n" +
                "            int x;\n" +
                "            int y = 2;\n" +
                "            switch (y) {\n" +
                "                case 'a': \n" +
                "                    break;\n" +
                "                case 2:\n" +
                "                    break;\n" +
                "                case 3:\n" +
                "                    break;\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", true);
    }

    @Test
    void invalidFunctionReturnTypes() {
        test(" class Cheese {\n" +
                "            static public int wee() {\n" +
                "                return;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidMainReturnType() {
        test(" void main() {\n" +
                "            return 4;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            return 'a';\n" +
                "        }", true);

        test(" void main() {\n" +
                "            return \"string\";\n" +
                "        }", true);

        test(" void main() {\n" +
                "            return true;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            return null;\n" +
                "        }", true);
    }

    @Test
    void invalidArrayDeclarations() {
        test(" void main() {\n" +
                "            int[] x = new int[][5];\n" +
                "        }", true);

        test(" void main() {\n" +
                "            int[][][][][][] x = new int[][5];\n" +
                "        }", true);

        test(" void main() {\n" +
                "            char[][] x = new int[][5];\n" +
                "        }", true);
    }

    @Test
    void invalidReferenceTypes() {
        test(" void main() {\n" +
                "            int x = null;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            char x = null;\n" +
                "        }", true);
    }

    @Test
    void invalidIndexAccess() {
        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            int[] x = new int['a'];\n" +
                "        }", true);

        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            int[] x = new int[true];\n" +
                "        }", true);
    }

    @Test
    void invalidConstructorParams() {
        test(" class Cheese {\n" +
                "            Cheese(int x) {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese('a');\n" +
                "        }", true);
    }

    @Test
    void invalidFunctionCallParams() {
        test(" class Cheese {\n" +
                "            public void Func(int y, int x, char b) {\n" +
                "                return Func(y, x, 7);\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "        }", true);
    }

    @Test
    void invalidThisInStaticFunction() {
        test(" class Cheese {\n" +
                "            private int y = 2;\n" +
                "            static private int x = this.y;\n" +
                "            static private void failure() {\n" +
                "                this.x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", true);
    }

    @Test
    void invalidPrivateFunctionCall() {
        test(" class Cheese {\n" +
                "            private void Wee() {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "            c.Wee();\n" +
                "        }", true);
    }

    @Test
    void invalidVoidArrays() {
        test(" void main() {\n" +
                "            void x;\n" +
                "        }", true);

        test(" void main() {\n" +
                "            void[] x;\n" +
                "        }", true);
    }

    @Test
    void invalidConstructorReturnTypes() {
        test(" class Cheese {\n" +
                "            Cheese(int x, char y, string z) {\n" +
                "                return 4;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese(4, 'a', \"hello\");\n" +
                "        }", true);
    }

//    @Test
//    void invalidConstructorReturnType() {
//        test(" class Cheese {\n" +
//                "            private int x;\n" +
//                "            private char y;\n" +
//                "            private string z;\n" +
//                "            Cheese(int x, char y, string z) {\n" +
//                "                this.x = x;\n" +
//                "                this.y = y;\n" +
//                "                this.z = z;\n" +
//                "                return;\n" +
//                "            }\n" +
//                "        }\n" +
//                "        void main() {\n" +
//                "            Cheese c = new Cheese(4, 'a', \"hello\");\n" +
//                "        }", true);
//    }

    @Test
    void validIndexAccess() {
        test(" void main() {\n" +
                "            int[] x = new int[1200];\n" +
                "        }", false);
    }

    @Test
    void validVariableDeclaration() {
        test(" void main() {\n" +
                "     bool y = true;\n" +
                "}", false);

        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "        }", false);
    }

    @Test
    void validIfStatement() {
        test(" void main() {\n" +
                "            if (true) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validLogicalOperators() {
        test(" void main() {\n" +
                "            if (true && true) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validRelationalOperators() {
        test(" void main() {\n" +
                "            if (1 < 2) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            if (1 > 2) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            if (1 >= 2) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            if (1 <= 2) {\n" +
                "                int x = 5;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validLogicalOperatorsWithMultipleOperands() {
        test(" void main() {\n" +
                "            if (1 < 2 && 2 < 4) {\n" +
                "                return;\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            if (1 < 2 || 2 < 3002) {\n" +
                "                return;\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validArithmeticOperators() {
        test(" void main() {\n" +
                "            1 + 2;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            1 - 2;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            1 * 2;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            1 / 2;\n" +
                "        }", false);
    }

    @Test
    void validUnaryOperators() {
        test(" void main() {\n" +
                "            +'a';\n" +
                "        }", false);

        test(" void main() {\n" +
                "            -1;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            !true;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            !false;\n" +
                "        }", false);
    }

    @Test
    void validWhileStatement() {
        test(" void main() {\n" +
                "            while (true) {\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            while (3 < 4) {\n" +
                "            }\n" +
                "        }", false);

        test(" void main() {\n" +
                "            while (!(1 < 3) && false) {\n" +
                "            }\n" +
                "        }", false);
    }

    @Test
    void validForStatement() {
        test(" void main() {\n" +
                "            int i;\n" +
                "            for (i = 4; i < 10; i += 5) {}\n" +
                "        }", false);

        test(" void main() {\n" +
                "            for (; false ;) {}\n" +
                "        }", false);
    }

    @Test
    void validCoutStatements() {
        test(" void main() {\n" +
                "            cout << 5;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            cout << \"Cheese, really getting sick of all this cheese\";\n" +
                "        }", false);

        test(" void main() {\n" +
                "            cout << 'o';\n" +
                "        }", false);
    }

    @Test
    void validCinStatements() {
        test(" void main() {\n" +
                "            int x;\n" +
                "            cin >> x;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            char x;\n" +
                "            cin >> x;\n" +
                "        }", false);
    }

    @Test
    void validSwitchStatement() {
        test(" void main() {\n" +
                "            switch (4) {\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", false);

        test(" void main() {\n" +
                "            switch ('a') {\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", false);

        test(" void main() {\n" +
                "            int x;\n" +
                "            int y = 2;\n" +
                "            switch (y) {\n" +
                "                case 1: \n" +
                "                    break;\n" +
                "                case 2:\n" +
                "                    break;\n" +
                "                case 3:\n" +
                "                    break;\n" +
                "                default:\n" +
                "                    break;\n" +
                "            } \n" +
                "        }", false);
    }

    @Test
    void validFunctionReturnTypes() {
        test(" class Cheese {\n" +
                "            static public int Wee() {\n" +
                "                int x;\n" +
                "                return x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", false);

        test(" class Cheese {\n" +
                "            static public char Wee() {\n" +
                "                char x;\n" +
                "                return x;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", false);

        test(" class Cheese {\n" +
                "            static public void Wee() {\n" +
                "                return;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", false);

        test(" class Cheese {\n" +
                "            static public void Wee() {\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", false);
    }

    @Test
    void validMainReturnType() {
        test(" void main() {\n" +
                "            return;\n" +
                "        }", false);

        test(" void main() {\n" +
                "        }", false);
    }

    @Test
    void validArrayDeclarations() {
        test(" void main() {\n" +
                "            int[][] x = new int[][5];\n" +
                "        }", false);
    }

    @Test
    void validReferenceTypes() {
        test(" void main() {\n" +
                "            char[][] x = null;\n" +
                "        }", false);

        test(" void main() {\n" +
                "            string x = null;\n" +
                "        }", false);

        test(" class Cheese {}\n" +
                "        void main() {\n" +
                "            Cheese x = null;\n" +
                "        }", false);
    }

    @Test
    void validIndexAccessReturnsEnclosedType() {
        test(" void main() {\n" +
                "            char[][] y = new char[][3];\n" +
                "            char[] x = y[5];\n" +
                "        }", false);

        test(" void main() {\n" +
                "            char[] y = new char[3];\n" +
                "            char x = y[5];\n" +
                "        }", false);
    }

    @Test
    void validFunctionCalls() {
        test(" class Cheese {\n" +
                "            public int Motz() {\n" +
                "                return 4;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "            int x = c.Motz();\n" +
                "        }", false);

        test(" class Motz {\n" +
                "            public int Yeet() {\n" +
                "                return 4;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        class Cheese {\n" +
                "            private void Yeet() {\n" +
                "                Motz m = new Motz();\n" +
                "                int x = m.Yeet();\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {}", false);
    }

    @Test
    void validFunctionCallParams() {
        test(" class Cheese {\n" +
                "            public int Motz(int x, int y) {\n" +
                "                return x + y;\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "            int x = c.Motz(4, 5);\n" +
                "        }", false);
    }

    @Test
    void validCallMainInBlockScope() {
        test(" class Cheese {\n" +
                "            public void Motz() {\n" +
                "                main();\n" +
                "            }\n" +
                "        }\n" +
                "        void main() {\n" +
                "            main();\n" +
                "        }", false);
    }

    @Test
    void validChainedFunctionCalls() {
        test(" class Baz {\n" +
                "            public bool Foo(int y) {\n" +
                "                return true;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        class Motz {\n" +
                "            public Baz baz = new Baz();\n" +
                "            public Baz Cheeto() {\n" +
                "                return this.baz;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        void main() {\n" +
                "            Motz m = new Motz();\n" +
                "            bool continueVar = m.Cheeto().Foo(4);\n" +
                "        }", false);
    }

    @Test
    void validChainedMemberAccessFunctionCall() {
        test(" class Baz {\n" +
                "            public int x = 4;\n" +
                "        }\n" +
                "\n" +
                "        class Motz {\n" +
                "            public Baz baz = new Baz();\n" +
                "            public Baz Cheeto() {\n" +
                "                return this.baz;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        void main() {\n" +
                "            Motz m = new Motz();\n" +
                "            int continueVar = m.Cheeto().x;\n" +
                "        }", false);
    }

    @Test
    void validPrivateMemberAccessWithinClass() {
        test(" class Cheese {\n" +
                "    private int x;\n" +
                "        public int X() {\n" +
                "            this.x = 7;\n" +
                "            return this.x;\n" +
                "        }\n" +
                "    }\n" +
                "    void main() {}", false);
    }

    @Test
    void validConstructorParams() {
        test(" class Cheese {\n" +
                "            Cheese(int x) {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese(4);\n" +
                "        }", false);

        test(" class Cheese {\n" +
                "            Cheese(int x, char y) {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese(4, 'a');\n" +
                "        }", false);

        test(" class Cheese {\n" +
                "            Cheese(int x, char y, string z) {}\n" +
                "        }\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese(4, 'a', \"hello\");\n" +
                "        }", false);
    }

    @Test
    void validParamTypesForFunctionCalls() {
        test(" class Cheese {\n" +
                "            public int Func2(int x, int y, char b) {}\n" +
                "            public int Func(int y, int x, char b) {\n" +
                "                return Func2(y, x, b);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "        }", false);
    }

    @Test
    void validRecursiveFunctionCallsWithParams() {
        test(" class Cheese {\n" +
                "            public int Func(int y, int x, char b) {\n" +
                "                return Func(y, x, b);\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "        void main() {\n" +
                "            Cheese c = new Cheese();\n" +
                "        }", false);
    }

    @Test
    void validNullSubstitutionForReferenceTypeString() {
        test(" void main() {\n" +
                "            cout << null;\n" +
                "        }", false);
    }

    @Test
    void validSwitchCaseStatement() {
        test(" void main() {\n" +
                "            int n = 1;\n" +
                "            switch(n){\n" +
                "                case 1:\n" +
                "                    cout << \"one\";\n" +
                "                    break;\n" +
                "                case 2:\n" +
                "                case 3:\n" +
                "                    cout << \"two or three\";\n" +
                "                    break;\n" +
                "                default:\n" +
                "                    cout << \"a number not in the range of 1 to 3\";\n" +
                "                    cout << 1 + 2;\n" +
                "                    break;\n" +
                "                }\n" +
                "    }", false);
    }

    @Test
    void invalidStaticDataMemberInit() {
        test("class Foo{\n" +
                "    static public int a = b + c;\n" +
                "    public int b;\n" +
                "    public int c;\n" +
                "}\n" +
                "void main() {\n" +
                "\n" +
                "}", true);
    }

    @Test
    void validNullReturn() {
        test("class A {\n" +
                "    public int[] f() {\n" +
                "        return null;\n" +
                "    }\n" +
                "    public A b() {\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "void main() {\n" +
                "}", false);
    }

    @Test
    void invalidStaticDupIDAccess() {
        test("class A {\n" +
                "  static private int x;\n" +
                "  private int x;\n" +
                "  \n" +
                "    public int getX() {\n" +
                "        return x;\n" +
                "    }\n" +
                "}\n" +
                "void main() {\n" +
                "}", true);
    }

    @Test
    void invalidStaticMethodReturn() {
        test("class A {\n" +
                "  private int x;\n" +
                "  \n" +
                "    static public int getX() {\n" +
                "        return x;\n" +
                "    }\n" +
                "}\n" +
                "void main() {\n" +
                "}", true);
    }

    @Test
    void validNullAssignment() {
        test("class Dave{}\n" +
                "void main() {\n" +
                "    Dave dave = new Dave();\n" +
                "    dave == null;\n" +
                "}", false);
    }

    @Test
    void validStaticFunctionReturnStatic() {
        test("class Dave{\n" +
                "    static private int b;\n" +
                "    static public int getB() {\n" +
                "        return b;\n" +
                "    }\n" +
                "}\n" +
                "void main() {\n" +
                "  \n" +
                "}", false);
    }

    @Test
    void validReturnNewObjNNull() {
        test("class Dave{   \n" +
                "     public Dave getDave() {\n" +
                "        return new Dave();\n" +
                "    }\n" +
                "    \n" +
                "     public Dave getDaveNull() {\n" +
                "        return null;\n" +
                "    }\n" +
                "}\n" +
                "void main() {\n" +
                "  \n" +
                "}", false);
    }

//    @Test
//    void validSameNameVarDifIfBlock() {
//        test("void main() {\n" +
//                "   if(true) int i;\n" +
//                "   if(false) int i;\n" +
//                "}", false);
//    }

    @Test
    void invalidMethodExp() {
        test("void main() {\n" +
                "  5 + 5 ();\n" +
                "}\n", true);
    }
    @Test
    void validBoolFunctionAnd() {
        test("class A {\n" +
                "    static public bool f(){}\n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    bool a = A.f() && A.f();\n" +
                "}", false);
    }

    //TODO: PLACE THESE TEST IN M4 test when it's ready

    @Test
    void invalidMethodMissingParenthM4() {
        test("class A {\n" +
                "    static public int f() {\n" +
                "        int x = 1;\n" +
                "        return x;\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    int k = 5;\n" +
                "    int x = 5;\n" +
                "    int i = (k + x) - A.f; \n" +
                "    cout << i; //expect 9\n" +
                "}", true);
    }


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
                "    static public int i;\n" +
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
                "    cout << i; //should be -1\n" +
                "    cout << j; // should be 0\n" +
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
                "\tif(i < j && i == l) // short circut\n" +
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
