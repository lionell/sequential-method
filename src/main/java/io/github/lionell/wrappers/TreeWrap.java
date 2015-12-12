package io.github.lionell.wrappers;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

public class TreeWrap {
    private NodeWrap root;

    public TreeWrap() {
    }

    public TreeWrap(NodeWrap root) {
        this.root = root;
    }

    public NodeWrap getRoot() {
        return root;
    }

    public void setRoot(NodeWrap root) {
        this.root = root;
    }
}
