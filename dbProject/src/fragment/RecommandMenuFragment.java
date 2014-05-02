package fragment;

import java.util.ArrayList;
import java.util.Arrays;

import object.Comment;
import object.SendServerURL;
import object.SnuMenu;
import object.SnuRestaurant;

import com.example.test.DatabaseHelper;
import com.example.test.ExpandableAdapter;
import com.example.test.ExpandableAdapterforSearch;
import com.example.test.MyListAdapter;
import com.example.test.R;
import com.example.test.SnuMenuDetails;
import com.example.test.R.layout;
import com.google.gson.Gson;

import comserver.SendServer;
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
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;

@SuppressLint("ValidFragment")
public class RecommandMenuFragment extends Fragment {

	Context mContext;
	EditText searchtext;
	Button searchbtn;
	Button recommbtn;
	ExpandableListView exList;
	ExpandableAdapterforSearch adapter;
	ArrayList<SnuRestaurant> SnuResList;
	DatabaseHelper db;

	ArrayList<String> RES = new ArrayList<String>();
	ArrayList<ArrayList<SnuMenu>> res = new ArrayList<ArrayList<SnuMenu>>();

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

		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = searchtext.getText().toString();
				if (keyword.equals("")) {
					Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT)
							.show();
				} else {
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

							for(int ii=0; ii<snumenu_arr.length; ii++){
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

							adapter = new ExpandableAdapterforSearch(
									getActivity(), SnuResList);
							exList.setAdapter(adapter);

						} else {
							Toast.makeText(mContext, "list reset",
									Toast.LENGTH_SHORT).show();
							SnuResList.clear();
							adapter = new ExpandableAdapterforSearch(
									getActivity(), SnuResList);
							exList.setAdapter(adapter);
						}

						// System.out.println("comment arr [ Id : " +
						// com_arr[0].getId() + " cafe : " +
						// com_arr[0].getCafe() + " menu : " +
						// com_arr[0].getMenu()
						// + " Date : " + com_arr[0].getDate() + " Rating : " +
						// com_arr[0].getRating() + " recommend : " +
						// com_arr[0].getRecommend());
					}
				}
			}
		});

		exList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Intent i = new Intent(v.getContext(), SnuMenuDetails.class);

				SnuMenu a = SnuResList.get(groupPosition).getMymenu()
						.get(childPosition);
				SnuRestaurant b = SnuResList.get(groupPosition);

				i.putExtra("search", "true");
				i.putExtra("menu", a.getMenu());
				i.putExtra("rating", a.getRating());
				i.putExtra("cafe", a.getCafe());
				i.putExtra("price", a.getPrice());
				i.putExtra("classify", a.getClassify());
				adapter.notifyDataSetChanged();
				startActivity(i);
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

	// @Override
	// public void onResume() {
	// super.onResume();
	// adapter.notifyDataSetChanged();
	// }
}
