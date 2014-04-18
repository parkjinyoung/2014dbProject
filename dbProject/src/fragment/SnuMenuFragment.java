package fragment;

import java.util.ArrayList;

import object.SnuMenu;
import object.SnuRestaurant;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

import com.example.test.ExpandableAdapter;
import com.example.test.R;
import com.example.test.SnuMenuDetails;
import com.example.test.SnuRestaurantDetails;
import com.example.test.DatabaseHelper;
import comserver.SendServer;

@SuppressLint("ValidFragment")
public class SnuMenuFragment extends Fragment {
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
	
	ArrayList<SnuMenu> res1 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res2 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res3 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res4 = new ArrayList<SnuMenu>();
	
	int DETAIL_SNU_MENU_REQUEST=1;
	DatabaseHelper db;
	Context mContext;
	ArrayList<SnuRestaurant> SnuResList;
	
	public SnuMenuFragment(Context context){
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_snu_menu, null);
		View tmpview = inflater.inflate(R.layout.snu_menu_group_row, container);
		SnuResList = new ArrayList<SnuRestaurant>();
		onCreateData();
		
		ExpandableListView exList = (ExpandableListView)view.findViewById(R.id.expandablelist);
		Button detailresbtn = (Button)tmpview.findViewById(R.id.detail_snu_res_btn);
		
		exList.setAdapter(new ExpandableAdapter(getActivity(), SnuResList));
		
		exList.setItemsCanFocus(true);
		detailresbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("DBDBDBDB","Btn Click Click");
				Toast.makeText(getActivity(), "Button Click", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(v.getContext(), SnuRestaurantDetails.class);
				startActivity(i);
			}
		});
		
		exList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
								
				Intent i = new Intent(v.getContext(), SnuMenuDetails.class);
				
				
				SnuMenu a = SnuResList.get(groupPosition).getMymenu().get(childPosition);
				
				String url = "http://laputan32.cafe24.com/GetEval";
				SendServer send = new SendServer(a, url);	
				System.out.println("return : " + send.send());
				
				SnuRestaurant b = SnuResList.get(groupPosition);
				
				i.putExtra("menu", a.getMenu());
				i.putExtra("eval", a.getEval());
				i.putExtra("resname", b.getName());
				
				// TODO : connect server to get data
				
				//startActivity(i);
				return false;
			}
		});

		
		
		return view;
	}
	protected void onCreateData() {
		db = new DatabaseHelper(getActivity());
		res1.clear();
		res2.clear();
		res3.clear();
		res4.clear();
//		
//		long a1 = db.createTodayMenu(new SnuMenu(RES1 , "JYP", "4.5"));
//		long a2 = db.createTodayMenu(new SnuMenu(RES2 , "JYP1", "2"));
//		long a3 = db.createTodayMenu(new SnuMenu(RES2 , "JYP2", "3"));
//		long a4 = db.createTodayMenu(new SnuMenu(RES3 , "JYP3", "1"));
	/*	
		ArrayList<SnuMenu> res1 = new ArrayList<SnuMenu>();
		ArrayList<SnuMenu> res2 = new ArrayList<SnuMenu>();
		SnuResList.add(new SnuRestaurant("restaurant 1", res1));
		SnuResList.add(new SnuRestaurant("restaurant 2", res2));
		res1.add(new SnuMenu("restaurant 1", "JYP", "4.5"));
		res1.add(new SnuMenu("restaurant 1", "yujinee", "2.0"));
		
		res2.add(new SnuMenu("restaurant 2", "kangdongh", "4.0"));
		res2.add(new SnuMenu("restaurant 2", "laputan", "3.5"));
		*/

		ArrayList<SnuMenu> allsnumenu = db.getAllSnuMenus();
		int i = db.getSnuMenuCount();
		Log.d("SNUMENU" , "Menu Count : " + Integer.toString(i));
//		SnuMenu test = db.getSnuMenu(RES1, "JYP");
//		Log.d("SNUMENU", test.getCafe() + " " + test.getMenu() + " " + test.getEval());
		for(SnuMenu snumenu : allsnumenu){
			Log.d("SNUMENU", "db_test");
			if(snumenu.getCafe().equals(RES1)){
				res1.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES2)){
				res2.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES3)){
				res3.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES4)){
				res4.add(snumenu);
			}
			
		}
		
		// when not null 
		SnuResList.add(new SnuRestaurant(RES1, res1));
		SnuResList.add(new SnuRestaurant(RES2, res2));
		SnuResList.add(new SnuRestaurant(RES3, res3));
		SnuResList.add(new SnuRestaurant(RES4, res4));
		
		db.closeDB();

	}

}
