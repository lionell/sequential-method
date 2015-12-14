package io.github.lionell;

import io.github.lionell.containers.SequentialTree;
import io.github.lionell.utils.SequentialMethod;
import io.github.lionell.utils.analysis.Parser;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class App {
    public static void main(String[] args) {
        Parser parser = new Parser("@x(P[x] || Q[x]) = @xP[x] || @xQ[x]");
        parser.run();
        SequentialMethod sequentialMethod =
                new SequentialMethod(parser.getFormula());
        sequentialMethod.run();
        SequentialTree tree = sequentialMethod.getTree();
    }
}
