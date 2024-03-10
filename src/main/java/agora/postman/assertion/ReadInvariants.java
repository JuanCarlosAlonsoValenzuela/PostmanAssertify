package agora.postman.assertion;

import agora.postman.assertion.files.InvariantDataFileManager;
import agora.postman.assertion.model.InvariantData;

import java.io.IOException;
import java.util.List;

import static agora.postman.assertion.files.CSVManager.readCSV;

/**
 * @author Juan C. Alonso
 */
public class ReadInvariants {

    public static List<InvariantData> getInvariantsDataFromPath(String invariantsPath) {

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

        List<InvariantData> invariantsData = invariantDataFileManager.getInvariantsData(rows);

        return invariantsData;

    }

}
