package application;

import android.app.Activity;
import framework.FileImplementation;
import framework.InternalFileImp;

/**
 * StringInternalFactory.java
 *
 * This class is used to retrieve a strategy and file implementation the client
 * class will always get a StringStrategy combined with an InternalFileImp.
 */
public class StringInternalFactory extends AbstractStorageFactory {

	/**
	 * Constructor to create SuperInternalFactory object
	 *
	 * @param TheAct	Activity used to create SuperInternalFactory object
	 */
	public StringInternalFactory(Activity TheAct){
		super(TheAct);
	}

	/**
	 * Method to return FormatStrategy of object
	 *
	 * @return	FormatStrategy of object
	 */
	@Override
	public FormatStrategy getStrategy(){
		return new StringStrategy();
	}

	/**
	 * Method to return FileImplementation of object
	 *
	 * @return	FileImplementation of object
	 */
	@Override
	public FileImplementation getFileImp(){
		return new InternalFileImp(Act);
	}
}
