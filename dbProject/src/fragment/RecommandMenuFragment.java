package fragment;

import java.util.ArrayList;
import java.util.Arrays;

import object.DeliveryRestaurant;
import object.Delivery_group;
import object.SnuMenu;
import object.SnuRestaurant;
import snu_module.SnuMenuDetails;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.ExpandableAdapterforSearch;
import com.example.test.R;
import com.google.gson.Gson;

import comserver.SendServer;
import comserver.SendServerURL;
import delivery_module.DeliveryDetails;
import delivery_module.ExpandableAdapter_delivery;

@SuppressLint("ValidFragment")
public class RecommandMenuFragment extends Fragment implements
		RadioGroup.OnCheckedChangeListener {

	Context mContext;
	EditText searchtext;
	Button searchbtn;
	Button recommbtn;

	ExpandableListView exList;

	DatabaseHelper db;
	int search_identifier = 1;

	ExpandableAdapterforSearch adapter1; // snumenu
	ExpandableAdapter_delivery adapter2; // delivery

	ArrayList<SnuRestaurant> SnuResList;
	ArrayList<String> RES = new ArrayList<String>();
	ArrayList<ArrayList<SnuMenu>> res = new ArrayList<ArrayList<SnuMenu>>();

	ArrayList<String> groupArr = new ArrayList<String>();
	ArrayList<ArrayList<DeliveryRestaurant>> resArr = new ArrayList<ArrayList<DeliveryRestaurant>>();
	ArrayList<Delivery_group> DelGroupList;
	DeliveryRestaurant[] del_arr = null;

	public RecommandMenuFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_recommand_menu, null);

		searchtext = (EditText) view
				.findViewById(R.id.recommenu_fragment_search);
		searchbtn = (Button) view
				.findViewById(R.id.recommenu_fragment_searchbtn);
		recommbtn = (Button) view
				.findViewById(R.id.recommenu_fragment_recombtn);
		exList = (ExpandableListView) view.findViewById(R.id.recommenu_exlist);

		RadioGroup rdg = (RadioGroup) view.findViewById(R.id.search_choose);
		rdg.setOnCheckedChangeListener(this); // 라디오버튼을 눌렸을때의 반응

		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = searchtext.getText().toString();
				if (keyword.equals("")) {
					Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT)
							.show();
				} else if (search_identifier == 1) {
					SendServer send = new SendServer(SendServerURL.getMenuURL,
							keyword);
					String sendresult = send.send();
					System.out.println("sendresult : " + sendresult);
					searchtext.setText("");

					SnuMenu[] snumenu_arr = null;
					ArrayList<SnuMenu> snumenulist = null;
					db = new DatabaseHelper(mContext);

					if (sendresult != null && !sendresult.equals("")) {
						// json array parsing
						snumenu_arr = new Gson().fromJson(sendresult,
								SnuMenu[].class);
						db.deleteSearchSnuMenuAll();
						/*
						 * for(int ii=0; ii<snumenu_arr.length; ii++){
						 * System.out.println("comment arr [" +
						 * Integer.toString(ii) + "] : " +
						 * snumenu_arr[ii].getMenu()); }
						 */
						SnuResList = new ArrayList<SnuRestaurant>();
						if (snumenu_arr.length != 0) {

							for (int ii = 0; ii < snumenu_arr.length; ii++) {
								db.createSearchMenu(snumenu_arr[ii]);
							}
							db.closeDB();
							snumenulist = new ArrayList<SnuMenu>(Arrays
									.asList(snumenu_arr));

							onCreateData();

							for (int j = 0; j < RES.size(); j++) {
								res.add(j, new ArrayList<SnuMenu>());
							}

							for (SnuMenu snumenu : snumenulist) {
								for (int j = 0; j < RES.size(); j++) {
									if (snumenu.getCafe().equals(RES.get(j))) {
										res.get(j).add(snumenu);
										break;
									}
								}
							}

							for (int j = 0; j < RES.size(); j++) {
								if (res.get(j).size() != 0)
									SnuResList.add(new SnuRestaurant(
											RES.get(j), res.get(j)));
							}

							adapter1 = new ExpandableAdapterforSearch(
									getActivity(), SnuResList);
							exList.setAdapter(adapter1);

						} else {
							Toast.makeText(mContext, "list reset",
									Toast.LENGTH_SHORT).show();
							SnuResList.clear();
							adapter1 = new ExpandableAdapterforSearch(
									getActivity(), SnuResList);
							exList.setAdapter(adapter1);
						}

						// System.out.println("comment arr [ Id : " +
						// com_arr[0].getId() + " cafe : " +
						// com_arr[0].getCafe() + " menu : " +
						// com_arr[0].getMenu()
						// + " Date : " + com_arr[0].getDate() + " Rating : " +
						// com_arr[0].getRating() + " recommend : " +
						// com_arr[0].getRecommend());
					}
				} else if (search_identifier == 2) {
					System.out.println("delivery_ radio change : " + Integer.toString(search_identifier));

					SendServer send = new SendServer(
							SendServerURL.getDeliveryURL, keyword);
					String sendresult = send.send();
					System.out.println("delivery_ sendresult : " + sendresult);
					searchtext.setText("");

					DelGroupList = new ArrayList<Delivery_group>();

					if (sendresult != null && !sendresult.equals("")) {
						del_arr = new Gson().fromJson(sendresult,
								DeliveryRestaurant[].class);
						if (del_arr.length != 0) {
							onCreateData_delivery();
							adapter2 = new ExpandableAdapter_delivery(
									getActivity(), DelGroupList);
							exList.setAdapter(adapter2);

						} else {
							DelGroupList.clear();
							adapter2 = new ExpandableAdapter_delivery(
									getActivity(), DelGroupList);
							exList.setAdapter(adapter2);
						}

					}
				}
			}
		});

		exList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				if (search_identifier == 1) {
					Intent i = new Intent(v.getContext(), SnuMenuDetails.class);

					SnuMenu a = SnuResList.get(groupPosition).getMymenu()
							.get(childPosition);
					SnuRestaurant b = SnuResList.get(groupPosition);

					i.putExtra("search", "true");
					i.putExtra("menu", a.getMenu());
					i.putExtra("rating", a.getRating());
					i.putExtra("cafe", a.getCafe());
					i.putExtra("price", a.getPrice());
					i.putExtra("time", a.getTime());
					adapter1.notifyDataSetChanged();
					startActivity(i);
				} else if (search_identifier == 2) {
					DeliveryRestaurant a = DelGroupList.get(groupPosition)
							.getMyres().get(childPosition);

					Intent i = new Intent(v.getContext(), DeliveryDetails.class);
					i.putExtra("resname", a.getCafe());
					startActivity(i);
				}
				return false;
			}
		});

		return view;
	}

	public void onCreateData() {
		RES.clear();

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
	}

	protected void onCreateData_delivery() {

		db = new DatabaseHelper(getActivity());
		groupArr = db.getAllDeliveryGroupName();

		for (int j = 0; j < groupArr.size(); j++) {
			resArr.add(j, new ArrayList<DeliveryRestaurant>());
		}

		ArrayList<DeliveryRestaurant> alldelres = new ArrayList<DeliveryRestaurant>(Arrays
				.asList(del_arr));
		for (DeliveryRestaurant del : alldelres) {
			for (int j = 0; j < groupArr.size(); j++) {
				if (del.getGrouping().equals(groupArr.get(j))) {
					resArr.get(j).add(del);
					break;
				}
			}
		}
		for (int j = 0; j < groupArr.size(); j++)
			
			if(resArr.get(j) != null && resArr.get(j).size() > 0)
				DelGroupList
					.add(new Delivery_group(groupArr.get(j), resArr.get(j)));

		db.closeDB();

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) { // 라디오버튼
		// TODO Auto-generated method stub
		switch (arg1) {
		case R.id.rd1_snumenu:
			search_identifier = 1;
			break;
		case R.id.rd2_delivery:
			search_identifier = 2;
			break;
		}
	}
	// @Override
	// public void onResume() {
	// super.onResume();
	// adapter.notifyDataSetChanged();
	// }
}
