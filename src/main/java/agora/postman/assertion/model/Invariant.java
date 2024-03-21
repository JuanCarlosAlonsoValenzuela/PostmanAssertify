package agora.postman.assertion.model;

import java.util.ArrayList;

import java.util.List;

import static agora.postman.assertion.Main.ARRAY_NESTING_SEPARATOR;
import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;

/**
 * @author Juan C. Alonso
 * Stores information about an invariant reported by AGORA
 */
public class Invariant {

    private String pptname;
    private String invariant;
    private String invariantType;
    private List<Variable> variables;
    private String postmanAssertion;

    private boolean isArrayNestingPpt; // True if the program point is an array nesting (i.e., contains %array)

    public Invariant(String pptname, String invariant, String invariantType,
                     List<String> variablesString, String postmanAssertion) {
        this.pptname = pptname;
        this.invariant = invariant;
        this.invariantType = invariantType;

        List<Variable> variables = new ArrayList<>();
        for(String variableName: variablesString) {
            // TODO: Remove information from specification, is not required anymore
            variables.add(new Variable(variableName));
        }

        this.variables = variables;
        this.postmanAssertion = postmanAssertion;

        this.isArrayNestingPpt = pptname.contains(ARRAY_NESTING_SEPARATOR);
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

    public List<Variable> getVariables() {
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
        String testCaseIndentation = (!parentBaseVariable.equals("response")) ? indentationStr: "";

        String res = testCaseIndentation + "// " + this.invariant + "\n";

        // Test case first line
        res = res + testCaseIndentation + "pm.test(\"" + this.invariant.replace("\"", "\\\"") + "\", () => {\n";

        // TODO: Get variable(s) value(s)
        // TODO: We are assuming only one value, with no nesting



        // Generate code to access to variable value
        for(Variable variable: this.variables) {
            // TODO: This method should NOT be static
            res = res + getPostmanVariableValueCode(parentBaseVariable, variable, testCaseIndentation, this.isArrayNestingPpt);
        }


        // This is the original code, prior to the method implementation
        // res = res + testCaseIndentation + "\t" + variableName + " = " + parentBaseVariable + "." + variableName + ";\n";

        // TODO: If variable is not null and not part of values to consider null
        // One not null conditions for each invariant variable
        List<String> notNullConditions = new ArrayList<>();
        for(Variable variable: this.variables) {

            // Get variable name
            String postmanVariableName = variable.getPostmanVariableName();

            String condition = "(" + postmanVariableName + " != null) && (!valuesToConsiderAsNull.includes(" + postmanVariableName + "))";

            notNullConditions.add(condition);
        }

        // Check that none of the invariants variables is null
        res = res + testCaseIndentation + "\t" + "if(" + String.join(" && ", notNullConditions) + ") {" + "\n";

        // Postman assertion, returned by AGORA
        res = res + testCaseIndentation + "\t\t//" + this.postmanAssertion + ";\n";

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




    // TODO: DOCUMENT
    // TODO: This method should NOT be static
    private static String getPostmanVariableValueCode(String parentBaseVariable, Variable variable, String baseIndentation, boolean isArrayNestingPpt) {

        // TODO: It is redundant to compute this twice
        String postmanVariableName = variable.getPostmanVariableName();

        List<String> variableHierarchyList = variable.getVariableHierarchyList();

        String currentIdentation = baseIndentation + "\t";
        int ifBracketsToClose = 0;

        String res = currentIdentation + "// Getting value of variable: " + postmanVariableName + "\n";

        if(variable.isReturn()) {  // Generate code for getting return variables

            if(isArrayNestingPpt) { // Array nesting program points (i.e., %array) only have one return variable (return_array)

                res = res + currentIdentation + postmanVariableName + " = " + parentBaseVariable + ";\n";

            } else {    // If normal program point
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
            }


        } else {    // Generate code for getting input variables (parameters)
            // TODO: Test with all datatypes (string, number, boolean)
            // TODO: for now, we assume that all input variables are query parameters
            // TODO: Read OAS to determine origin (query, path, body, form) of input parameters

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
        // TODO: Create test case for size of input variables
        // If the variable is the size of an array
        // Get array size
        if(variable.isSize()) {
            // If the retrieved array is not null
            res = res + currentIdentation + "if(" + postmanVariableName + " != null) {\n";

            // Get array size
            res = res + currentIdentation + "\t" + postmanVariableName + " = " + postmanVariableName + ".length;\n";

            // Close the bracket
            res = res + currentIdentation + "}\n\n";
        }

        if(DEBUG_MODE) {
            res = res + printVariableValueScript(postmanVariableName, currentIdentation);
        }

        res = res + "\n";

        return res;
    }

}
