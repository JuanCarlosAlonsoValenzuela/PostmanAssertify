
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import agora.postman.assertion.model.APIOperation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.checkerframework.checker.units.qual.A;

/**
 * @author Juan C. Alonso
 */
public class Script implements Serializable
{

    @SerializedName("exec")
    @Expose
    private List<String> exec;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("packages")
    @Expose
    private Packages packages;
    private final static long serialVersionUID = 5950281285453780232L;

    public Script(APIOperation apiOperation) {
        this.exec = generateExec(apiOperation);
        this.type = "text/javascript";
        this.packages = new Packages();
    }

    private static List<String> generateExec(APIOperation apiOperation) {
        return Arrays.stream(
                (apiOperation.generateInputParametersScript() + apiOperation.generateTestScript(new ArrayList<>()))
                        .split("\n")
                )
                .map(x-> x + "\r")
                .toList();
    }

    public List<String> getExec() {
        return exec;
    }

    public void setExec(List<String> exec) {
        this.exec = exec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

}
