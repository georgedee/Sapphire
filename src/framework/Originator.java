package framework;
/**
 * Has a state worth saving, using the memento pattern
 * 
 * Abstract so that applications can refine exactly what kind of 
 * state they would like to save
 * @author fqv12suu
 *
 */
public abstract class Originator {
	
	protected MementoState state;
	
	/**
	 * Sets the state of this Originator
	 * @param state
	 */
	public final void setState(MementoState state){
		this.state = state;
	}
	
	/**
	 * Returns a new instance of the current Originator stored
	 * @return
	 */
	public final Memento saveToMemento(){
		return new Memento(state);
	}
	
	/**
	 * Gets the state of the current Memento
	 * 
	 * @param m		The memento you wish to set the state to
	 */
	public final void restoreFromMemento(Memento m){
		state = m.getState();
	}
	
	
}
