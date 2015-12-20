package io.github.lionell.utils;

import io.github.lionell.formulas.Formula;

import java.util.List;

/**
 * Created by lionell on 20.12.2015.
 *
 * @author Ruslan Sakevych
 */
public class StringUtils {
    public static String formatArgs(List<String> args) {
        StringBuilder builder = new StringBuilder();
        builder.append(args.get(0));
        for (int i = 1; i < args.size(); i++) {
            builder.append(",");
            builder.append(args.get(i));
        }
        return builder.toString();
    }

    public static String formatFormula(Formula formula) {
        if (formula.isSimple()) {
            return formula.toString();
        }
        return "(" + formula + ")";
    }
}
