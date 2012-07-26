package edu.usc.apartment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.usc.R;

public class ApartmentItemizedOverlay extends ItemizedOverlay<OverlayItem> {

	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	private PopupPanel panel;
	private MapView mapView;
	private int rent;
	private int size;

	public ApartmentItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}

	public ApartmentItemizedOverlay(Drawable defaultMarker, Context context, MapView mapView) {
		// TODO Auto-generated constructor stub
		super(boundCenterBottom(defaultMarker));
		mContext = context;
		this.mapView = mapView;
		panel = new PopupPanel(R.layout.popup, context, mapView);
	}

	public void addOverlay(OverlayItem overlay) {
		mOverlays.add(overlay);
		populate();
	}
	
	public void clearOverlay() {
		mOverlays.clear();
		populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	@Override
	public int size() {
		return mOverlays.size();
	}

	@Override
	protected boolean onTap(int index) {
		/*
		 * OverlayItem item = mOverlays.get(index); AlertDialog dialog = new
		 * AlertDialog.Builder(mContext).create();
		 * dialog.setTitle(item.getTitle()); String point = " [ " +
		 * item.getPoint().getLatitudeE6() / 1E6 + ", " +
		 * item.getPoint().getLongitudeE6() / 1E6 + " ]";
		 * dialog.setMessage(item.getSnippet()); dialog.setCancelable(true);
		 * dialog.setButton("Close", new DialogInterface.OnClickListener() {
		 * public void onClick(DialogInterface dialog, int which) {
		 * dialog.dismiss(); } }); dialog.show();
		 */
		OverlayItem item = getItem(index);
		GeoPoint geo = item.getPoint();
		Drawable drawable = mContext.getResources().getDrawable(R.drawable.current_location_icon);
		boundCenterBottom(drawable);
		item.setMarker(drawable);
		addOverlay(item);
		mapView.refreshDrawableState();
		Point pt = mapView.getProjection().toPixels(geo, null);

		View view = panel.getView();

		// ((TextView)
		// view.findViewById(R.id.latitude)).setText(String.valueOf(geo.getLatitudeE6()
		// / 1000000.0));
		// ((TextView)
		// view.findViewById(R.id.longitude)).setText(String.valueOf(geo.getLongitudeE6()
		// / 1000000.0));
		// ((TextView) view.findViewById(R.id.x)).setText(item.getTitle());
		// ((TextView) view.findViewById(R.id.y)).setText(item.getSnippet());
		((TextView) view.findViewById(R.id.textView1)).setText(item.getTitle());
		String snippet = item.getSnippet();
		String delims = "[,]+";
		String[] tokens = snippet.split(delims);
		this.rent = Integer.parseInt(tokens[0]);
		this.size = Integer.parseInt(tokens[1]);
		((TextView) view.findViewById(R.id.rentValue)).setText("" + rent);
		if(size == 0)
			((TextView) view.findViewById(R.id.sizeValue)).setText("Studio");
		else
			((TextView) view.findViewById(R.id.sizeValue)).setText("" + size + " br");
		//((WebView) view.findViewById(R.id.imageView1)).loadUrl("http://www.trojanlistings.com/userimages/4479_1_p_s.jpg");
		
		
		
		//TextView mTextView = ((TextView) view.findViewById(R.id.textView2));
		//mTextView.setMovementMethod(LinkMovementMethod.getInstance());
		
		
		Button vistButton = ((Button) view.findViewById(R.id.webPageButton));
		vistButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				String text = "http://www.trojanlistings.com/listings/details?pid=4479";
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
				//mTextView.setText(Html.fromHtml(text));
				mContext.startActivity(i);
			}
		});
		
		
		
		((WebView) view.findViewById(R.id.webView1)).loadUrl("http://www.trojanlistings.com/listings/onebedroom/print?sorton=&desc=&start=all");
		((WebView) view.findViewById(R.id.webView1)).setBackgroundColor(0);
		panel.show(pt.y * 2 > mapView.getHeight());

		Drawable drawableReset = mContext.getResources().getDrawable(R.drawable.bubble);
		boundCenterBottom(drawableReset);
		panel.setItimizedOverlay(item, drawableReset);

		return true;
	}

	public void imageUrl(View view) {

		try {
			String url1 = "http://www.trojanlistings.com/userimages/4479_1_p_s.jpg";
			URL ulrn = new URL(url1);
			HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
			InputStream is = con.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(is);
			if (null != bmp)
				((ImageView) view.findViewById(R.id.imageView1))
						.setImageBitmap(bmp);
			else
				System.out.println("The Bitmap is NULL");

		} catch (Exception e) {
		}
	}

	public void setRent(int rentValue) {
		// TODO Auto-generated method stub
		this.rent = rentValue;
	}

	public void setValue(int sizeValue) {
		// TODO Auto-generated method stub
		this.size = sizeValue;
	}

}
