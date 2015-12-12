package io.github.lionell.logic;

import io.github.lionell.containers.CounterExample;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.containers.SequentialTree.SequentialNode;
import io.github.lionell.containers.Tree.Node;
import io.github.lionell.utils.SequenceParser;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class SequentialBuilder {
    private static final int STEP_LIMIT = 30;
    private SequentialTree tree;
    private Verdict verdict = Verdict.UNKNOWN;
    private Queue<Node<Sequence>> leavesQueue = new ArrayDeque<>();
    private List<Node<Sequence>> unclosedLeaves = new ArrayList<>();

    public SequentialBuilder(String example) {
        SequenceParser parser = new SequenceParser();
        tree = new SequentialTree(parser.parse(example));
        verdict = transform();
    }

    private Verdict transform() {
        leavesQueue.add(tree.getRoot());
        int stepsLeft = STEP_LIMIT;
        while (!leavesQueue.isEmpty()) {
            activateNode(leavesQueue.poll());
            stepsLeft--;
            if (stepsLeft == 0) {
                return Verdict.INFINITE_LOOP;
            }
        }
        return unclosedLeaves.isEmpty()
                ? Verdict.DEDUCIBLE
                : Verdict.NOT_DEDUCIBLE;
    }

    private void activateNode(Node<Sequence> node) {
        Sequence[] resultSequences = node.getValue().expand();
        if (resultSequences.length == 1) {
            Node<Sequence> child = new SequentialNode(resultSequences[0]);
            update(child);
            node.addChild(child);
        } else {
            Node<Sequence> firstChild = new SequentialNode(resultSequences[0]);
            update(firstChild);
            node.addChild(firstChild);
            Node<Sequence> secondChild = new SequentialNode(resultSequences[1]);
            update(secondChild);
            node.addChild(secondChild);
        }
    }

    private void update(Node<Sequence> node) {
        if (!node.getValue().isClosed()) {
            if (node.getValue().isAtomic()) {
                unclosedLeaves.add(node);
            } else {
                leavesQueue.add(node);
            }
        }
    }

    public List<CounterExample> getCounterExamples() {
        if (isDeducible()) {
            throw new IllegalStateException("Input sequence is deducible!");
        }
        List<CounterExample> counterExamples = new ArrayList<>();
        unclosedLeaves.forEach(
                leaf -> counterExamples.add(leaf.getValue().getCounterExample()));
        return counterExamples;
    }

    public SequentialTree getTree() {
        return tree;
    }

    public boolean isDeducible() {
        return verdict == Verdict.DEDUCIBLE;
    }

    private enum Verdict {
        DEDUCIBLE, NOT_DEDUCIBLE, INFINITE_LOOP, UNKNOWN
    }
}
