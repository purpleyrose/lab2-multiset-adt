import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    /** Helper to build a Tree with the given root and children. */
    private static Tree tree(int root, Tree... children) {
        return new Tree(root, new ArrayList<>(Arrays.asList(children)));
    }

    /**
     * Builds the tree:
     *        1
     *      / | \
     *     2  3  4
     *    / \     \
     *   5   2     6
     */
    private static Tree sampleTree() {
        return tree(1,
                tree(2, tree(5), tree(2)),
                tree(3),
                tree(4, tree(6)));
    }

    // ---------- constructor ----------

    @Test
    public void testConstructorNullSubtrees() {
        Tree t = new Tree(7, null);
        assertEquals(1, t.size());
        assertTrue(t.contains(7));
    }

    // ---------- isEmpty ----------

    @Test
    public void testIsEmptyDefaultConstructor() {
        Tree t = new Tree();
        assertTrue(t.isEmpty());
    }

    @Test
    public void testIsEmptyLeaf() {
        Tree t = tree(5);
        assertFalse(t.isEmpty());
    }

    @Test
    public void testIsEmptyWithSubtrees() {
        assertFalse(sampleTree().isEmpty());
    }

    // ---------- size ----------

    @Test
    public void testSizeEmpty() {
        assertEquals(0, new Tree().size());
    }

    @Test
    public void testSizeLeaf() {
        assertEquals(1, tree(5).size());
    }

    @Test
    public void testSizeOneLevel() {
        Tree t = tree(1, tree(2), tree(3));
        assertEquals(3, t.size());
    }

    @Test
    public void testSizeMultiLevel() {
        assertEquals(7, sampleTree().size());
    }

    @Test
    public void testSizeLinearChain() {
        Tree t = tree(1, tree(2, tree(3, tree(4))));
        assertEquals(4, t.size());
    }

    // ---------- contains ----------

    @Test
    public void testContainsEmpty() {
        // An empty tree contains nothing, including the sentinel root value 0.
        assertFalse(new Tree().contains(0));
    }

    @Test
    public void testContainsEmptyNonZero() {
        assertFalse(new Tree().contains(5));
    }

    @Test
    public void testContainsRoot() {
        assertTrue(sampleTree().contains(1));
    }

    @Test
    public void testContainsChild() {
        assertTrue(sampleTree().contains(3));
    }

    @Test
    public void testContainsDeepLeaf() {
        assertTrue(sampleTree().contains(6));
    }

    @Test
    public void testContainsMissing() {
        assertFalse(sampleTree().contains(99));
    }

    @Test
    public void testContainsNegative() {
        Tree t = tree(-3, tree(-7));
        assertTrue(t.contains(-7));
        assertFalse(t.contains(7));
    }

    // ---------- equals ----------

    @Test
    public void testEqualsSameInstance() {
        Tree t = sampleTree();
        assertEquals(t, t);
    }

    @Test
    public void testEqualsStructurallyEqual() {
        assertEquals(sampleTree(), sampleTree());
    }

    @Test
    public void testEqualsBothEmpty() {
        assertEquals(new Tree(), new Tree());
    }

    @Test
    public void testNotEqualsDifferentRoot() {
        assertNotEquals(tree(1, tree(2)), tree(9, tree(2)));
    }

    @Test
    public void testNotEqualsDifferentSubtrees() {
        assertNotEquals(tree(1, tree(2)), tree(1, tree(3)));
    }

    @Test
    public void testNotEqualsDifferentSubtreeOrder() {
        assertNotEquals(tree(1, tree(2), tree(3)), tree(1, tree(3), tree(2)));
    }

    @Test
    public void testNotEqualsNull() {
        assertNotEquals(null, sampleTree());
    }

    @Test
    public void testNotEqualsOtherType() {
        assertNotEquals("Tree", sampleTree());
    }

    // ---------- toString ----------

    @Test
    public void testToStringLeaf() {
        assertEquals("Tree{root=5, subtrees=[]}", tree(5).toString());
    }

    @Test
    public void testToStringNested() {
        assertEquals("Tree{root=1, subtrees=[Tree{root=2, subtrees=[]}]}",
                tree(1, tree(2)).toString());
    }

    // ---------- count ----------

    @Test
    public void testCountEmpty() {
        assertEquals(0, new Tree().count(1));
    }

    @Test
    public void testCountMissing() {
        assertEquals(0, sampleTree().count(99));
    }

    @Test
    public void testCountOnce() {
        assertEquals(1, sampleTree().count(6));
    }

    @Test
    public void testCountDuplicates() {
        // 2 appears both as an inner node and as a leaf
        assertEquals(2, sampleTree().count(2));
    }

    // ---------- add ----------

    @Test
    public void testAddToEmpty() {
        Tree t = new Tree();
        t.add(5);
        assertTrue(t.contains(5));
        assertEquals(1, t.size());
    }

    @Test
    public void testAddToNonEmpty() {
        Tree t = sampleTree();
        t.add(42);
        assertTrue(t.contains(42));
        assertEquals(8, t.size());
    }

    @Test
    public void testAddDuplicate() {
        Tree t = tree(3);
        t.add(3);
        assertEquals(2, t.count(3));
        assertEquals(2, t.size());
    }

    // ---------- remove ----------

    @Test
    public void testRemoveFromEmpty() {
        Tree t = new Tree();
        t.remove(5);
        assertTrue(t.isEmpty());
    }

    @Test
    public void testRemoveMissing() {
        Tree t = sampleTree();
        t.remove(99);
        assertEquals(sampleTree(), t);
    }

    @Test
    public void testRemoveOnlyItem() {
        Tree t = tree(5);
        t.remove(5);
        assertTrue(t.isEmpty());
        assertEquals(0, t.size());
    }

    @Test
    public void testRemoveLeaf() {
        Tree t = sampleTree();
        t.remove(6);
        assertFalse(t.contains(6));
        assertEquals(6, t.size());
    }

    @Test
    public void testRemoveRoot() {
        Tree t = sampleTree();
        t.remove(1);
        assertFalse(t.contains(1));
        assertEquals(6, t.size());
        // all other items are still present
        for (int item : new int[]{2, 3, 4, 5, 6}) {
            assertTrue(t.contains(item), "missing " + item);
        }
    }

    @Test
    public void testRemoveInnerNodeKeepsDescendants() {
        Tree t = sampleTree();
        t.remove(4);
        assertFalse(t.contains(4));
        assertTrue(t.contains(6));
        assertEquals(6, t.size());
    }

    @Test
    public void testRemoveOnlyOneOccurrence() {
        Tree t = sampleTree();
        t.remove(2);
        assertEquals(1, t.count(2));
        assertEquals(6, t.size());
    }
}
