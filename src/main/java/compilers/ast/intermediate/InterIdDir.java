package compilers.ast.intermediate;

import lombok.Getter;

@Getter
public class InterIdDir extends InterValue {
    private String id;

    public InterIdDir(String id) {
        this.id =  id;
    }

    public InterIdDir(int hash) {
        this.id =  "global$" + hash;
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
