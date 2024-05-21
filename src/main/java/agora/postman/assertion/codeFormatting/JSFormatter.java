package agora.postman.assertion.codeFormatting;

import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.Result;
import com.google.javascript.jscomp.SourceFile;

/**
 * @author Juan C. Alonso
 */
public class JSFormatter {

    public static String formatJSCode(String jsCode) {

        // Create a Closure Compiler instance
        Compiler compiler = new Compiler();

        // Set compiler options
        CompilerOptions options = new CompilerOptions();
        options.setPrettyPrint(true); // Enable pretty print (formatting)

        // Source files
        SourceFile input = SourceFile.fromCode("input.js", jsCode);
        SourceFile extern = SourceFile.fromCode("externs.js", ""); // Required but can be empty

        // Compile (format) the code
        Result result = compiler.compile(extern, input, options);

        // Output the formatted code
        if (result.success) {
            // Return formatted code
            return compiler.toSource();
        } else {
            throw new RuntimeException("Failed to compile the code.");
        }

    }

}
