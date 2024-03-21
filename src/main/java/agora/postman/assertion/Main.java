package agora.postman.assertion;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.APIOperation;
import agora.postman.assertion.model.ProgramPoint;
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

    private static String openApiSpecPath = "src/main/resources/oas_github_getOrgRepos.yaml";

    private static String invariantsPath = "src/main/resources/invariants_getOrgRepos_simplified.csv";

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

        // Create variable valuesToConsiderAsNull
        // TODO: Implement properly
        System.out.println("valuesToConsiderAsNull = [];\n");


        // Generates/Prints the Postman tests source code
        // TODO: Convert into method of class APIOperation
        List<String> orderedNestingLevels = programPointsDepthSearch(programPointHierarchy, new ArrayList<>(), new ArrayList<>(), null);

        /**
         * For each of these program points (in-depth search order):
         * 1.- Locate the variable from the response (check for non-null values)
         * 2.- If there is a program point for this nesting level
         * 3.- Generate all the test cases.
          */


    }

    // TODO: Document.
    // TODO: Make everything happen inside this method.
    // TODO: I think I won't be needing the "results" list anymore
    // TODO: The method used for fetching the designed program point is not efficient, modify the tree class to support this
    private static List<String> programPointsDepthSearch(Tree<String> tree, List<String> parents, List<String> results, String parentBaseVariable) {

        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        String result = String.join(HIERARCHY_SEPARATOR, updatedParents);

        results.add(result);

        // Print initial lines of the nesting level
        parentBaseVariable = generateNestingLevelInitialLines(parents, parentBaseVariable, tree, result);


        for(Tree<String> child: tree.getChildren()) {
            results = programPointsDepthSearch(child, updatedParents, results, parentBaseVariable);
        }

        // TODO: Improve this condition
        if(!parents.isEmpty()) {
            // TODO: The first nesting level (response) should not have a closing if/for, unless there is arrayNesting


            String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                System.out.println(indentationStr + "\t} // Closing for " + parentBaseVariable);
            }

            System.out.println(indentationStr + "} // Closing if "+ parentBaseVariable);
            System.out.println("\n");
        }

        // TODO: Create test cases
        // Close array nesting conditions
        Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
        if(!arrayNestingProgramPoints.isEmpty()) {
            int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());
            for(int i=maxArrayNestingLevel; i >= 1; i--) {

                System.out.println("} // Closing if array nesting level " + i);
                System.out.println("} // Closing for array nesting level " + i);

            }
        }

        return results;
    }

    // TODO: Document
    // TODO: This should be a class
    // TODO: Currently returns parentBaseVariable name, refactor to return an object with more information
    // TODO: Implement number of tabulations (based on parents.size)
    // TODO: Improve parameters, create a class or similar
    // TODO: Remove result from parameters, is unnecessary
    public static String generateNestingLevelInitialLines(List<String> parents, String parentBaseVariable, Tree<String> tree, String result) {

        String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

        // Print current nesting level (e.g., 200&data)
        System.out.println(indentationStr + "// " + result);

        if(parents.isEmpty()){  // If we are in the first nesting level

            // TODO: Implement multiple array nesting (%array%array)

            // Assign base variable
            System.out.println("response = pm.response.json();");
            parentBaseVariable = "response";

            // TODO: Implement if response == null ??

            System.out.println("// TODO: Postman tests here");
            if(DEBUG_MODE) {
                System.out.println("console.log(\"Printing value of " + parentBaseVariable + "\")");
                System.out.println("console.log(" + parentBaseVariable + ")");
            }

            // TODO: Print here invariants of nested arrays
            // TODO: Modify Beet so the variable names of the %array program points support nesting (e.g., if %array%array, variableName: return_array_array)
            Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
            // TODO: Multiple nesting levels, but maybe not all of them are present (e.g., maybe there are invariants fro %array and %array%array%array, but not for %array%array)
            if(!arrayNestingProgramPoints.isEmpty()) {
                int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());

                // TODO: START HERE
                // Iterate over all the array nesting levels
                for(int i=1; i<= maxArrayNestingLevel; i++) {
                    // Get nesting level variable
                    // parentBaseVariable is an array, we have to iterate over it
                    System.out.println("if(" + parentBaseVariable + " != null) { ");

                    System.out.println("// TODO: Generate tests of this nesting level (if any)");
                    System.out.println("// ...");

                    System.out.println("// Access to the next nesting level");

                    String baseVariableIndex = parentBaseVariable + "_index";
                    String baseVariableElement = parentBaseVariable + "_element";

                    System.out.println("for (" + baseVariableIndex + " in " + parentBaseVariable + ") {");
                    System.out.println(baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]");

                    parentBaseVariable = baseVariableElement;


                    // Update parentBaseVariable

                    // If the nesting level contains invariants, create the test cases

                }
                // TODO: END HERE
            }

            // Get invariants of this nesting level
            // TODO: Convert into function
            if(tree.getProgramPoint() != null) {
                System.out.println("// Invariants of this nesting level:");
                for(Invariant inv: tree.getProgramPoint().getInvariants()) {
                    System.out.println(inv.getPostmanTestCase(parentBaseVariable, indentationStr));
                }
            } else {
                System.out.println("// This nesting level has no invariants");
            }

            System.out.println("\n");

        } else {    // If we are in a deeper nesting level

            // TODO: Implement multiple array nesting (%array)

            String data = tree.getData();

            String baseVariableAsignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "." + data;
            parentBaseVariable = parentBaseVariable + "_" + data;

            System.out.println(indentationStr + baseVariableAsignation);
            System.out.println(indentationStr + "if(" + parentBaseVariable + " != null) {");

            // If the nesting type value is array
            if(tree.getNestingType().equals(NestingType.ARRAY)) {

                String baseVariableIndex = parentBaseVariable + "_index";
                String baseVariableElement = parentBaseVariable + "_element";

                System.out.println(indentationStr + "\tfor(" + baseVariableIndex + " in " + parentBaseVariable + ") {");
                System.out.println(indentationStr + "\t\t" + baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]");

                parentBaseVariable = baseVariableElement;

            }

            System.out.println(indentationStr + "\t\t// TODO: Postman tests here");




            if(DEBUG_MODE) {
                System.out.println(indentationStr + "\t\tconsole.log(\"Printing value of " + parentBaseVariable + "\")");
                System.out.println(indentationStr + "\t\tconsole.log(" + parentBaseVariable + ")");
            }

            // Get invariants of this nesting level
            if(tree.getProgramPoint() != null) {    // TODO: Convert into function
                System.out.println(indentationStr + "\t\t// Invariants of this nesting level:");
                indentationStr = indentationStr + "\t\t";
                for(Invariant inv: tree.getProgramPoint().getInvariants()) {
                    System.out.println(inv.getPostmanTestCase(parentBaseVariable, indentationStr));
                }
            } else {
                System.out.println(indentationStr + "\t\t// This nesting level has no invariants");
            }

            System.out.println("\n");



        }


        return parentBaseVariable;

    }


    // TODO: Move to a different class
    private static OpenAPI getOpenAPISpecification(){

        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolveFully(true);
        parseOptions.setFlatten(true);

        return new OpenAPIV3Parser().read(openApiSpecPath, null, parseOptions);
    }

}