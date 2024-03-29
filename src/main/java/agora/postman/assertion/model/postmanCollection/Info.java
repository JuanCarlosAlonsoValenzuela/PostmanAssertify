
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Info implements Serializable
{

    @SerializedName("_postman_id")
    @Expose
    private String postmanId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("schema")
    @Expose
    private String schema;
    @SerializedName("_exporter_id")
    @Expose
    private String exporterId;
    private final static long serialVersionUID = -9168111175055072215L;

    public String getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(String postmanId) {
        this.postmanId = postmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getExporterId() {
        return exporterId;
    }

    public void setExporterId(String exporterId) {
        this.exporterId = exporterId;
    }

}
