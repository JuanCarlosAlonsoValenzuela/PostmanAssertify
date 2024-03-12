package agora.postman.assertion;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;
import agora.postman.assertion.model.nestingLevelTree.PrintIndentedVisitor;
import agora.postman.assertion.model.nestingLevelTree.Tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static agora.postman.assertion.files.ReadInvariants.getInvariantsDataFromPath;

/**
 * @author Juan C. Alonso
 */
public class Main {

    public static String HIERARCHY_SEPARATOR = "&";
    public static String ARRAY_NESTING_SEPARATOR = "%";

    public static void main(String[] args) {

        // TODO: Manage exceptions of this project properly
        // Read invariants from file
        List<Invariant> invariants = getInvariantsDataFromPath("src/main/resources/test3.csv");

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


        // Get all the tree paths from the list of program point names
        List<String> paths = allProgramPoints.stream().map(ProgramPoint::getVariableHierarchyAsString).filter(x->!x.isEmpty()).toList();

        // Create program point hierarchy tree from list of paths
        Tree<String> programPointHierarchy = getProgramPointHierarchy(paths);

        programPointHierarchy.accept(new PrintIndentedVisitor(0));

        // Iterate over program point hierarchy (in-depth search)
        List<String> orderedNestingLevels = programPointsDepthSearch(programPointHierarchy, new ArrayList<>(), new ArrayList<>());

        /**
         * For each of these program points (in-depth search order):
         * 1.- Locate the variable from the response (check for non-null values)
         * 2.- If there is a program point for this nesting level
         * 3.- Generate all the test cases.
          */

        System.out.println("######################################################");
        for(String nestingLevel: orderedNestingLevels) {
            System.out.println(nestingLevel);
        }






    }

    // TODO: Document
    private static List<String> programPointsDepthSearch(Tree<String> tree, List<String> parents, List<String> results) {

        String data = tree.getData();

        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(data);

        String result = String.join(HIERARCHY_SEPARATOR, updatedParents);
        results.add(result);
//        System.out.println(result);

        for(Tree<String> child: tree.getChildren()) {
            results = programPointsDepthSearch(child, updatedParents, results);
        }

        return results;
    }


    /**
     * @param paths: List of program point names, separated by HIERARCHY_SEPARATOR
     * @return Program point hierarchy tree, derived from the list of paths.
     */
    // TODO: Move to a different class
    private static Tree<String> getProgramPointHierarchy(List<String> paths) {

        // Create a tree with a root node
        // TODO: Use other method for specifying the root
        Tree<String> programPointHierarchy = new Tree<>("200");

        // Create the variable of type tree that we will iterate on
        Tree<String> current = programPointHierarchy;

        // Given a list of tree paths, this for loop creates the complete tree
        for(String path: paths) {
            // Create a variable to store the root
            Tree<String> root = current;

            // For each item of the hierarchy
            for(String data: path.split(HIERARCHY_SEPARATOR)) {
                // Dive into the tree following the hierarchy by updating the value of current
                // If the node does not exist, it is added
                current = current.child(data);
            }

            // Set current to the root value again
            current = root;
        }

        return programPointHierarchy;
    }

}