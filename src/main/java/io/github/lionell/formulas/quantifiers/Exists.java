package io.github.lionell.formulas.quantifiers;

import io.github.lionell.containers.Sequence;
import io.github.lionell.exceptions.LogicalException;
import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.Quantifier;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.utils.NameGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Exists extends Quantifier {
    public Exists(String variableName, Formula formula) {
        this.formula = formula;
        this.variableName = variableName;
    }

    public Exists(String variableName, Formula formula, LogicalValue value) {
        this(variableName, formula);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        if (!isEvaluated()) {
            throw new LogicalException("Can't expand not evaluated exists!");
        }
        if (value == LogicalValue.TRUE) {
            Sequence resultSequence = new Sequence(sigma);
            Formula newFormula = formula.clone();
            newFormula.rename(variableName, NameGenerator.nextVariableName());
            newFormula.setValue(LogicalValue.TRUE);
            resultSequence.addFront(newFormula);
            return new Sequence[]{resultSequence};
        } else {
            Sequence resultSequence = new Sequence(sigma);
            resultSequence.addBack(clone());
            Set<String> freeNames =
                    new HashSet<>(sigma.getFreeVariableNames());
            freeNames.addAll(getFreeVariableNames());
            for (String freeName : freeNames) {
                Formula newFormula = formula.clone();
                newFormula.rename(variableName, freeName);
                newFormula.setValue(LogicalValue.FALSE);
                resultSequence.addFront(newFormula);
            }
            return new Sequence[]{resultSequence};
        }
    }

    @Override
    public Exists clone() {
        return new Exists(variableName, formula.clone(), value);
    }

    @Override
    public String toString() {
        return "#" + variableName + "(" + formula + ")";
    }
}
