package compilers.ast.intermediate;

import lombok.Getter;

@Getter
public class InterId extends InterValue {
    private String id;

    public InterId(String id) {
        this.id =  id;
    }

    public InterId(int hash) {
        this.id =  "temp$" + hash;
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
