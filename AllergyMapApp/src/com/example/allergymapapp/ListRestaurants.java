package com.example.allergymapapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.maptestapp.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class ListRestaurants extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        
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
					map.put("name", "name: " + e.getString("name"));
		        	map.put("overallRating", "Overall Rating: " +  e.getString("overallRating"));
		        	mylist.add(map);			
				}		
	        }catch(JSONException e)        {
	        	 Log.e("log_tag", "Error parsing data "+e.toString());
	        }
	    }
        
        //List Adapter
        ListAdapter adapter = new SimpleAdapter(this, mylist , R.layout.list_item, 
                        new String[] { "name", "overallRating" }, 
                        new int[] { R.id.item_title, R.id.item_subtitle });
        
        setListAdapter(adapter);
        
        final ListView lv = getListView();
        lv.setTextFilterEnabled(true);	
        lv.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) { 
        		@SuppressWarnings("unchecked")
				HashMap<String, String> o = (HashMap<String, String>) lv.getItemAtPosition(position);
        		Intent intent = new Intent(ListRestaurants.this, RestaurantDetailView.class);
  			  	Bundle bundle = new Bundle();
  			  	bundle.putInt("restaurantID", Integer.parseInt(o.get("id")));
  			  	intent.putExtras(bundle);
  			  	ListRestaurants.this.startActivity(intent);
			}
		});
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
}
