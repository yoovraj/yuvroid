package com.my.siggen;

public abstract class Constants {
	public static final int DEFAULT_LOWER_FREQ = 1000;
	public static final int DEFAULT_HIGHER_FREQ = 5000;

	public abstract class StartStopToggleButton {
		public static final boolean START = false;
		public static final boolean STOP  = true;
		
		public static final String  START_STRING  = "START";
		public static final String  STOP_STRING   = "STOP ";
		public static final String  INVALID_STRING= "INVALID";
	}
	
	public abstract class VARIABLES_ {
		public static final String CURRENT_FREQ = "CURRENT.FREQ";
		public static final String WAVE_TYPE    = "WAVE.TYPE";
		public static final int SINE_0          = 0;
		public static final int SQUARE_1        = 1;
		public static final String PLAYSTATUS   = "PLAYSTATUS";
	}
	
	/**
	 * Characteristic of wave
	 * @author yoovraj.shinde
	 *
	 */
	public abstract class WAVE {
	    public static final String DURATION     = "DURATION";
        public static final String SAMPLE_RATE  = "SAMPLE_RATE";
        public static final String NUM_SAMPLES  = "NUM_SAMPLES";
        public static final String FREQ_OF_TONE = "FREQ_OF_TONE";
        public static final String SAMPLE       = "SAMPLE";
	}
}
