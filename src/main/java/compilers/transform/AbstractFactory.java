package compilers.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractFactory<Source, Destination> {
    public Destination build(Source ctx, Stack<Destination> stack) {
        return null;
    }

    public <T extends Destination> T pop(Stack<Destination> stack) {
        return (T) stack.pop();
    }

    public <T extends Destination> List<T> popList(Stack<Destination> stack, int size) {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(pop(stack));
        }
        return list;
    }
}
