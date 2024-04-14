package compilers.ast.intermediate.statements;

import compilers.ast.intermediate.InterId;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter

public class InterVariable extends InterStatement {
    private InterId interId;
    private InterOperation interOperation;

    /**
     * InterVariable is placeholder for symbolTable, has no assembly instruction
     *
     * @param interId written to symbol table
     */
    public InterVariable(InterId interId, InterOperation interOperation) {
        super(interOperation, interId);
        this.interId = interId;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
