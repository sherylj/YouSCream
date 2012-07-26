package edu.usc.apartment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import edu.usc.R;

public class MyCustomLocationOverlay extends Overlay {
    private Context mContext;
    private float   mOrientation;
    
    private static final int CIRCLERADIUS = 2;
    
	private GeoPoint geopoint;
	private boolean search;

    public MyCustomLocationOverlay(Context context, MapView mapView, GeoPoint point) {
        //super(context, mapView);
        mContext = context;
        this.geopoint = point;
    }
    
    public MyCustomLocationOverlay(Context context, MapView mapView, GeoPoint point, boolean search) {
        //super(context, mapView);
        mContext = context;
        this.geopoint = point;
        this.search = search;
    }

    /*@Override 
    protected void drawMyLocation(Canvas canvas, MapView mapView, Location lastFix, GeoPoint myLocation, long when) {
        // translate the GeoPoint to screen pixels
        Point screenPts = mapView.getProjection().toPixels(myLocation, null);

        // create a rotated copy of the marker
        Bitmap arrowBitmap = BitmapFactory.decodeResource( mContext.getResources(), R.drawable.icon);
        Matrix matrix = new Matrix();
        matrix.postRotate(mOrientation);
        Bitmap rotatedBmp = Bitmap.createBitmap(
            arrowBitmap, 
            0, 0, 
            arrowBitmap.getWidth(), 
            arrowBitmap.getHeight(), 
            matrix, 
            true
        );
        // add the rotated marker to the canvas
        canvas.drawBitmap(
            rotatedBmp, 
            screenPts.x - (rotatedBmp.getWidth()  / 2), 
            screenPts.y - (rotatedBmp.getHeight() / 2), 
            null
        );
    }*/
    
    @Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// Transfrom geoposition to Point on canvas
		Projection projection = mapView.getProjection();
		Point point = new Point();
		projection.toPixels(geopoint, point);
 
		// background
		Paint background = new Paint();
		background.setColor(Color.WHITE);
		RectF rect = new RectF();
		rect.set(point.x + 2 * CIRCLERADIUS, point.y - 4 * CIRCLERADIUS,
				point.x + 90, point.y + 12);
 
		// text "My Location"
		Paint text = new Paint();
		text.setAntiAlias(true);
		text.setColor(Color.BLUE);
		text.setTextSize(12);
		text.setTypeface(Typeface.MONOSPACE);
 
		Bitmap _scratch = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.current_location_icon2);
		Bitmap _searchResults = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.search_results);
		
		// the circle to mark the spot
		Paint circle = new Paint();
		if(search) {
			/*circle.setColor(Color.BLUE);
			circle.setAntiAlias(true);
	 
			canvas.drawRoundRect(rect, 4, 4, background);
			canvas.drawCircle(point.x, point.y, CIRCLERADIUS, circle);
			canvas.drawText("My Search", point.x + 3 * CIRCLERADIUS, point.y + 3 * CIRCLERADIUS, text);*/
			
			canvas.drawBitmap(_searchResults, point.x - 40, point.y - 10 , circle);
		} else {
			canvas.drawBitmap(_scratch, point.x - 11, point.y - 18, circle);
		}
		
		
	}

    public void setOrientation(float newOrientation) {
         mOrientation = newOrientation;
    }
    
    public GeoPoint getGeoPoint() {
    	return this.geopoint;
    }
}