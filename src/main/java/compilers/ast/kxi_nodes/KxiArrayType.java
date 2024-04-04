package compilers.ast.kxi_nodes;

import lombok.Getter;

@Getter
public class KxiArrayType extends KxiAbstractType {

    private KxiAbstractType type;

    public KxiArrayType(ScalarType scalarType, KxiAbstractType type) {
        super(scalarType);
        this.type = type;
    }

}
