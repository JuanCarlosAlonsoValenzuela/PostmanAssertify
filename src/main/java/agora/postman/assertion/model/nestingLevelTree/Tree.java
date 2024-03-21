package agora.postman.assertion.model.nestingLevelTree;

import agora.postman.assertion.model.ProgramPoint;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Juan C. Alonso
 * This Tree class is used to represent the nesting level hierarchy as a tree. The tree is created from a set
 * of paths (i.e., set of pptnames).
 */
public class Tree<T> implements Visitable<T>{

    // NB: LinkedHashSet preserves insertion order
    private final Set<Tree<T>> children = new LinkedHashSet<>();

    // Value of the current tree node
    private final T data;

    // Value of the corresponding program point
    private ProgramPoint programPoint;

    private Map<Integer, ProgramPoint> arrayNestingProgramPoints;

    private final NestingType nestingType;

    public T getData() {
        return data;
    }

    public NestingType getNestingType() {
        return nestingType;
    }

    public Set<Tree<T>> getChildren() {
        return children;
    }

    // Create a new tree with a single node
    public Tree(T data, NestingType nestingType) {
        this.data = data;
        this.nestingType = nestingType;
        this.arrayNestingProgramPoints = new HashMap<>();
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
    public Tree<T> child(T data, NestingType currentNestingType) {
        // Iterate over the children of the node
        for (Tree<T> child: children ) {
            // If the node exists
            if (child.data.equals(data)) {
                // Simply return the child (of type tree) thus updating the variable we are iterating on
                return child;
            }
        }

        // If the child does not exist, create a new node with that value (see method below)
        return child(new Tree<T>(data, currentNestingType));
    }

    // Adds a new child to a tree (after checking that the child does not exist)
    public Tree<T> child(Tree<T> child) {
        // Add the child to the tree
        children.add(child);
        // Return the child
        return child;
    }

    public static NestingType getNestingTypeFromString(String type) {
        if(type.equals("object")) {
            return NestingType.OBJECT;
        } else if(type.equals("array")) {
            return NestingType.ARRAY;
        } else {
            throw new RuntimeException("Unexpected nesting type value, expected one of: array, string");
        }
    }

    public ProgramPoint getProgramPoint() {
        return this.programPoint;
    }

    public void setProgramPoint(ProgramPoint programPoint) {
        this.programPoint = programPoint;
    }

    public Map<Integer, ProgramPoint> getArrayNestingProgramPoints() {
        return arrayNestingProgramPoints;
    }

    // Adds a new array nesting program point to the Tree
    public void addArrayNestingProgramPoint(ProgramPoint programPoint) {
        this.arrayNestingProgramPoints.put(programPoint.getArrayNesting(), programPoint);
    }

}
