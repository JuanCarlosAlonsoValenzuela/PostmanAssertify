package agora.postman.assertion.debug;

/**
 * @author Juan C. Alonso
 */
public class DebugUtils {

    /**
     *
     * @param variableName: Name of the variable in Postman format
     * @return Postman test script snippet that prints the name and the value of the provided variable
     */
    public static String printVariableValueScript(String variableName) {
        return  "// Printing value of " + variableName + " variable\n"
                 + "console.log(\"Printing value of " + variableName + "\");\n"
                 + "console.log(" + variableName + ");\n\n";
    }
}
