
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Options implements Serializable
{

    @SerializedName("raw")
    @Expose
    private Raw raw;
    private final static long serialVersionUID = -5859399138921385836L;

    // Postman collection creation for experiment 2 (JSONMutator)
    // Creates options raw with language = json
    public Options(String languageValue) {
        this.raw = new Raw(languageValue);
    }

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }

}
