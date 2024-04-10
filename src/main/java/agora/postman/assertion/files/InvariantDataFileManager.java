package agora.postman.assertion.files;

import agora.postman.assertion.model.Invariant;

import java.io.IOException;
import java.util.*;

/**
 * @author Juan C. Alonso
 */
public class InvariantDataFileManager {

    private final Integer pptnameIndex;
    private final Integer invariantIndex;
    private final Integer invariantTypeIndex;
    private final Integer variablesIndex;
    private final Integer tpIndex;
    private final Integer postmanAssertionIndex;

    public InvariantDataFileManager(List<String> headers) throws IOException {

        this.pptnameIndex = getIndexOfElementInHeaders(headers, "pptname");
        this.invariantIndex = getIndexOfElementInHeaders(headers, "invariant");
        this.invariantTypeIndex = getIndexOfElementInHeaders(headers, "invariantType");
        this.variablesIndex = getIndexOfElementInHeaders(headers, "variables");
        this.tpIndex = getIndexOfElementInHeaders(headers, "tp");
        this.postmanAssertionIndex = getIndexOfElementInHeaders(headers, "postmanAssertion");

    }

    private static int getIndexOfElementInHeaders(List<String> headers, String header){
        for(int i = 0; i < headers.size(); i++) {
            if(headers.get(i).equalsIgnoreCase(header)) {
                return i;
            }
        }
        throw new NullPointerException("Element " + header + " not found in the csv headers");
    }

    public List<Invariant> getInvariantsData(List<List<String>> rows){

        List<Invariant> res = new ArrayList<>();

        for(List<String> row: rows) {

            int tpValue = Integer.parseInt(row.get(tpIndex));

            // Add the invariant iff is a true positive
            if(tpValue==1){

                // Read invariants with input parameters info
                Invariant invariantData = new Invariant(
                        row.get(pptnameIndex), row.get(invariantIndex), row.get(invariantTypeIndex),
                        getVariablesFromSingleInvariantData(row.get(variablesIndex)), row.get(postmanAssertionIndex)
                );

                res.add(invariantData);

            }

        }

        return res;

    }



    // Converts the list of variables in string format into a proper list. e.g., "(input.name, return.name)" -> ["input.name", "return.name"]
    private static List<String> getVariablesFromSingleInvariantData(String variablesAsString) {

        // Remove initial and final parentheses
        variablesAsString = variablesAsString.substring(1, variablesAsString.length()-1);

        // Split by comma
        String[] variables = variablesAsString.split(",");

        // Add all elements
        List<String> res = new ArrayList<>();
        for(String variable: variables){
            res.add(variable.trim());
        }

        return res;

    }


}
