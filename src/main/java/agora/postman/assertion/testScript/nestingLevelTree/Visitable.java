package agora.postman.assertion.testScript.nestingLevelTree;

/**
 * @author Juan C. Alonso
 */
public interface Visitable<T> {
    void accept(Visitor<T> visitor);
}
