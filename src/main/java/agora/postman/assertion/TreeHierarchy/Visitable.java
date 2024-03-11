package agora.postman.assertion.TreeHierarchy;

/**
 * @author Juan C. Alonso
 */
public interface Visitable<T> {
    void accept(Visitor<T> visitor);
}
