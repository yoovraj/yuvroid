package com.my.siggen.logic;

import com.my.siggen.Constants;
import com.my.siggen.GlobalVar;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SquareWaveLogic implements AudioWaveLogic{
    private static final int duration = 1; //seconds
    private static final int sampleRate = 8000;
    private static final int numSamples = sampleRate*duration;
    private static final double sample[] = new double[numSamples];
    public static double freqOfTone = 1000; // Hz
    
    private final byte generatedSnd[] = new byte[2*numSamples];

    public static AudioTrack audioTrack = null; 
    static {
    	audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STREAM);
        audioTrack.play();
      }
    public void play(double freqOfTone) {
    	SquareWaveLogic.freqOfTone = freqOfTone;
    }
    
	@Override
    public void play() {
    	Object freqOfToneValue = GlobalVar.getInstance().retrieve(Constants.VARIABLES_.CURRENT_FREQ);
    	if (freqOfToneValue != null ) {
            SquareWaveLogic.freqOfTone = ((Integer) freqOfToneValue).doubleValue();
    	}
        long initTime = System.currentTimeMillis();
        System.out.println("starting creation SQUARE ");
        for (int i = 0 ; i < numSamples; i++) {
            
            if ( ((int) Math.floor(i*freqOfTone/sampleRate))  % 2 == 1) {
                sample[i] = 1;
            } else {
                sample[i] = 0;
            }
        }
        System.out.println("pcm conversion started");
        int idx = 0;
        for (double dVal : sample) {
            short val = (short) (dVal*32767);
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
        System.out.println("sound generation completed in " + (System.currentTimeMillis() -  initTime ) + " ms");

        System.out.println("clicked and now will play");
        
        audioTrack.write(generatedSnd, 0, numSamples);
//        audioTrack.play();
        System.out.println("played");

    }
    
    public void releaseAudioTrack() {
    	audioTrack.release();
    }
}
