package agora.postman.assertion;

import agora.postman.assertion.model.InvariantData;

import java.util.List;

import static agora.postman.assertion.ReadInvariants.getInvariantsDataFromPath;

/**
 * @author Juan C. Alonso
 */
public class Main {
    public static void main(String[] args) {


        List<InvariantData> invariants = getInvariantsDataFromPath("src/main/resources/test.csv");

        for(InvariantData inv: invariants) {
            System.out.println(inv);
        }

    }
}