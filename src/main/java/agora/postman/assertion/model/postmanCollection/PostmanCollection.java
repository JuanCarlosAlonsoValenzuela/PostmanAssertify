
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private List<ItemFolder> itemFolder;
    private final static long serialVersionUID = 8612284934075767955L;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<ItemFolder> getItem() {
        return itemFolder;
    }

    public void setItem(List<ItemFolder> itemFolder) {
        this.itemFolder = itemFolder;
    }

}
