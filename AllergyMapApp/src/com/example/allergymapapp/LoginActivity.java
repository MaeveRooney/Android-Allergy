package com.example.allergymapapp;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.allergymapapp.R;
 
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputUsername;
    EditText inputPassword;
    TextView loginErrorMsg;
    private DatabaseHandler db;
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_UID = "id";
    private static String KEY_NAME = "username";
    private static String KEY_EMAIL = "email";
    private static String KEY_WHEAT_ALLERGY = "wheatAllergy";
    private static String KEY_GLUTEN_ALLERGY = "glutenAllergy";
    private static String KEY_DAIRY_ALLERGY = "dairyAllergy";
    private static String KEY_NUT_ALLERGY = "nutAllergy";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        db = new DatabaseHandler(LoginActivity.this);
        
        //check to see if logged in already
        if (db.getRowCount() != 0) {
        	final AlertDialog.Builder dialog = new AlertDialog.Builder(LoginActivity.this);
        	
        	dialog.setTitle("Logged In Already");
      	  	dialog.setMessage("You are logged in. To log out click 'Log Out'");
        	
        	dialog.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
	      		  @Override
	      		  public void onClick(DialogInterface arg0, int arg1) {
	      			  db.resetTables();
	      			Context context = getApplicationContext();
                	CharSequence text = "Successful Logout";
                	int duration = Toast.LENGTH_LONG;

                	Toast toast = Toast.makeText(context, text, duration);
                	toast.show();
	      			  arg0.cancel();
	      		  }});
        	
        	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(LoginActivity.this, MainMenu.class);
        			  LoginActivity.this.startActivity(intent);
        		  }});
        	dialog.show();
        }
        // Importing all assets like buttons, text fields
        inputUsername = (EditText) findViewById(R.id.loginUsername);
        inputUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				loginErrorMsg.setText("");			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
        });
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        inputPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				loginErrorMsg.setText("");			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				
			}
        });
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions(LoginActivity.this);
                JSONObject json = userFunction.loginUser(username, password);
                
                if (username.matches("")) {
                	loginErrorMsg.setText("Enter username");
                	return;
                }
                if (password.matches("")) {
                	loginErrorMsg.setText("Enter password");
                	return;
                }
 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
 
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_UID), 
                            		json_user.getString(KEY_WHEAT_ALLERGY), json_user.getString(KEY_GLUTEN_ALLERGY), 
                            		json_user.getString(KEY_DAIRY_ALLERGY),json_user.getString(KEY_NUT_ALLERGY));
                            
                            // Launch Dashboard Screen
                            Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
 
                            // Close all views before launching Dashboard
                            mainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            
                            Context context = getApplicationContext();
		                	CharSequence text = "Successful Login";
		                	int duration = Toast.LENGTH_LONG;

		                	Toast toast = Toast.makeText(context, text, duration);
		                	toast.show();
		                	
                            startActivity(mainMenu);
 
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
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