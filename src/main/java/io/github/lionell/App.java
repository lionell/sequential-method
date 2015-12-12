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
        SequentialBuilder builder = new SequentialBuilder();
        if (builder.isDeducible()) {
            System.out.println("Input expression is deducible");
        } else {
            SequentialTree tree = builder.getTree();
            List<CounterExample> example = builder.getCounterExamples();
        }
    }
}
