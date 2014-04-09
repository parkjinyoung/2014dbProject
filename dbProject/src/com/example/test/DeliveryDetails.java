package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DeliveryDetails extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent i = getIntent(); // 값을 받기 위한 Intent 생성

		String deliveryName = i.getStringExtra("deliveryName");

		setContentView(R.layout.detail_delivery_layout);

		TextView text_name = (TextView) findViewById(R.id.detail_delivery_name);
		ImageView image_eval = (ImageView) findViewById(R.id.detail_delivery_eval_img);
		TextView float_eval = (TextView) findViewById(R.id.detail_delivery_eval_float);

		/*
		 * String tmpeval = getIntent().getStringExtra("eval"); float eval =
		 * Float.parseFloat(tmpeval);
		 * 
		 * float_eval.setText(tmpeval);
		 */
		float eval = (float) 3.5;
		text_name.setText(deliveryName);
		if (eval > 2.5) {
			image_eval.setImageDrawable(getResources().getDrawable(
					R.drawable.star1));
		} else {
			image_eval.setImageDrawable(getResources().getDrawable(
					R.drawable.star2));
		}

		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("comment 1");
		list1.add("comment 2");
		list1.add("comment 3");

		ArrayAdapter<String> adapter1;
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list1);

		ListView listView1 = (ListView) findViewById(R.id.detail_delivery_comment);
		listView1.setAdapter(adapter1);
	}

}
