package agora.postman.assertion.preRequestScript;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.parameters.Parameter;

import java.net.MalformedURLException;
import java.net.URL;

import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class ParametersScript {


    // TODO: DOCUMENT
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
            case "form" ->
                // TODO: IMPLEMENT (requires test cases)
                    throw new RuntimeException("Form parameters not implemented yet");
            // TODO: TEST AGAIN
            case "header" -> inputVariableName + " = pm.request.headers.get(\"" + parameterName + "\");\n";
            default -> throw new RuntimeException("Unexpected value for parameter source, got: " + parameterIn);
        };

        return  res;

    }


    // TODO: DOCUMENT
    // completeURI = server + endpoint
    private static String getVariableValueOfPathParameter(String parameterName, String completeURI) {


        String path = null;
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
    // TODO: DOCUMENT
    public static String generateCastingVariableScript(Parameter parameter) {

        String parameterType = parameter.getSchema().getType();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameter);

        String res = "if (" + inputVariableName + " != null) { \n";

        // Decode URI component
        res += "\t" + inputVariableName + " = decodeURIComponent(" + inputVariableName + ");\n";

        switch (parameterType) {
            case "number" ->
                // TODO: Test with negative number
                // TODO: Create test comparing integer with number and check behavior
                    res = res + "\t" + inputVariableName + " = Number(" + inputVariableName + ");\n";
            case "integer" ->
                // TODO: Test with negative integer
                    res = res + "\t" + inputVariableName + " = parseInt(" + inputVariableName + ");\n";
            case "boolean" -> res = res + "\t" + inputVariableName + " = (" + inputVariableName + " == \"true\");\n";
            case "object" ->
                // TODO: Implement (check properties datatype), requires test cases
                    System.err.println("Object input parameters not implemented");
            case "array" -> {
                // TODO: Convert into a different function (or recursivity), requires test cases

                String separator = ",";

                // TODO: Check items datatype and convert them
                res = res + "\t" + inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => item.trim());\n";

                String itemsDatatype = ((ArraySchema) parameter.getSchema()).getItems().getType();

                if (!itemsDatatype.equals("string")) {
                    // TODO: IMPLEMENT, with test cases
                    throw new RuntimeException("Array of items that are not strings are not supported yet");
                }
            }
            case "string" -> {}
            default -> throw new RuntimeException("Unexpected parameter type: " + parameterType);
        }

        res = res + "}\n";

        return res;

    }

}
