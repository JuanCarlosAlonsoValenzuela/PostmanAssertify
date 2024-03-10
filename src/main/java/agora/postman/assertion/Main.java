package agora.postman.assertion;

import agora.postman.assertion.model.InvariantData;
import agora.postman.assertion.model.ProgramPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        List<InvariantData> invariants = getInvariantsDataFromPath("src/main/resources/test.csv");

        // Get unique pptnames
        // TODO: There can be multiple status codes and multiple API operations
        // TODO: Distinguish between ENTER and EXIT program point names.
        // TODO: ENTER program points are duplicated
        List<String> uniquePptNames = new ArrayList<>();
        for(InvariantData inv: invariants) {
            if(!uniquePptNames.contains(inv.getPptname())) {
                uniquePptNames.add(inv.getPptname());
            }
        }

        // Order pptnames by length in ascending order
        uniquePptNames = uniquePptNames.stream().sorted(Comparator.comparingInt(String::length)).toList();

        // TODO: Iterate over program points by nesting levels deep
//        for(String uniquePptName: uniquePptNames){
//            System.out.println(uniquePptName);
//        }

        String pptname = uniquePptNames.get(0);

        ProgramPoint programPoint = new ProgramPoint(pptname);

        System.out.println(programPoint);

    }
}