package agora.postman.assertion;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;

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
        List<Invariant> invariants = getInvariantsDataFromPath("src/main/resources/test.csv");

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

        // Get all program points with their corresponding invariants
        List<ProgramPoint> allProgramPoints = invariantsGroupedByPptName.entrySet().stream()
                .map(entry -> new ProgramPoint(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingInt(x-> x.getVariableHierarchy().size()))
                .toList();

        for(ProgramPoint ppt: allProgramPoints) {
            System.out.println(ppt);
        }


        

        // TODO: We are assuming that these program points are grouped by endpoint, operationId and responseCode









    }
}