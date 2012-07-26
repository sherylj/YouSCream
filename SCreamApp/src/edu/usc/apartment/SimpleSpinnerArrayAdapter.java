package edu.usc.apartment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

public class SimpleSpinnerArrayAdapter extends ArrayAdapter<String> implements SpinnerAdapter {

	public SimpleSpinnerArrayAdapter(Context context) {
		super(context, android.R.layout.simple_spinner_item, new String[]{"Rent","Min Rent", "Max Rent"});
		this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
	
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return super.getDropDownView(position, convertView, parent);
	}

}
