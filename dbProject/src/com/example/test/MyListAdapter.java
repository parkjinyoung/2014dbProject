package com.example.test;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import object.Comment;
import object.RecComment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import comserver.SendServer;

public class MyListAdapter extends ArrayAdapter<Comment>{
    private ArrayList<Comment> items;
    private int rsrc;
    
	public MyListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<Comment> objects) {
		super(context, resource, textViewResourceId, objects);
		this.items = objects;
		this.rsrc = resource;
		// TODO Auto-generated constructor stub
	}
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(rsrc, null);
        } 
        final TextView rectext = (TextView)v.findViewById(R.id.comment_up_count);  
        final TextView unrectext = (TextView)v.findViewById(R.id.comment_down_count);  
        
        final Comment e = items.get(position);
        if (e != null) {
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_text)).setText(e.getComment());
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_nickname)).setText(e.getnickname());
           
            rectext.setText(Integer.toString(e.getRecommend()));            
            unrectext.setText(Integer.toString(e.getUnrecommend())); 
    		
            Button recommendbtn = (Button) v.findViewById(R.id.comment_up_btn);
    		recommendbtn.setFocusable(true);
    		recommendbtn.setClickable(true);
    		
    		recommendbtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				String user_id = "tong";
    				RecComment reccom = new RecComment(user_id, e.getnickname(), "true", e.getMenu(), e.getCafe());
    				String url = "http://laputan32.cafe24.com/Eval";
    				SendServer send = new SendServer(reccom, url);
    				String sendresult = send.send();
    				
    				if (sendresult != null && !sendresult.equals("")) {
    					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
    					String isValid = (String) jo.get("message");
    					if(isValid.equals("true")) rectext.setText(Integer.toString(e.getRecommend()+1));
    				}
    				
    				System.out.println("recommand true : " + sendresult);
    				Toast.makeText(getContext(), "추천 " + e.getnickname(), Toast.LENGTH_SHORT).show();
    			}
    		});
    		
    		Button unrecommendbtn = (Button) v.findViewById(R.id.comment_down_btn);
    		unrecommendbtn.setFocusable(true);
    		unrecommendbtn.setClickable(true);
    		unrecommendbtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				String user_id = "tong";
    				RecComment reccom = new RecComment(user_id, e.getnickname(), "false", e.getMenu(), e.getCafe());
    				String url = "http://laputan32.cafe24.com/Eval";
    				SendServer send = new SendServer(reccom, url);
    				String sendresult = send.send();
    				
    				if (sendresult != null && !sendresult.equals("")) {
    					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
    					String isValid = (String) jo.get("message");
    					if(isValid.equals("true")) unrectext.setText(Integer.toString(e.getUnrecommend()+1));
    				}
    				
    				System.out.println("recommand false : " + sendresult);
    				Toast.makeText(getContext(), "안추천", Toast.LENGTH_SHORT).show();
    			}
    		});
        }
        

        
        return v;
    }
}
	
