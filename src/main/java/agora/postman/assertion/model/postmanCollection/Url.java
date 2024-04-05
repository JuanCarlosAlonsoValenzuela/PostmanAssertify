
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import agora.postman.assertion.model.APIOperation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static agora.postman.assertion.model.postmanCollection.Query.getAllQueryParameters;
import static agora.postman.assertion.model.postmanCollection.Variable.getAllPathVariables;

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

    // Standard PostmanCollection creation
    public Url(APIOperation apiOperation) {
        this.raw = apiOperation.getServer() + apiOperation.getEndpoint();

        URL url;
        try {
            url = new URL(apiOperation.getServer() + apiOperation.getEndpoint());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

        this.protocol = url.getProtocol();
        this.host = Arrays.asList(url.getHost().split("\\."));

        // Remove the first "/" and replace the path parameters delimiters ("{" and "}") with the ":" character,
        // used in Postman to identify path variables
        this.path = Arrays.stream(url.getPath()
                .replaceFirst("/", "")
                .replace("{", ":")
                .replace("}", "").split("/"))
                .toList();

        // Get the query parameters
        this.query = getAllQueryParameters(apiOperation.getParameters());

        // Set the path parameters as Postman variables
        this.variable = getAllPathVariables(apiOperation.getParameters());

    }

    // Postman collection creation for experiment 2 (JSONMutator)
    public Url(APIOperation apiOperation, Map<String, String> queryParameters, Map<String, String> pathParameters) {
        this.raw = apiOperation.getServer() + apiOperation.getEndpoint();

        URL url;
        try {
            url = new URL(apiOperation.getServer() + apiOperation.getEndpoint());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }

        this.protocol = url.getProtocol();
        this.host = Arrays.asList(url.getHost().split("\\."));

        // Remove the first "/" and replace the path parameters delimiters ("{" and "}") with the ":" character,
        // used in Postman to identify path variables
        this.path = Arrays.stream(url.getPath()
                        .replaceFirst("/", "")
                        .replace("{", ":")
                        .replace("}", "").split("/"))
                .toList();

        // Get the query parameters
        // TODO: Decode URI components
        this.query = getAllQueryParameters(queryParameters);

        // Set the path parameters as Postman variables
        // TODO: Decode URI components
        this.variable = getAllPathVariables(pathParameters);

    }

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
