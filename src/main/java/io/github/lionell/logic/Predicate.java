package io.github.lionell.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lionell on 07.12.2015.
 */
public class Predicate extends Formula {
    private String name;
    private List<String> args;

    public Predicate(String name, List<String> args) {
        this.name = name;
        this.args = args;
    }

    public Predicate(String name, List<String> args, LogicalValue value) {
        this(name, args);
        this.value = value;
    }

    @Override
    public Sequence[] expand(Sequence sigma) {
        throw new IllegalStateException("Predicate can't be expanded!");
    }

    @Override
    public void rename(String from, String to) {
        if (args.contains(from)) {
            int indexOfFrom = args.indexOf(from);
            args.set(indexOfFrom, to);
        }
    }

    @Override
    public Set<String> getFreeVariableNames() {
        return new HashSet<>(args);
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public Predicate clone() {
        List<String> argsCopy = new ArrayList<>(args);
        return new Predicate(name, argsCopy, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Predicate predicate = (Predicate) o;

        if (!name.equals(predicate.name)) {
            return false;
        }
        return args.equals(predicate.args);

    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + args.hashCode();
        return result;
    }
}
