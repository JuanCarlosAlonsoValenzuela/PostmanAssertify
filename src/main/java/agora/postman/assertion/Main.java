package agora.postman.assertion;

import agora.postman.assertion.model.*;
import agora.postman.assertion.model.nestingLevelTree.NestingType;
import agora.postman.assertion.model.nestingLevelTree.PrintIndentedVisitor;
import agora.postman.assertion.model.nestingLevelTree.Tree;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;

import java.util.*;

import static agora.postman.assertion.files.ReadInvariants.getAllApiOperations;

/**
 * @author Juan C. Alonso
 */
public class Main {

    private static String openApiSpecPath = "src/main/resources/oas_vimeo.yaml";

    private static String invariantsPath = "src/main/resources/test3.csv";

    public static String HIERARCHY_SEPARATOR = "&";
    public static String ARRAY_NESTING_SEPARATOR = "%";

    public static boolean DEBUG_MODE = true;

    public static void main(String[] args) {

        // TODO: Manage exceptions of this project properly
        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification();
        List<APIOperation> allApiOperations = getAllApiOperations(specification, invariantsPath);



        // Get unique pptnames
        // TODO: There can be multiple status codes and multiple API operations
        // TODO: Distinguish between ENTER and EXIT program point names.
        // TODO: ENTER program points are duplicated

        /**
         * TODOs:
         * 1.- Group all program points (ppts) by: endpoint, operationId, responseCode
         * 2.- Order ppts by length of their variable hierarchy list
         * 3.- Create hierarchy, increasing size one by one
         * 4.- If there are still ppts not in the hierarchy, assign them to the closest point in the hierarchy
         * 5.- Apply to both ENTER and EXIT ppts (for now, only EXIT)
         */


        // TODO: We are assuming that these program points are grouped by endpoint, operationId and responseCode
        // TODO: This tree is for a single operation
        // Create program point hierarchy tree from list of paths
        // TODO: Change from static method to method of the APIOperation class

        APIOperation apiOperation = allApiOperations.get(0);

        Tree<String> programPointHierarchy = apiOperation.getProgramPointHierarchy();

        // Print nesting level tree
        programPointHierarchy.accept(new PrintIndentedVisitor(3));


        System.out.println("############################### PRE-REQUEST SCRIPT ###############################");
        String preRequestScript = apiOperation.generatePreRequestScript();
        System.out.println(preRequestScript);

        // Iterate over program point hierarchy (in-depth search)
        System.out.println("############################### TEST SCRIPT ###############################");
        String testScript = apiOperation.generateTestScript();
        System.out.println(testScript);


        /**
         * For each of these program points (in-depth search order):
         * 1.- Locate the variable from the response (check for non-null values)
         * 2.- If there is a program point for this nesting level
         * 3.- Generate all the test cases.
          */


    }

    // TODO: Move to a different class
    // TODO: Document.
    // TODO: Make everything happen inside this method.
    // TODO: I think I won't be needing the "results" list anymore
    // TODO: The method used for fetching the designed program point is not efficient, modify the tree class to support this
    public static String programPointDepthSearch(Tree<String> tree, List<String> parents, List<String> results, String parentBaseVariable) {

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
            res += programPointDepthSearch(child, updatedParents, results, parentBaseVariable);
        }

        res += nLTS.getClosingLines();

        return res;

    }

    // TODO: Move to a different class
    private static OpenAPI getOpenAPISpecification(){

        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolveFully(true);
        parseOptions.setFlatten(true);

        return new OpenAPIV3Parser().read(openApiSpecPath, null, parseOptions);
    }

}