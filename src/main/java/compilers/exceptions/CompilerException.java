package compilers.exceptions;

public abstract class CompilerException extends ArgumentException {
    public CompilerException(String message) {
        super(message);
    }
}
