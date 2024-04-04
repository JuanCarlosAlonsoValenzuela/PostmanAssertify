package agora.postman.assertion.testScript;

import agora.postman.assertion.model.ProgramPoint;

import java.util.Collections;
import java.util.Map;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.testScript.PptTestCaseGeneration.generateProgramPointTestCases;

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
    // Used when the next nesting level is of type array
    public static ScriptSnippet generateAccessNextArrayNestingLevelSnippet(String parentBaseVariable) {

        String baseVariableIndex = parentBaseVariable + "_index";
        String baseVariableElement = parentBaseVariable + "_element";

        String snippet = "for(" + baseVariableIndex + " in " + parentBaseVariable + ") {\n";
        snippet += baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]\n";

        return new ScriptSnippet(baseVariableElement, snippet);
    }

}
