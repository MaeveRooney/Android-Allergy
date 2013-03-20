package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.content.Context;
 
public class RestaurantFunctions {
 
	private Context mContext;
	
    private static String restaurantURL = "http://maeverooney.x10.mx/restaurantGetAndPost.php";
 
    private static String insert_tag = "restaurant";
    private static String alter_tag = "alter";
    private static String review_tag = "review";
    private static String favourites_tag = "favourites";
 
    // constructor
    public RestaurantFunctions(Context context){
    	mContext = context;
    }
 
    
    public JSONObject addRestaurantToDB(String name, String email, String latitude, String longitude, String phone, String address, String wheat, String gluten, String dairy, String nut, String overall){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", insert_tag));
        params.add(new BasicNameValuePair("name", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("phone", phone));
        params.add(new BasicNameValuePair("latitude", latitude));
        params.add(new BasicNameValuePair("longitude", longitude));
        params.add(new BasicNameValuePair("address", address));
        params.add(new BasicNameValuePair("wheat", wheat));
        params.add(new BasicNameValuePair("gluten", gluten));
        params.add(new BasicNameValuePair("dairy", dairy));
        params.add(new BasicNameValuePair("nut", nut));
        params.add(new BasicNameValuePair("overall", overall));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    public JSONObject addReviewToRestaurant(String id, String wheat, String wheatNum, String gluten, String glutenNum, String dairy, String dairyNum, String nut, String nutNum, String overall, String overallNum){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", alter_tag));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("wheat", wheat));
        params.add(new BasicNameValuePair("wheatNum", wheatNum));
        params.add(new BasicNameValuePair("gluten", gluten));
        params.add(new BasicNameValuePair("glutenNum", glutenNum));
        params.add(new BasicNameValuePair("dairy", dairy));
        params.add(new BasicNameValuePair("dairyNum", dairyNum));
        params.add(new BasicNameValuePair("nut", nut));
        params.add(new BasicNameValuePair("nutNum", nutNum));
        params.add(new BasicNameValuePair("overall", overall));
        params.add(new BasicNameValuePair("overallNum", overallNum));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    public JSONObject addReviewToDB(String userID, String restaurantID, String wheat, String gluten, String dairy, String nut, String overall, String reviewText){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", review_tag));
        params.add(new BasicNameValuePair("userID", userID));
        params.add(new BasicNameValuePair("restaurantID", restaurantID));
        params.add(new BasicNameValuePair("wheat", wheat));
        params.add(new BasicNameValuePair("gluten", gluten));
        params.add(new BasicNameValuePair("dairy", dairy));
        params.add(new BasicNameValuePair("nut", nut));
        params.add(new BasicNameValuePair("overall", overall));
        params.add(new BasicNameValuePair("reviewText", reviewText));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    public JSONObject getUserFavourites(String userID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", favourites_tag));
        params.add(new BasicNameValuePair("userID", userID));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
 
}