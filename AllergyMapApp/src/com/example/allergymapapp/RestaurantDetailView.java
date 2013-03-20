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

import com.example.maptestapp.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RestaurantDetailView extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_view);
        Bundle bundle = getIntent().getExtras();
    	int restaurantID = bundle.getInt("restaurantID");
    	String id = Integer.toString(restaurantID);
    	
    	String name = "";
  	  	String phone = "Phone: ";
  	  	String address = "Address:\r\n";
  	  	String email = "Email: ";
  	  	TextView nameText=(TextView)findViewById(R.id.restaurant_name);
  	  	TextView addressText=(TextView)findViewById(R.id.restaurant_address);
  	  	TextView phoneText=(TextView)findViewById(R.id.restaurant_phone);
  	  	TextView emailText=(TextView)findViewById(R.id.restaurant_email);
  	  
  	  	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
         
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, RestaurantDetailView.this);
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
  	    			address+= restaurantObject.getString("address");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			phone+= restaurantObject.getString("phone");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			email+= restaurantObject.getString("restaurantEmail");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    	 }    	
        }
  	    // Parse address string to add line breaks
  	    // TODO check if null
  	    String newString = address.replace(",", ",\r\n");
  	    address = newString;
  	    nameText.setText(name);
  	    addressText.setText(address);
  	    emailText.setText(email);
  	    phoneText.setText(phone);
           
        String response2 = null;
        TaskAsyncHttpPost request = new TaskAsyncHttpPost(nameValuePairs, RestaurantDetailView.this);
	    try {
			response2 = request.execute("http://maeverooney.x10.mx/getReviews.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	    JSONArray myArray = null;
	    
	    if (response2 !=null){
			try {
				myArray = new JSONArray(response2);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}       
	        try{
	  	
		        for(int i=0;i<myArray.length();i++){						
					HashMap<String, String> map = new HashMap<String, String>();	
					JSONObject e = myArray.getJSONObject(i);
	
					map.put("id",e.getString("id"));
					map.put("rating", "rating: "+e.getString("overallRating")+" stars");
					map.put("text", "text: " + e.getString("text"));
		        	mylist.add(map);			
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
	    }
        
        //List Adapter
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.restaurant_item, 
                        new String[] { "text", "rating" }, 
                        new int[] { R.id.item_title, R.id.item_subtitle });
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
        		@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
        		Intent intent = new Intent(RestaurantDetailView.this, ReviewDetailView.class);
  			  	Bundle bundle = new Bundle();
  			  	bundle.putInt("reviewID", Integer.parseInt(o.get("id")));
  			  	intent.putExtras(bundle);
  			  	RestaurantDetailView.this.startActivity(intent);
			}
		});
    }
    
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back_button:
    		finish();
        break;
    	case R.id.menu_button:
    		Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
        case R.id.map_button:
        	Intent mapIntent = new Intent(v.getContext(), RestaurantMap.class);
            startActivityForResult(mapIntent, 0);
        break;
        case R.id.list_button:
        	Intent listIntent = new Intent(v.getContext(), ListRestaurants.class);
            startActivityForResult(listIntent, 0);
        break;
		}
		
	}
}
