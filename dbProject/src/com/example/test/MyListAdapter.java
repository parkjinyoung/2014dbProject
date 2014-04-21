package com.example.test;

import java.util.ArrayList;

import object.Comment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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
        Comment e = items.get(position);
        if (e != null) {
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_text)).setText(e.getComment());
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_nickname)).setText(e.getId());
        }
        return v;
    }
}
	
