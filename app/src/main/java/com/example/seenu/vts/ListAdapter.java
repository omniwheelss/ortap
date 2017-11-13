package com.example.seenu.vts;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ResourceAsColor")
public class ListAdapter extends BaseAdapter {
    
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;
    private static LayoutInflater inflater=null;
    //public ImageLoader imageLoader; 
    
    public ListAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    @SuppressLint("ResourceAsColor")
	public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.sa_list_row, null);

        /*TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        TextView duration = (TextView)vi.findViewById(R.id.duration); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image
        */
        
        TextView tvSlNo = (TextView)vi.findViewById(R.id.tvSlNo);
        ImageView imgIcon = (ImageView)vi.findViewById(R.id.imgIcon);
        TextView tvStatus = (TextView)vi.findViewById(R.id.tvStatus);
        TextView tvVehNo = (TextView)vi.findViewById(R.id.tvVehNo);
        TextView tvSpeed = (TextView)vi.findViewById(R.id.tvSpeed);
        TextView tvLoc = (TextView)vi.findViewById(R.id.tvLocation);
        TextView tvDate = (TextView)vi.findViewById(R.id.tvDate);
        TextView tvTime = (TextView)vi.findViewById(R.id.tvTime);
        
        
        HashMap<String, String> loc = new HashMap<String, String>();
        loc = data.get(position);
        
        tvSlNo.setText(loc.get(SA_Home.KEY_SLNO));
        tvStatus.setText(loc.get(SA_Home.KEY_STATUS));
        if(loc.get(SA_Home.KEY_STATUS).equalsIgnoreCase("Moving")){
        	imgIcon.setImageResource(R.drawable.sa_green);
        	tvVehNo.setTextColor(Color.parseColor("#1C7D0A"));
        	tvStatus.setTextColor(Color.parseColor("#407E33"));
        	tvSpeed.setTextColor(Color.parseColor("#407E33"));
        	
        }
        else if(loc.get(SA_Home.KEY_STATUS).equalsIgnoreCase("Stopped")){
        	imgIcon.setImageResource(R.drawable.sa_red);
        	tvVehNo.setTextColor(Color.parseColor("#A50D0A"));
        	tvStatus.setTextColor(Color.parseColor("#FD282C"));
        	tvSpeed.setTextColor(Color.parseColor("#FD282C"));
        }
        else if(loc.get(SA_Home.KEY_STATUS).equalsIgnoreCase("Verify") || loc.get(SA_Home.KEY_STATUS).equalsIgnoreCase("Verified")){
        	imgIcon.setImageResource(R.drawable.sa_grey);
        	tvVehNo.setTextColor(Color.parseColor("#747474"));
        	tvStatus.setTextColor(Color.parseColor("#747474"));
        	tvSpeed.setTextColor(Color.parseColor("#747474"));
        	
        }
        tvVehNo.setText(loc.get(SA_Home.KEY_VEHNO));
        tvSpeed.setText(loc.get(SA_Home.KEY_SPEED));
        tvLoc.setText(loc.get(SA_Home.KEY_LOCNAME));
        tvDate.setText(loc.get(SA_Home.KEY_DATE));
        tvTime.setText(loc.get(SA_Home.KEY_TIME));
        
        
        // Setting all values in listview
        /*title.setText(song.get(CustomizedListView.KEY_TITLE));
        artist.setText(song.get(CustomizedListView.KEY_ARTIST));
        duration.setText(song.get(CustomizedListView.KEY_DURATION));
        imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        */
        
        return vi;
    }
}