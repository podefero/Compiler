package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.antlr.KxiLexer;
import compilers.ast.kxi_nodes.expressions.token_expression.*;
import org.antlr.v4.runtime.Token;

public class TokenProcessor {

    public TokenProcessor() {


    }

    public TokenType getTokenType(Token token) {
        switch (token.getType()) {
            case KxiLexer.INTLIT:
                return new IntLitToken(token.getText());
            case KxiLexer.STRINGLIT:
                return new StringLitToken(token.getText());
            case KxiLexer.CHARLIT:
                return new CharLitToken(token.getText());
            case KxiLexer.TRUE:
            case KxiLexer.FALSE:
                return new BoolToken(token.getText());
            case KxiLexer.IDENTIFIER:
                return new IdentifierToken(token.getText());
            default:
                return new DefaultToken(token.getText());

        }
    }
}
