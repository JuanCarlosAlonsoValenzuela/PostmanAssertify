
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public PostmanCollection(OpenAPI specification, String invariantsPath) {
        this.info = new Info(specification);

        List<ItemFolder> itemFolders = new ArrayList<>();

        // Get all the API operations grouped by endpoints
        Map<String, List<APIOperation>> apiOperationsByEndpoint = getAllApiOperations(specification, invariantsPath)
                .stream()
                .collect(groupingBy(APIOperation::getEndpoint));

        for(String endpointApiOperations: apiOperationsByEndpoint.keySet()) {
            // Create a folder, each folder contains all the operations with a specific endpoint
            itemFolders.add(new ItemFolder(endpointApiOperations, apiOperationsByEndpoint.get(endpointApiOperations)));
        }

        this.itemFolders = itemFolders;
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
