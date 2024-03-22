package agora.postman.assertion.testScript;

import agora.postman.assertion.testScript.nestingLevelTree.Tree;

import java.util.ArrayList;
import java.util.List;

import static agora.postman.assertion.Main.HIERARCHY_SEPARATOR;

/**
 * @author Juan C. Alonso
 */
public class DepthSearch {

    // TODO: Move to a different class
    // TODO: Document.
    // TODO: Make everything happen inside this method.
    // TODO: I think I won't be needing the "results" list anymore
    // TODO: The method used for fetching the designed program point is not efficient, modify the tree class to support this
    public static String programPointDepthSearch(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        // Update parents hierarchy (TODO: Is this necessary?)
        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        // TODO: Rename result variable (both here and in method parameters)
        // TODO: Is this necessary?
        String result = String.join(HIERARCHY_SEPARATOR, updatedParents);

        NestingLevelTestScript nLTS = new NestingLevelTestScript(parents, parentBaseVariable, tree, result);

        String res = nLTS.getInitialLines();

        // Update parentBaseVariable
        parentBaseVariable = nLTS.getChildrenParentBaseVariable();



        for(Tree<String> child: tree.getChildren()) {
            res += programPointDepthSearch(child, updatedParents, parentBaseVariable);
        }

        res += nLTS.getClosingLines();

        return res;

    }
}
