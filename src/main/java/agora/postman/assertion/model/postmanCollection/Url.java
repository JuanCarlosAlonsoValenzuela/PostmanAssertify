
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Url implements Serializable
{

    @SerializedName("raw")
    @Expose
    private String raw;
    @SerializedName("protocol")
    @Expose
    private String protocol;
    @SerializedName("host")
    @Expose
    private List<String> host;
    @SerializedName("path")
    @Expose
    private List<String> path;
    @SerializedName("query")
    @Expose
    private List<Query> query;
    @SerializedName("variable")
    @Expose
    private List<Variable> variable;
    private final static long serialVersionUID = 2243623560081581229L;

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<Query> getQuery() {
        return query;
    }

    public void setQuery(List<Query> query) {
        this.query = query;
    }

    public List<Variable> getVariable() {
        return variable;
    }

    public void setVariable(List<Variable> variable) {
        this.variable = variable;
    }

}
