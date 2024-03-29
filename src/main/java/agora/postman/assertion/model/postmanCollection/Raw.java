
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Raw implements Serializable
{

    @SerializedName("language")
    @Expose
    private String language;
    private final static long serialVersionUID = 69221603936962708L;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
