package edu.usc.searchFilter;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import edu.usc.R;

public class SearchFilterActivity extends Activity implements
		Button.OnClickListener {
	/** Called when the activity is first created. */
	boolean open = false;
	public Context mContext;
	
	public String[] addressLines;
	public String getAddress = "";
	public List<Address> addresses;
	public EditText location;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_filter_main);
		// LinearLayout layout = (LinearLayout) findViewById (R.id.panel);
		final Button handle = (Button) this.findViewById(R.id.handleout);
		handle.setOnClickListener(this);
		mContext = this;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		final Button handle = (Button)findViewById(R.id.handleout);
		final Button handlein = (Button) findViewById(R.id.handlein);
		final RelativeLayout panel = (RelativeLayout) findViewById(R.id.panel);
		/*
		 * TranslateAnimation animPanel = new TranslateAnimation (-1, 0, 0, 0);
		 * TranslateAnimation animHandle = new TranslateAnimation (-1, 0, 0, 0);
		 * 
		 * animPanel.setFillAfter(true); animPanel.setDuration(1000);
		 * animHandle.setFillAfter(true); animHandle.setDuration(1000);
		 * 
		 * handle.setAnimation(animHandle); panel.setAnimation(animPanel);
		 */
		float handlePos = handle.getX();
		if (handle.getId() == ((Button) v).getId()) {
			AnimationSet setHandle = new AnimationSet(true);
			Animation animHandle = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, -1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			animHandle.setDuration(1000);
			// animation.setRepeatMode(Animation.RESTART);

			AnimationSet setPanel = new AnimationSet(true);
			Animation animPanel = new TranslateAnimation(
					Animation.RELATIVE_TO_PARENT, -1.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f,
					Animation.RELATIVE_TO_PARENT, 0.0f);
			animPanel.setDuration(1000);
			// animation.setRepeatMode(Animation.RESTART);

			long time = AnimationUtils.currentAnimationTimeMillis();
			animHandle.setStartTime(time);
			animPanel.setStartTime(time);

			setHandle.addAnimation(animHandle);
			setPanel.addAnimation(animPanel);

			panel.setVisibility(View.VISIBLE);
			handle.startAnimation(setHandle);
			handlein.startAnimation(setHandle);
			panel.startAnimation(setPanel);

			open = true;
			handle.setVisibility(View.INVISIBLE);
			handlein.setVisibility(View.VISIBLE);
			location = (EditText) findViewById(R.id.locationField);
			location.setOnKeyListener(new OnKeyListener() {
			    public boolean onKey(View v, int keyCode, KeyEvent event) {
			        // If the event is a key-down event on the "enter" button
			        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			          // Perform action on key press
			        	Editable e = location.getText();
			        	onQueryTextSubmit(e.toString());
			          //Toast.makeText(SearchFilterActivity.this, location.getText(), Toast.LENGTH_SHORT).show();
			         
			          return true;
			        }
			        return false;
			    }
			});
			final RadioGroup houseType = (RadioGroup) findViewById(R.id.radioGroup);
			final Button hunt = (Button) findViewById(R.id.find);

			hunt.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					AnimationSet Close = new AnimationSet(true);
					Animation animHandleClose = new TranslateAnimation(
							Animation.RELATIVE_TO_PARENT, handle.getX(),
							Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
							Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_PARENT, 0.0f);
					animHandleClose.setDuration(2000);
					animHandleClose.setFillAfter(true);
					// animation.setRepeatMode(Animation.RESTART);

					// AnimationSet setPanelClose = new AnimationSet (true);
					Animation animPanelClose = new TranslateAnimation(
							Animation.RELATIVE_TO_PARENT, panel.getX(),
							Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
							Animation.RELATIVE_TO_PARENT, 0.0f,
							Animation.RELATIVE_TO_PARENT, 0.0f);
					animPanelClose.setDuration(2000);
					animPanelClose.setFillAfter(true);
					// animation.setRepeatMode(Animation.RESTART);

					long timeClose = AnimationUtils
							.currentAnimationTimeMillis();
					animHandleClose.setStartTime(timeClose);
					animPanelClose.setStartTime(timeClose);

					Close.addAnimation(animPanelClose);
					Close.addAnimation(animHandleClose);
					panel.startAnimation(Close);
					handlein.startAnimation(Close);
					handle.startAnimation(Close);
					panel.setVisibility(View.GONE);
					handlein.setVisibility(View.INVISIBLE);
					handle.setVisibility(View.VISIBLE);

					final int checkedRadio;
					String loc = location.getText().toString();
					String type = null;
					checkedRadio = houseType.getCheckedRadioButtonId();
					if ((checkedRadio) == R.id.studio) {
						type = "Studio";
					}
					if ((checkedRadio) == R.id.onebhk) {
						type = "1 BHK";
					}
					if ((checkedRadio) == R.id.twobhk) {
						type = "2 BHK";
					}
					String toShow = type + " at or around " + loc;
					//Toast.makeText(SearchFilterActivity.this, toShow,Toast.LENGTH_SHORT).show();
					location.setText("");
					houseType.clearCheck();
				}
			});
		}

		handlein.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				AnimationSet Close = new AnimationSet(true);
				Animation animHandleClose = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, handle.getX(),
						Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animHandleClose.setDuration(2000);
				animHandleClose.setFillAfter(true);
				// animation.setRepeatMode(Animation.RESTART);

				// AnimationSet setPanelClose = new AnimationSet (true);
				Animation animPanelClose = new TranslateAnimation(
						Animation.RELATIVE_TO_PARENT, panel.getX(),
						Animation.RELATIVE_TO_PARENT, -panel.getWidth(),
						Animation.RELATIVE_TO_PARENT, 0.0f,
						Animation.RELATIVE_TO_PARENT, 0.0f);
				animPanelClose.setDuration(2000);
				animPanelClose.setFillAfter(true);
				// animation.setRepeatMode(Animation.RESTART);

				long timeClose = AnimationUtils.currentAnimationTimeMillis();
				animHandleClose.setStartTime(timeClose);
				animPanelClose.setStartTime(timeClose);

				Close.addAnimation(animPanelClose);
				Close.addAnimation(animHandleClose);

				panel.startAnimation(Close);
				handlein.startAnimation(Close);
				panel.setVisibility(View.GONE);
				handlein.setVisibility(View.INVISIBLE);
				handle.setVisibility(View.VISIBLE);
				open = false;
			}
		});
	}
	
	 public boolean onQueryTextSubmit(String query) {
		 	addresses = convertLocationToPoint(query);
		 	if(addresses != null) {
		    	if(addresses.size() == 0) {
		    		//Toast.makeText(this, "No Results found" , Toast.LENGTH_SHORT).show();
		    	}
		    	else {
			    	addressLines = new String[addresses.size()];
			    	
			    	for(int j = 0; j < addresses.size(); j++) {
			    		Address address = addresses.get(j);
				    	String s = "";
				    	for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
				    		s += address.getAddressLine(i) + "\n";
				    	}
				    	addressLines[j] = s;
				    	
				    	//mSearchView.setQuery(s, false);
				    	//Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
			    	}
			    	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			        imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
			    	Dialog dialog = createDialog();
			    	dialog.show();
			    	
			    	//mSearchView.clearFocus();
			    	
			        
		    	}
		 	}
	    	return false;
	    }
	
	public List<Address> convertLocationToPoint(String query) {
    	Geocoder gCoder = new Geocoder(this);
    	List<Address> addresses = null;
			try {
				addresses = gCoder.getFromLocationName(query, 10);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return addresses;
    }
	
	
	 public Dialog createDialog() {
	    	
	    	return new AlertDialog.Builder(mContext)
	        .setTitle("Pick")
	        //.setMessage("Message")
	        .setSingleChoiceItems(addressLines, -1, new DialogInterface.OnClickListener() {

	                public void onClick(DialogInterface dialog, int which) {
	                    // TODO Auto-generated method stub
	                	//Toast.makeText(mContext, addressLines[which], Toast.LENGTH_SHORT).show();
	                	getAddress = addressLines[which];
	                	//mContext.
						Geocoder gCoder = new Geocoder(mContext);
						try {
							addresses = gCoder.getFromLocationName(getAddress, 1);
							for(Address a : addresses) {
								int lat = (int) (Double.parseDouble(""+a.getLatitude() * 1E6));
								int log = (int) (Double.parseDouble(""+a.getLongitude() * 1E6));
								/*GeoPoint geoPoint = new GeoPoint(lat,log);
								if(myLocationOverlay != null) {
									mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
									mapView.invalidate();
								}
								myLocationOverlay = new MyCustomLocationOverlay(mContext, mapView, geoPoint);
								mapView.getOverlays().add(myLocationOverlay);
			                    mController.animateTo(geoPoint);
			                    mController.setZoom(15);
			        			mapView.invalidate();
			        			myLocationManager.removeUpdates(myLocationListener);*/
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
	                	
						
	                }
	            })
	        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	                // TODO Auto-generated method stub
	            	 if(getAddress != "")
				    		location.setText(getAddress);
	            	dialog.dismiss();
	            }

	        })
	        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

	            public void onClick(DialogInterface dialog, int which) {
	            	getAddress = "";
	            	
	                dialog.dismiss();

	            }
	        }).create();
			
	    }
}