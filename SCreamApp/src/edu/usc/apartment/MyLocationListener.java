package edu.usc.apartment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class MyLocationListener implements LocationListener{

	private GeoPoint GeoPoint;
	private MapView mapView;
	private MapController mController;
	private LocationManager myLocationManager;
	private LocationListener myLocationListener;
	private MyCustomLocationOverlay myLocationOverlay;
	private Context context;

	public MyLocationListener(MapView mapView, Context context, LocationManager locationManager, LocationListener locationListener, MapController controller) {
		// TODO Auto-generated constructor stub
		this.mapView = mapView;
		this.context = context;
		this.myLocationManager = locationManager;
		this.myLocationListener = locationListener;
		this.mController = controller;
	}

	public void onLocationChanged(Location location) {
		if (location != null) {
			GeoPoint point = new GeoPoint(
					(int) (location.getLatitude() * 1E6), 
					(int) (location.getLongitude() * 1E6));
			GeoPoint = point;
			String address = ConvertPointToLocation(point);
			//Toast.makeText(context, address, Toast.LENGTH_SHORT).show();

			myLocationOverlay = new MyCustomLocationOverlay(context, mapView, point);
			mapView.getOverlays().add(0, myLocationOverlay);
			mController.animateTo(point);
			mController.setZoom(15);
			mapView.invalidate();
			
		}
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onStatusChanged(String provider,
			int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	public String ConvertPointToLocation(GeoPoint point) {   
		String address = "";
		Geocoder geoCoder = new Geocoder(
				context, Locale.getDefault());
		try {
			List<Address> addresses = geoCoder.getFromLocation(
					point.getLatitudeE6()  / 1E6, 
					point.getLongitudeE6() / 1E6, 1);

			if (addresses.size() > 0) {
				for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++)
					address += addresses.get(0).getAddressLine(index) + " ";
			}
		}
		catch (IOException e) {        
			e.printStackTrace();
		}   

		return address;
	}
	
	public MyCustomLocationOverlay getMyLocationOverlay() {
		return this.myLocationOverlay;
	}
}

