package com.example.allergymapapp;

import java.util.HashMap;

import org.json.JSONObject;

import com.example.maptestapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyAccount extends Activity{
	Button btnUsername;
    Button btnEmail;
    Button btnPassword;
    Button btnSaveChanges;
    TextView usernameText;
    TextView emailText;
    CheckBox wheatCheck;
    CheckBox glutenCheck;
    CheckBox dairyCheck;
    CheckBox nutCheck;
    String userId;
    String wheat;
    String gluten;
    String dairy;
    String nut;
    String username;
    String email;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account);
        
        usernameText = (TextView) findViewById(R.id.registerName);
        emailText = (TextView) findViewById(R.id.registerEmail);
        btnUsername = (Button) findViewById(R.id.name_button);
        btnEmail = (Button) findViewById(R.id.email_button);
        btnPassword = (Button) findViewById(R.id.password_button);
        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);
        wheatCheck = (CheckBox) findViewById(R.id.wheatCheck);
        glutenCheck = (CheckBox) findViewById(R.id.glutenCheck);
        dairyCheck = (CheckBox) findViewById(R.id.dairyCheck);
        nutCheck = (CheckBox) findViewById(R.id.nutCheck);      
        
        DatabaseHandler db = new DatabaseHandler(MyAccount.this);
        
        //Check if user in sqlite database
        if (db.getRowCount() == 0) {
        	final AlertDialog.Builder dialog = new AlertDialog.Builder(MyAccount.this);
        	
        	dialog.setTitle("No Account");
      	  	dialog.setMessage("You must log in or register an account");
        	
        	dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
	      		  @Override
	      		  public void onClick(DialogInterface arg0, int arg1) {
	      			  Intent intent = new Intent(MyAccount.this, RegisterActivity.class);
	      			  MyAccount.this.startActivity(intent);
	      		  }});
        	
        	dialog.setNeutralButton("Login", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyAccount.this, LoginActivity.class);
        			  MyAccount.this.startActivity(intent);
        		  }});
        	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyAccount.this, MainMenu.class);
        			  MyAccount.this.startActivity(intent);
        		  }});
        }
        else {
	        //Get user from database
	        HashMap<String,String> user = db.getUserDetails();
	        
	        usernameText.setText(user.get("username"));
	        emailText.setText(user.get("email"));
	        userId = user.get("uid");
	        wheat = user.get("wheat");
	        gluten = user.get("gluten");
	        dairy = user.get("dairy");
	        nut = user.get("nut");
	        if (wheat == "1"){
	        	wheatCheck.setChecked(true);
	        }
	        if (gluten == "1"){
	        	glutenCheck.setChecked(true);
	        }
	        if (dairy == "1"){
	        	dairyCheck.setChecked(true);
	        }
	        if (nut == "1"){
	        	nutCheck.setChecked(true);
	        }
        }       
    }
    
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();       
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.wheatCheck:
                if (checked)
                    wheat = "1";
                else
                    wheat = "0";
                break;
            case R.id.glutenCheck:
                if (checked)
                    gluten = "1";
                else
                    gluten = "0";
                break;
            case R.id.dairyCheck:
                if (checked)
                    dairy = "1";
                else
                    dairy = "0";
                break;
            case R.id.nutCheck:
                if (checked)
                    nut = "1";
                else
                    nut = "0";
                break;
        }
    }
    
    public void changePassword(View v) {
    	LinearLayout layout = new LinearLayout(MyAccount.this);
    	layout.setOrientation(LinearLayout.VERTICAL);

    	final EditText currentPassword = new EditText(MyAccount.this);
    	currentPassword.setHint("Current Password");
    	layout.addView(currentPassword);

    	final EditText newPassword = new EditText(MyAccount.this);
    	newPassword.setHint("New Password");
    	layout.addView(newPassword);

    	final EditText confirmPassword = new EditText(MyAccount.this);
    	confirmPassword.setHint("Confirm New Password");
    	layout.addView(confirmPassword);
    	
    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setView(layout);
    	alert.setTitle("Change Password:").setPositiveButton("Save",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,
    	     int whichButton) {

    	    /* User clicked OK so do some stuff */
    	   }
    	  }).setNegativeButton("Cancel",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,
    	     int whichButton) {
    	     /*
    	     * User clicked cancel so do some stuff
    	     */
    	   }
    	  });
    	alert.show();
    }
    
    public void saveChanges() {
    	UserFunctions userFunction = new UserFunctions(MyAccount.this);
        JSONObject json = userFunction.changeUserDetails(userId, username, email, wheat, gluten, dairy, nut);

    }
    
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
