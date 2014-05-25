package com.example.test;

import java.util.ArrayList;

import object.DeliveryRestaurant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeliveryListAdapter extends ArrayAdapter<DeliveryRestaurant>{
    private ArrayList<DeliveryRestaurant> items;
    private int rsrc;
    
	public DeliveryListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<DeliveryRestaurant> objects) {
		super(context, resource, textViewResourceId, objects);
		this.items = objects;
		this.rsrc = resource;
	}
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(rsrc, null);
        } 
        DeliveryRestaurant e = items.get(position);
        if (e != null) {
        	
        	ImageView[] image_eval = new ImageView[5];
    		image_eval[0] = (ImageView) v.findViewById(R.id.del_eval_stars1);
    		image_eval[1] = (ImageView) v.findViewById(R.id.del_eval_stars2);
    		image_eval[2] = (ImageView) v.findViewById(R.id.del_eval_stars3);
    		image_eval[3] = (ImageView) v.findViewById(R.id.del_eval_stars4);
    		image_eval[4] = (ImageView) v.findViewById(R.id.del_eval_stars5);
        	
            ((TextView)v.findViewById(R.id.row_title)).setText(e.getResname());
           
            String tmpeval = e.getRating();
            System.out.println("delivery rating : " + tmpeval);
    		if(tmpeval!=null){
    			float eval = Float.parseFloat(tmpeval);
    			for(int j=0; j<5; j++){
    				if(eval >= 1) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.star25));
    				else if (eval >= 0.5) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.halfstar25));
    				else image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
    				eval -= 1;
    			}
    		}
        
    		else{
//    			No Comment Image
    			for(int j=0; j<5; j++){
    				image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
    			}
    		}
    		
        }
        
        return v;
    }
}
	
