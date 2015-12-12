package io.github.lionell.containers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class SequentialTree implements Tree<Sequence> {
    private Node<Sequence> root;

    public SequentialTree(Sequence sequence) {
        root = new SequentialNode(sequence);
    }

    @Override
    public Node<Sequence> getRoot() {
        return root;
    }

    public static class SequentialNode implements Node<Sequence> {
        private Sequence value;
        private List<Node<Sequence>> children;

        public SequentialNode(Sequence value) {
            this.value = value;
            children = new ArrayList<>();
        }

        @Override
        public List<Node<Sequence>> getChildren() {
            return children;
        }

        @Override
        public void addChild(Node<Sequence> child) {
            children.add(child);
        }

        @Override
        public Sequence getValue() {
            return value;
        }

        @Override
        public void setValue(Sequence value) {
            this.value = value;
        }
    }
}