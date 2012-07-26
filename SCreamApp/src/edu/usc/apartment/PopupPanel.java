package edu.usc.apartment;

import java.util.List;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.usc.R;

import edu.usc.Tutorial.*;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class PopupPanel{
	View popup;
	boolean isVisible = false;
	MapView map = null;
	
	public OverlayItem overlayItem;
	public Drawable drawableReset;

	public PopupPanel(int layout, Context context, final MapView mapView) {
		this.map = mapView;
		ViewGroup parent = (ViewGroup) map.getParent();
		LayoutInflater mInflater = LayoutInflater.from(context);
		
		popup = mInflater.inflate(layout, parent, false);
		final ImageButton button = (ImageButton) popup.findViewById(R.id.imageButton1);
		
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				overlayItem.setMarker(drawableReset);
				hide();
			}
		});
		popup.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//hide();
			}
		});
	}

	public View getView() {
		return (popup);
	}

	public void show(boolean alignTop) {
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				440,
				575);

		if (alignTop) {
			lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			lp.setMargins(0, 20, 0, 0);
		} else {
			lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			lp.setMargins(0, 0, 0, 60);
		}

		hide();

		((ViewGroup) map.getParent()).addView(popup, lp);
		isVisible = true;
	}

	public void hide() {
		if (isVisible ) {
			isVisible = false;
			((ViewGroup) popup.getParent()).removeView(popup);
		}
	}

	public void setMap(MapView mapView) {
		// TODO Auto-generated method stub
		this.map = mapView;
	}

	public void setItimizedOverlay(OverlayItem item, Drawable drawableReset) {
		// TODO Auto-generated method stub
		this.overlayItem = item;
		this.drawableReset = drawableReset;
	}
}
