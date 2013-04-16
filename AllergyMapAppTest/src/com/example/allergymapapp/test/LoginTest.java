package com.example.allergymapapp.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.allergymapapp.DatabaseHandler;
import com.example.allergymapapp.ListRestaurants;
import com.example.allergymapapp.LoginActivity;
import com.example.allergymapapp.MainMenu;
import com.example.allergymapapp.MyAccount;
import com.example.allergymapapp.MyFavouriteRestaurants;
import com.example.allergymapapp.MyReviews;
import com.example.allergymapapp.R;
import com.example.allergymapapp.RegisterActivity;
import com.example.allergymapapp.RestaurantMap;
import com.example.allergymapapp.ReviewRestaurant;
import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginTest extends ActivityInstrumentationTestCase2<LoginActivity> {

	private Activity mActivity;
	private Button btnLogin;
	private Button btnLinkToRegister;
	private EditText inputUsername;
	private EditText inputPassword;
	private TextView loginErrorMsg;
    private DatabaseHandler db;
	private Solo solo;
	
	public LoginTest() {
		  super("com.example.allergymapapp", LoginActivity.class);
	}

	@Override
	  protected void setUp() throws Exception {
		
		solo = new Solo(getInstrumentation(), getActivity());

	    setActivityInitialTouchMode(false);

	    mActivity = getActivity();

	    inputUsername = (EditText) mActivity.findViewById(R.id.loginUsername);
	    inputPassword = (EditText) mActivity.findViewById(R.id.loginPassword);
	    btnLogin = (Button) mActivity.findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) mActivity.findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) mActivity.findViewById(R.id.login_error);
        
	  } // end of setUp() method definition
	
	@Override
	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		solo.finishOpenedActivities();
		this.mActivity.finish();
		super.tearDown();
	} // end of tearDown()

	@Test
	public void testCorrectActivity() {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
	}

	@Test
	public void testGoToRegisterActivity() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	btnLinkToRegister.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", RegisterActivity.class);
	}
	
	@Test 
	public void testIncorrectUsername() throws Throwable{
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	inputUsername.setText("johnnySmith");
		    	inputPassword.setText("password");
		    	btnLogin.performClick();
		    }
		  });
		assertEquals("Incorrect username/password",loginErrorMsg.getText().toString());
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
	}
	
	@Test 
	public void testCorrectUsernameAndPassword() throws Throwable{
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	inputUsername.setText("johnSmith");
		    	inputPassword.setText("secret1");
		    	btnLogin.performClick();
		    }
		  });
		assertEquals("",loginErrorMsg.getText().toString());
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
	}

}
