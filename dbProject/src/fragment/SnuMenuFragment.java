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

	ArrayList<String> RES = new ArrayList<String>();
	ArrayList<ArrayList<SnuMenu>> res = new ArrayList<ArrayList<SnuMenu>>();

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
//				
//				String url = "http://laputan32.cafe24.com/GetEval";
//				SendServer send = new SendServer(a, url);
//				String sendresult = send.send();
//				System.out.println("return : " + sendresult);
//				
//				if (sendresult != null && !sendresult.equals("")) {
//					
//					//json array parsing
//					Comment[] com_arr = new Gson().fromJson(sendresult, Comment[].class);
//					for(int ii=0; ii<com_arr.length; ii++){
//						System.out.println("comment arr [" + Integer.toString(ii) + "] : " + com_arr[ii].getComment());
//					}
//					
//					System.out.println("comment arr [ Id : " + com_arr[0].getId() + " cafe : " + com_arr[0].getCafe() + " menu : " + com_arr[0].getMenu()
//							+ " Date : " + com_arr[0].getDate() + " Rating : " + com_arr[0].getRating() + " recommend : " + com_arr[0].getRecommend());
//				}
//				


				SnuRestaurant b = SnuResList.get(groupPosition);

				i.putExtra("menu", a.getMenu());
				i.putExtra("eval", a.getRating());
				i.putExtra("cafe", a.getCafe());
				i.putExtra("price", a.getPrice());
				i.putExtra("classify", a.getClassify());

				// TODO : connect server to get data

				startActivity(i);
				return false;
			}
		});



		return view;
	}
	protected void onCreateData() {

		/*
		RES.add(0, "220동");
		RES.add(1, "301동");
		RES.add(2, "302동");
		RES.add(3, "감골식당");
		RES.add(4, "공깡");
		RES.add(5, "기숙사(901동)");
		RES.add(6, "기숙사(919동)");
		RES.add(7, "동원관");
		RES.add(8, "상아회관");
		RES.add(9, "서당골(사범대)");
		RES.add(10, "자하연");
		RES.add(11, "전망대(농대)");
		RES.add(12, "학생회관");
		*/

		db = new DatabaseHelper(getActivity());
		RES = db.getVisibleResAll();

		for(int j=0; j<RES.size(); j++) {
			res.add(j, new ArrayList<SnuMenu>());
		}

		ArrayList<SnuMenu> allsnumenu = db.getAllSnuMenus();
		int i = db.getSnuMenuCount();
		Log.d("SNUMENU" , "Menu Count : " + Integer.toString(i));
		for(SnuMenu snumenu : allsnumenu){
			Log.d("SNUMENU", "db_test");
			for(int j=0; j<RES.size(); j++){
				if(snumenu.getCafe().equals(RES.get(j))){
					res.get(j).add(snumenu);
				}
			}
		}		
		// when not null 
		for(int j=0; j<RES.size(); j++)
			SnuResList.add(new SnuRestaurant(RES.get(j), res.get(j)));

		db.closeDB();

	}

}