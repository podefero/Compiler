package compilers.ast.assembly;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public
class AssemblyBlock extends AbstractAssembly {
    List<AbstractAssembly> preBlock;
    List<AbstractAssembly> bodyBlock;
    List<AbstractAssembly> postBlock;

    public AssemblyBlock() {
        preBlock = new ArrayList<>();
        bodyBlock = new ArrayList<>();
        postBlock = new ArrayList<>();
    }

    public List<AbstractAssembly> combineBlocks() {
        List<AbstractAssembly> combinedList = new ArrayList<>();

        combinedList.addAll(preBlock);

        combinedList.addAll(bodyBlock);

        combinedList.addAll(postBlock);

        return combinedList;
    }
}
