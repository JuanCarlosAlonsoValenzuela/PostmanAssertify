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
    // TODO: Implement proper indentation
    public static ScriptSnippet generateRootArrayNestingSnippet(
            Map<Integer, ProgramPoint> arrayNestingProgramPoints, String parentBaseVariable, String indentationStr
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
            snippet += generateProgramPointTestCases(arrayNestingProgramPoints.get(i), parentBaseVariable, indentationStr);

            snippet += "// Access to the next nesting level\n";

            // Generate the code to access to the next nesting level
            ScriptSnippet accessNextArrayNestingLevelSnippet = generateAccessNextArrayNestingLevelSnippet(parentBaseVariable, indentationStr);

            parentBaseVariable = accessNextArrayNestingLevelSnippet.newParentBaseVariable();
            snippet += accessNextArrayNestingLevelSnippet.snippet();

            if(DEBUG_MODE) {
                snippet += printVariableValueScript(parentBaseVariable, "");
            }

        }

        return new ScriptSnippet(parentBaseVariable, snippet);

    }

    // TODO: Document and add more code
    // TODO: Implement proper indentation
//    public static ScriptSnippet generatePropertyArrayNestingSnippet(
//            Map<Integer, ProgramPoint> arrayNestingProgramPoints, String parentBaseVariable, String indentationStr
//    ) {
//        String snippet = "";
//
//        int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());
//
//        // Iterate over all the array nesting levels
//        for(int i=1; i<= maxArrayNestingLevel; i++) {
//            // Update parentBaseVariable
//            snippet +=
//
//        }
//
//    }

    // TODO: DOCUMENT
    // TODO: Rename
    public static ScriptSnippet generateAccessNextObjectNestingLevelSnippet(Tree<String> tree, String parentBaseVariable, String indentationStr) {

        String data = tree.getData();

        String baseVariableAsignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "." + data;
        parentBaseVariable = parentBaseVariable + "_" + data;

        String snippet = indentationStr + baseVariableAsignation + "\n";
        snippet += indentationStr + "if(" + parentBaseVariable + " != null) {\n";

        return new ScriptSnippet(parentBaseVariable, snippet);
    }


    // TODO: DOCUMENT
    // TODO: Rename
    // Used when the next nesting level is of type array
    public static ScriptSnippet generateAccessNextArrayNestingLevelSnippet(String parentBaseVariable, String indentationStr) {

        String baseVariableIndex = parentBaseVariable + "_index";
        String baseVariableElement = parentBaseVariable + "_element";

        String snippet = indentationStr + "\tfor(" + baseVariableIndex + " in " + parentBaseVariable + ") {\n";
        snippet += indentationStr + "\t\t" + baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]\n";

        return new ScriptSnippet(baseVariableElement, snippet);
    }

    // TODO: Move to a different class
    // TODO: DOCUMENT
    public static String generateProgramPointTestCases(ProgramPoint programPoint, String parentBaseVariable, String indentationStr) {
        String res = "";
        if(programPoint != null) {

            res += indentationStr + "// Invariants of this nesting level:\n";

            for(Invariant inv: programPoint.getInvariants()) {
                res += inv.getPostmanTestCase(parentBaseVariable, indentationStr);
            }

        } else {
            res += indentationStr + "// This nesting level has no invariants\n";
        }

        res += "\n";

        return res;

    }

}
