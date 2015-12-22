package io.github.lionell;

import io.github.lionell.exceptions.SystemException;
import io.github.lionell.utils.NameGenerator;
import io.github.lionell.utils.analysis.Parser;
import io.github.lionell.utils.SequentialMethod;
import io.github.lionell.utils.WrapBuilder;
import io.github.lionell.wrappers.Wrap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

@RestController
public class CheckController {
    @RequestMapping("/check")
    public
    @ResponseBody
    Wrap solve(@RequestParam(value = "expr") String expression) {
        WrapBuilder wrapBuilder = new WrapBuilder();
        try {
            Parser parser = new Parser(expression);
            parser.run();
            NameGenerator.reset();
            NameGenerator.setUsedVariableNames(parser.getFormula().getVariableNames());
            SequentialMethod sequentialMethod =
                    new SequentialMethod(parser.getFormula());
            sequentialMethod.run();
            wrapBuilder
                    .setSequentialTree(sequentialMethod.getTree())
                    .setVerity(sequentialMethod.getVerity());
            if (sequentialMethod.getVerity() != Boolean.TRUE) {
                wrapBuilder.setExamples(sequentialMethod.getCounterExamples());
            }
        } catch (SystemException e) {
            //wrapBuilder.setError(e.getMessage());
            wrapBuilder.setError(expression);
        }
        return wrapBuilder.getWrap();
    }
}
