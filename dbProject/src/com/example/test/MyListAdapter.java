package com.example.test;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import object.Comment;
import object.RecComment;
import object.SnuMenu;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import comserver.SendServer;

public class MyListAdapter extends ArrayAdapter<Comment>{
    private ArrayList<Comment> items;
    private int rsrc;
    DatabaseHelper db;
    
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
       
        final TextView rectext = (TextView) v.findViewById(R.id.comment_up_count);  
        final TextView unrectext = (TextView) v.findViewById(R.id.comment_down_count);  
        
        ImageView[] image_eval = new ImageView[5];
		image_eval[0] = (ImageView) v.findViewById(R.id.comment_eval_stars1);
		image_eval[1] = (ImageView) v.findViewById(R.id.comment_eval_stars2);
		image_eval[2] = (ImageView) v.findViewById(R.id.comment_eval_stars3);
		image_eval[3] = (ImageView) v.findViewById(R.id.comment_eval_stars4);
		image_eval[4] = (ImageView) v.findViewById(R.id.comment_eval_stars5);
		
		Button deletebtn = (Button) v.findViewById(R.id.comment_delete_btn);
		Button modifybtn = (Button) v.findViewById(R.id.comment_modify_btn);

		LinearLayout mycommentlayout = (LinearLayout) v.findViewById(R.id.mycomment_btn_layout);
		
        final Comment e = items.get(position);
        if (e != null) {
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_text)).setText(e.getComment());
            ((TextView)v.findViewById(R.id.snumenu_detail_comment_nickname)).setText(e.getnickname());

            String tmpeval = e.getRating();
            
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
    		
    		String user_id = "yujinee";
    		
    		mycommentlayout.setVisibility(View.GONE);
    		modifybtn.setVisibility(View.GONE);
			deletebtn.setVisibility(View.GONE);
    		
			
    		if(e.getnickname().equals(user_id)){
    			modifybtn.setVisibility(View.VISIBLE);
    			deletebtn.setVisibility(View.VISIBLE);
    			mycommentlayout.setVisibility(View.VISIBLE);
    			System.out.println("NICKNAME : " + e.getnickname());
    		}
            
            
            rectext.setText(Integer.toString(e.getRecommend()));            
            unrectext.setText(Integer.toString(e.getUnrecommend())); 
    		
            Button recommendbtn = (Button) v.findViewById(R.id.comment_up_btn);
    		recommendbtn.setFocusable(true);
    		recommendbtn.setClickable(true);
    		
    		recommendbtn.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				String user_id = "yujinee";
    				RecComment reccom = new RecComment(user_id, e.getnickname(), "true", e.getMenu(), e.getCafe());
    				String url = "http://laputan32.cafe24.com/Eval";
    				SendServer send = new SendServer(reccom, url);
    				String sendresult = send.send();
    				
    				if (sendresult != null && !sendresult.equals("")) {
    					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
    					String isValid = (String) jo.get("message");
    					if(isValid.equals("success")) rectext.setText(Integer.toString(e.getRecommend()+1));
    				}
    				
    				System.out.println("recommend true : " + sendresult);
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
    					if(isValid.equals("success")) unrectext.setText(Integer.toString(e.getUnrecommend()+1));
    				}
    				
    				System.out.println("recommend false : " + sendresult);
    				Toast.makeText(getContext(), "안추천", Toast.LENGTH_SHORT).show();
    			}
    		});
    		
    		modifybtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(v.getContext(), EvalSnuMenu.class);
					
					i.putExtra("cafe", e.getCafe());
					i.putExtra("menu", e.getMenu());
					i.putExtra("comment", e.getComment());
					i.putExtra("rating", e.getRating());
					Toast.makeText(getContext(), "수정", Toast.LENGTH_SHORT).show();
					
					v.getContext().startActivity(i);
				}
			});
    		
    		deletebtn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), "삭제", Toast.LENGTH_SHORT).show();
					
					String url = "http://laputan32.cafe24.com/Eval";
					SendServer send = new SendServer(e, url, "3");
					String sendresult = send.send();
					System.out.println("delete send = " + sendresult);
					// Comment e;
					// 서버로 날려서 삭제 요청
					// 삭제후 새로운 rating 받아옴
					
					Intent i = new Intent(v.getContext(),SnuMenuDetails.class);
					
					db = new DatabaseHelper(v.getContext());
					
					//db.updateSnuMenu(e.getCafe(), e.getMenu(), newrating); // 수정된 eval 넣어줌
					
					db.closeDB();
					
					i.putExtra("menu", e.getMenu());
					i.putExtra("cafe", e.getCafe());
					
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					v.getContext().startActivity(i);
				}
			});
        }
        

        
        return v;
    }
}
	
