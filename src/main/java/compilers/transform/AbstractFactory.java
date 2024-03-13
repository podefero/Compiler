package compilers.transform;

import lombok.AllArgsConstructor;

import java.util.Queue;

@AllArgsConstructor
public abstract class AbstractFactory<Source, Destination>  {
    protected Source ctx;
    protected Queue<Destination> queue;


    public Destination build() {
        return null;
    }
}
