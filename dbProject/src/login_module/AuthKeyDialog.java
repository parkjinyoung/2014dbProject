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

//키 인증을 위한 dialog box
public class AuthKeyDialog extends Dialog implements OnClickListener {
	Button sendbtn;
	EditText mKeyView;
	String mKey;
	Context context;
	public AuthKeyDialog(Context context)
	{
		super(context);
		this.context = context;
		setContentView(R.layout.auth_dialog);
		sendbtn = (Button)findViewById(R.id.auth_button);
		mKeyView = (EditText)findViewById(R.id.authkey);
		sendbtn.setOnClickListener(this);
	}
	// 내용 입력하고 버틀 클릭 시, 서버에 인증키와 유저번호를 보내고 성공 여부를 받아옴. 성공 시, 인증됨으로 유저의 정보가 바뀐다.
	
	public void onClick(View view)
	{
		if(view == sendbtn)
		{
			mKey = mKeyView.getText().toString();
			String url = "http://laputan32.cafe24.com/User";
			UserInfo a = new UserInfo();
			MyApplication myApp = (MyApplication)context.getApplicationContext();
			a.setId(myApp.getId());
			a.setUno(myApp.getUno());
			a.setKey(mKey);
			SendServer send = new SendServer(a, url, "5");
			String sendresult = send.send();
			JSONObject job = (JSONObject) JSONValue.parse(sendresult);
			String result = (String) job.get("message");
			if(result.equals("success")){
				myApp.setAuth(true);
				new AlertDialog.Builder(context)
				.setTitle("완료!")
				.setMessage("인증되었습니다.")
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
				.setMessage("인증 키를 다시 한번 확인 해 주세요.")
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
