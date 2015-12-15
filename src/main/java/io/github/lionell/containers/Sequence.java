package io.github.lionell.containers;

import io.github.lionell.exceptions.LogicalException;
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
        Set<Predicate> set = new HashSet<>();
        for (Formula formula : formulas) {
            if (formula.isAtomic()) {
                Predicate predicate = (Predicate) formula;
                Predicate denialPredicate = predicate.clone();
                denialPredicate.setValue(denialPredicate.getValue().negate());
                if (set.contains(denialPredicate)) {
                    return true;
                }
                set.add(predicate);
            }
        }
        return false;
    }

    public boolean isAtomic() {
        for (Formula formula : formulas) {
            if (!formula.isAtomic()) {
                return false;
            }
        }
        return true;
    }

    private Formula getFirstExpandable() {
        for (Formula formula : formulas) {
            if (!formula.isAtomic()) {
                return formula;
            }
        }
        throw new LogicalException("Not found expandable formula in sequence!");
    }

    public Sequence[] expand() {
        if (isAtomic()) {
            throw new LogicalException("Atomic formula cannot be expanded!");
        }
        Formula formula = getFirstExpandable();
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
            throw new LogicalException("Can't get counter example from closed sequence!");
        }
        if (!isAtomic()) {
            throw new LogicalException("Can't get counter example from expandable sequence!");
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

    public Sequence clone() {
        return new Sequence(this);
    }

    public List<Formula> getFormulas() {
        return formulas;
    }
}
