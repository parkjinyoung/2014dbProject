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
import comserver.SendServerURL;
import object.Comment;
import object.DeliveryRestaurant;
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
	DeliveryRestaurant del;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaldelivery_layout);
		
		db = new DatabaseHelper(getApplicationContext());
		final String resname = getIntent().getStringExtra("resname");
		/*
		final String menu = getIntent().getStringExtra("menu");
		final String tmpeval = getIntent().getStringExtra("eval");
		
		final String time = getIntent().getStringExtra("time");*/
		final String search = getIntent().getStringExtra("search");

		final String rating = getIntent().getStringExtra("rating");
		final String comment = getIntent().getStringExtra("comment");
		
		del = new DeliveryRestaurant();
		del = db.getDelivery(resname);
		String menu = del.getMenu_url();
		String tmpeval = del.getRating();
		String time = del.getHours();

		
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
				String uno = Integer.toString(myApp.getUno()); // get from db
				
				SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
				Date currentTime = new Date ();
				String mTime = mSimpleDateFormat.format ( currentTime );
				
				System.out.println ("time : " +  mTime );
				
				Comment com = new Comment(resname,"",uno,comment,eval,mTime,0,0);
				com.setDno(del.getDno());
				// (in server) using eval as float instead of string 
				
				//send com to SetEval
				//평가등록 5, 평가받아옴 6, 평가삭제 7, 추천 4 ?
				SendServer send = new SendServer(com, SendServerURL.commentURL, "5");
				String sendresult = send.send();
				
				String geteval = "0";
				System.out.println("comment sendresult = " + sendresult);
				if (sendresult != null && !sendresult.equals("")) {
					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
					geteval = (String) jo.get("rating");
				}
				
					db = new DatabaseHelper(getApplicationContext());
					//if(search!=null&&search.equals("true")) db.updateSearchSnuMenu(cafe, menu, geteval);
					//else 
					db.updateDeliveryEval(resname, geteval);
					db.closeDB();
				Toast.makeText(EvalDelivery.this, comment + " " + eval, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), DeliveryDetails.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				i.putExtra("resname", resname);
				
				startActivity(i);
			}
		});
	}
}
