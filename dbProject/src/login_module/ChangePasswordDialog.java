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
import android.text.InputFilter;
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

//비밀번호 변경을 위한 dialog box, 비밀번호 3자 이상인지 확인 -> 변경된 비밀번호 전송
public class ChangePasswordDialog extends Dialog implements OnClickListener {
	Button sendbtn;
	Button dupbtn;
	EditText mPasswordView;
	String mPassword;
	Context context;
	public ChangePasswordDialog(Context context)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.changevalue_dialog);
		sendbtn = (Button)findViewById(R.id.change_button);
		mPasswordView = (EditText)findViewById(R.id.value);
		mPasswordView.setHint("변경할 비밀번호");
		InputFilter[] filterArray = new InputFilter[1];
		filterArray[0] = new InputFilter.LengthFilter(12);
		mPasswordView.setFilters(filterArray);
		sendbtn.setOnClickListener(this);
	}
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
			mPassword = mPasswordView.getText().toString();
			if(mPassword.length()<3){
				mPasswordView.setError("비밀번호가 너무 짧습니다");
				mPasswordView.requestFocus();
			}
			else
			{
				String url = "http://laputan32.cafe24.com/User";
				UserInfo a = new UserInfo();
				MyApplication myApp = (MyApplication)context.getApplicationContext();
				a.setUno(myApp.getUno());
				a.setPassword(mPassword);
				SendServer send = new SendServer(a, url, "6");
				String sendresult = send.send();
				JSONObject job = (JSONObject) JSONValue.parse(sendresult);
				String result = (String) job.get("message");
				if(result.equals("success")){
					new AlertDialog.Builder(context)
					.setTitle("완료!")
					.setMessage("비밀번호가 변경되었습니다.")
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
				}
				dismiss();
			}
		}
	}

}