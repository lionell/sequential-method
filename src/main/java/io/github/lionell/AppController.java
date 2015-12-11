package io.github.lionell;

import io.github.lionell.wrappers.Node;
import io.github.lionell.wrappers.Tree;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lionell on 08.12.2015.
 *
 * @author Ruslan Sakevych
 */

@RestController
public class AppController {
    @RequestMapping("/solve")
    public @ResponseBody Tree solve(@RequestParam(value = "name", defaultValue = "NaN") String name) {
        List<String> formulas1 = new ArrayList<>();
        formulas1.add("P(" + name + ")");
        formulas1.add("Q(x)");
        Node root = new Node(formulas1);
        List<Node> children = new ArrayList<>();
        List<String> formulas2 = new ArrayList<>();
        formulas2.add("R(x)");
        formulas2.add("T(x)");
        Node left = new Node(formulas2);
        children.add(left);
        List<String> formulas3 = new ArrayList<>();
        formulas3.add("R(x)");
        formulas3.add("Q(x)");
        Node right = new Node(formulas3);
        children.add(right);
        root.setChildren(children);
        return new Tree(root);
    }
}
