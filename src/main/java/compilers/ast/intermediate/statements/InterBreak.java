package compilers.ast.intermediate.statements;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.expression.operation.InterOperation;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class InterBreak extends InterStatement {
   String exitLoop;

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
