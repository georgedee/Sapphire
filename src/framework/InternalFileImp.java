package framework;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;

/**
 * InternalFileImp.java
 *
 * Internal implementation of reading and writing a string from and to a file.
 * MODE_PRIVATE ensures only the application can read the file.
 */
public class InternalFileImp extends FileImplementation{

	/**
	 * Constructor to create InternalFileImp object
	 *
	 * @param TheAct	Activity used to create InternalFileImp object
	 */
	public InternalFileImp(Activity TheAct){
		super(TheAct);
	}

	/**
	 * Method to read text from a file from internal storage
	 *
	 * @param FileName	file name to read text from
	 * @return			text read from file
	 */
	@Override
	public String read(String FileName){
		FileInputStream fis;
		try{
			fis = Act.openFileInput(FileName);
		} catch (FileNotFoundException e){
			e.printStackTrace();
			return "ERROR_FILE_NOT_FOUND";
		}
		
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader bufferedReader = new BufferedReader(isr);
		StringBuilder sb = new StringBuilder();
		String line;
		try{
			while((line = bufferedReader.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e){
			e.printStackTrace();
			return "ERROR_WHILE_READING_FILE";
		}
	}

	/**
	 * Method to write text to file in internal storage
	 *
	 * @param FileName	file name to write text to
	 * @param Contents	text to write to file
	 * @return			true if write successful false otherwise
	 */
	@Override
	public boolean write(String FileName, String Contents){
		FileOutputStream fos;
		try{
			fos = Act.openFileOutput(FileName, Context.MODE_PRIVATE);
		} catch(FileNotFoundException e){
			e.printStackTrace();
			return false;
		}
		
		try{
			fos.write(Contents.getBytes());
			fos.close();
			return true;
		} catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}

}
