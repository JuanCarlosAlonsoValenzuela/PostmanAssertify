package agora.postman.assertion.testScript;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;
import agora.postman.assertion.testScript.nestingLevelTree.Tree;

import java.util.Collections;
import java.util.Map;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;

/**
 * @author Juan C. Alonso
 */
public class ArrayNestingSnippets {

    // TODO: Document and add more comments
    public static ScriptSnippet generateRootArrayNestingSnippet(
            Map<Integer, ProgramPoint> arrayNestingProgramPoints, String parentBaseVariable
    ) {

        String snippet = "";

        int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());

        // Iterate over all the array nesting levels
        for(int i=1; i<= maxArrayNestingLevel; i++) {
            if(i > 1) {
                // Get nesting level variable
                // parentBaseVariable is an array, we have to iterate over it
                snippet += "if(" + parentBaseVariable + " != null) {\n";
            }

            // Generate test cases of this nesting level
            snippet += generateProgramPointTestCases(arrayNestingProgramPoints.get(i), parentBaseVariable);

            snippet += "// Access to the next nesting level\n";

            // Generate the code to access to the next nesting level
            ScriptSnippet accessNextArrayNestingLevelSnippet = generateAccessNextArrayNestingLevelSnippet(parentBaseVariable);

            parentBaseVariable = accessNextArrayNestingLevelSnippet.newParentBaseVariable();
            snippet += accessNextArrayNestingLevelSnippet.snippet();

            if(DEBUG_MODE) {
                snippet += printVariableValueScript(parentBaseVariable);
            }

        }

        return new ScriptSnippet(parentBaseVariable, snippet);

    }

    // TODO: DOCUMENT
    // TODO: Rename
    public static ScriptSnippet generateAccessNextObjectNestingLevelSnippet(Tree<String> tree, String parentBaseVariable) {

        String data = tree.getData();

        String baseVariableAssignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "[\"" + data + "\"]";
        parentBaseVariable = parentBaseVariable + "_" + data;

        String snippet = baseVariableAssignation + "\n";
        snippet += "if(" + parentBaseVariable + " != null) {\n";

        return new ScriptSnippet(parentBaseVariable, snippet);
    }


    // TODO: DOCUMENT
    // TODO: Rename
    // Used when the next nesting level is of type array
    public static ScriptSnippet generateAccessNextArrayNestingLevelSnippet(String parentBaseVariable) {

        String baseVariableIndex = parentBaseVariable + "_index";
        String baseVariableElement = parentBaseVariable + "_element";

        String snippet = "for(" + baseVariableIndex + " in " + parentBaseVariable + ") {\n";
        snippet += baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]\n";

        return new ScriptSnippet(baseVariableElement, snippet);
    }

    // TODO: Move to a different class
    // TODO: DOCUMENT
    public static String generateProgramPointTestCases(ProgramPoint programPoint, String parentBaseVariable) {
        String res = "";
        if(programPoint != null) {

            res += "// Invariants of this nesting level:\n";

            for(Invariant inv: programPoint.getInvariants()) {
                res += inv.getPostmanTestCase(parentBaseVariable);
            }

        } else {
            res += "// This nesting level has no invariants\n";
        }

        res += "\n";

        return res;

    }

}
