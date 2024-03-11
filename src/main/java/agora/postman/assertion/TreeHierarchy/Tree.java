package agora.postman.assertion.TreeHierarchy;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Juan C. Alonso
 */
public class Tree<T> implements Visitable<T>{

    // NB: LinkedHashSet preserves insertion order
    private final Set<Tree<T>> children = new LinkedHashSet<>();

    // Value of the current tree node
    private final T data;

    // Create a new tree with a single node
    public Tree(T data) {
        this.data = data;
    }

    // Used to print the tree
    public void accept(Visitor<T> visitor) {
        visitor.visitData(this, data);

        for (Tree<T> child : children) {
            Visitor<T> childVisitor = visitor.visitTree(child);
            child.accept(childVisitor);
        }
    }

    // Tries to add a new child (node) to the tree
    public Tree<T> child(T data) {
        // Iterate over the children of the node
        for (Tree<T> child: children ) {
            // If the node exists
            if (child.data.equals(data)) {
                // Simply return the child (of type tree) thus updating the variable we are iterating on
                return child;
            }
        }

        // If the child does not exist, create a new node with that value (see method below)
        return child(new Tree<T>(data));
    }

    // Adds a new child to a tree (after checking that the child does not exist)
    public Tree<T> child(Tree<T> child) {
        // Add the child to the tree
        children.add(child);
        // Return the child
        return child;
    }

}
