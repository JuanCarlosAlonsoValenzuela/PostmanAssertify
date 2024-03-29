
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import agora.postman.assertion.model.APIOperation;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static agora.postman.assertion.model.postmanCollection.Header.getAllHeaders;

/**
 * @author Juan C. Alonso
 */
public class Request implements Serializable
{

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("header")
    @Expose
    private List<Header> header;
    @SerializedName("url")
    @Expose
    private Url url;
    @SerializedName("body")
    @Expose
    private Body body;
    private final static long serialVersionUID = 5015692323996114517L;

    public Request(APIOperation apiOperation) {
        this.method = apiOperation.getMethod();

        this.header = getAllHeaders(apiOperation.getParameters());
        this.url = new Url(apiOperation);
        // The body is empty
        this.body = new Body();
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

}
