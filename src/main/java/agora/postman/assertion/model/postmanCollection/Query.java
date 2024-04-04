
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * @author Juan C. Alonso
 */
public class Query implements Serializable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("disabled")
    @Expose
    private Boolean disabled;
    private final static long serialVersionUID = 2856330039437107078L;

    /**
     * @param parameters: List of all the parameters of an operation (including different sources)
     * @return List of query objects, created from all the input parameters that use "query" as source (disabled by default)
     */
    public static List<Query> getAllQueryParameters(List<Parameter> parameters) {
        List<Query> res = new ArrayList<>();

        if(parameters != null) {
            for (Parameter parameter : parameters) {
                if (parameter.getIn().equals("query")) {
                    res.add(new Query(parameter));
                }
            }
        }

        return res;
    }

    public Query(Parameter parameter) {
        this.key = parameter.getName();
        this.value = "";
        this.disabled = true;
    }

    /**
     * This method is used in Postman collection creation for experiment 2 (JSONMutator)
     * @param queryParameters: Map where keys: enabled query parameter names and the values: variable values
     * @return List of query objects, containing these key-value pairs, all of them enabled
     */
    public static List<Query> getAllQueryParameters(Map<String, String> queryParameters) {
        List<Query> res = new ArrayList<>();

        for(String queryParameterName: queryParameters.keySet()) {
            res.add(new Query(
                    queryParameterName,
                    queryParameters.get(queryParameterName)
            ));
        }

        return res;
    }

    public Query(String queryParameterName, String queryParameterValue) {
        this.key = queryParameterName;
        this.value = queryParameterValue;
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

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
