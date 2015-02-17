package framework;

/**
 * AbstractFile.java
 *
 * The parent class for any class that wants to use the Bridge pattern.
 * Provides interface that inheriting classes can use to store strings.
 * Methods forward to the Bridge class FileImplementation to make this
 * process abstract.
 */
public abstract class AbstractFile {

	private FileImplementation FileImp;

	/**
	 * Constructor to make instance of AbstractFile
	 *
	 * @param Imp	FileImplementation used to make instance
	 */
	protected AbstractFile(FileImplementation Imp){
		FileImp = Imp;
	}

	/**
	 * Method to read String from file
	 *
	 * @param FileName	file name of file string is being read from
	 * @return			text from file
	 */
	protected final String read(String FileName){
		return FileImp.read(FileName);
	}

	/**
	 * Method to write String to file
	 *
	 * @param FileName	file name of file string is being written to
	 * @param Contents	text to be written to file
	 * @return			true if write successful false otherwise
	 */
	protected final boolean write(String FileName, String Contents){
		return FileImp.write(FileName, Contents);
	}

	/**
	 * Method to set FileImplementaion
	 *
	 * @param NewImp	FileImplementation to set
	 */
	public void setImplementation(FileImplementation NewImp){
		FileImp = NewImp;
	}
}
