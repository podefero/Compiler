package compilers.ast.kxi_nodes.helper;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.Modifier;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class KxiFordSemi extends AbstractKxiNode {
    @Override
    public void accept(KxiVisitorBase visit) {
        visit.visit(this);
    }
}
