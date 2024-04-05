package compilers.visitor.kxi.symboltable;

import compilers.ast.kxi_nodes.KxiAbstractType;
import compilers.ast.kxi_nodes.Modifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SymbolData {
    private boolean isStatic;
    private Modifier modifier;
    private KxiAbstractType type;
}
