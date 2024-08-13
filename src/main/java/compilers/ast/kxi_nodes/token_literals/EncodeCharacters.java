package compilers.ast.kxi_nodes.token_literals;

public class EncodeCharacters {


    public String encodeText(String line) {
        //trim quotes off-line
        line = trimQuotes(line);
        String encodedString = "";
        boolean hasEscapeSequence = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            //check length here in case of '\' character being the only character
            if (ch == '\\' && !hasEscapeSequence && line.length() != 1) {
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

    private String trimQuotes(String line){
        return line.substring(1, line.length()-1);
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
