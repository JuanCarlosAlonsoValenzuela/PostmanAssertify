package agora.postman.assertion.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Juan C. Alonso
 * Stores information about an invariant reported by AGORA
 */
public class Invariant {

    private String pptname;
    private String invariant;
    private String invariantType;
    private List<String> variables;
    private String postmanAssertion;

    public Invariant(String pptname, String invariant, String invariantType, List<String> variables, String postmanAssertion) {
        this.pptname = pptname;
        this.invariant = invariant;
        this.invariantType = invariantType;
        this.variables = variables;
        this.postmanAssertion = postmanAssertion;
    }

    public String getPptname() {
        return pptname;
    }

    public String getInvariant() {
        return invariant;
    }

    public String getInvariantType() {
        return invariantType;
    }

    public List<String> getVariables() {
        return variables;
    }

    public String getPostmanAssertion() { return postmanAssertion; }

    // TODO: Use StringBuilder
    // TODO: Get variable name
    // TODO: Values of type array
    // TODO: Input and output variables
    // TODO: One and two variables
    // TODO: Variable datatype
    // TODO: Access base variable (nesting level)
    public String getPostmanTestCase(String parentBaseVariable, String indentationStr) {

        // TODO: Change this condition (hardcoded)
        String testCaseIndentation = (!parentBaseVariable.equals("response")) ? indentationStr + "\t\t": "";

        String res = testCaseIndentation + "// " + this.invariant + "\n";

        // Test case first line
        res = res + testCaseIndentation + "pm.test(\"" + this.invariant.replace("\"", "\\\"") + "\", () => {\n";

        // TODO: Get variable(s) value(s)
        // TODO: We are assuming only one value, with no nesting
        // TODO: Modify so it can be applied to invariants with multiple variables
        // TODO: Implement, multiple functions, same method as the one used in Daikon



        // Generate code to access to variable value
        for(String variable: this.variables) {
            res = res + getPostmanVariableValueCode(parentBaseVariable, variable, testCaseIndentation);
        }


        // This is the original code, prior to the method implementation
        // res = res + testCaseIndentation + "\t" + variableName + " = " + parentBaseVariable + "." + variableName + ";\n";

        // TODO: If variable is not null and not part of values to consider null
        // One not null conditions for each invariant variable
        List<String> notNullConditions = new ArrayList<>();
        for(String variable: this.variables) {

            // Get variable name
            String postmanVariableName = getPostmanVariableName(variable);

            String condition = "(" + postmanVariableName + " != null) && (!valuesToConsiderAsNull.includes(" + postmanVariableName + "))";

            notNullConditions.add(condition);
        }

        // Check that none of the invariants variables is null
        res = res + testCaseIndentation + "\t" + "if(" + String.join(" && ", notNullConditions) + ") {" + "\n";

        // Postman assertion, returned by AGORA
        res = res + testCaseIndentation + "\t\t" + "// " + this.postmanAssertion + ";\n";

        // Close if variable not null and not part of values to consider as null bracket
        res = res + testCaseIndentation + "\t}\n";

        // Close test case bracket
        res = res + testCaseIndentation + "})\n";

        return res;

    }

    public String toString() {
        return "InvariantData{" +
                "pptname='" + pptname + '\'' +
                ", invariant='" + invariant + '\'' +
                ", invariantType='" + invariantType + '\'' +
                ", variables=" + variables +
                ", postmanAssertion='" + postmanAssertion + '\'' +
                '}';
    }

