package io.github.lionell.logic.quantifiers;

import io.github.lionell.logic.Formula;
import io.github.lionell.logic.LogicalValue;
import io.github.lionell.logic.Quantifier;
import io.github.lionell.logic.Sequence;
import io.github.lionell.utils.NameGenerator;

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
        checkValue();
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
            for (String freeVariableName : sigma.getFreeVariableNames()) {
                Formula newFormula = formula.clone();
                newFormula.rename(variableName, freeVariableName);
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
