package agora.postman.assertion.files;

import agora.postman.assertion.model.APIOperation;
import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static agora.postman.assertion.files.CSVManager.readCSV;

/**
 * @author Juan C. Alonso
 */
public class ReadInvariants {


    // TODO: Document
    public static List<APIOperation> getAllApiOperations(OpenAPI specification, String invariantsPath) {

        // Read all the API invariants from a CSV file (invariantsPath), the specification is used to determine the
        // source of the input variables (QUERY, PATH, BODY, FORM)
        List<Invariant> allInvariants = getInvariantsDataFromPath(specification, invariantsPath);

        // Group all the invariants per pptname
        Map<String, List<Invariant>> invariantsGroupedByPptName = allInvariants
                .stream().collect(Collectors.groupingBy(Invariant::getPptname));

        // Create the list of all the API program points, each one of them containing its list of invariants
        List<ProgramPoint> allProgramPoints = new ArrayList<>();
        for(String programPointName: invariantsGroupedByPptName.keySet()) {
            allProgramPoints.add(new ProgramPoint(programPointName, invariantsGroupedByPptName.get(programPointName)));
        }

        // Group all the program points by their corresponding operation
        Map<String, List<ProgramPoint>> programPointsGroupedByApiOperation = allProgramPoints.stream()
                .collect(Collectors.groupingBy(ProgramPoint::getApiOperationIdentifier));

        // Create the list of all the API operations, each one of them containing the list of program points
        List<APIOperation> allApiOperations = new ArrayList<>();
        for(String operationIdentifier: programPointsGroupedByApiOperation.keySet()) {
            System.out.println("OPERATION IDENTIFIER: " + operationIdentifier);
            List<ProgramPoint> apiOperationProgramPoints = programPointsGroupedByApiOperation.get(operationIdentifier);

            // Any of the pptnames of the operations is valid
            String pptname = apiOperationProgramPoints.get(0).getPptname();

            allApiOperations.add(new APIOperation(pptname, apiOperationProgramPoints, specification));
        }

        return allApiOperations;

    }

    // TODO: Rename and move to a different class
    // TODO: REMOVE (OLD)
    private static List<Invariant> getInvariantsDataFromPath(OpenAPI specification, String invariantsPath) {

        // Read the csv file as a list of rows
        List<List<String>> rows = readCSV(invariantsPath, true, ';');

        List<String> header = rows.get(0);

        rows = rows.subList(1, rows.size());

        InvariantDataFileManager invariantDataFileManager = null;
        try {
            invariantDataFileManager = new InvariantDataFileManager(header);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Invariant> invariantsData = invariantDataFileManager.getInvariantsData(specification, rows);

        return invariantsData;

    }

}
