package implementation;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import framework.Audio;
import framework.Music;
import framework.Sound;

/**
 * AndroidAudio.java
 *
 * A class to construct an object of AndroidAudio and methods to manipulate an
 * AndroidAudio object. The AndroidAudio class creates AndroidMusic and
 * AndroidAudio.
 *
 * @author Unknown
 */
public class AndroidAudio implements Audio {
    AssetManager assets;
    SoundPool soundPool;
    
    /**
     * Constructor to create AndroidAudio instance
     * 
     * @param activity	Activity to extract assets from
     */
    public AndroidAudio(Activity activity) {
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.assets = activity.getAssets();
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    /**
     * Method used to return AndroidMusic Singleton instance
     * 
     * @return	AndroidMusic Singleton instance
     */
    public Music createMusic(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            return AndroidMusic.getInstance(assetDescriptor);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load music '" + filename + "'");
        }
    }

    @Override
    /**
     * Method used to return AndroidSound instance
     * 
     * @return	AndroidSound instance
     */
    public Sound createSound(String filename) {
        try {
            AssetFileDescriptor assetDescriptor = assets.openFd(filename);
            int soundId = soundPool.load(assetDescriptor, 0);
            return new AndroidSound(soundPool, soundId);
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load sound '" + filename + "'");
        }
    }
}
