package compilers.transform;

import java.util.List;
import java.util.Stack;

public abstract class AbstractFactory<Source, Destination>  {
    public Destination build(Source ctx, Stack<Destination> stack) {
        return null;
    }
    public <T extends Destination> T pop(Stack<Destination> stack){
        return (T) stack.pop();
    };

    public <T extends Destination> List<T> popList(Stack<Destination> stack, Class type){
        List<T> list = null;
        while(stack.peek().getClass() ==  type) {
            list.add(pop(stack));
        }
        return list;
    }
}
