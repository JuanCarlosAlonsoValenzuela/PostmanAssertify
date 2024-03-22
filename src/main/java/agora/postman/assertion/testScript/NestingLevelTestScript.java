package agora.postman.assertion.testScript;

import agora.postman.assertion.model.Invariant;
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

        // TODO: Change parameters order
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
    // TODO: Implement number of tabulations (based on parents.size)
    // TODO: Improve parameters, create a class or similar
    // Returns initial lines test scripts and sets the value of the next parentBaseVariable
    private String generateInitialLinesScript(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

        // Print current nesting level (e.g., 200&data)
        String res = indentationStr + "// " + String.join(HIERARCHY_SEPARATOR, parents) + HIERARCHY_SEPARATOR + tree.getData() + "\n";

        if(parents.isEmpty()){  // If we are in the first nesting level

            // Assign base variable
            res += "response = pm.response.json();\n";
            parentBaseVariable = "response";

            // TODO: Implement if response == null ?? Create test case

            if(DEBUG_MODE) {
                res += printVariableValueScript(parentBaseVariable, "");
            }

            // Invariants of nested arrays in root (e.g., 200%array%array)
            Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
            // TODO: TEST Multiple nesting levels, but maybe not all of them are present (e.g., maybe there are invariants for %array and %array%array%array, but not for %array%array)
            if(!arrayNestingProgramPoints.isEmpty()) {
                ScriptSnippet rootArrayNestingSnippet = generateRootArrayNestingSnippet(arrayNestingProgramPoints, parentBaseVariable, indentationStr);

                parentBaseVariable = rootArrayNestingSnippet.newParentBaseVariable();
                res += rootArrayNestingSnippet.snippet();
            }

            // Get invariants of this nesting level
            res += generateProgramPointTestCases(tree.getProgramPoint(), parentBaseVariable, indentationStr);

        } else {    // If we are in a deeper nesting level

            // TODO: Implement multiple array nesting (%array), e.g., 200&data%array%array
            Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
            if(!arrayNestingProgramPoints.isEmpty()) {
                // TODO: IMPLEMENT
            }

            // Access next object in the nesting hierarchy
            ScriptSnippet accessNextObjectNestingLevelSnippet = generateAccessNextObjectNestingLevelSnippet(tree, parentBaseVariable, indentationStr);
            parentBaseVariable = accessNextObjectNestingLevelSnippet.newParentBaseVariable();
            res += accessNextObjectNestingLevelSnippet.snippet();

            // If the nesting type value is array
            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                // Generate the code to access to the next nesting level
                ScriptSnippet accessNextArrayNestingLevelSnippet = generateAccessNextArrayNestingLevelSnippet(parentBaseVariable, indentationStr);
                parentBaseVariable = accessNextArrayNestingLevelSnippet.newParentBaseVariable();
                res += accessNextArrayNestingLevelSnippet.snippet();
            }

            if(DEBUG_MODE) {
                res += printVariableValueScript(parentBaseVariable, indentationStr);
            }

            res += generateProgramPointTestCases(tree.getProgramPoint(), parentBaseVariable, indentationStr);

        }

        this.childrenParentBaseVariable = parentBaseVariable;

        return res;

    }


    // TODO: DOCUMENT
    private String generateClosingLinesScript(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        String res = "";

        // TODO: Improve this condition
        if(!parents.isEmpty()) {
            // TODO: The first nesting level (response) should not have a closing if/for, unless there is arrayNesting


            String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                res += indentationStr + "\t} // Closing for " + parentBaseVariable + "\n";
            }

            res += indentationStr + "} // Closing if "+ parentBaseVariable + "\n\n";

        }

        // TODO: Create test cases
        // Close array nesting conditions
        Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
        if(!arrayNestingProgramPoints.isEmpty()) {
            int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());
            for(int i=maxArrayNestingLevel; i >= 1; i--) {

                res += "} // Closing if array nesting level " + i + "\n";
                res += "} // Closing for array nesting level " + i + "\n";

            }
        }

        return res;
    }


}
