package compilers.ast.assembly;

import lombok.Getter;

@Getter
public abstract class Operand extends AbstractAssembly {
    protected String value;
}
