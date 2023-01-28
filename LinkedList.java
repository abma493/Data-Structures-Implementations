import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Abraham Martinez
 */

public class LinkedList<E> implements IList<E> {
  // CS314 students. Add you instance variables here.
  private DoubleListNode<E> front;
  private DoubleListNode<E> back;
  private int size;

  public LinkedList() {
    front = new DoubleListNode<>();
    back = new DoubleListNode<>();
  }

  // Adds an item to the end of this list.
  public void add(E item) {
    if (size == 0) {
      front.setData(item);
      front.setNext(back);
      back.setPrev(front);
      size++;
    } else {
      DoubleListNode<E> current = front;
      while (current.getNext() != back) {
        current = current.getNext();
      }
      current.setNext(new DoubleListNode<E>(current, item, back));
      back.setPrev(current.getNext());
      size++;
    }
  }

  // Inserts an item at a specified location
  public void insert(int pos, E item) {
    if (pos < 0 || pos > size || item == null) {
      throw new IllegalArgumentException("Item or position invalid.");
    }
    if (pos == 0) {
      if (size == 0) {
        front = new DoubleListNode<E>(null, item, back);
        size++;
      } else {
        front = new DoubleListNode<E>(null, item, front);
        size++;
      }
    } else {
      DoubleListNode<E> current = nodeAt(pos - 1);
      current.setNext(new DoubleListNode<E>(current, item, current.getNext()));
      current.getNext().getNext().setPrev(current.getNext()); // correction 4/19/22
      size++;
    }
  }

  // Remove an element in the list based on position.
  public E remove(int pos) {
    if (pos < 0 || pos >= size) {
      throw new IllegalArgumentException("Invalid position.");
    }
    E val = null;
    if (pos == 0) {
      val = front.getData();
      front = front.getNext();
      front.setPrev(null);
      size--;
    } else {
      DoubleListNode<E> current = nodeAt(pos - 1);
      val = current.getNext().getData();
      current.setNext(current.getNext().getNext());
      size--;
    }
    return val;
  }

  // Remove the first occurence of object in this list.
  public boolean remove(E obj) {
    if (obj == null) {
      throw new IllegalArgumentException("Invalid object.");
    }
    DoubleListNode<E> current = front;
    for (int i = 0; i < size; i++) {
      if (current.getData().equals(obj)) {
        remove(i);
        return true;
      }
      current = current.getNext();
    }
    return false;

  }

  // Helper method to find a node at a specified position.
  private DoubleListNode<E> nodeAt(int pos) {
    DoubleListNode<E> current = front;
    for (int i = 0; i < pos; i++) {
      current = current.getNext();
    }
    return current;
  }

  // Return a sublist of elements in this list from start (incl.) to stop (excl.)
  public IList<E> getSubList(int start, int stop) {
    if (start < 0 || start > size || start > stop || stop > size) {
      throw new IllegalArgumentException("Enter a valid start and stop value.");
    }
    IList<E> result = new LinkedList<E>();
    for (int i = start; i < stop; i++) {
      result.add(this.get(i));
    }
    return result;
  }

