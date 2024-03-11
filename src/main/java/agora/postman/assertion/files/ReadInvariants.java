package agora.postman.assertion.files;

import agora.postman.assertion.model.Invariant;

import java.io.IOException;
import java.util.List;

import static agora.postman.assertion.files.CSVManager.readCSV;

/**
 * @author Juan C. Alonso
 */
public class ReadInvariants {

    public static List<Invariant> getInvariantsDataFromPath(String invariantsPath) {

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

        List<Invariant> invariantsData = invariantDataFileManager.getInvariantsData(rows);

        return invariantsData;

    }

}
