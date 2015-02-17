package implementation;

import android.graphics.Bitmap;

import framework.Image;
import framework.Graphics.ImageFormat;
/**
 * AndroidImage.java
 *
 * A class to construct an object of AndroidImage and methods to manipulate an
 * AndroidImage object.
 *
 * @author Unknown
 */
public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;

	/**
	 * A method to construct an AndroidImage object.
	 *
	 * @param bitmap	the Bitmap variable to be used in construction
	 * @param format	the ImageFormar variable to be used in construction
	 */
    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

	/**
	 * A method to return the width of an AndroidImage object.
	 *
	 * @return	int representation of width of AndroidImage object
	 */
    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

	/**
	 * A method to return the height of an AndroidImage object.
	 *
	 * @return	int representation of height of AndroidImage object
	 */
    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

	/**
	 * A method to return the format of an AndroidImage object.
	 *
	 * @return	ImageFormat of AndroidImage object
	 */
    @Override
    public ImageFormat getFormat() {
        return format;
    }

	/**
	 * A method to clear reference to pixel data and free object associated
	 * with bitmap.
	 */
    @Override
    public void dispose() {
        bitmap.recycle();
    }      
}
