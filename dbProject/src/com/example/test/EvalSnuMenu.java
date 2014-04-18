package com.example.test;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class EvalSnuMenu extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evalsnumenu_layout);
		
		String menu = getIntent().getStringExtra("menu");
		String cafe = getIntent().getStringExtra("cafe");
		
		TextView menuname = (TextView) findViewById(R.id.eval_snumenu_menu_name);
		TextView cafename = (TextView) findViewById(R.id.eval_snumenu_res_name);
		Button evalbtn = (Button) findViewById(R.id.eval_snumenu_btn);
		final EditText commenttext = (EditText) findViewById(R.id.eval_snumenu_comment);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.eval_snumenu_ratingbar);
		


		menuname.setText(menu);
		cafename.setText(cafe);
	 
//		ratingbar.setOnRatingBarChangeListener(new 
//	             OnRatingBarChangeListener() {
//	             public void onRatingChanged(RatingBar ratingBar, 
//	                   float rating, boolean fromUser) {
//	     
//	             }
//	       });
		
		evalbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String comment = commenttext.getText().toString();
				String eval = String.valueOf(ratingbar.getRating());	
				Toast.makeText(EvalSnuMenu.this, comment + " " + eval, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
