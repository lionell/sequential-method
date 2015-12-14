package io.github.lionell.utils.analysis.tokens;

/**
 * Created by lionell on 14.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class ForAllToken extends Token {
    private String variable;

    public ForAllToken(String variable) {
        super(Type.FOR_ALL);
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }
}
