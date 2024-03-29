
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    private final static long serialVersionUID = 2856330039437107078L;

    /**
     * @param parameters: List of all the parameters of an operation (including different sources)
     * @return List of query objects, created from all the input parameters that use "query" as source
     */
    public static List<Query> getAllQueryParameters(List<Parameter> parameters) {
        List<Query> res = new ArrayList<>();

        for(Parameter parameter: parameters){
            if(parameter.getIn().equals("query")) {
                res.add(new Query(parameter));
            }
        }

        return res;
    }

    public Query(Parameter parameter) {
        this.key = parameter.getName();
        this.value = "";
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
