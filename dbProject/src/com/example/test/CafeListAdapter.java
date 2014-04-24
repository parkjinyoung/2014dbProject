package com.example.test;

import java.util.ArrayList;

import object.Comment;
import object.SnuMenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CafeListAdapter extends ArrayAdapter<SnuMenu>{
    private ArrayList<SnuMenu> items;
    private int rsrc;
    
	public CafeListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<SnuMenu> objects) {
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
        SnuMenu e = items.get(position);
        if (e != null) {
            ((TextView)v.findViewById(R.id.row_title)).setText(e.getMenu());
            ((TextView)v.findViewById(R.id.snu_todaymenu_price)).setText(Integer.toString(e.getPrice()));
        }
        
        return v;
    }
}
	
