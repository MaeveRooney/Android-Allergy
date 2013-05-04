package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

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

public class ListRestaurants extends ListActivity {
	boolean isFavourite=false;
	Button heartButton;
	TextView favouriteText;
	String restIDStr="0";
	String userIDStr="0";
	boolean isUser = false;
	boolean wheatAllergy = false;
	boolean glutenAllergy = false;
	boolean dairyAllergy = false;
	boolean nutAllergy = false;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        
        DatabaseHandler db = new DatabaseHandler(ListRestaurants.this);
        if (db.getRowCount() != 0) {
	        //Get user from database
	        HashMap<String,String> user = db.getUserDetails();
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
        
        Button mapBtn = (Button)findViewById(R.id.list_button);
	    mapBtn.setEnabled(false);
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
           
        String response = null;
        
	    try {
			response = new TaskAsyncHttpGet(ListRestaurants.this).execute("http://maeverooney.x10.mx/selectAllRestaurants.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	    JSONArray myArray = null;
	    
	    if (response !=null){
			try {
				myArray = new JSONArray(response);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}       
	        try{
		        for(int i=0;i<myArray.length();i++){						
					HashMap<String, String> map = new HashMap<String, String>();	
					JSONObject e = myArray.getJSONObject(i);
					map.put("id", e.getString("id"));
					map.put("name", "Name: " + e.getString("name"));
		        	map.put("rating", e.getString("overallRating"));
		        	map.put("wheatrating", e.getString("overallRating"));
		        	map.put("glutenrating", e.getString("wheatRating"));
		        	map.put("dairyrating", e.getString("dairyRating"));
		        	map.put("nutrating", e.getString("nutRating"));
		        	mylist.add(map);			
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
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
			super(ListRestaurants.this, R.layout.list_item, list);
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
		}
		
	}
	
	private void OpenDialog(int restID, String name, String rating){
		  final AlertDialog.Builder dialog = new AlertDialog.Builder(ListRestaurants.this);
		  restIDStr = restID+"";
		  final int restaurantID = restID;
		  
		  LayoutInflater layout = LayoutInflater.from(ListRestaurants.this);
		  View buttonView = layout.inflate(R.layout.heart_button, null);
		  favouriteText = (TextView) buttonView.findViewById(R.id.favouriteText);
		  heartButton = (Button) buttonView.findViewById(R.id.heartButton);
		  heartButton.setOnClickListener(addToFavourites);
		  RatingBar ratingBar = (RatingBar) buttonView.findViewById(R.id.ratingbar_Indicator);
		  dialog.setView(buttonView);
		  
	      DatabaseHandler db = new DatabaseHandler(ListRestaurants.this);
	      //Check if user in sqlite database
	      if (db.getRowCount() != 0) {
		      //Get user from database
		      HashMap<String,String> user = db.getUserDetails();
		      userIDStr = (String)user.get("uid");
		      //if restaurant in favourites change isFavourite to true
		      isFavourite = CheckIfRestaurantInFavourites(restID, userIDStr);
		      // if user is logged in show favourite button
			  heartButton.setOnClickListener(addToFavourites);	
			  if (isFavourite){
				  favouriteText.setText("tap heart to remove from favourites");
				  heartButton.setBackgroundResource(R.drawable.heart_on);
			  }
			  dialog.setView(buttonView);  
	      } else {
	    	  heartButton.setVisibility(View.GONE);
			  favouriteText.setVisibility(View.GONE);
	      }
		  
		  dialog.setTitle(name);
		  ratingBar.setRating(Float.parseFloat(rating));
		  
		  dialog.setPositiveButton("More Info", new DialogInterface.OnClickListener() {
			  @Override
			  public void onClick(DialogInterface arg0, int arg1) {
				  Intent intent = new Intent(ListRestaurants.this, RestaurantDetailView.class);
				  Bundle bundle = new Bundle();
				  bundle.putInt("restaurantID", restaurantID);
				  intent.putExtras(bundle);
				  ListRestaurants.this.startActivity(intent);
			  }});
		  
		  dialog.setNeutralButton("Review Restaurant", new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO go to review restaurant
				//pass restaurant id
				Intent intent = new Intent(ListRestaurants.this, ReviewRestaurant.class);
				Bundle bundle = new Bundle();
				bundle.putInt("restaurantID", restaurantID);
				intent.putExtras(bundle);
				ListRestaurants.this.startActivity(intent);
			}});
		  
		  dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {		  
			  @Override     
			  public void onClick(DialogInterface arg0, int arg1) {
				  
				  arg0.cancel();
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
				  RestaurantFunctions restaurantFunction = new RestaurantFunctions(ListRestaurants.this);
			      restaurantFunction.removeFromFavourites(userIDStr, restIDStr);
			      restaurantFunction.decreaseFavouriteCountOfRestaurant(restIDStr);
			} else {
				isFavourite = true;
				favouriteText.setText("tap heart to remove from favourites");
				heartButton.setBackgroundResource(R.drawable.heart_on);
				// add to favourites table in db
				RestaurantFunctions restaurantFunction = new RestaurantFunctions(ListRestaurants.this);
		        restaurantFunction.addToFavourites(userIDStr, restIDStr);
		        restaurantFunction.increaseFavouriteCountOfRestaurant(restIDStr);
			}
		  }
	};

	private boolean CheckIfRestaurantInFavourites(int restaurantID, String userID){
		// get users favourites from db
		RestaurantFunctions restaurantFunction = new RestaurantFunctions(ListRestaurants.this);
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
