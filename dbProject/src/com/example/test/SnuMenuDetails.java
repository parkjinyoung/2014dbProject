package com.example.test;

import java.util.ArrayList;

import object.Comment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SnuMenuDetails extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.detail_snu_menu_layout);

		TextView text_name = (TextView) findViewById(R.id.detail_snu_menu_name);
		TextView text_cafe = (TextView) findViewById(R.id.detail_snu_menu_cafe);
		ImageView image_eval = (ImageView) findViewById(R.id.detail_snu_menu_eval_img);
		TextView float_eval = (TextView) findViewById(R.id.detail_snu_menu_eval_float);

		final String name = getIntent().getStringExtra("menu");
		String tmpeval = getIntent().getStringExtra("eval");
		final String resname = getIntent().getStringExtra("resname");
		float eval = Float.parseFloat(tmpeval);
		text_cafe.setText(resname);
		
		float_eval.setText(tmpeval);
		text_name.setText(name);
		if(eval > 2.5){
			image_eval.setImageDrawable(getResources().getDrawable(R.drawable.star1));
		}
		else{
			image_eval.setImageDrawable(getResources().getDrawable(R.drawable.star2));
		}



		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("comment1");
		list1.add("comment2");
		list1.add("comment3");
		
		//////////////////////
		ArrayList<Comment> comlist1 = new ArrayList<Comment>();

		Comment com1 = new Comment();
		com1.setCafe(resname);
		com1.setMenu(name);
		com1.setRating(tmpeval);
		com1.setComment("GOOD");
		com1.setUser_id("1234");
		comlist1.add(com1);
		
		Comment com2 = new Comment();
		com2.setCafe(resname);
		com2.setMenu(name);
		com2.setRating(tmpeval);
		com2.setComment("BAD");
		com2.setUser_id("1234");
		comlist1.add(com2);
		
		
		ArrayAdapter<Comment> madapter1;
		madapter1 = new MyListAdapter(this, R.layout.comment_list_item, R.id.snumenu_detail_comment_text, comlist1);
		ListView listView1 = (ListView)findViewById(R.id.detail_snu_menu_comment_listview);
		listView1.setAdapter(madapter1);
		
		////////////////////////////
		
//		ArrayAdapter<String> adapter1;
//		adapter1 = new ArrayAdapter<String>(this, R.layout.comment_list_item, list1);
//		ListView listView1 = (ListView)findViewById(R.id.detail_snu_menu_comment);
//		listView1.setAdapter(adapter1);
		
		
		
		Button evalbtn = (Button) findViewById(R.id.detail_snu_menu_do_eval_btn);
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), EvalSnuMenu.class);
				i.putExtra("menu", name);
				i.putExtra("cafe", resname);
				
				startActivity(i);
				
			}
		});
		
	}
}
