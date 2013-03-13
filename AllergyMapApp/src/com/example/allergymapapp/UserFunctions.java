package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
 
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.content.Context;
 
public class UserFunctions {
 
	private Context mContext;
	
	private static String loginURL = "http://maeverooney.x10.mx/userGetAndPost.php";
    private static String registerURL = "http://maeverooney.x10.mx/userGetAndPost.php";
 
    private static String login_tag = "login";
    private static String register_tag = "register";
 
    // constructor
    public UserFunctions(Context context){
    	mContext = context;
    }
 
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(loginURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return json;
    }
 
    /**
     * function make Login Request
     * @param name
     * @param email
     * @param password
     * */
    public JSONObject registerUser(String name, String email, String password, String wheat, String gluten, String dairy, String nut){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("wheat", wheat));
        params.add(new BasicNameValuePair("gluten", gluten));
        params.add(new BasicNameValuePair("dairy", dairy));
        params.add(new BasicNameValuePair("nut", nut));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(registerURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    /**
     * Change user details
     */
    public JSONObject changeUserDetails(String id, String name, String email, String wheat, String gluten, String dairy, String nut){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "changedetails"));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("username", name));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("wheat", wheat));
        params.add(new BasicNameValuePair("gluten", gluten));
        params.add(new BasicNameValuePair("dairy", dairy));
        params.add(new BasicNameValuePair("nut", nut));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(registerURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    /**
     * Change Password
     */
    public JSONObject changePassword(String id, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "changepassword"));
        params.add(new BasicNameValuePair("id", id));
        params.add(new BasicNameValuePair("password", password));
 
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(registerURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
    /**
     * Check is Username and Email already in use
     */
    public JSONObject checkUsername(String username){
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "checkusername"));
        params.add(new BasicNameValuePair("username", username));
        
    	String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(registerURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
    
 public JSONObject checkEmail(String email){
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", "checkemail"));
        params.add(new BasicNameValuePair("email", email));
        
    	String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(params, mContext);
  	  	try {
	  		response = httpRequest.execute(registerURL).get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	  	
        JSONObject json = null;
		try {
			json = new JSONObject(response);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return json;
    }
 
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }
 
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
 
}