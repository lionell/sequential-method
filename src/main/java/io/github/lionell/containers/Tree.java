package io.github.lionell.containers;

import java.util.List;

/**
 * Created by lionell on 11.12.2015.
 *
 * @author Ruslan Sakevych
 */
public interface Tree<T> {
    Node<T> getRoot();

    interface Node<T> {
        List<Node<T>> getChildren();

        void addChild(Node<T> child);

        T getValue();

        void setValue(T value);
    }
}
