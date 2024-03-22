package agora.postman.assertion.testScript.nestingLevelTree;

/**
 * @author Juan C. Alonso
 * This class, implementing the Visitor interface, is used to print, using identation, the tree representing the
 * nesting level hierarchy.
 */
public class PrintIndentedVisitor implements Visitor<String> {

    private final int indent;

    public PrintIndentedVisitor(int indent) {
        this.indent = indent;
    }

    public Visitor<String> visitTree(Tree<String> tree) {
        return new PrintIndentedVisitor(indent + 2);
    }

    public void visitData(Tree<String> parent, String data) {
        for (int i = 0; i < indent; i++) {
            System.out.print(" ");
        }

        System.out.println(data);
    }

}
