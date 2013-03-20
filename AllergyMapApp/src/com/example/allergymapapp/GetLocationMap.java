package com.example.allergymapapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.maptestapp.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class GetLocationMap extends MapActivity {
	String restaurantName;
	String restaurantEmail;
	String restaurantPhone;
	String restaurantReviewText;
	float wheatRating = 0;
	float glutenRating = 0;
	float dairyRating = 0;
	float nutRating = 0;
	float overallRating = 0;
	private MapController myMapController;
	private MapView mapView;
	private LocationManager locationManager;
	private MyLocationListener locationListener;
	private GeoPoint markerGeoPoint = new GeoPoint(53345800, -6267100);
	private String bestProvider;
	private int latitude;
	private int longitude;
	private GeoPoint mOldTopLeft;
    private GeoPoint mOldCenter;
    private GeoPoint mOldBottomRight;
    private int mOldZoomLevel = -1;
	private static MapViewListener mMapViewListener;
    public MapViewListener getMapViewListener() { return mMapViewListener; }
    public void setMapViewListener(MapViewListener value) { mMapViewListener = value; }
 
	
	@Override
	protected boolean isRouteDisplayed() {
	    return false;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.location_map);
	    
	    // get parameters passed from GetLocationMap activity
	    Bundle bundle = getIntent().getExtras();
	    if (bundle != null){
	    	restaurantName = (String) bundle.getCharSequence("restaurantName");
	    	restaurantEmail = (String) bundle.getCharSequence("restaurantEmail");
	    	restaurantPhone = (String) bundle.getCharSequence("restaurantPhone");
	    	restaurantReviewText = (String) bundle.getCharSequence("restaurantReviewText");
	    	wheatRating = bundle.getFloat("wheatRating"); 
	    	glutenRating = bundle.getFloat("glutenRating",glutenRating);
	    	dairyRating = bundle.getFloat("dairyRating",dairyRating);
	    	nutRating = bundle.getFloat("nutRating",nutRating);
	    	overallRating = bundle.getFloat("overallRating",overallRating);
	    }
		
	    mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    
	    myMapController = mapView.getController();
	    myMapController.setZoom(15);
        
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

 		Criteria criteria = new Criteria();
 		bestProvider = locationManager.getBestProvider(criteria, false);
 		Location location = locationManager.getLastKnownLocation(bestProvider);


        locationListener = new MyLocationListener();

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                locationListener);
        
        //Get the current location in start-up
        if (location != null) {
        	markerGeoPoint = new GeoPoint(
               (int)(location.getLatitude()*1000000),
               (int)(location.getLongitude()*1000000));
        }
        myMapController.animateTo(markerGeoPoint);
        
        latitude = markerGeoPoint.getLatitudeE6();
        longitude = markerGeoPoint.getLongitudeE6();
        
        Drawable marker=getResources().getDrawable(R.drawable.red_marker);
        
        marker.setBounds(0, 0, marker.getIntrinsicWidth(),
                                marker.getIntrinsicHeight());
        
        mapView.getOverlays().add(new MarkerLocations(marker, markerGeoPoint));

	}
	
	/** Register for the updates when Activity is in foreground */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(bestProvider, 10000, 15, locationListener);
	}

	/** Stop the updates when Activity is paused */
	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(locationListener);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
      if (keyCode == KeyEvent.KEYCODE_Z) {
        mapView.displayZoomControls(true);
        return(true);
      }
      
      return(super.onKeyDown(keyCode, event));
    }

	public void onClick(View v) {
		switch(v.getId()) {
        case R.id.save_location_button:
        	String addressString = "";
            
            try {
            	
            	Geocoder gc = new Geocoder(GetLocationMap.this, Locale.getDefault());
            	
            	double lat = latitude/1E6;
            	double lng = longitude/1E6;
                List<Address> addresses = gc.getFromLocation(lat, lng, 1);
                StringBuilder sb = new StringBuilder();

                if (addresses.size() > 0) {
                    Address address = addresses.get(0);

                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
                        sb.append(address.getAddressLine(i)).append("\n");
 
                }
                addressString = sb.toString();
            } catch (IOException e) {
            }
            Intent intent = new Intent(GetLocationMap.this, ReviewRestaurant.class);
			  	Bundle bundle = new Bundle();
			  	bundle.putInt("longitude", longitude);
			  	bundle.putInt("latitude", latitude);
			  	bundle.putCharSequence("address", addressString);
			  	bundle.putCharSequence("restaurantName", restaurantName);
				bundle.putCharSequence("restaurantPhone", restaurantPhone);
				bundle.putCharSequence("restaurantEmail", restaurantEmail);
				bundle.putCharSequence("restaurantReview", restaurantReviewText);
				bundle.putFloat("wheatRating",wheatRating);
				bundle.putFloat("glutenRating",glutenRating);
				bundle.putFloat("dairyRating",dairyRating);
				bundle.putFloat("nutRating",nutRating);
				bundle.putFloat("overallRating",overallRating);
			  	intent.putExtras(bundle);
			  	GetLocationMap.this.startActivity(intent);
			  	finish();
        break;
        case R.id.back_button:
        	finish();
        break;
		}
		
	}
	
    
   class MarkerLocations extends ItemizedOverlay<OverlayItem>{
	   
	   	private List<OverlayItem> locations = new ArrayList<OverlayItem>();
	   	private Drawable marker;
	   	private String inDrag=null;

	   public MarkerLocations(Drawable defaultMarker, GeoPoint geoPoint) {
		    super(defaultMarker);
		    this.marker=defaultMarker;
		    GeoPoint myPlace = geoPoint;
		    locations.add(new OverlayItem(myPlace , "My Place", "My Place"));
		    populate();
	   }

	   @Override
	   protected OverlayItem createItem(int i) {
		    // TODO Auto-generated method stub
		    return locations.get(i);
	   }

	   @Override
	   public int size() {
		    // TODO Auto-generated method stub
		    return locations.size();
	   }

	   @Override
	   public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		    // TODO Auto-generated method stub
		    super.draw(canvas, mapView, shadow);	    
		    boundCenterBottom(marker);
	   }
	   
	   @Override
	    public boolean onTouchEvent(MotionEvent event, MapView mapView) {
	      final int action=event.getAction();
	      final int x=(int)event.getX();
	      final int y=(int)event.getY();
	      boolean result=false;
	      final GeoPoint point = mapView.getProjection().fromPixels((int) event.getX(),(int) event.getY());
	      
	      
	      if (action==MotionEvent.ACTION_DOWN) {
	        for (OverlayItem item : locations) {
	          Point p=new Point(0,0);
	          
	          mapView.getProjection().toPixels(item.getPoint(), p);
	          
	          if (hitTest(item, marker, x-p.x, y-p.y)) {
	            result=true;
	            for (int i=0; i < locations.size(); i++){
    		  		locations.remove(i);
    		  	}
	            inDrag="1";	            
	            break;
	          }
	        }
	      }
	      else if (action==MotionEvent.ACTION_MOVE && inDrag!=null) {			    
	        	result=true;
	      }
	      else if (action==MotionEvent.ACTION_UP && inDrag!=null) {
	    	  	Drawable marker=getResources().getDrawable(R.drawable.red_marker);
	    		
			    marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());

		        mapView.getOverlays().add(new MarkerLocations(marker, point));
			    myMapController.setCenter(point);
		        System.out.println(mapView.getOverlays().size());
		        System.out.println(locations.size());
		        latitude = point.getLatitudeE6();
			    longitude = point.getLongitudeE6();
		        
		        inDrag=null;
		        result=true;
	      }
	      
	      return(result || super.onTouchEvent(event, mapView));
	    }

   }
   
}

