package com.example.test;

import java.util.Locale;

import login_module.MyApplication;
import login_module.myPreference;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.google.gson.Gson;

import object.Comment;
import object.DeliveryRestaurant;
import object.SnuMenu;
import object.UserInfo;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Application;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import comserver.SendServer;
import comserver.SendServerURL;
import fragment.ConfigFragment;
import fragment.DeliveryMenuFragment;
import fragment.RecommandMenuFragment;
import fragment.SnuMenuFragment;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;

	ViewPager mViewPager;
	DatabaseHelper db;
	static String RES1 = "학생회관";
	static String RES2 = "301동";
	static String RES3 = "302동";
	static String RES4 = "감골식당";
	static String RES5 = "공깡";
	static String RES6 = "기숙사(901동)";
	static String RES7 = "기숙사(919동)";
	static String RES8 = "동원관";
	static String RES9 = "상아회관";
	static String RES10 = "서당골(사범대)";
	static String RES11 = "자하연";
	static String RES12 = "전망대(농대)";
	static String RES13 = "학생회관";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		onCreateData();
		onCreateLogin();
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getApplicationContext(), getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	private void onCreateLogin() {
		MyApplication myApp = (MyApplication) this.getApplicationContext();
		;
		myPreference m = new myPreference(getApplicationContext());
		if (m.getValue(myPreference.AUTO_LOGIN, false)) {
			try {
				String url = "http://laputan32.cafe24.com/User";
				String mId = m.getValue(myPreference.USER_ID, "");
				UserInfo a = new UserInfo(mId, m.getValue(
						myPreference.USER_PWD, ""));
				SendServer send = new SendServer(a, url, "1");
				String sendresult = send.send();
				JSONObject job = (JSONObject) JSONValue.parse(sendresult);
				String result = (String) job.get("message");
				if (result.equals("success")) {
					myApp.setNickName((String) job.get("nickname"));
					myApp.setAuth(Boolean.parseBoolean((String) job
							.get("authenticated")));
					myApp.setId(mId);
					myApp.setLoginStatus(true);
					new AlertDialog.Builder(this)
							.setTitle("로그인  성공")
							.setMessage("환영합니다, " + myApp.getNickName() + "님.")
							.setNeutralButton("Close",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				} else {
					new AlertDialog.Builder(this)
							.setTitle("로그인 실패")
							.setMessage("Close")
							.setNeutralButton("Close",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
										}
									}).show();
				}
			} catch (Exception e) {
				new AlertDialog.Builder(this)
						.setTitle("로그인 실패")
						.setMessage("로그인이 실패하였습니다.")
						.setNeutralButton("Close",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		Context mContext;

		public SectionsPagerAdapter(Context context, FragmentManager fm) {
			super(fm);
			mContext = context;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new SnuMenuFragment(mContext);
			case 1:
				return new DeliveryMenuFragment(mContext);
			case 2:
				return new RecommandMenuFragment(mContext);
			case 3:
				return new ConfigFragment(mContext);
			}
			return null;
		}

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			}
			return null;

		}
	}

	protected void onCreateData() {

		db = new DatabaseHelper(getApplicationContext());

		db.deleteSnuMenuAll();
		db.deleteDeliveryAll();

		SendServer send = new SendServer(SendServerURL.getMenuURL);
		String result = send.send();

		SnuMenu[] todaymenu_arr = new Gson().fromJson(result, SnuMenu[].class);
		for (int i = 0; i < todaymenu_arr.length; i++) {

			db.createTodayMenu(todaymenu_arr[i]);
		}
		
		DeliveryRestaurant del = new DeliveryRestaurant("getAllDelivery");
		send = new SendServer(del, SendServerURL.getDeliveryURL);
		result = send.send();
		System.out.println("get delivery all = " + result);

		DeliveryRestaurant[] delarr = new Gson().fromJson(result, DeliveryRestaurant[].class);
		for (int i = 0; i < delarr.length; i++) {

			db.createDelivery(delarr[i]);
		}

		db.closeDB();
	}

}
