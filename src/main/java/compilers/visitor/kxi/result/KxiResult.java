package compilers.visitor.kxi.result;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class KxiResult {
    List<ResultFlag> resultFlagList;

    public KxiResult() {
        resultFlagList = new ArrayList<>();
    }

    public boolean containsFlag(ResultFlag resultFlag) {
        return resultFlagList.contains(resultFlag);
    }


}
