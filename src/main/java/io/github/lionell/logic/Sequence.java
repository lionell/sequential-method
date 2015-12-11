package io.github.lionell.logic;

import io.github.lionell.containers.CounterExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sequence {
    private List<Formula> formulas;

    public Sequence(Formula... formulas) {
        this.formulas = Arrays.asList(formulas);
    }

    public Sequence(Sequence example) {
        formulas = new ArrayList<>(example.formulas);
    }

    public boolean isClosed() {
        Set<Predicate> predicateSet = new HashSet<>();
        for (Formula formula : formulas) {
            if (formula.isAtomic()) {
                Predicate predicate = (Predicate) formula;
                Predicate denialPredicate = predicate.clone();
                denialPredicate.setValue(LogicalValue.negate(denialPredicate.value));
                if (predicateSet.contains(denialPredicate)) {
                    return true;
                }
                predicateSet.add(predicate);
            }
        }
        return false;
    }

    public boolean isAtomic() {
        return findFirstExpandable() == null;
    }

    public boolean isCompleted() {
        return isAtomic() || isClosed();
    }

    public CounterExample getCounterExample() {
        if (!isAtomic()) {
            throw new IllegalStateException("The sequence is expandable, can't find counterexample");
        }
        return null;
    }

    private Formula findFirstExpandable() {
        for (Formula formula : formulas) {
            if (!formula.isAtomic()) {
                return formula;
            }
        }
        return null;
    }

    public Sequence[] expand() {
        if (isAtomic()) {
            throw new IllegalStateException("Atomic formula cannot be expanded!");
        }
        Formula formula = findFirstExpandable();
        Sequence sigma = clone();
        sigma.formulas.remove(formula);
        return formula.expand(sigma);
    }

    public Set<String> getFreeVariableNames() {
        Set<String> freeNames = new HashSet<>();
        for (Formula formula : formulas) {
            freeNames.addAll(formula.getFreeVariableNames());
        }
        return freeNames;
    }

    public void addFront(Formula formula) {
        formulas.add(0, formula);
    }

    public void addBack(Formula formula) {
        formulas.add(formula);
    }

    public Sequence clone() {
        return new Sequence(this);
    }
}
