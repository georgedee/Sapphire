package implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import framework.Audio;
import framework.FileIO;
import framework.Game;
import framework.Graphics;
import framework.Input;
import framework.Screen;

/**
 * AndroidGame.java
 *
 * A class to construct an object of AndroidGame and methods to manipulate an
 * AndroidFileGame object. The AndroidFileIO class is used to create a game as
 * well as pause and resume the game.
 *
 * @author Unknown
 */
public abstract class AndroidGame extends Activity implements Game {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    Screen screen;
    WakeLock wakeLock;

	/**
	 * Method used to load the game from a previous saved state. When called,
	 * firstly the details regarding the previous state are extracted. Next,
	 * the app is run in full screen mode and it is determined if the device
	 * is currently portrait or landscape. The display width and height are
	 * then calculated and returned in variables scaleX and scaleY. New
	 * AndroidFastRenderView, AndroidGraphics, AndroidFileIO, AndroidAudio,
	 * AndroidInput are created using the buffer and display dimensions.
	 * PowerManager and wakeLock variables are defined. PowerManager gives the
	 * class control of the power state of the device. wakeLock is set to
	 * FULL_WAKE_LOCK which turns the CPU on, puts the screen on bright and
	 * puts the keyboard on bright.
	 *
	 * @param savedInstanceState	Saved instance of game state
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        int frameBufferWidth = isPortrait ? 480: 800;
        int frameBufferHeight = isPortrait ? 800: 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Config.RGB_565);
        
        float scaleX = (float) frameBufferWidth
                / getWindowManager().getDefaultDisplay().getWidth();
        float scaleY = (float) frameBufferHeight
                / getWindowManager().getDefaultDisplay().getHeight();

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(getAssets(), frameBuffer);
        fileIO = new AndroidFileIO(this);
        audio = new AndroidAudio(this);
        input = new AndroidInput(this, renderView, scaleX, scaleY);
        screen = getInitScreen();
        setContentView(renderView);

		// Gives class control of power state
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// FULL_WAKE_LOCK ensures CPU is on, screen is bright and keyboard is
		// bright
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "MyGame");
    }

	/**
	 * A method used to run the app when it is resumed by the user. Screen and
	 * keyboard remain lit through wakeLock, and screen and renderView become
	 * active again.
	 */
    @Override
    public void onResume() {
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

	/**
	 * A method used to pause the app. The app not longer light the screen and
	 * keyboard and the screen and renderView are paused.
	 */
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

	/**
	 * A method to return input
	 *
	 * @return	Input of object called on
	 */
    @Override
    public Input getInput() {
        return input;
    }

	/**
	 * A method to return file input/output stream
	 *
	 * @return	FileIO of object called on
	 */
    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

	/**
	 * A method to return graphics
	 *
	 * @return	Graphics of object called on
	 */
    @Override
    public Graphics getGraphics() {
        return graphics;
    }

	/**
	 * A method to return audio
	 *
	 * @return	Audio of object called on
	 */
    @Override
    public Audio getAudio() {
        return audio;
    }

	/**
	 * A method to set screen
	 *
	 * @param screen	Screen object to be set
	 */
    @Override
    public void setScreen(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

	/**
	 * A method to return current screen
	 *
	 * @return	Screen of object called on
	 */
    public Screen getCurrentScreen() {

        return screen;
    }
}
