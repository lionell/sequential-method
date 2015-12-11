package io.github.lionell.logic;

import io.github.lionell.containers.CounterExample;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.containers.SequentialTree.SequentialNode;
import io.github.lionell.containers.Tree;
import io.github.lionell.containers.Tree.Node;
import io.github.lionell.utils.SequenceParser;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by lionell on 07.12.2015.
 */
public class SequentialBuilder {
    private static final int STEP_LIMIT = 30;
    private SequentialTree tree;
    private Verdict verdict = Verdict.UNKNOWN;
    private Queue<Node<Sequence>> leaves = new ArrayDeque<>();

    public SequentialBuilder(String example) {
        SequenceParser parser = new SequenceParser();
        tree = new SequentialTree(parser.parse(example));
        transform();
    }

    private void transform() {
        leaves.add(tree.getRoot());
        int stepsLeft = STEP_LIMIT;
        while (!leaves.isEmpty() && stepsLeft > 0) {
            activateNode(leaves.poll());
            stepsLeft--;
        }
        if (stepsLeft == 0) {
            verdict = Verdict.NOT_DEDUCIBLE;
        }
    }

    private void activateNode(Node<Sequence> node) {
        Sequence[] resultSequences = node.getValue().expand();
        if (resultSequences.length == 1) {
            Node<Sequence> child = new SequentialNode(resultSequences[0]);
            if (!child.getValue().isCompleted()) {
                leaves.add(child);
            }
            node.addChild(child);
        } else {
            Node<Sequence> firstChild = new SequentialNode(resultSequences[0]);
            if (!firstChild.getValue().isCompleted()) {
                leaves.add(firstChild);
            }
            node.addChild(firstChild);
            Node<Sequence> secondChild = new SequentialNode(resultSequences[1]);
            if (!secondChild.getValue().isCompleted()) {
                leaves.add(secondChild);
            }
            node.addChild(secondChild);
        }
    }

    public CounterExample getExample() {
        if (isDeducible()) {
            throw new IllegalStateException("Sequence is deducible!");
        }
        // TODO
        return new CounterExample();
    }

    public SequentialTree getTree() {
        return tree;
    }

    public boolean isDeducible() {
        return verdict == Verdict.DEDUCIBLE;
    }

    private enum Verdict {
        DEDUCIBLE, NOT_DEDUCIBLE, UNKNOWN
    }
}
