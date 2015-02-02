package framework;

import android.app.Activity;
import implementation.AndroidAudio;
import implementation.AndroidSound;

public class SoundResource {

	private Audio myAudio;
	private Sound mySound;
	
	
	public SoundResource (Activity act){
		myAudio = new AndroidAudio(act);
	}
	
	/**
	 * Loads a Sound object from myAudio and assigns it to class member mySound
	 * 
	 * @param resourcePath 	The file path of the Sound 
	 */
	public void load(String resourcePath){
		mySound = myAudio.createSound(resourcePath);
	}
	
	
	/**
	 * Plays the SoundResource with volume 0.9
	 */
	public void play(){
		mySound.play((float)0.9);
	}
	
	/**
	 * Stops this instance of SoundResource
	 */
	public void stop(){
		mySound.stop();
		
	}
	
}
