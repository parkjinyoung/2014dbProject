package login_module;

import java.util.ArrayList;

import object.UserInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.test.Config_SnuResOnOff;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;
import com.example.test.R.menu;

import comserver.SendServer;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.os.Build;

public class MyInfoActivity extends Activity {

	Context context;
	MyApplication myApp;
	TextView idView;
	TextView nickView;
	TextView emailView;

	Button passwordBtn;
	Button nickBtn;
	Button emailBtn;
	Button newBtn;
	Button authBtn;
	Button outBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		
		context =this;

		myApp = (MyApplication) getApplicationContext();

		idView = (TextView) findViewById(R.id.infoId);
		nickView = (TextView) findViewById(R.id.infoNick);
		emailView = (TextView) findViewById(R.id.emailinfo);

		passwordBtn = (Button) findViewById(R.id.changePassword);
		nickBtn = (Button) findViewById(R.id.changeNick);
		emailBtn = (Button) findViewById(R.id.changeEmail);
		newBtn = (Button) findViewById(R.id.newauth_button);
		authBtn = (Button) findViewById(R.id.auth);
		outBtn = (Button) findViewById(R.id.deleteId);

		passwordBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						ChangePasswordDialog dialog = new ChangePasswordDialog(context);
						dialog.show();
					}
				});
		nickBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						ChangeNickDialog dialog = new ChangeNickDialog(context);
						dialog.show();
					}
				});
		emailBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						ChangeEmailDialog dialog = new ChangeEmailDialog(context);
						dialog.show();
					}
				});
		newBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						MyApplication myApp = (MyApplication)getApplicationContext();
						String newKey = keygen();
						String mEmail = myApp.getEmail();
						Gmail m = new Gmail(mEmail+"@snu.ac.kr",newKey);
						if(!m.send()){
							new AlertDialog.Builder(context)
							.setTitle("이메일 전송 오류")
							.setMessage("인터넷 연결 혹은 이메일 주소를 다시 학인해 주세요.")
							.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
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
							new AlertDialog.Builder(context)
							.setTitle("완료")
							.setMessage("이메일을 확인해 주세요.")
							.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
								}
							}).show();
						}
					}
				});
		authBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						AuthKeyDialog dialog = new AuthKeyDialog(context);
						dialog.show();
					}
				});
		outBtn.setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						new AlertDialog.Builder(context)
						.setTitle("회원탈퇴")
						.setMessage("정말로 회원을 탈퇴하시겠습니까?")
						.setPositiveButton("네", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String url = "http://laputan32.cafe24.com/User";
								UserInfo a = new UserInfo();
								a.setUno(myApp.getUno());
								SendServer send = new SendServer(a,url,"9");
								String sendresult = send.send();
								JSONObject job = (JSONObject) JSONValue.parse(sendresult);
								String result = (String) job.get("message");
								if(result.equals("success"))
								{
									new AlertDialog.Builder(context)
									.setTitle("완료")
									.setMessage("이용해 주셔서 감사합니다.")
									.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											myApp.clear();
											finish();
											Intent i = new Intent(null,MainActivity.class);
											context.startActivity(i);
										}
									}).show();
								}
								else
								{
									new AlertDialog.Builder(context)
									.setTitle("실패")
									.setMessage("회원탈퇴가 정상적으로 처리되지 않았습니다. 다시 시도해 주세요")
									.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
										}
									}).show();
								}
							}
						})
						.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						}).show();			
					}
				});
		if(myApp.getAuth())
		{
			emailView.setText("인증되었습니다.");
			emailBtn.setVisibility(View.INVISIBLE);
			newBtn.setVisibility(View.INVISIBLE);
			authBtn.setVisibility(View.INVISIBLE);
		}
		else
		{
			emailView.setText("이메일 주소 : " + myApp.getEmail()+"@snu.ac.kr");
		}
		idView.setText("계정  : "+myApp.getId());
		nickView.setText("닉네임 : "+myApp.getNickName());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_info, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
}
