package inputParametersScript;

import agora.postman.assertion.files.FileManager;
import agora.postman.assertion.model.APIOperation;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.Main.getOpenAPISpecification;
import static agora.postman.assertion.files.ReadInvariants.getAllApiOperations;

/**
 * @author Juan C. Alonso
 */
public class InputParametersScriptGenerationTests {

    private static Stream<Arguments> inputParametersScriptGeneration() {
        return Stream.of(
                // ipsg_input_parameters_script_001: Query parameters of all primitive datatypes
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_001/oas_query_parameters_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_001/invariants_test_001.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_001/oracle_input_parameters_script_001.js"
                ),
                /* ipsg_input_parameters_script_002: Path parameters of all primitive datatypes
                The name of the server in the OAS specification has been modified, it also includes a path
                 */
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_002/oas_path_parameters_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_002/invariants_test_002.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_002/oracle_input_parameters_script_002.js"
                ),
                // ipsg_input_parameters_script_003: Header parameters of all primitive datatypes
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_003/oas_header_parameters_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_003/invariants_test_003.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_003/oracle_input_parameters_script_003.js"
                ),
                // ipsg_input_parameters_script_004: Query parameters of arrays of all primitive datatypes
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_004/oas_query_parameters_array_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_004/invariants_test_004.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_004/oracle_input_parameters_script_004.js"
                ),
                /* ipsg_input_parameters_script_005: Path parameters of arrays of all primitive datatypes
                The name of the server in the OAS specification has been modified, it also includes a path
                 */
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_005/oas_path_parameters_array_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_005/invariants_test_005.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_005/oracle_input_parameters_script_005.js"
                ),
                // ipsg_input_parameters_script_006: Header parameters of arrays of all primitive datatypes
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_006/oas_header_parameters_array_primitive.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_006/invariants_test_006.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_006/oracle_input_parameters_script_006.js"
                ),
                // ipsg_input_parameters_script_007: Simple request body parameters
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_007/oas_request_body.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_007/invariants_test_007.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_007/oracle_input_parameters_script_007.js"
                ),
                //ipsg_input_parameters_script_008: Request body parameter with object hierarchy
                Arguments.of(
                        "src/test/resources/inputParametersScriptGeneration/test_008/oas_complex_request_body.yaml",
                        "src/test/resources/inputParametersScriptGeneration/test_008/invariants_test_008.csv",
                        "src/test/resources/inputParametersScriptGeneration/test_008/oracle_input_parameters_script_008.js"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("inputParametersScriptGeneration")
    public void inputParametersScriptGeneration(String oasSpecPath, String invariantsCsvPath, String oracleScriptPath) throws IOException {

        DEBUG_MODE = true;

        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification(oasSpecPath);
        List<APIOperation> allApiOperations = getAllApiOperations(specification, invariantsCsvPath);

        // Get the first API operation
        APIOperation apiOperation = allApiOperations.get(0);

        // Generate the input parameters script
        String inputParametersScript = apiOperation.generateInputParametersScript();
        System.out.println(inputParametersScript);

        String[] generatedInputParametersScript = inputParametersScript.split("\n");

        String[] oracleInputParametersScript = FileManager.readFileAsString(oracleScriptPath, StandardCharsets.UTF_8).split("\n");

        // Assert input parameters scripts files have the same number of lines
        Assertions.assertEquals(
                oracleInputParametersScript.length,
                generatedInputParametersScript.length,
                "The generated input parameters script file does not have the expected number of lines"
        );

        for(int i = 0; i < oracleInputParametersScript.length; i++) {
            System.out.println("Expected: " + oracleInputParametersScript[i]);
            System.out.println("Generated: " + generatedInputParametersScript[i]);

            int lineNumber = i + 1;
            Assertions.assertEquals(
                    oracleInputParametersScript[i].trim(),
                    generatedInputParametersScript[i].trim(),
                    "The content of line " + lineNumber + " content is different from the expected");

        }
    }
}
