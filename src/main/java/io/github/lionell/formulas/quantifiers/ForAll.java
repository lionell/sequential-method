package io.github.lionell.formulas.quantifiers;

import io.github.lionell.formulas.Formula;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.formulas.Quantifier;
import io.github.lionell.containers.Sequence;

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
        return new Sequence[0];
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
