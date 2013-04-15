package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.allergymapapp.R;

public class ReviewRestaurant extends Activity{
	final Context context = this;
	int restaurantID= 0;
	EditText restaurantAddressEditText;
	String address = null;
	String name = null;
	String phone = null;
	String email = null;
	String reviewText = null;
	String latitude;
	String longitude;
	String userID;
	String password;
	int longitudeInt = 0;
	int latitudeInt = 0;
	float wheatRating = 0;
	float glutenRating = 0;
	float dairyRating = 0;
	float nutRating = 0;
	float overallRating = 0;
	EditText restaurantNameEditText;
	EditText restaurantPhoneEditText;
	EditText restaurantEmailEditText;
	EditText restaurantReviewTextEditText;
	Button locateRestaurant;
	String wheatNum;
	String glutenNum;
	String dairyNum;
	String nutNum;
	String overallNum;	
	String wheat;
	String gluten;
	String dairy;
	String nut;
	String overall;
	String newAverageWheatRating;
	String newAverageGlutenRating;
	String newAverageDairyRating;
	String newAverageNutRating;
	String newAverageOverallRating;
	String userWheat;
	String userGluten;
	String userNut;
	String userDairy;
	Boolean userIsLoggedIn = false;
	
	
	// TODO if restaurant id passed as parameter. fill in necessary info
	// i.e. name, location, phone etc.
	// disable those fields
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_review);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
 
        //User must be logged in to write review
        DatabaseHandler db = new DatabaseHandler(ReviewRestaurant.this);
        //Check if user in sqlite database
        if (db.getRowCount() == 0) {
        	final AlertDialog.Builder dialog = new AlertDialog.Builder(ReviewRestaurant.this);
        	
        	dialog.setTitle("No Account");
      	  	dialog.setMessage("You must log in or register to write a review");
        	
        	dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
	      		  @Override
	      		  public void onClick(DialogInterface arg0, int arg1) {
	      			  Intent intent = new Intent(ReviewRestaurant.this, RegisterActivity.class);
	      			  ReviewRestaurant.this.startActivity(intent);
	      		  }});
        	
        	dialog.setNeutralButton("Login", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(ReviewRestaurant.this, LoginActivity.class);
        			  ReviewRestaurant.this.startActivity(intent);
        		  }});
        	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(ReviewRestaurant.this, MainMenu.class);
        			  ReviewRestaurant.this.startActivity(intent);
        		  }});
        }
        else {
	        //Get user from database
	        HashMap<String,String> user = db.getUserDetails();
	        userID = (String)user.get("uid");
	        userWheat = (String)user.get("wheat");
	        userGluten = (String)user.get("gluten");
	        userDairy = (String)user.get("dairy");
	        userNut = (String)user.get("nut");
        }       
    
        //Get ratingbars and text objects from layout
        TextView addressHeader = (TextView)findViewById(R.id.addressHeader);
        TextView phoneHeader = (TextView)findViewById(R.id.phoneHeader);
        TextView emailHeader = (TextView)findViewById(R.id.emailHeader);
        TextView wheatHeader = (TextView)findViewById(R.id.wheatHeader);
        TextView glutenHeader = (TextView)findViewById(R.id.glutenHeader);
        TextView dairyHeader = (TextView)findViewById(R.id.dairyHeader);
        TextView nutHeader = (TextView)findViewById(R.id.nutHeader);
        final RatingBar wheatRatingBar = (RatingBar)findViewById(R.id.ratingbar_wheat);
        final RatingBar glutenRatingBar = (RatingBar)findViewById(R.id.ratingbar_gluten);
        final RatingBar dairyRatingBar = (RatingBar)findViewById(R.id.ratingbar_dairy);
        final RatingBar nutRatingBar = (RatingBar)findViewById(R.id.ratingbar_nut);
        final RatingBar overallRatingBar = (RatingBar)findViewById(R.id.ratingbar_overall);
        final TextView emailErrorMsg = (TextView)findViewById(R.id.email_error);
        locateRestaurant = (Button)findViewById(R.id.btnLocate);
        restaurantAddressEditText = (EditText) findViewById(R.id.restaurantAddress);
        restaurantNameEditText = (EditText)findViewById(R.id.restaurantName);
        restaurantPhoneEditText = (EditText)findViewById(R.id.restaurantPhone);
        restaurantEmailEditText = (EditText) findViewById(R.id.restaurantEmail);
        restaurantReviewTextEditText = (EditText)findViewById(R.id.reviewText);
        restaurantEmailEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				emailErrorMsg.setText("");
				if (restaurantEmailEditText.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && s.length() > 0)
	            {
					emailErrorMsg.setText("");
	            }
				else if (!restaurantEmailEditText.getText().toString().matches("")){
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
        
        // Set appropriate listener to listen required events
        wheatRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {  	 
    				wheatRating = rating;  	 
    			}
    		});
        glutenRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {    	 
    				glutenRating = rating;    	 
    			}
    		});
        dairyRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {    	 
    				dairyRating = rating;    	 
    			}
    		});
        nutRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {   	 
    				nutRating = rating;    	 
    			}
    		});
        overallRatingBar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
    		public void onRatingChanged(RatingBar ratingBar, float rating,
    				boolean fromUser) {   	 
    				overallRating = rating;    	 
    			}
    		});
        
        //Remove irrelevant ratingbars and headings for user        
        if (userWheat.equals("0")){
        	wheatHeader.setVisibility(View.GONE);
        	wheatRatingBar.setVisibility(View.GONE);
        }
        if (userGluten.equals("0")){
        	glutenHeader.setVisibility(View.GONE);
        	glutenRatingBar.setVisibility(View.GONE);
        }
        if (userDairy.equals("0")){
        	dairyHeader.setVisibility(View.GONE);
        	dairyRatingBar.setVisibility(View.GONE);
        }
        if (userNut.equals("0")){
        	nutHeader.setVisibility(View.GONE);
        	nutRatingBar.setVisibility(View.GONE);
        }
        
        // get parameters passed from GetLocationMap activity
	    Bundle bundle = getIntent().getExtras();
	    if (bundle != null){
	    	restaurantID = bundle.getInt("restaurantID");
	    	latitudeInt = bundle.getInt("latitude");
	    	longitudeInt = bundle.getInt("longitude");
	    	name = (String) bundle.getCharSequence("restaurantName");
	    	email = (String) bundle.getCharSequence("restaurantEmail");
	    	phone = (String) bundle.getCharSequence("restaurantPhone");
	    	reviewText = (String) bundle.getCharSequence("restaurantReviewText");
	    	wheatRating = bundle.getFloat("wheatRating"); 
	    	glutenRating = bundle.getFloat("glutenRating",glutenRating);
	    	dairyRating = bundle.getFloat("dairyRating",dairyRating);
	    	nutRating = bundle.getFloat("nutRating",nutRating);
	    	overallRating = bundle.getFloat("overallRating",overallRating);
	    	address = (String) bundle.getCharSequence("address");
	    	if (address != null) {
	        	restaurantAddressEditText.setText(address);
	        }
	    	if (name != null) {
	        	restaurantNameEditText.setText(name);
	        } 
	    	if (phone != null) {
	        	restaurantPhoneEditText.setText(phone);
	        } 
	    	if (email != null) {
	        	restaurantEmailEditText.setText(email);
	        }
	    	if (reviewText != null) {
	        	restaurantReviewTextEditText.setText(reviewText);
	        }
	    	if (wheatRating >0){
	    		wheatRatingBar.setRating(wheatRating);
	    	}
	    	if (glutenRating >0){
	    		glutenRatingBar.setRating(glutenRating);
	    	}
	    	if (dairyRating >0){
	    		dairyRatingBar.setRating(dairyRating);
	    	}
	    	if (nutRating >0){
	    		nutRatingBar.setRating(nutRating);
	    	}
	    	if (overallRating >0){
	    		overallRatingBar.setRating(overallRating);
	    	}
	    }
	    
	    if (restaurantID != 0){
	    	//disable and hide locate on map button
	    	locateRestaurant.setEnabled(false);
	    	locateRestaurant.setVisibility(View.GONE);
	    	String id = Integer.toString(restaurantID);
	    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        nameValuePairs.add(new BasicNameValuePair("id", id));
	        String response = null;
	        String name= "";
	        String email= "";
	        String address= "";
	        String phone = "";
	  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, ReviewRestaurant.this);
	  	  	try {
		  		response = httpRequest.execute("http://maeverooney.x10.mx/getOneRestaurant.php").get();
	  		} catch (InterruptedException e3) {
	  			// TODO Auto-generated catch block
	  			e3.printStackTrace();
	  		} catch (ExecutionException e3) {
	  			// TODO Auto-generated catch block
	  			e3.printStackTrace();
	  		}
	  	    if (response !=null){
	  		    JSONArray myArray = null;
	  		    JSONObject restaurantObject = null;
	  			try {
	  				myArray = new JSONArray(response);
	  			} catch (JSONException e) {
	  				e.printStackTrace();
	  			}
	  	    	try {
	  	    		restaurantObject = myArray.getJSONObject(0);
	  			} catch (JSONException e) {
	  				e.printStackTrace();
	  			}
	  	    	if (restaurantObject != null){
	  	    		try {
	  					name = restaurantObject.getString("name");
	  				} catch (JSONException e) {
	  					  // TODO Auto-generated catch block
	  					  e.printStackTrace();
	  				}
	  	    		try {
	  	    			address+= restaurantObject.getString("address");
	  	    		} catch (JSONException e) {
	  	    			// TODO Auto-generated catch block
	  	    			e.printStackTrace();
	  	    		}
	  	    		try {
	  	    			phone+= restaurantObject.getString("phone");
	  	    		} catch (JSONException e) {
	  	    			// TODO Auto-generated catch block
	  	    			e.printStackTrace();
	  	    		}
	  	    		try {
	  	    			email+= restaurantObject.getString("restaurantEmail");
	  	    		} catch (JSONException e) {
	  	    			// TODO Auto-generated catch block
	  	    			e.printStackTrace();
	  	    		}
	  	    	 }    	
	        }
	  	    // Parse address string to add line breaks
	  	    String newString = address.replace(",", ",\r\n");
	  	    address = newString;
	  	    // TODO if fields are null allow user to enter relevant info
	  	    if (!name.matches("null")){
	  	    	restaurantNameEditText.setText(name);
	  	    	restaurantNameEditText.setEnabled(false);
	  	    }
	  	    if (!address.matches("null")){
	  	    	//hide address header and field
	  	    	restaurantAddressEditText.setVisibility(View.GONE);
	  	    	addressHeader.setVisibility(View.GONE);
	  	    } 
	  	    if (!email.matches("null")) {
	  	    	//hide email header and field
	  	    	restaurantEmailEditText.setVisibility(View.GONE);
	  	    	emailHeader.setVisibility(View.GONE);
	  	    }
	  	    if (!phone.matches("null")){
	  	    	//hide phone header and field
	  	    	restaurantPhoneEditText.setEnabled(false);
	  	    	restaurantPhoneEditText.setVisibility(View.GONE);
	  	    	phoneHeader.setVisibility(View.GONE);
	  	    }
	    }
          
	}
	
	public void saveChanges(View v) {
		name = restaurantNameEditText.getText().toString();
		email = restaurantEmailEditText.getText().toString();
		phone = restaurantPhoneEditText.getText().toString();
		latitude = Integer.toString(latitudeInt);
		longitude = Integer.toString(longitudeInt);
		wheat = Float.toString(wheatRating);
		gluten = Float.toString(glutenRating);
		dairy = Float.toString(dairyRating);
		nut = Float.toString(nutRating);
		overall = Float.toString(overallRating);
		reviewText = restaurantReviewTextEditText.getText().toString();

		if (restaurantID != 0){
			addReviewToRestaurant();
		} else {
			addRestaurant();
		}
	}
	
	
	public void locateRestaurant(View v) {
		Intent intent = new Intent(v.getContext(), GetLocationMap.class);
		Bundle bundle = new Bundle();
		bundle.putCharSequence("restaurantName", restaurantNameEditText.getText().toString());
		bundle.putCharSequence("restaurantPhone", restaurantPhoneEditText.getText().toString());
		bundle.putCharSequence("restaurantEmail", restaurantEmailEditText.getText().toString());
		bundle.putCharSequence("restaurantReview", restaurantReviewTextEditText.getText().toString());
		bundle.putFloat("wheatRating",wheatRating);
		bundle.putFloat("glutenRating",glutenRating);
		bundle.putFloat("dairyRating",dairyRating);
		bundle.putFloat("nutRating",nutRating);
		bundle.putFloat("overallRating",overallRating);
		intent.putExtras(bundle);
        startActivityForResult(intent, 0);
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
    
    private int addRestaurantToTable(String name, String email, String latitude, String longitude, String phone, String address, String wheat, String gluten, String dairy, String nut, String overall){
    	int id = 0;
    	RestaurantFunctions restaurantFunction = new RestaurantFunctions(ReviewRestaurant.this);
        JSONObject json = restaurantFunction.addRestaurantToDB(name, email, latitude, longitude, phone, address, wheat, gluten, dairy, nut, overall);
        try {
            if (json.getString("success") != null) {
                String res = json.getString("success");
                if(Integer.parseInt(res) == 1){
                    JSONObject json_restaurant = json.getJSONObject("restaurant");
                    id =Integer.parseInt(json_restaurant.getString("id"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    	return id;
    }
    
    private void getCurrentRatingDataFromRestaurant(int restaurantID){
    	int currentWheatNumVotes = 0;
    	int currentGlutenNumVotes = 0;
    	int currentDairyNumVotes = 0;
    	int currentNutNumVotes = 0;
    	int currentOverallNumVotes = 0;
    	float currentWheatRating = 0;
    	float currentGlutenRating = 0;
    	float currentDairyRating = 0;
    	float currentNutRating = 0;
    	float currentOverallRating = 0;
    	String id = Integer.toString(restaurantID);
    	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, ReviewRestaurant.this);
  	  	try {
	  		response = httpRequest.execute("http://maeverooney.x10.mx/getOneRestaurant.php").get();
  		} catch (InterruptedException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		} catch (ExecutionException e3) {
  			// TODO Auto-generated catch block
  			e3.printStackTrace();
  		}
  	    if (response !=null){
  		    JSONArray myArray = null;
  		    JSONObject restaurantObject = null;
  			try {
  				myArray = new JSONArray(response);
  			} catch (JSONException e) {
  				e.printStackTrace();
  			}
  	    	try {
  	    		restaurantObject = myArray.getJSONObject(0);
  			} catch (JSONException e) {
  				e.printStackTrace();
  			}
  	    	if (restaurantObject != null){
  	    		try {
  					currentWheatNumVotes = Integer.parseInt(restaurantObject.getString("wheatNumVotes"));  					
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    		try {
  	    			currentGlutenNumVotes = Integer.parseInt(restaurantObject.getString("glutenNumVotes"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentDairyNumVotes = Integer.parseInt(restaurantObject.getString("dairyNumVotes"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentNutNumVotes = Integer.parseInt(restaurantObject.getString("nutNumVotes"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentOverallNumVotes = Integer.parseInt(restaurantObject.getString("overallNumVotes"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  					currentWheatRating = Float.parseFloat(restaurantObject.getString("wheatRating"));
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    		try {
  	    			currentGlutenRating = Float.parseFloat(restaurantObject.getString("glutenRating"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentDairyRating = Float.parseFloat(restaurantObject.getString("dairyRating"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentNutRating = Float.parseFloat(restaurantObject.getString("nutRating"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			currentOverallRating = Float.parseFloat(restaurantObject.getString("overallRating"));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    	 }    	
        }
  	    if (wheatRating > 0){
	    	float n = currentWheatNumVotes*currentWheatRating;
	    	int newNumVotes = currentWheatNumVotes + 1;
	    	float newTotal = n + wheatRating;
	    	float newAverage = newTotal/newNumVotes;
	    	wheatNum = Integer.toString(newNumVotes);
	    	newAverageWheatRating = Float.toString(newAverage);
	    }
  	    if (glutenRating > 0){
	    	float n = currentGlutenNumVotes*currentGlutenRating;
	    	int newNumVotes = currentWheatNumVotes + 1;
	    	float newTotal = n + glutenRating;
	    	float newAverage = newTotal/newNumVotes;
	    	glutenNum = Integer.toString(newNumVotes);
	    	newAverageGlutenRating = Float.toString(newAverage);
	    }
  	    if (dairyRating > 0){
	    	float n = currentDairyNumVotes*currentDairyRating;
	    	int newNumVotes = currentDairyNumVotes + 1;
	    	float newTotal = n + dairyRating;
	    	float newAverage = newTotal/newNumVotes;
	    	dairyNum = Integer.toString(newNumVotes);
	    	newAverageDairyRating = Float.toString(newAverage);
	    }
  	    if (nutRating > 0){
	    	float n = currentNutNumVotes*currentNutRating;
	    	int newNumVotes = currentNutNumVotes + 1;
	    	float newTotal = n + nutRating;
	    	float newAverage = newTotal/newNumVotes;
	    	nutNum = Integer.toString(newNumVotes);
	    	newAverageNutRating = Float.toString(newAverage);
	    }
  	    if (overallRating > 0){
	    	float n = currentOverallNumVotes*currentOverallRating;
	    	int newNumVotes = currentOverallNumVotes + 1;
	    	float newTotal = n + dairyRating;
	    	float newAverage = newTotal/newNumVotes;
	    	overallNum = Integer.toString(newNumVotes);
	    	newAverageOverallRating = Float.toString(newAverage);
	    }
  	    
  	    
    }
    
    public void addReviewToRestaurant(){
    	AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
    	alert2.setTitle("Enter Your Password:");
    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    	LinearLayout layout = new LinearLayout(ReviewRestaurant.this);
    	layout.setOrientation(LinearLayout.VERTICAL);

    	final EditText passwordText = new EditText(ReviewRestaurant.this);
    	passwordText.setHint("password");
    	
    	layout.addView(passwordText);
   	
    	alert2.setView(layout);
    	alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog, int whichButton) {
				password = passwordText.getText().toString();
				UserFunctions userFunction = new UserFunctions(ReviewRestaurant.this);
		        JSONObject json = userFunction.checkPassword(userID, password);

		        // check for login response
		        try {
		            if (json.getString("success") != null) {
		                String res = json.getString("success");
		                if(Integer.parseInt(res) == 1){
		                	//alter restaurant with new review ratings
		    				getCurrentRatingDataFromRestaurant(restaurantID);
		    				RestaurantFunctions restaurantFunction = new RestaurantFunctions(ReviewRestaurant.this);
		    		        restaurantFunction.addReviewToRestaurant(Integer.toString(restaurantID), newAverageWheatRating, wheatNum, newAverageGlutenRating, glutenNum, newAverageDairyRating, dairyNum, newAverageNutRating, nutNum, newAverageOverallRating, overallNum);
		    		        restaurantFunction.addReviewToDB(userID, Integer.toString(restaurantID), wheat, gluten, dairy, nut, overall, reviewText);
		    		        Intent menuIntent = new Intent(ReviewRestaurant.this, MainMenu.class);
		    		        startActivityForResult(menuIntent, 0);
		    		        Context context = getApplicationContext();
		                	CharSequence text = "Review Added";
		                	int duration = Toast.LENGTH_LONG;

		                	Toast toast = Toast.makeText(context, text, duration);
		                	toast.show();
		                }else{
		                	Context context = getApplicationContext();
		                	CharSequence text = "incorrect password";
		                	int duration = Toast.LENGTH_LONG;

		                	Toast toast = Toast.makeText(context, text, duration);
		                	toast.show();
		                }
		            }
		        } catch (JSONException e) {
		            e.printStackTrace();
		        }
    	   } 
    	});
    	alert2.setNegativeButton("Cancel",
    	  new DialogInterface.OnClickListener() {
    	   public void onClick(DialogInterface dialog,int whichButton) {
    	     dialog.cancel();
    	   }
    	});
    	AlertDialog alertDialog2 = alert2.create();
    	alertDialog2.show();       
    }
    
    public void addRestaurant(){
    	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
    	 
		// set title
		alertDialogBuilder.setTitle("Incomplete Review");
 
			// set dialog message
		alertDialogBuilder
			.setMessage("Click yes to exit!")
			.setCancelable(false)
			.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, just close
					// the dialog box and do nothing
						dialog.cancel();
					}
				});
 
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		if (name.matches("")){
			// show it
			alertDialog.setMessage("Please enter restaurant name");
			alertDialog.show();			
		} else if (longitude.matches("0")){
			// show it
			alertDialog.setMessage("Please locate restaurant on map");
			alertDialog.show();			
		} else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+") && email.length() > 0){
			alertDialog.setMessage("invalid email address");
			alertDialog.show();
		} else {
	    	AlertDialog.Builder alert2 = new AlertDialog.Builder(this);
	    	alert2.setTitle("Enter Your Password:");
	    	this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
	
	    	LinearLayout layout = new LinearLayout(ReviewRestaurant.this);
	    	layout.setOrientation(LinearLayout.VERTICAL);
	
	    	final EditText passwordText = new EditText(ReviewRestaurant.this);
	    	passwordText.setHint("password");
	    	
	    	layout.addView(passwordText);
	   	
	    	alert2.setView(layout);
	    	alert2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	   public void onClick(DialogInterface dialog, int whichButton) {
					password = passwordText.getText().toString();
					UserFunctions userFunction = new UserFunctions(ReviewRestaurant.this);
			        JSONObject json = userFunction.checkPassword(userID, password);
	
			        // check for login response
			        try {
			            if (json.getString("success") != null) {
			                String res = json.getString("success");
			                if(Integer.parseInt(res) == 1){
			                	restaurantID = addRestaurantToTable(name, email, latitude, longitude, phone, address, wheat, gluten, dairy, nut, overall);			
			                	RestaurantFunctions restaurantFunction = new RestaurantFunctions(ReviewRestaurant.this);        
			                	restaurantFunction.addReviewToDB(userID, Integer.toString(restaurantID), wheat, gluten, dairy, nut, overall, reviewText);
			    		        Intent menuIntent = new Intent(ReviewRestaurant.this, MainMenu.class);
			    		        startActivityForResult(menuIntent, 0);
			    		        Context context = getApplicationContext();
			                	CharSequence text = "Review Added";
			                	int duration = Toast.LENGTH_LONG;
	
			                	Toast toast = Toast.makeText(context, text, duration);
			                	toast.show();
			                }else{
			                	Context context = getApplicationContext();
			                	CharSequence text = "incorrect password";
			                	int duration = Toast.LENGTH_LONG;
	
			                	Toast toast = Toast.makeText(context, text, duration);
			                	toast.show();
			                }
			            }
			        } catch (JSONException e) {
			            e.printStackTrace();
			        }
	    	   } 
	    	});
	    	alert2.setNegativeButton("Cancel",
	    	  new DialogInterface.OnClickListener() {
	    	   public void onClick(DialogInterface dialog,int whichButton) {
	    	     dialog.cancel();
	    	   }
	    	});
	    	AlertDialog alertDialog2 = alert2.create();
	    	alertDialog2.show(); 
		}
    }

}
