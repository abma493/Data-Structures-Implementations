import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A binary search tree class.
 *
 * @author Abraham Martinez
 * @version 1.1
 * @param <E> The data type of the elements of this BinarySearchTree.
 *            Must implement Comparable or inherit from a class that implements
 *            Comparable.
 *
 */
public class BinarySearchTree<E extends Comparable<? super E>> {

  private BSTNode<E> root;
  private int size;
  private int counter;
  private boolean logicalSwitch; // employs two separate functions for add() and remove()

  public BinarySearchTree() {
    root = null;
  }

  /**
   * Add the specified item to this Binary Search Tree if it is not already
   * present.
   * <br>
   * pre: <tt>value</tt> != null<br>
   * post: Add value to this tree if not already present. Return true if this tree
   * changed as a result of this method call, false otherwise.
   * 
   * @param value the value to add to the tree
   * @return false if an item equivalent to value is already present
   *         in the tree, return true if value is added to the tree and size() =
   *         old size() + 1
   */
  public boolean add(E value) {
    logicalSwitch = true; // notDuplicate bool
    root = add(value, root);
    size += logicalSwitch ? 1 : 0;
    return logicalSwitch;
  }

  // helper for add
  private BSTNode<E> add(E value, BSTNode<E> root) {
    if (root == null) {
      root = new BSTNode<E>(value);
    } else if (root.data.equals(value)) {
      logicalSwitch = false;
    } else if (value.compareTo(root.data) <= 0) {
      root.setLeft(add(value, root.left));
    } else {
      root.setRight(add(value, root.right));
    }
    return root; // same root is delivered back, unless it was null.
  }

  /**
   * Remove a specified item from this Binary Search Tree if it is present.
   * <br>
   * pre: <tt>value</tt> != null<br>
   * post: Remove value from the tree if present, return true if this tree
   * changed as a result of this method call, false otherwise.
   * 
   * @param value the value to remove from the tree if present
   * @return false if value was not present
   *         returns true if value was present and size() = old size() - 1
   */
  public boolean remove(E value) {
    if (value == null) {
      throw new IllegalArgumentException("Value is null");
    }
    logicalSwitch = false; // isPresent bool
    root = remove(value, root);
    size -= logicalSwitch ? 1 : 0;
    return logicalSwitch;
  }

  /*
   * Helper for remove
   * 
   * Update: 11/19/21 @11:15PM
   * Update: 5/5/22 @8:11PM
   * Desc: Algorithm updated. Correction for ample cases.
   * 
   */
  private BSTNode<E> remove(E value, BSTNode<E> root) {
    if (root != null) {
      if (root.data.equals(value)) {
        logicalSwitch = true;
        if (root.getLeft() == null && root.getRight() == null) { // leaf node
          root = null;
        } else if (root.getLeft() == null) {
          root = root.right;
        } else if (root.getRight() == null) {
          root = root.left;
        } else { // both nodes
          BSTNode<E> search = root.right;
          while (search.getLeft() != null) {
            search = search.getLeft();
          }
          root.setData(search.getData());
          root.right = remove(root.data, root.right); // important
        }
      } else {
        if (value.compareTo(root.getData()) < 0) {
          root.setLeft(remove(value, root.left));
        } else {
          root.setRight(remove(value, root.right));
        }
      }
    }
    return root;
  }

  /**
   * Check to see if the specified element is in this Binary Search Tree.
   * <br>
   * pre: <tt>value</tt> != null<br>
   * post: return true if value is present in tree, false otherwise
   * 
   * @param value the value to look for in the tree
   * @return true if value is present in this tree, false otherwise
   */
  public boolean isPresent(E value) {
    if (value == null) {
      throw new IllegalArgumentException("Value is null");
    }
    return isPresent(value, root);
  }

  // helper for isPresent
  private boolean isPresent(E value, BSTNode<E> root) {
    if (root == null) {
      return false;
    } else {
      int compare = value.compareTo(root.data);
      if (compare == 0) { // node found!
        return true;
      } else if (compare < 0) { // value smaller than node value
        return isPresent(value, root.getLeft());
      } else { // value greater than node value
        return isPresent(value, root.getRight());
      }
    }
  }

  /**
   * Return how many elements are in this Binary Search Tree.
   * <br>
   * pre: none<br>
   * post: return the number of items in this tree
   * 
   * @return the number of items in this Binary Search Tree
   */
  public int size() {
    return size;
  }

  /**
   * return the height of this Binary Search Tree.
   * <br>
   * pre: none<br>
   * post: return the height of this tree.
   * If the tree is empty return -1, otherwise return the
   * height of the tree
   * 
   * @return the height of this tree or -1 if the tree is empty
   */
  public int height() {
    return height(root);
  }

