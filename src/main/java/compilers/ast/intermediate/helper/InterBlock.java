package compilers.ast.intermediate.helper;

import compilers.ast.GenericListNode;
import compilers.ast.intermediate.AbstractInterNode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InterBlock extends AbstractInterNode {
   String name;
   GenericListNode interStatements;

}
