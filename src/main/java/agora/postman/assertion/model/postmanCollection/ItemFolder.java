
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ItemFolder implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("item")
    @Expose
    private List<ItemRequest> item;
    private final static long serialVersionUID = -2746514765098961986L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemRequest> getItem() {
        return item;
    }

    public void setItem(List<ItemRequest> item) {
        this.item = item;
    }

}
