package implementation;

import java.util.ArrayList;
import java.util.List;

import android.view.MotionEvent;
import android.view.View;

import framework.Pool;
import framework.Input.TouchEvent;
import framework.Pool.PoolObjectFactory;

/**
 * MultiTouchHandler.java
 *
 * A class to construct an object of MutliTouchHandler and methods to
 * manipulate a MultiTouchHandler object. The MultiTouchHandler class is used
 * to manage multiple touches on the device.
 *
 * @author Unknown
 */
public class MultiTouchHandler implements TouchHandler {
    private static final int MAX_TOUCHPOINTS = 10;
    
    boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    int[] touchX = new int[MAX_TOUCHPOINTS];
    int[] touchY = new int[MAX_TOUCHPOINTS];
    int[] id = new int[MAX_TOUCHPOINTS];
    Pool<TouchEvent> touchEventPool;
    List<TouchEvent> touchEvents = new ArrayList<TouchEvent>();
    List<TouchEvent> touchEventsBuffer = new ArrayList<TouchEvent>();
    float scaleX;
    float scaleY;

	/**
	 * A constructor to create a MultiTouchHandler object.
	 *
	 * @param view		View variable of object
	 * @param scaleX	x coordinate of object
	 * @param scaleY	y coordinate of object
	 */
    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        PoolObjectFactory<TouchEvent> factory = new PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };
        touchEventPool = new Pool<TouchEvent>(factory, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

	/**
	 * A method to perform a series of actions if the device screen is touched
	 * in a certain way.
	 *
	 * @param v			the View of the application
	 * @param event		the MotionEvent to take place
	 * @return			returns false if an event is added to
	 * 					touchEventsBuffer, otherwise, returns true
	 */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if (i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }
                int pointerId = event.getPointerId(i);
                if (event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    // if it's an up/down/cancel/out event, mask the id to see if we should process it for this touch
                    // point
                    continue;
                }
                switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = true;
                    id[i] = pointerId;
                    touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = false;
                    id[i] = -1;
                    touchEventsBuffer.add(touchEvent);
                    break;

                case MotionEvent.ACTION_MOVE:
                    touchEvent = touchEventPool.newObject();
                    touchEvent.type = TouchEvent.TOUCH_DRAGGED;
                    touchEvent.pointer = pointerId;
                    touchEvent.x = touchX[i] = (int) (event.getX(i) * scaleX);
                    touchEvent.y = touchY[i] = (int) (event.getY(i) * scaleY);
                    isTouched[i] = true;
                    id[i] = pointerId;
                    touchEventsBuffer.add(touchEvent);
                    break;
                }
            }
            return true;
        }
    }

	/**
	 * A method to determine if touch is down or not.
	 *
	 * @param pointer	int position of touch
	 * @return			false if index of pointer is between 0 and
	 * 					MAX_TOUCHPOINTS otherwise return the value of isTouched
	 * 					array at index value.
	 */
    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return false;
            else
                return isTouched[index];
        }
    }

	/**
	 * A method to get x coordinate of touch
	 *
	 * @param pointer	int position of touch
	 * @return			0 if index of pointer is between 0 and MAX_TOUCHPOINTS,
	 * 					otherwise return value of touchX array at index value.
	 */
    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchX[index];
        }
    }

	/**
	 * A method to get y coordinate of touch
	 *
	 * @param pointer	int position of touch
	 * @return			0 if index of pointer is between 0 and MAX_TOUCHPOINTS,
	 * 					otherwise return value of touchY array at index value.
	 */
    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if (index < 0 || index >= MAX_TOUCHPOINTS)
                return 0;
            else
                return touchY[index];
        }
    }

	/**
	 * A method to get touch events from List array of type TouchEvent.
	 *
	 * @return	touch events from List<TouchEvent> array
	 */
    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for (int i = 0; i < len; i++)
                touchEventPool.free(touchEvents.get(i));
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEvents;
        }
    }

	/**
	 * A method to return the index of a given pointerId or -1 is no index.
	 *
	 * @param pointerId		int value corresponding to pointerId
	 * @return				index of given pointerID or -1 if there is no index
	 */
    private int getIndex(int pointerId) {
        for (int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if (id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
