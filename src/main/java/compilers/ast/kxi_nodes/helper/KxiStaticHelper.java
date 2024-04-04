package compilers.ast.kxi_nodes.helper;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KxiStaticHelper extends AbstractKxiNode {
   private boolean isStatic;
}
