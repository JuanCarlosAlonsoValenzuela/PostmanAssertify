package agora.postman.assertion.preRequestScript;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.parameters.Parameter;

import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class ParametersScript {


    // TODO: DOCUMENT
    // Given a parameter (its source can be one of: query, path, form or header), this function generates the Postman code
    // to obtain its value. The resulting variable will be of type string, use the generateCastingVariableScript function
    // to change its type.
    public static String generateGetVariableValueScript(Parameter parameter) {

        String parameterName = parameter.getName();
        String parameterIn = parameter.getIn();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameter);


        String res = "// Getting value of the " + parameterName + " " + parameterIn + " parameter \n";
        res = switch (parameterIn) {
            case "query" -> res + inputVariableName + " = pm.request.url.query.get(\"" + parameterName + "\");\n";
            // TODO: CHANGE IMPLEMENTATION, pathParamValue = pm.request.url.path[2]
            case "path" -> res + inputVariableName + " = pm.request.url.variables.get(\"" + parameterName + "\");\n";
            case "form" ->
                // TODO: IMPLEMENT (requires test cases)
                    throw new RuntimeException("Form parameters not implemented yet");
            // TODO: TEST AGAIN
            case "header" -> res + inputVariableName + " = pm.request.headers.get(\"" + parameterName + "\");\n";
            default -> throw new RuntimeException("Unexpected value for parameter source, got: " + parameterIn);
        };

        return  res;

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
