package io.github.lionell;

import io.github.lionell.utils.SequentialTreeBuilder;
import io.github.lionell.utils.WrapperTreeBuilder;
import io.github.lionell.wrappers.WrapperTree;
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
    @RequestMapping("/solve")
    public @ResponseBody
    WrapperTree solve(@RequestParam(value = "expr", defaultValue = "NaN") String expression) {
        SequentialTreeBuilder sequentialTreeBuilder =
                new SequentialTreeBuilder(expression);
        WrapperTreeBuilder treeWrapper = new WrapperTreeBuilder();
        treeWrapper.addSequentialTree(sequentialTreeBuilder.getTree())
                .addVerdict(sequentialTreeBuilder.getVerdict());
        if (!sequentialTreeBuilder.getVerdict()) {
            treeWrapper
                    .addCounterExamples(sequentialTreeBuilder.getCounterExamples());
        }
        return treeWrapper.createTree();
    }
}
