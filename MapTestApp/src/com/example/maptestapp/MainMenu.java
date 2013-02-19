package com.example.maptestapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainMenu extends Activity implements OnClickListener {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_buttons);
		findViewById(R.id.map_button).setOnClickListener(this);
		findViewById(R.id.list_button).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {	
		switch(view.getId()) {
	    	case R.id.map_button:
	    		Intent mapIntent = new Intent(view.getContext(), RestaurantMap.class);
                startActivityForResult(mapIntent, 0);
	        break;
	        case R.id.list_button:
	        	Intent listIntent = new Intent(view.getContext(), ListRestaurants.class);
                startActivityForResult(listIntent, 0);
	        break;
		}

	}
	

}
