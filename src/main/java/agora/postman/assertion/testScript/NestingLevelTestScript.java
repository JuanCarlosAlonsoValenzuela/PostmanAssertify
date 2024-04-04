package agora.postman.assertion.testScript;

import agora.postman.assertion.model.ProgramPoint;
import agora.postman.assertion.testScript.nestingLevelTree.NestingType;
import agora.postman.assertion.testScript.nestingLevelTree.Tree;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.Main.HIERARCHY_SEPARATOR;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.testScript.ArrayNestingSnippets.*;
import static agora.postman.assertion.testScript.ObjectNestingSnippets.generateAccessNextObjectNestingLevelSnippet;
import static agora.postman.assertion.testScript.PptTestCaseGeneration.generateProgramPointTestCases;

/**
 * @author Juan C. Alonso
 * TODO: DOCUMENT
 */
public class NestingLevelTestScript {

    private String childrenParentBaseVariable;
    private String initialLines;
    private String closingLines;


    public NestingLevelTestScript(Tree<String> tree, List<String> parents, String parentBaseVariable) {
        // This method also sets the value of this.childrenParentBaseVariable
        this.initialLines = generateInitialLinesScript(tree, parents, parentBaseVariable);

        this.closingLines = generateClosingLinesScript(tree, parents, parentBaseVariable);
    }

    public String getChildrenParentBaseVariable() {
        return childrenParentBaseVariable;
    }

    public String getInitialLines() {
        return initialLines;
    }

    public String getClosingLines() {
        return closingLines;
    }

    // TODO: DOCUMENT
    // TODO: Split into multiple methods
    // Returns initial lines test scripts and sets the value of the next parentBaseVariable
    private String generateInitialLinesScript(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        // Print current nesting level (e.g., 200&data)
        String res = "// " + String.join(HIERARCHY_SEPARATOR, parents) + HIERARCHY_SEPARATOR + tree.getData() + "\n";

        if(parents.isEmpty()){  // If we are in the first nesting level

            // Assign base variable
            res += "response = pm.response.json();\n";
            parentBaseVariable = "response";

            if(DEBUG_MODE) {
                res += printVariableValueScript(parentBaseVariable);
            }

            // Invariants of nested arrays in root (e.g., 200%array%array)
            Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
            if(!arrayNestingProgramPoints.isEmpty()) {
                ScriptSnippet rootArrayNestingSnippet = generateRootArrayNestingSnippet(arrayNestingProgramPoints, parentBaseVariable);

                parentBaseVariable = rootArrayNestingSnippet.newParentBaseVariable();
                res += rootArrayNestingSnippet.snippet();
            }

            // Get invariants of this nesting level
            res += generateProgramPointTestCases(tree.getProgramPoint(), parentBaseVariable);

        } else {    // If we are in a deeper nesting level

            // Access next object in the nesting hierarchy
            ScriptSnippet accessNextObjectNestingLevelSnippet = generateAccessNextObjectNestingLevelSnippet(tree, parentBaseVariable);
            parentBaseVariable = accessNextObjectNestingLevelSnippet.newParentBaseVariable();
            res += accessNextObjectNestingLevelSnippet.snippet();

            if(DEBUG_MODE) {
                res += printVariableValueScript(parentBaseVariable);
            }

            // If the nesting type value is array
            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
                if(!arrayNestingProgramPoints.isEmpty()) {
                    // Generate code to access to all the nested arrays program points
                    ScriptSnippet propertyArrayNestingSnippet = generateRootArrayNestingSnippet(arrayNestingProgramPoints, parentBaseVariable);

                    parentBaseVariable = propertyArrayNestingSnippet.newParentBaseVariable();
                    res += propertyArrayNestingSnippet.snippet();
                }

                // Generate the code to access to the next nesting level
                ScriptSnippet accessNextArrayNestingLevelSnippet = generateAccessNextArrayNestingLevelSnippet(parentBaseVariable);
                parentBaseVariable = accessNextArrayNestingLevelSnippet.newParentBaseVariable();
                res += accessNextArrayNestingLevelSnippet.snippet();

                if(DEBUG_MODE) {
                    res += printVariableValueScript(parentBaseVariable);
                }

            }

            res += generateProgramPointTestCases(tree.getProgramPoint(), parentBaseVariable);

        }

        this.childrenParentBaseVariable = parentBaseVariable;

        return res;

    }


    // TODO: DOCUMENT
    private String generateClosingLinesScript(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        String res = "";

        if(!parents.isEmpty()) {

            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                res += "} // Closing for " + parentBaseVariable + "\n";
            }

            res += "} // Closing if "+ parentBaseVariable + "\n\n";

        }

        // Close array nesting conditions
        Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
        if(!arrayNestingProgramPoints.isEmpty()) {
            int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());
            for(int i=maxArrayNestingLevel; i >= 1; i--) {

                // This if is not necessary for the first nesting level:
                //  If the nesting is in the root, it makes no sense to check whether the whole response is null
                //  If the nesting is in a property, the "if" clause would be duplicated
                if(i != 1) {
                    res += "} // Closing if array nesting level " + i + "\n";
                }
                res += "} // Closing for array nesting level " + i + "\n";
            }
        }

        return res;
    }


}
