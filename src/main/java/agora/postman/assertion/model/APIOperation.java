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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// START PRE-REQUEST SCRIPT ////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        // Get values of the query, path and form parameters
        for(Parameter parameter: this.parameters) {

            // Generate the script that extracts the variable value (always as string)
            res = res + generateGetVariableValueScript(parameter);

            // If the parameter is not of type string, generate a script that converts the variable into the
            // corresponding datatype, after checking that the variable is not null
            if(!parameter.getSchema().getType().equals("string")) {
                res = res + generateCastingVariableScript(parameter);
            }

            if(DEBUG_MODE) {    // TODO: Convert into function
                // Get variable name in the Postman script
                String inputVariableName = getInputVariableName(parameter);
                res = res + "console.log(\"Printing value of " + inputVariableName + "\");\n";
                res = res + "console.log(" + inputVariableName + ");\n\n";
            }

        }

        // Get values of the body parameters
        RequestBody requestBody = this.requestBody;
        if(requestBody != null) {

            // Get the request body itself
            res = res + "let request_body = JSON.parse(pm.request.body.raw);\n";

            if(DEBUG_MODE) {    // TODO: Convert into function
                res = res + "console.log(\"Printing value of request_body\");\n";
                res = res + "console.log(request_body);\n\n";
            }

            // Generates the code that obtains the value of all the body parameters (first nesting level) specified in
            // the OAS
            res = res + generateBodyParametersScript(requestBody);


        }


        return res;
    }

    // TODO: Move to a different class
    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    private static String getInputVariableName(Parameter parameter) {
        return getInputVariableName(parameter.getName(), parameter.getSchema().getType());
    }


    // TODO: Move to a different class
    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    private static String getInputVariableName(Schema schema) {
        return getInputVariableName(schema.getName(), schema.getType());
    }

    // TODO: Move to a different class
    // Used to get an input variable name in the pre-request script
    // TODO: Must be consistent with getPostmanVariableName method
    private static String getInputVariableName(String parameterName, String parameterType) {

        String inputVariableName = "input_" + parameterName;
        if(parameterType.equals("array")) {
            inputVariableName = inputVariableName + "_array";
        }

        return inputVariableName;
    }



    // Given a parameter (its source can be one of: query, path, form or header), this function generates the Postman code
    // to obtain its value. The resulting variable will be of type string, use the generateCastingVariableScript function
    // to change its type.
    private static String generateGetVariableValueScript(Parameter parameter) {

        String parameterName = parameter.getName();
        String parameterIn = parameter.getIn();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameter);


        String res = "// Getting value of the " + parameterName + " " + parameterIn + " parameter \n";
        res = switch (parameterIn) {
            case "query" -> res + inputVariableName + " = pm.request.url.query.get(\"" + parameterName + "\");\n";
            case "path" -> res + inputVariableName + " = pm.request.url.variables.get(\"" + parameterName + "\");\n";
            case "form" ->
                // TODO: IMPLEMENT
                    throw new RuntimeException("Form parameters not implemented yet");
            case "header" -> res + inputVariableName + " = pm.request.headers.get(\"" + parameterName + "\");\n";
            default -> throw new RuntimeException("Unexpected value for parameter source, got: " + parameterIn);
        };

        return  res;

    }

    // Generates the Postman code necessary to cast an input parameter to its corresponding datatype
    // TODO: DOCUMENT
    private static String generateCastingVariableScript(Parameter parameter) {

        String parameterType = parameter.getSchema().getType();

        // Get variable name in the Postman script
        String inputVariableName = getInputVariableName(parameter);

        String res = "if (" + inputVariableName + " != null) { \n";

        switch (parameterType) {
            case "number" ->
                // TODO: Test with negative number
                // TODO: Create test comparing integer with number and check behavior
                    res = res + "\t" + inputVariableName + " = Number(" + inputVariableName + ");\n";
            case "integer" ->
                // TODO: Test with negative integer
                    res = res + "\t" + inputVariableName + " = parseInt(" + inputVariableName + ");\n";
            case "boolean" -> res = res + "\t" + inputVariableName + " = (" + inputVariableName + " == \"true\");\n";
            case "object" ->
                // TODO: Implement (check properties datatype)
                    System.err.println("Object input parameters not implemented");
            case "array" -> {
                // TODO: Convert into a different function (or recursivity)

                String separator = ",";

                // TODO: Check items datatype and convert them
                res = res + "\t" + inputVariableName + " = " + inputVariableName + ".split(\"" + separator + "\").map(item => item.trim());\n";

                String itemsDatatype = ((ArraySchema) parameter.getSchema()).getItems().getType();

                if (!itemsDatatype.equals("string")) {
                    // TODO: IMPLEMENT
                    throw new RuntimeException("Array of items that are not strings are not supported yet");
                }
            }
            default -> throw new RuntimeException("Unexpected parameter type: " + parameterType);
        }

        res = res + "}\n";

        return res;

    }


    // TODO: DOCUMENT
    // We assume that there is already a parent variable called request_body that represents the whole response body
    // JSON
    private static String generateBodyParametersScript(RequestBody requestBody) {

        String res = "";

        // Get all the variables (only first nesting level) of the request body
        // The remaining nesting levels are obtained in the Test script
        // TODO: Check behavior of different datatypes
        Collection<MediaType> mediaTypes = requestBody.getContent().values();
        if(mediaTypes.isEmpty()) {
            throw new NullPointerException("Request body is empty");
        }

        // Use only the first schema
        // TODO: Implement if ArraySchema, for now, we assume that it is an object
        Schema requestSchema = mediaTypes.iterator().next().getSchema();


        Set<String> requestSchemaKeyset = requestSchema.getProperties().keySet();
        for(String requestVariableName: requestSchemaKeyset) {

            // TODO: Consider that the body can be an array
            Schema variableSchema = (Schema) requestSchema.getProperties().get(requestVariableName);

            res = res + generateSingleBodyPropertyScript(variableSchema);

        }


        return res;

    }


    // TODO: DOCUMENT
    private static String generateSingleBodyPropertyScript(Schema variableSchema) {

        // Get variable name in the postman format
        String inputVariableName = getInputVariableName(variableSchema);

        // Variable name in the OAS
        String requestVariableName = variableSchema.getName();


        // Add variable assignation
        String res = "// Getting value of the " + requestVariableName + " property of the request body\n";

        res = res + inputVariableName + " = request_body." + requestVariableName + ";\n";

        if(DEBUG_MODE) {        // TODO: Convert into function
            res = res + "console.log(\"Printing value of " + inputVariableName + "\");\n";
            res = res + "console.log(" + inputVariableName + ");\n\n";
        }

        return res;

    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////// END PRE-REQUEST SCRIPT //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * @param paths: List of program point names, separated by HIERARCHY_SEPARATOR
     * @return Program point hierarchy tree, derived from the list of paths.
     */
    // TODO: Update parameters in Javadoc
    // TODO: Create test cases with array nesting
    // TODO: Improve comments
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

                    // If the program point hierarchy level is "data%array%array", changes its value to "data"
                    if(data.contains(ARRAY_NESTING_SEPARATOR)) {
                        data = data.split(ARRAY_NESTING_SEPARATOR)[0];
                    }

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
                //  If array nesting
                // TODO: Convert into function (This code is duplicated)
                int currentArrayNesting = programPoint.getArrayNesting();
                if(currentArrayNesting > 0) {
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

            // String containing the response code and (in some cases) the number of nested arrays
            String splitPptNameFirstElement = splitPptName[0];

            if(splitPptNameFirstElement.contains(ARRAY_NESTING_SEPARATOR)) { // If the input string contains the response code followed by the array hierarchy separator (e.g., 200%array():EXIT())
                // TODO: Create multiple tests
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
