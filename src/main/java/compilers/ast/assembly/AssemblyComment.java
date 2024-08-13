package compilers.ast.assembly;

import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

@Getter
public class AssemblyComment extends AbstractAssembly {
    private String comment;
    public AssemblyComment(String comment) {
        this.comment = ";" + comment;
    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
