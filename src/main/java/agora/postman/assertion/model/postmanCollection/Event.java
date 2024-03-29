
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Event implements Serializable
{

    @SerializedName("listen")
    @Expose
    private String listen;
    @SerializedName("script")
    @Expose
    private Script script;
    private final static long serialVersionUID = 8095976627350601624L;

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

}
