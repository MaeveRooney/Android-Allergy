package com.example.allergymapapp;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.maptestapp.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class RestaurantMap extends MapActivity {
	
	private MapController myMapController;
	private MapView mapView;
	private GeoPoint Dublin = new GeoPoint(53345800, -6267100);
	private MyLocationOverlay myLocationOverlay;
	private GeoPoint mCurrentPoint;

	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map_view);

	    Button mapBtn = (Button)findViewById(R.id.map_button);
	    mapBtn.setEnabled(false);

	    mapView = (MapView) findViewById(R.id.mapview);
	    myMapController = mapView.getController();
	    myMapController.setZoom(15);
	    mapView.setBuiltInZoomControls(true);


	    // create an overlay that shows our current location
        myLocationOverlay = new MyLocationOverlay(this, mapView);
        myLocationOverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapView.getController().animateTo(myLocationOverlay.getMyLocation());
            }
        });

        // add this overlay to the MapView and refresh it
        mapView.getOverlays().add(myLocationOverlay);
        mapView.postInvalidate();

      	    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable drawable = this.getResources().getDrawable(R.drawable.red_marker);
	    MapItemizedOverlay itemizedoverlay = new MapItemizedOverlay(drawable, this);
	    
	    //get restaurants from web server
	    String response = null;	    
	    TaskAsyncHttpGet httpRequest = new TaskAsyncHttpGet(RestaurantMap.this);
		try {
			response = httpRequest.execute("http://maeverooney.x10.mx/selectAllRestaurants.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    if (response !=null){
		    JSONArray myArray = null;
			try {
				myArray = new JSONArray(response);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
		    for (int i = 0; i<myArray.length(); i++) {
		    	JSONObject e = null;
		    	String id = "id";
		    	String name = "name";
		    	String Latitude = "GPSLatitude";
		    	String Longitude = "GPSLongitude";
		    	String wheatRating = "wheatRating";
		    	String glutenRating = "glutenRating";
		    	String dairyRating = "dairyRating";
		    	String nutRating = "nutRating";
		    	String marker = "good";
		    	try {
					e = myArray.getJSONObject(i);
				} catch (JSONException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		    	try {
					id = e.getString("id");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					name = e.getString("name");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					Longitude = e.getString("GPSLongitude");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					Latitude = e.getString("GPSLatitude");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					wheatRating = e.getString("wheatRating");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					glutenRating = e.getString("glutenRating");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					dairyRating = e.getString("dairyRating");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}try {
					nutRating = e.getString("nutRating");
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    	
		    	// check if user.
		    	// check ratings for each allergy in restaurant object.
		    	// assign red marker to low rating restaurants
	   	   		
	   	   		GeoPoint point = new GeoPoint(Integer.parseInt(Latitude),Integer.parseInt(Longitude));
	   	   		OverlayItem overlayitem = new OverlayItem(point, id, marker);
	   	   		//add Restaurants to overlay array
	     	    itemizedoverlay.addOverlay(overlayitem);
		    }
		}
	    mapOverlays.add(itemizedoverlay);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onClick(View v) {
		switch(v.getId()) {
    	case R.id.menu_button:
    		Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
        case R.id.list_button:
        	Intent listIntent = new Intent(v.getContext(), ListRestaurants.class);
            startActivityForResult(listIntent, 0);
        break;
        case R.id.back_button:
        	finish();
        break;
		}
		
	}
	
   @Override
   protected void onResume() {
	   	super.onResume();
	   	// when our activity resumes, we want to register for location updates
	   	myLocationOverlay.enableMyLocation();

   }

   @Override
   protected void onPause() {
	   	super.onPause();
	   	// when our activity pauses, we want to remove listening for location updates
	   	myLocationOverlay.disableMyLocation();
   }
   
    
}
