package agora.postman.assertion.files;

import agora.postman.assertion.model.APIOperation;
import agora.postman.assertion.model.Invariant;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.IOException;
import java.util.List;

import static agora.postman.assertion.files.CSVManager.readCSV;

/**
 * @author Juan C. Alonso
 */
public class ReadInvariants {


    // TODO: Document
    public static List<APIOperation> getAllApiOperations(OpenAPI specification, String invariantsPath) {

        // Read the invariants csv as a list of rows

        // Iterate over all rows (invariants)

        // Create the API operation if it does not exist

        // Add the program point to the APIOperation if it does not exist

        // Create the invariant and add it to the program point

    }

    // TODO: Rename and move to a different class
    // TODO: REMOVE (OLD)
    public static List<Invariant> getInvariantsDataFromPath(OpenAPI specification, String invariantsPath) {

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
