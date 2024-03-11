package agora.postman.assertion.TreeHierarchy;

/**
 * @author Juan C. Alonso
 */
public class PrintIndentedVisitor implements Visitor<String> {

    private final int indent;

    PrintIndentedVisitor(int indent) {
        this.indent = indent;
    }

    public Visitor<String> visitTree(Tree<String> tree) {
        return new PrintIndentedVisitor(indent + 2);
    }

    public void visitData(Tree<String> parent, String data) {
        for (int i = 0; i < indent; i++) { // TODO: naive implementation
            System.out.print(" ");
        }

        System.out.println(data);
    }

}
