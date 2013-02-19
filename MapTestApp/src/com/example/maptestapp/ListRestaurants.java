package com.example.maptestapp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class ListRestaurants extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        
        final Resources res = getResources();
        final TabHost tabHost = getTabHost();
        
        final Intent i1 = new Intent().setClass(this, RestaurantMap.class);
        final TabSpec oneSpec = tabHost.newTabSpec("A").setIndicator("Map View",
        		res.getDrawable(R.drawable.ic_action_search))
        		.setContent(i1);
        tabHost.addTab(oneSpec);
        
        final Intent i2 = new Intent().setClass(this, ListRestaurants.class);
        final TabSpec twoSpec = tabHost.newTabSpec("B").setIndicator("List View",
        		res.getDrawable(R.drawable.ic_action_search))
        		.setContent(i2);
        tabHost.addTab(twoSpec);

        tabHost.setCurrentTab(2);
        
        ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String, String>>();
           
        String response = null;
        
	    try {
			response = new TaskAsync().execute("http://maeverooney.x10.mx/selectAllRestaurants.php").get();
		} catch (InterruptedException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (ExecutionException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	    
	    JSONArray myArray = null;
	    
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
        		Toast.makeText(ListRestaurants.this, "ID '" + o.get("id") + "' was clicked.", Toast.LENGTH_SHORT).show(); 

			}
		});
    }
}
