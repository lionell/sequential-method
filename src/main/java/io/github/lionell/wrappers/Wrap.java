package io.github.lionell.wrappers;

import java.util.List;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Wrap {
    private TreeWrap tree;
    private Boolean verity;
    private List<Example> examples;
    private String error;

    public TreeWrap getTree() {
        return tree;
    }

    public void setTree(TreeWrap tree) {
        this.tree = tree;
    }

    public Boolean getVerity() {
        return verity;
    }

    public void setVerity(Boolean verity) {
        this.verity = verity;
    }

    public List<Example> getExamples() {
        return examples;
    }

    public void setExamples(List<Example> examples) {
        this.examples = examples;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
