package application;

import framework.FileImplementation;
import android.app.Activity;

/**
 * AbstractStorageFactory.java
 * 
 * Contains the abstract methods for the SimpleText class to use to get a
 * formatting strategy and file implementation without having to know its
 * precise type.
 * 
 * @author George
 */
public abstract class AbstractStorageFactory {
	
	protected Activity Act;

	/**
	 * Constructor to create AbstractStorageFactory object
	 *
	 * @param TheAct	Activity used to create AbstractStorageObject
	 */
	public AbstractStorageFactory(Activity TheAct){
		Act = TheAct;
	}

	/**
	 * Method to return FormatStrategy
	 *
	 * @return	FormatStrategy of object
	 */
	public abstract FormatStrategy getStrategy();

	/**
	 * Method to return FileImplementation
	 *
	 * @return	FileImplementation of object
	 */
	public abstract FileImplementation getFileImp();
}
