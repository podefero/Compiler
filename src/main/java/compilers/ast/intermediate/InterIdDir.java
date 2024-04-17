package compilers.ast.intermediate;

import compilers.ast.assembly.Directive;
import lombok.Getter;

@Getter
public class InterIdDir extends InterValue {
    private String id;
    Directive directive;

    public InterIdDir(String id, Directive directive) {
        this.directive = directive;
        this.id =  id;
    }

    public InterIdDir(int hash, Directive directive) {
        this.directive = directive;
        this.id =  "global$" + hash;
    }

    @Override
    public String getTerminalValue() {
        return this.id;
    }
}
