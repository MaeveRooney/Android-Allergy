package com.example.allergymapapp;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.allergymapapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	        username = (String)user.get("username");
	        email = (String)user.get("email");
	        userId = (String)user.get("uid");
	        wheat = (String)user.get("wheat");
	        gluten = (String)user.get("gluten");
	        dairy = (String)user.get("dairy");
	        nut = (String)user.get("nut");
	        usernameText.setText(username);
	        emailText.setText(email);
	        
	        if (wheat.equals("1")){
	        	wheatCheck.setChecked(true);
	        }
	        if (gluten.equals("1")){
	        	glutenCheck.setChecked(true);
	        }
	        if (dairy.equals("1")){
	        	dairyCheck.setChecked(true);
	        }
	        if (nut.equals("1")){
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
    
    
    // Alertdialog to prompt user to enter new username
    // checks dynamically to see is username is available
    public void changeUsername(View v) {
    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Change Username:");
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    	LinearLayout layout = new LinearLayout(MyAccount.this);
    	layout.setOrientation(LinearLayout.VERTICAL);

    	final EditText newUsername = new EditText(MyAccount.this);
    	newUsername.setHint("new Username");
    	
    	layout.addView(newUsername);

    	
    	alert.setView(layout).setPositiveButton("Save", new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog, int whichButton) {
    		   UserFunctions userFunction = new UserFunctions(MyAccount.this);
				JSONObject json = userFunction.checkUsername(newUsername.getText().toString());
				try {
					if (json.getString("error") != null) {
					    String res = json.getString("error");
					    if(Integer.parseInt(res) == 3){
					    	CharSequence text = "Username Unavailable";
				            int duration = Toast.LENGTH_LONG;
				            Toast toast = Toast.makeText(MyAccount.this, text, duration);
				            toast.show();
					    }
					    else {
							username = newUsername.getText().toString();
							usernameText.setText(username);
						}
					}
					
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

    	    
    	   } 
    	   }).setNegativeButton("Cancel",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,int whichButton) {
    	     dialog.cancel();
    	   }
    	  });
    	alert.show();
    }
    
    // Alertdialog to prompt user to enter new username
    // checks dynamically to see is username is available
    public void changeEmail(View v) {
    	final AlertDialog.Builder alert = new AlertDialog.Builder(this);
    	alert.setTitle("Change Email Address:");
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    	LinearLayout layout = new LinearLayout(MyAccount.this);
    	layout.setOrientation(LinearLayout.VERTICAL);

    	final EditText newEmail = new EditText(MyAccount.this);
    	newEmail.setHint("New Email Address");
    	
    	layout.addView(newEmail);

    	
    	alert.setView(layout).setPositiveButton("Save", new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog, int whichButton) {
    		   
    		   if (newEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && newEmail.getText().toString().length() > 0)
	            {
					alert.setMessage("");
					UserFunctions userFunction = new UserFunctions(MyAccount.this);
					JSONObject json = userFunction.checkEmail(newEmail.getText().toString());
					try {
						if (json.getString("error") != null) {
						    String res = json.getString("error");
						    if(Integer.parseInt(res) == 2){
						    	CharSequence text = "Email already registered";
					            int duration = Toast.LENGTH_LONG;
					            Toast toast = Toast.makeText(MyAccount.this, text, duration);
					            toast.show();
						    }
						    else {
								email = newEmail.getText().toString();
						        emailText.setText(email);
							}
						}
						
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
				else {
					CharSequence text = "invalid email";
		            int duration = Toast.LENGTH_LONG;
		            Toast toast = Toast.makeText(MyAccount.this, text, duration);
		            toast.show();
				}

    	    
    	   } 
    	   }).setNegativeButton("Cancel",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,int whichButton) {
    	     dialog.cancel();
    	   }
    	  });
    	alert.show();
    }
    
    // enter current password and check if correct
    // compare 2 new passwords to see if match
    // replace password in db with new one
    public void changePassword(View v) {
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

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
    	alert.setView(layout).setTitle("Change Password:");
    	alert.setPositiveButton("Save", new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog, int whichButton) {

    	    /* User clicked OK so do some stuff */
    		   UserFunctions userFunction = new UserFunctions(MyAccount.this);
				JSONObject json = userFunction.checkPassword(userId, currentPassword.getText().toString());
				try {
					if (json.getString("success") != null) {
					    String res = json.getString("success");
					    if(Integer.parseInt(res) == 1){
					    	//password correct
					    	if (confirmPassword.getText().toString().matches(newPassword.getText().toString())){				    	
					    		JSONObject json2 = userFunction.changePassword(userId, newPassword.getText().toString());
					    		if (json2.getString("success") != null) {
								    String res2 = json2.getString("success");
								    if(Integer.parseInt(res2) == 1){
							    		CharSequence text = "Password Changed";
							            int duration = Toast.LENGTH_LONG;
							            Toast toast = Toast.makeText(MyAccount.this, text, duration);
							            toast.show();
								    }
								    else {
								    	CharSequence text = "Password not changed";
							            int duration = Toast.LENGTH_LONG;
							            Toast toast = Toast.makeText(MyAccount.this, text, duration);
							            toast.show();
								    }
					    		}
					    	}
					    	else {
					    		CharSequence text = "New Password did not match";
					            int duration = Toast.LENGTH_LONG;
					            Toast toast = Toast.makeText(MyAccount.this, text, duration);
					            toast.show();
					    	}
					    }
					    else {
					    	CharSequence text = "Incorrect Password";
				            int duration = Toast.LENGTH_LONG;
				            Toast toast = Toast.makeText(MyAccount.this, text, duration);
				            toast.show();
					    }
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    	   }
    	  }).setNegativeButton("Cancel",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,int whichButton) {
    	     dialog.cancel();
    	   }
    	  });
    	alert.show();
    }
    
    public void saveChanges(View v) {
    	UserFunctions userFunction = new UserFunctions(MyAccount.this);
        userFunction.changeUserDetails(userId, username, email, wheat, gluten, dairy, nut);
        //Clear all previous data in database
        DatabaseHandler db = new DatabaseHandler(MyAccount.this);
        userFunction.logoutUser(getApplicationContext());
        db.addUser(username, email, userId, wheat, gluten, dairy, nut);
        
        //Show toast with success text
        CharSequence text = "Changes Saved";
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(MyAccount.this, text, duration);
        toast.show();
        
        Intent menuIntent = new Intent(MyAccount.this, MainMenu.class);
        startActivityForResult(menuIntent, 0);
        finish();
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
