package io.github.lionell.utils.analysis.tokens;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Lexeme {
    private String value;
    private Type type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        OPEN_ROUND_BRACKET,
        CLOSED_ROUND_BRACKET,
        OPEN_BRACKET,
        CLOSED_BRACKET,
        PREDICATE,
        VARIABLE,
        COMMA,
        AND,
        OR,
        IMPLIES,
        ASSUME,
        DENY,
        EXISTS,
        FOR_ALL
    }
}
