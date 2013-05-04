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

import com.example.allergymapapp.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class MyReviews extends ListActivity {
	String userID;
	String KEY_ERROR = "error";
	String KEY_SUCCESS = "success";
	String restaurantName;
	String restaurantOverallRating;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_reviews_list_view);
        
      //User must be logged in to write review
        DatabaseHandler db = new DatabaseHandler(MyReviews.this);
        //Check if user in sqlite database
        if (db.getRowCount() == 0) {
        	final AlertDialog.Builder dialog = new AlertDialog.Builder(MyReviews.this);
        	
        	dialog.setTitle("No Account");
      	  	dialog.setMessage("You must log in or register to view your reviews");
        	
        	dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
	      		  @Override
	      		  public void onClick(DialogInterface arg0, int arg1) {
	      			  Intent intent = new Intent(MyReviews.this, RegisterActivity.class);
	      			MyReviews.this.startActivity(intent);
	      		  }});
        	
        	dialog.setNeutralButton("Login", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyReviews.this, LoginActivity.class);
        			  MyReviews.this.startActivity(intent);
        		  }});
        	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyReviews.this, MainMenu.class);
        			  MyReviews.this.startActivity(intent);
        		  }});
        	dialog.show();
        }
        else {
	        //Get user from database
	        HashMap<String,String> user = db.getUserDetails();
	        userID = (String)user.get("uid");
        } 
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        
        // get users favourites from db
		UserFunctions restaurantFunction = new UserFunctions(MyReviews.this);
        JSONObject json = restaurantFunction.getUsersReviews(userID);	        

	    
	    // check for favourites from json response
	    try {
            if (json.getString(KEY_SUCCESS) != null) {
                String res = json.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                	JSONArray myArray = null;
                	myArray = json.getJSONArray("array");
                	try{
                	  	
        		        for(int i=0;i<myArray.length();i++){						
        					HashMap<String, String> map = new HashMap<String, String>();	
        					JSONObject e = myArray.getJSONObject(i);
        					String restaurantID = e.getString("restaurantID");
        					getRestaurantDetails(restaurantID);
        					map.put("restaurantID", restaurantID);
        					map.put("name", "name: " + restaurantName);
        		        	map.put("reviewText", "Review: " +  e.getString("reviewText"));
        		        	mylist.add(map);			
        				}		
        	        }catch(JSONException e)        {
        	        	 Log.e("log_tag", "Error parsing data "+e.toString());
        	        }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	    // no favourites
        try{
            if (json.getString(KEY_ERROR) != null) {
                String res = json.getString(KEY_ERROR);
                if(Integer.parseInt(res) == 1){
                	// TODO alert dialog no favourites
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        //List Adapter
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.my_reviews_list_item, 
                        new String[] { "name", "reviewText" }, 
                        new int[] { R.id.item_title, R.id.item_subtitle });
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
        		@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
        		Intent intent = new Intent(MyReviews.this, RestaurantDetailView.class);
  			  	Bundle bundle = new Bundle();
  			  	bundle.putInt("restaurantID", Integer.parseInt(o.get("restaurantID")));
  			  	intent.putExtras(bundle);
  			  	MyReviews.this.startActivity(intent);
			}
		});
    }
    
	public void onClick(View v) {
		switch(v.getId()) {
    	case R.id.map_button:
    		Intent mapIntent = new Intent(v.getContext(), RestaurantMap.class);
            startActivityForResult(mapIntent, 0);
        break;
        case R.id.menu_button:
        	Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
        case R.id.back_button:
        	finish();
        break;
        case R.id.list_button:
        	Intent listIntent = new Intent(v.getContext(), ListRestaurants.class);
            startActivityForResult(listIntent, 0);
        break;
		}		
	}
	
	private void getRestaurantDetails(String id) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
         
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, MyReviews.this);
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
  					restaurantName = restaurantObject.getString("name");
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    		try {
  	    			restaurantOverallRating = restaurantObject.getString("overallRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    	 }    	
        }
	}
}
