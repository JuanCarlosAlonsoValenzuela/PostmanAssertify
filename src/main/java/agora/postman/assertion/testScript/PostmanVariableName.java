package agora.postman.assertion.testScript;

/**
 * @author Juan C. Alonso
 */
public class PostmanVariableName {

    // TODO: THIS METHOD MUST BE IDENTICAL TO THE ONE IN DAIKON, every modification performed here must be performed in Daikon too!!!
    // TODO: Document properly (with multiple input/output example)
    // TODO: Program points that are nested arrays? (e.g., GitHub)
    // Returns the variable name in the format used in the Postman assertion
    public static String getPostmanVariableName(String originalVariableName) {

        if(!originalVariableName.startsWith("input.") &&
                !originalVariableName.startsWith("return.") &&
                !originalVariableName.startsWith("size(input.") &&
                !originalVariableName.startsWith("size(return.")
        ) {
            throw new RuntimeException("Unexpected variable name");
        }

        String postmanVariableName = originalVariableName;

        // If the variable contains shift
        String shiftSuffix = "";
        if (postmanVariableName.matches(".* [+][0-9]+$")) {            // increase
            int plusIndex = postmanVariableName.lastIndexOf(" +");
            shiftSuffix = "_plus_" + postmanVariableName.substring(plusIndex+2);
            postmanVariableName = postmanVariableName.substring(0, plusIndex);

        } else if (postmanVariableName.matches(".* -[0-9]+$")) {    // decrease
            int minusIndex = postmanVariableName.lastIndexOf(" -");
            shiftSuffix = "_minus_" + postmanVariableName.substring(minusIndex+2);
            postmanVariableName = postmanVariableName.substring(0, minusIndex);
        }

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
            postmanVariableName += "_array";
        }

        // If the variable name is accessing an array element
        // (e.g., return.data.results[return.data.offset] or return.data.results[return.data.count-1])
        if (postmanVariableName.contains("[") && postmanVariableName.contains("]")) {
            int firstBracketIndex = postmanVariableName.indexOf("[");
            int lastBracketIndex = postmanVariableName.lastIndexOf("]");

            String arrayElementVariable = postmanVariableName.substring(firstBracketIndex + 1, lastBracketIndex);

            // Update Postman variable name by adding the formatted the array element variable
            // e.g., return.data[return.offset] -> return_data_return_offset
            // e.g., return.data[1] -> return_data_1
            postmanVariableName = postmanVariableName.substring(0, firstBracketIndex) + "_" +
                    (arrayElementVariable.matches("^[0-9]+$")
                            // if the array element is a number (e.g., return.data.results[1])
                            ? arrayElementVariable
                            // if the array element is another variable (e.g., return.data.results[return.data.offset])
                            : getPostmanVariableName(arrayElementVariable));
        }

        // Add shift suffix
        postmanVariableName += shiftSuffix;

        // Kebab case is not supported in JS
        postmanVariableName = postmanVariableName.replace("-", "_");
        // Replace variable hierarchy separator with snake_case
        postmanVariableName = postmanVariableName.replace(".", "_");


        return postmanVariableName;

    }

}
