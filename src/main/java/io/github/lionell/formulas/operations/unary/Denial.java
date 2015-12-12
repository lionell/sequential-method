package io.github.lionell.formulas.operations.unary;

import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.operations.UnaryOperation;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.containers.Sequence;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Denial extends UnaryOperation {
    public Denial(Formula formula) {
        this.formula = formula;
    }

    public Denial(Formula formula, LogicalValue value) {
        this(formula);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        checkValue();
        Sequence resultSequence = new Sequence(sigma);
        Formula newFormula = formula.clone();
        newFormula.setValue(LogicalValue.negate(value));
        resultSequence.addFront(newFormula);
        return new Sequence[]{resultSequence};
    }

    @Override
    public Denial clone() {
        return new Denial(formula.clone(), value);
    }

    @Override
    public String toString() {
        return "!(" + formula + ")";
    }
}
