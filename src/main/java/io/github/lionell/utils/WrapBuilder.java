package io.github.lionell.utils;

import io.github.lionell.containers.Sequence;
import io.github.lionell.containers.SequentialTree;
import io.github.lionell.containers.Tree;
import io.github.lionell.wrappers.Example;
import io.github.lionell.wrappers.FormulaWrap;
import io.github.lionell.wrappers.NodeWrap;
import io.github.lionell.wrappers.TreeWrap;
import io.github.lionell.wrappers.Wrap;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class WrapBuilder {
    private Wrap wrap = new Wrap();

    public WrapBuilder() {
    }

    public WrapBuilder setSequentialTree(SequentialTree sequentialTree) {
        wrap.setTree(new TreeWrap(processNode(sequentialTree.getRoot())));
        return this;
    }

    private NodeWrap processNode(Tree.Node<Sequence> node) {
        NodeWrap wrapperNode = new NodeWrap();
        wrapperNode.setFormulas(
                node.getValue().getFormulas()
                        .stream()
                        .map(formula -> new FormulaWrap(formula.toString(),
                                formula.getValue().toBoolean()))
                        .collect(Collectors.toList())
        );
        wrapperNode.setClosed(node.getValue().isClosed());
        wrapperNode.setChildren(
                node.getChildren().stream()
                        .map(this::processNode)
                        .collect(Collectors.toList())
        );
        return wrapperNode;
    }

    public WrapBuilder setExamples(List<Example> examples) {
        wrap.setExamples(examples);
        return this;
    }

    public WrapBuilder setVerity(Boolean verity) {
        wrap.setVerity(verity);
        return this;
    }

    public WrapBuilder setError(String error) {
        wrap.setError(error);
        return this;
    }

    public Wrap getWrap() {
        return wrap;
    }
}
