package compilers.exceptions;

public class ParseException extends CompilerException{

    public ParseException(String line, String message) {
        super(line, message);
    }
}
