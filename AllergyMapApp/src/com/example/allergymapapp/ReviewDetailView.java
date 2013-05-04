package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.allergymapapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ReviewDetailView extends Activity{
	
	int restaurantID = 0;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_review_view);
        Bundle bundle = getIntent().getExtras();
    	int reviewID = bundle.getInt("reviewID");
    	String restaurantName = (String) bundle.getCharSequence("restaurantName");
    	String id = Integer.toString(reviewID);
    	
    	TextView reviewHeader=(TextView)findViewById(R.id.review_header);
    	TextView authorHeader=(TextView)findViewById(R.id.review_author);
    	TextView reviewText=(TextView)findViewById(R.id.review_text);
    	RatingBar rate=(RatingBar)findViewById(R.id.ratingbar_Indicator);
    	RatingBar wheatrate=(RatingBar)findViewById(R.id.ratingbar_Indicator_wheat);
    	RatingBar glutenrate=(RatingBar)findViewById(R.id.ratingbar_Indicator_gluten);
    	RatingBar dairyrate=(RatingBar)findViewById(R.id.ratingbar_Indicator_dairy);
    	RatingBar nutrate=(RatingBar)findViewById(R.id.ratingbar_Indicator_nut);
    	  
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
  	    		try {
					reviewHeader.setText("Review For: " + restaurantName);
					String authorName = reviewObject.getString("username");
					authorHeader.setText("Author: " + authorName);
					String review = reviewObject.getString("text");
					reviewText.setText(review);
					float rating = Float.parseFloat(reviewObject.getString("overallRating"));
					float wheatrating = Float.parseFloat(reviewObject.getString("wheatRating"));
					float glutenrating = Float.parseFloat(reviewObject.getString("glutenRating"));
					float dairyrating = Float.parseFloat(reviewObject.getString("dairyRating"));
					float nutrating = Float.parseFloat(reviewObject.getString("nutRating"));
					rate.setRating(rating);
					wheatrate.setRating(wheatrating);
					glutenrate.setRating(glutenrating);
					dairyrate.setRating(dairyrating);
					nutrate.setRating(nutrating);
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
