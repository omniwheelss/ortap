package com.example.seenu.vts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.json.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SA_Home extends Activity{
	
    SA_ServiceLayer objServiceLayer;
    SA_DatabaseHandler databaseHandler;
	Context mContext = this;    
	ListView lstContent;
	String APP_NAME = "VEHICLE TRACKING SYSTEM";
	TextView tvMove,tvStop,tvVerify;
	ListAdapter lstAdapter;
	String userName,password;
	// Progress Dialog
    private ProgressDialog pDialog;
	static final String KEY_LOCATION = "location"; // parent node
	static final String KEY_SLNO = "SlNo";
	static final String KEY_STATUS = "device_health";
	static final String KEY_VEHNO = "asset_no";
	static final String KEY_SPEED = "speed";  
	static final String KEY_LOCNAME = "loc";
	static final String KEY_DATE = "date";
	static final String KEY_TIME = "time";
	/* 1 - Moving  2 - Stopped  3 - Verify */
	static final String KEY_STATUSFLAG = "";

    int movingCount = 0;
    int stoppedCount = 0;
    int verifyCount = 0;
    
    MyTimerTask myTimerTask;
	Timer myTimer;
	
	@SuppressLint("NewApi")
	@Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sa_home);

        objServiceLayer = new SA_ServiceLayer();
        databaseHandler = new SA_DatabaseHandler(mContext);
        myTimer = new Timer();
        //tvResultContent = (TextView) findViewById(R.id.tvResultContent); 
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
        
        lstContent = (ListView)findViewById(R.id.lstContent); 
        
        tvMove = (TextView) findViewById(R.id.tvMove);
        tvStop = (TextView) findViewById(R.id.tvStop);
        tvVerify = (TextView) findViewById(R.id.tvVerify);
        
        userName = (String) getIntent().getExtras().getString("UserName");
        
        password = (String) getIntent().getExtras().getString("Password");
        
		/* commented
        if(objServiceLayer.connectedToNetwork(mContext)){

        	
        	/* Start the timer *
    		myTimer.cancel();
            myTimer = new Timer();
            myTimerTask= new MyTimerTask();
            myTimer.scheduleAtFixedRate(myTimerTask, 0, 1*60*1000);

        }
        else{
        	showAlert();
        }        


        /* Move Tab Click Event *
        tvMove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		        showData("1");		
			}
		});
        
        /* Stop Tab Click Event *
        tvStop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(mContext, "Stop", Toast.LENGTH_SHORT).show();
		    showData("2");		
			}
		});
        
        tvVerify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showData("3");
			}
		});
        */
    }	
	
	
	private void showData(String colValue){
		ArrayList<HashMap<String, String>> curLocList = new ArrayList<HashMap<String, String>>();
		
		Cursor curLocSummary = databaseHandler.getLocSummary(colValue); 
		
		//Toast.makeText(mContext, "Count :: " + curLocSummary.getCount(), Toast.LENGTH_SHORT).show();
		
		if(curLocSummary.getCount() > 0){
			curLocSummary.moveToFirst();
			while (!curLocSummary.isAfterLast()) {
				HashMap<String, String> loc = new HashMap<String, String>();
				
				loc.put(KEY_SLNO, curLocSummary.getString(0));
				loc.put(KEY_VEHNO, curLocSummary.getString(1));
				loc.put(KEY_STATUS, curLocSummary.getString(2));
				loc.put(KEY_SPEED, curLocSummary.getString(3));
				loc.put(KEY_LOCNAME, curLocSummary.getString(4));
				loc.put(KEY_DATE, curLocSummary.getString(5));
				loc.put(KEY_TIME, curLocSummary.getString(6));
				curLocList.add(loc);
				
				curLocSummary.moveToNext();
			}
			lstAdapter=new ListAdapter(SA_Home.this, curLocList);
			lstContent.setVisibility(View.VISIBLE);
	        lstContent.setAdapter(lstAdapter);		
		}
		else{
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			alertDialog.setTitle("Alert");
			alertDialog.setMessage("No Data Found");
			alertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			alertDialog.show();
			
			lstContent.setVisibility(View.GONE);
		}
	}

	private void showAlert(){
		  AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			// Setting Dialog Title
			alertDialog.setTitle("Network Settings");
			// Setting Dialog Message
			alertDialog.setMessage("Network is not enabled. Do you want to go to settings menu?");
			// On pressing Settings button
			alertDialog.setPositiveButton("Settings",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							SA_Home.this.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
							SA_Home.this.finish();
						}
					});
			// on pressing cancel button
			alertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							SA_Home.this.finish();
						}
					});
			// Showing Alert Message
			alertDialog.show();  
	  }

	private class MyTimerTask extends TimerTask {
	        @Override
	        public void run() {
	            runOnUiThread(new Runnable() {
	                //@SuppressLint("ShowToast")
					public void run() {
						//if(isFinishing()){
							if(objServiceLayer.connectedToNetwork(mContext)){
								//code to get and send location information
                                Log.d("connectedToNetwork", "checked connectedToNetwork");
								new LoadLocation().execute();	
							}
							else{
								showAlert();
							}	
						//}
						
					}
	            });
	        }
	    }
	



/**
 * Background Async Task to Load all product by making HTTP Request
 * */
class LoadLocation extends AsyncTask<String, String, String> {

	ArrayList<HashMap<String, String>> locList = new ArrayList<HashMap<String, String>>();
	
