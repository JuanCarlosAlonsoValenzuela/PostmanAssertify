package agora.postman.assertion.preRequestScript;

import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.Collection;
import java.util.Set;

import static agora.postman.assertion.GeneratePostmanCollection.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class RequestBodyScript {


    // We assume that there is already a parent variable called request_body that represents the whole response body
    // JSON
    public static String generateBodyParametersScript(RequestBody requestBody) {

        String res = "";

        // Get all the variables (only first nesting level) of the request body
        // The remaining nesting levels are obtained in the Test script
        Collection<MediaType> mediaTypes = requestBody.getContent().values();
        if(mediaTypes.isEmpty()) {
            throw new NullPointerException("Request body is empty");
        }

        // Use only the first schema
        // TODO: Implement if ArraySchema, for now, we assume that it is an object
        Schema requestSchema = mediaTypes.iterator().next().getSchema();


        Set<String> requestSchemaKeyset = requestSchema.getProperties().keySet();
        for(String requestVariableName: requestSchemaKeyset) {
            res += generateSingleBodyPropertyScript(requestVariableName);
        }

        return res;

    }


    private static String generateSingleBodyPropertyScript(String requestVariableName) {

        // Get variable name in the postman format
        String inputVariableName = getInputVariableName(requestVariableName);

        // Add variable assignation
        String res = "// Getting value of the " + requestVariableName + " property of the request body\n";

        res += inputVariableName + " = request_body[\"" + requestVariableName + "\"];\n";

        if(DEBUG_MODE) {
            res += printVariableValueScript(inputVariableName);
        }

        return res;

    }

}
