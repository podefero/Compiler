import compilers.antlr.KxiLexer;
import compilers.antlr.KxiParser;
import compilers.ast.GenericNode;
import compilers.ast.assembly.AssemblyMain;
import compilers.ast.intermediate.symboltable.InterSymbolTable;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.util.InputHandler;
import compilers.util.OutputHandler;
import compilers.visitor.antlr.AntlrToKxiVisitor;
import compilers.visitor.assembly.AssemblyAssembleVisitor;
import compilers.visitor.assembly.ExpressionToAssemblyVisitor;
import compilers.visitor.assembly.StatementsToAssemblyVisitor;
import compilers.visitor.generic.GraphVizVisitor;
import compilers.visitor.intermediate.*;
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
    void validUnarySimp4() {
        test("//UnaryPLUS\n" +
                "void main() {\n" +
                "    char i = 'k';\n" +
                "    cout << i; //should print int value of k 107\n" +
                "}", false);
    }

    @Test
    void simpleAddition() {
        test("//UnaryPLUS\n" +
                "void main() {\n" +
                "    int i = 1;\n" +
                "    i = i + 1; //convert to int\n" +
                "    cout << i; \n" +
                "}", false);
    }


    @Test
    void simpleArithmic() {
        test("void main() {\n" +
                "   int i;\n" +
                "   int j;\n" +
                "   i *= j += 1;\n" +
                "   i = i - j;\n" +
                "}\n", false);
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
                "\tcout << r; cout << i; cout << j; // print 30;\n" +
                "}", false);
    }

//    @Test
//    void validArithmicAssignmentExpressionsStaticTestM4() {
//        test("class Test {\n" +
//                "   static private int i = 10;\n" +
//                "   static private int j = 10;\n" +
//                "   static public int r = i += j *= j / 10;\n" +
//                "\n" +
//                "}\n" +
//                "void main() { cout << Test.r;\n" +
//                "\n" +
//                "}\n", false);
//    }

