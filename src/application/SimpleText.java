package application;

import framework.AbstractFile;
import framework.FileImplementation;

/**
 * SimpleText.java
 *
 * Class used to demonstrate Strategy design patten.
 * Different strategies are used to output the text in different ways.
 */
public class SimpleText extends AbstractFile{
	String Heading = "";
	String Body = "";
	
	private FormatStrategy MyStrat = new StringStrategy();

	/**
	 * Constructor to create SimpleText object.
	 * Forwards a FileImplementation instance to parent class.
	 *
	 * @param Imp	FileImplementation of text
	 */
	public SimpleText(FileImplementation Imp){
		super(Imp);
	}

	/**
	 * Constructor to create SimpleText object.
	 * Uses an AbstractStorageFactory for its initialisation.
	 *
	 * @param Fact	AbstractStorageFactory used to format text
	 */
	public SimpleText(AbstractStorageFactory Fact){
		super(Fact.getFileImp());
		MyStrat = Fact.getStrategy();
	}

	/**
	 * Method to set strategy used to format text
	 *
	 * @param myStrat	FormatStrategy to format text
	 */
	public void setMyStrat(FormatStrategy myStrat) {
		MyStrat = myStrat;
	}

	/**
	 * Method to set heading of text
	 *
	 * @param heading	heading of text
	 */
	public void setHeading(String heading){
		Heading = heading;
	}

	/**
	 * Method to set body of text
	 *
	 * @param body	body of text
	 */
	public void setBody(String body){
		Body = body;
	}

	/**
	 * Method to output text using specified FormatStrategy
	 *
	 * @return	formatted text
	 */
	public String FormatOutput(){
		return MyStrat.Format(Heading, Body);
	}

	/**
	 * Method to write output text to file.
	 * Ensures SimpleText actually uses the Bridge to read and write files.
	 *
	 * @param FileName	file name to write output to
	 * @return			true if file written successfully false if not
	 */
	public boolean writeToFile(String FileName){
		return write(FileName, FormatOutput());
	}

	/**
	 * Method to read text from file
	 *
	 * @param FileName	file name to read from
	 * @return			true if file read successfully false if not
	 */
	public boolean readFromFile(String FileName){
		String FileContents = read(FileName);
		Heading = "";
		Body = FileContents;
		
		return !FileContents.contains("ERROR_");
	}
}
