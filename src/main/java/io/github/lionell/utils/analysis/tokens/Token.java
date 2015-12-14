package io.github.lionell.utils.analysis.tokens;

import io.github.lionell.exceptions.ParserException;

/**
 * Created by lionell on 13.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Token {
    protected Type type;

    public Token(Type type) {
        this.type = type;
    }

    public Token(Lexeme lexeme) {
        switch (lexeme.getType()) {
            case AND:
                type = Type.AND;
                break;
            case OR:
                type = Type.OR;
                break;
            case IMPLIES:
                type = Type.IMPLIES;
                break;
            case ASSUME:
                type = Type.ASSUME;
                break;
            case DENY:
                type = Type.DENY;
                break;
            case EXISTS:
                type = Type.EXISTS;
                break;
            case FOR_ALL:
                type = Type.FOR_ALL;
                break;
            case OPEN_ROUND_BRACKET:
                type = Type.OPEN_ROUND_BRACKET;
                break;
            default:
                throw new ParserException("Cannot create token from this lexeme type");
        }
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        PREDICATE,
        AND,
        OR,
        IMPLIES,
        ASSUME,
        DENY,
        EXISTS,
        FOR_ALL,
        OPEN_ROUND_BRACKET,
    }
}
