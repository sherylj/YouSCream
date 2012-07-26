package edu.usc.apartment;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.usc.R;
import edu.usc.Tutorial.Start;
import edu.usc.searchFilter.SearchFilterActivity;

public class YouSCreamActivity extends MapActivity implements OnItemClickListener, IReportBack, OnQueryTextListener, Button.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {

	public Context mContext;
	public MapController mController;
	public LocationManager myLocationManager;
	public LocationListener myLocationListener;
	private Cursor cursor;
	public ApartmentDBAdapter apartDB;
	private ApartmentItemizedOverlay itemizedoverlay;
	private List<Overlay> mapOverlays;
	private MapView mapView;
	private Location tommyTrojan;
	public ProgressDialog dialog;
	public boolean runOnce;
	public SearchView mSearchView;
	private SearchManager searchManager;
	private String[] addressLines;
	public String getAddress = "";
	public List<Address> addresses;
	public MyCustomLocationOverlay myLocationOverlay;
	public EditText location;
	boolean open = false;
	public SeekBar minSeekBar;
	public TextView minText;
	public SeekBar maxSeekBar;
	public TextView maxText;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		runOnce = false;
		dialog = ProgressDialog.show(this, "",  "Loading Apartments. Please wait...", true);
		
		setContentView(R.layout.apartment);
		/*final ExpandableListView apartExpandView = (ExpandableListView) findViewById(R.id.apartmentExpandView);
		apartExpandView.setGroupIndicator(null);
		apartExpandView.setChildIndicator(null);

		String[] titles = { "Rent", "Size" };
		String[] rent = { "Min", "Max" };
		String[] size = { "Studio", "1 Bdr", "2 Bdr", "Display All" };
		String[][] contents = { rent, size };
		
		//GeoPoint tommyTrojan = new GeoPoint(34020542,-118285439);
		
	    
		final ApartmentExpandList adapter = new ApartmentExpandList(this, titles,contents);
		apartExpandView.setAdapter(adapter);
		
		
		//apartExpandView.setGroupIndicator(R.drawable.icon_arrow);
		apartExpandView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
		    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		        Object e = (Object)adapter.getChild(groupPosition, childPosition);
		        //doing some work for child
		        if(e.toString().equals("1 Bdr")) {
		        	get1Bdr();
		        } else if(e.toString().equals("2 Bdr")) {
		        	get2Bdr();
		        } else if(e.toString().equals("Studio")) {
		        	getStudio();
		        } else if(e.toString().equals("Display All")) {
		        	getAllApartments();
		        } else if(e.toString().equals("Min")) {
		        	new RentPopup(YouSCreamActivity.this);
		        }
		        
		        Toast.makeText(getBaseContext(), e.toString() , Toast.LENGTH_SHORT).show();
				return true;
		    }
		});*/
		//this.workwithListActionBar();

		mapView = (MapView) findViewById(R.id.mapview);
		mapView.displayZoomControls(true);
		mapView.setBuiltInZoomControls(true);

		mController = mapView.getController();
		mController.setZoom(15);

		
		
		
		
		mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.bubble);
		itemizedoverlay = new ApartmentItemizedOverlay(drawable, this, mapView);
		 
		tommyTrojan = new Location("Tommy Trojan");
		tommyTrojan.setLatitude(34.0210643);
		tommyTrojan.setLongitude(-118.28575);
		myLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		myLocationListener = new MyLocationListener(mapView, getBaseContext(), myLocationManager, myLocationListener, mController);
		//myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
		/*apartDB = new ApartmentDBAdapter(this);
		apartDB.open();
		apartDB.update();
		addAddresses();
		
		getAllApartments();*/
		
		mContext = this;
		
		
		new Thread(new Runnable() {
            @Override
            public void run()
            {
            	apartDB = new ApartmentDBAdapter(mContext);
        		apartDB.open();
        		apartDB.update();
        		addAddresses();
        		
        		if(!runOnce)
        			getAllApartments();
        		runOnce = true;
        		
        		dialog.dismiss();
        		//myLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        		myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
        		//myLocationManager.removeUpdates(myLocationListener);
            }
        }).start();
		
		final Button handle = (Button) this.findViewById(R.id.handleout);
		handle.setOnClickListener(this);
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
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
			final RadioGroup locationType = (RadioGroup) findViewById(R.id.radioGroup2);
			final int checkedLocationRadio = locationType.getCheckedRadioButtonId();
			location = (EditText) findViewById(R.id.locationField);
			location.setHint("Selected a search type above");
			location.setEnabled(false);
			locationType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(RadioGroup group,int checkedId) {
							int checkedRadio = locationType.getCheckedRadioButtonId();
							if (checkedRadio == R.id.currentLocRadio) {
								location.setText("");
								location.setEnabled(false);
								int lat = (int) (Double.parseDouble(""+(myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude() * 1E6)));
								int log = (int) (Double.parseDouble(""+(myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLongitude() * 1E6)));
								GeoPoint p = new GeoPoint(lat, log);
								 location.setText(ConvertPointToLocation(p));
							}
								
							if (checkedRadio == R.id.searchLocRadio) {
								location.setText("");
								location.setHint("Enter a Search Query");
								location.setEnabled(true);
							}
						}
					});
			
			
			
			location.setOnKeyListener(new OnKeyListener() {
			    public boolean onKey(View v, int keyCode, KeyEvent event) {
			        // If the event is a key-down event on the "enter" button
			        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
			          // Perform action on key press
			        	
			        	InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				        imm.hideSoftInputFromWindow(location.getWindowToken(), 0);
			          //Toast.makeText(SearchFilterActivity.this, location.getText(), Toast.LENGTH_SHORT).show();
			         
			          return true;
			        }
			        return false;
			    }
			});
			
			minSeekBar = (SeekBar) findViewById(R.id.min_bar);
			minSeekBar.setOnSeekBarChangeListener(this);
			
			minText = (TextView) findViewById(R.id.min_text);
			minText.addTextChangedListener(this);
			
			maxSeekBar = (SeekBar) findViewById(R.id.max_bar);
			maxSeekBar.setOnSeekBarChangeListener(this);
			
			maxText = (TextView) findViewById(R.id.max_text);
			maxText.addTextChangedListener(this);
			
			
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
						getStudio();
					}
					if ((checkedRadio) == R.id.onebhk) {
						type = "1 BHK";
						get1Bdr();
					}
					if ((checkedRadio) == R.id.twobhk) {
						type = "2 BHK";
						get2Bdr();
					}
					if ((checkedRadio) == R.id.displayall) {
						type = "Display All";
						getAllApartments();
					}

					
                	//mContext.
					Geocoder gCoder = new Geocoder(mContext);
					try {
						addresses = gCoder.getFromLocationName(getAddress, 1);
						for(Address a : addresses) {
							int lat = (int) (Double.parseDouble(""+a.getLatitude() * 1E6));
							int log = (int) (Double.parseDouble(""+a.getLongitude() * 1E6));
							GeoPoint geoPoint = new GeoPoint(lat,log);
							if(myLocationOverlay != null && mapView.getOverlays().indexOf(myLocationOverlay) != -1) {
								mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
								mapView.invalidate();
							}
							myLocationOverlay = new MyCustomLocationOverlay(mContext, mapView, geoPoint, true);
							mapView.getOverlays().add(myLocationOverlay);
		                    mController.animateTo(geoPoint);
		                    mController.setZoom(15);
		        			mapView.invalidate();
		        			myLocationManager.removeUpdates(myLocationListener);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String toShow = type + " at or around " + loc;
					//Toast.makeText(SearchFilterActivity.this, toShow,Toast.LENGTH_SHORT).show();
					//location.setText("");
					Editable e = location.getText();
					if(location.isEnabled())
						onQueryTextSubmit(e.toString());
					else {
						if(myLocationOverlay != null) {
							mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
							mapView.invalidate();
						}
						 myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000,100.0f,myLocationListener);
					}
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
			        imm.hideSoftInputFromWindow(location.getWindowToken(), 0);
			    	Dialog dialog = createDialog();
			    	dialog.show();
			    	
			    	//mSearchView.clearFocus();
			    	
			        
		    	}
		 	}
	    	return false;
	    }
	
	
	
	
	 public Dialog createDialog() {
	    	
	    	return new AlertDialog.Builder(mContext)
	        .setTitle("These are the closest addresses that match your query")
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
								GeoPoint geoPoint = new GeoPoint(lat,log);
								if(myLocationOverlay != null) {
									mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
									mapView.invalidate();
								}
								myLocationOverlay = new MyCustomLocationOverlay(mContext, mapView, geoPoint, true);
								mapView.getOverlays().add(myLocationOverlay);
			                    mController.animateTo(geoPoint);
			                    mController.setZoom(15);
			        			mapView.invalidate();
			        			myLocationManager.removeUpdates(myLocationListener);
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


	public void addAddresses() {
		ArrayList<ApartDescr> addressInputs = new ArrayList<ApartDescr>();
		addressInputs.add(new ApartDescr("3019 Budlong Ave Los Angeles, CA 90007", "1", "700"));
		addressInputs.add(new ApartDescr("1228 W 22nd St. 90007 Los Angeles, CA 90007", "1", "395" ));
		addressInputs.add(new ApartDescr("1239 W 30th Los Angeles, CA 90007","1","1050"));
		addressInputs.add(new ApartDescr("2850 South Harcourt Avenue Los Angeles, CA 90016","1","1400"));
		addressInputs.add(new ApartDescr("637 W 23rd Street Los Angeles, CA 90007","1","1000"));
		addressInputs.add(new ApartDescr("544 W 41st Dr Los Angeles, CA 90037","1","800"));
		addressInputs.add(new ApartDescr("645 W. 9th St. Los Angeles, CA 90015","1","2000"));
		addressInputs.add(new ApartDescr("4200 Via Arbolada #201 Los Angeles, CA 90042","1","1400"));
		addressInputs.add(new ApartDescr("803 W 30th Street Los Angeles, CA 30007","1","1295"));
		addressInputs.add(new ApartDescr("3584 Figueroa Street Los Angeles, CA 90007","2","1699"));
		addressInputs.add(new ApartDescr("3760 S. Figueroa Street Los Angeles, CA 90007", "2", "1025"));
		addressInputs.add(new ApartDescr("2619 Wilshire Blvd. Los Angeles, CA 90057", "0", "600"));
		
		Geocoder gCoder = new Geocoder(this);
		for(ApartDescr s : addressInputs) {
			List<Address> addresses;
			try {
				addresses = gCoder.getFromLocationName(s.getAddress(), 5);
				for(Address a : addresses) {
					apartDB.createTodo("" + a.getLatitude(), " " + a.getLongitude(), s.getAddress(), "" + s.getRent(), "" + s.getSize());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public void get1Bdr() {

		mapView.getOverlays().clear();
		mapView.postInvalidate();
		itemizedoverlay.clearOverlay();
		cursor = apartDB.fetchAllTodos();
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			//Toast.makeText(getBaseContext(), cursor.getString(3), Toast.LENGTH_SHORT).show();
			int latitude = (int) ((Double.parseDouble(cursor.getString(1)) * 1E6));
			int longitude = (int) ((Double.parseDouble(cursor.getString(2)) * 1E6));
			int rentValue = Integer.parseInt(cursor.getString(4));
			int sizeValue = Integer.parseInt(cursor.getString(5));
			
			if(sizeValue == 1) {
				GeoPoint p = new GeoPoint( latitude,  longitude);
				OverlayItem overlayitem = new OverlayItem(p, cursor.getString(3), "" + rentValue + "," + sizeValue);
				itemizedoverlay.addOverlay(overlayitem);
			}
			cursor.moveToNext();
		}
		cursor.close();
		mapOverlays.add(itemizedoverlay);
	}
	
	public void get2Bdr() {

		mapView.getOverlays().clear();
		mapView.postInvalidate();
		itemizedoverlay.clearOverlay();
		cursor = apartDB.fetchAllTodos();
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			//Toast.makeText(getBaseContext(), cursor.getString(3), Toast.LENGTH_SHORT).show();
			int latitude = (int) ((Double.parseDouble(cursor.getString(1)) * 1E6));
			int longitude = (int) ((Double.parseDouble(cursor.getString(2)) * 1E6));
			int rentValue = Integer.parseInt(cursor.getString(4));
			int sizeValue = Integer.parseInt(cursor.getString(5));
			
			if(sizeValue == 2) {
				GeoPoint p = new GeoPoint( latitude,  longitude);
				OverlayItem overlayitem = new OverlayItem(p, cursor.getString(3), "" + rentValue + "," + sizeValue);
				itemizedoverlay.addOverlay(overlayitem);
			}
			cursor.moveToNext();
		}
		cursor.close();
		mapOverlays.add(itemizedoverlay);
	}
	
	public void getStudio() {

		mapView.getOverlays().clear();
		mapView.postInvalidate();
		itemizedoverlay.clearOverlay();
		cursor = apartDB.fetchAllTodos();
		//startManagingCursor(cursor);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			//Toast.makeText(getBaseContext(), cursor.getString(3), Toast.LENGTH_SHORT).show();
			int latitude = (int) ((Double.parseDouble(cursor.getString(1)) * 1E6));
			int longitude = (int) ((Double.parseDouble(cursor.getString(2)) * 1E6));
			int rentValue = Integer.parseInt(cursor.getString(4));
			int sizeValue = Integer.parseInt(cursor.getString(5));
			
			if(sizeValue != 1 && sizeValue != 2) {
				GeoPoint p = new GeoPoint( latitude,  longitude);
				OverlayItem overlayitem = new OverlayItem(p, cursor.getString(3), "" + rentValue + "," + sizeValue);
				itemizedoverlay.addOverlay(overlayitem);
			}
			cursor.moveToNext();
		}
		cursor.close();
		mapOverlays.add(itemizedoverlay);
	}
	
	public void getAllApartments() {
		mapView.getOverlays().clear();
		mapView.postInvalidate();
		itemizedoverlay.clearOverlay();
		cursor = apartDB.fetchAllTodos();
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			//Toast.makeText(getBaseContext(), cursor.getString(3), Toast.LENGTH_SHORT).show();
			int latitude = (int) ((Double.parseDouble(cursor.getString(1)) * 1E6));
			int longitude = (int) ((Double.parseDouble(cursor.getString(2)) * 1E6));
			int rentValue = Integer.parseInt(cursor.getString(4));
			int sizeValue = Integer.parseInt(cursor.getString(5));
			GeoPoint p = new GeoPoint( latitude,  longitude);
			OverlayItem overlayitem = new OverlayItem(p, cursor.getString(3), "" + rentValue + "," + sizeValue);
			itemizedoverlay.addOverlay(overlayitem);
			cursor.moveToNext();
		}
		cursor.close();
		mapOverlays.add(itemizedoverlay);
		
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ActionBar actionBar = this.getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	// pauses listener while app is inactive
	@Override
	public void onPause() {
		super.onPause();
		myLocationManager.removeUpdates(myLocationListener);
	}

	// reactivates listener when app is resumed
	@Override
	public void onResume() {
		super.onResume();
		myLocationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 1000, 100.0f,
				myLocationListener);
	}

	public void workwithListActionBar() {
		ActionBar bar = this.getActionBar();
		// bar.setTitle("Rent");
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		bar.setListNavigationCallbacks(new SimpleSpinnerArrayAdapter(this),
				new ListListener(this, this));
	}
	
	/*@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {

		switch (item.getItemId()) {
		case R.id.submenu_itemStudio:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			
			return true;
		case R.id.submenu_item1Bdr:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			
			return true;

		case R.id.submenu_item2Bdr:
			if (item.isChecked())
				item.setChecked(false);
			else
				item.setChecked(true);
			return true;
		
		default:
			return super.onMenuItemSelected(featureId, item);
		}
	}*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		//SubMenu menu = (SubMenu) findViewById(R.id.submenu2);
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent intent = new Intent(YouSCreamActivity.this, edu.usc.Tutorial.Start.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.putExtra("dialog", "stop");
			startActivity(intent);
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			return true;
		
		/*case R.id.menu_currentLoc:
			if(myLocationOverlay != null && mapView.getOverlays().indexOf(myLocationOverlay) != -1) {
				mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
				mapView.invalidate();
				
			}
        	myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
        	myLocationOverlay = null;*/
			
		case R.id.menu_currentLoc:
			final AlertDialog.Builder builder=new AlertDialog.Builder(YouSCreamActivity.this);
            builder.setTitle("Housing Help");
            builder.setMessage("YouSCream provides a simple yet powerful search engine that enables students retrieve " +
            		"real time information on available housing options based on current or selected location. " +
            		"\n\n" +
            		"*** Based on current location housing options are displayed in form of housing icons on the map." +
            		"\n\n" +
            		"*** Expand the side panel and input location information as well as filters needed and hunt!");
            
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                      }
                    });
            builder.setIcon(R.drawable.question);
            AlertDialog alert = builder.create();
            alert.show();
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void onGroupItemClick(MenuItem item) {
	    // One of the group items (using the onClick attribute) was clicked
	    // The item parameter passed here indicates which item it is
	    // All other menu item clicks are handled by onOptionsItemSelected()
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
	    super.onPrepareOptionsMenu(menu);
	    getActionBar().setSubtitle("Apartments");
	    return true;
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		/*getMenuInflater().inflate(R.menu.search, menu);
		//SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		// Set appropriate listeners for searchView
		searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setIconifiedByDefault(false); // Do not iconify the widget;
		MenuItem searchItem = menu.findItem(R.id.menu_search);
        mSearchView = (SearchView) searchItem.getActionView();
        setupSearchView(searchItem);
        mSearchView.setRight(100);
        mSearchView.setMinimumWidth(1000);
        mSearchView.setSaveEnabled(true);*/
		//getMenuInflater().inflate(R.menu.current_location_impl, menu);

		getMenuInflater().inflate(R.menu.help_item, menu);
		return true;
		
	}
	
	private void setupSearchView(MenuItem searchItem) {

        if (isAlwaysExpanded()) {
            mSearchView.setIconifiedByDefault(false);
        } else {
            searchItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView.setOnQueryTextListener(this);
    }

    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty()) {
        	if(myLocationOverlay != null) {
				mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
				mapView.invalidate();
				
			}
        	myLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
        	/*List<Overlay> overlays = mapView.getOverlays();
        	for(Overlay o : overlays) {
        		if(o instanceof MyLocationOverlay) {
        			myLocationOverlay = (MyCustomLocationOverlay) o;
        			return false;
        		}
        	}*/
        	myLocationOverlay = null;
        }
        	
        return false;
    }
    
    public void setMyLocationOverlay(MyCustomLocationOverlay overlay) {
    	this.myLocationOverlay = overlay;
    }

    /*public boolean onQueryTextSubmit(String query) {
    	if(convertLocationToPoint(query).size() == 0) {
    		Toast.makeText(this, "No Results found" , Toast.LENGTH_SHORT).show();
    	}
    	else {
	    	addressLines = new String[convertLocationToPoint(query).size()];
	    	
	    	for(int j = 0; j < convertLocationToPoint(query).size(); j++) {
	    		Address address = convertLocationToPoint(query).get(j);
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
	    	if(getAddress != "")
	    		mSearchView.setQuery(getAddress, false);
	    	mSearchView.clearFocus();
	    	
	        
    	}
    	return false;
    }
    
    public Dialog createDialog() {
    	
    	return new AlertDialog.Builder(YouSCreamActivity.this)
        .setTitle("Pick")
        //.setMessage("Message")
        .setSingleChoiceItems(addressLines, -1, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                	//Toast.makeText(mContext, addressLines[which], Toast.LENGTH_SHORT).show();
                	getAddress = addressLines[which];
                	mSearchView.setQuery(getAddress, false);
					Geocoder gCoder = new Geocoder(mContext);
					try {
						addresses = gCoder.getFromLocationName(getAddress, 1);
						for(Address a : addresses) {
							int lat = (int) (Double.parseDouble(""+a.getLatitude() * 1E6));
							int log = (int) (Double.parseDouble(""+a.getLongitude() * 1E6));
							GeoPoint geoPoint = new GeoPoint(lat,log);
							if(myLocationOverlay != null) {
								mapView.getOverlays().remove(mapView.getOverlays().indexOf(myLocationOverlay));
								mapView.invalidate();
							}
							myLocationOverlay = new MyCustomLocationOverlay(mContext, mapView, geoPoint);
							mapView.getOverlays().add(myLocationOverlay);
		                    mController.animateTo(geoPoint);
		                    mController.setZoom(15);
		        			mapView.invalidate();
		        			myLocationManager.removeUpdates(myLocationListener);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
                	
					
                }
            })
        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
            	dialog.dismiss();
            }

        })
        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            	getAddress = "";
            	
                dialog.dismiss();

            }
        }).create();
		
    }*/
   

    public boolean onClose() {
        //mStatusView.setText("Closed!");
        return false;
    }

    protected boolean isAlwaysExpanded() {
        return true;
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
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}
	
	public String ConvertPointToLocation(GeoPoint point) {
    	String address = "";
    	try {
    		Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        	List<Address> addresses = geoCoder.getFromLocation(point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6, 1);
        	
			for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++) {
		          address += addresses.get(0).getAddressLine(index) + " ";
		      }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
    }
	
	public int calculateDistance(Location l1, Location l2) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Double.valueOf(twoDForm.format(l1.distanceTo(l2) / 1609.344)).intValue();
		
	}

	@Override
	public void reportBack(String tag, String message) {
		
		// TODO Auto-generated method stub
		Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
		Dialog yourDialog = new Dialog(this);
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.minrent,
				(ViewGroup) findViewById(R.id.minRentDialog));
		AlertDialog dialog = new AlertDialog.Builder(YouSCreamActivity.this)
		.create();
		dialog.setView(layout);
		dialog.setTitle(message);
		dialog.setMessage(message);
		dialog.setCancelable(true);
		dialog.setButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		dialog.show();
		SeekBar sb = (SeekBar) layout.findViewById(R.id.seekBar1);
		final TextView tv = (TextView) layout.findViewById(R.id.textView2);
		sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// Do something here with new value
				tv.setText("" + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void reportTransient(String tag, String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub
		if (!minText.getText().toString().equals("")) {
			if (Integer.parseInt(minText.getText().toString()) > 1699)
				minText.setText("1699");
			minSeekBar.setProgress(convertToRentValue(minText.getText().toString()));
		} else
			minSeekBar.setProgress(395);
		
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		if(fromUser) {
			switch(seekBar.getId()) {
				case R.id.min_bar:
					minText.setText(convertToRentValue(progress));
					break;
				case R.id.max_bar:
					maxText.setText(convertToRentValue(progress));
					break;
			}
			
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String convertToRentValue(int value) {
		return Integer.toString( (1304/100) * value + 395);
	}
	
	public int convertToRentValue(String value) {
		return (100 * (Integer.parseInt(value) - 395))/1304;
	}
}