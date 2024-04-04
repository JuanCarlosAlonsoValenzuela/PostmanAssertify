
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.Map;

import agora.postman.assertion.model.APIOperation;
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

    // For now, we only generate test scripts, no pre-request scripts
    public Event(APIOperation apiOperation) {
        this.listen = "test";
        this.script = new Script(apiOperation);

    }

    // Postman collection creation for experiment 2 (JSONMutator)
    public Event(APIOperation apiOperation, String responseBody) {
        this.listen = "test";
        this.script = new Script(apiOperation, responseBody);
    }

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
