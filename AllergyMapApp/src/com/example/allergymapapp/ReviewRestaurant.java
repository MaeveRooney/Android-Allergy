package com.example.allergymapapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.maptestapp.R;

public class ReviewRestaurant extends Activity{
	TextView RestaurantAddress;
	String address = null;
	int longitude = 0;
	int latitude = 0;
	
	// TODO if restaurant id passed as parameter. fill in necessary info
	// i.e. name, location, phone etc.
	// disable those fields
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        // get parameters passed from GetLocationMap activity
	    Bundle bundle = getIntent().getExtras();
	    if (bundle != null){
	    	latitude = bundle.getInt("latitude");
	    	longitude = bundle.getInt("longitude");
	    	address = (String) bundle.getCharSequence("address");
	    }

    	
        // Importing all assets like buttons, text fields
        EditText inputName = (EditText) findViewById(R.id.restaurantName);
        RestaurantAddress = (TextView) findViewById(R.id.restaurantAddress);
        
        if (address != null) {
        	RestaurantAddress.setText(address);
        }

        
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

}
