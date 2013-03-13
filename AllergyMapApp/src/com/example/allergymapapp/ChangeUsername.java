package com.example.allergymapapp;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.maptestapp.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeUsername extends Activity{
	Button btnChange;
    EditText changeNameEmail;
    TextView changeErrorMsg;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_name_email);
        
     // Importing all assets like buttons, text fields
        changeNameEmail = (EditText) findViewById(R.id.registerName);
        changeNameEmail.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				UserFunctions userFunction = new UserFunctions(ChangeUsername.this);
				JSONObject json = userFunction.checkUsername(changeNameEmail.getText().toString());
				try {
					if (json.getString("error") != null) {
					    String res = json.getString("error");
					    if(Integer.parseInt(res) == 3){
					    	changeErrorMsg.setText("Username unavailable");
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
				changeErrorMsg.setText("");				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
        });
    }

}
