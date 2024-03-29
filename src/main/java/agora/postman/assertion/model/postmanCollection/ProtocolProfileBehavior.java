
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class ProtocolProfileBehavior implements Serializable
{

    @SerializedName("disableBodyPruning")
    @Expose
    private Boolean disableBodyPruning;
    private final static long serialVersionUID = 6415001091087088832L;

    public ProtocolProfileBehavior() {
        this.disableBodyPruning = true;
    }

    public Boolean getDisableBodyPruning() {
        return disableBodyPruning;
    }

    public void setDisableBodyPruning(Boolean disableBodyPruning) {
        this.disableBodyPruning = disableBodyPruning;
    }

}
