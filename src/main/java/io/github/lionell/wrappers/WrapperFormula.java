package io.github.lionell.wrappers;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class WrapperFormula {
    private String formula;
    private Boolean value;

    public WrapperFormula() {
    }

    public WrapperFormula(String formula, Boolean value) {
        this.formula = formula;
        this.value = value;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
}
