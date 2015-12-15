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
    private static StringBuilder currentVariableName = new StringBuilder();
    private static StringBuilder currentCounterExampleName = new StringBuilder();

    public static String nextVariableName() {
        do {
            int index = currentVariableName.length() - 1;
            while (index >= 0 && currentVariableName.charAt(index) == 'z') {
                currentVariableName.replace(index, index + 1, "a");
                index--;
            }
            if (index < 0) {
                currentVariableName.append('a');
            } else {
                currentVariableName.replace(index, index + 1,
                        Character.toString((char) (currentVariableName.charAt(index) + 1)));
            }
        } while (usedVariableNames.contains(currentVariableName.toString()));
        usedVariableNames.add(currentVariableName.toString());
        return currentVariableName.toString();
    }

    public static String nextCounterExampleName() {
        do {
            int index = currentCounterExampleName.length() - 1;
            while (index >= 0 && currentCounterExampleName.charAt(index) == 'Z') {
                currentCounterExampleName.replace(index, index + 1, "A");
                index--;
            }
            if (index < 0) {
                currentCounterExampleName.append('A');
            } else {
                currentCounterExampleName.replace(index, index + 1,
                        Character.toString((char) (currentCounterExampleName.charAt(index) + 1)));
            }
        } while (usedCounterExampleNames.contains(currentCounterExampleName.toString()));
        usedCounterExampleNames.add(currentCounterExampleName.toString());
        return currentCounterExampleName.toString();
    }

    public static void setUsedVariableNames(Set<String> variableNames) {
        usedVariableNames.addAll(variableNames);
    }
}
