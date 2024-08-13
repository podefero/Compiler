package compilers.visitor.kxi.symboltable;

import lombok.Data;

@Data
public class BlockScope extends BlockAbstractScope {
    private String methodId;
}