  // helper for height
  private int height(BSTNode<E> root) {
    if (root == null) {
      return -1;
    } else if (root.left == null && root.right == null) { // is it a leaf?
      return 0;
    } else {
      return 1 + Math.max(height(root.left), height(root.right));
    }
  }

  /**
   * Return a list of all the elements in this Binary Search Tree.
   * <br>
   * pre: none<br>
   * post: return a List object with all data from the tree in ascending order.
   * If the tree is empty return an empty List
   * 
   * @return a List object with all data from the tree in sorted order
   *         if the tree is empty return an empty List
   */
  public List<E> getAll() {
    List<E> result = new ArrayList<>();
    getAll(result, root);
    Collections.sort(result);
    return result;
  }

  // helper for getAll
  private void getAll(List<E> result, BSTNode<E> root) {
    if (root != null) {
      result.add(root.data);
      if (root.left != null) {
        getAll(result, root.left);
      }
      if (root.right != null) {
        getAll(result, root.right);
      }
    }
  }

  /**
   * return the maximum value in this binary search tree.
   * <br>
   * pre: <tt>size()</tt> > 0<br>
   * post: return the largest value in this Binary Search Tree
   * 
   * @return the maximum value in this tree
   */
  public E max() {
    if (size <= 0) {
      throw new IllegalArgumentException("Tree is empty.");
    }
    return max(root);
  }

  // helper for max
  private E max(BSTNode<E> root) {
    E result = root.getData();
    while (root.getRight() != null) {
      result = root.getRight().getData();
      root = root.getRight();
    }
    return result;
  }

  /**
   * return the minimum value in this binary search tree.
   * <br>
   * pre: <tt>size()</tt> > 0<br>
   * post: return the smallest value in this Binary Search Tree
   * 
   * @return the minimum value in this tree
   */
  public E min() {
    if (size <= 0) {
      throw new IllegalArgumentException("Tree is empty");
    }
    return min(root);
  }

  // helper for min
  private E min(BSTNode<E> root) {
    E result = root.getData();
    while (root.getLeft() != null) {
      result = root.getLeft().getData();
      root = root.getLeft();
    }
    return result;
  }

  /**
   * An add method that implements the add algorithm iteratively
   * instead of recursively.
   * <br>
   * pre: data != null
   * <br>
   * post: if data is not present add it to the tree,
   * otherwise do nothing.
   * 
   * @param data the item to be added to this tree
   * @return true if data was not present before this call to add,
   *         false otherwise.
   */
  public boolean iterativeAdd(E data) {
    logicalSwitch = true; // notDuplicate bool
    if (root != null) {
      logicalSwitch = !data.equals(root.data); // check duplicity in starting root
      BSTNode<E> root = this.root;
      if (data.compareTo(root.data) <= 0 && logicalSwitch) {
        while (root.getLeft() != null && logicalSwitch) {
          root = root.getLeft();
          logicalSwitch = !data.equals(root.data); // check duplicity in left branch
        }
        if (logicalSwitch)
          root.setLeft(new BSTNode<E>(data));
      } else if (logicalSwitch) {
        while (root.getRight() != null && logicalSwitch) {
          root = root.getRight();
          logicalSwitch = !data.equals(root.data); // check duplicity in right branch
        }
        if (logicalSwitch)
          root.setRight(new BSTNode<E>(data));
      }
    } else {
      root = new BSTNode<E>(data);
    }
    size += logicalSwitch ? 1 : 0;
    return logicalSwitch;
  }

  /**
   * Return the "kth" element in this Binary Search Tree. If kth = 0 the
   * smallest value (minimum) is returned.
   * If kth = 1 the second smallest value is returned, and so forth.
   * <br>
   * pre: 0 <= kth < size()
   * 
   * @param kth indicates the rank of the element to get
   * @return the kth value in this Binary Search Tree
   */
  public E get(int kth) {
    if (kth < 0 || kth >= size) {
      throw new IllegalArgumentException("Kth element out of bounds");
    }
    counter = 0;
    return get(root, kth);
  }

  /* Helper for get method (uses inorder traversal) */
  private E get(BSTNode<E> root, int kth) {

    // base case
    if (root == null)
      return null;

    // search left
    E left = get(root.left, kth);

    // if kth's smallest is found in left subtree, return it.
    if (left != null) {
      return left;
    }

    // if current element is kth's smallest, return it
    if (counter == kth)
      return root.data;
    counter++;

    // else search in right subtree
    return get(root.right, kth);
  }

