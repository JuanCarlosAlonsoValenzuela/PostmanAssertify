package agora.postman.assertion.testScript;

import agora.postman.assertion.testScript.nestingLevelTree.Tree;

/**
 * @author Juan C. Alonso
 */
public class ObjectNestingSnippets {

    // TODO: DOCUMENT
    public static ScriptSnippet generateAccessNextObjectNestingLevelSnippet(Tree<String> tree, String parentBaseVariable) {

        String data = tree.getData();

        String baseVariableAssignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "[\"" + data + "\"]";
        parentBaseVariable = parentBaseVariable + "_" + data;

        String snippet = baseVariableAssignation + "\n";
        snippet += "if(" + parentBaseVariable + " != null) {\n";

        return new ScriptSnippet(parentBaseVariable, snippet);
    }

}
