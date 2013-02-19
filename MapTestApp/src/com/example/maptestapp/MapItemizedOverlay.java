package com.example.maptestapp;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

public class MapItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	private Context mContext;
	//Context allows access to application-specific resources and classes, 
	//as well as up-calls for application-level operations such as launching activities, 
	//broadcasting and receiving intents, etc.

	public MapItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		mContext = context;
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return mOverlays.get(i);
	}
	
	@Override
	public int size() {
	  return mOverlays.size();
	}
	
	
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    //populate() is used by superclass ItemizedOverlay, which will read each of the 
	    //OverlayItem objects and prepare them to be drawn.
	    populate();
	}
	
	//When the user taps an item overlaid on the map.
	//The title and snippet of the item are displayed in an AlertDialog
	@Override
	protected boolean onTap(int index) {
	  OverlayItem item = mOverlays.get(index);
	  AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	  dialog.setTitle(item.getTitle());
	  dialog.setMessage(item.getSnippet());
	  dialog.show();
	  return true;
	}
	
	

}
