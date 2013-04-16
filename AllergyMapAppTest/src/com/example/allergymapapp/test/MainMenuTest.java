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
import android.widget.TextView;

public class MainMenuTest extends ActivityInstrumentationTestCase2<MainMenu> {

	private Activity mActivity;
    private Button mapBtn;
    private Button listBtn;
    private Button accountBtn;
    private Button loginBtn;
    private Button registerBtn;
    private Button reviewBtn;
    private Button myReviewsBtn;
    private Button favouritesBtn;
	private Solo solo;
	
	public MainMenuTest() {
		  super("com.example.allergymapapp", MainMenu.class);
	}

	@Override
	  protected void setUp() throws Exception {
		
		solo = new Solo(getInstrumentation(), getActivity());

	    setActivityInitialTouchMode(false);

	    mActivity = getActivity();

	    mapBtn = (Button)mActivity.findViewById(R.id.map_button);
		listBtn = (Button)mActivity.findViewById(R.id.list_button);
		accountBtn = (Button)mActivity.findViewById(R.id.account_button);
		loginBtn = (Button)mActivity.findViewById(R.id.login_button);
		registerBtn = (Button)mActivity.findViewById(R.id.register_button);
		reviewBtn = (Button)mActivity.findViewById(R.id.review_button);
		favouritesBtn = (Button)mActivity.findViewById(R.id.favourites_button);
		myReviewsBtn = (Button)mActivity.findViewById(R.id.my_reviews_button);

        
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
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
	}
	
	@Test
	public void testGoToMyAccount() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	accountBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MyAccount.class);
	}
	
	@Test
	public void testGoToRestaurantMao() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	mapBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", RestaurantMap.class);
	}
	
	@Test
	public void testGoToRestaurantList() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	listBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", ListRestaurants.class);
	}
	
	@Test
	public void testGoToLoginActivity() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	loginBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", LoginActivity.class);
	}
	
	@Test
	public void testGoToRegisterActivity() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	registerBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", RegisterActivity.class);
	}
	
	@Test
	public void testGoToMyFavourites() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	favouritesBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MyFavouriteRestaurants.class);
	}
	
	
	@Test
	public void testGoToMyReviews() throws Throwable {
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MainMenu.class);
		runTestOnUiThread(new Runnable() {
		    @Override
		    public void run() {
		    	myReviewsBtn.performClick();
		    }
		  });		
		getInstrumentation().waitForIdleSync();
		solo.assertCurrentActivity("wrong activiy", MyReviews.class);
	}

}
