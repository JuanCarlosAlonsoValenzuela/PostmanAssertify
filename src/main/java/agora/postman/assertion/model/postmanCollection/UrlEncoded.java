package agora.postman.assertion.model.postmanCollection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Juan C. Alonso
 */
public class UrlEncoded implements Serializable {

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("disabled")
    @Expose
    private boolean disabled;

    // Postman collection creation for experiment 2 (JSONMutator)
    public UrlEncoded(String parameterName, String parameterValue) {
        this.key = URLDecoder.decode(parameterName, StandardCharsets.UTF_8);
        this.value = parameterValue;
        this.type = "text";
        this.disabled = false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

}
