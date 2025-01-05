package agora.postman.assertion.testScript;

import agora.postman.assertion.testScript.nestingLevelTree.Tree;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class DepthSearch {


    public static String programPointDepthSearch(Tree<String> tree, List<String> parents, String parentBaseVariable,
                                                 String response, List<Parameter> parameters, RequestBody requestBody) {

        NestingLevelTestScript nLTS = new NestingLevelTestScript(tree, parents, parentBaseVariable,
                response, parameters, requestBody);

        String res = nLTS.getInitialLines();

        // Update parentBaseVariable
        parentBaseVariable = nLTS.getChildrenParentBaseVariable();

        // Update parents hierarchy
        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        // Generate children test scripts
        for(Tree<String> child: tree.getChildren()) {
            res += programPointDepthSearch(child, updatedParents, parentBaseVariable, response, parameters, requestBody);
        }

        // Add closing lines (i.e., closing if/for brackets)
        res += nLTS.getClosingLines();

        return res;

    }
}
