package compilers.lexer.tokens;

public class EncodeCharacters {


    public String encodeText(String line, boolean isString) {
        String encodedString = "";
        boolean hasEscapeSequence = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '\"' && !hasEscapeSequence && isString) continue;
            else if (ch == '\'' && !hasEscapeSequence && !isString) continue;
            if (ch == '\\' && !hasEscapeSequence) {
                hasEscapeSequence = true;
            } else if (hasEscapeSequence) {

                //else encode as normally
                char encodedChar = encodeCharacter(ch);
                encodedString += encodedChar;
                hasEscapeSequence = false;
            } else {
                encodedString += ch;
            }
        }
        return encodedString;
    }

    private char encodeCharacter(char ch) {
        char encodedChar = '\0';

        switch (ch) {
            case 'n':
                encodedChar = '\n';
                break;
            case 'r':
                encodedChar = '\r';
                break;
            case 't':
                encodedChar = '\t';
                break;
            case '\\':
                encodedChar = '\\';
                break;
            case '\"':
                encodedChar = '\"';
                break;
            case '\'':
                encodedChar = '\'';
                break;
        }
        return encodedChar;
    }
}
