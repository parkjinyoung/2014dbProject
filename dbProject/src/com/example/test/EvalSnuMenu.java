package com.example.test;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import comserver.SendServer;
import object.Comment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EvalSnuMenu extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evalsnumenu_layout);
		
		final String menu = getIntent().getStringExtra("menu");
		final String tmpeval = getIntent().getStringExtra("eval");
		final String cafe = getIntent().getStringExtra("cafe");
		final int price = getIntent().getIntExtra("price", 0);
		final String classify = getIntent().getStringExtra("classify");
		
		TextView menuname = (TextView) findViewById(R.id.eval_snumenu_menu_name);
		TextView cafename = (TextView) findViewById(R.id.eval_snumenu_res_name);
		Button evalbtn = (Button) findViewById(R.id.eval_snumenu_btn);
		final EditText commenttext = (EditText) findViewById(R.id.eval_snumenu_comment);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.eval_snumenu_ratingbar);
		


		menuname.setText(menu);
		cafename.setText(cafe);
	 
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comment = commenttext.getText().toString();
				String eval = String.valueOf(ratingbar.getRating());
				float rating = ratingbar.getRating();
				
				String user_id = "yujinee"; // get from db
				
				SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
				Date currentTime = new Date ();
				String mTime = mSimpleDateFormat.format ( currentTime );
				
				System.out.println ("time : " +  mTime );
				
				Comment com = new Comment(cafe,menu,user_id,comment,eval,mTime,0,0);
				// (in server) using eval as float instead of string 
				
				//send com to SetEval
				//평가등록 1, 평가받아옴 2, 평가삭제 3, 추천 4 
				String url = "http://laputan32.cafe24.com/Eval";
				SendServer send = new SendServer(com, url, "1");
				String sendresult = send.send();
				System.out.println("comment insert = " + sendresult);
				
				Toast.makeText(EvalSnuMenu.this, comment + " " + eval, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), SnuMenuDetails.class);
				
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
