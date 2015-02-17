package framework;
/**
 * Wrap the actual state of the program into a managable form
 * @author fqv12suu
 *
 */
public class Memento {
	private MementoState state;
	
	public Memento(MementoState newState){
		state = newState;
	}
	
	public MementoState getState(){
		return state;
	}
}
