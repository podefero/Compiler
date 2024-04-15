package compilers.ast.assembly;

import compilers.ast.GenericListNode;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.List;

@Getter
public class AssemblyMain extends AbstractAssembly {
    List<AbstractAssembly> assemblyCodes;

    public AssemblyMain(GenericListNode codes) {
        super(codes);
        this.assemblyCodes = getNodeList(codes);

    }

    @Override
    public void accept(KxiVisitorBase visit) {
        visitChildren(visit);
        visit.visit(this);
    }
}
