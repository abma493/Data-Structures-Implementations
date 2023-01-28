/*  
 * Written by Abraham Martinez
 *
 * Based on a skeletal structure provided by UTCS
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A simple implementation of an ISet. 
 * Elements are not in any particular order.
 * Students are to implement methods that 
 * were not implemented in AbstractSet and override
 * methods that can be done more efficiently. 
 * An ArrayList must be used as the internal storage container.
 *
 */
public class UnsortedSet<E> extends AbstractSet<E> {

    private ArrayList<E> myUnSortedSet; 
    private int size;
 
    public UnsortedSet() {
    	myUnSortedSet = new ArrayList<>();
    }
    
    protected ArrayList<E> internalStorage() {
    	return myUnSortedSet;
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
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				E val = myUnSortedSet.get(current);
				erase = current;
				current++;
				removeOK = true;
				return val;
			}
			
			@Override
			public void remove() {
				if(!removeOK) {
					throw new IllegalStateException();
				}
				myUnSortedSet.remove(erase);
				current--;
				size--;
				removeOK = false;
			}		
		};
		return itr;
	}
	
	@Override
	public boolean add(E item) {
		if(item == null) {
			throw new IllegalArgumentException("Invalid item.");
		}
		int index = 0;
		while(index < size) {
			if(myUnSortedSet.get(index).equals(item)) {
				return false; // this item cannot be added!
			}
			index++;
		}
		myUnSortedSet.add(item);
		size++;
		return true;
	}

	@Override
	public boolean remove(E item) {
		if(item == null) {
			throw new IllegalArgumentException("Item is null");
		}
		boolean removed = myUnSortedSet.remove(item);
		size -= removed ? 1 : 0;
		return removed;
	}
	
	@Override
	public int size() {
		return size;
	}
	
	@Override
	public Object clone() {
		Object result = new UnsortedSet<>();
		return result;
	}

	@Override
	public ISet<E> intersection(ISet<E> otherSet) {
    	if(otherSet == null) {
    		throw new IllegalArgumentException("other set is invalid");
    	}
    	ISet<E> result = new UnsortedSet<>();
    	for(E e : this) {
    		if(otherSet.contains(e)) {
    			result.add(e);
    		}
    	}
		return result;
	}

	@Override
	public ISet<E> union(ISet<E> otherSet) {
    	if(otherSet == null) {
    		throw new IllegalArgumentException("other set is invalid");
    	}
    	ISet<E> result = new UnsortedSet<>();
    	for(E e : this) {
    		result.add(e);
    		for(E v : otherSet) {
    			result.add(v);
    		}
    	}
		return result;
	}
	
	
	
	

}






















