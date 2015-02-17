package implementation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import framework.FileIO;
/**
 * AndroidFileIO.java
 *
 * A class to construct an object of AndroidFileIO and methods to manipulate an
 * AndroidFileIO object. The AndroidFileIO class is used to create I/O streams
 * to assets.
 *
 * @author Unknown
 */
public class AndroidFileIO implements FileIO {
    Context context;
    AssetManager assets;
    String externalStoragePath;

	/**
	 * Constructor to create new AndroidFileIO object.
	 *
	 * @param context	Context to extract assets from
	 */
    public AndroidFileIO(Context context) {
        this.context = context;
        this.assets = context.getAssets();
        this.externalStoragePath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + File.separator;
        
 
    
    }

	/**
	 * Method used to read asset from a given file.
	 *
	 * @param file			File to read asset from
	 * @return				InputStream to file
	 * @throws IOException	Thrown if cannot read asset from file
	 */
    @Override
    public InputStream readAsset(String file) throws IOException {
        return assets.open(file);
    }

	/**
	 * Method used to read file
	 *
	 * @param file			File to read from
	 * @return				A new FileInputStream containing external storage
	 * 						path and file
	 * @throws IOException	Thrown if cannot read file
	 */
    @Override
    public InputStream readFile(String file) throws IOException {
        return new FileInputStream(externalStoragePath + file);
    }

	/**
	 * Method used to write to file
	 *
	 * @param file			File to write to
	 * @return				A new FileOutputStream containing external storage
	 * 						path and file
	 * @throws IOException	Thrown if cannot write to file
	 */
    @Override
    public OutputStream writeFile(String file) throws IOException {
        return new FileOutputStream(externalStoragePath + file);
    }

	/**
	 * Method used to access shared preferences
	 *
	 * @return	SharedPreferences object containing the shared preferences of
	 * 			the object it's been called on
	 */
    public SharedPreferences getSharedPref() {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
