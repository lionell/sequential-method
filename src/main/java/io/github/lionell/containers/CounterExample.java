package io.github.lionell.containers;

import io.github.lionell.utils.NameGenerator;

import java.util.List;
import java.util.Map;

public class CounterExample {
    private String name = NameGenerator.nextCounterExampleName();
    private Map<String, String> delta;
    private List<Pair<String, Map<String, String>>> example;
}
