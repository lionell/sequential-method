package io.github.lionell;

import io.github.lionell.containers.CounterExample;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.logic.SequentialBuilder;

import java.util.List;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String input = "(exists x P(x) -> Q(x)) -> (P(x) -> exists x Q(x))";
        SequentialBuilder builder = new SequentialBuilder(input);
        if (builder.isDeducible()) {
            System.out.println("Input expression is deducible");
        } else {
            SequentialTree tree = builder.getTree();
            List<CounterExample> example = builder.getCounterExamples();
        }
    }
}
