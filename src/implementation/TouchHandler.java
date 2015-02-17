package implementation;

import java.util.List;

import android.view.View.OnTouchListener;

import framework.Input.TouchEvent;
/**
 * TouchHandler.java
 *
 * An interface showing the methods needed for a class that implements
 * TouchHandler.
 *
 * @author Unknown
 */
public interface TouchHandler extends OnTouchListener {
    public boolean isTouchDown(int pointer);

	/**
	 * A method to get x coordinate of touch
	 *
	 * @param pointer	int position of touch
	 * @return			x coordinate of touch
	 */
    public int getTouchX(int pointer);

	/**
	 * A method to get y coordinate of touch
	 *
	 * @param pointer	int position of touch
	 * @return			y coordinate of touch
	 */
    public int getTouchY(int pointer);

	/**
	 * A method to get touch events from List array of type TouchEvent.
	 *
	 * @return	touch events from List<TouchEvent> array
	 */
    public List<TouchEvent> getTouchEvents();
}
