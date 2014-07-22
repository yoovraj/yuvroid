package com.my.siggen.logic;

import com.my.siggen.Constants;
import com.my.siggen.GlobalVar;
import com.my.siggen.logic.math.SineLookUpTable;

public class SineWaveLogic implements AudioWaveLogic{
    private int duration = 1; //seconds
    private int sampleRate = 8000;
    private int numSamples = sampleRate*duration;
    private double sample[] = new double[numSamples];
    public double freqOfTone = 1000; // Hz
    
	@Override
    public void generateData() {
    	Object freqOfToneValue = GlobalVar.getInstance().retrieve(Constants.VARIABLES_.CURRENT_FREQ);
    	if (freqOfToneValue != null ) {
            freqOfTone = ((Integer) freqOfToneValue).doubleValue();
    	}
        long initTime = System.currentTimeMillis();
        System.out.println("starting creation sine " + freqOfTone);
        double multiplyFactor = 2*Math.PI / (sampleRate/freqOfTone);
        for (int i = 0 ; i < numSamples; i++) {
//            System.out.println(multiplyFactor*i + " = " + sample[i]);
            sample[i] = SineLookUpTable.sineOf(multiplyFactor*i );
        }
        System.out.println("sound generation completed in " + (System.currentTimeMillis() -  initTime ) + " ms");
        
        GlobalVar.getInstance().save(Constants.WAVE.DURATION, duration);
        GlobalVar.getInstance().save(Constants.WAVE.FREQ_OF_TONE, freqOfTone);
        GlobalVar.getInstance().save(Constants.WAVE.NUM_SAMPLES, numSamples);
        GlobalVar.getInstance().save(Constants.WAVE.SAMPLE, sample);
        GlobalVar.getInstance().save(Constants.WAVE.SAMPLE_RATE, sampleRate);
        
    }
}
