package application;

import android.app.Activity;
import framework.ExternalFileImp;
import framework.FileImplementation;

/**
 * HTMLExternalFactory.java
 *
 * The class will provide the combination of an HTMLStrategy and
 * ExternalFileImp to the client class.
 */
public class HTMLExternalFactory extends AbstractStorageFactory{

	/**
	 * Constructor to create HTMLExternalFactory object
	 *
	 * @param TheAct	Activity of HTMLExternalFactory
	 */
	public HTMLExternalFactory(Activity TheAct){
		super(TheAct);
	}

	/**
	 * Method to get FormatStrategy from object
	 *
	 * @return	FormatStrategy of object
	 */
	@Override
	public FormatStrategy getStrategy(){
		return new HTMLStrategy();
	}

	/**
	 * Method to get FileImplementation of object
	 *
	 * @return	FileImplmentation of object
	 */
	@Override
	public FileImplementation getFileImp(){
		return new ExternalFileImp(Act);
	}

}
