package compilers.ast.kxi_nodes;

import lombok.Getter;

@Getter
public class KxiArrayType extends KxiAbstractType {

    private KxiAbstractType insideType;

    public KxiArrayType(ScalarType scalarType, KxiAbstractType insideType) {
        super(scalarType);
        this.insideType = insideType;
    }

}
