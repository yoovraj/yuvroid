package com.my.siggen;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.my.siggen.async.AudioAsyncTask;

public class MainActivity extends Activity {

	public static boolean stopLoop = false;
	public static boolean startStopToggleStatus = Constants.StartStopToggleButton.STOP;
	public SeekBar seekBar = null;
	public TextView textViewLowFreq = null;
	public TextView textViewHighFreq = null;
	public TextView textViewCurrFreq = null;

	public EditText editTextLowerFreq = null;
	public EditText editTextHigherFreq = null;

	public Button buttonStartStopToggle = null;
//	Intent intentAudioService = null;
//	Intent intenAudioThreadIntentService = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialise
//		intentAudioService =  new Intent(this, AudioService.class);
//		intenAudioThreadIntentService = new Intent(this, AudioThreadIntentService.class);
		
		textViewLowFreq = (TextView) this.findViewById(R.id.textViewLowerFreq);
		textViewLowFreq.setText(String.valueOf(Constants.DEFAULT_LOWER_FREQ));
		
		textViewHighFreq = (TextView) this.findViewById(R.id.textViewHighFreq);
		textViewHighFreq.setText(String.valueOf(Constants.DEFAULT_HIGHER_FREQ));
		
		textViewCurrFreq = (TextView) this.findViewById(R.id.textViewCurrFreq);
		textViewCurrFreq.setText(textViewLowFreq.getText());
		
		editTextLowerFreq = (EditText) this.findViewById(R.id.editTextLowerFreq);
		editTextLowerFreq.setText(String.valueOf(Constants.DEFAULT_LOWER_FREQ));
		
		editTextHigherFreq = (EditText) this.findViewById(R.id.editTextHigherFreq);
		editTextHigherFreq.setText(String.valueOf(Constants.DEFAULT_HIGHER_FREQ));
		
		seekBar = (SeekBar) this.findViewById(R.id.seekBarFreq);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				Integer lowerFreq = Integer.valueOf(editTextLowerFreq.getText().toString());
				Integer higherFreq = Integer.valueOf(editTextHigherFreq.getText().toString());
				Integer currentFreq = lowerFreq + (higherFreq - lowerFreq)*arg1/100 ;
				
				textViewLowFreq.setText(lowerFreq.toString());
				textViewCurrFreq.setText(currentFreq.toString());
				textViewHighFreq.setText(higherFreq.toString());
				GlobalVar.getInstance().save(Constants.VARIABLES_.CURRENT_FREQ, currentFreq);
//				intentAudioService.putExtra("CURRENT.FREQ", currentFreq.intValue());
			}
		});
		
		buttonStartStopToggle = (Button) this.findViewById(R.id.buttonStartStopToggle);
		System.out.println("After Set");
    	Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();

		System.out.println("Started");
		   for (int rate : new int[] {8000, 11025, 16000, 22050, 44100}) {  // add the rates you wish to check against
		        int bufferSize = AudioRecord.getMinBufferSize(rate, AudioFormat.CHANNEL_CONFIGURATION_DEFAULT, AudioFormat.ENCODING_PCM_16BIT);
		        if (bufferSize > 0) {
		        	Toast.makeText(getApplicationContext(), String.valueOf(rate), Toast.LENGTH_LONG).show();
		        }
		    }
		// run while loop
//		SineWaveLogic sineWaveLogic = new SineWaveLogic();
//		while (!stopLoop) {
//			sineWaveLogic.play(1000);
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClickButtonStartStopToggle(View v) {
		System.out.println("Clicked Button " + startStopToggleStatus);
		startStopToggleStatus = !startStopToggleStatus;
		
		if (startStopToggleStatus == Constants.StartStopToggleButton.START) {
	        GlobalVar.getInstance().save(Constants.VARIABLES_.PLAYSTATUS, Boolean.TRUE);
            new AudioAsyncTask().execute();
			buttonStartStopToggle.setText(Constants.StartStopToggleButton.STOP_STRING);
		} else if (startStopToggleStatus == Constants.StartStopToggleButton.STOP) {
	        GlobalVar.getInstance().save(Constants.VARIABLES_.PLAYSTATUS, Boolean.FALSE);
			buttonStartStopToggle.setText(Constants.StartStopToggleButton.START_STRING);
		} else {
			buttonStartStopToggle.setText(Constants.StartStopToggleButton.INVALID_STRING);
		}
		
		stopLoop = startStopToggleStatus;
		System.out.println("changed " + startStopToggleStatus);
	}
	
	public void onClickRadioGroupWaveType(View v) {
	    // Is the button now checked?
	    boolean checked = ((RadioButton) v).isChecked();
	    
	    // Check which radio button was clicked
	    switch(v.getId()) {
	        case R.id.radioSineWave:
	            if (checked)
	        	    GlobalVar.getInstance().save(Constants.VARIABLES_.WAVE_TYPE, Constants.VARIABLES_.SINE_0);
	            break;
	        case R.id.radioSquareWave:
	            if (checked)
	        	    GlobalVar.getInstance().save(Constants.VARIABLES_.WAVE_TYPE, Constants.VARIABLES_.SQUARE_1);
	            break;
	    }
	}
}
