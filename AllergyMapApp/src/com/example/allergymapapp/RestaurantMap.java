package com.example.allergymapapp;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
	String userID = "";
	int isFavourite = 0;
	JSONObject jsonFavourites;
	Boolean greenMarkerAdded = false;
	Boolean redMarkerAdded = false;
	Boolean heartMarkerAdded = false;
	Boolean neutralMarkerAdded = false;
	Boolean userHasWheatAllergy = false;
	Boolean userHasGlutenAllergy = false;
	Boolean userHasDairyAllergy = false;
	Boolean userHasNutAllergy = false;

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

      	//create overlay for each possible pin drawable    
	    List<Overlay> mapOverlays = mapView.getOverlays();
	    Drawable greenDrawable = this.getResources().getDrawable(R.drawable.green_marker);
	    greenDrawable.setBounds(0 - greenDrawable.getIntrinsicWidth() / 2, 0 - greenDrawable.getIntrinsicHeight(), 
	    		greenDrawable.getIntrinsicWidth() / 2, 0);//Set the new marker
	    MapItemizedOverlay greenItemizedOverlay = new MapItemizedOverlay(greenDrawable, this);
	    
	    Drawable redDrawable = this.getResources().getDrawable(R.drawable.red_marker);
	    redDrawable.setBounds(0 - redDrawable.getIntrinsicWidth() / 2, 0 - redDrawable.getIntrinsicHeight(), 
	    		redDrawable.getIntrinsicWidth() / 2, 0);//Set the new marker
	    MapItemizedOverlay redItemizedOverlay = new MapItemizedOverlay(redDrawable, this);
	    
	    Drawable heartDrawable = this.getResources().getDrawable(R.drawable.heart_marker);
	    heartDrawable.setBounds(0 - heartDrawable.getIntrinsicWidth() / 2, 0 - heartDrawable.getIntrinsicHeight(), 
	    		heartDrawable.getIntrinsicWidth() / 2, 0);//Set the new marker
	    MapItemizedOverlay heartItemizedOverlay = new MapItemizedOverlay(heartDrawable, this);
	    
	    Drawable neutralDrawable = this.getResources().getDrawable(R.drawable.neutral_marker);
	    neutralDrawable.setBounds(0 - neutralDrawable.getIntrinsicWidth() / 2, 0 - neutralDrawable.getIntrinsicHeight(), 
	    		neutralDrawable.getIntrinsicWidth() / 2, 0);//Set the new marker
	    MapItemizedOverlay neutralItemizedOverlay = new MapItemizedOverlay(neutralDrawable, this);
	    
	    //check if user has favourites
		DatabaseHandler db = new DatabaseHandler(RestaurantMap.this);
        //Check if user in sqlite database
        if (db.getRowCount() != 0) {
		      //Get user from database
		      HashMap<String,String> user = db.getUserDetails();
		      userID = (String)user.get("uid");
		      String wheat = (String)user.get("wheat");
	          String gluten = (String)user.get("gluten");
	          String dairy = (String)user.get("dairy");
	          String nut = (String)user.get("nut");
	        
	          if (wheat.equals("1")){
	        	  userHasWheatAllergy = true;
	          }
	          if (gluten.equals("1")){
	        	  userHasGlutenAllergy = true;
	          }
	          if (dairy.equals("1")){
	        	  userHasDairyAllergy = true;
	          }
	          if (nut.equals("1")){
	        	  userHasNutAllergy = true;
	          }
		      //get user favourites
		      RestaurantFunctions restaurantFunction = new RestaurantFunctions(RestaurantMap.this);
		      jsonFavourites = restaurantFunction.getUserFavourites(userID);	
        }
	      
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
		    	String goodBadNeutral = "neutral";
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
				if (!userID.matches("")){
					goodBadNeutral = compareRatingsToUserAllergies(wheatRating, glutenRating, dairyRating, nutRating);
				}
				
				//create overlay item
				GeoPoint point = new GeoPoint(Integer.parseInt(Latitude),Integer.parseInt(Longitude));
	   	   		OverlayItem overlayitem = new OverlayItem(point, id, goodBadNeutral);
		    	
		    	// check if user.
		    	// check ratings for each allergy in restaurant object.
		    	// assign red marker to low rating restaurants
				
				//check if user has favourites
			    if (!userID.matches("")) {
			    	  checkIfRestaurantInFavourites(id);

					  if (isFavourite == 1){
						  //add heart marker
						  heartItemizedOverlay.addOverlay(overlayitem);
						  heartMarkerAdded = true;
					  }
					  else {
						  //check rating against user allergies
						  //if good rating use green marker
						  // else use red marker
						  if (goodBadNeutral.matches("good")){
							  greenItemizedOverlay.addOverlay(overlayitem);
							  greenMarkerAdded = true;
						  }
						  if (goodBadNeutral.matches("bad")){
							  redItemizedOverlay.addOverlay(overlayitem);
							  redMarkerAdded = true;
						  }
						  
	
					  }
			    } else {
			    	//add neutral marker
		     	    neutralItemizedOverlay.addOverlay(overlayitem);
		     	    neutralMarkerAdded= true;
			    }	
		    }
		}
	    if (greenMarkerAdded){
	    	mapOverlays.add(greenItemizedOverlay);
	    }
		if (redMarkerAdded){
			mapOverlays.add(redItemizedOverlay);    	
		}
		if (heartMarkerAdded){
			mapOverlays.add(heartItemizedOverlay);
		}
		if (neutralMarkerAdded){
			mapOverlays.add(neutralItemizedOverlay);
		}
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
	   	mapView.postInvalidate();

   }

   @Override
   protected void onPause() {
	   	super.onPause();
	   	// when our activity pauses, we want to remove listening for location updates
	   	myLocationOverlay.disableMyLocation();
   }
   
   private void checkIfRestaurantInFavourites(String restaurantID){
		// get users favourites from db
	    JSONArray myArray = null;	    
	    // check for favourites from json response
	    try {
            if (jsonFavourites.getString("success") != null) {
                String res = jsonFavourites.getString("success");
                if(Integer.parseInt(res) == 1){
                	myArray = jsonFavourites.getJSONArray("array");
                	try{
                	  	
        		        for(int i=0;i<myArray.length();i++){	
        					JSONObject e = myArray.getJSONObject(i);
        					String thisRestaurantID = e.getString("restaurantID");
        					if (thisRestaurantID.matches(restaurantID)){
        						isFavourite = 1;
        						break;
        					} else {
        						isFavourite = 0;
        					}
        				}		
        	        }catch(JSONException e)        {
        	        	 Log.e("log_tag", "Error parsing data "+e.toString());
        	        }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	}
   
   private String compareRatingsToUserAllergies(String wheatRating, String glutenRating, String dairyRating, String nutRating){
	   if (userHasWheatAllergy && Float.parseFloat(wheatRating)<=2.5){
		   return "bad";
	   }
	   else if (userHasGlutenAllergy && Float.parseFloat(glutenRating)<=2.5){
		   return "bad";
	   }
	   else if (userHasDairyAllergy && Float.parseFloat(dairyRating)<=2.5){
		   return "bad";
	   }
	   else if (userHasNutAllergy && Float.parseFloat(nutRating)<=2.5){
		   return "bad";
	   }
	   return "good";
   }
   
    
}
