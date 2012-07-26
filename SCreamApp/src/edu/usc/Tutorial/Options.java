package edu.usc.Tutorial;

import edu.usc.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Options extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);      
        
        Button BackButton = (Button)findViewById(R.id.button2);
        BackButton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent StartGameIntent = new Intent(Options.this,Start.class);
        		startActivity(StartGameIntent);
        	}
        });
              
    }
}