//    @Test
//    void validStaticFunctionCallsM4() {
//        test("class Test {\n" +
//                "    static public int testInt = 1;\n" +
//                "    static private B b;\n" +
//                "    \n" +
//                "    static public int intF() {\n" +
//                "        return testInt;\n" +
//                "    }\n" +
//                "\n" +
//                "    static public B getB() {\n" +
//                "        b.bInt += intF();\n" +
//                "        return b;\n" +
//                "    }\n" +
//                "    \n" +
//                "}\n" +
//                "\n" +
//                "class B {\n" +
//                "    static public int bInt = 2;\n" +
//                "    \n" +
//                "}\n" +
//                "\n" +
//                "void main() {\n" +
//                "    int test = Test.testInt; //1\n" +
//                "    int f = Test.intF(); //1\n" +
//                "    int b = Test.getB().bInt; //3\n" +
//                "    cout << test;\n" +
//                "    cout << f;\n" +
//                "    cout << b;\n" +
//                "}", false);
//    }

    @Test
    void cout12() {
        test("  class Cheddar {\n" +
                "        static public int y = 1;\n" +
                "        static public int x = x + y + 1;\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Cheddar.y;\n" +
                "        cout << Cheddar.x;\n" +
                "    }", false);
    }

    @Test
    void localFunctionVariable() {
        test(" class Beeze {\n" +
                "    static public void fun(){\n" +
                "        int x;\n" +
                "        cout << x;\n" +
                "        x = 14;// set x location on the stack\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    void main() {\n" +
                "        Beeze.fun();\n" +
                "        Beeze.fun();\n" +
                "    }", false);
    }

    @Test
    void coutFuncParamsArgs() {
        test(" class Cheese {\n" +
                "        static public void print(int x, int y) {\n" +
                "            cout << x;\n" +
                "            cout << y;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    void main() {\n" +
                "        Cheese.print(3, 4);\n" +
                "    }", false);
    }

    @Test
    void plusEqualInCoplexExpTwo() {
        test("  void main(){\n" +
                "        int c = 2;\n" +
                "        true && false || (c += 1) > 2;// false and true\n" +
                "        cout << c;\n" +
                "    }", false);

    }

    @Test
    void plusEqualInCoplexExp() {
        test(" void main(){\n" +
                "            int c = 2;\n" +
                "            c < 2 || (c += 5) < 2;\n" +
                "            cout << c;\n" +
                "            }", false);
    }


    @Test
    void fourfive() {
        test("  void main(){\n" +
                "        int n = 3;\n" +
                "        int t = 3;\n" +
                "        (n += 1) > 3 || (t += 2) > 3;\n" +
                "        cout << n;\n" +
                "        cout << t;\n" +
                "    }", false);
    }

    @Test
    void switchOne() {
        test("   void main(){\n" +
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
                "                    break;\n" +
                "                }\n" +
                "            }", false);
    }


    @Test
    void defaultSwitch() {
        test(" void main(){\n" +
                "        int n = 0;\n" +
                "        switch(n){\n" +
                "            case 1:\n" +
                "                cout << \"one\";\n" +
                "                break;\n" +
                "            case 2:\n" +
                "            case 3:\n" +
                "                cout << \"two or three\";\n" +
                "                break;\n" +
                "            default:\n" +
                "                cout << \"a number not in the range of 1 to 3\";\n" +
                "                break;\n" +
                "        }\n" +
                "    }", false);
    }


    @Test
    void whileLoop() {
        test("  void main() {\n" +
                "        int x = 10;\n" +
                "        while(x > 0) {\n" +
                "            cout << x;\n" +
                "            x = x - 1;\n" +
                "        }\n" +
                "    }", false);
    }

    @Test
    void forLoop() {
        test("void main() {\n" +
                "   int x = 0;\n" +
                "   for(x; x < 10; x = x +1) {\n" +
                "    cout << x;\n" +
                "   }\n" +
                "\n" +
                "}", false);
    }

    @Test
    void whileLoopTwo() {
        test("void main() {\n" +
                "   int x = 10;\n" +
                "   while (x > 0) {\n" +
                "    x = x - 1;\n" +
                "    cout << x;\n" +
                "    if(x < 5) break;\n" +
                "   }\n" +
                "}", false);
    }


    @Test
    void whileLoopInf() {
        test("void main() {\n" +
                "   int x = 10;\n" +
                "   while (x > 0) {\n" +
                "    x = x - 1;\n" +
                "    cout << x;\n" +
                "    if(x > 5) break;\n" +
                "   }\n" +
                "}", false);
    }




    @Test
    void staticMemberA() {
        test(" class Cheese {\n" +
                "        static public char y = 'a';\n" +
                "        static public char aye() {\n" +
                "            return y;\n" +
                "        }\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Cheese.aye();\n" +
                "    }", false);
    }

    @Test
    void staticMemberSeven() {
        test(" class Beez {\n" +
                "        static public int x = 4;\n" +
                "    }\n" +
                "    class Cheese {\n" +
                "        static public int y = 3;\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Beez.x + Cheese.y;\n" +
                "    }", false);
    }

    @Test
    void simpRecursive() {
        test("class Test {\n" +
                "    static public int f(int count) {\n" +
                "        if(count == 0)\n" +
                "            return count;\n" +
                "       return f(count - 1);\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "void main () {\n" +
                "    cout << Test.f(10);\n" +
                "}", false);
    }

    @Test
    void fib() {
        test("  //First 10 fibonacci numbers\n" +
                "    class Leonardo {\n" +
                "        static public int Fib(int n) {\n" +
                "            if (n <= 1) {\n" +
                "                return n;\n" +
                "            }\n" +
                "            return Fib(n - 1) + Fib(n - 2);\n" +
                "        }\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Leonardo.Fib(10);\n" +
                "    }", false);
    }

    @Test
    void staticVariableTest() {
        test(" //Test static member variables\n" +
                "    class Cheese {\n" +
                "        static public int y = 4;\n" +
                "        static public int x = Cheese.y + 1;\n" +
                "        static public int z = x + y;\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Cheese.z;\n" +
                "    }\n" +
                "    ---\n" +
                "    9", false);
    }

    @Test
    void staticVariableTestTwo() {
        test("  //Test member qualification\n" +
                "    class Cheese {\n" +
                "        static public int x = 90;\n" +
                "        static public int cheesey() {\n" +
                "            int x = 4;\n" +
                "            return x;\n" +
                "        }\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Cheese.cheesey();\n" +
                "    }\n" +
                "    ---\n" +
                "    4", false);
    }

    @Test
    void staticAccessMember() {
        test("  // Returning Classes and Accessing Members\n" +
                "    class Yoink{\n" +
                "        static public char me = 'z';\n" +
                "    }\n" +
                "    \n" +
                "    void main() {\n" +
                "        cout<< Yoink.me;\n" +
                "    }\n" +
                "    ---\n" +
                "    z", false);
    }

    @Test
    void danglingElse() {
        test("void main() {\n" +
                " if(true)\n" +
                "    if(true)\n" +
                "        if(false){}\n" +
                "            else{\n" +
                "            cout << 99;\n" +
                "        }\n" +
                "}", false);
    }


    @Test
    void ifElse() {
        test("void main() {\n" +
                " if(false) cout << \"true\";\n" +
                " else cout << \"false\";\n" +
                "}", false);
    }


    @Test
    void elseWithBlock() {
        test("  // Else Statement With Block\n" +
                "    void main() {\n" +
                "        bool test = true;\n" +
                "        int y = 1;\n" +
                "\n" +
                "        if(!test){\n" +
                "        cout<<\"Fail\";\n" +
                "        }\n" +
                "        else{\n" +
                "            y =5;\n" +
                "        }\n" +
                "        cout<< y;\n" +
                "    }\n" +
                "    ---\n" +
                "    5", false);
    }

    @Test
    void loveScopes() {
        test("  //We are Loving Scopes\n" +
                "    void main() {\n" +
                "        int y = 1;\n" +
                "        if( y < 5){\n" +
                "            bool y = false;\n" +
                "            {\n" +
                "                int y = 5;\n" +
                "                {\n" +
                "                    char y = 'a';\n" +
                "                    {\n" +
                "                        string y = \"Ayo\";\n" +
                "                        cout<< y;\n" +
                "                    }\n" +
                "                    cout<< y;\n" +
                "                }\n" +
                "                cout<< y;\n" +
                "            }\n" +
                "             if(y)\n" +
                "                cout<<\"Yoink\";  \n" +
                "            else\n" +
                "                cout<<\"Yeet\";\n" +
                "        }\n" +
                "         cout<< y;\n" +
                "    }\n" +
                "    ---\n" +
                "    Ayoa5Yeet1", false);
    }

    @Test
    void nestedBlocks() {
        test("void main() {\n" +
                "    int y = 1;\n" +
                "    if (y < 10) {\n" +
                "        {\n" +
                "            char y = 'a';\n" +
                "            {\n" +
                "                string y = \"hello\";\n" +
                "                cout << y;\n" +
                "            }\n" +
                "            cout << y;\n" +
                "        }\n" +
                "        cout << y;\n" +
                "    }\n" +
                "}", false);
    }

    @Test
    void nestedFunction() {
        test("  // Nested Reference to Class Function outside of Class Scope\n" +
                "       class B {\n" +
                "\n" +
                "        static public int gimmeInt(){\n" +
                "            return 15;\n" +
                "        }\n" +
                "        static public void eatingInt(int x){\n" +
                "            cout<< x;\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    void main() {\n" +
                "        B.eatingInt(B.gimmeInt());\n" +
                "    }\n" +
                "    ---\n" +
                "    15", false);
    }

    //TODO for post exp happen after loop
    @Test
    void ForNestedWhile() {
        test("   // For Loop nested in While Loop\n" +
                "    void main() {\n" +
                "        int x = 10;\n" +
                "        while(x > 0){\n" +
                "            cout<<\"Ready\";\n" +
                "            for(;x > 0;x -=1){\n" +
                "                cout<<x;\n" +
                "            }\n" +
                "            cout<< \"Stop\";\n" +
                "        }\n" +
                "        }\n" +
                "    ---\n" +
                "    Ready10987654321Stop", false);
    }

    @Test
    void whileLoopFor() {
        test("void main() {\n" +
                "    int x = 10;\n" +
                "    while(x > 0){\n" +
                "        x-=1;\n" +
                "    }\n" +
                " \n" +
                "}\n", false);
    }


    @Test
    void callMaininMain() {
        test("  // Make sure that you can call main within main\n" +
                "    class B{\n" +
                "        static public int counter= 5;\n" +
                "    }\n" +
                "\n" +
                "    void main(){\n" +
                "        int c = 5;\n" +
                "        if(B.counter > 0){\n" +
                "            B.counter -=1;\n" +
                "            cout<< B.counter;\n" +
                "                main();\n" +
                "        }\n" +
                "        cout<< c;\n" +
                "        cout<<\"End\";\n" +
                "    }\n" +
                "    ---\n" +
                "    432105End5End5End5End5End5End", false);
    }

    @Test
    void doubleParamRecur() {
        test(" // Testing Recursion but with two Parameters in case your code works for just one\n" +
                "    class  B {\n" +
                "        static public int doubleRecursion(int y, int r){\n" +
                "\n" +
                "            if(y < 1 || r < 1){\n" +
                "                return y;\n" +
                "            }\n" +
                "            else{\n" +
                "                cout<< y;\n" +
                "                cout<< r;\n" +
                "                doubleRecursion(y-1, r-1);\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "    \n" +
                "    void main(){\n" +
                "        B.doubleRecursion(8,9);\n" +
                "    }\n" +
                "    ---\n" +
                "    8978675645342312", false);
    }

    @Test
    void recusionParams() {
        test(" // Testing Recursion when using local variables as well as params;\n" +
                "    class  B {\n" +
                "        static public int doubleRecursion(int y, int r){\n" +
                "            int c;\n" +
                "            c = 1;\n" +
                "            if(y < 1 || r < 1){\n" +
                "                return y;\n" +
                "            }\n" +
                "            else{\n" +
                "                cout<< y;\n" +
                "                cout<< r;\n" +
                "                doubleRecursion(y-c, r-c);\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "        \n" +
                "    void main(){\n" +
                "        B.doubleRecursion(8,9);\n" +
                "    }\n" +
                "    ---\n" +
                "    8978675645342312", false);
    }

    //TODO fix
    @Test
    void ackerman() {
        test("  class Ackerman {\n" +
                "        static public int ackerman(int m, int n) {\n" +
                "            if (m == 0) {\n" +
                "                return n +1;\n" +
                "            } else if (m > 0 && n == 0) {\n" +
                "                return ackerman(m - 1, 1);\n" +
                "            } else {\n" +
                "                return ackerman(m - 1, ackerman(m, n - 1));\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "    void main() {\n" +
                "        cout << Ackerman.ackerman(1, 1);\n" +
                "    }\n" +
                "    ---\n" +
                "    125", false);
    }

    @Test
    void ackermanSub() {
        test("class Ack {\n" +
                "    static public int ack(int m, int n) {\n" +
                "        if(m==0) {\n" +
                "            return n + 1;\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "void main() {\n" +
                "    cout << Ack.ack(3, 4);\n" +
                "}\n -- 5", false);
    }

    @Test
    void javaAck() {
        System.out.println(testAck(1, 1));

    }

    int testAck(int m, int n) {
        if (m == 0) {
            return n + 1;
        } else if (m > 0 && n == 0) {
            return testAck(m - 1, 1);
        } else {
            return testAck(m - 1, testAck(m, n - 1));
        }
    }

    @Test
    void mainRetEarly() {
        test("     //Testing early return from main\n" +
                "    void main(){\n" +
                "\n" +
                "        cout<< \"This Prints\";\n" +
                "        return;\n" +
                "        cout<<\"This doesn't print\";\n" +
                "    }\n" +
                "    ---\n" +
                "    This Prints", false);
    }

    @Test
    void cinTest() {
        test("void main() {\n" +
                "    int x;\n" +
                "    cin >> x;\n" +
                "    cout << x; //Ensure the value that prints is the same as your input\n" +
                "}", false);
    }

    @Test
    void cinTestTwo() {
        test("void main() {\n" +
                "    char x;\n" +
                "    cin >> x;\n" +
                "    cout << x; //Ensure the value that prints is the same as your input\n" +
                "}", false);
    }

    @Test
    void ifElseTest() {
        test("void main() {\n" +
                "    int m = 0;\n" +
                "    int n = 0;\n" +
                "    if( m == 0) {\n" +
                "        cout << n += 1;\n" +
                "    } else if (m > 0 && n == 0) {\n" +
                "        cout << m -=1;\n" +
                "    } else {\n" +
                "        cout << n;\n" +
                "    }\n" +
                "}", false);
    }

    @Test
    void ifElseTestTwo() {
        test("void main() {\n" +
                "    int m = 1;\n" +
                "    int n = 0;\n" +
                "    if( m == 0) {\n" +
                "        cout << n += 1;\n" +
                "    } else if (m > 0 && n == 0) {\n" +
                "        cout << m -=1;\n" +
                "    } else {\n" +
                "        cout << n;\n" +
                "    }\n" +
                "}", false);
    }

    @Test
    void ifElseTestThree() {
        test("void main() {\n" +
                "    int m = 1;\n" +
                "    int n = 5;\n" +
                "    if( m == 0) {\n" +
                "        cout << n += 1;\n" +
                "    } else if (m > 0 && n == 0) {\n" +
                "        cout << m -=1;\n" +
                "    } else {\n" +
                "        cout << n;\n" +
                "    }\n" +
                "}", false);
    }

    @Test
    void binaryArithAssign() {
        test("void main() {\n" +
                "  int m = 10;\n" +
                "  int n = 10;\n" +
                "  cout << m-=1;\n" +
                "  cout << n+=1;\n" +
                "}", false);
    }

    @Test
    void cMinusTestProg() {
        test("void main() {\n" +
                "    // Test for loop\n" +
                "    int i = 0;\n" +
                "    for (i; i < 5; i+=1) {\n" +
                "        cout << \"For loop iteration: \";\n" +
                "        cout << i;\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "\n" +
                "    // Test while loop\n" +
                "    int j = 0;\n" +
                "    while (j < 5) {\n" +
                "        cout << \"While loop iteration: \";\n" +
                "        cout << j;\n" +
                "        cout << '\\n';\n" +
                "        j+=1;\n" +
                "    }\n" +
                "\n" +
                "    // Test if statement with logical expressions\n" +
                "    int a = 5;\n" +
                "    int b = 10;\n" +
                "    if (a < b && b > 0) {\n" +
                "        cout << \"If statement with logical AND: true\";\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "    if (a > b || b > 0) {\n" +
                "        cout << \"If statement with logical OR: true\";\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "\n" +
                "    // Test switch statement\n" +
                "    int choice = 2;\n" +
                "    switch (choice) {\n" +
                "        case 1:\n" +
                "            cout << \"Switch case 1\";\n" +
                "            cout << '\\n';\n" +
                "            break;\n" +
                "        case 2:\n" +
                "            cout << \"Switch case 2\";\n" +
                "            cout << '\\n';\n" +
                "            break;\n" +
                "        default:\n" +
                "            cout << \"Default case\";\n" +
                "            cout << '\\n';\n" +
                "    }\n" +
                "\n" +
                "    // Test data types\n" +
                "    int intValue = 10;\n" +
                "    char charValue = 'a';\n" +
                "    string stringValue = \"Hello, World!\";\n" +
                "\n" +
                "    cout << \"Integer value: \"; \n" +
                "    cout << intValue;\n" +
                "    cout << '\\n';\n" +
                "    cout << \"Character value: \"; \n" +
                "    cout << charValue; \n" +
                "    cout << '\\n';\n" +
                "    cout << \"String value: \"; \n" +
                "    cout << stringValue; \n" +
                "    cout << '\\n';\n" +
                "\n" +
                "    // Test nested loops and blocks\n" +
                "    int x = 0;\n" +
                "    for (x; x < 3; x+=1) {\n" +
                "        cout << \"Outer loop iteration: \";\n" +
                "        cout << x;\n" +
                "        cout << '\\n';\n" +
                "        int y = 0;\n" +
                "        for (y; y < 2; y+=1) {\n" +
                "            cout << \"Inner loop iteration: \";\n" +
                "            cout << y; \n" +
                "            cout << '\\n';\n" +
                "        }\n" +
                "    }\n" +
                "}\n", false);
    }

    @Test
    void forLoopTest() {
        test("void main() {\n" +
                "    // Test for loop\n" +
                "    int i = 5;\n" +
                "    for (i; i < 5; i-=1) {\n" +
                "        cout << \"For loop iteration: \";\n" +
                "        cout << i;\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "    }\n", false);
    }

    @Test
    void logicalExp() {
        test("void main() {\n" +
                "     // Test if statement with logical expressions\n" +
                "    int a = 5;\n" +
                "    int b = 10;\n" +
                "    if (a < b && b > 0) {\n" +
                "        cout << \"If statement with logical AND: true\";\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "    if (a > b || b > 0) {\n" +
                "        cout << \"If statement with logical OR: true\";\n" +
                "        cout << '\\n';\n" +
                "    }\n" +
                "}\n", false);
    }

    @Test
    void testSwitchStatement() {
        test("void main() {\n" +
                "    // Test switch statement\n" +
                "    int choice = 2;\n" +
                "    switch (choice) {\n" +
                "        case 1:\n" +
                "            cout << \"Switch case 1\";\n" +
                "            cout << '\\n';\n" +
                "            break;\n" +
                "        case 2:\n" +
                "            cout << \"Switch case 2\";\n" +
                "            cout << '\\n';\n" +
                "            break;\n" +
                "        default:\n" +
                "            cout << \"Default case\";\n" +
                "            cout << '\\n';\n" +
                "    }\n" +
                "}\n", false);
    }

    @Test
    void dataTypes() {
        test("void main() {\n" +
                "    // Test data types\n" +
                "    int intValue = 10;\n" +
                "    char charValue = 'a';\n" +
                "    string stringValue = \"Hello, World!\";\n" +
                "\n" +
                "    cout << \"Integer value: \";\n" +
                "    cout << intValue;\n" +
                "    cout << '\\n';\n" +
                "    cout << \"Character value: \";\n" +
                "    cout << charValue;\n" +
                "    cout << '\\n';\n" +
                "    cout << \"String value: \";\n" +
                "    cout << stringValue;\n" +
                "    cout << '\\n';\n" +
                "}\n", false);
    }

    @Test
    void nestedLoops() {
        test("void main() {\n" +
                "   // Test nested loops and blocks\n" +
                "    int x = 0;\n" +
                "    for (x; x < 3; x+=1) {\n" +
                "        cout << \"Outer loop iteration: \";\n" +
                "        cout << x;\n" +
                "        cout << '\\n';\n" +
                "        int y = 0;\n" +
                "        for (y; y < 2; y+=1) {\n" +
                "            cout << \"Inner loop iteration: \";\n" +
                "            cout << y;\n" +
                "            cout << '\\n';\n" +
                "        }\n" +
                "    }\n" +
                "}\n", false);
    }

    @Test
    void forLoopExpression() {
        test("void main() {\n" +
                "  // Test nested for loops\n" +
                "    int i;\n" +
                "    for (i; i < 3; i = i + 1) {\n" +
                "        cout << i;\n" +
                "    }\n" +
                "}\n", false);
    }

    @Test
    void helloWorld() {
        test("void main() {\n" +
                "    cout << \"hello world\";\n" +
                "}", false);
    }

    @Test
    void helloWorldTwo() {
        test("void main() {\n" +
                "    string s = \"helloworld\";\n" +
                "    cout << s;\n" +
                "}", false);
    }


    @Test
    void simpVariable() {
        test("void main() {\n" +
                "   int i = 1 + 1 + 3;\n" +
                "}\n", false);
    }

    @Test
    void simpVariableTwo() {
        test("void main() {\n" +
                "   int i = 1;\n" +
                "   i += 1; cout << i;\n" +
                "}\n", false);
    }

    @Test
    void simpVariableThree() {
        test("void main() {\n" +
                "   int i = 1;\n" +
                "   cout << i += 1;\n" +
                "}\n", false);
    }


    @Test
    void simpIfTest() {
        test("void main() {\n" +
                "  bool b = false;\n" +
                "  if(true) cout << 99;\n" +
                "}\n", false);
    }

    @Test
    void simpIfTestTwo() {
        test("void main() {\n" +
                "  bool b = false;\n" +
                "  if(false) cout << 99;\n" +
                "}\n", false);
    }

    @Test
    void simpleITestThree() {
        test("void main() {\n" +
                "    if(false)\n" +
                "        cout << 99;\n" +
                "    else\n" +
                "        cout << 33;\n" +
                "}", false);
    }

    @Test
    void simpleIfTestFour() {
        test("void main() {\n" +
                "    if(true)\n" +
                "        cout << 99;\n" +
                "    else\n" +
                "        cout << 33;\n" +
                "}", false);
    }

    @Test
    void simpleIfTestBlock() {
        test("void main() {\n" +
                "  if(true) {\n" +
                "    cout << 99;\n" +
                "  } else {\n" +
                "    cout << 33;\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void simpleIfTestBlockTwo() {
        test("void main() {\n" +
                "  if(false) {\n" +
                "    cout << 99;\n" +
                "  } else {\n" +
                "    cout << 33;\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void simpleIfTestBlockThree() {
        test("void main() {\n" +
                "  if(false) {\n" +
                "    cout << 55 + 44;\n" +
                "  } else {\n" +
                "    cout << 11 + 22;\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void simpleIfTestBlockFour() {
        test("void main() {\n" +
                "  if(true) {\n" +
                "    cout << 55 + 44;\n" +
                "  } else {\n" +
                "    cout << 11 + 22;\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void simpleIfTestBlockVar() {
        test("void main() {\n" +
                "\n" +
                "  bool b = false;\n" +
                "  if(b) {\n" +
                "    cout << 99;\n" +
                "  } else {\n" +
                "    cout << 33;\n" +
                "  }\n" +
                "}", false);
    }

    @Test
    void simplePlusEqual() {
        test("void main() {\n" +
                "   int x = 0;\n" +
                "   x += 1;\n" +
                "   cout << x;\n" +
                "}", false);
    }
    @Test
    void simplePlusEqualTwo() {
        test("void main() {\n" +
                "   int x = 2;\n" +
                "   x += x;\n" +
                "   cout << x;\n" +
                "}", false);
    }
    @Test
    void simplePlusEqualThree() {
        test("void main() {\n" +
                "   int x = 2;\n" +
                "   x += x += x;\n" +
                "   cout << x;\n" +
                "}", false);
    }


    //    @Test
//    void imSpacedOut() {
//        test("", false);
//    }
    //    @Test
//    void imSpacedOut() {
//        test("", false);
//    }
    //    @Test
//    void imSpacedOut() {
//        test("", false);
//    }
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

        BreakAndReturnsVisitor breakAndReturnsVisitor = new BreakAndReturnsVisitor();
        rootNode.accept(breakAndReturnsVisitor);

        rootNode.accept(new FullyLoadedIdVisitor(symbolTableVisitor.getScopeHandler()));


        ExpressionToTempVisitor expressionToTempVisitor = new ExpressionToTempVisitor();
        rootNode.accept(expressionToTempVisitor);

        drawGraph(rootNode);

        InterSymbolTableVisitor interSymbolTableVisitor =
                new InterSymbolTableVisitor(new InterSymbolTable(new HashMap<>(), new HashMap<>()), null, expressionToTempVisitor.tempVars);

//        InterGlobal interGlobal = intermediateFinalizeVisitor.getInterGlobal();
        rootNode.accept(interSymbolTableVisitor);

        ExpressionToAssemblyVisitor expressionToAssemblyVisitor = new ExpressionToAssemblyVisitor(interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main"));

        rootNode.accept(expressionToAssemblyVisitor);

        StatementsToAssemblyVisitor statementsToAssemblyVisitor = new StatementsToAssemblyVisitor(new ArrayList<>(), new ArrayList<>()
                , interSymbolTableVisitor.getInterSymbolTable()
                , interSymbolTableVisitor.getInterSymbolTable().getFunctionDataMap().get("main$main")
                , null);

        rootNode.accept(statementsToAssemblyVisitor);

        AssemblyAssembleVisitor assemblyAssembleVisitor = new AssemblyAssembleVisitor();
        AssemblyMain assemblyMain = statementsToAssemblyVisitor.getRootNode();

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

    void drawGraph(GenericNode interGlobal) {
        GraphVizVisitor graphVizVisitor = new GraphVizVisitor(interGlobal);

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
