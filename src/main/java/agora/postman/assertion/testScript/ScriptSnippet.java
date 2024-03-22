package agora.postman.assertion.testScript;

/**
 * @author Juan C. Alonso
 * When generating a snippet of code that also updates the parentBaseVariable, a method returns a ScriptSnippet object
 * containing both the generated script and the updated parentBaseVariable.
 */
public record ScriptSnippet(String newParentBaseVariable, String snippet) {
}
