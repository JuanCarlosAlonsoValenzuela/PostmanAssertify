package agora.postman.assertion.testScript;

import agora.postman.assertion.testScript.nestingLevelTree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class DepthSearch {

    // TODO: Document.
    public static String programPointDepthSearch(Tree<String> tree, List<String> parents, String parentBaseVariable, String response) {

        NestingLevelTestScript nLTS = new NestingLevelTestScript(tree, parents, parentBaseVariable, response);

        String res = nLTS.getInitialLines();

        // Update parentBaseVariable
        parentBaseVariable = nLTS.getChildrenParentBaseVariable();

        // Update parents hierarchy
        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        // Generate children test scripts
        for(Tree<String> child: tree.getChildren()) {
            res += programPointDepthSearch(child, updatedParents, parentBaseVariable, response);
        }

        // Add closing lines (i.e., closing if/for brackets)
        res += nLTS.getClosingLines();

        return res;

    }
}
