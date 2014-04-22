package fragment;

import java.util.ArrayList;

import object.Comment;
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

import com.example.test.DatabaseHelper;
import com.example.test.ExpandableAdapter;
import com.example.test.R;
import com.example.test.SnuMenuDetails;
import com.example.test.SnuRestaurantDetails;
import com.google.gson.Gson;

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
	ArrayList<SnuMenu> res5 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res6 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res7 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res8 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res9 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res10 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res11 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res12 = new ArrayList<SnuMenu>();
	ArrayList<SnuMenu> res13 = new ArrayList<SnuMenu>();
	
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
				String sendresult = send.send();
				System.out.println("return : " + sendresult);
				
				if (sendresult != null && !sendresult.equals("")) {
					
					//json array parsing
					Comment[] com_arr = new Gson().fromJson(sendresult, Comment[].class);
					for(int ii=0; ii<com_arr.length; ii++){
						System.out.println("comment arr [" + Integer.toString(ii) + "] : " + com_arr[ii].getComment());
					}
					
					System.out.println("comment arr [ Id : " + com_arr[0].getId() + " cafe : " + com_arr[0].getCafe() + " menu : " + com_arr[0].getMenu()
							+ " Date : " + com_arr[0].getDate() + " Rating : " + com_arr[0].getRating() + " recommend : " + com_arr[0].getRecommend());
				}
				
				
				
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
		res5.clear();
		res6.clear();
		res7.clear();
		res8.clear();
		res9.clear();
		res10.clear();
		res11.clear();
		res12.clear();
		res13.clear();

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
			else if(snumenu.getCafe().equals(RES5)){
				res5.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES6)){
				res6.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES7)){
				res7.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES8)){
				res8.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES9)){
				res9.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES10)){
				res10.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES11)){
				res11.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES12)){
				res12.add(snumenu);
			}
			else if(snumenu.getCafe().equals(RES13)){
				res13.add(snumenu);
			}
			else{
				System.out.println("ERROR in gettoday_snf");
			}
			
		}
		
		// when not null 
		SnuResList.add(new SnuRestaurant(RES1, res1));
		SnuResList.add(new SnuRestaurant(RES2, res2));
		SnuResList.add(new SnuRestaurant(RES3, res3));
		SnuResList.add(new SnuRestaurant(RES4, res4));
		SnuResList.add(new SnuRestaurant(RES5, res5));
		SnuResList.add(new SnuRestaurant(RES6, res6));
		SnuResList.add(new SnuRestaurant(RES7, res7));
		SnuResList.add(new SnuRestaurant(RES8, res8));
		SnuResList.add(new SnuRestaurant(RES9, res9));
		SnuResList.add(new SnuRestaurant(RES10, res10));
		SnuResList.add(new SnuRestaurant(RES11, res11));
		SnuResList.add(new SnuRestaurant(RES12, res12));
		SnuResList.add(new SnuRestaurant(RES13, res13));
		
		db.closeDB();

	}

}
