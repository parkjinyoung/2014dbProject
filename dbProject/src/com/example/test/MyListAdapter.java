package com.example.test;

import java.util.ArrayList;

import object.Comment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        final Comment e = items.get(position);
        if (e != null) {
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_text)).setText(e.getComment());
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_nickname)).setText(e.getnickname());
    		Button recommendbtn = (Button) v.findViewById(R.id.comment_up_btn);
    		recommendbtn.setFocusable(true);
    		recommendbtn.setClickable(true);
    		recommendbtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Toast.makeText(getContext(), "추천 " + e.getnickname(), Toast.LENGTH_SHORT).show();
    			}
    		});
    		
    		Button unrecommendbtn = (Button) v.findViewById(R.id.comment_down_btn);
    		unrecommendbtn.setFocusable(true);
    		unrecommendbtn.setClickable(true);
    		unrecommendbtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				Toast.makeText(getContext(), "안추천", Toast.LENGTH_SHORT).show();
    			}
    		});
        }
        

        
        return v;
    }
}
	
