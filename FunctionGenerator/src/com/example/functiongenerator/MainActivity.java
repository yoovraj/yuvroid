package com.example.functiongenerator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void exitApplication(View v ) {
        System.out.println("exiting");
        System.exit(0);
    }
    
    public void startSineWaveGeneration(View v) {
        System.out.println("Starting sinewave generation");
        Intent intent = new Intent(this, SineWaveActivity.class);
        EditText sineWaveFrequency = (EditText) findViewById(R.id.editTextFrequency);
        String message = sineWaveFrequency.getText().toString();
        if (message == null || message.length() == 0) {
            message = "1000";
        }
        intent.putExtra("SINEWAVE.FREQ", message);
        startActivity(intent);
    }
    
    public void startSquareWaveGeneration(View v) {
        System.out.println("Starting squarewave generation");
        Intent intent = new Intent(this, SquareWaveActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextFrequency);
        String message = editText.getText().toString();
        if (message == null || message.length() == 0) {
            message = "1000";
        }
        intent.putExtra("SQUAREWAVE.FREQ", message);
        startActivity(intent);
    }
}
