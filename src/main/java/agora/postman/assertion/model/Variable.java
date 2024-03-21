package agora.postman.assertion.model;

import java.util.Arrays;
import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class Variable {

    // Variable name (format returned by AGORA)
    private String variableName;

    private boolean isReturn;

    // TODO: Add isArrayElement attribute
    private boolean isSize;
    private List<String> variableHierarchyList;

    public Variable(String variableName) {
        this.variableName = variableName;

        // This method sets the values of the "variableHierarchyList", "isSize" and "isReturn" attributes
        this.setVariableHierarchyList();

    }

    public String getVariableName() {
        return variableName;
    }

    public boolean isReturn() {
        return isReturn;
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
    private void setVariableHierarchyList() {

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

            this.isReturn = variableHierarchyList.get(0).equals("return");
            variableHierarchyList = variableHierarchyList.subList(1, variableHierarchyList.size());

            // Set the value
            this.variableHierarchyList = variableHierarchyList;

        } else {
            throw new RuntimeException("Unexpected AGORA variable name");
        }

        if(variableHierarchyList.isEmpty()) {
            throw new RuntimeException("Variable hierarchy list cannot be empty");
        }

    }

}
