package io.github.lionell.logic;

import io.github.lionell.containers.CounterExample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
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
        formulas.stream()
                .map(Formula::getFreeVariableNames)
                .forEach(freeNames::addAll);
        return freeNames;
    }

    public CounterExample getCounterExample() {
        if (isClosed()) {
            throw new IllegalStateException("Can't get counterexample from closed sequence!");
        }
        if (!isAtomic()) {
            throw new IllegalStateException("Can't get counterexample from expandable sequence!");
        }
        CounterExample counterExample = new CounterExample();
        formulas.stream()
                .map(formula -> ((Predicate) formula))
                .map(Predicate::getCounterExample)
                .forEach(counterExample::addPredicateValue);
        return counterExample;
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
