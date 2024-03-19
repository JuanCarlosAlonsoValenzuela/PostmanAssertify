package agora.postman.assertion.model;

import agora.postman.assertion.model.nestingLevelTree.NestingType;
import agora.postman.assertion.model.nestingLevelTree.Tree;
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

import static agora.postman.assertion.Main.*;
import static agora.postman.assertion.model.nestingLevelTree.Tree.getNestingTypeFromString;

/**
 * @author Juan C. Alonso
 */
// TODO: Consider renaming this class because it also includes the response code
public class APIOperation {

    private final String endpoint;
    private final String operationId;
    private final int responseCode;

    private List<ProgramPoint> enterProgramPoints;
    private List<ProgramPoint> exitProgramPoints;

    // These attributes are derived from the OAS
    private final List<Parameter> parameters;
    private final RequestBody requestBody;
    private final Schema responseSchema;

    public APIOperation(String pptname, List<ProgramPoint> programPoints, OpenAPI specification) {

        List<String> pptnameComponents = Arrays.stream(pptname.split(HIERARCHY_SEPARATOR)).toList();

        this.endpoint = pptnameComponents.get(0);
        this.operationId = pptnameComponents.get(1);
        this.responseCode = getResponseCodeValue(pptnameComponents.get(2));

        // Set program points
        this.enterProgramPoints = programPoints.stream().filter(x-> x.getPptType().equals(PptType.ENTER)).toList();
        this.exitProgramPoints = programPoints.stream().filter(x-> x.getPptType().equals(PptType.EXIT)).toList();

        // Get the operation of the OAS with the endpoint and the operationId
        Operation oasOperation = getOASOperation(specification, endpoint, operationId);

        // Get the parameters
        this.parameters = oasOperation.getParameters();
        // Get the request body
        this.requestBody = oasOperation.getRequestBody();

        // Get the schema of the response with responseCode
        // TODO: Use Schema vs use ArraySchema
        this.responseSchema = getResponseSchema(oasOperation, responseCode);

    }


    /**
     *
     * @return Pre-Request script containing variables with the values of all the input parameters
     */
    // TODO: Test with all possible datatypes and parameter sources
    // TODO: Add debug mode
    // TODO: Create test cases checking that the variable names are consistent
    // TODO: Explain that we only get the top point of the hierarchy
    // TODO: We do not get the array sizes or elements
    // TODO: Datatypes, it considers everything as a String
    // TODO: Decode uri? I think it is not necessary
    public String generatePreRequestScript() {

        String res = "";


        // TODO: Get Postman variable name, it has to be consistent

        // Get values of the query, path and form parameters
        for(Parameter parameter: this.parameters) {
            String parameterIn = parameter.getIn();
            String parameterName = parameter.getName();

            String parameterType = parameter.getSchema().getType();

            String inputVariableName = "input_" + parameterName;    // TODO: Test this, check conformance with getPostmanVariableName
            if(parameterType.equals("array")) {
                inputVariableName = inputVariableName + "_array";
            }

            res = res + "// Getting value of the " + parameterName + " " + parameterIn + " parameter \n";
            if(parameterIn.equals("query")) {
                res = res + inputVariableName + " = pm.request.url.query.get(\"" + parameterName + "\");\n";
            } else if (parameterIn.equals("path")) {
                res = res + inputVariableName + " = pm.request.url.variables.get(\"" + parameterName + "\");\n";
            } else if(parameterIn.equals("form")) {
                // TODO: IMPLEMENT
                throw new RuntimeException("Form parameters not implemented yet");
            } else if (parameterIn.equals("header")) {
                // TODO: Test
                res = res + inputVariableName + " = pm.request.headers.get(\"" + parameterName + "\");\n";

            } else {
                throw new RuntimeException("Unexpected value for parameter source, got: " + parameterIn);
            }

            // TODO: ANALYZE DATATYPE AND CONVERT IF NOT NULL (IT ALWAYS READS THE VALUE AS STRING)
            // TODO: string (do nothing), object, array (items datatype), number, integer, boolean
            if(!parameterType.equals("string")) {
                res = res + "if (" + inputVariableName + " != null) { \n";

                if(parameterType.equals("number")) {
                    // TODO: Test with negative number
                    // TODO: Create test comparing integer with number and check behavior
                    res = res + "\t" + inputVariableName + " = Number(" + inputVariableName + ");\n";
                } else if (parameterType.equals("integer")) {
                    // TODO: Test with negative integer
                    res = res + "\t" + inputVariableName + " = parseInt(" + inputVariableName + ");\n";
                } else if(parameterType.equals("boolean")) {
                    res = res + "\t" + inputVariableName + " = (" + inputVariableName + " == \"true\");\n";
                } else if(parameterType.equals("object")) {
                    // TODO: Implement (check properties datatype)
                    System.err.println("Object input parameters not implemented");
                } else if(parameterType.equals("array")) {

                    String separator = ",";

                    // TODO: Check items datatype and convert them
                    res = res + "\t" + inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => item.trim());\n";

                    String itemsDatatype = ((ArraySchema) parameter.getSchema()).getItems().getType();

                    if(!itemsDatatype.equals("string")) {
                        // TODO: IMPLEMENT
                        throw new RuntimeException("Array of items that are not strings are not supported yet");
                    }


                } else {
                    throw new RuntimeException("Unexpected parameter type: " + parameterType);
                }


                res = res + "}\n";
            }

            if(DEBUG_MODE) {
                res = res + "console.log(\"Printing value of " + inputVariableName + "\");\n";
                res = res + "console.log(" + inputVariableName + ");\n\n";
            }

        }

