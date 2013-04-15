package com.example.allergymapapp;

import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.allergymapapp.R;
 
public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
 
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
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                UserFunctions userFunction = new UserFunctions(LoginActivity.this);
                JSONObject json = userFunction.loginUser(email, password);
                
                if (email.matches("")) {
                	loginErrorMsg.setText("Enter email address");
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