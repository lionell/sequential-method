package io.github.lionell.formulas;

import io.github.lionell.containers.Sequence;
import io.github.lionell.containers.Triple;
import io.github.lionell.exceptions.LogicalException;
import io.github.lionell.miscellaneous.LogicalValue;
import io.github.lionell.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
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
        throw new LogicalException("Predicate can't be expanded!");
    }

    @Override
    public void rename(String from, String to) {
        if (args.contains(from)) {
            args.set(args.indexOf(from), to);
        }
    }

    @Override
    public Set<String> getFreeVariableNames() {
        return new HashSet<>(args);
    }

    @Override
    public Set<String> getVariableNames() {
        return getFreeVariableNames();
    }

    @Override
    public boolean isAtomic() {
        return true;
    }

    @Override
    public boolean isSimple() {
        return true;
    }

    @Override
    public Predicate clone() {
        return new Predicate(name, new ArrayList<>(args), value);
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

        return name.equals(predicate.name)
                && args.equals(predicate.args)
                && value.equals(predicate.value);

    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = name.hashCode();
        result = PRIME * result + args.hashCode();
        result = PRIME * result + value.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return name + "[" + StringUtils.formatArgs(args) + "]";
    }

    public Triple<String, List<String>, Boolean> getCounterExample() {
        if (!isEvaluated()) {
            throw new LogicalException("Not evaluated predicate can't have counter example!");
        }
        return new Triple<>(name, args, value.toBoolean());
    }
}