  /**
   * Return a List with all values in this Binary Search Tree
   * that are less than the parameter <tt>value</tt>.
   * <tt>value</tt> != null<br>
   * 
   * @param value the cutoff value
   * @return a List with all values in this tree that are less than
   *         the parameter value. If there are no values in this tree less
   *         than value return an empty list. The elements of the list are
   *         in ascending order.
   */
  public List<E> getAllLessThan(E value) {
    if (value == null)
      throw new IllegalArgumentException("Value is null.");
    List<E> result = new ArrayList<>();
    getAllLessThan(result, root, value);
    return result;
  }

  /*
   * helper for getAllLessThan
   * Cool thing here is that since it does an inorder traversal, it
   * adds items to the list in ascending order without calling Collections.sort!
   */
  private void getAllLessThan(List<E> result, BSTNode<E> root, E value) {
    if (root == null) {
      return;
    }
    // getAllLessThan(result, root.left, value);
    // if(root.data.compareTo(value) < 0) {
    // result.add(root.data);
    // }
    // getAllLessThan(result, root.right, value);

    if (root.data.compareTo(value) < 0) {
      result.add(root.data);
    }
    getAllLessThan(result, root.left, value);
    getAllLessThan(result, root.right, value);
  }

  /**
   * Return a List with all values in this Binary Search Tree
   * that are greater than the parameter <tt>value</tt>.
   * <tt>value</tt> != null<br>
   * 
   * @param value the cutoff value
   * @return a List with all values in this tree that are greater
   *         than the parameter value. If there are no values in this tree
   *         greater than value return an empty list.
   *         The elements of the list are in ascending order.
   */
  public List<E> getAllGreaterThan(E value) {
    if (value == null) {
      throw new IllegalArgumentException("Value is null.");
    }
    List<E> result = new ArrayList<>();
    getAllGreaterThan(result, root, value);
    return result;
  }

  // helper for getAllGreaterThan
  private void getAllGreaterThan(List<E> result, BSTNode<E> root, E value) {
    if (root == null) {
      return;
    }
    getAllGreaterThan(result, root.left, value);
    if (root.data.compareTo(value) > 0) {
      result.add(root.data);
    }
    getAllGreaterThan(result, root.right, value);
  }

  /**
   * Find the number of nodes in this tree at the specified depth.
   * <br>
   * pre: none
   * 
   * @param d The target depth.
   * @return The number of nodes in this tree at a depth equal to
   *         the parameter d.
   */
  public int numNodesAtDepth(int d) {
    return numNodesAtDepth(root, d);
  }

  private int numNodesAtDepth(BSTNode<E> root, int d) {
    if (root == null) {
      return 0;
    }
    if (d == 0) {
      return 1;
    } else {
      return numNodesAtDepth(root.right, d - 1) + numNodesAtDepth(root.left, d - 1);
    }
  }

  /**
   * Prints a vertical representation of this tree.
   * The tree has been rotated counter clockwise 90
   * degrees. The root is on the left. Each node is printed
   * out on its own row. A node's children will not necessarily
   * be at the rows directly above and below a row. They will
   * be indented three spaces from the parent. Nodes indented the
   * same amount are at the same depth.
   * <br>
   * pre: none
   */
  public void printTree() {
    printTree(root, "");
  }

  private void printTree(BSTNode<E> n, String spaces) {
    if (n != null) {
      printTree(n.getRight(), spaces + "  ");
      System.out.println(spaces + n.getData());
      printTree(n.getLeft(), spaces + "  ");
    }
  }

  protected static class BSTNode<E extends Comparable<? super E>> {
    /*
     * Static inner class used to define the properties of a Node in a BST.
     * 
     * It takes a type E that extends Comparable, that is, type E is
     * expected to implement Comparable and type E can be any type that implements
     * Comparable. Moreover, the Comparable interface is expected to take some type
     * which is an ancestor of E.
     */
    private E data;
    private int height;
    private BSTNode<E> left;
    private BSTNode<E> right;

    @SuppressWarnings("unused")
    public BSTNode() {
      this(null);
      height = 0;
    }

    public BSTNode(E initValue) {
      this(null, initValue, null);
      height = 0;
    }

    public BSTNode(BSTNode<E> initLeft,
        E initValue,
        BSTNode<E> initRight) {
      data = initValue;
      left = initLeft;
      right = initRight;
      height = 0;
    }

    public E getData() {
      return data;
    }

    public BSTNode<E> getLeft() {
      return left;
    }

    public BSTNode<E> getRight() {
      return right;
    }

    public int getHeight() {
      return height;
    }

    @SuppressWarnings("unused")
    public void setData(E theNewValue) {
      data = theNewValue;
    }

    public void setLeft(BSTNode<E> theNewLeft) {
      left = theNewLeft;
    }

    public void setRight(BSTNode<E> theNewRight) {
      right = theNewRight;
    }
  }
}
