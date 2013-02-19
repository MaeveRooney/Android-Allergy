package com.example.maptestapp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.graphics.drawable.Drawable;
import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class RestaurantMap extends MapActivity {

	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
	    MapView mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.marker);
	    MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);
	    String response = null;
	    try {
			response = new TaskAsync().execute("http://maeverooney.x10.mx/selectAllRestaurants.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    JSONArray myArray = null;
		try {
			myArray = new JSONArray(response);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    for (int i = 0; i<myArray.length(); i++) {
	    	JSONObject e = null;
	    	String name = "name";
	    	String overallRating = "overallRating";
	    	String Latitude = "GPSLatitude";
	    	String Longitude = "GPSLongitiude";
	    	try {
				e = myArray.getJSONObject(i);
			} catch (JSONException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
	    	try {
				name = e.getString("name");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}try {
				overallRating = e.getString("overallRating");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}try {
				Latitude = e.getString("GPSLatitude");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	try {
				Longitude = e.getString("GPSLongitude");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
   	   		
   	   		GeoPoint point = new GeoPoint(Integer.parseInt(Latitude),Integer.parseInt(Longitude));
   	   		OverlayItem overlayitem = new OverlayItem(point, name, overallRating+" stars");
   	   		//add Restaurants to overlay array
     	    itemizedoverlay.addOverlay(overlayitem);
	    }
	    mapOverlays.add(itemizedoverlay);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}
