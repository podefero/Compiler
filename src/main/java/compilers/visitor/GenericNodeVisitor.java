package compilers.visitor;

import compilers.ast.GenericNode;

public interface GenericNodeVisitor {
    void visit(GenericNode genericNode);
}
