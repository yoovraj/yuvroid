package com.my.siggen.logic;

import android.media.AudioFormat;
import android.media.AudioRecord;

import com.my.siggen.Constants;
import com.my.siggen.GlobalVar;
import com.my.siggen.logic.math.SineLookUpTable;

public class SineWaveLogic implements AudioWaveLogic{
	
//	public static SineWaveLogic instance = new SineWaveLogic();
//	private SineWaveLogic() {
//	}
//	public static SineWaveLogic getInstance() {
//		return instance;
//	}
    private static final int duration = AudioThreadIntentService.duration; //seconds
    private static final int sampleRate = AudioThreadIntentService.sampleRate;
    private static final int numSamples = sampleRate*duration;
    private double sample[] = new double[numSamples];
    public static double freqOfTone = AudioThreadIntentService.freqOfTone; // Hz
    
    private byte generatedSnd[] = new byte[2*numSamples];

    public void play(double freqOfTone) {
    	SineWaveLogic.freqOfTone = freqOfTone;
    }
    
	@Override
    public void play() {
    	Object freqOfToneValue = GlobalVar.getInstance().retrieve(Constants.VARIABLES_.CURRENT_FREQ);
    	if (freqOfToneValue != null ) {
            SineWaveLogic.freqOfTone = ((Integer) freqOfToneValue).doubleValue();
    	}
        long initTime = System.currentTimeMillis();
        System.out.println("starting creation sine " + freqOfTone);
        double multiplyFactor = 2*Math.PI / (sampleRate/freqOfTone);
        for (int i = 0 ; i < numSamples; i++) {
            sample[i] = SineLookUpTable.sineOf(multiplyFactor*i );
        }
        AudioThreadIntentService.sample = sample;
        System.out.println("sample.len = " + sample.length);
        
        System.out.println("pcm conversion started");
        int idx = 0;
        for (double dVal : sample) {
            short val = (short) (dVal*32767);
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
        }
        AudioThreadIntentService.generatedSnd = generatedSnd;
        System.out.println("sound generation completed in " + (System.currentTimeMillis() -  initTime ) + " ms");

        System.out.println("clicked and now will play");
        int bufferSize = AudioRecord.getMinBufferSize(8000, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT);
        System.out.println(" buffer size = " + bufferSize);
//        int tmp = numSamples/bufferSize;
//        byte[] sendData = new byte[bufferSize];
//        for(int i = 0; i<tmp; i++) {
//        	for(int j=0;j<bufferSize; j++) {
//        		sendData[j] = generatedSnd[bufferSize*i+j];
//        	}
//        	System.out.println(" writeCode = " + audioTrack.write(sendData, 0, bufferSize));
//        }
//        System.out.println(" writeCode = " + audioTrack.write(generatedSnd, 0, numSamples));
//        audioTrack.play();
        System.out.println("played");
    }
    
    public void releaseAudioTrack() {
//    	audioTrack.release();
    }
}
