package fragment;


import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.R.layout;

import java.util.ArrayList;

import snu_module.SnuRestaurantDetails;
import login_module.AuthkeyActivity;
import login_module.LoginActivity;
import login_module.MyApplication;
import login_module.RegisterActivity;
import login_module.myPreference;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.test.Config_SnuResOnOff;
import com.example.test.R;

@SuppressLint("ValidFragment")
public class ConfigFragment extends Fragment {

	Context mContext;
	MyApplication myApp;
	ArrayList<String> configarr;
	ArrayAdapter<String> adapter;
	View view;
	ListView list;
	public ConfigFragment(Context context) {
		mContext = context;
	}
	@Override
	public void onResume()
	{
		super.onResume();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_config, null);
		myApp = (MyApplication)getActivity().getApplicationContext();

		createArr();

		list = (ListView) view.findViewById(R.id.configlist);
		adapter = new ArrayAdapter<String>(this.mContext, R.layout.config_list_item_1, configarr);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				switch(position){
				//로그아웃
				case 0:
					if(myApp.getLoginStatus()){
						myApp.setLoginStatus(false);
						myApp.setId("");
						myApp.setAuth(false);
						myApp.setNickName("User");
						myPreference m = new myPreference(v.getContext());
						m.put(myPreference.AUTO_LOGIN, false);
						new AlertDialog.Builder(getActivity())
						.setTitle("로그아웃 완료")
						.setMessage("Close")
						.setNeutralButton("Close", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						}).show();
					}
					else
					{
						Intent intent = new Intent(v.getContext(), LoginActivity.class);
						startActivity(intent);
					}
					break;
					//인증/로그인
				case 1:
					if(myApp.getLoginStatus())
					{
						Intent intent = new Intent(v.getContext(), AuthkeyActivity.class);
						startActivity(intent);
					}
					else
					{
						Intent intent = new Intent(v.getContext(), RegisterActivity.class);
						startActivity(intent);
					}
					break;

					//식당 onoff
				case 2:
					Intent i = new Intent(v.getContext(), Config_SnuResOnOff.class);
					startActivity(i);
					break;
					//내 정보 보기
				case 3:
					break;
					//공지사항
				case 4:
					break;
					//개발자 지원
				case 5:
					break;
				default:
					break;
				}
				//String configmenu = configarr.get(position);

				//Intent i = new Intent(v.getContext(), .class);
				//i.putExtra("configmenu", configmenu);
				//startActivity(i);
			}
		});
		return view;
	}
	private void createArr() {
		configarr = new ArrayList<String>();
		configarr.add("로그인/로그아웃");
		configarr.add("회원가입/인증하기");
		configarr.add("식당 on/off");
		configarr.add("내 정보 보기");
		configarr.add("공지사항");
		configarr.add("개발자 지원");
	}
}
