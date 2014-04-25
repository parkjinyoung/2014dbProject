package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import comserver.SendServer;
import object.Comment;
import object.SnuMenu;
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
import android.widget.Toast;

public class SnuMenuDetails extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.detail_snu_menu_layout);

		TextView text_name = (TextView) findViewById(R.id.detail_snu_menu_name);
		TextView text_cafe = (TextView) findViewById(R.id.detail_snu_menu_cafe);
		
		ImageView[] image_eval = new ImageView[5];
		image_eval[0] = (ImageView) findViewById(R.id.detail_snu_menu_eval_img1);
		image_eval[1] = (ImageView) findViewById(R.id.detail_snu_menu_eval_img2);
		image_eval[2] = (ImageView) findViewById(R.id.detail_snu_menu_eval_img3);
		image_eval[3] = (ImageView) findViewById(R.id.detail_snu_menu_eval_img4);
		image_eval[4] = (ImageView) findViewById(R.id.detail_snu_menu_eval_img5);
		
		TextView float_eval = (TextView) findViewById(R.id.detail_snu_menu_eval_float);

		final String menu = getIntent().getStringExtra("menu");
		final String tmpeval = getIntent().getStringExtra("eval");
		final String cafe = getIntent().getStringExtra("cafe");
		final int price = getIntent().getIntExtra("price", 0);
		final String classify = getIntent().getStringExtra("classify");

		
		
		if(tmpeval!=null){
			float eval = Float.parseFloat(tmpeval);
			
			for(int j=0; j<5; j++){
				if(eval >= 1) image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.star25));
				else if (eval >= 0.5) image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.halfstar25));
				else image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.emptystar25));
				eval -= 1;
			}
		}
		else{
//			No Comment Image
			for(int j=0; j<5; j++){
				image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.emptystar25));
			}
		}
		
		Toast.makeText(getApplicationContext(), "eval = " + tmpeval, Toast.LENGTH_SHORT).show();
		
		text_cafe.setText(cafe);
		
//		int eval = Integer.parseInt(tmpeval);
		
		
		float_eval.setText(tmpeval);
		text_name.setText(menu);


		String url = "http://laputan32.cafe24.com/Eval";
		SnuMenu a = new SnuMenu(menu, cafe, tmpeval, price, classify);
		
		SendServer send = new SendServer(a, url, "2");
		String sendresult = send.send();
		System.out.println("return : " + sendresult);
		
		if (sendresult != null && !sendresult.equals("")) {
			
			//json array parsing
			Comment[] com_arr = new Gson().fromJson(sendresult, Comment[].class);
			for(int ii=0; ii<com_arr.length; ii++){
				System.out.println("comment arr [" + Integer.toString(ii) + "] : " + com_arr[ii].getComment());
			}
			
			if(com_arr.length!=0){
				ArrayAdapter<Comment> madapter1;
				ArrayList<Comment> comarrlist = new ArrayList<Comment>(Arrays.asList(com_arr));
				
				madapter1 = new MyListAdapter(this, R.layout.comment_list_item, R.id.snumenu_detail_comment_text, comarrlist);
				ListView listView1 = (ListView)findViewById(R.id.detail_snu_menu_comment_listview);
				listView1.setAdapter(madapter1);
			
			}
			
//			System.out.println("comment arr [ Id : " + com_arr[0].getId() + " cafe : " + com_arr[0].getCafe() + " menu : " + com_arr[0].getMenu()
//					+ " Date : " + com_arr[0].getDate() + " Rating : " + com_arr[0].getRating() + " recommend : " + com_arr[0].getRecommend());
		}
		

		//////////////////////
		
		Button evalbtn = (Button) findViewById(R.id.detail_snu_menu_do_eval_btn);
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(v.getContext(), EvalSnuMenu.class);
				
				i.putExtra("cafe", cafe);
				i.putExtra("menu", menu);
				i.putExtra("price", price);
				i.putExtra("classify", classify);
				i.putExtra("eval", tmpeval);
				
				startActivity(i);
				
			}
		});
		
	}
}
