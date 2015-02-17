package implementation;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

import framework.Input;

/**
 * AndroidInput.java
 *
 * A class to construct an object of AndroidInput and methods to manipulate an
 * AndroidInput object. The AndroidInput class is used to recognise input from
 * the device the application is running on.
 *
 * @author Unknown
 */
public class AndroidInput implements Input {    
    TouchHandler touchHandler;

	/**
	 * A constructor to create an AndroidInput object. A SingleTouchHandler
	 * object is created if the value of VERSION.SDK is less than five,
	 * otherwise, a MultiTouchHandler object is created.
	 *
	 * @param context	a variable of type Context
	 * @param view		a variable of type View used to create either a
	 *                  SingleTouchHandler object or a MultiTouchHandler object
	 * @param scaleX	a variable of type float used to create either a
	 *                  SingleTouchHandler or a MultiTouchHandler
	 * @param scaleY	a variable of type float used to create either a
	 *                  SingleTouchHandler object or a MultiTouchHandler object
	 */
    public AndroidInput(Context context, View view, float scaleX, float scaleY) {
        if(Integer.parseInt(VERSION.SDK) < 5) 
            touchHandler = new SingleTouchHandler(view, scaleX, scaleY);
        else
            touchHandler = new MultiTouchHandler(view, scaleX, scaleY);        
    }

	/**
	 * A method that returns a true/false value symbolising if the object is
	 * touchDown or not.
	 *
	 * @param pointer	pointer variable used to determine if object
	 * 					isTouchDown or not
	 * @return			boolean value determining whether object isTouchDown
	 * 					or not
	 */
    @Override
    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

	/**
	 * A method that returns int value of variable x.
	 *
	 * @param pointer	pointer variable used to retrieve x value
	 * @return			int value of x
	 */
    @Override
    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

	/**
	 * A method that returns int value of variable y.
	 *
	 * @param pointer	pointer variable used to retrieve y value
	 * @return			int value of y
	 */
    @Override
    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

	/**
	 * A method to return touch events from the touchHandler.
	 *
	 * @return	an array of touch events in type List<TouchEvent>
	 */
    @Override
    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
    
}
