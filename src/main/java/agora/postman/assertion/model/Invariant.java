package agora.postman.assertion.model;

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
        String testCaseIdentation = (!parentBaseVariable.equals("response")) ? indentationStr + "\t\t": "";

        String res = testCaseIdentation + "// " + this.invariant + "\n";

        // Test case first line
        res = res + testCaseIdentation + "pm.test(\"" + this.invariant.replace("\"", "\\\"") + "\", () => {\n";

        // Close test case bracket
        res = res + testCaseIdentation + "})\n";

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

}
