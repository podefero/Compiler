package compilers.ast.assembly;

import lombok.Getter;

@Getter
public enum Directive {
    INT(".INT"),
    BYT(".BYT"),
    STR(".STR")
   ;
    private final String value;

    Directive(String value) {
        this.value = value;
    }
}
