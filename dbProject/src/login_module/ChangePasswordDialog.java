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
import android.text.InputType;
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

public class ChangePasswordDialog extends Dialog implements OnClickListener {
	Button sendbtn;
	Button dupbtn;
	EditText mPasswordView;
	String mPassword;
	public ChangePasswordDialog(Context context)
	{
		super(context);

		setContentView(R.layout.changevalue_dialog);
		sendbtn = (Button)findViewById(R.id.change_button);
		mPasswordView = (EditText)findViewById(R.id.value);
		mPasswordView.setHint("������ ��й�ȣ");
		sendbtn.setOnClickListener(this);
	}
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
			mPassword = mPasswordView.getText().toString();
			String url = "http://laputan32.cafe24.com/User";
			UserInfo a = new UserInfo();
			MyApplication myApp = (MyApplication)getOwnerActivity().getApplicationContext();
			a.setUno(myApp.getUno());
			a.setPassword(mPassword);
			SendServer send = new SendServer(a, url, "6");
			String sendresult = send.send();
			JSONObject job = (JSONObject) JSONValue.parse(sendresult);
			String result = (String) job.get("message");
			if(result.equals("success")){
				myApp.setNickName(mPassword);
				new AlertDialog.Builder(getOwnerActivity())
				.setTitle("�Ϸ�!")
				.setMessage("��й�ȣ�� ����Ǿ����ϴ�.")
				.setNeutralButton("�Ϸ�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(null,MyInfoActivity.class);
						getOwnerActivity().startActivity(intent);
					}
				}).show();
			}
			else{
				new AlertDialog.Builder(getOwnerActivity())
				.setTitle("����")
				.setMessage("���� ���³� ���ͳ� ���� ���°� ���� �ʽ��ϴ�.")
				.setNeutralButton("�ݱ�", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
			}
			dismiss();
		}
	}

}