package delivery_module;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import login_module.MyApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;
import com.google.gson.Gson;

import comserver.SendServer;
import object.Comment;
import object.SendServerURL;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EvalDelivery extends Activity{
	DatabaseHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaldelivery_layout);
		
		final String menu = getIntent().getStringExtra("menu");
		final String tmpeval = getIntent().getStringExtra("eval");
		final String time = getIntent().getStringExtra("time");
		
		//final String search = getIntent().getStringExtra("search");
		//System.out.println("eval search = " + search);

		final String rating = getIntent().getStringExtra("rating");
		final String comment = getIntent().getStringExtra("comment");
		final String resname = getIntent().getStringExtra("resname");
		final String group = getIntent().getStringExtra("group");
		System.out.println("take : resname = " + resname);

		
		TextView restxt = (TextView) findViewById(R.id.eval_delivery_res_name);
		Button evalbtn = (Button) findViewById(R.id.eval_delivery_btn);
		final EditText commenttext = (EditText) findViewById(R.id.eval_delivery_comment);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.eval_delivery_ratingbar);
		
		if(comment !=null && rating != null){
			commenttext.setText(comment);
			ratingbar.setRating(Float.parseFloat(rating));
		}

		restxt.setText(resname);
	 
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comment = commenttext.getText().toString();
				String eval = String.valueOf(ratingbar.getRating());
				float rating = ratingbar.getRating();
				
				MyApplication myApp = (MyApplication)getApplicationContext();
				String user_id = myApp.getNickName(); // get from db
				
				SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
				Date currentTime = new Date ();
				String mTime = mSimpleDateFormat.format ( currentTime );
				
				System.out.println ("time : " +  mTime );
				
				Comment com = new Comment(resname,"",user_id,comment,eval,mTime,0,0);
				// (in server) using eval as float instead of string 
				
				//이것도 제대로 구현하기 - delivery comment 랑 snumenu comment 를 같이쓸것인지?
				//send com to SetEval
				//평가등록 1, 평가받아옴 2, 평가삭제 3, 추천 4 
				SendServer send = new SendServer(com, SendServerURL.commentURL, "1");
				String sendresult = send.send();
				System.out.println("comment insert = " + sendresult);
				
				String geteval = "0";
				
				if (sendresult != null && !sendresult.equals("")) {
					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
					geteval = (String) jo.get("rating");
				}
				
					db = new DatabaseHelper(getApplicationContext());
					//디비부분구현해야함.
					//if(search!=null&&search.equals("true")) db.updateSearchSnuMenu(cafe, menu, geteval);
					//else 
					//db.updateSnuMenu(cafe, menu, geteval);
					db.closeDB();
				Toast.makeText(EvalDelivery.this, comment + " " + eval, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), DeliveryDetails.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				i.putExtra("deliveryName", resname);
				
				startActivity(i);
			}
		});
	}
}
