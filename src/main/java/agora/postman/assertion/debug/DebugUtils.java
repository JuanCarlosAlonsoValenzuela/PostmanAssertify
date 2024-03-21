package agora.postman.assertion.debug;

public class DebugUtils {

    // TODO: Document
    public static String printVariableValueScript(String variableName, String indentation) {
        return indentation + "// Printing value of " + variableName + " variable\n"
                + indentation + "console.log(\"Printing value of " + variableName + "\");\n"
                + indentation + "console.log(" + variableName + ");\n\n";
    }
}
