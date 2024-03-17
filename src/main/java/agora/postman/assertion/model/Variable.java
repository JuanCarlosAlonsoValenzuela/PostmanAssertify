package agora.postman.assertion.model;

import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.Arrays;
import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class Variable {

    // Variable name (format returned by AGORA)
    private String variableName;

    // Source of the input variable (query, path, or body), null if the variable is part of the API response
    // Its value is null by default
    private VariableType variableType;

    private boolean isSize;
    private List<String> variableHierarchyList;

    public Variable(String variableName, List<Parameter> parameters) {
        this.variableName = variableName;

        // This method sets the values of the "variableHierarchyList", "isSize" and "isReturn" attributes
        this.setVariableHierarchyList(parameters);

    }

    public String getVariableName() {
        return variableName;
    }

    public VariableType getVariableType() {
        return variableType;
    }

    public boolean isSize() {
        return isSize;
    }


    public List<String> getVariableHierarchyList() {
        return variableHierarchyList;
    }

    // TODO: THIS METHOD MUST BE IDENTICAL TO THE ONE IN DAIKON, every modification performed here must be performed in Daikon too!!!
    // TODO: Document properly (with multiple input/output example)
    // TODO: Consider moving to a different class
    // TODO: Program points that are nested arrays? (e.g., GitHub)
    // Returns the variable name in the format used in the Postman assertion
    public String getPostmanVariableName() {

        String originalVariableName = this.variableName;

        // TODO: Test this if clause
        if(!originalVariableName.startsWith("input.") &&
                !originalVariableName.startsWith("return.") &&
                !originalVariableName.startsWith("size(input.") &&
                !originalVariableName.startsWith("size(return.")
        ) {
            throw new RuntimeException("Unexpected variable name");
        }

        String postmanVariableName = originalVariableName;

        // If the variable is the size of an array
        if(postmanVariableName.startsWith("size(")) {

            // Remove "size(" (at the start) and ")" (at the end) characters
            postmanVariableName = postmanVariableName.substring("size(".length(), postmanVariableName.length() - 1);

            // Add suffix
            postmanVariableName = postmanVariableName + "_size";
        }

        // Remove array special characters and add a suffix indicating that the variable is an array
        if (postmanVariableName.contains("[]") || postmanVariableName.contains("[..]")) {

            // Remove characters
            postmanVariableName = postmanVariableName.replace("[]", "");
            postmanVariableName = postmanVariableName.replace("[..]", "");

            // Add suffix
            postmanVariableName = postmanVariableName + "_array";
        }

        // Replace variable hierarchy separator with snake_case
        postmanVariableName = postmanVariableName.replace(".", "_");


        return postmanVariableName;

    }


    /**
     * This method is used ONLY IN THE CONSTRUCTOR to set the value of the variableHierarchyList attribute, using
     * variableName as parameter, it also sets the "isSize" and "isReturn" attributes
     */
    private void setVariableHierarchyList(List<Parameter> parameters) {

        // Split AGORA variable name to extract variable hierarchy
        String variableHierarchyString = this.variableName;

        // Determine whether the variable is the size of an array
        this.isSize = false;
        if(variableHierarchyString.startsWith("size(")) {

            variableHierarchyString = variableHierarchyString
                    .substring("size(".length(), variableHierarchyString.length() - 1);

            this.isSize = true;
        }

        // Remove array characters
        variableHierarchyString = variableHierarchyString.replace("[]", "");
        variableHierarchyString = variableHierarchyString.replace("[..]", "");

        List<String> variableHierarchyList;

        if(variableHierarchyString.startsWith("return.") || variableHierarchyString.startsWith("input.")) {
//            TODO: IMPLEMENT ENTER
            variableHierarchyList = Arrays.asList(variableHierarchyString.split("\\."));

            boolean isReturn = variableHierarchyList.get(0).equals("return");
            variableHierarchyList = variableHierarchyList.subList(1, variableHierarchyList.size());

            // Set the value
            this.variableHierarchyList = variableHierarchyList;

            // Determine whether the variable is a parameter or a response field
            if(isReturn) {
                this.variableType = VariableType.RETURN;
            } else {
                // Determine source of input parameter (QUERY, PATH or BODY)
                this.variableType = getInputParameterType(parameters);
            }

        } else {
            throw new RuntimeException("Unexpected AGORA variable name");
        }

        if(variableHierarchyList.isEmpty()) {
            throw new RuntimeException("Variable hierarhy list cannot be empty");
        }

    }

    private VariableType getInputParameterType(List<Parameter> parameters) {
        // TODO: Create jUnit test case for each parameter type
        // TODO: Implement body
        // TODO: Implement form parameters
        String firstHierarchyElement = this.variableHierarchyList.get(0);
        for(Parameter parameter: parameters) {
            if(parameter.getName().equals(firstHierarchyElement)) {

                String inValue = parameter.getIn();

                if(inValue.equals("query")) {
                    return VariableType.QUERY;
                }else if(inValue.equals("path")) {
                    return VariableType.PATH;
                } else {
                    throw new NullPointerException("Unexpected variable type");
                }

            }

        }

        throw new NullPointerException("Parameter with name: " + firstHierarchyElement + " not found");

    }


}
