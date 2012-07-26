package edu.usc.apartment;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import edu.usc.R;

public class RentPopup extends Dialog implements OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {
	
	private Button cancel;
	private SeekBar minSeekBar;
	private TextView minText;
	private SeekBar maxSeekBar;
	private TextView maxText;
	
	
	public RentPopup(Context context) {
		super(context);
		this.setContentView(R.layout.rentpopup);
		setContentView(R.layout.rentpopup);
		setTitle("Rent Filter");
		
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		
		minSeekBar = (SeekBar) findViewById(R.id.min_bar);
		minSeekBar.setOnSeekBarChangeListener(this);
		
		minText = (TextView) findViewById(R.id.min_text);
		minText.addTextChangedListener(this);
		
		maxSeekBar = (SeekBar) findViewById(R.id.max_bar);
		maxSeekBar.setOnSeekBarChangeListener(this);
		
		maxText = (TextView) findViewById(R.id.max_text);
		maxText.addTextChangedListener(this);
		
		show();
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()) {
			case R.id.cancel:
				dismiss();
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser) {
			switch(seekBar.getId()) {
				case R.id.min_bar:
					minText.setText(Integer.toString(progress));
					break;
				case R.id.max_bar:
					maxText.setText(Integer.toString(progress));
					break;
			}
			
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		if (!minText.getText().toString().equals("")) {
			if (Integer.parseInt(minText.getText().toString()) > 255)
				minText.setText("255");
			minSeekBar.setProgress(Integer.parseInt(minText.getText().toString()));
		} else
			minSeekBar.setProgress(0);
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	
}
