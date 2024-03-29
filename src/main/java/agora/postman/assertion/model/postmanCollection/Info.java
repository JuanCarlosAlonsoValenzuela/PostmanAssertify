
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.models.OpenAPI;

import static agora.postman.assertion.Main2.POSTMAN_COLLECTION_SCHEMA;

/**
 * @author Juan C. Alonso
 */
public class Info implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("schema")
    @Expose
    private String schema;


    private final static long serialVersionUID = -9168111175055072215L;

    public Info(OpenAPI specification) {
        this.name = specification.getInfo().getTitle();
        this.description = specification.getInfo().getDescription();
        this.schema = POSTMAN_COLLECTION_SCHEMA;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }



}
