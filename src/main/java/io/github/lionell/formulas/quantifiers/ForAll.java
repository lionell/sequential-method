package io.github.lionell.formulas.quantifiers;

import io.github.lionell.formulas.Formula;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.formulas.Quantifier;
import io.github.lionell.containers.Sequence;
import io.github.lionell.utils.NameGenerator;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class ForAll extends Quantifier {
    public ForAll(String variableName, Formula formula) {
        this.formula = formula;
        this.variableName = variableName;
    }

    public ForAll(String variableName, Formula formula, LogicalValue value) {
        this(variableName, formula);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        checkValue();
        Sequence resultSequence = new Sequence(sigma);
        if (value == LogicalValue.TRUE) {
            resultSequence.addBack(clone());
            Set<String> freeVariableNames =
                    new HashSet<>(sigma.getFreeVariableNames());
            freeVariableNames.addAll(getFreeVariableNames());
            for (String freeVariableName : freeVariableNames) {
                Formula newFormula = formula.clone();
                newFormula.rename(variableName, freeVariableName);
                newFormula.setValue(LogicalValue.TRUE);
                resultSequence.addFront(newFormula);
            }
        } else {
            Formula newFormula = formula.clone();
            newFormula.rename(variableName, NameGenerator.nextVariableName());
            newFormula.setValue(LogicalValue.FALSE);
            resultSequence.addFront(newFormula);
        }
        return new Sequence[]{resultSequence};
    }

    @Override
    public ForAll clone() {
        return new ForAll(variableName, formula.clone(), value);
    }

    @Override
    public String toString() {
        return "@" + variableName + "(" + formula + ")";
    }
}
