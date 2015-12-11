package io.github.lionell.wrappers;

import java.util.List;
import java.util.Map;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

public class Tree {
    private Node root;
    private String verdict;
    private List<Map<String, String>> example;

    public Tree(Node root) {
        this.root = root;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<Map<String, String>> getExample() {
        return example;
    }

    public void setExample(List<Map<String, String>> example) {
        this.example = example;
    }
}
