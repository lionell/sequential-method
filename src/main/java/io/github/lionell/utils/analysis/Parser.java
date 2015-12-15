package io.github.lionell.utils.analysis;

import io.github.lionell.formulas.Formula;
import io.github.lionell.miscellaneous.LogicalValue;

public class Parser {
    private String input;
    private Formula formula;

    public Parser(String input) {
        this.input = input;
    }

    public void run() {
        Tokenizer tokenizer = new Tokenizer(input);
        tokenizer.run();
        InfixToPostfixConverter converter =
                new InfixToPostfixConverter(tokenizer.getLexemes());
        converter.run();
        FormulaGenerator generator = new FormulaGenerator(converter.getRPN());
        generator.run();
        formula = generator.getFormula();
        formula.setValue(LogicalValue.FALSE);
    }

    public Formula getFormula() {
        return formula;
    }
}
