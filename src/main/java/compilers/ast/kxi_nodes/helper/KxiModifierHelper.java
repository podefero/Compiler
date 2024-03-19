package compilers.ast.kxi_nodes.helper;

import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.Modifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KxiModifierHelper extends AbstractKxiNode {
   private Modifier modifier;
}
