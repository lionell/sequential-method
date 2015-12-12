package io.github.lionell.containers;

/**
 * Created by lionell on 12.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Triple<L, M, R> {
    private L first;
    private M second;
    private R third;

    public Triple(L first, M second, R third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public L getFirst() {
        return first;
    }

    public void setFirst(L first) {
        this.first = first;
    }

    public M getSecond() {
        return second;
    }

    public void setSecond(M second) {
        this.second = second;
    }

    public R getThird() {
        return third;
    }

    public void setThird(R third) {
        this.third = third;
    }
}
