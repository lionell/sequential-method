package io.github.lionell.wrappers;

import java.util.List;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

public class WrapperNode {
    private List<WrapperFormula> formulas;
    private List<WrapperNode> children;
    private boolean closed;

    public WrapperNode() {
    }

    public List<WrapperFormula> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<WrapperFormula> formulas) {
        this.formulas = formulas;
    }

    public List<WrapperNode> getChildren() {
        return children;
    }

    public void setChildren(List<WrapperNode> children) {
        this.children = children;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
