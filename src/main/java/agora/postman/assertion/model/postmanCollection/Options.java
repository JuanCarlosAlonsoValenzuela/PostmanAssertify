
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Options implements Serializable
{

    @SerializedName("raw")
    @Expose
    private Raw raw;
    private final static long serialVersionUID = -5859399138921385836L;

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }

}
