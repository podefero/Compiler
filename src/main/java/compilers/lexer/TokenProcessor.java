package compilers.lexer;

import compilers.lexer.tokens.*;
import org.antlr.v4.runtime.Token;

public class TokenProcessor {

    public TokenProcessor() {


    }

    public TokenType getTokenType(Token token) {
        switch (token.getType()) {
            case compilers.lexer.KxiLexer.INTLIT:
                return new IntLitToken(token.getText());
            case compilers.lexer.KxiLexer.STRINGLIT:
                return new StringLitToken(token.getText());
            case compilers.lexer.KxiLexer.CHARLIT:
                return new CharLitToken(token.getText());
            case compilers.lexer.KxiLexer.TRUE:
            case compilers.lexer.KxiLexer.FALSE:
                return new BoolToken(token.getText());
            default:
                return new DefaultToken(token.getText());

        }
    }
}