  // Find the position of an element in the list.
  public int indexOf(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Item is invalid.");
    }
    int index = size - 1;
    while (index >= 0) {
      if (nodeAt(index).getData().equals(item)) {
        return index;
      }
      index--;
    }
    return -1;
  }

  // Find the position of an element in the string starting a specified position.
  public int indexOf(E item, int pos) {
    if (pos < 0 || pos > size || item == null) {
      throw new IllegalArgumentException("Invalid item or position.");
    }
    int index = pos;
    while (index < size) {
      if (nodeAt(index).getData().equals(item)) {
        return index;
      }
      index++;
    }
    return -1;
  }

  // Change the data at the specified position in the list.
  public E set(int pos, E item) {
    DoubleListNode<E> result = nodeAt(pos);
    E val = result.getData();
    result.setData(item);
    return val;
  }

  // Get an element from the list.
  public E get(int pos) {
    if (pos < 0 || pos > size) {
      throw new IllegalArgumentException("Invalid position.");
    }
    DoubleListNode<E> found = nodeAt(pos);
    E val = found.getData();
    return val;
  }

  // Return the list to an empty state.
  public void makeEmpty() {
    size = 0;
    front = new DoubleListNode<>();
    back = new DoubleListNode<>();
  }

  /*
   * Returns an Iterator for this list.
   * 
   * Perfect example of applying an anonymous class for improved readability.
   * We created a one-time-use class with no name that implictly implements the
   * Iterator interface. Remember that an anonymous class must extend a superclass
   * OR implement an interface. Within the anon. class, we overwrote the interface
   * methods. RECALL an anon. class does NOT have a constructor as a result of its
   * inability to be instantiated anywhere else within this class or outside.
   */
  public Iterator<E> iterator() {
    Iterator<E> itr = new Iterator<E>() {
      private DoubleListNode<E> current = front;
      private boolean removeOK = false;

      @Override
      public boolean hasNext() {
        return current != back && size != 0;
      }

      @Override
      public E next() {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        E result = current.getData();
        current = current.getNext();
        removeOK = true;
        return result;
      }

      @Override
      public void remove() {
        if (!removeOK) {
          throw new IllegalStateException();
        }
        size--;

        DoubleListNode<E> toRemove = current.getPrev();
        if (toRemove.getPrev() == null) {
          front = current;
          front.setPrev(null);
        } else {
          current.setPrev(toRemove.getPrev());
          toRemove.getPrev().setNext(current);
        }

        removeOK = false;
      }
    };
    return itr;
  }

  /*
   * Removes all elements in this list from start (incl.) to stop (excl.)
   * Think of Concurrent Modification issues; items are removed from end to
   * beginning
   * to prevent values from shifting too early.
   */
  public void removeRange(int start, int stop) {
    if (start < 0 || start > size || start > stop || stop > size) {
      throw new IllegalArgumentException("Enter a valid start and stop value.");
    }
    DoubleListNode<E> current;
    if (start == 0) {
      current = nodeAt(stop);
      front = current;
      front.setNext(current.getNext());
      size = size - stop;
    } else {
      current = nodeAt(start - 1);
      current.setNext(nodeAt(stop));
      size = size - (stop - start);
    }
  }

  @Override
  /*
   * Determines if this LinkedList is equal to other.
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof LinkedList) || obj == null) {
      return false;
    }
    if (obj == this) { // is obj in param the same as this object calling?
      return true;
    }
    LinkedList<?> other = (LinkedList<?>) obj;
    if (other.size != this.size) {
      return false;
    }
    for (int i = 0; i < size; i++) {
      if (!(this.get(i).equals(other.get(i)))) {
        return false;
      }
    }
    return true;
  }

  /**
   * add item to the front of the list. <br>
   * pre: item != null <br>
   * post: size() = old size() + 1, get(0) = item
   *
   * @param item the data to add to the front of this list
   */
  public void addFirst(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Item is null.");
    }
    if (size == 0) {
      front.setData(item);
      front.setNext(back);
      size++;
    } else {
      front.setPrev(new DoubleListNode<E>(null, item, front));
      front = front.getPrev();
      size++;
    }
  }

  /**
   * add item to the end of the list. <br>
   * pre: item != null <br>
   * post: size() = old size() + 1, get(size() - 1) = item
   *
   * @param item the data to add to the end of this list
   */
  public void addLast(E item) {
    if (item == null) {
      throw new IllegalArgumentException("Item is null.");
    }
    if (size == 0) {
      front.setData(item);
      front.setNext(back);
      size++;
    } else {
      DoubleListNode<E> prev = back.getPrev();
      prev.setNext(new DoubleListNode<E>(prev, item, back));
      back.setPrev(prev.getNext());
      size++;
    }
  }

  // returns the size of the list.
  public int size() {
    return size;
  }

  /**
   * remove and return the first element of this list. <br>
   * pre: size() > 0 <br>
   * post: size() = old size() - 1
   *
   * @return the old first element of this list
   */
  public E removeFirst() {
    if (size <= 0) {
      throw new IllegalStateException("There are not elements.");
    }
    E result = this.remove(0);
    return result;
  }

  /**
   * remove and return the last element of this list. <br>
   * pre: size() > 0 <br>
   * post: size() = old size() - 1
   *
   * @return the old last element of this list
   */
  public E removeLast() {
    if (size == 0) {
      throw new IllegalArgumentException("List is empty.");
    }
    return this.remove(size - 1);
  }

  // Returns the list as a string.
  public String toString() {
    if (front.getData() == null) {
      return "[]";
    }
    String result = "[" + front.getData();
    DoubleListNode<E> current = front.getNext();
    while (current != back) {
      result += ", " + current.getData();
      current = current.getNext();
    }
    result += "]";
    return result;
  }
}
