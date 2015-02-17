package framework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Environment;

/**
 * ExternalFileImp.java
 *
 * External implementation of reading and writing a string from and to a file.
 */
public class ExternalFileImp extends FileImplementation{
	
	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;

	/**
	 * Constructor used to create ExternalFileImp object
	 *
	 * @param TheAct	Activity used to create ExternalFileImp object
	 */
	public ExternalFileImp(Activity TheAct){
		super(TheAct);
	}

	/**
	 * Method to read text from file
	 *
	 * @param FileName	name of file to read text from
	 * @return			text in file
	 */
	@Override
	public String read(String FileName){
		UpdateExternalStorageState();
		
		if(mExternalStorageAvailable){
			File file = new File(Act.getExternalFilesDir(null),FileName);
				//getExternalFilesDir(null) returns the root of the external storage
			FileInputStream fis;
			
			try{
				fis = new FileInputStream(file);
			} catch(IOException e){
				e.printStackTrace();
				return "ERROR_FILE_DOES_NOT_EXIST_ON_EXT";
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
			} catch(IOException e){
				e.printStackTrace();
				return "ERROR_WHILE_READING_FILE";
			}
		}
		else {
			return "ERROR_EXTERNAL_STORAGE_NOT_AVAILABLE";
		}
	}

	/**
	 * Method to write text to file
	 *
	 * @param FileName	name of file to write text to
	 * @param Contents	text to write to file
	 * @return			true if successful false otherwise
	 */
	@Override
	public boolean write(String FileName, String Contents){
		UpdateExternalStorageState();
		
		if(mExternalStorageWriteable){
			File file = new File(Act.getExternalFilesDir(null), FileName);
				//getExternalFilesDir(null) returns the root of the external storage
			FileOutputStream fos;
			
			try{
				fos = new FileOutputStream(file);
			} catch(IOException e){
				e.printStackTrace();
				return false;
			}
			
			try{
				fos.write(Contents.getBytes());
				fos.close();
			}catch (IOException e){
				e.printStackTrace();
				return false;
			}
		}
		else {
			return false;
		}
		
		return true;
	}

	/**
	 * Mehtod to update external storage state
	 */
	private void UpdateExternalStorageState(){
		String state = Environment.getExternalStorageState();
		
		if(Environment.MEDIA_MOUNTED.equals(state)){
			//We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
			//We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			//Something is wrong, we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

}
