package framework;

import java.util.ArrayList;

/**
 * A container class for Memento class instances
 * (like an undo stack)
 * 
 * @author fqv12suu
 *
 */
public class CareTaker {
	
	private ArrayList<Memento> savedStates = new ArrayList<Memento>();
	
	/**
	 * Add a Memento to the CareTaker container
	 * 
	 * @param 		Memento m: Memento to add
	 */
	public void addMemento(Memento m){
		savedStates.add(m);
	}
	
	/**
	 * Returns the Memento at the given index in the CareTaker container
	 * 
	 * @param 		int index: the interger index of the Memento to return 
	 * @return		The Momento in the given index.
	 */
	public Memento getMemento(int index){
		return savedStates.get(index);
	}
}
