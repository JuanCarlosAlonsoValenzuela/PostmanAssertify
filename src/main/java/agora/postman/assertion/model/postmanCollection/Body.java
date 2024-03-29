
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private final static long serialVersionUID = 1394237792582911341L;

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

}
