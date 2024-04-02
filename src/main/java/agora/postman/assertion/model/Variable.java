package agora.postman.assertion.model;

import java.util.Arrays;
import java.util.List;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;

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

    private String shift;

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

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "variableName='" + variableName + '\'' +
                ", isReturn=" + isReturn +
                ", isSize=" + isSize +
                ", variableHierarchyList=" + variableHierarchyList +
                ", shift='" + shift + '\'' +
                '}';
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

    // TODO: DOCUMENT
    // TODO: Split into multiple methods
    public String getPostmanVariableValueCode(String parentBaseVariable, boolean isArrayNestingPpt) {

        String postmanVariableName = this.getPostmanVariableName();

        List<String> variableHierarchyList = this.getVariableHierarchyList();

        int ifBracketsToClose = 0;

        String res = "// Getting value of variable: " + postmanVariableName + "\n";

        if(this.isReturn()) {  // Generate code for getting return variables

            if(isArrayNestingPpt) { // Array nesting program points (i.e., %array) only have one return variable (return_array)

                // TODO: Create test cases for array element
                res += postmanVariableName + " = " + parentBaseVariable + ";\n";

            } else {    // If normal program point
                // First line/nested variable
                res += postmanVariableName + " = " + parentBaseVariable + "." + variableHierarchyList.get(0) + ";\n";

                // TODO: START CONVERT INTO FUNCTION (DUPLICATED)
                for(int i = 1; i < variableHierarchyList.size(); i++) {

                    // Check that the variable is not null
                    res += "if(" + postmanVariableName + " != null) {\n";

                    String variableHierarchyElement = variableHierarchyList.get(i);

                    // If there is shift in the LAST ELEMENT. This happens if the element ends with +/- followed by an integer
                    // (e.g., return.user.age-1)
                    if((i == variableHierarchyList.size()-1) && (variableHierarchyElement.matches(".*[+-]{1}[0-9]{1,}$"))) {

                        // Access next hierarchy element (without using the shift value)
                        int lastIndex = Math.max(
                                variableHierarchyElement.lastIndexOf("+"),
                                variableHierarchyElement.lastIndexOf("-")
                        );

                        String nextHierarchyElement = variableHierarchyElement.substring(0, lastIndex);

                        res += postmanVariableName + " = " + postmanVariableName + "." + nextHierarchyElement + ";\n";

                        // If the variable is not null
                        res += "if(" + postmanVariableName + " != null) {\n";

                        // Compute shift
                        String shiftString = variableHierarchyElement.substring(lastIndex);

                        res += postmanVariableName + " = " + postmanVariableName + " " + shiftString + ";\n";

                        // Increment number of if brackets to close (1 more for shift)
                        ifBracketsToClose++;

                    } else {
                        // Access next hierarchy element
                        res += postmanVariableName + " = " + postmanVariableName + "." + variableHierarchyElement + ";\n";
                    }

                    // Increment the number of if brackets to close
                    ifBracketsToClose++;
                }
                // TODO: END CONVERT INTO FUNCTION (DUPLICATED)
            }


        } else {    // Generate code for getting input variables (parameters)
            // Only if there is nesting level OR if the input variable is the size of an array
            if(this.isSize() || variableHierarchyList.size() != 1) {

                // Get name of the variable obtained in the pre-request script (i.e., only first hierarchy level)
                // TODO: START CONVERT INTO FUNCTION 1
                // TODO: Conflict with shift?
                String firstHierarchyLevelVariableName = "input_" + variableHierarchyList.get(0);
                // If the variable in the pre-request script is an array
                // Only the last hierarchy element can be an array
                if((variableHierarchyList.size() == 1) && (this.variableName.contains("[]") || this.variableName.contains("[..]"))) {
                    firstHierarchyLevelVariableName += "_array";
                }
                // TODO: END CONVERT INTO FUNCTION 1

                // First hierarchy element
                res += postmanVariableName + " = " + firstHierarchyLevelVariableName + ";\n";


                // TODO: START CONVERT INTO FUNCTION (DUPLICATED)
                // Access to the subsequent hierarchy elements
                for(int i=1; i<variableHierarchyList.size(); i++) {     // TODO: This code is duplicated (convert into function)

                    // Check that the variable is not null
                    res += "if(" + postmanVariableName + " != null) {\n";

                    String variableHierarchyElement = variableHierarchyList.get(i);

                    // If there is shift in the LAST ELEMENT. This happens if the element ends with +/- followed by an integer
                    // (e.g., return.user.age-1)
                    if((i == variableHierarchyList.size()-1) && (variableHierarchyElement.matches(".*[+-]{1}[0-9]{1,}$"))) {

                        // Access next hierarchy element (without using the shift value)
                        int lastIndex = Math.max(
                                variableHierarchyElement.lastIndexOf("+"),
                                variableHierarchyElement.lastIndexOf("-")
                        );

                        String nextHierarchyElement = variableHierarchyElement.substring(0, lastIndex);

                        res += postmanVariableName + " = " + postmanVariableName + "." + nextHierarchyElement + ";\n";

                        // If the variable is not null
                        res += "if(" + postmanVariableName + " != null) {\n";

                        // Compute shift
                        String shiftString = variableHierarchyElement.substring(lastIndex);

                        res += postmanVariableName + " = " + postmanVariableName + " " + shiftString + ";\n";

                        // Increment number of if brackets to close (1 more for shift)
                        ifBracketsToClose++;

                    } else {
                        // Access next hierarchy element
                        res += postmanVariableName + " = " + postmanVariableName + "." + variableHierarchyElement + ";\n";
                    }

                    // Increment the number of if brackets to close
                    ifBracketsToClose++;

                }  // TODO: END CONVERT INTO FUNCTION

            } else if (variableHierarchyList.get(0).matches(".*[+-]{1}[0-9]{1,}$")) {
                // If the input variable has hierarchy size == 1 and has shift
                // TODO: An input variable with hierarchy size == 1 can have shift. IMPLEMENT!!!
                // TODO: Test and check variable name behavior

                // Access next hierarchy element (without using the shift value)
                String variableHierarchyElement = variableHierarchyList.get(0);
                int lastIndex = Math.max(
                        variableHierarchyElement.lastIndexOf("+"),
                        variableHierarchyElement.lastIndexOf("-")
                );

                // If the variable is not null
                res += "if(" + postmanVariableName + " != null) {\n";

                // Compute shift
                String shiftString = variableHierarchyElement.substring(lastIndex);

                res += postmanVariableName + " = " + postmanVariableName + " " + shiftString + ";\n";

                // Increment number of if brackets to close (1 more for shift)
                ifBracketsToClose++;

            }

        }


        // Close if brackets (common for both input and exit)
        while(ifBracketsToClose > 0) {

            // Close if bracket
            res += "}\n";

            ifBracketsToClose--;
        }


        // THIS IS COMMON TO BOTH INPUT AND RETURN
        // If the variable is the size of an array
        // Get array size
        if(this.isSize()) {
            // If the retrieved array is not null
            res += "if(" + postmanVariableName + " != null) {\n";

            // Get array size, and add shift if not null
            String shiftValue = (this.shift != null) ? " " + this.shift: "";
            res += postmanVariableName + " = " + postmanVariableName + ".length" + shiftValue + ";\n";


            // Close the bracket
            res += "}\n\n";
        }

        if(DEBUG_MODE) {
            res+= printVariableValueScript(postmanVariableName);
        }

        res += "\n";

        return res;
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
            this.isSize = true;

            // If there is some type of shift. For example: size(return.users[])-1
            if(!variableHierarchyString.endsWith(")")) {
                // Store shift (e.g., "+1", "-1")
                this.shift = variableHierarchyString
                        .substring(
                                variableHierarchyString.lastIndexOf(")") + 1
                        );
            }

            // Update the value of variableHierarchyString by removing "size(" at the start and ")" (and the possible
            // shift) at the end
            variableHierarchyString = variableHierarchyString
                    .substring(
                            "size(".length(),
                            variableHierarchyString.lastIndexOf(")")
                    );
        }

        // TODO: Can a variable be size of array element??

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
