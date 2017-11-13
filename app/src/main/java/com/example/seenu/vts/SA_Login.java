package com.example.seenu.vts;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.json.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;


public class SA_Login extends Activity{
	
    SA_ServiceLayer objServiceLayer;
	
	Context mContext = this;    
	
	String APP_NAME = "VEHICLE TRACKING SYSTEM";
	
	ImageView ivSignin;
	
	EditText etUserName,etPassword;
	
	private ProgressDialog pDialog;
	
	static final String KEY_AUTHENDICATE = "user_status"; // parent node
	static final String KEY_STATUS = "status";
	
	String strStatus = "";

	@SuppressLint("NewApi")
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sa_login);
	
	    ivSignin = (ImageView) findViewById(R.id.ivSignIn);
	    
	    etUserName = (EditText) findViewById(R.id.etUserName);
	    
	    etPassword = (EditText) findViewById(R.id.etPassword);
	    
	    objServiceLayer = new SA_ServiceLayer();
	    
	    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        
	    
	    ivSignin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.v("onClick", "Inside");
			    if(etUserName.getText().toString().trim().length() == 0 ){
			    	showAlert("Please Enter Username");
			    }
			    else if(etPassword.getText().toString().trim().length() == 0){
			    	showAlert("Please Enter Password");
			    }
			    else{
			    	if(objServiceLayer.connectedToNetwork(mContext)){
                        Log.d("connectedToNetwork", "Inside");
                        //code to get and send location information
						new LoginCredential().execute();	
					}
			    	else{
			    		  AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			  			// Setting Dialog Title
			  			alertDialog.setTitle("Network Settings");
			  			// Setting Dialog Message
			  			alertDialog.setMessage("Network is not enabled. Do you want to go to settings menu?");
			  			// On pressing Settings button
			  			alertDialog.setPositiveButton("Settings",
			  					new DialogInterface.OnClickListener() {
			  						public void onClick(DialogInterface dialog, int which) {
			  							SA_Login.this.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
			  							SA_Login.this.finish();
			  						}
			  					});
			  			// on pressing cancel button
			  			alertDialog.setNegativeButton("Cancel",
			  					new DialogInterface.OnClickListener() {
			  						public void onClick(DialogInterface dialog, int which) {
			  							dialog.cancel();
			  							SA_Login.this.finish();
			  						}
			  					});
			  			// Showing Alert Message
			  			alertDialog.show();
			    	}
			    }   
			}
		});
        
	}
	
	/* Alert Message */
	private void showAlert(String strMessage){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		alertDialog.setTitle("Alert");
		alertDialog.setMessage(strMessage);
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		alertDialog.show();
	}
	
	
	/**
	 * Background Async Task to Load all product by making HTTP Request
	 * */
	class LoginCredential extends AsyncTask<String, String, String> {

	    /**
	     * Before starting background thread Show Progress Dialog
	     * */
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        pDialog = new ProgressDialog(SA_Login.this);
	        pDialog.setMessage("Loading... Please wait...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(false);
	        pDialog.show();
	    }

	    /**
	     * getting All products from url
	     * */
	    protected String doInBackground(String... args) {

			String serviceUrl = objServiceLayer.getXmlFromUrl("http://onroadtrack.com/app/oauth.php?key=14583&format=xml&username="+ etUserName.getText().toString() + "&&password=" + etPassword.getText().toString());

			try {
				JSONObject jObject = new JSONObject(serviceUrl);
				JSONObject uniObject = jObject.getJSONObject(KEY_AUTHENDICATE);
				strStatus = uniObject.getString(KEY_STATUS);
				return strStatus;
			} catch (JSONException e) {
				//some exception handler code.
				return null;
			}
	        // looping through all item nodes <item>

	        //return null;
	    }

	    /**
	     * After completing background task Dismiss the progress dialog
	     * **/
	    protected void onPostExecute(String file_url) {
	        // dismiss the dialog after getting all products
	        pDialog.dismiss();
	        // updating UI from Background Thread
	        runOnUiThread(new Runnable() {
	            public void run() {
	            	if(strStatus.equalsIgnoreCase("false")){
	            		showAlert("Please Enter Valid Username and Password");
	            	}
	            	else{
	            		Intent i = new Intent(mContext,SA_Home.class);
                        i.putExtra("UserName", etUserName.getText().toString());
                        i.putExtra("Password", etPassword.getText().toString());
	            		startActivity(i);	
	            	}
	            }
	        });
	    }

	   }

	   @Override
	   public void onDestroy() {
	       super.onDestroy();
	       if (pDialog != null) {
	    	   pDialog.dismiss();
	    	   pDialog = null;
	       }
	   }

	   @Override
	   public void onPause(){
	    super.onPause();
	    if(pDialog != null)
	    	pDialog.dismiss();
	   }

	   @Override
	   protected void onStop() {
	       super.onStop();
	       if (pDialog != null) {
	          pDialog.dismiss();
	          pDialog = null;
	       }
	   }
	   
		/* Physical Back button disable */
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				SA_Login.this.finish();
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);				
			}
			return super.onKeyDown(keyCode, event);
		}
	   	
}
