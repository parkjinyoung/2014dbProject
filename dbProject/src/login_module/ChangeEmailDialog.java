package login_module;

import object.UserInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;
import com.example.test.R.menu;
import com.example.test.R.string;

import comserver.SendServer;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.v4.app.NavUtils;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View.OnClickListener;
//이메일 변경시 사용되는 다이얼로그 박스. 이메일 변경 시 1. 이메일 중복 확인 2. 이메일 변경 내용 서버에 전달 3. 새 인증키르 이메일 보내기 및 인증키변경 내용을 서버에 전달
public class ChangeEmailDialog extends Dialog implements OnClickListener {
	Button sendbtn;
	Button dupbtn;
	EditText mEmailView;
	String mEmail;
	Context context;
	MyApplication myApp;
	public ChangeEmailDialog(Context context)
	{
		super(context);
		this.context =context;
		setContentView(R.layout.changevalue_dialog);
		sendbtn = (Button)findViewById(R.id.change_button);
		mEmailView = (EditText)findViewById(R.id.value);
		mEmailView.setHint("변경할 이메일(mySnu id)");
		sendbtn.setOnClickListener(this);
	}
	//새 인증키
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
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
			//중복확인
			mEmail = mEmailView.getText().toString();
			String url = "http://laputan32.cafe24.com/User";
			UserInfo a = new UserInfo();
			myApp = (MyApplication)context.getApplicationContext();
			a.setUno(myApp.getUno());
			a.setEmail(mEmail);
			SendServer sender = new SendServer(a,url, "10");
			String sendresult = sender.send();
			JSONObject job = (JSONObject) JSONValue.parse(sendresult);
			String result = (String) job.get("message");
			//결과
			if(result.equals("success"))
			{
				// 변경된 이메일 서버에 전달
				SendServer send = new SendServer(a, url, "11");
				sendresult = send.send();
				job = (JSONObject) JSONValue.parse(sendresult);
				result = (String) job.get("message");
				if(result.equals("success")){
					//새 인증키 발행 및 메일로 보냄
					myApp.setEmail(mEmail);
					String newKey = keygen();
					String mEmail = myApp.getEmail();
					Gmail m = new Gmail(mEmail+"@snu.ac.kr",newKey);
					a = new UserInfo();
					a.setUno(myApp.getUno());
					a.setKey(newKey);
					send = new SendServer(a,url,"7");
					sendresult = send.send();
					job = (JSONObject) JSONValue.parse(sendresult);
					result = (String) job.get("message");
					if(result.equals("success")&&m.send())
					{
						new AlertDialog.Builder(context)
						.setTitle("완료!")
						.setMessage("이메일이 변경되었습니다.")
						.setNeutralButton("완료", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(context,MyInfoActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(intent);
							}

						}).show();
					}
					else
					{
						new AlertDialog.Builder(context)
						.setTitle("이메일 전송 실패!")
						.setMessage("이메일은 변경되었으나 인증번호 전송에 실패했습니다. 인증번호 재전송이 필요합니다.")
						.setNeutralButton("완료", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent intent = new Intent(null,MyInfoActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(intent);
							}

						}).show();
					}
				}
				else{
					new AlertDialog.Builder(context)
					.setTitle("실패")
					.setMessage("서버 상태나 인터넷 연결 상태가 좋지 않습니다.")
					.setNeutralButton("닫기", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
						}
					}).show();
					dismiss();
				}

				dismiss();
			}
			else
			{
				mEmailView.setError("중복된 닉네임입니다.");
				mEmailView.requestFocus();
			}

		}
	}

}
