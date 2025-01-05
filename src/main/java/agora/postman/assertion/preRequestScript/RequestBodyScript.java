package agora.postman.assertion.preRequestScript;

import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static agora.postman.assertion.GeneratePostmanCollection.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.preRequestScript.ParametersScript.generateCastingVariableScript;
import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class RequestBodyScript {


    // We assume that there is already a parent variable called request_body that represents the whole response body
    // JSON
    public static String generateBodyParametersScript(RequestBody requestBody) {

        StringBuilder res = new StringBuilder();

        // Get all the variables (only first nesting level) of the request body
        // The remaining nesting levels are obtained in the Test script
        Collection<String> mediaTypeFormats = requestBody.getContent().keySet();
        Collection<MediaType> mediaTypes = requestBody.getContent().values();
        if(mediaTypes.isEmpty()) {
            throw new NullPointerException("Request body is empty");
        }

        // Use only the first schema
        // TODO: Implement if ArraySchema, for now, we assume that it is an object
        String mediaTypeFormat = mediaTypeFormats.iterator().next();
        Schema requestSchema = mediaTypes.iterator().next().getSchema();

        // If the request body format is x-www-form-urlencoded
        if (mediaTypeFormat.contains("x-www-form-urlencoded")) {
            // Get the request body as variable
            res.append("let request_body = pm.request.body.urlencoded;\n");

            if(DEBUG_MODE) {
                res.append(printVariableValueScript("request_body"));
            }

            Map<String, Schema> requestSchemaDictionary = requestSchema.getProperties();
            for(String parameterName: requestSchemaDictionary.keySet()) {
                res.append(generateSingleFormUrlEncodedParameterScript(parameterName, requestSchemaDictionary.get(parameterName)));
            }

        } else {    // If the request body follows other format (e.g., application/json)
            // Get the request body itself
            res.append("let request_body = JSON.parse(pm.request.body.raw);\n");

            if(DEBUG_MODE) {
                res.append(printVariableValueScript("request_body"));
            }

            Set<String> requestSchemaKeyset = requestSchema.getProperties().keySet();
            for(String requestVariableName: requestSchemaKeyset) {
                res.append(generateSingleBodyPropertyScript(requestVariableName));
            }
        }


        return res.toString();

    }

    // Used to get each one of the parameters of a raw (i.e., application/json) request body
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

    // Used to get each one of the parameters of a form (i.e., x-www-form-urlencoded) request body
    private static String generateSingleFormUrlEncodedParameterScript(String requestVariableName, Schema parameterSchema) {

        // Get variable name in the postman format
        String inputVariableName = getInputVariableName(requestVariableName, parameterSchema.getType());

        // Add variable assignation
        String res = "// Getting value of the " + requestVariableName + " parameter\n";

        res += inputVariableName + " = (request_body.find(param => param.key === \"" + requestVariableName + "\") || {}).value || null;\n";

        // Cast variable to its corresponding datatype
        res += generateCastingVariableScript(requestVariableName, parameterSchema);

        if(DEBUG_MODE) {
            res += printVariableValueScript(inputVariableName);
        }

        return res;
    }

}
