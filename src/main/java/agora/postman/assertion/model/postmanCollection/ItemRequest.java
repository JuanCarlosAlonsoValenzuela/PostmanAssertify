
package agora.postman.assertion.model.postmanCollection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agora.postman.assertion.model.APIOperation;
import agora.postman.assertion.mutation.MutatedTestCase;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Juan C. Alonso
 */
public class ItemRequest implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("event")
    @Expose
    private List<Event> event;
    @SerializedName("request")
    @Expose
    private Request request;
    @SerializedName("response")
    @Expose
    private List<Object> response;
    @SerializedName("protocolProfileBehavior")
    @Expose
    private ProtocolProfileBehavior protocolProfileBehavior;
    private final static long serialVersionUID = 849339939549916296L;

    // Standard PostmanCollection creation
    public ItemRequest(APIOperation apiOperation, String[] valuesToConsiderAsNull) {
        this.name = apiOperation.getOperationId() + "_" + apiOperation.getResponseCode();
        this.event = Collections.singletonList(new Event(apiOperation, valuesToConsiderAsNull));
        this.request = new Request(apiOperation);
        this.response = new ArrayList<>();
        this.protocolProfileBehavior = new ProtocolProfileBehavior();
    }

    // Postman collection creation for experiment 2 (JSONMutator)
    public ItemRequest(APIOperation apiOperation, String[] valuesToConsiderAsNull, String testName, MutatedTestCase mutatedTestCase) {
        this.name = testName;
        this.event = Collections.singletonList(new Event(apiOperation, valuesToConsiderAsNull, mutatedTestCase.getResponseBody()));

        this.request = new Request(
                apiOperation,
                mutatedTestCase.getHeaderParameters(),
                mutatedTestCase.getQueryParameters(),
                mutatedTestCase.getPathParameters(),
                mutatedTestCase.getFormParameters(),
                mutatedTestCase.getBodyParameter()
        );

        this.response = new ArrayList<>();
        this.protocolProfileBehavior = new ProtocolProfileBehavior();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Event> getEvent() {
        return event;
    }

    public void setEvent(List<Event> event) {
        this.event = event;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public List<Object> getResponse() {
        return response;
    }

    public void setResponse(List<Object> response) {
        this.response = response;
    }

    public ProtocolProfileBehavior getProtocolProfileBehavior() {
        return protocolProfileBehavior;
    }

    public void setProtocolProfileBehavior(ProtocolProfileBehavior protocolProfileBehavior) {
        this.protocolProfileBehavior = protocolProfileBehavior;
    }

}
