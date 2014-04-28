package com.example.test;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

import comserver.SendServer;
import object.Comment;
import object.SnuMenu;
import object.UserInfo;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class AuthkeyActivity extends ActionBarActivity {

	
	String mKey;
	
	EditText mKeyView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authkey);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

	}
	
	public void sendKey(View view)
	{
		mKeyView = (EditText) findViewById(R.id.authkey);
		mKey = mKeyView.getText().toString();
		String url = "http://laputan32.cafe24.com/User";
		UserInfo a = new UserInfo();
		a.setId("kangdongh");
		a.setKey(mKey);
		SendServer send = new SendServer(a, url, "5");
		String sendresult = send.send();
		JSONObject job = (JSONObject) JSONValue.parse(sendresult);
		String result = (String) job.get("message");
		if(result.equals("success")){
			new AlertDialog.Builder(this)
			.setTitle("Success!")
			.setMessage("Succeed to register.")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(null,MainActivity.class);
					startActivity(intent);
				}
			}).show();
		}
		else;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.authkey, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_authkey,
					container, false);
			return rootView;
		}
	}

}
