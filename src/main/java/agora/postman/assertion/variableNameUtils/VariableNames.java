package agora.postman.assertion.variableNameUtils;

import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * @author Juan C. Alonso
 */
public class VariableNames {

    // Used to get an input variable name in the pre-request script
    // Must be consistent with getPostmanVariableName method
    public static String getInputVariableName(Parameter parameter) {
        return getInputVariableName(parameter.getName(), parameter.getSchema().getType());
    }


    // Used to get an input variable name in the pre-request script
    // Must be consistent with getPostmanVariableName method
    public static String getInputVariableName(String variableName) {
        return getInputVariableName(variableName, "");
    }

    // Used to get an input variable name in the pre-request script
    // Must be consistent with getPostmanVariableName method
    public static String getInputVariableName(String parameterName, String parameterType) {

        String inputVariableName = "input_" + parameterName;
        if(parameterType.equals("array")) {
            inputVariableName = inputVariableName + "_array";
        } else {
            // Replace array characters
            inputVariableName = inputVariableName.replace("[", "_");
            inputVariableName = inputVariableName.replace("]", "");
        }

        // Kebab case is not supported in JS
        inputVariableName = inputVariableName.replace("-", "_");
        // Replace variable hierarchy separator with snake_case
        inputVariableName = inputVariableName.replace(".", "_");

        return inputVariableName;
    }

}
