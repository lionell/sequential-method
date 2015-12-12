package io.github.lionell.containers;

import io.github.lionell.utils.NameGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class CounterExample {
    private String name = NameGenerator.nextCounterExampleName();
    private Map<String, String> delta;
    private Map<String, Map<List<String>, Boolean>> example;

    public CounterExample() {
        delta = new HashMap<>();
        example = new HashMap<>();
    }

    public void addPredicateValue(Triple<String, List<String>, Boolean> predicateVariablesValue) {
        List<String> newVariables = new ArrayList<>();
        for (String variable : predicateVariablesValue.getSecond()) {
            String newName;
            if (delta.containsKey(variable)) {
                newName = delta.get(variable);
            } else {
                newName = NameGenerator.nextVariableName();
                delta.put(variable, newName);
            }
            newVariables.add(newName);
        }
        if (!example.containsKey(predicateVariablesValue.getFirst())) {
            example.put(predicateVariablesValue.getFirst(), new HashMap<>());
        }
        example.get(predicateVariablesValue.getFirst())
                .put(newVariables, predicateVariablesValue.getThird());
    }
}
