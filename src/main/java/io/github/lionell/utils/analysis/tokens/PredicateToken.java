package io.github.lionell.utils.analysis.tokens;

import java.util.List;

/**
 * Created by lionell on 13.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class PredicateToken extends Token {
    private String name;
    private List<String> args;

    public PredicateToken(String name, List<String> args) {
        super(Type.PREDICATE);
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }
}
