package agora.postman.assertion.testScript.nestingLevelTree;

/**
 * @author Juan C. Alonso
 */
public interface Visitor<T> {

    Visitor<T> visitTree(Tree<T> tree);

    void visitData(Tree<T> parent, T data);

}
