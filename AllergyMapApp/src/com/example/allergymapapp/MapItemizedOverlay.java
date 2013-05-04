package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.HashMap;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.allergymapapp.R;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	Button heartButton;
	TextView favouriteText;
	int isFavourite = 0;
	String userID = "-1";
	String restaurantID = "-1";
	OverlayItem thisItem;
	String goodBadNeutral = "neutral";
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
	  thisItem = mOverlays.get(index);
	  final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  restaurantID = thisItem.getTitle();
	  goodBadNeutral = thisItem.getSnippet();
	  // red, green or heart shaped
	  String name = "";
	  String rating = "";
	  RatingBar ratingBar = null;
	  

	  LayoutInflater layout = LayoutInflater.from(mContext);
	  View buttonView = layout.inflate(R.layout.heart_button, null);
	  favouriteText = (TextView) buttonView.findViewById(R.id.favouriteText);
	  heartButton = (Button) buttonView.findViewById(R.id.heartButton);
	  heartButton.setOnClickListener(addToFavourites);
	  ratingBar = (RatingBar) buttonView.findViewById(R.id.ratingbar_Indicator);
	  dialog.setView(buttonView);
	  
      DatabaseHandler db = new DatabaseHandler(mContext);
      //Check if user in sqlite database
      if (db.getRowCount() != 0) {
	      //Get user from database
	      HashMap<String,String> user = db.getUserDetails();
	      userID = (String)user.get("uid");
	      //if restaurant in favourites change isFavourite to 1
	      checkIfRestaurantInFavourites();
	      // if user is logged in show favourite button
		  	
		  if (isFavourite == 1){
			  favouriteText.setText("tap heart to remove from favourites");
			  heartButton.setBackgroundResource(R.drawable.heart_on);
			  //change marker to heart marker
			  Drawable heartMarker = mContext.getResources().getDrawable(R.drawable.heart_marker);
			  //Set its bounds
			  heartMarker.setBounds(0 - heartMarker.getIntrinsicWidth() / 2, 0 - heartMarker.getIntrinsicHeight(), 
					  heartMarker.getIntrinsicWidth() / 2, 0);//Set the new marker
			  thisItem.setMarker(heartMarker);
		  }  
      } else {
    	  heartButton.setVisibility(View.GONE);
		  favouriteText.setVisibility(View.GONE);
      }
	  List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
      nameValuePairs.add(new BasicNameValuePair("id", restaurantID));
	  
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
	  ratingBar.setRating(Float.parseFloat(rating));
	  
	  dialog.setPositiveButton("More Info", new DialogInterface.OnClickListener() {
		  @Override
		  public void onClick(DialogInterface arg0, int arg1) {
			  Intent intent = new Intent(mContext, RestaurantDetailView.class);
			  Bundle bundle = new Bundle();
			  bundle.putInt("restaurantID", Integer.parseInt(restaurantID));
			  intent.putExtras(bundle);
			  mContext.startActivity(intent);
		  }});
	  
	  dialog.setNeutralButton("Review Restaurant", new DialogInterface.OnClickListener() {		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO go to review restaurant
			//pass restaurant id
			Intent intent = new Intent(mContext, ReviewRestaurant.class);
			Bundle bundle = new Bundle();
			bundle.putInt("restaurantID", Integer.parseInt(restaurantID));
			intent.putExtras(bundle);
			mContext.startActivity(intent);
		}});
	  
	  dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {		  
		  @Override     
		  public void onClick(DialogInterface arg0, int arg1) {
			  arg0.cancel();
		  }});
	  dialog.show();
	  return true;
	}

	View.OnClickListener addToFavourites = new View.OnClickListener() {
		  public void onClick(View v) {
			  if (isFavourite == 1){
				  isFavourite = 0;
				  favouriteText.setText("tap heart to add to favourites");
				  heartButton.setBackgroundResource(R.drawable.heart_off);
				  // remove from favourites table in db
				  RestaurantFunctions restaurantFunction = new RestaurantFunctions(mContext);
			      restaurantFunction.removeFromFavourites(userID, restaurantID);
			      restaurantFunction.decreaseFavouriteCountOfRestaurant(restaurantID);
				  // make marker red or green or neutral
				  Drawable marker = null;
				  if (goodBadNeutral.matches("good")){
					  marker = mContext.getResources().getDrawable(R.drawable.green_marker);
				  }
				  if (goodBadNeutral.matches("bad")){
					  marker = mContext.getResources().getDrawable(R.drawable.red_marker);
				  }
				  else{
					  marker = mContext.getResources().getDrawable(R.drawable.neutral_marker);
				  }
				  //Set its bounds
				  marker.setBounds(0 - marker.getIntrinsicWidth() / 2, 0 - marker.getIntrinsicHeight(), 
							  marker.getIntrinsicWidth() / 2, 0);//Set the new marker
				  thisItem.setMarker(marker);
			} else {
				isFavourite = 1;
				favouriteText.setText("tap heart to remove from favourites");
				heartButton.setBackgroundResource(R.drawable.heart_on);
				// add to favourites table in db
				RestaurantFunctions restaurantFunction = new RestaurantFunctions(mContext);
		        restaurantFunction.addToFavourites(userID, restaurantID);
		        restaurantFunction.increaseFavouriteCountOfRestaurant(restaurantID);
				//change marker to heart marker
				Drawable heartMarker = mContext.getResources().getDrawable(R.drawable.heart_marker);
				//Set its bounds
				heartMarker.setBounds(0 - heartMarker.getIntrinsicWidth() / 2, 0 - heartMarker.getIntrinsicHeight(), 
						  heartMarker.getIntrinsicWidth() / 2, 0);//Set the new marker
				thisItem.setMarker(heartMarker);
			}
		  }
	};
	
	private void checkIfRestaurantInFavourites(){
		// get users favourites from db
				RestaurantFunctions restaurantFunction = new RestaurantFunctions(mContext);
		        JSONObject json = restaurantFunction.getUserFavourites(userID);	        
		    
			    JSONArray myArray = null;
			    
			    // check for favourites from json response
			    try {
		            if (json.getString("success") != null) {
		                String res = json.getString("success");
		                if(Integer.parseInt(res) == 1){
		                	myArray = json.getJSONArray("array");
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
	

}
