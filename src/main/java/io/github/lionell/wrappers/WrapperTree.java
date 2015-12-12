package io.github.lionell.wrappers;

import io.github.lionell.containers.CounterExample;

import java.util.List;
import java.util.Map;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

public class WrapperTree {
    private WrapperNode root;
    private String verdict;
    private List<CounterExample> example;

    public WrapperTree() {
    }

    public WrapperNode getRoot() {
        return root;
    }

    public void setRoot(WrapperNode root) {
        this.root = root;
    }

    public String getVerdict() {
        return verdict;
    }

    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public List<CounterExample> getExample() {
        return example;
    }

    public void setExample(List<CounterExample> example) {
        this.example = example;
    }
}