    /**
     * Before starting background thread Show Progress Dialog
     * */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(SA_Home.this);
        pDialog.setMessage("Loading... Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * getting All products from url
     * */
    protected String doInBackground(String... args) {
    	
		String serviceUrl = objServiceLayer.getXmlFromUrl("http://onroadtrack.com/app/loc_summary.php?key=14583&format=xml&username="+userName+"&&password="+password);

		try {
			JSONObject jObject = new JSONObject(serviceUrl);
			JSONArray jArray = jObject.getJSONArray("location");

			movingCount = 0;
			stoppedCount = 0;
			verifyCount = 0;

			for(int i = 0; i < jArray.length(); i++) {

				JSONObject getColumn = jArray.getJSONObject(i);
				String Asset_No = getColumn.getString("asset_no");
				String Device_Health = getColumn.getString("device_health");
				String Speed = getColumn.getString("speed");
				String Location = getColumn.getString("loc");
				String Date = getColumn.getString("date");
				String Time = getColumn.getString("time");

				HashMap<String, String> loc = new HashMap<String, String>();

				int slNo = (i+1);
				//int i = 0; //(i+1);
				loc.put(KEY_SLNO, String.valueOf(slNo));

				if (Asset_No != null) {
					loc.put(KEY_STATUS, Device_Health);

					if (Device_Health.trim().equalsIgnoreCase("Moving")) {
						loc.put(KEY_STATUSFLAG, "1");
						movingCount += 1;
					} else if (Device_Health.trim().equalsIgnoreCase("Stopped")) {
						loc.put(KEY_STATUSFLAG, "2");
						stoppedCount += 1;
					} else if (Device_Health.trim().equalsIgnoreCase("Verify") || Device_Health.trim().equalsIgnoreCase("Verified")) {
						loc.put(KEY_STATUSFLAG, "3");
						verifyCount += 1;
					}

				} else {
					loc.put(KEY_STATUSFLAG, "");
					loc.put(KEY_STATUS, "");
				}

				if (Asset_No != null && Asset_No.trim() != "") {
					loc.put(KEY_VEHNO, Asset_No.trim());
				} else {
					loc.put(KEY_VEHNO, "");
				}

				if (Speed != null && Speed.trim() != "") {
					loc.put(KEY_SPEED, Speed.trim());
				} else {
					loc.put(KEY_SPEED, "");
				}

				if (Location != null && Location.trim() != "") {
					loc.put(KEY_LOCNAME, Location.trim());
				} else {
					loc.put(KEY_LOCNAME, "");
				}

				if (Date != null && Date.trim() != "") {
					loc.put(KEY_DATE, Date.trim());
				} else {
					loc.put(KEY_DATE, "");
				}

				if (Time != null && Time.trim() != "") {
					loc.put(KEY_TIME, Time.trim());
				} else {
					loc.put(KEY_TIME, "");
				}

				//adding HashList to ArrayList
				locList.add(loc);

				/*if(i == 0){
					databaseHandler.deleteLocSummary();
				}*/

				//databaseHandler.addLocationSummary(new LocSummary(loc.get(KEY_SLNO), loc.get(KEY_VEHNO), loc.get(KEY_STATUS), loc.get(KEY_SPEED), loc.get(KEY_LOCNAME), loc.get(KEY_DATE), loc.get(KEY_TIME),loc.get(KEY_STATUSFLAG)), "Insert", "", "");

			}

		} catch (JSONException e) {
			//some exception handler code.
			return null;
		}
		return null;
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
            	
            	// Getting adapter by passing xml data ArrayList
             	lstAdapter=new ListAdapter(SA_Home.this, locList);        
             	
             	lstContent.setVisibility(View.VISIBLE);
             		
                lstContent.setAdapter(lstAdapter);
      

                tvMove.setText("Moving " + String.valueOf(movingCount));
                tvStop.setText("Stopped " + String.valueOf(stoppedCount));
                tvVerify.setText("Failure " + String.valueOf(verifyCount));
                
                // Click event for single list row
                lstContent.setOnItemClickListener(new OnItemClickListener() {

           			@Override
           			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
             	     	//Toast.makeText(mContext, "hi", Toast.LENGTH_SHORT).show();					
             		}
             	  
                });
            }
        });
    }

   }

   @Override
   public void onDestroy() {
	   myTimer.cancel();
       super.onDestroy();
       if (pDialog != null) {
    	   pDialog.dismiss();
    	   pDialog = null;
       }
   }

   @Override
   public void onPause(){
	   myTimer.cancel();
    super.onPause();
    if(pDialog != null)
    	pDialog.dismiss();
   }

   @Override
   protected void onStop() {
	   myTimer.cancel();
       super.onStop();
       if (pDialog != null) {
          pDialog.dismiss();
          pDialog = null;
       }
   }
   
   public void onResume(){
		  /* Start the timer */
	   myTimer.cancel();
       myTimer = new Timer();
       myTimerTask= new MyTimerTask();
       myTimer.scheduleAtFixedRate(myTimerTask, 0, 1*60*1000);
		  super.onResume();
   }
	  
   public void onRestart(){
		  /* Start the timer */
	   myTimer.cancel();
       myTimer = new Timer();
       myTimerTask= new MyTimerTask();
       myTimer.scheduleAtFixedRate(myTimerTask, 0, 1*60*1000);
		  super.onRestart();
	 }
   
   
   /* Physical Back button disable */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//return false;
			
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
			// Setting Dialog Title
			alertDialog.setTitle("Exit");      
			// Setting Dialog Message
			alertDialog.setMessage("Do you want to Exit the App?");
			// On pressing Settings button
			alertDialog.setPositiveButton("Yes",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							SA_Home.this.finish();
							Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.addCategory(Intent.CATEGORY_HOME);
							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(intent);
						}
					});
			// on pressing cancel button
			alertDialog.setNegativeButton("No",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			// Showing Alert Message
			alertDialog.show();
			
		}
		return super.onKeyDown(keyCode, event);
	}
  	
   
   
}

