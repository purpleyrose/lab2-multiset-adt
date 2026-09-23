import java.util.ArrayList;
import java.util.Objects;

public class Tree extends MultiSet {
    // We recommend attempting this class last, as it hasn't been scaffolded for your team.
    // Even if your team doesn't have time to implement this class, it is a useful exercise
    // to think about how d you might split up the work to get the Tree and TreeMultiSet
    // implemented.
    private int root;
    private ArrayList<Tree> subtrees;

    public Tree(int root, ArrayList<Tree> subtrees) {
        this.root = root;
        this.subtrees = Objects.requireNonNullElseGet(subtrees, ArrayList::new);
    }

    public Tree() {
        this(0, new ArrayList<>());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Tree other = (Tree) obj;
        if (root != other.root) {
            return false;
        }
        return subtrees.equals(other.subtrees);

    }


    @Override
    void add(int item) {

    }

    @Override
    void remove(int item) {

    }

    @Override
    boolean contains(int item) {
        if (isEmpty()) {
            return false;
        }
        else if (root == item) {
            return true;
        }
        else {
            for (Tree tree : subtrees) {
                if (tree.contains(item)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return boolean true if the tree is empty, which means the root is zero and the subtrees are empty
     */
    @Override
    boolean isEmpty() {
        return root == 0 && subtrees.isEmpty();
    }

    @Override
    int count(int item) {
        return 0;
    }

    /**
     * Returns the number of nodes in the tree.
     * @return the number of nodes in the tree
     */
    @Override
    int size() {
        if (isEmpty()) {
            return 0;
        }
       int size = 1;
       for (Tree tree : subtrees) {
           size += tree.size();
       }
       return size;
    }

    @Override
    public String toString() {
        return "Tree{" + "root=" + root + ", subtrees=" + subtrees + '}';
    }


}
