
package agora.postman.assertion.model.postmanCollection;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agora.postman.assertion.model.APIOperation;
import agora.postman.assertion.mutation.MutatedTestCase;
import agora.postman.assertion.mutation.MutatedTestCaseFileManager;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.models.OpenAPI;

import static agora.postman.assertion.files.CSVManager.getCSVRecord;
import static agora.postman.assertion.mutation.MutatedTestCaseFileManager.readMutatedTestCasesFromPath;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Juan C. Alonso
 */
public class ItemFolder implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item")
    @Expose
    private List<ItemRequest> items;
    private final static long serialVersionUID = -2746514765098961986L;

    // Standard PostmanCollection creation
    public ItemFolder(String endpointName, List<APIOperation> endpointOperations, String[] valuesToConsiderAsNull) {
        this.name = endpointName;

        List<ItemRequest> itemRequests = new ArrayList<>();
        for(APIOperation apiOperation: endpointOperations) {
            itemRequests.add(new ItemRequest(apiOperation, valuesToConsiderAsNull));
        }

        this.items = itemRequests;

    }

    // Postman collection creation for experiment 2 (JSONMutator), it can only contain a single APIOperation
    public ItemFolder(String endpointName, APIOperation apiOperation, String[] valuesToConsiderAsNull, String mutantsPath) {
        this.name = endpointName;

        // Read mutants CSV file
        List<MutatedTestCase> mutatedTestCases = readMutatedTestCasesFromPath(mutantsPath);

        // Create one ItemRequest per mutant
        List<ItemRequest> itemRequests = new ArrayList<>();
        int nTest = 1;
        for(MutatedTestCase mutatedTestCase: mutatedTestCases) {
            itemRequests.add(new ItemRequest(apiOperation, valuesToConsiderAsNull, String.format("Test_%03d", nTest), mutatedTestCase));
            nTest++;
        }
        this.items = itemRequests;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemRequest> getItem() {
        return items;
    }

    public void setItem(List<ItemRequest> items) {
        this.items = items;
    }

}
