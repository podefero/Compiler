package compilers.ast.intermediate;

import compilers.visitor.kxi.symboltable.SymbolTable;
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

}
