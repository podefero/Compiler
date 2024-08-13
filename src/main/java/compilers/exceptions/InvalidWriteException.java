package compilers.exceptions;

public class InvalidWriteException extends CompilerException{
    public InvalidWriteException(String line, String message) {
        super(line, message);
    }
}
