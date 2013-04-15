package com.example.allergymapapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.allergymapapp.R;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
 
public class RegisterActivity extends Activity {
    Button btnRegister;
    Button btnLinkToLogin;
    EditText inputUsername;
    EditText inputEmail;
    EditText inputPassword;
    EditText confirmPassword;
    TextView usernameErrorMsg;
    TextView emailErrorMsg;
    TextView registerErrorMsg;
    TextView passwordErrorMsg;
    CheckBox wheatCheck;
    CheckBox glutenCheck;
    CheckBox dairyCheck;
    CheckBox nutCheck;
    
    
    
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
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
        setContentView(R.layout.register);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        // Importing all assets like buttons, text fields
        inputUsername = (EditText) findViewById(R.id.registerName);
        inputUsername.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				UserFunctions userFunction = new UserFunctions(RegisterActivity.this);
				JSONObject json = userFunction.checkUsername(inputUsername.getText().toString());
				try {
					if (json.getString(KEY_ERROR) != null) {
					    String res = json.getString(KEY_ERROR);
					    if(Integer.parseInt(res) == 3){
					    	usernameErrorMsg.setText("Username unavailable");
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

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				usernameErrorMsg.setText("");				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        });
        
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (inputEmail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0)
	            {
					emailErrorMsg.setText("");
					UserFunctions userFunction = new UserFunctions(RegisterActivity.this);
					JSONObject json = userFunction.checkEmail(inputEmail.getText().toString());
					try {
						if (json.getString(KEY_ERROR) != null) {
						    String res = json.getString(KEY_ERROR);
						    if(Integer.parseInt(res) == 2){
						    	emailErrorMsg.setText("Email already registered");
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
        
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        confirmPassword.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				if (confirmPassword.getText().toString().matches(inputPassword.getText().toString()) && s.length() > 0)
				{
					passwordErrorMsg.setText("");
				}
				else {
					passwordErrorMsg.setText("Passwords do not match");
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
        
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        usernameErrorMsg = (TextView) findViewById(R.id.username_error);
        emailErrorMsg = (TextView) findViewById(R.id.email_error);
        registerErrorMsg = (TextView) findViewById(R.id.register_error);
        passwordErrorMsg = (TextView) findViewById(R.id.password_error);
        wheatCheck = (CheckBox) findViewById(R.id.wheatCheck);
        glutenCheck = (CheckBox) findViewById(R.id.glutenCheck);
        dairyCheck = (CheckBox) findViewById(R.id.dairyCheck);
        nutCheck = (CheckBox) findViewById(R.id.nutCheck);
        
        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = inputUsername.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String wheat = "0";
                String gluten = "0";
                String dairy = "0";
                String nut = "0";
                
                if (wheatCheck.isChecked() == true) {
                	wheat = "1";
                }
                if (glutenCheck.isChecked() == true) {
                	gluten = "1";
                }
                if (dairyCheck.isChecked() == true) {
                	dairy = "1";
                }
                if (nutCheck.isChecked() == true) {
                	nut = "1";
                }
                UserFunctions userFunction = new UserFunctions(RegisterActivity.this);
                JSONObject json = userFunction.registerUser(name, email, password, wheat, gluten, dairy, nut);
 
                // check for login response
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                        	usernameErrorMsg.setText("");
                        	emailErrorMsg.setText("");
                        	registerErrorMsg.setText("");
                            // user successfully registered
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
 
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json_user.getString(KEY_UID), 
                            		json_user.getString(KEY_WHEAT_ALLERGY), json_user.getString(KEY_GLUTEN_ALLERGY), 
                            		json_user.getString(KEY_DAIRY_ALLERGY),json_user.getString(KEY_NUT_ALLERGY));
                            // Launch Main Menu
                            Intent mainMenu = new Intent(getApplicationContext(), MainMenu.class);
                            // Close all views before launching Main Menu
                            mainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainMenu);
                            // Close Registration Screen
                            finish();
                        }else{
                            // Error in registration
                            registerErrorMsg.setText("Unsuccessful registration");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try{
	                if (json.getString(KEY_ERROR) != null) {
	                    String res = json.getString(KEY_ERROR);
	                    if(Integer.parseInt(res) == 2){
	                    	usernameErrorMsg.setText("");
	                    	emailErrorMsg.setText("Email already registered");
	                    }
	                    if(Integer.parseInt(res) == 3){
	                    	emailErrorMsg.setText("");
	                    	usernameErrorMsg.setText("Username unavailable");
	                    }
	                }
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
            
            }
        });
 
        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
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
