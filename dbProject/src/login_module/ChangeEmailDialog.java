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
		mEmailView.setHint("������ �̸���(mySnu id)");
		sendbtn.setOnClickListener(this);
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
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
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
			if(result.equals("success"))
			{
				SendServer send = new SendServer(a, url, "11");
				sendresult = send.send();
				job = (JSONObject) JSONValue.parse(sendresult);
				result = (String) job.get("message");
				if(result.equals("success")){
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
						.setTitle("�Ϸ�!")
						.setMessage("�̸����� ����Ǿ����ϴ�.")
						.setNeutralButton("�Ϸ�", new DialogInterface.OnClickListener() {
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
						.setTitle("�̸��� ���� ����!")
						.setMessage("�̸����� ����Ǿ����� ������ȣ ���ۿ� �����߽��ϴ�. ������ȣ �������� �ʿ��մϴ�.")
						.setNeutralButton("�Ϸ�", new DialogInterface.OnClickListener() {
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
					.setTitle("����")
					.setMessage("���� ���³� ���ͳ� ���� ���°� ���� �ʽ��ϴ�.")
					.setNeutralButton("�ݱ�", new DialogInterface.OnClickListener() {
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
				mEmailView.setError("�ߺ��� �г����Դϴ�.");
				mEmailView.requestFocus();
			}

		}
	}

}
