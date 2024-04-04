package compilers.ast.kxi_nodes;

import lombok.Getter;

@Getter
public abstract class KxiAbstractType extends AbstractKxiNode {
    protected ScalarType scalarType;

    public KxiAbstractType(ScalarType scalarType) {
        this.scalarType = scalarType;
    }

    public String getName(String dim, KxiAbstractType kxiAbstractType) {
        while (kxiAbstractType instanceof KxiArrayType) {
            dim += "[]";
            kxiAbstractType = ((KxiArrayType) kxiAbstractType).getType();
        }
        if (kxiAbstractType instanceof KxiType) {
            if (((KxiType) kxiAbstractType).getIdName() != null)
                dim = ((KxiType) kxiAbstractType).getIdName().getValue() + dim;
            return scalarType + " " + dim;
        }
        return dim;
    }

    public int getSize() {
        return 0;
    }

}
