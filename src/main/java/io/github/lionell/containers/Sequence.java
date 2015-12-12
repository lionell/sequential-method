package io.github.lionell.containers;

import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.Predicate;
import io.github.lionell.wrappers.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                denialPredicate.setValue(denialPredicate.getValue().negate());
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
        return formulas
                .stream()
                .map(Formula::getFreeVariableNames)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Example getCounterExample() {
        if (isClosed()) {
            throw new IllegalStateException("Can't get counterexample from closed sequence!");
        }
        if (!isAtomic()) {
            throw new IllegalStateException("Can't get counterexample from expandable sequence!");
        }
        Example example = new Example();
        formulas.stream()
                .map(formula -> ((Predicate) formula))
                .map(Predicate::getCounterExample)
                .forEach(example::addPredicateValue);
        return example;
    }

    public void addFront(Formula formula) {
        formulas.add(0, formula);
    }

    public void addBack(Formula formula) {
        formulas.add(formula);
    }

    public List<Formula> getFormulas() {
        return formulas;
    }

    public Sequence clone() {
        return new Sequence(this);
    }
}
