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

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.Main.getOpenAPISpecification;
import static agora.postman.assertion.files.ReadInvariants.getAllApiOperations;

/**
 * @author Juan C. Alonso
 */
public class TestScriptGenerationTests {

    private static Stream<Arguments> testScriptGeneration() {
        return Stream.of(
                /* tsg_test_script_001: Simple test script
                Generates test script for an API response with two nesting levels: the first level of type object and
                the second of type array of objects, each one of them with a single test case (i.e., invariant). The
                response schema is the one used in the byIdOrTitle operation of the OMDb API.
                Values to consider as null: "N/A"
                Nesting levels:
                    /&searchByIdOrTitle&200():::EXIT
                        /&searchByIdOrTitle&200&Ratings():::EXIT
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_001/oas_omdb_byIdOrTitle.yaml",
                        "src/test/resources/testScriptGeneration/test_001/invariants_test_001.csv",
                        new String[]{"N/A"},
                        "src/test/resources/testScriptGeneration/test_001/oracle_test_script_001.js"
                ),
                /* tsg_test_script_002: Test script with 3 nesting levels and multiple datatypes
                Generates test script for the Yelp API (3 nesting levels, the first one of type object). Multiple
                datatypes (array of objects, array of strings and all the possible primitive datatypes: string,
                boolean, number, integer) in the response. Nesting levels:
                    /businesses/search&getBusinesses&200():::EXIT
                        /businesses/search&getBusinesses&200&businesses():::EXIT
                            /businesses/search&getBusinesses&200&businesses&categories():::EXIT
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_002/oas_yelp_getBusinesses.yaml",
                        "src/test/resources/testScriptGeneration/test_002/invariants_test_002.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_002/oracle_test_script_002.js"
                ),
                /* tsg_test_script_003: Complex response
                Generates test script for the Vimeo API. The response schema of this API operation contains multiple
                nesting levels. The first nesting level is of type object, there are nesting level of type object and
                array, some of the nesting levels have multiple children and some of the nesting levels do not have
                invariants, so it is a perfect test for the depth search algorithm. Nesting levels:

                    /videos&search_videos&200():::EXIT

                        /videos&search_videos&200&data():::EXIT

                            /videos&search_videos&200&data&tags():::EXIT

                            /videos&search_videos&200&data&categories():::EXIT

                                        /videos&search_videos&200&data&categories&icon&sizes():::EXIT
                                        /videos&search_videos&200&data&categories&pictures&sizes():::EXIT

                                    /videos&search_videos&200&data&categories&subcategories():::EXIT

                                /videos&search_videos&200&data&spatial&director_timeline():::EXIT


                            /videos&search_videos&200&data&pictures&sizes():::EXIT


                                    /videos&search_videos&200&data&user&pictures&sizes():::EXIT
                                /videos&search_videos&200&data&user&skills():::EXIT
                                /videos&search_videos&200&data&user&websites():::EXIT


                                    /videos&search_videos&200&data&uploader&pictures&sizes():::EXIT

                Program points hierarchy tree:

                    200
                      data
                        user
                          websites
                          skills
                          pictures
                            sizes
                        categories
                          pictures
                            sizes
                          subcategories
                          icon
                            sizes
                        pictures
                          sizes
                        tags
                        uploader
                          pictures
                            sizes
                        spatial
                          director_timeline
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_003/oas_vimeo.yaml",
                        "src/test/resources/testScriptGeneration/test_003/invariants_test_003.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_003/oracle_test_script_003.js"
                ),
                /* tsg_test_script_004: Response of type array
                Generates test script for the response schema of the v31ListOfCodes operation of the RESTCountries
                API. This API operation is an array with nested objects. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT
                        /v3.1/alpha&v31ListOfCodes&200():::EXIT
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_004/oas_v31ListOfCodes_restCountries.yaml",
                        "src/test/resources/testScriptGeneration/test_004/invariants_test_004.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_004/oracle_test_script_004.js"
                ),
                /* tsg_test_script_005: Response of type array 2
                Generates test script for a modified version of the response schema of the v31ListOfCodes operation of
                the RESTCountries with an additional nesting level. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT
                        /v3.1/alpha&v31ListOfCodes&200():::EXIT
                            /v3.1/alpha&v31ListOfCodes&200&users():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_005/oas_v31ListOfCodes_restCountries_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_005/invariants_test_005.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_005/oracle_test_script_005.js"
                ),
                /* tsg_test_script_006: Response of type array 3
                Generates test script for a modified version of the response schema of the v31ListOfCodes operation of
                the RESTCountries with TWO nested arrays in the first nesting level. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT
                        /v3.1/alpha&v31ListOfCodes&200%array%array():::EXIT
                            /v3.1/alpha&v31ListOfCodes&200():::EXIT
                                /v3.1/alpha&v31ListOfCodes&200&users():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_006/oas_v31ListOfCodes_restCountries_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_006/invariants_test_006.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_006/oracle_test_script_006.js"
                ),
                /* tsg_test_script_007: Response of type array 4
                Generates test script for a modified version of the response schema of the v31ListOfCodes operation of
                the RESTCountries with THREE nested arrays in the first nesting level. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT
                        /v3.1/alpha&v31ListOfCodes&200%array%array():::EXIT
                            /v3.1/alpha&v31ListOfCodes&200%array%array%array():::EXIT
                                /v3.1/alpha&v31ListOfCodes&200():::EXIT
                                    /v3.1/alpha&v31ListOfCodes&200&users():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_007/oas_v31ListOfCodes_restCountries_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_007/invariants_test_007.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_007/oracle_test_script_007.js"
                ),
                /* tsg_test_script_008: Response property of type nested array of object
                Generates test script for a modified version of the getArtistAlbums operation of the Spotify API with
                TWO nested arrays as value of the "artists" property. Nesting levels:

                    /artists/{id}/albums&getArtistAlbums&200():::EXIT
                        /artists/{id}/albums&getArtistAlbums&200&items():::EXIT
                            /artists/{id}/albums&getArtistAlbums&200&items&artists%array():::EXIT
                                /artists/{id}/albums&getArtistAlbums&200&items&artists():::EXIT
                                    /artists/{id}/albums&getArtistAlbums&200&items&artists&links():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_008/oas_spotify_artistAlbums_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_008/invariants_test_008.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_008/oracle_test_script_008.js"
                ),
                /* tsg_test_script_009: Response property of type nested array of object 2
                Generates test script for a modified version of the getArtistAlbums operation of the Spotify API with
                THREE nested arrays as value of the "artists" property. Nesting levels:

                    /artists/{id}/albums&getArtistAlbums&200():::EXIT
                        /artists/{id}/albums&getArtistAlbums&200&items():::EXIT
                            /artists/{id}/albums&getArtistAlbums&200&items&artists%array():::EXIT
                                /artists/{id}/albums&getArtistAlbums&200&items&artists%array%array():::EXIT
                                    /artists/{id}/albums&getArtistAlbums&200&items&artists():::EXIT
                                        /artists/{id}/albums&getArtistAlbums&200&items&artists&links():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_009/oas_spotify_artistAlbums_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_009/invariants_test_009.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_009/oracle_test_script_009.js"
                ),
                /* tsg_test_script_010: Nested arrays in both root and response property
                Generates test script for a modified version of the response schema of the v31ListOfCodes operation of
                the RESTCountries, with array nesting levels ini both the root and a response property. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT
                        /v3.1/alpha&v31ListOfCodes&200():::EXIT
                            /v3.1/alpha&v31ListOfCodes&200&users%array():::EXIT
                                /v3.1/alpha&v31ListOfCodes&200&users():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_010/oas_v31ListOfCodes_restCountries_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_010/invariants_test_010.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_010/oracle_test_script_010.js"
                ),
                /* tsg_test_script_011: Complex request body property
                Generates test script for an API  operation with a deep hierarchy in the request body. Nesting levels:

                    /users/playlists&createPlaylist&201()

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_011/oas_complex_request_body.yaml",
                        "src/test/resources/testScriptGeneration/test_011/invariants_test_011.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_011/oracle_test_script_011.js"
                ),
                /* tsg_test_script_012: Variables with shift
                Generates test script with a single Postman test chat includes two variables (in input and return) with
                shift, conforming the invariant: "size(input.hotelIds[]) -1 >= size(return.data[]) -1"
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_012/oas_amadeusHotel_simplified.yaml",
                        "src/test/resources/testScriptGeneration/test_012/invariants_test_012.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_012/oracle_test_script_012.js"
                ),
                /* tsg_test_script_013: Variables with array element access (access to variables)
                Generates test script with a single Postman test chat includes two variables that access array elements
                (with and without shift), conforming the invariant:
                    return.data.results[return.data.offset] == return.data.results[return.data.total -1]
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_013/oas_marvel_simplified.yaml",
                        "src/test/resources/testScriptGeneration/test_013/invariants_test_013.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_013/oracle_test_script_013.js"
                ),
                /* tsg_test_script_014: Variables with array element access (access to index)
                Generates test script with a single Postman test that accesses to an array element by indicating an
                index, the invariant being:
                    return.data.results[0] == return.data.results[return.data.total -1]
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_014/oas_marvel_simplified.yaml",
                        "src/test/resources/testScriptGeneration/test_014/invariants_test_014.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_014/oracle_test_script_014.js"
                ),
                /* tsg_test_script_015: Array nesting with no reported invariants
                This test is based on the original RESTCountries formats. It features a single array nesting level
                in the root, but with no reported invariants. The generated test script must contemplate the presence
                of this program point despite it not having any reported invariants. Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT       (CONTAINS NO INVARIANTS)
                        /v3.1/alpha&v31ListOfCodes&200():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_015/oas_rest_countries_simplified.yaml",
                        "src/test/resources/testScriptGeneration/test_015/invariants_test_015.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_015/oracle_test_script_015.js"
                ),
                /* tsg_test_script_016: Multiple Array nesting with no reported invariants
                Generates test script for a modified version of the response schema of the v31ListOfCodes operation of
                the RESTCountries with THREE nested arrays in the first nesting level (there are no invariants for
                any of these nesting levels). Nesting levels:

                    /v3.1/alpha&v31ListOfCodes&200%array():::EXIT                       (CONTAINS NO INVARIANTS)
                        /v3.1/alpha&v31ListOfCodes&200%array%array():::EXIT             (CONTAINS NO INVARIANTS)
                            /v3.1/alpha&v31ListOfCodes&200%array%array%array():::EXIT   (CONTAINS NO INVARIANTS)
                                /v3.1/alpha&v31ListOfCodes&200():::EXIT
                                    /v3.1/alpha&v31ListOfCodes&200&users():::EXIT
                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_016/oas_v31ListOfCodes_restCountries_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_016/invariants_test_016.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_016/oracle_test_script_016.js"
                ),
                /* tsg_test_script_017: Response property of type nested array of object 2 with no reported invariants
                Generates test script for a modified version of the getArtistAlbums operation of the Spotify API with
                THREE nested arrays as value of the "artists" property (there are no invariants for
                any of these nesting levels). Nesting levels:

                    /artists/{id}/albums&getArtistAlbums&200():::EXIT
                        /artists/{id}/albums&getArtistAlbums&200&items():::EXIT
                            /artists/{id}/albums&getArtistAlbums&200&items&artists%array():::EXIT               (CONTAINS NO INVARIANTS)
                                /artists/{id}/albums&getArtistAlbums&200&items&artists%array%array():::EXIT     (CONTAINS NO INVARIANTS)
                                    /artists/{id}/albums&getArtistAlbums&200&items&artists():::EXIT
                                        /artists/{id}/albums&getArtistAlbums&200&items&artists&links():::EXIT

                 */
                Arguments.of(
                        "src/test/resources/testScriptGeneration/test_017/oas_spotify_artistAlbums_modified.yaml",
                        "src/test/resources/testScriptGeneration/test_017/invariants_test_017.csv",
                        new String[]{},
                        "src/test/resources/testScriptGeneration/test_017/oracle_test_script_017.js"
                )
        );

        // TODO: Test with multiple API operations
        // TODO: Test with multiple response codes
        // TODO: Values to consider as null
        // TODO: Fix indentation
        // TODO: Test with nested request body properties
        // TODO: Test with kebab-case (both in input and output)
    }

    @ParameterizedTest
    @MethodSource("testScriptGeneration")
    public void testScriptGeneration(String oasSpecPath, String invariantsCsvPath, String[] valuesToConsiderAsNull, String oracleScriptPath) throws IOException {

        DEBUG_MODE = true;

        // Read invariants from file
        OpenAPI specification = getOpenAPISpecification(oasSpecPath);
        List<APIOperation> allApiOperations = getAllApiOperations(specification, invariantsCsvPath);

        // Get the first API operation
        APIOperation apiOperation = allApiOperations.get(0);

        // Generate the test script
        String testScript = apiOperation.generateTestScript(valuesToConsiderAsNull, "pm.response.json()");
        System.out.println(testScript);

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
