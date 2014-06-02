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

public class ChangeNickDialog extends Dialog implements OnClickListener {
	Button sendbtn;
	Button dupbtn;
	EditText mNickView;
	String mNick;
	Context context;
	public ChangeNickDialog(Context context)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.changevalue_dialog);
		sendbtn = (Button)findViewById(R.id.change_button);
		mNickView = (EditText)findViewById(R.id.value);
		mNickView.setHint("변경할 닉네임");
		sendbtn.setOnClickListener(this);
	}
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
			mNick = mNickView.getText().toString();
			String url = "http://laputan32.cafe24.com/User";
			UserInfo a = new UserInfo();
			MyApplication myApp = (MyApplication)context.getApplicationContext();
			a.setUno(myApp.getUno());
			a.setNickname(mNick);
			SendServer sender = new SendServer(a,url, "3");
			String sendresult = sender.send();
			JSONObject job = (JSONObject) JSONValue.parse(sendresult);
			String result = (String) job.get("message");
			if(result.equals("success"))
			{
				SendServer send = new SendServer(a, url, "8");
				sendresult = send.send();
				job = (JSONObject) JSONValue.parse(sendresult);
				result = (String) job.get("message");
				if(result.equals("success")){
					myApp.setNickName(mNick);
					new AlertDialog.Builder(context)
					.setTitle("완료!")
					.setMessage("닉네임이 변경되었습니다.")
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
				mNickView.setError("중복된 닉네임입니다.");
				mNickView.requestFocus();
			}

		}
	}

}
