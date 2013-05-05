package com.example.allergymapapp;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class RestaurantDetailView extends ListActivity {
	String restaurantName;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_view);
        Bundle bundle = getIntent().getExtras();
    	int restaurantID = bundle.getInt("restaurantID");
    	String id = Integer.toString(restaurantID);
    	
    	String name = "";
  	  	String phone = "Phone: ";
  	  	String address = "Address:\r\n";
  	  	String email = "Email: ";
  	  	String imageURL = "http://http://maeverooney.x10.mx/images/rest4.jpg";
  	  	ImageView img=(ImageView)findViewById(R.id.restaurant_image);
  	  	TextView nameText=(TextView)findViewById(R.id.restaurant_name);
  	  	TextView addressText=(TextView)findViewById(R.id.restaurant_address);
  	  	TextView phoneText=(TextView)findViewById(R.id.restaurant_phone);
  	  	TextView emailText=(TextView)findViewById(R.id.restaurant_email);
  	  	RatingBar rate = (RatingBar)findViewById(R.id.ratingbar_Indicator);
  	  
  	  	List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("id", id));
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
         
        String response = null;
  	  	TaskAsyncHttpPost httpRequest = new TaskAsyncHttpPost(nameValuePairs, RestaurantDetailView.this);
  	  	try {
	  		response = httpRequest.execute("http://maeverooney.x10.mx/getOneRestaurant.php").get();
	  		String str = response.replace(":null", ":\"0\"");
	  		response = str;
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
  					restaurantName = name;
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
  	    		try {
  	    			rate.setRating(Float.parseFloat(restaurantObject.getString("overallRating")));
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    		try {
  	    			imageURL = restaurantObject.getString("imageURL");
  	    		} catch (JSONException e) {
  	    			// TODO Auto-generated catch block
  	    			e.printStackTrace();
  	    		}
  	    	 }    	
        }
  	    // Parse address string to add line breaks
  	    String newString = address.replace(",", ",\r\n");
  	    address = newString;
  	    nameText.setText(name);
  	    addressText.setText(address);
  	    emailText.setText(email);
  	    phoneText.setText(phone);
  	    Drawable drawable = LoadImageFromWebOperations(imageURL);
  	    img.setImageDrawable(drawable);
           
        String response2 = null;
        TaskAsyncHttpPost request = new TaskAsyncHttpPost(nameValuePairs, RestaurantDetailView.this);
	    try {
			response2 = request.execute("http://maeverooney.x10.mx/getReviews.php").get();
	  		String str = response2.replace(":null", ":\"0\"");
	  		response2 = str;
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	    JSONArray myArray = null;
	    
	    if (response2 !=null){
			try {
				myArray = new JSONArray(response2);
			} catch (JSONException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}       
	        try{
	  	
		        for(int i=0;i<myArray.length();i++){						
					HashMap<String, String> map = new HashMap<String, String>();	
					JSONObject e = myArray.getJSONObject(i);
					String rating = e.getString("overallRating");
					if (rating.equals("null")){
						rating = "0";
					}
					map.put("id",e.getString("id"));
					map.put("rating", e.getString("overallRating"));
					map.put("text", "Review: " + e.getString("text"));
		        	mylist.add(map);			
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
	    }
	    
    	ArrayList<RowModel> list=new ArrayList<RowModel>();
	    
	    for (HashMap<String, String> entry : mylist) {
			list.add(new RowModel(entry.get("text"), Float.parseFloat(entry.get("rating")), entry.get("id")));
		}
	    

		RatingAdapter adapter = new RatingAdapter(list);
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {        		
  			  	RowModel o = (RowModel) (((RatingAdapter)getListAdapter()).getItem(position));
        		Intent intent = new Intent(RestaurantDetailView.this, ReviewDetailView.class);
  			  	Bundle bundle = new Bundle();
  			  	bundle.putInt("reviewID", Integer.parseInt(o.id));
  			  	bundle.putCharSequence("restaurantName", restaurantName);
  			  	bundle.putInt("reviewID", Integer.parseInt(o.id));
  			  	intent.putExtras(bundle);
  			  	RestaurantDetailView.this.startActivity(intent);
			}
		});
    }
    
    private Drawable LoadImageFromWebOperations(String string) {
    	try{
    		URL url = new URL(string);                     
		    HttpURLConnection connection  = (HttpURLConnection) url.openConnection();
		    InputStream is = connection.getInputStream();
 			Drawable d = Drawable.createFromStream(is, "src name");
 			return d;
 		}catch (Exception e) {
 			System.out.println("Exc="+e);
 			return null;
 		}
	}

	private RowModel getModel(int position) {
		return (RowModel) (((RatingAdapter)getListAdapter()).getItem(position));
	}
    
    @SuppressWarnings("rawtypes")
	class RatingAdapter extends ArrayAdapter {
		@SuppressWarnings("unchecked")
		RatingAdapter(ArrayList list) {
			super(RestaurantDetailView.this, R.layout.list_item, list);
		}
		
		public View getView(int position, View convertView, ViewGroup parent) {
			View row=convertView;
			ViewHolder holder;
			if (row==null) {		
				LayoutInflater inflater = getLayoutInflater();
				holder=new ViewHolder();
				row=inflater.inflate(R.layout.short_list_item, parent, false);
				holder.rate=(RatingBar) row.findViewById(R.id.ratingbar_Indicator);
				holder.title=(TextView) row.findViewById(R.id.item_title);
				holder.subtitle =(TextView) row.findViewById(R.id.item_subtitle);
				row.setTag(holder);
			} else {
				holder=(ViewHolder)row.getTag();
			}
			RowModel model=getModel(position);
			
			holder.subtitle.setText("User rating: ");
			holder.title.setText(model.label);
			holder.rate.setRating(model.rating);		
			return(row);
		}
	}
    
    static class ViewHolder {
    	TextView subtitle = null;
    	RatingBar rate = null;
		TextView title = null;
	    int position;
	}
    
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.back_button:
    		finish();
        break;
    	case R.id.menu_button:
    		Intent menuIntent = new Intent(v.getContext(), MainMenu.class);
            startActivityForResult(menuIntent, 0);
        break;
        case R.id.map_button:
        	Intent mapIntent = new Intent(v.getContext(), RestaurantMap.class);
            startActivityForResult(mapIntent, 0);
        break;
        case R.id.list_button:
        	Intent listIntent = new Intent(v.getContext(), ListRestaurants.class);
            startActivityForResult(listIntent, 0);
        break;
		}
		
	}
	
	class RowModel {
		String label;
		float rating;
		String id;
		
		RowModel(String label, float rating, String id) {
			this.label=label;
			this.rating= rating;
			this.id = id;
		}
		
	}
}
