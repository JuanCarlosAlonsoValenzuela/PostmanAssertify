package agora.postman.assertion.model;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.media.Schema;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Juan C. Alonso
 */
// TODO: Consider renaming this class because it also includes the response code
public class APIOperation {

    private final String endpoint;
    private final String operationId;
    private final int responseCode;

    // These attributes are from the OAS
    private final List<Parameter> parameters;
    private final RequestBody requestBody;
    private final Schema responseSchema;

    public APIOperation(String endpoint, String operationId, int responseCode, OpenAPI specification) {
        this.endpoint = endpoint;
        this.operationId = operationId;
        this.responseCode = responseCode;

        // Get the operation of the OAS with the endpoint and the operationId
        Operation oasOperation = getOASOperation(specification, endpoint, operationId);

        // Get the parameters
        this.parameters = oasOperation.getParameters();
        // Get the request body
        this.requestBody = oasOperation.getRequestBody();

        // Get the schema of the response with responseCode
        // TODO: Use Schema vs use ArraySchema
        this.responseSchema = getResponseSchema(oasOperation, responseCode);

    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getOperationId() {
        return operationId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Schema getResponseSchema() {
        return responseSchema;
    }

    // TODO: DOCUMENT
    private static Operation getOASOperation(OpenAPI specification, String targetEndpoint, String targetOperationId) {

        Paths paths = specification.getPaths();

        // A path (endpoint) contains several operations (http methods/verbs)
        for(Map.Entry<String, PathItem> path: paths.entrySet()) {

            String operationEndpoint = path.getKey();

            if(operationEndpoint.equals(targetEndpoint)) {

                PathItem pathItem = path.getValue();

                for(Map.Entry<PathItem.HttpMethod, Operation> operationEntry: pathItem.readOperationsMap().entrySet()) {

                    Operation operation = operationEntry.getValue();
                    String operationId = operation.getOperationId();

                    if(operationId.equals(targetOperationId)) {
                        return operation;
                    }
                }
            }
        }

        throw new NullPointerException("Could not find operation with endpoint: " + targetEndpoint + " and operation id: " + targetOperationId + " in OAS");

    }

    // TODO: DOCUMENT
    private static Schema getResponseSchema(Operation oasOperation, int responseCode) {

        Collection<MediaType> mediaTypes = oasOperation
                .getResponses()
                .get(Integer.toString(responseCode))
                .getContent()
                .values();

        if(mediaTypes.isEmpty()) {
            throw new NullPointerException("No media types found for operation " + oasOperation.getOperationId());
        }

        // Return the first schema
        return mediaTypes.iterator().next().getSchema();

    }

}
