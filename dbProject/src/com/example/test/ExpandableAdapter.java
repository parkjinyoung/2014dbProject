
package com.example.test;

import java.util.ArrayList;

import object.SnuMenu;
import object.SnuRestaurant;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter{
	
	Context mContext;
	
	ArrayList<SnuRestaurant> mSnuResList;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;

	public ExpandableAdapter(Context c, ArrayList <SnuRestaurant> SnuResList){
		super();
		this.inflater = LayoutInflater.from(c);
		this.mContext = c;
		this.mSnuResList = SnuResList;
	}
	
	@Override
	public SnuMenu getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mSnuResList.get(groupPosition).getMymenu().get(childPosition);
	}

	@Override
	  public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;
		
		if(v == null){
			viewHolder = new ViewHolder();
			 v = inflater.inflate(R.layout.snu_menu_list_row, parent, false);
			 	viewHolder.title = (TextView) v.findViewById(R.id.row_title);
			 	//viewHolder.image = (ImageView) v.findViewById(R.id.eval_stars);
			 	
			 	ImageView[] image_eval = new ImageView[5];
				image_eval[0] = (ImageView) v.findViewById(R.id.eval_stars1);
				image_eval[1] = (ImageView) v.findViewById(R.id.eval_stars2);
				image_eval[2] = (ImageView) v.findViewById(R.id.eval_stars3);
				image_eval[3] = (ImageView) v.findViewById(R.id.eval_stars4);
				image_eval[4] = (ImageView) v.findViewById(R.id.eval_stars5);
				
				String tmpeval = getChild(groupPosition, childPosition).getRating();
		        
				if(tmpeval!=null){
					float eval = Float.parseFloat(tmpeval);
					
					for(int j=0; j<5; j++){
						if(eval >= 1) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.star25));
						else if (eval == 0.5) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.halfstar25));
						else if (eval <= 0) image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
						eval -= 1;
					}
				}
				else{
//					No Comment Image
					for(int j=0; j<5; j++){
						image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
					}
				}
			 	
			 	viewHolder.price = (TextView) v.findViewById(R.id.snu_todaymenu_price);
	            v.setTag(viewHolder);
	        }else{
	            viewHolder = (ViewHolder)v.getTag();
	        }
		
			viewHolder.title.setText(getChild(groupPosition, childPosition).getMenu());
			viewHolder.price.setText(Integer.toString(getChild(groupPosition, childPosition).getPrice()));
			
			
			return v;
	    }

	@Override
    public int getChildrenCount(int groupPosition) {
        return mSnuResList.get(groupPosition).getMymenu().size();
    }

	@Override
	public SnuRestaurant getGroup(int groupPosition) {
		return mSnuResList.get(groupPosition);
	}

	@Override
	   public int getGroupCount() {
        return mSnuResList.size();
    }

	@Override
	   public long getGroupId(int groupPosition) {
        return groupPosition;
    }
	

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
	
		View v = convertView;
		final int grppos = groupPosition;
        
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.snu_menu_group_row, parent, false);
            viewHolder.title = (TextView) v.findViewById(R.id.group_title);
 
            
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        
        viewHolder.title.setText(getGroup(groupPosition).getName());
        Button detailresbtn = (Button) v.findViewById(R.id.detail_snu_res_btn);
        detailresbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("DBDBDBDB","Btn Click");
				SnuRestaurant a = mSnuResList.get(grppos);

				Intent i = new Intent(v.getContext(), SnuRestaurantDetails.class);
				
				i.putExtra("cafe", a.getName());
				
				v.getContext().startActivity(i);
				
			}
		});
        
        return v;
    }

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
    class ViewHolder {
        public TextView title;
        public TextView price;
        public ImageView image;
    }
 

}
