package com.my.siggen.logic;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioThreadIntentService extends IntentService{
    public AudioThreadIntentService() {
		super("AudioThreadIntentService");
		// TODO Auto-generated constructor stub
	}
	public static final int duration = 1; //seconds
	public static final int sampleRate = 8000;
	public static final int numSamples = sampleRate*duration;
	public static double sample[] = new double[numSamples];
	public static double freqOfTone = 1000; // Hz
    
	public static byte generatedSnd[] = new byte[2*numSamples];

    public static AudioTrack audioTrack = null; 
     static {
    	 audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
    	            AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STREAM);
         audioTrack.play();
    }
 
 	@Override
 	protected void onHandleIntent(Intent intent) {
        int idx = 0;
        while(true) {
        	for (double dVal : sample) {
                short val = (short) (dVal*32767);
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

            System.out.println(" writeCode = " + audioTrack.write(generatedSnd, 0, numSamples));
        }
 	}
}
