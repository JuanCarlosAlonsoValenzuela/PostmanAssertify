package agora.postman.assertion.model.nestingLevelTree;

/**
 * @author Juan C. Alonso
 */
public interface Visitable<T> {
    void accept(Visitor<T> visitor);
}
