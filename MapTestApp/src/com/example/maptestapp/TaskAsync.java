package com.example.maptestapp;

import android.os.AsyncTask;

public class TaskAsync extends AsyncTask<String, String, String> {

private String response = null;


@Override
protected String doInBackground(String... params) {
    try {
		response = new getJSONStringFromURL().getInternetData(params[0]);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return response;
}

}
