package io.github.lionell.utils.analysis;

import io.github.lionell.exceptions.GeneratorException;
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
    private Stack<Formula> stack = new Stack<>();
    private Formula formula;

    public FormulaGenerator(List<Token> rpn) {
        this.rpn = rpn;
    }

    public void run() {
        for (Token token : rpn) {
            switch (token.getType()) {
                case PREDICATE:
                    PredicateToken predicateToken = ((PredicateToken) token);
                    stack.push(new Predicate(predicateToken.getName(),
                            predicateToken.getArgs()));
                    break;
                case EXISTS:
                    ExistsToken existsToken = ((ExistsToken) token);
                    if (stack.isEmpty()) {
                        throw new GeneratorException("Unexpected empty stack!");
                    }
                    stack.push(new Exists(existsToken.getVariable(), stack.pop()));
                    break;
                case FOR_ALL:
                    ForAllToken forAllToken = ((ForAllToken) token);
                    if (stack.isEmpty()) {
                        throw new GeneratorException("Unexpected empty stack!");
                    }
                    stack.push(new ForAll(forAllToken.getVariable(), stack.pop()));
                    break;
                case DENY:
                    if (stack.isEmpty()) {
                        throw new GeneratorException("Unexpected empty stack!");
                    }
                    stack.push(new Denial(stack.pop()));
                    break;
                case AND:
                    if (stack.size() < 2) {
                        throw new GeneratorException("Unexpected stack size!");
                    }
                    Formula andRight = stack.pop();
                    Formula andLeft = stack.pop();
                    stack.push(new Conjunction(andRight, andLeft));
                    break;
                case OR:
                    if (stack.size() < 2) {
                        throw new GeneratorException("Unexpected stack size!");
                    }
                    Formula orRight = stack.pop();
                    Formula orLeft = stack.pop();
                    stack.push(new Disjunction(orLeft, orRight));
                    break;
                case ASSUME:
                    if (stack.isEmpty()) {
                        throw new GeneratorException("Unexpected empty stack!");
                    }
                    if (stack.size() == 1) {
                        break;
                    }
                case IMPLIES:
                    if (stack.size() < 2) {
                        throw new GeneratorException("Unexpected stack size!");
                    }
                    Formula impliesRight = stack.pop();
                    Formula impliesLeft = stack.pop();
                    stack.push(new Implication(impliesLeft, impliesRight));
                    break;
                default:
                    throw new ParserException("Unexpected token while generating RPN!");
            }
        }
        if (stack.size() != 1) {
            throw new ParserException("Expecting stack size equals 1!");
        }
        formula = stack.pop();
    }

    public Formula getFormula() {
        return formula;
    }
}
