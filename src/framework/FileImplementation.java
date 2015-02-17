package framework;

import android.app.Activity;

/**
 * FileImplementation.java
 *
 * Provides the abstration for storing files on either internal or external
 * storage.
 */
public abstract class FileImplementation {
	
	protected Activity Act;

	/**
	 * Constructor to make instance of FileImplementation
	 *
	 * @param TheAct	Acticity used to create implementation
	 */
	protected FileImplementation(Activity TheAct){
		Act = TheAct;
	}

	/**
	 * Method to read text from file
	 *
	 * @param FileName	file name of file to read text from
	 * @return			text from file
	 */
	public abstract String read(String FileName);

	/**
	 * Method to write text to file
	 *
	 * @param FileName	file name of file to write text to
	 * @param Contents	text to write
	 * @return			true if write successful false otherwise
	 */
	public abstract boolean write(String FileName, String Contents);
}
