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
    private Button accountBtn;
    private Button loginBtn;
    private Button reviewBtn;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_buttons);
		mapBtn = (Button)findViewById(R.id.map_button);
		mapBtn.setOnClickListener(this);
		listBtn = (Button)findViewById(R.id.list_button);
		listBtn.setOnClickListener(this);
		accountBtn = (Button)findViewById(R.id.account_button);
		accountBtn.setOnClickListener(this);
		loginBtn = (Button)findViewById(R.id.login_button);
		loginBtn.setOnClickListener(this);
		reviewBtn = (Button)findViewById(R.id.review_button);
		reviewBtn.setOnClickListener(this);
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
	        case R.id.account_button:
	        	Intent accountIntent = new Intent(view.getContext(), MyAccount.class);
                startActivityForResult(accountIntent, 0);
	        break;
	        case R.id.login_button:
	        	Intent loginIntent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(loginIntent, 0);
	        break;
	        case R.id.review_button:
	        	Intent reviewIntent = new Intent(view.getContext(), ReviewRestaurant.class);
                startActivityForResult(reviewIntent, 0);
	        break;
		}

	}

	

}
