package io.github.lionell.utils;

import io.github.lionell.containers.CounterExample;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.containers.Tree;
import io.github.lionell.logic.Sequence;
import io.github.lionell.wrappers.WrapperFormula;
import io.github.lionell.wrappers.WrapperNode;
import io.github.lionell.wrappers.WrapperTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class WrapperTreeBuilder {
    private WrapperTree wrapperTree = new WrapperTree();

    public WrapperTreeBuilder() {
    }

    public WrapperTreeBuilder addSequentialTree(SequentialTree sequentialTree) {
        wrapperTree.setRoot(processNode(sequentialTree.getRoot()));
        return this;
    }

    private WrapperNode processNode(Tree.Node<Sequence> node) {
        WrapperNode wrapperNode = new WrapperNode();
        List<WrapperFormula> formulas = new ArrayList<>();
        node.getValue().getFormulas().stream()
                .map(formula -> new WrapperFormula(formula.toString(),
                        formula.getValue().toBoolean()))
                .forEach(formulas::add);
        wrapperNode.setFormulas(formulas);
        wrapperNode.setClosed(node.getValue().isClosed());
        List<WrapperNode> children = new ArrayList<>();
        node.getChildren().stream()
                .map(this::processNode)
                .forEach(children::add);
        wrapperNode.setChildren(children);
        return wrapperNode;
    }

    public WrapperTreeBuilder addCounterExamples(List<CounterExample> counterExamples) {
        wrapperTree.setExample(counterExamples);
        return this;
    }

    public void addVerdict(Boolean verdict) {
        wrapperTree.setVerdict(verdict.toString());
    }

    public WrapperTree createTree() {
        return wrapperTree;
    }
}
