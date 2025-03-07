package agora.postman.assertion.model;

import agora.postman.assertion.GeneratePostmanCollection;
import agora.postman.assertion.testScript.nestingLevelTree.NestingType;
import agora.postman.assertion.testScript.nestingLevelTree.Tree;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;

import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.media.Schema;

import java.util.*;

import static agora.postman.assertion.GeneratePostmanCollection.*;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;
import static agora.postman.assertion.testScript.DepthSearch.programPointDepthSearch;
import static agora.postman.assertion.testScript.nestingLevelTree.Tree.getNestingTypeFromString;
import static agora.postman.assertion.preRequestScript.ParametersScript.generateCastingVariableScript;
import static agora.postman.assertion.preRequestScript.ParametersScript.generateGetVariableValueScript;
import static agora.postman.assertion.preRequestScript.RequestBodyScript.generateBodyParametersScript;
import static agora.postman.assertion.variableNameUtils.VariableNames.getInputVariableName;

/**
 * @author Juan C. Alonso
 */
public class APIOperation {

    private final String server;
    private final String endpoint;
    private final String operationId;
    private final int responseCode;

    private List<ProgramPoint> enterProgramPoints;
    private List<ProgramPoint> exitProgramPoints;

    // These attributes are derived from the OAS
    private final String method;
    private final List<Parameter> parameters;
    private final RequestBody requestBody;
    private final Schema responseSchema;



    public APIOperation(String pptname, List<ProgramPoint> programPoints, OpenAPI specification) {

        List<String> pptnameComponents = Arrays.stream(pptname.split(HIERARCHY_SEPARATOR)).toList();

        // If no default server is provided, we use the first one of the OAS
        String serverName = (GeneratePostmanCollection.server==null) ? specification.getServers().get(0).getUrl(): GeneratePostmanCollection.server;
        if(serverName.endsWith("/")) {
            serverName = serverName.substring(0, serverName.length() - 1);
        }
        this.server = serverName;

        this.endpoint = pptnameComponents.get(0);
        this.operationId = pptnameComponents.get(1);
        this.responseCode = getResponseCodeValue(pptnameComponents.get(2));

        // Set program points
        this.enterProgramPoints = programPoints.stream().filter(x-> x.getPptType().equals(PptType.ENTER)).toList();
        this.exitProgramPoints = programPoints.stream().filter(x-> x.getPptType().equals(PptType.EXIT)).toList();

        // Get the HttpMethod (verb) and the operation of the OAS with the endpoint and the operationId
        Map.Entry<PathItem.HttpMethod, Operation> pathItemEntry = getOASOperation(specification, endpoint, operationId);
        Operation oasOperation = pathItemEntry.getValue();

        // Get the method
        this.method = pathItemEntry.getKey().name();

        // Get the parameters
        this.parameters = oasOperation.getParameters();
        // Get the request body
        this.requestBody = oasOperation.getRequestBody();

        // Get the schema of the response with responseCode
        this.responseSchema = getResponseSchema(oasOperation, responseCode);

    }

