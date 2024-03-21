package agora.postman.assertion.model;

import agora.postman.assertion.model.nestingLevelTree.NestingType;
import agora.postman.assertion.model.nestingLevelTree.Tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static agora.postman.assertion.Main.DEBUG_MODE;
import static agora.postman.assertion.Main.HIERARCHY_SEPARATOR;
import static agora.postman.assertion.debug.DebugUtils.printVariableValueScript;

public class TestScriptManager {

//    private String parentBaseVariable;
    private Tree<String> tree;

    private String testScript;

    public TestScriptManager(Tree<String> programPointHierarchy) {
        this.tree = programPointHierarchy;
        this.testScript = "";
    }


    // Performs an in-depth search on the program point hierarchy tree and returns the test script
    // of a single APIOperation
    public String generateTestScript() {

        this.testScript = "valuesToConsiderAsNull = [];\n";

        this.programPointsDepthSearch(this.tree, new ArrayList<>(), null);

        // TODO: CHANGE THIS
        return this.testScript;
    }


    private void programPointsDepthSearch(Tree<String> tree, List<String> parents, String parentBaseVariable) {

        List<String> updatedParents = new ArrayList<>(parents);
        updatedParents.add(tree.getData());

        // TODO: Rename this variable
        String result = String.join(HIERARCHY_SEPARATOR, updatedParents);


        // Print initial lines of the nesting level TODO: Rename this method
        // TODO: Static or not?
        // TODO: RETURNS NEW parentBaseVariable
        parentBaseVariable = this.generateNestingLevelInitialLines(parents, parentBaseVariable, tree, result);

        for(Tree<String> child: tree.getChildren()) {
            // TODO: Check recursive behaviour of this.parentBaseVariable here
            programPointsDepthSearch(child, updatedParents, parentBaseVariable);
        }

        // TODO: Add closing brackets here (copy from original method)

        // TODO: Improve this condition
        if(!parents.isEmpty()) {
            // TODO: The first nesting level (response) should not have a closing if/for, unless there is arrayNesting


            String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

            if(tree.getNestingType().equals(NestingType.ARRAY)) {
                this.testScript += indentationStr + "\t} // Closing for " + parentBaseVariable + "\n";
            }

            this.testScript += indentationStr + "} // Closing if "+ parentBaseVariable + "\n\n";

        }

        // TODO: Create test cases
        // Close array nesting conditions
        Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
        if(!arrayNestingProgramPoints.isEmpty()) {
            int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());
            for(int i=maxArrayNestingLevel; i >= 1; i--) {

                this.testScript += "} // Closing if array nesting level " + i + "\n";
                this.testScript += "} // Closing for array nesting level " + i + "\n";

            }
        }

    }

    // TODO: Consider removing result parameter (I think it can be derived)
    private String generateNestingLevelInitialLines(List<String> parents, String parentBaseVariable, Tree<String> tree, String result) {

        String indentationStr = "\t".repeat(Math.max(parents.size() - 1, 0));

        // Print current nesting level (e.g., 200&data)
        String res = indentationStr + "// " + result + "\n";

        if(parents.isEmpty()){  // If we are in the first nesting level

            // TODO: Implement multiple array nesting (%array%array)

            // Assign base variable
            res = res + "response = pm.response.json();\n";
            parentBaseVariable = "response";

            // TODO: Implement if response == null ??

            res = res + "// TODO: Postman tests here\n";

            if(DEBUG_MODE) {
                res = res + printVariableValueScript(parentBaseVariable, "");
            }

            // TODO: Print here invariants of nested arrays
            // TODO: Modify Beet so the variable names of the %array program points support nesting (e.g., if %array%array, variableName: return_array_array)
            Map<Integer, ProgramPoint> arrayNestingProgramPoints = tree.getArrayNestingProgramPoints();
            // TODO: Multiple nesting levels, but maybe not all of them are present (e.g., maybe there are invariants for %array and %array%array%array, but not for %array%array)
            if(!arrayNestingProgramPoints.isEmpty()) {
                int maxArrayNestingLevel = Collections.max(arrayNestingProgramPoints.keySet());

                // TODO: START HERE
                // Iterate over all the array nesting levels
                for(int i=1; i<= maxArrayNestingLevel; i++) {
                    // Get nesting level variable
                    // parentBaseVariable is an array, we have to iterate over it
                    res = res + "if(" + parentBaseVariable + " != null) {\n";

                    // TODO: Generate test cases of this level
                    ProgramPoint arrayNestingProgramPoint = arrayNestingProgramPoints.get(i);
                    if (arrayNestingProgramPoint != null) {

                        res = res + "// Invariants of array nesting level " + i + "\n";

                        for(Invariant inv: arrayNestingProgramPoint.getInvariants()) {
                            res = res + inv.getPostmanTestCase(parentBaseVariable, indentationStr);
                        }


                    } else {
                        res = res + "// Array nesting level " + i + " has no invariants\n";
                    }

                    res = res + "// Access to the next nesting level\n";

                    String baseVariableIndex = parentBaseVariable + "_index";
                    String baseVariableElement = parentBaseVariable + "_element";

                    res = res + "for (" + baseVariableIndex + " in " + parentBaseVariable + ") {\n";

                    res = res + baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]\n";

                    parentBaseVariable = baseVariableElement;


                    // Update parentBaseVariable

                    // If the nesting level contains invariants, create the test cases

                }
                // TODO: END HERE
            }

            // Get invariants of this nesting level
            // TODO: Convert into function
            if(tree.getProgramPoint() != null) {

                res = res + "// Invariants of this nesting level:\n";

                for(Invariant inv: tree.getProgramPoint().getInvariants()) {
                    res = res + inv.getPostmanTestCase(parentBaseVariable, indentationStr);
                }
            } else {
                res = res + "// This nesting level has no invariants\n";
            }

            res = res + "\n";

        } else {    // If we are in a deeper nesting level

            // TODO: Implement multiple array nesting (%array)

            String data = tree.getData();

            String baseVariableAsignation = parentBaseVariable + "_" + data + " = " + parentBaseVariable + "." + data;
            parentBaseVariable = parentBaseVariable + "_" + data;

            res = res + indentationStr + baseVariableAsignation + "\n";
            res = res + indentationStr + "if(" + parentBaseVariable + " != null) {\n";

            // If the nesting type value is array
            if(tree.getNestingType().equals(NestingType.ARRAY)) {

                String baseVariableIndex = parentBaseVariable + "_index";
                String baseVariableElement = parentBaseVariable + "_element";

                res = res + indentationStr + "\tfor(" + baseVariableIndex + " in " + parentBaseVariable + ") {\n";
                res = res + indentationStr + "\t\t" + baseVariableElement + " = " + parentBaseVariable + "[" + baseVariableIndex + "]\n";

                parentBaseVariable = baseVariableElement;

            }

            res = res + indentationStr + "\t\t// TODO: Postman tests here\n";

            if(DEBUG_MODE) {
                res = res + printVariableValueScript(parentBaseVariable, indentationStr);
            }

            // Get invariants of this nesting level
            if(tree.getProgramPoint() != null) {    // TODO: Convert into function

                res = res + indentationStr + "\t\t// Invariants of this nesting level:\n";

                indentationStr = indentationStr + "\t\t";
                for(Invariant inv: tree.getProgramPoint().getInvariants()) {

                    res = res + inv.getPostmanTestCase(parentBaseVariable, indentationStr);

                }
            } else {
                res = res + indentationStr + "\t\t// This nesting level has no invariants\n";
            }

            res = res + "\n";

        }

        this.testScript += res;

        return parentBaseVariable;

    }



}