    // TODO: This method must be identical to the one in Daikon
    // TODO: Document properly (with multiple input/output example)
    // TODO: Consider moving to a different class
    // TODO: Program points that are nested arrays? (e.g., GitHub)
    // Returns the variable name in the format used in the Postman assertion
    private static String getPostmanVariableName(String originalVariableName) {

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
    // TODO: INDENTATION
    private static String getPostmanVariableValueCode(String parentBaseVariable, String agoraVariableName, String baseIndentation) {

        // TODO: It is redundant to compute this twice
        String postmanVariableName = getPostmanVariableName(agoraVariableName);

        // TODO: START CREATE VARIABLE HIERARCHYStart
        // Split AGORA variable name to extract variable hierarchy
        String variableHierarchyString = agoraVariableName;

        // TODO: Use and document
        boolean isSize = false;
        if(variableHierarchyString.startsWith("size(")) {

            variableHierarchyString = variableHierarchyString.substring("size(".length(), variableHierarchyString.length() - 1);
            isSize = true;
        }

        // Remove array characters
        variableHierarchyString = variableHierarchyString.replace("[]", "");
        variableHierarchyString = variableHierarchyString.replace("[..]", "");

        // TODO: Use and document
        boolean isReturn = false;
        List<String> variableHierarchyList;

        if(variableHierarchyString.startsWith("return.") || variableHierarchyString.startsWith("input.")) {
//            TODO: IMPLEMENT ENTER
            variableHierarchyList = Arrays.asList(variableHierarchyString.split("\\."));

            isReturn = variableHierarchyList.get(0).equals("return");

            variableHierarchyList = variableHierarchyList.subList(1, variableHierarchyList.size());

        } else {
            throw new RuntimeException("Unexpected AGORA variable name");
        }

        // TODO: END CREATE VARIABLE HIERARCHY



        // TODO: Comprobación del nulo para todos menos el último
        // TODO: Recordar el size para el último
        // TODO: IsEnter vs isExit (for now, only exit)
        // TODO: Añadir un console.log al final para facilitar el debugging

        if(variableHierarchyList.isEmpty()) {
            throw new RuntimeException("Variable hierarhy list cannot be empty");
        }

        String currentIdentation = baseIndentation + "\t";
        int ifBracketsToClose = 0;

        String res = currentIdentation + "// Getting value of variable: " + postmanVariableName + "\n";

        if(isReturn) {  // Generate code for getting return variables

            // First line/nested variable
            res = res + currentIdentation + postmanVariableName + " = " + parentBaseVariable + "." + variableHierarchyList.get(0) + ";\n";


            for(int i = 1; i < variableHierarchyList.size(); i++) {

                // Check that the variable is not null
                res = res + currentIdentation + "if(" + postmanVariableName + " != null) {\n";

                currentIdentation = currentIdentation + "\t";

                res = res + currentIdentation + postmanVariableName + " = " + postmanVariableName + "." + variableHierarchyList.get(i) + ";\n";

                // Increment the number of if brackets to close
                ifBracketsToClose++;

            }

        } else {    // Generate code for getting input variables (parameters)
            // TODO: Test with all datatypes (string, number, boolean)
            // TODO: for now, we assume that all input variables are query parameters
            // TODO: Read OAS to determine origin (query, path, body) of input parameters
            res = res + currentIdentation + postmanVariableName + " = "  + "pm.request.url.query.get(\"" + variableHierarchyList.get(0) + "\")" + ";\n";

            // Decode only if the variable is not null (otherwise, we obtain the "undefined" string)
            res = res + currentIdentation + "if(" + postmanVariableName + " != null) {\n";

            currentIdentation = currentIdentation + "\t";

            // Decode input variable
            res = res + currentIdentation + postmanVariableName + " = decodeURIComponent(" + postmanVariableName + ");\n";

            // Increment the number of if brackets to close
            ifBracketsToClose++;

            // TODO: Implement input variables with hierarchy (for now, a exception is thrown)
            if(variableHierarchyList.size() != 1) {
                throw new RuntimeException("Input parameters with hierarchy are not supported yet");
            }

        }


        // Close if brackets (common for both input and exit)
        // TODO: Create test with deep indentation
        while(ifBracketsToClose > 0) {

            // Reduce indentation level
            currentIdentation = currentIdentation.substring(0, currentIdentation.length()-1);

            // Close if bracket
            res = res + currentIdentation + "}\n";

            ifBracketsToClose--;
        }


        // TODO: THIS IS COMMON TO BOTH INPUT AND RETURN
        // If the variable is the size of an array
        // Get array size
        if(isSize) {
            // If the retrieved array is not null
            res = res + currentIdentation + "if(" + postmanVariableName + " != null) {\n";

            // Get array size
            res = res + currentIdentation + "\t" + postmanVariableName + " = " + postmanVariableName + ".length;\n";

            // Close the bracket
            res = res + currentIdentation + "}\n";
        }

        res = res + "\n";

        return res;
    }

}
