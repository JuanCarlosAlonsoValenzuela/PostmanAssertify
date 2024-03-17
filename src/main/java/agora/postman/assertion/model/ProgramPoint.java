package agora.postman.assertion.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static agora.postman.assertion.Main.ARRAY_NESTING_SEPARATOR;
import static agora.postman.assertion.Main.HIERARCHY_SEPARATOR;
import static agora.postman.assertion.model.APIOperation.getResponseCodeValue;

/**
 * @author Juan C. Alonso
 * Stores information about a program point name, all this information is automatically derived from the pptname in
 * the csv file. A pptname contains, in this order, the endpoint of the API, the operation id, the response code, the
 * list of variables required to access to the nesting level of the program point (variableHierachy) and the program
 * point type (ENTER or EXIT). It also contains the list of all the invariants of the program point.
 */
public class ProgramPoint {

    private String pptname;
    private PptType pptType;
    private String endpoint;
    private String operationId;
    private int responseCode;
    private List<String> variableHierarchy;

    // Invariants of this nesting level
    private List<Invariant> invariants;

    // TODO: Take ARRAY_HIERARCHY_SEPARATOR (GitHub and RESTCountries) into account
    public ProgramPoint(String pptname){

        // TODO: Modify after ApiOperation refactorization
        List<String> pptnameComponents = Arrays.stream(pptname.split(HIERARCHY_SEPARATOR)).toList();

        this.pptname = pptname;
        this.pptType = getPptType(pptname);
        this.endpoint = pptnameComponents.get(0);
        this.operationId = pptnameComponents.get(1);
        this.responseCode = getResponseCodeValue(pptnameComponents.get(2));     // TODO: Can contain %array, create test

        // The input are the elements of the list that contain the nested variables
        this.variableHierarchy = getVariableHierarchy(pptnameComponents.subList(3, pptnameComponents.size()));

        this.invariants = new ArrayList<>();

    }

    // TODO: Take ARRAY_HIERARCHY_SEPARATOR (GitHub and RESTCountries) into account
    public ProgramPoint(String pptname, List<Invariant> invariants){
        List<String> pptnameComponents = Arrays.stream(pptname.split(HIERARCHY_SEPARATOR)).toList();

        this.pptname = pptname;
        this.pptType = getPptType(pptname);
        this.endpoint = pptnameComponents.get(0);
        this.operationId = pptnameComponents.get(1);
        this.responseCode = getResponseCodeValue(pptnameComponents.get(2));     // TODO: Can contain %array, create test

        // The input are the elements of the list that contain the nested variables
        this.variableHierarchy = getVariableHierarchy(pptnameComponents.subList(3, pptnameComponents.size()));

        this.invariants = invariants;

    }

    public String getPptname() {
        return pptname;
    }

    public PptType getPptType() {
        return pptType;
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

    public List<String> getVariableHierarchy() {
        return variableHierarchy;
    }

    public List<Invariant> getInvariants() {
        return invariants;
    }

    public void addInvariant(Invariant invariant) {
        this.invariants.add(invariant);
    }

    /**
     * @return variable hierarchy list as a single string, joined by HIERARCHY_SEPARATOR, this string is a path
     * in the nesting level tree. For instance if variableHierarchy=["items", "images"] and HIERARCHY_SEPARATOR="&",
     * this function returns "items&images".
     */
    // TODO: Explain that I use responseCode as root (if I end up using it).
    public String getVariableHierarchyAsString() {
        return String.join(HIERARCHY_SEPARATOR, this.variableHierarchy);
    }

    // TODO: Update
    @Override
    public String toString() {
        return "ProgramPoint{" +
                "pptType=" + pptType +
                ", endpoint='" + endpoint + '\'' +
                ", operationId='" + operationId + '\'' +
                ", responseCode=" + responseCode +
                ", variableHierarchy=" + variableHierarchy +
                '}';
    }

    /**
     * @param pptname: program point name
     * @return  whether its type is ENTER or EXIT
      */
    private static PptType getPptType(String pptname) {
        String[] splitPptName = pptname.split(":::");

        if(splitPptName.length != 2) {
            throw new RuntimeException("Unexpected length for split pptname, expected 2, got: " + splitPptName.length);
        }

        String pptTypeString = splitPptName[1];

        if(pptTypeString.equals("ENTER")) {
            return PptType.ENTER;
        } else if (pptTypeString.equals("EXIT")) {
            return PptType.EXIT;
        } else {
            throw new RuntimeException("Unexpected pptType, expected 'ENTER' or 'EXIT', got: " + pptTypeString);
        }

    }




    /**
     * @param pptnameSublist: sublist of the program point name containing the nesting level, the last one contains
     *                      a suffix (such as ():::EXIT) that must be suppressed
     * @return Hierarchy of nested variables as list of strings
     */
    private static List<String> getVariableHierarchy(List<String> pptnameSublist) {

        if(pptnameSublist.isEmpty()) {
            return new ArrayList<>();
        }

        // Add all the elements of the list but the last one
        List<String> res = new ArrayList<>(pptnameSublist.subList(0, pptnameSublist.size() - 1));

        // Remove the undesired suffix from the last variable of the hierarchy
        String[] splitLastVariable = pptnameSublist.get(pptnameSublist.size()-1).split("\\(\\):::");

        if(splitLastVariable.length != 2) {
            throw new RuntimeException("Unexpected size for splitLastVariable array, expected 1, got: " + splitLastVariable.length);
        }

        // Add the last variable
        String lastVariableName = splitLastVariable[0];
        if(!lastVariableName.isEmpty()){
            res.add(lastVariableName);
        }

        return res;
    }


}
