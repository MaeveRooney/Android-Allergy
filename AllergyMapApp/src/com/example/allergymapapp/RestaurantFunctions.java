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
 
import android.content.Context;
 
public class RestaurantFunctions {
 
	private Context mContext;
	
    private static String restaurantURL = "http://maeverooney.x10.mx/restaurantGetAndPost.php";
 
    private static String insert_tag = "restaurant";
    private static String alter_tag = "alter";
    private static String review_tag = "review";
    private static String favourites_tag = "favourites";
    private static String favourites_add_tag = "addfavourites";
    private static String favourites_delete_tag = "deletefavourites";
 
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
    
    public JSONObject addToFavourites(String userID, String restaurantID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", favourites_add_tag));
        params.add(new BasicNameValuePair("userID", userID));
        params.add(new BasicNameValuePair("restaurantID", restaurantID));
 
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
    
    public JSONObject removeFromFavourites(String userID, String restaurantID){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", favourites_delete_tag));
        params.add(new BasicNameValuePair("userID", userID));
        params.add(new BasicNameValuePair("restaurantID", restaurantID));
 
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
    
    public JSONObject increaseFavouriteCountOfRestaurant(String restaurantID){
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", restaurantID));
        String numFavourites = null;
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
  	    			numFavourites = restaurantObject.getString("numFavourites");
  	    			if (numFavourites.matches("null")){
  	    				numFavourites = "0";
  	    			}
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    	 }    	
        }
  	    int tempNum = Integer.parseInt(numFavourites);
  	    tempNum++;
  	    numFavourites = tempNum+"";	    
    	
    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "changecount"));
        params.add(new BasicNameValuePair("restaurantID", restaurantID));
        params.add(new BasicNameValuePair("numFavourites", numFavourites));
        
 
        String response2 = null;
  	  	TaskAsyncHttpPost httpRequest2 = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response2 = httpRequest2.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    public JSONObject decreaseFavouriteCountOfRestaurant(String restaurantID){
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", restaurantID));
        String numFavourites = null;
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
  	    			numFavourites = restaurantObject.getString("numFavourites");
  	    			if (numFavourites.matches("null")){
  	    				numFavourites = "0";
  	    			}
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    	 }    	
        }
  	    int tempNum = Integer.parseInt(numFavourites);
  	    tempNum--;
  	    numFavourites = tempNum+"";	    
    	
    	// Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "changecount"));
        params.add(new BasicNameValuePair("restaurantID", restaurantID));
        params.add(new BasicNameValuePair("numFavourites", numFavourites));
        
 
        String response2 = null;
  	  	TaskAsyncHttpPost httpRequest2 = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response2 = httpRequest2.execute(restaurantURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response2);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }

 
}