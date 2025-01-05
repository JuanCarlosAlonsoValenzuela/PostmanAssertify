package agora.postman.assertion.testScript;
import io.swagger.v3.oas.models.parameters.Parameter;
import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;
import io.swagger.v3.oas.models.parameters.RequestBody;

import java.util.List;

/**
 * @author Juan C. Alonso
 */
public class PptTestCaseGeneration {


    public static String generateProgramPointTestCases(ProgramPoint programPoint, String parentBaseVariable,
                                                       List<Parameter> parameters, RequestBody requestBody) {
        String res = "";
        if(programPoint != null) {

            res += "// Invariants of this nesting level:\n";

            for(Invariant inv: programPoint.getInvariants()) {
                res += inv.getPostmanTestCase(parentBaseVariable, parameters, requestBody);
            }

        } else {
            res += "// This nesting level has no invariants\n";
        }

        res += "\n";

        return res;

    }

}
