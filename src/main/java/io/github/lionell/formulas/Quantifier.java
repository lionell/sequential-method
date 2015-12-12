package io.github.lionell.formulas;

import io.github.lionell.formulas.Formula;

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
        Set<String> freeVariableNames = new HashSet<>(formula.getFreeVariableNames());
        if (freeVariableNames.contains(variableName)) {
            freeVariableNames.remove(variableName);
        }
        return freeVariableNames;
    }

    @Override
    public boolean isAtomic() {
        return false;
    }
}