    public String getServer() {
        return server;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public String getOperationId() {
        return operationId;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getMethod() {
        return method;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     *
     * @return Pre-Request script containing variables with the values of all the input parameters
     */
    // We only get the top point of the hierarchy
    // We do not get the array sizes or elements
    public String generateInputParametersScript() {

        String res = "";

        // Get values of the query, path and form parameters
        List<Parameter> parameters = this.parameters;
        if (parameters != null) {
            for(Parameter parameter: this.parameters) {

                // Generate the script that extracts the variable value (always as string)
                res += generateGetVariableValueScript(parameter, this.server + this.endpoint);

                // Decode URI component and generate a script that converts the variable into the
                // corresponding datatype, after checking that the variable is not null
                res += generateCastingVariableScript(parameter);


                if(DEBUG_MODE) {
                    res += printVariableValueScript(getInputVariableName(parameter));
                }

            }
        }

        // Get values of the body parameters
        RequestBody requestBody = this.requestBody;
        if(requestBody != null) {
            // Generates the code that obtains the value of all the body parameters (first nesting level) specified in
            // the OAS
            res += generateBodyParametersScript(requestBody);

        }

        return res;
    }

    public String generateTestScript(String[] valuesToConsiderAsNull, String response) {

        // Create array of strings to consider as null
        String arrayString = "[";
        if(valuesToConsiderAsNull.length != 0) {
            arrayString += "\"" + valuesToConsiderAsNull[0] + "\"";

            for(int i = 1; i < valuesToConsiderAsNull.length; i++) {
                arrayString += ", \"" + valuesToConsiderAsNull[i] + "\"";
            }
        }
        arrayString += "]";

        String res = "valuesToConsiderAsNull = " + arrayString + ";\n";

        Tree<String> programPointHierarchy = this.getProgramPointHierarchy();

        // Prints the program point hierarchy tree
//        programPointHierarchy.accept(new PrintIndentedVisitor(0));

        res += programPointDepthSearch(programPointHierarchy, new ArrayList<>(), null, response, this.parameters, this.requestBody);

        return res;

    }

    /**
     * @return Program point hierarchy tree, derived from the list of paths.
     */
    private Tree<String> getProgramPointHierarchy() {

        // Get responseSchema
        Schema rootSchema = this.responseSchema;
        // TODO: DUPLICATED START CONVERT INTO FUNCTION
        int nRootNestingLevels = 0;
        while(rootSchema.getType().equals("array")) {
            // Update value of current schema
            rootSchema = ((ArraySchema) rootSchema).getItems();
            // Increase the number of nesting levels of the node
            nRootNestingLevels++;
        }
        // TODO: DUPLICATED END CONVERT INTO FUNCTION

        // Create a tree with a root node
        Tree<String> programPointHierarchy = new Tree<>(Integer.toString(this.responseCode),
                getNestingTypeFromString(this.responseSchema.getType()), nRootNestingLevels
        );

        // Create the variable of type tree that we will iterate on
        Tree<String> current = programPointHierarchy;

        // Iterate over all program points
        // Given the list of all the tree paths, this for loop creates the complete tree
        // We only use the EXIT program points because they also contain the ENTER invariants
        for(ProgramPoint programPoint: this.exitProgramPoints) {

            // Get the path of this program point
            String path = programPoint.getVariableHierarchyAsString();

            // Get response format of the root without the initial nesting levels
            Schema currentSchema = rootSchema;

            // If the path is not empty, create the path and assign the program point to the last path element
            if(!path.isEmpty()) {
                // Create a variable to store the root
                Tree<String> root = current;

                // For each item of the hierarchy
                for (String data : path.split(HIERARCHY_SEPARATOR)) {

                    // If the program point hierarchy level is "data%array%array", changes its value to "data"
                    if(data.contains(ARRAY_NESTING_SEPARATOR)) {
                        data = data.split(ARRAY_NESTING_SEPARATOR)[0];
                    }

                    NestingType currentNestingType = getNestingTypeFromString(((Schema) currentSchema.getProperties().get(data)).getType());

                    int nArrayNestingLevels = 0;
                    if(currentNestingType.equals(NestingType.ARRAY)) {
                        currentSchema = ((ArraySchema)currentSchema.getProperties().get(data)).getItems();

                        // Iterate through array nesting levels and compute the number of array nesting levels
                        while(currentSchema.getType().equals("array")) {
                            // Update value of current schema
                            currentSchema = ((ArraySchema) currentSchema).getItems();
                            // Increase the number of nesting levels of the node
                            nArrayNestingLevels++;
                        }

                    } else { // If object
                        currentSchema = (Schema) currentSchema.getProperties().get(data);
                    }

                    // TODO: END CONVERT INTO FUNCTION


                    // Dive into the tree following the hierarchy by updating the value of current
                    // If the node does not exist, it is added
                    // Create a new child containing the data, the currentNestingType and a null ProgramPoint for each array nesting level (if any)
                    current = current.child(data, currentNestingType, nArrayNestingLevels);
                }

                // Assign the program point to the last element of the path
                //  If array nesting
                // TODO: Convert into function (This code is duplicated)
                int currentArrayNesting = programPoint.getArrayNesting();
                if(currentArrayNesting > 0) {
                    // Update the value of the array nesting program point (initially it is a null ProgramPoint)
                    current.addArrayNestingProgramPoint(programPoint);
                } else {
                    current.setProgramPoint(programPoint);
                }

                // Set current to the root value again
                current = root;
            } else {
                // If the path is an empty string, assign the invariants to the root
                // (the path of the first nesting level is an empty string)
                // TODO: Convert into function (This code is duplicated)
                int currentArrayNesting = programPoint.getArrayNesting();
                if(currentArrayNesting > 0) {
                    current.addArrayNestingProgramPoint(programPoint);
                } else {
                    current.setProgramPoint(programPoint);
                }

            }
        }

        return programPointHierarchy;
    }


    private static Map.Entry<PathItem.HttpMethod, Operation> getOASOperation(OpenAPI specification, String targetEndpoint, String targetOperationId) {

        Paths paths = specification.getPaths();

        // A path (endpoint) contains several operations (http methods/verbs)
        for(Map.Entry<String, PathItem> path: paths.entrySet()) {

            String operationEndpoint = path.getKey();

            if(operationEndpoint.equals(targetEndpoint)) {

                PathItem pathItem = path.getValue();

                for(Map.Entry<PathItem.HttpMethod, Operation> operationEntry: pathItem.readOperationsMap().entrySet()) {

                    Operation operation = operationEntry.getValue();
                    String operationId = operation.getOperationId();

                    if(operationId.equals(targetOperationId)) {
                        return operationEntry;
                    }
                }
            }
        }

        throw new NullPointerException("Could not find operation with endpoint: " + targetEndpoint + " and operation id: " + targetOperationId + " in OAS");

    }



    private static Schema getResponseSchema(Operation oasOperation, int responseCode) {

        Collection<MediaType> mediaTypes = oasOperation
                .getResponses()
                .get(Integer.toString(responseCode))
                .getContent()
                .values();

        if(mediaTypes.isEmpty()) {
            throw new NullPointerException("No media types found for operation " + oasOperation.getOperationId());
        }

        // Return the first schema
        return mediaTypes.iterator().next().getSchema();

    }

    /**
     *
     * @param pptnameResponseCodeItem Part of the pptname containing the API response code, it can be simply the
     *                                response code, the response code followed by the pptname suffix
     *                                (e.g., 200():::EXIT(), this happens when there is no variable hierarchy) or the
     *                                response code followed by the array hierarchy separator (e.g., 200%array():EXIT())
     * @return The API response code as int
     */
    public static int getResponseCodeValue(String pptnameResponseCodeItem) {

        if(pptnameResponseCodeItem.contains("():::")) {    // If the element contains the program point suffix (e.g., 200():::EXIT())

            String[] splitPptName = pptnameResponseCodeItem.split("\\(\\):::");
            if(splitPptName.length != 2) {
                throw new RuntimeException("Unexpected length for split pptname, expected 2, got: " + splitPptName.length);
            }

            // String containing the response code and (in some cases) the number of nested arrays
            String splitPptNameFirstElement = splitPptName[0];

            if(splitPptNameFirstElement.contains(ARRAY_NESTING_SEPARATOR)) { // If the input string contains the response code followed by the array hierarchy separator (e.g., 200%array():EXIT())
                String[] arrayNesting = splitPptNameFirstElement.split(ARRAY_NESTING_SEPARATOR);

                // Return the response code
                return Integer.parseInt(arrayNesting[0]);

            } else {    // if the input string contains the response code followed by the pptname suffix (e.g., 200():::EXIT()
                return  Integer.parseInt(splitPptNameFirstElement);
            }

        } else {    // If the element is simply and integer (e.g., 200)
            return Integer.parseInt(pptnameResponseCodeItem);
        }
    }

}
