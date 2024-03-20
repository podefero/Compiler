package compilers.transform;

import compilers.ast.GenericListNode;
import compilers.ast.GenericNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * @param <Source>      AST we want to transform
 * @param <Destination> New AST
 */
public abstract class AbstractFactory<Source, Destination> {
    /**
     * Stack is used due to DFS traversal of AST
     * Used as helper for AbstractVisitorTransform
     *
     * @param ctx   from the ctx we can create a new node for new AST. New node pushed on stack
     * @param stack: some new nodes need more context
     * @return returns the new node.
     */
    public Destination build(Source ctx, Stack<Destination> stack) {
        return null;
    }

    public <T extends GenericNode> T pop(Stack<Destination> stack) {
        return (T) stack.pop();
    }

    public <T extends GenericNode> T popList(Stack<Destination> stack, int size) {
        List<T> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(pop(stack));
        }
        //Collections.reverse(list);
        GenericListNode genericListNode = new GenericListNode((List<GenericListNode>) list);

        return (T) genericListNode;
    }

    public <T extends Source> boolean objectIsNotNull(Source evaluate) {
        return evaluate != null;
    }

    public <T extends Source> int getListSizeFromCtx(List<T> ctx) {
        int size = 0;
        if (objectIsNotNull((T) ctx)) return ctx.size();
        return size;
    }
}
