package com.example.allergymapapp;

import com.example.maptestapp.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainMenu extends Activity implements OnClickListener {
    private Button mapBtn;
    private Button listBtn;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_buttons);
		mapBtn = (Button)findViewById(R.id.map_button);
		mapBtn.setOnClickListener(this);
		listBtn = (Button)findViewById(R.id.list_button);
		listBtn.setOnClickListener(this);
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
