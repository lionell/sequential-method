package io.github.lionell;

import io.github.lionell.utils.analysis.parser.Parser;
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
public class AppController {
    @RequestMapping("/check")
    public
    @ResponseBody
    Wrap solve(@RequestParam(value = "expr", defaultValue = "true") String expression) {
        WrapBuilder wrapBuilder = new WrapBuilder();
        Parser parser = new Parser(expression);
        try {
            parser.run();
            SequentialMethod sequentialMethod =
                    new SequentialMethod(parser.getFormula());
            sequentialMethod.run();
            wrapBuilder
                    .setSequentialTree(sequentialMethod.getTree())
                    .setVerity(sequentialMethod.getVerity());
            if (!sequentialMethod.getVerity()) {
                wrapBuilder.setExamples(sequentialMethod.getCounterExamples());
            }
        } catch (Exception e) {
            wrapBuilder.setError(e.getMessage());
        }
        return wrapBuilder.getWrap();
    }
}
