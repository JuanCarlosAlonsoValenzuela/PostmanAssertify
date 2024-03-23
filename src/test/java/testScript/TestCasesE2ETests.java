package testScript;

import agora.postman.assertion.files.FileManager;
import agora.postman.assertion.model.APIOperation;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static agora.postman.assertion.Main.getOpenAPISpecification;
import static agora.postman.assertion.files.ReadInvariants.getAllApiOperations;

/**
 * @author Juan C. Alonso
 */
public class TestCasesE2ETests {

    private static Stream<Arguments> testScriptGeneration() {
        return Stream.of(
                /* e2e_test_script_001: Simple test script
                Generates test script for an API response with two nesting levels: the first level of type object and
                the second of type array of objects, each one of them with a single test case (i.e., invariant). The
                response schema is the one used in the byIdOrTitle operation of the OMDb API.
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_001/oas_omdb_byIdOrTitle.yaml",
                        "src/test/resources/testScriptGeneration/test_001/invariants_test_001.csv",
                        "src/test/resources/testScriptGeneration/test_001/oracle_script_test_001.js"
                )

        );

        // TODO: Test with multiple API operations
        // TODO: Test with mulitple response codes
        // TODO: Response of type array
        // TODO: Multiple Nested arrays (both in root and in deep program point)
        // TODO: Nesting levels WITHOUT test cases/invariants (e.g., Vimeo)
        // TODO: Different datatypes
        // TODO: Values to consider as null
        // TODO: Test with both unary and binary invariants
        // TODO: Fix indentation
    }

    // TODO: Consider renaming this class (not exactly endToEnd)
    @ParameterizedTest
    @MethodSource("testScriptGeneration")
    public void testScriptGeneration(String oasSpecPath, String invariantsCsvPath, String oracleScriptPath) throws IOException {

        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification(oasSpecPath);
        List<APIOperation> allApiOperations = getAllApiOperations(specification, invariantsCsvPath);

        // Get the first API operation
        APIOperation apiOperation = allApiOperations.get(0);

        // TODO: IMPLEMENT
        List<String> valuesToConsiderAsNull = new ArrayList<>();

        // Generate the test script
        String testScript = apiOperation.generateTestScript(valuesToConsiderAsNull);

        String[] generatedTestScript = testScript.split("\n");

        String[] oracleTestScript = FileManager.readFileAsString(oracleScriptPath, StandardCharsets.UTF_8).split("\n");

        // Assert both test scripts files have the same number of lines
        Assertions.assertEquals(oracleTestScript.length, generatedTestScript.length, "The generated test script file does not have the expected number of lines");

        for(int i = 0; i < oracleTestScript.length; i++) {
            System.out.println("Expected: " + oracleTestScript[i]);
            System.out.println("Generated: " + generatedTestScript[i]);

            int lineNumber = i + 1;
            Assertions.assertEquals(
                    oracleTestScript[i].trim(),
                    generatedTestScript[i].trim(),
                    "The content of line " + lineNumber + " content is different from the expected");
        }




    }

}
