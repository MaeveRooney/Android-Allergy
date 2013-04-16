package com.example.allergymapapp.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.allergymapapp.DatabaseHandler;
import com.example.allergymapapp.MyAccount;
import com.example.allergymapapp.R;
import com.jayway.android.robotium.solo.Solo;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MyAccountTest extends ActivityInstrumentationTestCase2<MyAccount> {

	private Activity mActivity;
	private Button btnUsername;
	private Button btnEmail;
	private Button btnPassword;
	private Button btnSaveChanges;
	private TextView usernameText;
	private TextView emailText;
	private CheckBox wheatCheck;
	private CheckBox glutenCheck;
	private CheckBox dairyCheck;
	private CheckBox nutCheck;
	private DatabaseHandler db;
	private Solo solo;
	
	public MyAccountTest() {
		  super("com.example.allergymapapp",MyAccount.class);
	}

	@Override
	  protected void setUp() throws Exception {
		
		solo = new Solo(getInstrumentation(), getActivity());

	    setActivityInitialTouchMode(false);

	    mActivity = getActivity();

	    usernameText = (TextView) mActivity.findViewById(R.id.registerName);
        emailText = (TextView) mActivity.findViewById(R.id.registerEmail);
        btnUsername = (Button) mActivity.findViewById(R.id.name_button);
        btnEmail = (Button) mActivity.findViewById(R.id.email_button);
        btnPassword = (Button) mActivity.findViewById(R.id.password_button);
        btnSaveChanges = (Button) mActivity.findViewById(R.id.btnSaveChanges);
        wheatCheck = (CheckBox) mActivity.findViewById(R.id.wheatCheck);
        glutenCheck = (CheckBox) mActivity.findViewById(R.id.glutenCheck);
        dairyCheck = (CheckBox) mActivity.findViewById(R.id.dairyCheck);
        nutCheck = (CheckBox) mActivity.findViewById(R.id.nutCheck);
        
        db = new DatabaseHandler(mActivity);
        //logs out user
	    db.resetTables();
        
	  } // end of setUp() method definition
	
	@Override
	public void tearDown() throws Exception {
		try {
			this.solo.finalize();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		this.mActivity.finish();
		super.tearDown();
	} // end of tearDown()

	@Test
	public void testCorrectActivity() {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MyAccount.class);
	}
	
	@Test
	public void testUserNotLoggedIn() {
	    //logs out user
	    db.resetTables();
	    getInstrumentation().waitForIdleSync();
	    assertTrue("login dialog is not present!", solo.searchText("No Account"));
	}
	
	@UiThreadTest
	public void testChangePassword() {
		db.resetTables();
		db.addUser("john", "john@john.com", "1", "1","0","1","0");
		getInstrumentation().waitForIdleSync();
		btnPassword.performClick();
		assertTrue("password dialog is not present", solo.searchText("Change Password:"));
	}

}
