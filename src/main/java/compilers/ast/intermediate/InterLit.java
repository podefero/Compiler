package compilers.ast.intermediate;

import lombok.Getter;

@Getter
public class InterLit<T> extends InterValue {
    private T value;

    public InterLit(T value) {
        this.value = value;
    }

}
