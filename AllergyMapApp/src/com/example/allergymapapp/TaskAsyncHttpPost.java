package com.example.allergymapapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

class TaskAsyncHttpPost extends AsyncTask<String, Void, String> {
	private List<NameValuePair> nameValuePairs = null;
	private ProgressDialog progDailog;
	private Context context;
	
	public TaskAsyncHttpPost(List<NameValuePair> nameValuePairs, Context context){
		this.nameValuePairs = nameValuePairs;
		this.context = context;
	}

	@Override
    protected void onPreExecute() {
        super.onPreExecute();
        progDailog = new ProgressDialog(context);
        progDailog.setMessage("Loading...");
        progDailog.setIndeterminate(false);
        progDailog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDailog.setCancelable(true);
        progDailog.show();
    }
	
	@Override
	protected String doInBackground(String... urlString) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost(urlString[0]);
		
		try {
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
				return builder.toString();
			} 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(String unused) {
        super.onPostExecute(unused);
        progDailog.dismiss();
    }
}


