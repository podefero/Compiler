package compilers.ast.kxi_nodes.expressions.token_expression;

import compilers.antlr.KxiLexer;
import compilers.ast.ScalarType;
import compilers.ast.kxi_nodes.expressions.token_expression.*;
import org.antlr.v4.runtime.Token;

public class TokenProcessor {

    public TokenProcessor() {


    }

    public TokenType getTokenType(Token token) {
        switch (token.getType()) {
            case KxiLexer.INTLIT:
                return new IntLitToken(token.getText(), ScalarType.INT);
            case KxiLexer.STRINGLIT:
                return new StringLitToken(token.getText(), ScalarType.STRING);
            case KxiLexer.CHARLIT:
                return new CharLitToken(token.getText(), ScalarType.CHAR);
            case KxiLexer.TRUE:
            case KxiLexer.FALSE:
                return new BoolToken(token.getText(), ScalarType.BOOL);
            case KxiLexer.IDENTIFIER:
                return new IdentifierToken(token.getText(), ScalarType.ID);
            default:
                return new DefaultToken(token.getText(), ScalarType.UNKNOWN);

        }
    }
}
