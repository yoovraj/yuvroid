package com.my.siggen.logic;

import com.my.siggen.Constants;
import com.my.siggen.GlobalVar;

import android.app.IntentService;
import android.content.Intent;

public class AudioService extends IntentService {

	public static boolean AUDIOSERVICE_STOP = false;
	public AudioService() {
		super("AudioService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Integer waveType = null ;
		AudioWaveLogic audioWaveLogic = null;
		while(!AUDIOSERVICE_STOP) {
			waveType = (Integer) GlobalVar.getInstance().retrieve(Constants.VARIABLES_.WAVE_TYPE);
			if (waveType == null) {
				waveType = Constants.VARIABLES_.SINE_0;
			}
			switch (waveType.intValue()) {
			case Constants.VARIABLES_.SINE_0:
				audioWaveLogic = new SineWaveLogic();
				break;

			case Constants.VARIABLES_.SQUARE_1:
				audioWaveLogic = new SquareWaveLogic();
				break;
			default:
				break;
			}
			audioWaveLogic.play();
		}
// TODO fix error -> uninitialized audiotrack while closing service -> releasing
//		audioWaveLogic.releaseAudioTrack();
	}

	  
	  
}
