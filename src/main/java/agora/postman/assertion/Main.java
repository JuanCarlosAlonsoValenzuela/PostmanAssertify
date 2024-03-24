package agora.postman.assertion;

import agora.postman.assertion.model.*;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;
import org.checkerframework.checker.units.qual.A;

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
        OpenAPI specification = getOpenAPISpecification(openApiSpecPath);
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

        System.out.println("//////////////////////////// INPUT PARAMETERS SCRIPT ////////////////////////////");
        // TODO: CHANGE NAME (NOW IT IS PART OF THE TEST SCRIPT AGAIN)
        String preRequestScript = apiOperation.generatePreRequestScript();
        System.out.println(preRequestScript);

        // Iterate over program point hierarchy (in-depth search)
        System.out.println("////////////////////////////  TEST SCRIPT ////////////////////////////");
        List<String> valuesToConsiderAsNull = new ArrayList<>(); // TODO: Create test case
//        valuesToConsiderAsNull.add("N/A");
//        valuesToConsiderAsNull.add("");

        String testScript = apiOperation.generateTestScript(valuesToConsiderAsNull);
        System.out.println(testScript);


        /**
         * For each of these program points (in-depth search order):
         * 1.- Locate the variable from the response (check for non-null values)
         * 2.- If there is a program point for this nesting level
         * 3.- Generate all the test cases.
          */

    }



    // TODO: Move to a different class
    public static OpenAPI getOpenAPISpecification(String oasPath){

        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolveFully(true);
        parseOptions.setFlatten(true);

        return new OpenAPIV3Parser().read(oasPath, null, parseOptions);
    }

}