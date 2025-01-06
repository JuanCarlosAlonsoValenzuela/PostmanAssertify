
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.yaml.snakeyaml.util.UriEncoder;

/**
 * @author Juan C. Alonso
 */
public class Body implements Serializable
{

    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("formdata")
    @Expose
    private List<Object> formdata;
    @SerializedName("raw")
    @Expose
    private String raw;
    @SerializedName("options")
    @Expose
    private Options options;
    @SerializedName("urlencoded")
    @Expose
    private List<UrlEncoded> urlEncodedItems;

    private final static long serialVersionUID = 1394237792582911341L;

    // Standard Postman collection
    public Body() {}

    // Postman collection creation for experiment 2 (JSONMutator)
    // The body can be either a raw JSON or a set of form parameters
    public Body(Map<String, String> formParameters, String bodyParameter) {
        if (bodyParameter != null && !bodyParameter.isEmpty()) {
            this.mode = "raw";
            this.raw = bodyParameter;
            this.options = new Options("json");
        } else if (formParameters != null) {
            this.mode = "urlencoded";

            // Add form parameters as items of the urlencoded array
            List<UrlEncoded> urlEncodedItems = new ArrayList<>();
            for(String formParameterKey: formParameters.keySet()) {
                urlEncodedItems.add(new UrlEncoded(formParameterKey, formParameters.get(formParameterKey)));
            }
            this.urlEncodedItems = urlEncodedItems;

        } else { // Default value: empty body
            this.mode = "raw";
            this.raw = "";
            this.options = new Options("json");
        }

    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public List<Object> getFormdata() {
        return formdata;
    }

    public void setFormdata(List<Object> formdata) {
        this.formdata = formdata;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public List<UrlEncoded> getUrlEncodedItems() {
        return urlEncodedItems;
    }

    public void setUrlEncodedItems(List<UrlEncoded> urlEncodedItems) {
        this.urlEncodedItems = urlEncodedItems;
    }
}
