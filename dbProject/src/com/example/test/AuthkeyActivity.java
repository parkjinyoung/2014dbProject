package com.example.test;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import comserver.SendServer;
import object.UserInfo;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class AuthkeyActivity extends Activity {

	String mKey;
	EditText mKeyView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authkey);
		setupActionBar();
		mKeyView = (EditText) findViewById(R.id.authkey);
		MyApplication myApp = (MyApplication)getApplicationContext();
		boolean flag = myApp.getAuth();
		if(flag){
			new AlertDialog.Builder(this)
			.setTitle("Already Authenticated")
			.setMessage("Go Back to least Activity")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).show();
		}
		
	}
	
	public void sendKey(View view)
	{
		mKey = mKeyView.getText().toString();
		String url = "http://laputan32.cafe24.com/User";
		UserInfo a = new UserInfo();
		MyApplication myApp = (MyApplication)getApplicationContext();
		if(!myApp.getLoginStauts())
		{
			new AlertDialog.Builder(this)
			.setTitle("Critical Error")
			.setMessage("Please try login first.")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).show();
		}
		a.setId(myApp.getId());
		a.setKey(mKey);
		SendServer send = new SendServer(a, url, "5");
		String sendresult = send.send();
		JSONObject job = (JSONObject) JSONValue.parse(sendresult);
		String result = (String) job.get("message");
		if(result.equals("success")){
			myApp.setAuth(true);
			new AlertDialog.Builder(this)
			.setTitle("Success!")
			.setMessage("Succeed to register.")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					finish();
				}
			}).show();
		}
		else{
			new AlertDialog.Builder(this)
			.setTitle("Failed")
			.setMessage("Failed to register.")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
		}
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

}
