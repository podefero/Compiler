package compilers.exceptions;

public class TypeCheckException extends CompilerException{
    public TypeCheckException(String line, String message) {
        super(line, message);
    }
}
