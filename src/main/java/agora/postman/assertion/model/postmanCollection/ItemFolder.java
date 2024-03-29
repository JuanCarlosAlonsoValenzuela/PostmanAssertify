
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import agora.postman.assertion.model.APIOperation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.models.OpenAPI;

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

    public ItemFolder(String endpointName, List<APIOperation> endpointOperations) {
        this.name = endpointName;

        List<ItemRequest> itemRequests = new ArrayList<>();
        for(APIOperation apiOperation: endpointOperations) {
            itemRequests.add(new ItemRequest(apiOperation));
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
