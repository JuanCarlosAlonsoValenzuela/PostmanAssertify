package agora.postman.assertion.preRequestScript;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.net.MalformedURLException;
import java.net.URL;

import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class ParametersScript {


    // Given a parameter (its source can be one of: query, path, form or header), this function generates the Postman code
    // to obtain its value. The resulting variable will be of type string, use the generateCastingVariableScript function
    // to change its type.
    public static String generateGetVariableValueScript(Parameter parameter, String completeURI) {

        String parameterName = parameter.getName();
        String parameterIn = parameter.getIn();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameter);


        String res = "// Getting value of the " + parameterName + " " + parameterIn + " parameter \n";
        res += switch (parameterIn) {
            case "query" -> inputVariableName + " = pm.request.url.query.get(\"" + parameterName + "\");\n";
            case "path" -> inputVariableName + getVariableValueOfPathParameter(parameterName, completeURI);
            case "header" -> inputVariableName +
                    " = pm.request.headers.find(header => !header.disabled && header.key==\"" + parameterName + "\");\n" +
                    "if(" + inputVariableName + " != null){\n" +
                    inputVariableName + " = " + inputVariableName + ".value;\n" +
                    "}\n";
            case "formData" -> inputVariableName + " = (pm.request.body.urlencoded.find(param => param.key === \"" + parameterName + "\") || {}).value || null;\n";
            default -> throw new RuntimeException("Unexpected value for parameter source, got: " + parameterIn);
        };

        return  res;

    }


    // completeURI = server + endpoint
    private static String getVariableValueOfPathParameter(String parameterName, String completeURI) {

        String path;
        try {
            path = new URL(completeURI).getPath();
        } catch (MalformedURLException e) {
            throw new RuntimeException("The value of the URI: " + completeURI + " is not valid");
        }
        path = path.replaceFirst("/", "");

        String[] endpointElements = path.split("/");
        for(int i = 0; i < endpointElements.length; i++) {
            String endpointElement = endpointElements[i];
            if(endpointElement.equals("{" + parameterName + "}")) {
                return " = pm.request.url.path[" + i + "];\n";
            }
        }
        throw new NullPointerException("Path parameter " + parameterName + " not found in endpoint: " + completeURI);
    }


    // Generates the Postman code necessary to cast an input parameter to its corresponding datatype
    public static String generateCastingVariableScript(Parameter parameter) {
        return generateCastingVariableScript(parameter.getName(), parameter.getSchema());
    }

    // Generates the Postman code necessary to cast an input parameter to its corresponding datatype
    public static String generateCastingVariableScript(String parameterName, Schema parameterSchema) {

        String parameterType = parameterSchema.getType();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameterName, parameterType);

        String res = "if (" + inputVariableName + " != null) { \n";

        // Decode URI component
        res += inputVariableName + " = decodeURIComponent(" + inputVariableName + ");\n";

        switch (parameterType) {
            case "number" -> res = res + inputVariableName + " = Number(" + inputVariableName + ");\n";
            case "integer" -> res = res + inputVariableName + " = parseInt(" + inputVariableName + ");\n";
            case "boolean" -> res = res + inputVariableName + " = (" + inputVariableName + " == \"true\");\n";
            case "array" -> {

                String separator = ",";
                String itemsDatatype = ((ArraySchema) parameterSchema).getItems().getType();

                // Check items datatype and convert them
                res+= switch (itemsDatatype) {
                    case "string" -> inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => item.trim());\n";
                    case "integer" -> inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => parseInt(item.trim()));\n";
                    case "number" -> inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => Number(item.trim()));\n";
                    case "boolean" -> inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => item.trim() == \"true\");\n";
                    default -> throw new RuntimeException("Unexpected input array items datatype: " + itemsDatatype);
                };

            }
            case "string" -> {}
            default -> throw new RuntimeException("Unexpected parameter type: " + parameterType);
        }

        res += "}\n";

        return res;

    }

}
