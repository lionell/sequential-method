package io.github.lionell.utils.analysis.tokens;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Token {
    private String value;
    private TokenType type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
