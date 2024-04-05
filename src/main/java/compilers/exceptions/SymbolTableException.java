package compilers.exceptions;

public class SymbolTableException extends CompilerException{
    public SymbolTableException(String line, String message) {
        super(line, message);
    }
}
