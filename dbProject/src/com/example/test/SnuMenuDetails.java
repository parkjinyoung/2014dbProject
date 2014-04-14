package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
		ImageView image_eval = (ImageView) findViewById(R.id.detail_snu_menu_eval_img);
		TextView float_eval = (TextView) findViewById(R.id.detail_snu_menu_eval_float);

		final String name = getIntent().getStringExtra("menu");
		String tmpeval = getIntent().getStringExtra("eval");
		final String resname = getIntent().getStringExtra("resname");
		float eval = Float.parseFloat(tmpeval);

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

		ArrayAdapter<String> adapter1;
		adapter1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, list1);

		ListView listView1 = (ListView)findViewById(R.id.detail_snu_menu_comment);
		listView1.setAdapter(adapter1);
		
		Button evalbtn = (Button) findViewById(R.id.detail_snu_menu_do_eval_btn);
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(v.getContext(), EvalSnuMenu.class);
				i.putExtra("menu", name);
				i.putExtra("resname", resname);
				
				startActivity(i);
				
			}
		});
		
	}
}
