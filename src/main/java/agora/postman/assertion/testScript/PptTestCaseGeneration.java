package agora.postman.assertion.testScript;

import agora.postman.assertion.model.Invariant;
import agora.postman.assertion.model.ProgramPoint;

/**
 * @author Juan C. Alonso
 */
public class PptTestCaseGeneration {


    public static String generateProgramPointTestCases(ProgramPoint programPoint, String parentBaseVariable) {
        String res = "";
        if(programPoint != null) {

            res += "// Invariants of this nesting level:\n";

            for(Invariant inv: programPoint.getInvariants()) {
                res += inv.getPostmanTestCase(parentBaseVariable);
            }

        } else {
            res += "// This nesting level has no invariants\n";
        }

        res += "\n";

        return res;

    }

}
