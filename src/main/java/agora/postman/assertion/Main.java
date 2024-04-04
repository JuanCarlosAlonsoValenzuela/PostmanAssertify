package agora.postman.assertion;

import agora.postman.assertion.model.postmanCollection.PostmanCollection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import io.swagger.v3.parser.core.models.ParseOptions;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Juan C. Alonso
 */
public class Main {

    private static String openApiSpecPath = "src/test/resources/testScriptGeneration/test_013/oas_marvel_simplified.yaml";

    private static String invariantsPath = "src/test/resources/testScriptGeneration/test_013/invariants_test_013.csv";

    public static boolean DEBUG_MODE = true;

    public static String HIERARCHY_SEPARATOR = "&";
    public static String ARRAY_NESTING_SEPARATOR = "%";

    // TODO: READ FROM .properties file
    // Server to use for generating the API requests, if null, the first server available in the OAS will be used
    public static String server = null;

    // TODO: READ FROM .properties file
    public static String POSTMAN_COLLECTION_SCHEMA = "https://schema.getpostman.com/json/collection/v2.1.0/collection.json";

    public static void main(String[] args) {

        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification(openApiSpecPath);


        // TODO: Reimplement ApiOperation, grouping by response code
        // TODO: Implement Strings to consider as null!!!
        // TODO: Test with multiple response codes
        // TODO: Test with multiple Http verbs
        // TODO: Test with multiple test with multiple operations, endpoints and paths
        // TODO: ENTER program points?
        // TODO: Closing brackets comments are incorrect
        // TODO: Apply modifications in Beet (or Daikon) to avoid confusing shift with kebab-case
        // Create PostmanCollection
        PostmanCollection postmanCollection = new PostmanCollection(specification, invariantsPath);

        // Output path
        String outputPath = getOutputPath(specification.getInfo().getTitle() + ".json", openApiSpecPath);

        // Create Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter fileWriter = new FileWriter(outputPath)) {
            // Convert POJO to JSON and write to file
            gson.toJson(postmanCollection, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static OpenAPI getOpenAPISpecification(String oasPath){

        ParseOptions parseOptions = new ParseOptions();
        parseOptions.setResolveFully(true);
        parseOptions.setFlatten(true);

        return new OpenAPIV3Parser().read(oasPath, null, parseOptions);
    }

    private static String getOutputPath(String filename, String folder) {
        Path path = java.nio.file.Paths.get(folder);      // openApiSpecPath
        Path dir = path.getParent();
        Path fn = path.getFileSystem().getPath(filename);
        Path target = (dir == null) ? fn : dir.resolve(fn);

        return target.toString();
    }

}
