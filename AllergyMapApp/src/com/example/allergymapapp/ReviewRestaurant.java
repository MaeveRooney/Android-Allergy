package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

import com.example.maptestapp.R;

public class ReviewRestaurant extends Activity{
	final Context context = this;
	int restaurantID= -1;
	EditText restaurantAddress;
	String address = null;
	int longitudeInt = 0;
	int latitudeInt = 0;
	float wheatRating = 0;
	float glutenRating = 0;
	float dairyRating = 0;
	float nutRating = 0;
	EditText restaurantNameEditText;
	EditText restaurantEmailEditText;
	Button locateRestaurant;
	
	// TODO if restaurant id passed as parameter. fill in necessary info
	// i.e. name, location, phone etc.
	// disable those fields
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        //Get ratingbars and text objects from layout
        final RatingBar wheatRatingBar = (RatingBar)findViewById(R.id.ratingbar_wheat);
        final RatingBar glutenRatingBar = (RatingBar)findViewById(R.id.ratingbar_gluten);
        final RatingBar dairyRatingBar = (RatingBar)findViewById(R.id.ratingbar_dairy);
        final RatingBar nutRatingBar = (RatingBar)findViewById(R.id.ratingbar_nut);
        final TextView emailErrorMsg = (TextView)findViewById(R.id.email_error);
        locateRestaurant = (Button)findViewById(R.id.btnLocate);
        restaurantAddress = (EditText) findViewById(R.id.restaurantAddress);
        restaurantNameEditText = (EditText)findViewById(R.id.restaurantName);
        restaurantEmailEditText = (EditText) findViewById(R.id.restaurantEmail);
        restaurantEmailEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (restaurantEmailEditText.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0)
	            {
					
	            }
				else {
					emailErrorMsg.setText("invalid email");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        });
        
        // Set appropriate listener to listen required events
        wheatRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {  	 
    				wheatRating = rating;  	 
    			}
    		});
        glutenRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {    	 
    				glutenRating = rating;    	 
    			}
    		});
        dairyRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {    	 
    				dairyRating = rating;    	 
    			}
    		});
        nutRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {   	 
    				nutRating = rating;    	 
    			}
    		});
        
        
        // get parameters passed from GetLocationMap activity
	    Bundle bundle = getIntent().getExtras();
	    if (bundle != null){
	    	restaurantID = bundle.getInt("restaurantID");
	    	latitudeInt = bundle.getInt("latitude");
	    	longitudeInt = bundle.getInt("longitude");
	    	address = (String) bundle.getCharSequence("address");
	    	if (address != null) {
	        	restaurantAddress.setText(address);
	        } 
	    }
	    
	    if (!(restaurantID == -1)){
	    	locateRestaurant.setEnabled(false);
	    	locateRestaurant.setVisibility(View.GONE);
	    	String id = Integer.toString(restaurantID);
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("id", id));
	        String response = null;
	        String name= "";
	        String email= "";
	        String address= "";
	        String phone = "";
	  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, ReviewRestaurant.this);
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
	  	    String newString = address.replace(",", ",\r\n");
	  	    address = newString;
	  	    restaurantNameEditText.setText(name);
	  	    restaurantAddress.setText(address);
	  	    restaurantEmailEditText.setText(email);
	  	    //phoneText.setText(phone);
	    }
          
	}
	
	public void saveChanges(View v) {
		String name = restaurantNameEditText.getText().toString();
		String email = restaurantEmailEditText.getText().toString();
		String latitude = Integer.toString(latitudeInt);
		String longitude = Integer.toString(longitudeInt);
		String wheat = Float.toString(wheatRating);
		String gluten = Float.toString(glutenRating);
		String dairy = Float.toString(dairyRating);
		String nut = Float.toString(nutRating);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
		// set title
		alertDialogBuilder.setTitle("Incomplete Review");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("Click yes to exit!")
			.setCancelable(false)
			.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		
		if (restaurantID != 1){
			//alter restaurant with new review ratings
		} else {
			if (name.matches("")){
				// show it
				alertDialog.setMessage("please enter restaurant name");
				alertDialog.show();			
			}		
			if (longitude.matches("0")){
				// show it
				alertDialog.setMessage("please locate restaurant on map");
				alertDialog.show();			
			}
			if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && email.length() > 0){
				alertDialog.setMessage("invalid email address");
				alertDialog.show();
			}
			restaurantID = addRestaurantToTable();
			// add restaurant to table and get id back
		}
		//post review to review table
		//add restaurant to restaurant table

	}
	
	
	public void locateRestaurant(View v) {
		Intent intent = new Intent(v.getContext(), GetLocationMap.class);
        startActivityForResult(intent, 0);
	}
	
	
    // Navigation buttons
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
        case R.id.map_button:
        	Intent mapIntent = new Intent(v.getContext(), RestaurantMap.class);
            startActivityForResult(mapIntent, 0);
        break;
        case R.id.back_button:
        	finish();
        break;
		}
    }
    
    private int addRestaurantToTable(){
    	return 1;
    }

}
