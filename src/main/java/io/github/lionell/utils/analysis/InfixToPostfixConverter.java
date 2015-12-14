package io.github.lionell.utils.analysis;

import io.github.lionell.exceptions.ConverterException;
import io.github.lionell.utils.analysis.tokens.ExistsToken;
import io.github.lionell.utils.analysis.tokens.ForAllToken;
import io.github.lionell.utils.analysis.tokens.Lexeme;
import io.github.lionell.utils.analysis.tokens.PredicateToken;
import io.github.lionell.utils.analysis.tokens.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by lionell on 13.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class InfixToPostfixConverter {
    private List<Lexeme> infix;
    private List<Token> postfix = new ArrayList<>();
    private Stack<Token> opStack = new Stack<>();
    private int index = 0;

    public InfixToPostfixConverter(List<Lexeme> infix) {
        this.infix = infix;
    }

    public void run() {
        while (index < infix.size()) {
            switch (getLexeme().getType()) {
                case PREDICATE:
                    String predicateName = getLexeme().getValue();
                    match(Lexeme.Type.PREDICATE);
                    match(Lexeme.Type.OPEN_BRACKET);
                    List<String> args = new ArrayList<>();
                    args.add(getLexeme().getValue());
                    match(Lexeme.Type.VARIABLE);
                    while (getLexeme().getType() == Lexeme.Type.COMMA) {
                        match(Lexeme.Type.COMMA);
                        args.add(getLexeme().getValue());
                        match(Lexeme.Type.VARIABLE);
                    }
                    match(Lexeme.Type.CLOSED_BRACKET);
                    postfix.add(new PredicateToken(predicateName, args));
                    break;
                case EXISTS:
                    match(Lexeme.Type.EXISTS);
                    opStack.push(new ExistsToken(getLexeme().getValue()));
                    match(Lexeme.Type.VARIABLE);
                    break;
                case FOR_ALL:
                    match(Lexeme.Type.FOR_ALL);
                    opStack.push(new ForAllToken(getLexeme().getValue()));
                    match(Lexeme.Type.VARIABLE);
                    break;
                case AND:
                case OR:
                case ASSUME:
                case IMPLIES:
                case DENY:
                    Token token = new Token(getLexeme());
                    while (!opStack.isEmpty()
                            && opStack.peek().getType() != Token.Type.OPEN_ROUND_BRACKET
                            && getPriority(opStack.peek()) >= getPriority(token)) {
                        postfix.add(opStack.pop());
                    }
                    match(Lexeme.Type.AND, Lexeme.Type.OR, Lexeme.Type.ASSUME,
                            Lexeme.Type.IMPLIES, Lexeme.Type.EXISTS,
                            Lexeme.Type.FOR_ALL, Lexeme.Type.DENY);
                    opStack.push(token);
                    break;
                case OPEN_ROUND_BRACKET:
                    opStack.push(new Token(getLexeme()));
                    match(Lexeme.Type.OPEN_ROUND_BRACKET);
                    break;
                case CLOSED_ROUND_BRACKET:
                    while (!opStack.isEmpty()
                            && opStack.peek().getType() != Token.Type.OPEN_ROUND_BRACKET) {
                        postfix.add(opStack.pop());
                    }
                    if (opStack.isEmpty()) {
                        throw new ConverterException("Opened bracket expected");
                    }
                    opStack.pop();
                    match(Lexeme.Type.CLOSED_ROUND_BRACKET);
                    break;
                default:
                    throw new ConverterException("Unexpected lexeme!");
            }
        }
        while (!opStack.isEmpty()) {
            postfix.add(opStack.pop());
        }
    }

    private int getPriority(Token operation) {
        switch (operation.getType()) {
            case DENY:
            case EXISTS:
            case FOR_ALL:
                return 4;
            case AND:
                return 3;
            case OR:
                return 2;
            case IMPLIES:
                return 1;
            case ASSUME:
                return 0;
            default:
                throw new ConverterException("Can't get priority from this token");
        }
    }

    private Lexeme getLexeme() {
        return infix.get(index);
    }

    private void match(Lexeme.Type... types) {
        for (Lexeme.Type type : types) {
            if (getLexeme().getType() == type) {
                index++;
                return;
            }
        }
        throw new ConverterException("Lexeme mismatch!");
    }

    public List<Token> getRPN() {
        return postfix;
    }
}
