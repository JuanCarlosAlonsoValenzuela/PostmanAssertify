package agora.postman.assertion.model;

import java.util.ArrayList;

import java.util.List;

import static agora.postman.assertion.Main.ARRAY_NESTING_SEPARATOR;
import static agora.postman.assertion.model.Variable.getPostmanVariableName;

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
    // TODO: Array item
    public String getPostmanTestCase(String parentBaseVariable) {

        String res =  "// " + this.invariant + "\n";

        // Test case first line
        res +=  "pm.test(\"" + this.invariant.replace("\"", "\\\"") + "\", () => {\n";

        // Generate code to access to variable value
        for(Variable variable: this.variables) {
            res += variable.getPostmanVariableValueCode(parentBaseVariable, this.isArrayNestingPpt);
        }

        // Check that none of the invariants variables is null or one of the values to consider as null
        res += generateNotNullConditionsSnippet(this.variables);

        // Postman assertion, returned by AGORA
        // TODO: REMOVE comment characters (//)
        res += "//" + this.postmanAssertion + ";\n";

        // Close if variable not null and not part of values to consider as null bracket
        res += "}\n";

        // Close test case bracket
        res += "})\n";

        return res;

    }

    // TODO: DOCUMENT
    // TODO: Move to a different class
    private static String generateNotNullConditionsSnippet(List<Variable> variables) {

        // If variable is not null and not part of values to consider null
        // One not null conditions for each invariant variable
        List<String> notNullConditions = new ArrayList<>();
        for(Variable variable: variables) {
            // Get variable name
            String postmanVariableName = getPostmanVariableName(variable.getVariableName());

            String condition = "(" + postmanVariableName + " != null) && (!valuesToConsiderAsNull.includes(" + postmanVariableName + "))";

            notNullConditions.add(condition);
        }
        // Check that none of the invariants variables is null
        return "if(" + String.join(" && ", notNullConditions) + ") {" + "\n";

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

}
