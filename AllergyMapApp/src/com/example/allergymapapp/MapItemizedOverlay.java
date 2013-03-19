package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	//Context allows access to application-specific resources and classes, 
	//as well as up-calls for application-level operations such as launching activities, 
	//broadcasting and receiving intents, etc.

	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    //populate() is used by superclass ItemizedOverlay, which will read each of the 
	    //OverlayItem objects and prepare them to be drawn.
	    populate();
	}
	
	//When the user taps an item overlaid on the map.
	//The title and snippet of the item are displayed in an AlertDialog
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  final String id = item.getTitle();
	  final String marker = item.getSnippet();
	  String name = "";
	  String rating = "";
	  
	  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
      nameValuePairs.add(new BasicNameValuePair("id", id));
	  
	  String response = null;
	  TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, mContext);
	  try {
		response = httpRequest.execute("http://maeverooney.x10.mx/getOneRestaurant.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    if (response !=null){
		    JSONArray myArray = null;
		    JSONObject restaurantObject = null;
			try {
				myArray = new JSONArray(response);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	try {
	    		restaurantObject = myArray.getJSONObject(0);
			} catch (JSONException e) {
				e.printStackTrace();
			}
	    	if (restaurantObject != null){
	    		try {
					name = restaurantObject.getString("name");
				} catch (JSONException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  }
	    		  try {
			  		rating = restaurantObject.getString("overallRating");
				  } catch (JSONException e) {
					  // TODO Auto-generated catch block
					  e.printStackTrace();
				  }
	    	  }    	
      }
	    
	  dialog.setTitle(name);
	  dialog.setMessage(rating+" stars");
	  
	  dialog.setPositiveButton("More Info", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface arg0, int arg1) {
			  Intent intent = new Intent(mContext, RestaurantDetailView.class);
			  Bundle bundle = new Bundle();
			  bundle.putInt("restaurantID", Integer.parseInt(id));
			  intent.putExtras(bundle);
			  mContext.startActivity(intent);
		  }});
	  
	  dialog.setNeutralButton("Review Restaurant", new DialogInterface.OnClickListener() {		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO go to review restaurant
			//pass restaurant id
			Intent intent = new Intent(mContext, RestaurantDetailView.class);
			Bundle bundle = new Bundle();
			bundle.putInt("restaurantID", Integer.parseInt(id));
			intent.putExtras(bundle);
			mContext.startActivity(intent);
		}});
	  
	  dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {		  
		  @Override     
		  public void onClick(DialogInterface arg0, int arg1) {
			  arg0.cancel();
		  }});
	  dialog.show();
	  return true;
	}
	
	

}
