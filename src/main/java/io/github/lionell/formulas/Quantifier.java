package io.github.lionell.formulas;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public abstract class Quantifier extends Formula {
    protected String variableName;
    protected Formula formula;

    @Override
    public void rename(String from, String to) {
        if (!variableName.equals(from)) {
            formula.rename(from, to);
        }
    }

    @Override
    public Set<String> getFreeVariableNames() {
        Set<String> names =
                getVariableNames();
        if (names.contains(variableName)) {
            names.remove(variableName);
        }
        return names;
    }

    @Override
    public Set<String> getVariableNames() {
        return new HashSet<>(formula.getFreeVariableNames());
    }

    @Override
    public boolean isAtomic() {
        return false;
    }

    @Override
    public boolean isSimple() {
        return true;
    }
}
