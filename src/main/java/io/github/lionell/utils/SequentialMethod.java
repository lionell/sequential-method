package io.github.lionell.utils;

import io.github.lionell.containers.Sequence;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.containers.SequentialTree.SequentialNode;
import io.github.lionell.containers.Tree.Node;
import io.github.lionell.formulas.Formula;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.wrappers.Example;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class SequentialMethod {
    private static final int STEP_LIMIT = 30;
    private SequentialTree tree;
    private LogicalValue verity = LogicalValue.UNKNOWN;
    private Queue<Node<Sequence>> leavesQueue = new ArrayDeque<>();
    private List<Node<Sequence>> unclosedLeaves = new ArrayList<>();

    public SequentialMethod(Formula formula) {
        tree = new SequentialTree(new Sequence(formula));
    }

    public void run() {
        try{
            verity = transform();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private LogicalValue transform() {
        leavesQueue.add(tree.getRoot());
        int stepsLeft = STEP_LIMIT;
        while (!leavesQueue.isEmpty()) {
            activateNode(leavesQueue.poll());
            stepsLeft--;
            if (stepsLeft == 0) {
                return LogicalValue.FALSE;
            }
        }
        return unclosedLeaves.isEmpty()
                ? LogicalValue.TRUE
                : LogicalValue.FALSE;
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

    public List<Example> getCounterExamples() {
        if (verity == LogicalValue.TRUE) {
            throw new IllegalStateException("Input sequence is deducible!");
        }
        return unclosedLeaves.stream()
                .map(Node::getValue)
                .map(Sequence::getCounterExample)
                .collect(Collectors.toList());
    }

    public SequentialTree getTree() {
        return tree;
    }

    public Boolean getVerity() {
        return verity.toBoolean();
    }
}
