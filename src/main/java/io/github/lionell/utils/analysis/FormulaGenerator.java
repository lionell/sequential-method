package io.github.lionell.utils.analysis;

import io.github.lionell.exceptions.ParserException;
import io.github.lionell.formulas.Formula;
import io.github.lionell.formulas.Predicate;
import io.github.lionell.formulas.operations.binary.Conjunction;
import io.github.lionell.formulas.operations.binary.Disjunction;
import io.github.lionell.formulas.operations.binary.Implication;
import io.github.lionell.formulas.operations.unary.Denial;
import io.github.lionell.formulas.quantifiers.Exists;
import io.github.lionell.formulas.quantifiers.ForAll;
import io.github.lionell.utils.analysis.tokens.ExistsToken;
import io.github.lionell.utils.analysis.tokens.ForAllToken;
import io.github.lionell.utils.analysis.tokens.PredicateToken;
import io.github.lionell.utils.analysis.tokens.Token;

import java.util.List;
import java.util.Stack;

/**
 * Created by lionell on 14.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class FormulaGenerator {
    private List<Token> rpn;
    private Formula formula;

    public FormulaGenerator(List<Token> rpn) {
        this.rpn = rpn;
    }

    public void run() {
        Stack<Formula> stack = new Stack<>();
        for (Token token : rpn) {
            switch (token.getType()) {
                case PREDICATE:
                    PredicateToken predicateToken = ((PredicateToken) token);
                    stack.push(new Predicate(predicateToken.getName(),
                            predicateToken.getArgs()));
                    break;
                case EXISTS:
                    ExistsToken existsToken = ((ExistsToken) token);
                    stack.push(new Exists(existsToken.getVariable(), stack.pop()));
                    break;
                case FOR_ALL:
                    ForAllToken forAllToken = ((ForAllToken) token);
                    stack.push(new ForAll(forAllToken.getVariable(), stack.pop()));
                    break;
                case DENY:
                    stack.push(new Denial(stack.pop()));
                    break;
                case AND:
                    stack.push(new Conjunction(stack.pop(), stack.pop()));
                    break;
                case OR:
                    stack.push(new Disjunction(stack.pop(), stack.pop()));
                    break;
                case IMPLIES:
                case ASSUME:
                    Formula right = stack.pop();
                    Formula left = stack.pop();
                    stack.push(new Implication(left, right));
                    break;
                default:
                    throw new ParserException("Unexpected token!");
            }
        }
        if (stack.size() != 1) {
            throw new ParserException("Unexpected stack size!");
        }
        formula = stack.pop();
    }

    public Formula getFormula() {
        return formula;
    }
}
