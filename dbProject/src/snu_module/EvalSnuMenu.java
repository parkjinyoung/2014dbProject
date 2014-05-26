package snu_module;

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

public class EvalSnuMenu extends Activity{
	DatabaseHelper db;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evalsnumenu_layout);
		
		final String menu = getIntent().getStringExtra("menu");
		final String tmpeval = getIntent().getStringExtra("eval");
		final String cafe = getIntent().getStringExtra("cafe");
		final int price = getIntent().getIntExtra("price", 0);
		final String time = getIntent().getStringExtra("time");
		final String search = getIntent().getStringExtra("search");
		System.out.println("eval search = " + search);

		final String rating = getIntent().getStringExtra("rating");
		final String comment = getIntent().getStringExtra("comment");
		
		TextView menuname = (TextView) findViewById(R.id.eval_snumenu_menu_name);
		TextView cafename = (TextView) findViewById(R.id.eval_snumenu_res_name);
		Button evalbtn = (Button) findViewById(R.id.eval_snumenu_btn);
		final EditText commenttext = (EditText) findViewById(R.id.eval_snumenu_comment);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.eval_snumenu_ratingbar);
		
		if(comment !=null && rating != null){
			commenttext.setText(comment);
			ratingbar.setRating(Float.parseFloat(rating));
		}

		menuname.setText(menu);
		cafename.setText(cafe);
	 
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
				
				Comment com = new Comment(cafe,menu,user_id,comment,eval,mTime,0,0);
				// (in server) using eval as float instead of string 
				
				//send com to SetEval
				//평가등록 1, 평가받아옴 2, 평가삭제 3, 추천 4 
				//String url = "http://laputan32.cafe24.com/Eval";
				SendServer send = new SendServer(com, SendServerURL.commentURL, "1");
				String sendresult = send.send();
				System.out.println("comment insert = " + sendresult);
				
				String geteval = "0";
				
				if (sendresult != null && !sendresult.equals("")) {
					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
					geteval = (String) jo.get("rating");
				}
				
					db = new DatabaseHelper(getApplicationContext());
					if(search!=null&&search.equals("true")) db.updateSearchSnuMenu(cafe, menu, geteval);
					else db.updateSnuMenu(cafe, menu, geteval);
					db.closeDB();
				Toast.makeText(EvalSnuMenu.this, comment + " " + eval, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), SnuMenuDetails.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
				i.putExtra("cafe", cafe);
				i.putExtra("menu", menu);
				i.putExtra("price", price);
				i.putExtra("time", time);
				i.putExtra("eval", geteval);
				i.putExtra("search", search);
				
				startActivity(i);
			}
		});
	}
}
