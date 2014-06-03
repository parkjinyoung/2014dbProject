package com.example.test;

import java.util.ArrayList;

import fragment.ConfigFragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

//스누메뉴 즐겨찾기 설정
public class Config_SnuResOnOff extends Activity {
	private DatabaseHelper db;
	private ArrayList<String> allsnures;
	private CustomAdapter mCustomAdapter = null;
	static private String[] res_array = {"220동", "301동", "302동", "감골식당", "공깡", "기숙사(901동)", "기숙사(919동)", "동원관", "상아회관", "서당골(사범대)", "자하연", "전망대(농대)", "학생회관"};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.config_snuresonoff);
		db = new DatabaseHelper(getApplicationContext());
		setLayout();

		allsnures = new ArrayList<String>();
		ini_allsnures();

		mCustomAdapter = new CustomAdapter(Config_SnuResOnOff.this, allsnures);
		mListView.setAdapter(mCustomAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view,
					int position, long id) {
				mCustomAdapter.setChecked(position);
				mCustomAdapter.notifyDataSetChanged();
			}

		});
	}

	//초기화
	private void ini_allsnures() {
		for(int j=0; j<res_array.length; j++)
			allsnures.add(res_array[j]);
	}

	class CustomAdapter extends BaseAdapter {

		private ViewHolder viewHolder = null;
		private LayoutInflater inflater = null;
		private ArrayList<String> sArrayList = new ArrayList<String>();
		private boolean[] isCheckedConfirm;

		public CustomAdapter (Context c, ArrayList<String> mList){
			inflater = LayoutInflater.from(c);
			this.sArrayList = mList;
			this.isCheckedConfirm = new boolean[sArrayList.size()];

			//현재 즐겨찾기 되어있는 식당들은 기본으로 체크
			ArrayList<String> getVisibleRes = db.getVisibleResAll();
			for(int j=0; j<getVisibleRes.size(); j++){
				for(int k=0; k<res_array.length; k++){
					if(getVisibleRes.get(j).equals(res_array[k])) {
						isCheckedConfirm[k] = true;
						break;
					}
				}
			}

		}

		public void setAllChecked(boolean ischeked) {
			int tempSize = isCheckedConfirm.length;
			for(int a=0 ; a<tempSize ; a++){
				isCheckedConfirm[a] = ischeked;
			}
		}

		public void setChecked(int position) {
			isCheckedConfirm[position] = !isCheckedConfirm[position];
		}

		public ArrayList<Integer> getChecked(){
			int tempSize = isCheckedConfirm.length;
			ArrayList<Integer> mArrayList = new ArrayList<Integer>();
			for(int b=0; b<tempSize ; b++){
				if(isCheckedConfirm[b]){
					mArrayList.add(new Integer(b));
				}
			}
			return mArrayList;
		}
		@Override
		public int getCount() {
			return sArrayList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;

			if( v == null ){
				viewHolder = new ViewHolder();
				v = inflater.inflate(R.layout.config_snuresonoff_row, null);
				viewHolder.cBox = (CheckBox) v.findViewById(R.id.main_check_box);
				v.setTag(viewHolder);
			}

			else {
				viewHolder = (ViewHolder)v.getTag();
			}

			viewHolder.cBox.setClickable(false);
			viewHolder.cBox.setFocusable(false);
			viewHolder.cBox.setChecked(true);
			viewHolder.cBox.setText(sArrayList.get(position));
			viewHolder.cBox.setChecked(isCheckedConfirm[position]);

			return v;
		}
	}

	class ViewHolder {
		private CheckBox cBox = null;
	}

	private ListView mListView = null;
	private CheckBox mAllCheckBox = null;
	private Button mSubmitbtn = null;

	private void setLayout(){
		mListView = (ListView) findViewById(R.id.res_onoff_list);
		mSubmitbtn = (Button) findViewById(R.id.res_check_submit);
		//확인 버튼 클릭
		//디비에 새로운 즐겨찾기 식당들 저장
		mSubmitbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				db.deleteVisibleResAll();
				for(int i=0 ; i<mCustomAdapter.getChecked().size() ; i++){
					db.createVisibleRes(allsnures.get(mCustomAdapter.getChecked().get(i)));
				}
				db.closeDB();
				finish();
			}
		});

		mAllCheckBox = (CheckBox) findViewById(R.id.all_res_check_box);
		mAllCheckBox.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mCustomAdapter.setAllChecked(mAllCheckBox.isChecked());
				mCustomAdapter.notifyDataSetChanged();
			}
		});
	}
}
