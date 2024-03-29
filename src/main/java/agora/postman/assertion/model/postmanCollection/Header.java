
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.models.parameters.Parameter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class Header implements Serializable
{

    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("disabled")
    @Expose
    private Boolean disabled;
    private final static long serialVersionUID = -6136304426128785655L;

    /**
     * @param parameters: List of all the parameters of an operation (including different sources)
     * @return List of headers objects, created from all the input parameters that use "header" as source
     */
    public static List<Header> getAllHeaders(List<Parameter> parameters) {
        List<Header> res = new ArrayList<>();

        for(Parameter parameter: parameters) {
            if(parameter.getIn().equals("header")) {
                res.add(new Header(parameter));
            }
        }
        return res;
    }

    public Header(Parameter parameter) {
        this.key = parameter.getName();
        this.value = "";
        this.type = "";
        this.disabled = true;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

}
