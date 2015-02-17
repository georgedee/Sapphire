package implementation;

import android.media.SoundPool;

import framework.Sound;
/**
 * AndroidSound.java
 *
 * A class to construct an object of AndroidSound and methods to manipulate an
 * AndroidSound object. The AndroidSound class is used to play, dispose and
 * stop AndroidSound objects.
 *
 * @author Unknown
 */
public class AndroidSound implements Sound {
    int soundId;
    SoundPool soundPool;

	/**
	 * A constructor to construct an AndroidSound object.
	 *
	 * @param soundPool	SoundPool varaible used to create AndroidSound object
	 * @param soundId	int varaible contain sound ID used to create
	 *                  AndroidSound object
	 */
    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

	/**
	 * A method to play AndroidSound object
	 *
	 * @param volume	float variable specifying the volume at which the sound
	 *                  should be played
	 */
    @Override
    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

	/**
	 * A method to unload a sound from a soundID.
	 */
    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

	/**
	 * A method to stop the AndroidSound object being played
	 */
    @Override
    public void stop(){
    	soundPool.stop(soundId);
    }

}
