package implementation;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;

import framework.Music;

/**
 * AndroidMusic.java
 *
 * A class to construct an object of AndroidMusic and methods to manipulate an
 * AndroidMusic object. The AndroidMusic class returns information about the
 * state of music such as if it's playing, looping or has stopped.
 *
 * @author Unknown
 */
public class AndroidMusic implements Music, OnCompletionListener, OnSeekCompleteListener, OnPreparedListener, OnVideoSizeChangedListener {
    MediaPlayer mediaPlayer;
    boolean isPrepared = false;
    private static AndroidMusic instance;
    
    /**
     * Constructor which creates AndroidMusic instance
     * 
     * @param assetDescriptor	The audio data
     */
    public AndroidMusic(AssetFileDescriptor assetDescriptor) {
    	load(assetDescriptor);
    }
    
    /**
     * A private method which constructs a new AndroidMusic instance from the 
     * audio data.
     * 
     * @param assetDescriptor	The audio data
     */
    private void load(AssetFileDescriptor assetDescriptor){
    	mediaPlayer = new MediaPlayer();
    	try{
    		mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(),
    				assetDescriptor.getStartOffset(),
    				assetDescriptor.getLength());
    		mediaPlayer.prepare();
    		isPrepared = true;
    		mediaPlayer.setOnCompletionListener(this);
    		mediaPlayer.setOnSeekCompleteListener(this);
    		mediaPlayer.setOnPreparedListener(this);
    		mediaPlayer.setOnVideoSizeChangedListener(this);
    	} catch (Exception e){
    		throw new RuntimeException("Couldn't load music");
    	}
    }
    
    /**
     * Method used by other classes to retrieve the Singleton instance of 
     * AndroidMusic.
     * 
     * @param assetDescriptor	The audio data	
     * @return					Single instance of AndroidMusic
     */
    public static AndroidMusic getInstance(AssetFileDescriptor assetDescriptor){
    	if(instance == null){
    		instance = new AndroidMusic(assetDescriptor);
    	} else {
    		instance.stop();
    		instance.load(assetDescriptor);
    	}
    	return instance;
    }

    /**
     * Method to stop media player.
     */
    @Override
    public void dispose() {
    
         if (this.mediaPlayer.isPlaying()){
               this.mediaPlayer.stop();
                }
        this.mediaPlayer.release();
    }

    /**
     * Method to see if media player is looping.
     * 
     * @return	Boolean result to say if media player is looping or not
     */
    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }
    
    /**
     * Method to see if media player is playing.
     * 
     * @return	Boolean result to say if media player is playing or not
     */
    @Override
    public boolean isPlaying() {
        return this.mediaPlayer.isPlaying();
    }
    
    /**
     * Method to see if media player has stopped.
     * 
     * @return	Boolean result to say if media player has stopped or not
     */
    @Override
    public boolean isStopped() {
        return !isPrepared;
    }
    
    /**
     * Method to pause media player.
     */
    @Override
    public void pause() {
        if (this.mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    /**
     * Method to play media player.
     */
    @Override
    public void play() {
        if (this.mediaPlayer.isPlaying())
            return;

        try {
            synchronized (this) {
                if (!isPrepared)
                    mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Method to set media player to loop.
     * 
     * @param isLooping	value stating if media player is on loop or not
     */
    @Override
    public void setLooping(boolean isLooping) {
        mediaPlayer.setLooping(isLooping);
    }
    
    /**
     * Method to set media player volume.
     * 
     * @param volume value corresponding the volume level
     */
    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }
    
    /**
     * Method to stop media player from playing.
     */
    @Override
    public void stop() {
         if (this.mediaPlayer.isPlaying() == true){
        this.mediaPlayer.stop();
        
       synchronized (this) {
           isPrepared = false;
        }}
    }
    
    /**
     * Method to change isPrepared value.
     * 
     * @param player	Media player instance
     */
    @Override
    public void onCompletion(MediaPlayer player) {
        synchronized (this) {
            isPrepared = false;
        }
    }
    
    /**
     * Method to play media player from the beginning.
     */
    @Override
    public void seekBegin() {
        mediaPlayer.seekTo(0);
        
    }

	/**
	 * A method to change value of isPrepared to true.
	 *
	 * @param player	player object to change isPrepared value of
	 */
    @Override
    public void onPrepared(MediaPlayer player) {
        // TODO Auto-generated method stub
         synchronized (this) {
               isPrepared = true;
            }
        
    }

	/**
	 * A method to complete player when seeked.
	 *
	 * @param player	player object to complete seek of
	 */
    @Override
    public void onSeekComplete(MediaPlayer player) {
        // TODO Auto-generated method stub
        
    }

	/**
	 * A method to change video size
	 *
	 * @param player	player object to change size of
	 * @param width		width of video size
	 * @param height	height of video size
	 */
    @Override
    public void onVideoSizeChanged(MediaPlayer player, int width, int height) {
        // TODO Auto-generated method stub
        
    }
}
