package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
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
	 
	 String name = getIntent().getStringExtra("menu");
	 String tmpeval = getIntent().getStringExtra("eval");
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
     list1.add("肄�");
     list1.add("硫�");
     list1.add("��");
     
     ArrayAdapter<String> adapter1;
     adapter1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, list1);

     ListView listView1 = (ListView)findViewById(R.id.detail_snu_menu_comment);
     listView1.setAdapter(adapter1);
}
}
