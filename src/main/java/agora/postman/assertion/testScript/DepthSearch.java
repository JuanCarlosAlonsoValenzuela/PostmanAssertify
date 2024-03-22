package agora.postman.assertion.testScript;

import agora.postman.assertion.testScript.nestingLevelTree.Tree;

import java.util.ArrayList;
import java.util.List;

import static agora.postman.assertion.Main.HIERARCHY_SEPARATOR;

/**
 * @author Juan C. Alonso
 */
public class DepthSearch {

    // TODO: Document.
    public static String programPointDepthSearch(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        NestingLevelTestScript nLTS = new NestingLevelTestScript(tree, parents, parentBaseVariable);

        String res = nLTS.getInitialLines();

        // Update parentBaseVariable
        parentBaseVariable = nLTS.getChildrenParentBaseVariable();

        // Update parents hierarchy
        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        // Generate children test scripts
        for(Tree<String> child: tree.getChildren()) {
            res += programPointDepthSearch(child, updatedParents, parentBaseVariable);
        }

        // Add closing lines (i.e., closing if/for brackets)
        res += nLTS.getClosingLines();

        return res;

    }
}