        // TODO: Get value of the body parameter

        return res;
    }

    /**
     * @param paths: List of program point names, separated by HIERARCHY_SEPARATOR
     * @return Program point hierarchy tree, derived from the list of paths.
     */
    // TODO: Update parameters in Javadoc
    public Tree<String> getProgramPointHierarchy() {

        // Create a tree with a root node
        // TODO: Use other method for specifying the root
        Tree<String> programPointHierarchy = new Tree<>(Integer.toString(this.responseCode),
                getNestingTypeFromString(this.responseSchema.getType())
        );

        // Create the variable of type tree that we will iterate on
        Tree<String> current = programPointHierarchy;

        // Iterate over all program points
        // Given the list of all the tree paths, this for loop creates the complete tree
        // We only use the EXIT program points because they also contain the ENTER invariants
        for(ProgramPoint programPoint: this.exitProgramPoints) {

            // Get the path of this program point
            String path = programPoint.getVariableHierarchyAsString();

            // Get response format
            Schema currentSchema = this.responseSchema;

            // If the path is not empty, create the path and assign the program point to the last path element
            if(!path.isEmpty()) {
                // Create a variable to store the root
                Tree<String> root = current;

                // For each item of the hierarchy
                for (String data : path.split(HIERARCHY_SEPARATOR)) {

                    // TODO: START CONVERT INTO FUNCTION
                    NestingType currentNestingType = getNestingTypeFromString(((Schema) currentSchema.getProperties().get(data)).getType());

                    if(currentNestingType.equals(NestingType.ARRAY)) {

                        currentSchema = ((ArraySchema)currentSchema.getProperties().get(data)).getItems();

                    } else { // If object
                        currentSchema = (Schema) currentSchema.getProperties().get(data);
                    }

                    // TODO: END CONVERT INTO FUNCTION


                    // Dive into the tree following the hierarchy by updating the value of current
                    // If the node does not exist, it is added
                    current = current.child(data, currentNestingType);
                }

                // Assign the program point to the last element of the path
                current.setProgramPoint(programPoint);

                // TODO: Assign the variable type to the last element of the path

                // Set current to the root value again
                current = root;
            } else {
                // If the path is an empty string, assign the invariants to the root
                // (the path of the first nesting level is an empty string)
                current.setProgramPoint(programPoint);

            }
        }

        return programPointHierarchy;
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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public Schema getResponseSchema() {
        return responseSchema;
    }

    public List<ProgramPoint> getEnterProgramPoints() {
        return enterProgramPoints;
    }

    public List<ProgramPoint> getExitProgramPoints() {
        return exitProgramPoints;
    }

    // TODO: DOCUMENT
    public static Operation getOASOperation(OpenAPI specification, String targetEndpoint, String targetOperationId) {

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
                        return operation;
                    }
                }
            }
        }

        throw new NullPointerException("Could not find operation with endpoint: " + targetEndpoint + " and operation id: " + targetOperationId + " in OAS");

    }

    // TODO: DOCUMENT
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
    public static int getResponseCodeValue(String pptnameResponseCodeItem) {    // TODO: Can contain %array

        if(pptnameResponseCodeItem.contains("():::")) {    // If the element contains the program point suffix (e.g., 200():::EXIT())

            String[] splitPptName = pptnameResponseCodeItem.split("\\(\\):::");
            if(splitPptName.length != 2) {
                throw new RuntimeException("Unexpected length for split pptname, expected 2, got: " + splitPptName.length);
            }


            if(pptnameResponseCodeItem.contains(ARRAY_NESTING_SEPARATOR)) { // If the input string contains the response code followed by the array hierarchy separator (e.g., 200%array():EXIT())
                // TODO: IMPLEMENT
                return -1;
            } else {    // if the input string contains the response code followed by the pptname suffix (e.g., 200():::EXIT()
                return  Integer.parseInt(splitPptName[0]);
            }

        } else {    // If the element is simply and integer (e.g., 200)
            return Integer.parseInt(pptnameResponseCodeItem);
        }
    }

}
