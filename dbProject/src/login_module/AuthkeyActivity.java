package login_module;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;

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
	private String keygen()
	{
		Integer a1 = (int)(Math.random()*10);
		Integer a2 = (int)(Math.random()*10);
		Integer a3 = (int)(Math.random()*10);
		Integer a4 = (int)(Math.random()*10);
		Integer a5 = (int)(Math.random()*10);
		Integer a6 = (int)(Math.random()*10);
		return a1.toString()+a2.toString()+a3.toString()+a4.toString()+a5.toString()+a6.toString();
	}
	public void newAuth(View view)
	{
		MyApplication myApp = (MyApplication)getApplicationContext();
		String newKey = keygen();
		String mId = myApp.getId();
		Gmail m = new Gmail(mId+"@snu.ac.kr",newKey);
		if(!m.send()){
			new AlertDialog.Builder(this)
			.setTitle("Error on sending e-mail")
			.setMessage("Please try again after check internet connection")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
				}
			}).show();
		}
		String url = "http://laputan32.cafe24.com/User";
		UserInfo a = new UserInfo();
		a.setUno(myApp.getUno());
		a.setKey(newKey);
		SendServer send = new SendServer(a,url,"7");
		String sendresult = send.send();
		JSONObject job = (JSONObject) JSONValue.parse(sendresult);
		String result = (String) job.get("message");
		if(result.equals("success"))
		{
			new AlertDialog.Builder(this)
			.setTitle("Succeed")
			.setMessage("Please check your e-mail again")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
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
		if(!myApp.getLoginStatus())
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
		a.setUno(myApp.getUno());
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
