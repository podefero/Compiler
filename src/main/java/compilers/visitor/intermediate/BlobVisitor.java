package compilers.visitor.intermediate;

import compilers.ast.GenericNode;
import compilers.ast.intermediate.InterBlob;
import compilers.ast.kxi_nodes.AbstractKxiNode;
import compilers.ast.kxi_nodes.KxiMain;
import compilers.ast.kxi_nodes.statements.AbstractKxiStatement;
import compilers.ast.kxi_nodes.statements.conditional.KxiIfStatement;
import compilers.visitor.kxi.KxiVisitorBase;
import lombok.Getter;

import java.util.Stack;

@Getter
public class BlobVisitor extends KxiVisitorBase {
    Stack<InterBlob> blobs;
    InterBlob masterBlob;

    public BlobVisitor() {
        blobs = new Stack<>();
    }

    @Override
    public void visit(KxiMain kxiMain) {
        GenericNode[] blibs = new GenericNode[blobs.size()];
        masterBlob = new InterBlob(blobs.toArray(blibs));
    }



    @Override
    public void visit(KxiIfStatement node) {
        AbstractKxiNode resultExpression = node.getConditionalExpression();
        GenericNode expression = node.getChildren().remove(0);
        InterBlob interBlob = new InterBlob(node, resultExpression, expression);
        blobs.push(interBlob);
    }
}
