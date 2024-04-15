package compilers.ast.assembly;

public abstract class Operand extends AbstractAssembly {

    public String intLiteral(int value) {
        String result = "#" + value;
        return result;
    }
}
