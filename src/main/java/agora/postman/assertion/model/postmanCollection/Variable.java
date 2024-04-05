
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.models.parameters.Parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Variable implements Serializable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    private final static long serialVersionUID = -4241441786828262136L;

    /**
     * @param parameters: List of all the parameters of an operation (including different sources)
     * @return List of variable objects, created from all the input parameters that use "path" as source
     */
    public static List<Variable> getAllPathVariables(List<Parameter> parameters) {
        List<Variable> res = new ArrayList<>();

        if(parameters != null) {
            for (Parameter parameter : parameters) {
                if (parameter.getIn().equals("path")) {
                    res.add(new Variable(parameter));
                }
            }
        }
        return res;
    }

    public Variable(Parameter parameter) {
        this.key = parameter.getName();
        this.value = "";
    }

    /**
     * This method is used in Postman collection creation for experiment 2 (JSONMutator)
     * @param pathParameters Map where keys: path parameter names and the values: variable values
     * @return List of variable objects,  containing these key-value pairs
     */
    public static List<Variable> getAllPathVariables(Map<String, String> pathParameters) {
        List<Variable> res = new ArrayList<>();

        for(String pathParameterName: pathParameters.keySet()) {
            res.add(new Variable(
                    pathParameterName,
                    pathParameters.get(pathParameterName)
            ));
        }

        return res;
    }

    public Variable(String variableName, String variableValue) {
        this.key = variableName;
        this.value = variableValue;
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

}
