package agora.postman.assertion;

import agora.postman.assertion.model.postmanCollection.PostmanCollection;
import com.google.gson.Gson;
import io.swagger.v3.oas.models.OpenAPI;

import java.io.FileWriter;
import java.io.IOException;

import static agora.postman.assertion.Main.getOpenAPISpecification;

/**
 * @author Juan C. Alonso
 */
public class Main2 {

    private static String openApiSpecPath = "src/test/resources/testScriptGeneration/test_003/oas_vimeo.yaml";
    private static String invariantsPath = "src/test/resources/testScriptGeneration/test_003/invariants_test_003.csv";

    // TODO: READ FROM .properties file
    public static String POSTMAN_COLLECTION_SCHEMA = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";

    public static void main(String[] args) {

        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification(openApiSpecPath);


        // TODO: Reimplement ApiOperation, grouping by response code
        // TODO: Implement Strings to consider as null!!!
        // TODO: NEW CODE STARTS HERE
        // Create an instance of PostmanCollection
        PostmanCollection postmanCollection = new PostmanCollection(specification, invariantsPath);

        // Create Gson instance
        Gson gson = new Gson();

        try(FileWriter fileWriter = new FileWriter("output.json")) {
            // Convert POJO to JSON and write to file
            // TODO: Export with indentation
            gson.toJson(postmanCollection, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
