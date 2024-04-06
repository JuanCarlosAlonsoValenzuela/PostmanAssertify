
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.*;

import agora.postman.assertion.model.APIOperation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.models.OpenAPI;

import static agora.postman.assertion.files.ReadInvariants.getAllApiOperations;
import static java.util.stream.Collectors.groupingBy;

/**
 * @author Juan C. Alonso
 */
public class PostmanCollection implements Serializable
{

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("item")
    @Expose
    private List<ItemFolder> itemFolders;
    private final static long serialVersionUID = 8612284934075767955L;

    // Standard PostmanCollection creation
    public PostmanCollection(OpenAPI specification, String invariantsPath, String[] valuesToConsiderAsNull) {
        this.info = new Info(specification);

        List<ItemFolder> itemFolders = new ArrayList<>();

        // Get all the API operations grouped by endpoints
        Map<String, List<APIOperation>> apiOperationsByEndpoint = getAllApiOperations(specification, invariantsPath)
                .stream()
                .collect(groupingBy(APIOperation::getEndpoint));

        for(String endpointApiOperations: apiOperationsByEndpoint.keySet()) {
            // Create a folder, each folder contains all the operations with a specific endpoint
            itemFolders.add(new ItemFolder(endpointApiOperations, apiOperationsByEndpoint.get(endpointApiOperations), valuesToConsiderAsNull));
        }

        this.itemFolders = itemFolders;
    }

    // Postman collection creation for experiment 2 (JSONMutator), it can only contain a single APIOperation
    public PostmanCollection(OpenAPI specification, String invariantsPath, String[] valuesToConsiderAsNull, String configurationName, String mutantsPath) {
        this.info = new Info(specification);

        // Get all the API operations grouped by endpoints
        List<APIOperation> apiOperations = getAllApiOperations(specification, invariantsPath);

        // In experiment 2, there can only be one APIOperation
        if(apiOperations.size() != 1) {
            throw new IllegalArgumentException("Experiment 2 only supports a maximum of one API operation");
        }

        // A single ItemFolder
        this.itemFolders = Collections.singletonList(new ItemFolder(configurationName, apiOperations.get(0), valuesToConsiderAsNull, mutantsPath));

    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<ItemFolder> getItem() {
        return itemFolders;
    }

    public void setItem(List<ItemFolder> itemFolders) {
        this.itemFolders = itemFolders;
    }

}
