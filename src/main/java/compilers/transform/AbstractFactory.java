package compilers.transform;

import lombok.AllArgsConstructor;

import java.util.Queue;

public abstract class AbstractFactory<Source, Destination>  {
    public Destination build(Source ctx, Queue<Destination> queue ) {
        return null;
    }
}
