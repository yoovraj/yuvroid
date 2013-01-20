package com.example.functiongenerator;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Menu;
//http://marblemice.blogspot.jp/2010/04/generate-and-play-tone-in-android.html

public class SineWaveActivity extends Activity {
    private final int duration = 1; //seconds
    private final int sampleRate = 8000;
    private final int numSamples = sampleRate*duration;
    private final double sample[] = new double[numSamples];
    private double freqOfTone = 1000; // Hz
    
    private final byte generatedSnd[] = new byte[2*numSamples];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sine_wave);
        Intent intent = getIntent();
        freqOfTone = Double.parseDouble(intent.getStringExtra("SINEWAVE.FREQ"));
        
      long initTime = System.currentTimeMillis();
      System.out.println("starting creation sine ");
      for (int i = 0 ; i < numSamples; i++) {
          sample[i] = Math.sin(2*Math.PI*i / (sampleRate/freqOfTone));
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
      AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 8000, AudioFormat.CHANNEL_CONFIGURATION_MONO,
              AudioFormat.ENCODING_PCM_16BIT, numSamples, AudioTrack.MODE_STATIC);
      audioTrack.write(generatedSnd, 0, numSamples);
      audioTrack.play();
      System.out.println("played");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_sine_wave, menu);
        return true;
    }

}
