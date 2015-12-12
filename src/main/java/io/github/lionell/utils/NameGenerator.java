package io.github.lionell.utils;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class NameGenerator {
    private static Set<String> usedVariableNames = new HashSet<>();
    private static Set<String> usedCounterExampleNames = new HashSet<>();
    private static Random random = new Random();

    public static String nextVariableName() {
        String name;
        do {
            name = nextName();
        } while (usedVariableNames.contains(name));
        usedVariableNames.add(name);
        return name;
    }

    public static String nextCounterExampleName() {
        String name;
        do {
            name = nextName();
        } while (usedCounterExampleNames.contains(name));
        usedCounterExampleNames.add(name);
        return name;
    }

    private static String nextName() {
        return (char) (random.nextInt(26) + 'a') + "";
    }
}
