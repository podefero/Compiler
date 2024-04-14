package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import lombok.Getter;

@Getter

public class InterVariable extends InterStatement {
    private InterId interId;

    /**
     * InterVariable is placeholder for symbolTable, has no assembly instruction
     * @param interId written to symbol table
     */
    public InterVariable(InterId interId) {
        super(interId);
        this.interId = interId;
    }
}
