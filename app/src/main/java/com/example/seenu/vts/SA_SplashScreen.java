package com.example.seenu.vts;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;

public class SA_SplashScreen extends Activity{
	//stopping splash screen starting home activity.  
    private static final int STOPSPLASH = 0;
    //time duration in millisecond for which your splash screen should visible to  
    //user. here i have taken half second  
    private static final long SPLASHTIME = 2000;
    Context mContext = this;

    //HANDLER FOR SPLASH SCREEN  
    @SuppressLint("HandlerLeak")
	private Handler splashHandler = new Handler() {  
         @Override
         public void handleMessage(Message msg) {
             switch (msg.what) {
                case STOPSPLASH:
                	//Generating and Starting new intent on splash time out
                	finish();
					Intent intent = new Intent(mContext,SA_Login.class);				
					startActivity(intent);
                    break;  
              }  
              super.handleMessage(msg);   
         }  
    };    
          
    @SuppressLint("HandlerLeak")
	@Override  
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sa_splashscreen);
        //Generating message and sending it to splash handle
        Message msg = new Message();  
        msg.what = STOPSPLASH;  
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }	
    
}
