/*  
 *
 *  Written by Abraham Martinez 
 *
 *  Based on a skeletal structure provided by UTCS
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * In this implementation of the ISet interface the elements in the Set are
 * maintained in ascending order.
 * 
 * The data type for E must be a type that implements Comparable.
 * 
 * Implement methods that were not implemented in AbstractSet
 * and override methods that can be done more efficiently. An ArrayList must
 * be used as the internal storage container. For methods involving two set,
 * if that method can be done more efficiently if the other set is also a
 * SortedSet, then do so.
 */
public class SortedSet<E extends Comparable<? super E>> extends AbstractSet<E> {

  private ArrayList<E> mySortedSet;
  private int size;

  /**
   * create an empty SortedSet
   */
  public SortedSet() {
    mySortedSet = new ArrayList<>();
  }

  /**
   * create a SortedSet out of an unsorted set. <br>
   * 
   * @param other != null
   */
  public SortedSet(ISet<E> other) {
    mySortedSet = new ArrayList<>();
    for (E e : other) {
      this.add(e);
    }
  }

  /**
   * Return the smallest element in this SortedSet.
   * <br>
   * pre: size() != 0
   * 
   * @return the smallest element in this SortedSet.
   */
  public E min() {
    if (size == 0) {
      throw new IllegalStateException("Set is Empty");
    }
    return mySortedSet.get(0);
  }

  /**
   * Return the largest element in this SortedSet.
   * <br>
   * pre: size() != 0
   * 
   * @return the largest element in this SortedSet.
   */
  public E max() {
    return mySortedSet.get(size - 1);
  }

  @Override
  public boolean add(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Invalid item.");
    }
    if (size >= 1) {
      int index = 0;
      while (index < size) {
        if (mySortedSet.get(index).equals(item)) {
          return false;
        }
        if (item.compareTo(mySortedSet.get(index)) < 0) {
          mySortedSet.add(index, item);
          size++;
          return true;
        }
        if (index == size - 1) {
          mySortedSet.add(item);
          size++;
          return true;
        }
        index++;
      }
    } else { // 1st item
      mySortedSet.add(item);
      size++;
    }
    return true;
  }

  @Override
  public ISet<E> intersection(ISet<E> otherSet) {
    if (otherSet == null) {
      throw new IllegalArgumentException("other set is invalid");
    }
    ISet<E> result = new UnsortedSet<>();
    for (E e : this) {
      if (otherSet.contains(e)) {
        result.add(e);
      }
    }
    return result;
  }

  @Override
  public Iterator<E> iterator() {
    Iterator<E> itr = new Iterator<E>() {
      private int current;
      private int erase;
      public boolean removeOK;

      @Override
      public boolean hasNext() {
        return current < size;
      }

      @Override
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        E val = mySortedSet.get(current);
        erase = current;
        current++;
        removeOK = true;
        return val;
      }

      @Override
      public void remove() {
        if (!removeOK) {
          throw new IllegalStateException();
        }
        mySortedSet.remove(erase);
        current--;
        size--;
        removeOK = false;
      }
    };
    return itr;
  }

  @Override
  public boolean remove(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Item is null");
    }
    boolean removed = mySortedSet.remove(item);
    size -= removed ? 1 : 0;
    return removed;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public ISet<E> union(ISet<E> otherSet) {
    if (otherSet == null) {
      throw new IllegalArgumentException("other set is invalid");
    }
    ISet<E> result = new UnsortedSet<>();
    for (E e : this) {
      result.add(e);
      for (E v : otherSet) {
        result.add(v);
      }
    }
    return result;
  }

  @Override
  public Object clone() {
    Object result = new SortedSet<>();
    return result;
  }

}