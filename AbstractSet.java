/*  
 * Written by Abraham Martinez
 *
 * An abstract class for Sets
 */

import java.util.Iterator;

public abstract class AbstractSet<E> implements ISet<E> {

  /*
   * Sole constructor. (For invocation by subclass constructors,
   * typically implicit.)
   */
  protected AbstractSet() {
  }

  // MUST be implemented by subclass
  public abstract Iterator<E> iterator();

  // MUST be implemented by subclass
  public abstract int size();

  // MUST be implemented by subclass
  public abstract boolean add(E item);

  @Override
  public boolean addAll(ISet<E> set) {
    if (set == null) {
      throw new IllegalArgumentException("Set is null");
    }
    boolean modified = false;
    for (E elem : set) {
      if (add(elem)) {
        modified = true;
      }
    }
    return modified;
  }

  @Override
  public void clear() {
    Iterator<E> itr = this.iterator();
    while (itr.hasNext()) {
      itr.next();
      itr.remove();
    }
  }

  @Override
  public boolean contains(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Item is null");
    }
    Iterator<E> itr = this.iterator();
    while (itr.hasNext()) {
      if (itr.next().equals(item)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean containsAll(ISet<E> otherSet) {
    if (otherSet == null) {
      throw new IllegalArgumentException("other Set is invalid");
    }
    for (E e : otherSet) {
      if (!contains(e)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ISet)) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    assert obj instanceof ISet;
    @SuppressWarnings("unchecked")
    ISet<E> other = (ISet<E>) obj;
    return containsAll(other);
  }

  @Override
  // Overrides Object's clone method.
  public abstract Object clone();

  public ISet<E> difference(ISet<E> otherSet) {
    if (otherSet == null) {
      throw new IllegalArgumentException("other Set is invalid");
    }
    @SuppressWarnings("unchecked")
    ISet<E> result = (ISet<E>) this.clone();
    for (E e : this) {
      if (!otherSet.contains(e)) {
        result.add(e);
      }
    }
    return result;
  }

  /**
   * Return a String version of this set.
   * Format is (e1, e2, ... en)
   * 
   * @return A String version of this set.
   */
  public String toString() {
    StringBuilder result = new StringBuilder();
    String separator = ", ";
    result.append("(");
    Iterator<E> it = this.iterator();
    while (it.hasNext()) {
      result.append(it.next());
      result.append(separator);
    }
    // get rid of extra separator
    if (this.size() > 0) {
      result.setLength(result.length() - separator.length());
    }
    result.append(")");
    return result.toString();
  }
}