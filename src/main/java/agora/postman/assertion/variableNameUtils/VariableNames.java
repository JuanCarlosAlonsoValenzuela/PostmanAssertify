package agora.postman.assertion.variableNameUtils;

import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;

/**
 * @author Juan C. Alonso
 */
public class VariableNames {

    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    public static String getInputVariableName(Parameter parameter) {
        return getInputVariableName(parameter.getName(), parameter.getSchema().getType());
    }


    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    public static String getInputVariableName(String variableName) {
        return getInputVariableName(variableName, "");
    }

    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    private static String getInputVariableName(String parameterName, String parameterType) {

        String inputVariableName = "input_" + parameterName;
        if(parameterType.equals("array")) {
            inputVariableName = inputVariableName + "_array";
        }

        return inputVariableName;
    }

}
