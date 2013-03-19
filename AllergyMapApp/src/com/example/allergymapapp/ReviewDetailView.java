package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.maptestapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ReviewDetailView extends Activity{
	
	int restaurantID = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_review_view);
        Bundle bundle = getIntent().getExtras();
    	int reviewID = bundle.getInt("reviewID");
    	String id = Integer.toString(reviewID);
    	
    	TextView reviewText=(TextView)findViewById(R.id.review_info);
    	  
  	  	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
    	
    	String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, ReviewDetailView.this);
  	  	try {
	  		response = httpRequest.execute("http://maeverooney.x10.mx/getOneReview.php").get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	    if (response !=null){
  	    	reviewText.setText(response);
  		    JSONArray myArray = null;
  		    JSONObject reviewObject = null;
  			try {
  				myArray = new JSONArray(response);
  			} catch (JSONException e) {
  				e.printStackTrace();
  			}
  	    	try {
  	    		reviewObject = myArray.getJSONObject(0);
  			} catch (JSONException e) {
  				e.printStackTrace();
  			}
  	    	if (reviewObject != null){
  	    		String id2 = null;
  	    		try {
					id2 = reviewObject.getString("restaurant");
					restaurantID = Integer.parseInt(id2);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
  	    		
  	    	 }    	
        }
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
    	case R.id.back_button:
    		Intent intent = new Intent(ReviewDetailView.this, RestaurantDetailView.class);
		  	Bundle bundle = new Bundle();
		  	bundle.putInt("restaurantID", restaurantID);
		  	intent.putExtras(bundle);
		  	ReviewDetailView.this.startActivity(intent);
        break;
        case R.id.menu_button:
        	Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
		}
		
	}

}
