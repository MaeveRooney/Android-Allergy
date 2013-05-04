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

import com.example.allergymapapp.R;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MyFavouriteRestaurants extends ListActivity {
	String userID;
	String KEY_ERROR = "error";
	String KEY_SUCCESS = "success";
	String restaurantName;
	String restaurantOverallRating;
	String restaurantWheatRating;
	String restaurantGlutenRating;
	String restaurantDairyRating;
	String restaurantNutRating;
	boolean isFavourite=false;
	boolean isUser = false;
	boolean wheatAllergy = false;
	boolean glutenAllergy = false;
	boolean dairyAllergy = false;
	boolean nutAllergy = false;
	Button heartButton;
	TextView favouriteText;
	String restIDStr="0";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourite_list_view);
        
      //User must be logged in to write review
        DatabaseHandler db = new DatabaseHandler(MyFavouriteRestaurants.this);
        //Check if user in sqlite database
        if (db.getRowCount() == 0) {
        	final AlertDialog.Builder dialog = new AlertDialog.Builder(MyFavouriteRestaurants.this);
        	
        	dialog.setTitle("No Account");
      	  	dialog.setMessage("You must log in or register to view favourites");
        	
        	dialog.setPositiveButton("Register", new DialogInterface.OnClickListener() {
	      		  @Override
	      		  public void onClick(DialogInterface arg0, int arg1) {
	      			  Intent intent = new Intent(MyFavouriteRestaurants.this, RegisterActivity.class);
	      			MyFavouriteRestaurants.this.startActivity(intent);
	      		  }});
        	
        	dialog.setNeutralButton("Login", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyFavouriteRestaurants.this, LoginActivity.class);
        			  MyFavouriteRestaurants.this.startActivity(intent);
        		  }});
        	dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        		  @Override
        		  public void onClick(DialogInterface arg0, int arg1) {
        			  Intent intent = new Intent(MyFavouriteRestaurants.this, MainMenu.class);
        			  MyFavouriteRestaurants.this.startActivity(intent);
        		  }});
        	dialog.show();
        }
        else {
	        //Get user from database
	        HashMap<String,String> user = db.getUserDetails();
	        userID = (String)user.get("uid");
	        String wheat = (String)user.get("wheat");
	        String gluten = (String)user.get("gluten");
	        String dairy = (String)user.get("dairy");
	        String nut = (String)user.get("nut");
	        
	        isUser=true;
	        if (wheat.equals("1")){
	        	wheatAllergy = true;
	        }
	        if (gluten.equals("1")){
	        	glutenAllergy = true;
	        }
	        if (dairy.equals("1")){
	        	dairyAllergy = true;
	        }
	        if (nut.equals("1")){
	        	nutAllergy = true;
	        }
        } 
        
        
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
        
        // get users favourites from db
		RestaurantFunctions restaurantFunction = new RestaurantFunctions(MyFavouriteRestaurants.this);
        JSONObject json = restaurantFunction.getUserFavourites(userID);	        

	    
	    JSONArray myArray = null;
	    
	    // check for favourites from json response
	    try {
            if (json.getString(KEY_SUCCESS) != null) {
                String res = json.getString(KEY_SUCCESS);
                if(Integer.parseInt(res) == 1){
                	myArray = json.getJSONArray("array");
                	try{
                	  	
        		        for(int i=0;i<myArray.length();i++){						
        					HashMap<String, String> map = new HashMap<String, String>();	
        					JSONObject e = myArray.getJSONObject(i);
        					String restaurantID = e.getString("restaurantID");
        					getRestaurantDetails(restaurantID);
        					map.put("id", restaurantID);
        					map.put("name", "Name: " + restaurantName);
        		        	map.put("rating", restaurantOverallRating);
        		        	map.put("wheatrating", restaurantWheatRating);
        		        	map.put("glutenrating", restaurantGlutenRating);
        		        	map.put("dairyrating", restaurantDairyRating);
        		        	map.put("nutrating", restaurantNutRating);
        		        	mylist.add(map);			
        				}		
        	        }catch(JSONException e)        {
        	        	 Log.e("log_tag", "Error parsing data "+e.toString());
        	        }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
	    // no favourites
        try{
            if (json.getString(KEY_ERROR) != null) {
                String res = json.getString(KEY_ERROR);
                if(Integer.parseInt(res) == 1){
                	// TODO alert dialog no favourites
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        

    	ArrayList<RowModel> list=new ArrayList<RowModel>();
        
    	for (HashMap<String, String> entry : mylist) {
			list.add(new RowModel(entry.get("name"), Float.parseFloat(entry.get("rating")), Float.parseFloat(entry.get("wheatrating")),Float.parseFloat(entry.get("glutenrating")),Float.parseFloat(entry.get("dairyrating")),Float.parseFloat(entry.get("nutrating")),entry.get("id")));
		}
	    

		RatingAdapter adapter = new RatingAdapter(list);
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
    			RowModel o = (RowModel) (((RatingAdapter)getListAdapter()).getItem(position));
        		OpenDialog(Integer.parseInt(o.id),o.label, o.rating+"");
			}
		});
    }
    
    private RowModel getModel(int position) {
		return (RowModel) (((RatingAdapter)getListAdapter()).getItem(position));
	}
    
    @SuppressWarnings("rawtypes")
	class RatingAdapter extends ArrayAdapter {
		@SuppressWarnings("unchecked")
		RatingAdapter(ArrayList list) {
			super(MyFavouriteRestaurants.this, R.layout.list_item, list);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			ViewHolder holder;
			
			if (row==null) {		
				LayoutInflater inflater = getLayoutInflater();
				holder=new ViewHolder();
				row=inflater.inflate(R.layout.list_item, parent, false);
				holder.rate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator);
				holder.wheatrate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator_wheat);
				holder.glutenrate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator_gluten);
				holder.dairyrate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator_dairy);
				holder.nutrate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator_nut);
				holder.title=(TextView) row.findViewById(R.id.item_title);
				holder.wheatRow = (LinearLayout) row.findViewById(R.id.wheatRow);
				if (isUser && !wheatAllergy){
					holder.wheatRow.setVisibility(View.GONE);
				}
				holder.glutenRow = (LinearLayout) row.findViewById(R.id.glutenRow);
				if (isUser && !glutenAllergy){
					holder.glutenRow.setVisibility(View.GONE);
				}
				holder.dairyRow = (LinearLayout) row.findViewById(R.id.dairyRow);
				if (isUser && !dairyAllergy){
					holder.dairyRow.setVisibility(View.GONE);
				}
				holder.nutRow = (LinearLayout) row.findViewById(R.id.nutRow);
				if (isUser && !nutAllergy){
					holder.nutRow.setVisibility(View.GONE);
				}
				row.setTag(holder);
			} else {
				holder=(ViewHolder)row.getTag();
			}
			RowModel model=getModel(position);

			holder.title.setText(model.label);
			holder.rate.setRating(model.rating);
			holder.wheatrate.setRating(model.wheatrating);
			holder.glutenrate.setRating(model.glutenrating);
			holder.dairyrate.setRating(model.dairyrating);
			holder.nutrate.setRating(model.nutrating);
			return(row);
		}
	}
    
    static class ViewHolder {
    	RatingBar rate = null;
		RatingBar wheatrate = null;
		RatingBar glutenrate = null;
		RatingBar dairyrate = null;
		RatingBar nutrate = null;
		TextView title = null;
		LinearLayout wheatRow= null;
		LinearLayout glutenRow= null;
		LinearLayout dairyRow= null;
		LinearLayout nutRow= null;
	    int position;
	}
    
	public void onClick(View v) {
		switch(v.getId()) {
    	case R.id.map_button:
    		Intent mapIntent = new Intent(v.getContext(), RestaurantMap.class);
            startActivityForResult(mapIntent, 0);
        break;
        case R.id.menu_button:
        	Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
        case R.id.back_button:
        	finish();
        break;
        case R.id.list_button:
        	Intent listIntent = new Intent(v.getContext(), ListRestaurants.class);
            startActivityForResult(listIntent, 0);
        break;
		}		
	}
	
	private void getRestaurantDetails(String id) {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
         
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, MyFavouriteRestaurants.this);
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
  					restaurantName = restaurantObject.getString("name");
  				} catch (JSONException e) {
  					  // TODO Auto-generated catch block
  					  e.printStackTrace();
  				}
  	    		try {
  	    			restaurantOverallRating = restaurantObject.getString("overallRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			restaurantWheatRating = restaurantObject.getString("wheatRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			restaurantGlutenRating = restaurantObject.getString("glutenRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			restaurantDairyRating = restaurantObject.getString("dairyRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			restaurantNutRating = restaurantObject.getString("nutRating");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    	 }    	
        }
	}
	
	private void OpenDialog(int restID, String name, String rating){
		  final AlertDialog.Builder dialog = new AlertDialog.Builder(MyFavouriteRestaurants.this);
		  restIDStr = restID+"";
		  final int restaurantID = restID;
		  
	      DatabaseHandler db = new DatabaseHandler(MyFavouriteRestaurants.this);
	      //Check if user in sqlite database
	      if (db.getRowCount() != 0) {
		      //Get user from database
		      HashMap<String,String> user = db.getUserDetails();
		      userID = (String)user.get("uid");
		      //if restaurant in favourites change isFavourite to true
		      isFavourite = CheckIfRestaurantInFavourites(restID, userID);
		      // if user is logged in show favourite button
			  LayoutInflater layout = LayoutInflater.from(MyFavouriteRestaurants.this);
			  View buttonView = layout.inflate(R.layout.heart_button, null);
			  favouriteText = (TextView) buttonView.findViewById(R.id.favouriteText);
			  heartButton = (Button) buttonView.findViewById(R.id.heartButton);
			  heartButton.setOnClickListener(addToFavourites);	
			  if (isFavourite){
				  favouriteText.setText("tap heart to remove from favourites");
				  heartButton.setBackgroundResource(R.drawable.heart_on);
			  }
			  dialog.setView(buttonView);  
	      }
		  
		  dialog.setTitle(name);
		  dialog.setMessage(rating+" stars");
		  
		  dialog.setPositiveButton("More Info", new DialogInterface.OnClickListener() {
			  @Override
			  public void onClick(DialogInterface arg0, int arg1) {
				  Intent intent = new Intent(MyFavouriteRestaurants.this, RestaurantDetailView.class);
				  Bundle bundle = new Bundle();
				  bundle.putInt("restaurantID", restaurantID);
				  intent.putExtras(bundle);
				  MyFavouriteRestaurants.this.startActivity(intent);
			  }});
		  
		  dialog.setNeutralButton("Review Restaurant", new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO go to review restaurant
				//pass restaurant id
				Intent intent = new Intent(MyFavouriteRestaurants.this, ReviewRestaurant.class);
				Bundle bundle = new Bundle();
				bundle.putInt("restaurantID", restaurantID);
				intent.putExtras(bundle);
				MyFavouriteRestaurants.this.startActivity(intent);
			}});
		  
		  dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {		  
			  @Override     
			  public void onClick(DialogInterface arg0, int arg1) {
				  finish();
				  startActivity(getIntent());
			  }});
		  dialog.show();
		}
	
	View.OnClickListener addToFavourites = new View.OnClickListener() {
		  public void onClick(View v) {
			  if (isFavourite){
				  isFavourite = false;
				  favouriteText.setText("tap heart to add to favourites");
				  heartButton.setBackgroundResource(R.drawable.heart_off);
				  // remove from favourites table in db
				  RestaurantFunctions restaurantFunction = new RestaurantFunctions(MyFavouriteRestaurants.this);
			      restaurantFunction.removeFromFavourites(userID, restIDStr);
			      restaurantFunction.decreaseFavouriteCountOfRestaurant(restIDStr);
			} else {
				isFavourite = true;
				favouriteText.setText("tap heart to remove from favourites");
				heartButton.setBackgroundResource(R.drawable.heart_on);
				// add to favourites table in db
				RestaurantFunctions restaurantFunction = new RestaurantFunctions(MyFavouriteRestaurants.this);
		        restaurantFunction.addToFavourites(userID, restIDStr);
		        restaurantFunction.increaseFavouriteCountOfRestaurant(restIDStr);
			}
		  }
	};

	private boolean CheckIfRestaurantInFavourites(int restaurantID, String userID){
		// get users favourites from db
		RestaurantFunctions restaurantFunction = new RestaurantFunctions(MyFavouriteRestaurants.this);
      JSONObject json = restaurantFunction.getUserFavourites(userID);	        
  
	    JSONArray myArray = null;
	    
	    // check for favourites from json response
	    try {
          if (json.getString("success") != null) {
              String res = json.getString("success");
              if(Integer.parseInt(res) == 1){
              	myArray = json.getJSONArray("array");
              	try{
              	  	
      		        for(int i=0;i<myArray.length();i++){	
      					JSONObject e = myArray.getJSONObject(i);
      					String thisRestaurantID = e.getString("restaurantID");
      					if (thisRestaurantID.matches(restaurantID+"")){
      						return true;
      					} 
      				}		
      	        }catch(JSONException e)        {
      	        	 Log.e("log_tag", "Error parsing data "+e.toString());
      	        }
              }
          }
      } catch (JSONException e) {
          e.printStackTrace();
      }
	    return false;
	}
	
	
	class RowModel {
		String label;
		float rating;
		float wheatrating;
		float glutenrating;
		float dairyrating;
		float nutrating;
		String id;
		
		RowModel(String label, float rating, float wheatrating, float glutenrating, float dairyrating, float nutrating, String id) {
			this.label=label;
			this.wheatrating= wheatrating;
			this.glutenrating= glutenrating;
			this.dairyrating= dairyrating;
			this.nutrating= nutrating;
			this.rating= rating;
			this.id = id;
		}
		
	}
}
