package agora.postman.assertion;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;
import agora.postman.assertion.model.nestingLevelTree.PrintIndentedVisitor;
import agora.postman.assertion.model.nestingLevelTree.Tree;

import java.util.*;
import java.util.stream.Collectors;

import static agora.postman.assertion.files.ReadInvariants.getInvariantsDataFromPath;

/**
 * @author Juan C. Alonso
 */
public class Main {

    public static String HIERARCHY_SEPARATOR = "&";
    public static String ARRAY_NESTING_SEPARATOR = "%";
    public static String ROOT_NAME = "200"; // TODO: REFACTOR/DELETE

    public static void main(String[] args) {

        // TODO: Manage exceptions of this project properly
        // Read invariants from file
        List<Invariant> invariants = getInvariantsDataFromPath("src/main/resources/test2.csv");

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

        Map<String, List<Invariant>> invariantsGroupedByPptName = invariants
                .stream().collect(Collectors.groupingBy(Invariant::getPptname));

        // TODO: We are assuming that these program points are grouped by endpoint, operationId and responseCode

        // Get all program points with their corresponding invariants
        List<ProgramPoint> allProgramPoints = invariantsGroupedByPptName.entrySet().stream()
                .map(entry -> new ProgramPoint(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(x-> x.getVariableHierarchy().size()))
                .toList();


        // Create program point hierarchy tree from list of paths
        Tree<String> programPointHierarchy = getProgramPointHierarchy(allProgramPoints);

        programPointHierarchy.accept(new PrintIndentedVisitor(0));

        // Iterate over program point hierarchy (in-depth search)
        System.out.println("###############################");
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
            // TODO: The first nesting level (response) should not have a closing if/for


            String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

            System.out.println(indentationStr + "\t} // Closing for " + parentBaseVariable);
            System.out.println(indentationStr + "} // Closing if "+ parentBaseVariable);
            System.out.println("\n");
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
            // Assign base variable
            System.out.println("response = pm.response.json();");
            parentBaseVariable = "response";

            System.out.println("// TODO: Postman tests here");
            System.out.println("console.log(\"Printing value of " + parentBaseVariable + "\")");
            System.out.println("console.log(" + parentBaseVariable + ")");


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

            String data = tree.getData();

            // TODO: Null pointer if the array is empty (modify if clause to contemplate this)
            String baseVariableAsignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "." + data;
            parentBaseVariable = parentBaseVariable + "_" + data;

            System.out.println(indentationStr + baseVariableAsignation);
            System.out.println(indentationStr + "if(" + parentBaseVariable + " != null) {");

            String baseVariableIndex = parentBaseVariable + "_index";
            String baseVariableElement = parentBaseVariable + "_element";

            System.out.println(indentationStr + "\tfor(" + baseVariableIndex + " in " + parentBaseVariable + ") {");
            System.out.println(indentationStr + "\t\t" + baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]");


            System.out.println(indentationStr + "\t\t// TODO: Postman tests here");


            parentBaseVariable = baseVariableElement;

            System.out.println(indentationStr + "\t\tconsole.log(\"Printing value of " + parentBaseVariable + "\")");
            System.out.println(indentationStr + "\t\tconsole.log(" + parentBaseVariable + ")");

            // Get invariants of this nesting level
            if(tree.getProgramPoint() != null) {    // TODO: Convert into function
                System.out.println(indentationStr + "\t\t// Invariants of this nesting level:");
                for(Invariant inv: tree.getProgramPoint().getInvariants()) {
                    System.out.println(inv.getPostmanTestCase(parentBaseVariable, indentationStr));
                }
            } else {
                System.out.println("// This nesting level has no invariants");
            }

            System.out.println("\n");



        }


        return parentBaseVariable;

    }


    /**
     * @param paths: List of program point names, separated by HIERARCHY_SEPARATOR
     * @return Program point hierarchy tree, derived from the list of paths.
     */
    // TODO: Update parameters in Javadoc
    // TODO: Move to a different class
    private static Tree<String> getProgramPointHierarchy(List<ProgramPoint> allProgramPoints) {

        // Create a tree with a root node
        // TODO: Use other method for specifying the root
        Tree<String> programPointHierarchy = new Tree<>(ROOT_NAME);

        // Create the variable of type tree that we will iterate on
        Tree<String> current = programPointHierarchy;

        // Iterate over all program points
        // Given the list of all the tree paths, this for loop creates the complete tree
        for(ProgramPoint programPoint: allProgramPoints) {

            // Get the path of this program point
            String path = programPoint.getVariableHierarchyAsString();

            // If the path is not empty, create the path and assign the program point to the last path element
            if(!path.isEmpty()) {
                // Create a variable to store the root
                Tree<String> root = current;

                // For each item of the hierarchy
                for (String data : path.split(HIERARCHY_SEPARATOR)) {
                    // Dive into the tree following the hierarchy by updating the value of current
                    // If the node does not exist, it is added
                    current = current.child(data);
                }

                // Assign the program point to the last element of the path
                current.setProgramPoint(programPoint);

                // Set current to the root value again
                current = root;
            } else {
                // If the path is an empty string, assign the invariants to the root
                // (the path of the first nesting level is an empty string)
                current.setProgramPoint(programPoint);

            }
        }

        return programPointHierarchy;
    }

}