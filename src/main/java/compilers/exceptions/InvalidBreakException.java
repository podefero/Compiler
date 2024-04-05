package compilers.exceptions;

public class InvalidBreakException extends CompilerException{
    public InvalidBreakException(String line, String message) {
        super(line, message);
    }
}
