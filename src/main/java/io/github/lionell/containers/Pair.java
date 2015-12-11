package io.github.lionell.containers;

/**
 * Created by lionell on 11.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class Pair<L, R> {
    private L first;
    private R second;

    public Pair(L first, R second) {
        this.first = first;
        this.second = second;
    }

    public L getFirst() {
        return first;
    }

    public void setFirst(L first) {
        this.first = first;
    }

    public R getSecond() {
        return second;
    }

    public void setSecond(R second) {
        this.second = second;
    }
}
