package agora.postman.assertion.model;

/**
 * @author Juan C. Alonso
 */
public class Variable {

    // Variable name (format returned by AGORA)
    private String variableName;

    // Source of the input variable (query, path, or body), null if the variable is part of the API response
    // Its value is null by default
    private InputVariableSource inputVariableSource;

    public Variable(String variableName) {
        this.variableName = variableName;
        this.inputVariableSource = null;
    }

    public String getVariableName() {
        return variableName;
    }

    public InputVariableSource getInputVariableSource() {
        return inputVariableSource;
    }

    public void setInputVariableSource(InputVariableSource inputVariableSource) {
        this.inputVariableSource = inputVariableSource;
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


}
