package agora.postman.assertion;

import agora.postman.assertion.model.postmanCollection.PostmanCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.FileWriter;
import java.io.IOException;

import static agora.postman.assertion.Main.getOpenAPISpecification;
import static agora.postman.assertion.Main.getOutputPath;

/**
 * @author Juan C. Alonso
 * This class is used to perform experiment 2. It takes the same inputs as Main.java, with an additional file
 * containing the mutated API responses (generated by JSONMutator). The execution result will be a Postman collection
 * containing the tests mocked with the values of the mutated test cases
 */
public class MainMock {

    private static String openApiSpecPath = "src/main/resources/test_mock_with_all_parameteres_types/swagger.yaml";

    private static String invariantsPath = "src/main/resources/test_mock_with_all_parameteres_types/invariants.csv";

    private static String configurationName = "test";
    private static String mutantsPath = "src/main/resources/test_mock_with_all_parameteres_types/test-cases_1712821217637.csv";

    private static String[] valuesToConsiderAsNull = {};

    public static void main(String[] args) {


        if (args.length == 4) {         // without strings to consider as null
            openApiSpecPath = args[0];
            invariantsPath = args[1];
            configurationName = args[2];
            mutantsPath = args[3];
            valuesToConsiderAsNull = new String[]{};
        } else if(args.length == 5) {   // with strings to consider as null
            openApiSpecPath = args[0];
            invariantsPath = args[1];
            configurationName = args[2];
            mutantsPath = args[3];
            valuesToConsiderAsNull = args[4].split(";");
        }

        // Read OAS from file
        OpenAPI specification = getOpenAPISpecification(openApiSpecPath);

        // Create PostmanCollection
        PostmanCollection postmanCollection = new PostmanCollection(specification, invariantsPath, valuesToConsiderAsNull, configurationName, mutantsPath);

        // Output path
        String outputPath = getOutputPath(configurationName + ".json", mutantsPath);

        // Create Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter fileWriter = new FileWriter(outputPath)) {
            // Convert POJO to JSON and write to file
            gson.toJson(postmanCollection, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
