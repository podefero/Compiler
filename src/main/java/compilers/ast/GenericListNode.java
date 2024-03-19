package compilers.ast;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
/**
 * Helper Class for GenericNode
 * Helps build the param list
 */
public class GenericListNode extends GenericNode {
    private List<GenericListNode> list;

}
