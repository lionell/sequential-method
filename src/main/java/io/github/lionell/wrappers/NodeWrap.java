package io.github.lionell.wrappers;

import java.util.List;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

public class NodeWrap {
    private List<FormulaWrap> formulas;
    private List<NodeWrap> children;
    private boolean closed;

    public NodeWrap() {
    }

    public List<FormulaWrap> getFormulas() {
        return formulas;
    }

    public void setFormulas(List<FormulaWrap> formulas) {
        this.formulas = formulas;
    }

    public List<NodeWrap> getChildren() {
        return children;
    }

    public void setChildren(List<NodeWrap> children) {
        this.children = children;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }
}
