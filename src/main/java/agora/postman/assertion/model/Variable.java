package agora.postman.assertion.model;

import java.util.Arrays;
import java.util.List;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.testScript.PostmanVariableName.getPostmanVariableName;

/**
 * @author Juan C. Alonso
 */
public class Variable {

    // Variable name (format returned by AGORA)
    private String variableName;

    private boolean isReturn;
    private boolean isSize;
    private String shift;
    private List<String> variableHierarchyList;

    // If the variable name is accessing an array element that is an index
    // (e.g., return.data.results[1]
    private Integer arrayElementIndex;

    // If the variable name is accessing an array element that is another variable
    // (e.g., return.data.results[return.data.offset] or return.data.results[return.data.count-1])
    private Variable arrayElementVariable;

    public Variable(String variableName) {
        this.variableName = variableName;

        // This method sets the values of the remaining attributes
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

    @Override
    public String toString() {
        return "Variable{" +
                "variableName='" + variableName + '\'' +
                ", isReturn=" + isReturn +
                ", isSize=" + isSize +
                ", shift='" + shift + '\'' +
                ", variableHierarchyList=" + variableHierarchyList +
                ", arrayElementIndex=" + arrayElementIndex +
                ", arrayElementVariable=" + arrayElementVariable +
                '}';
    }



    // TODO: Split into multiple methods
    public String getPostmanVariableValueCode(String parentBaseVariable, boolean isArrayNestingPpt) {

        String postmanVariableName = getPostmanVariableName(this.variableName);

        List<String> variableHierarchyList = this.getVariableHierarchyList();

        int ifBracketsToClose = 0;

        String res = "// Getting value of variable: " + postmanVariableName + "\n";

        if(this.isReturn()) {  // Generate code for getting return variables

            if(isArrayNestingPpt) { // Array nesting program points (i.e., %array) only have one return variable (return_array)

                res += postmanVariableName + " = " + parentBaseVariable + ";\n";

            } else {    // If normal program point
                // First line/nested variable
                res += postmanVariableName + " = " + parentBaseVariable + "[\"" + variableHierarchyList.get(0) + "\"];\n";

                // TODO: START CONVERT INTO FUNCTION (DUPLICATED)
                for(int i = 1; i < variableHierarchyList.size(); i++) {

                    // Check that the variable is not null
                    res += "if(" + postmanVariableName + " != null) {\n";

                    // Access next hierarchy element
                    res += postmanVariableName + " = " + postmanVariableName + "[\"" + variableHierarchyList.get(i) + "\"];\n";


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

                    // Access next hierarchy element
                    res += postmanVariableName + " = " + postmanVariableName + "[\"" + variableHierarchyList.get(i) + "\"];\n";

                    // Increment the number of if brackets to close
                    ifBracketsToClose++;

                }  // TODO: END CONVERT INTO FUNCTION

            }

        }


        // Close if brackets (common for both input and exit)
        while(ifBracketsToClose > 0) {

            // Close if bracket
            res += "}\n";

            ifBracketsToClose--;
        }

        // If the variable name is accessing an array element
        // It can be a number (e.g., return.data.results[1])
        // Or another variable (e.g., return.data.results[return.data.offset] or return.data.results[return.data.count-1])
        if (this.arrayElementVariable != null || this.arrayElementIndex != null) {
            // If the retrieved array is not null
            res += "if(" + postmanVariableName + " != null) {\n";

            if(this.arrayElementIndex != null) {    // If the array element is an index

                res+= postmanVariableName + " = " + postmanVariableName + "[" + this.arrayElementIndex + "];\n";

            } else { // If the array element is another variable
                // Add code used to access the array element variable
                res += this.arrayElementVariable.getPostmanVariableValueCode(parentBaseVariable, isArrayNestingPpt);

                String arrayElementVariableName = getPostmanVariableName(this.arrayElementVariable.getVariableName());

                // If the array element index variable is not null
                res += "if(" + arrayElementVariableName + " != null) {\n";

                // Access the array element
                res += postmanVariableName + " = " + postmanVariableName + "[" + arrayElementVariableName +"];\n";

                // Close array element index variable is not null if bracket
                res += "}\n";
            }

            // Close retrieved array is not null if bracket
            res += "}\n";

        }

        // THIS IS COMMON TO BOTH INPUT AND RETURN
        // If the variable is the size of an array
        // Get array size
        if(this.isSize) {
            // If the retrieved array is not null
            res += "if(" + postmanVariableName + " != null) {\n";

            // Get array size
            res += postmanVariableName + " = " + postmanVariableName + ".length;\n";

            // Close the bracket
            res += "}\n\n";
        }

        // Add shift (only the last element can have shift)
        // This is common for input, return and array size
        if(this.shift != null) {
            // If the variable is not null
            res += "if(" + postmanVariableName + " != null) {\n";

            // Compute variable with shift
            res += postmanVariableName + " = " + postmanVariableName + " " + this.shift + ";\n";

            // Close if bracket
            res += "}\n";
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
    // TODO: SPLIT THIS METHOD
    private void setVariableHierarchyList() {

        // Split AGORA variable name to extract variable hierarchy
        String variableHierarchyString = this.variableName;

        // Set value of shift
        // If the variable name ends with a white space followed by +/- and a number (integer)
        if(variableHierarchyString.matches(".* [+-][0-9]+$")) {

            // Last occurrence of one of the +/- characters (-1 to include whitespace as part of the shift)
            int shiftIndex = Math.max(
                    variableHierarchyString.lastIndexOf("+"),
                    variableHierarchyString.lastIndexOf("-")
            ) - 1;

            // Set shift value
            this.shift = variableHierarchyString.substring(shiftIndex);

            // Remove shift value from variableHierarchyString
            variableHierarchyString = variableHierarchyString.substring(0, shiftIndex);
        }

        // Determine whether the variable is the size of an array
        this.isSize = false;
        if(variableHierarchyString.startsWith("size(")) {
            this.isSize = true;

            // Update the value of variableHierarchyString by removing "size(" at the start and ")" at the end
            variableHierarchyString = variableHierarchyString
                    .substring(
                            "size(".length(),
                            variableHierarchyString.lastIndexOf(")")
                    );
        }

        // Remove array characters
        variableHierarchyString = variableHierarchyString.replace("[]", "");
        variableHierarchyString = variableHierarchyString.replace("[..]", "");

        // If the variable name is accessing an array element
        // (e.g., return.data.results[return.data.offset] or return.data.results[return.data.count-1])
        if (variableHierarchyString.contains("[") && variableHierarchyString.contains("]")) {

            int firstBracketIndex = variableHierarchyString.indexOf("[");
            int lastBracketIndex = variableHierarchyString.lastIndexOf("]");

            String arrayElementVariableName = variableHierarchyString.substring(firstBracketIndex + 1, lastBracketIndex);

            if(arrayElementVariableName.matches("^[0-9]+$")) {  // if the array element is a number (e.g., return.data.results[1])
                this.arrayElementIndex = Integer.valueOf(arrayElementVariableName);
            } else {    // if the array element is another variable (e.g., return.data.results[return.data.offset])
                // Create a Variable object and assign it to the "arrayElementVariable" attribute
                this.arrayElementVariable = new Variable(arrayElementVariableName);
            }

            // Remove the array index variable from variableHierarchyString
            // (to avoid it being part of the variableHierarchyList
            variableHierarchyString = variableHierarchyString.substring(0, firstBracketIndex) +
                    variableHierarchyString.substring(lastBracketIndex+1);

        }

        List<String> variableHierarchyList;
        if(variableHierarchyString.startsWith("return.") || variableHierarchyString.startsWith("input.")) {
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
