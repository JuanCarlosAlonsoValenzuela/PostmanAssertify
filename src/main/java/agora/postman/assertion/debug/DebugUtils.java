package agora.postman.assertion.debug;

/**
 * @author Juan C. Alonso
 */
public class DebugUtils {

    /**
     *
     * @param variableName: Name of the variable in Postman format
     * @param indentation: Indentation level
     * @return Postman test script snippet that prints the name and the value of the provided variable
     */
    public static String printVariableValueScript(String variableName, String indentation) {
        return indentation + "// Printing value of " + variableName + " variable\n"
                + indentation + "console.log(\"Printing value of " + variableName + "\");\n"
                + indentation + "console.log(" + variableName + ");\n\n";
    }
}
