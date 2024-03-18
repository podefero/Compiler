package compilers.ast.kxi_nodes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class KxiModifierHelper extends AbstractKxiNode {
   private Modifier modifier;
}
