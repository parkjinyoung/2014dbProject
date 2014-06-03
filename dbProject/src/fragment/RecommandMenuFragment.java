package fragment;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import login_module.MyApplication;
import object.DeliveryRestaurant;
import object.Delivery_group;
import object.MenuRecommend;
import object.SnuMenu;
import object.SnuRestaurant;
import snu_module.ExpandableAdapter;
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
	int flag = 0;
	
	ExpandableAdapterforSearch adapter1; // snumenu
	ExpandableAdapter_delivery adapter2; // delivery
	
	ExpandableAdapter adapter3;

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
		rdg.setOnCheckedChangeListener(this); // ������ư�� ���������� ����
		// �����޴����� �˻����� / ������Ŀ��� �˻����� ����
		recommbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				flag = 0;
				// flag=0 �� ��õ / flag=1 �� �˻�
				// ������ ��õ 
				db = new DatabaseHelper(mContext);
				MenuRecommend menurec= new MenuRecommend();
				MyApplication myApp = (MyApplication) mContext;
				String uno = myApp.getUno(); // get from db
				
				// �α��� �Ǿ����� ������ ��� ��� ����
				if(uno.equals("")){
					Toast.makeText(mContext, "�α������ּ���", Toast.LENGTH_SHORT).show();
				}
				else{
				menurec.setVisibleres(db.getVisibleResAll());
				menurec.setUno(uno);
				
				SendServer send = new SendServer(menurec, SendServerURL.getMenuURL);
				String sendresult = send.send();
				System.out.println("sendresult : " + sendresult);
				
				if (sendresult != null && !sendresult.equals("")) {
					JSONObject job = (JSONObject) JSONValue.parse(sendresult);
					String mno = (String) job.get("mno");
					
					// ��õ���� Menu �Ľ��ϴ� �κ�
					SnuMenu sm = db.getSnuMenu(mno);
					
					SnuResList = new ArrayList<SnuRestaurant>();
					
					onCreateData();

					for (int j = 0; j < RES.size(); j++) {
						res.add(j, new ArrayList<SnuMenu>());
					}


					for (int j = 0; j < RES.size(); j++) {
						if (sm.getCafe().equals(RES.get(j))) {
							res.get(j).add(sm);
							break;
						}
					}
					

					for (int j = 0; j < RES.size(); j++) {
						if (res.get(j).size() != 0)
							SnuResList.add(new SnuRestaurant(
									RES.get(j), res.get(j)));
					}

					adapter3 = new ExpandableAdapter(getActivity(), SnuResList);
					exList.setAdapter(adapter3);
					
				}
				}
				db.closeDB();
			}
		});
		
		
		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				// �˻�
				// TODO Auto-generated method stub
				String keyword = searchtext.getText().toString();
				if (keyword.equals("")) {
					Toast.makeText(mContext, "�˻�� �Է����ּ���", Toast.LENGTH_SHORT)
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
						// �˻��� data �Ľ�
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
				// �� List�� item�� ���������� ó��
				// ��õ�� �˻��� ��� ���� �ٸ� adapter�� ����ϱ� ������ flag�� ����
				// �˻��� ExpandableAdapterforSearch �� �̿��ϰ� / todaymenu�� ���ؼ� ExpandableAdapter �̿�
				if (search_identifier == 1 && flag==1) {
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
				} else if (search_identifier == 2 && flag==1) {
					DeliveryRestaurant a = DelGroupList.get(groupPosition)
							.getMyres().get(childPosition);

					Intent i = new Intent(v.getContext(), DeliveryDetails.class);
					i.putExtra("resname", a.getCafe());
					startActivity(i);
				}
				else if(flag==0){
					Intent i = new Intent(v.getContext(), SnuMenuDetails.class);

					SnuMenu a = SnuResList.get(groupPosition).getMymenu()
							.get(childPosition);
					SnuRestaurant b = SnuResList.get(groupPosition);

					i.putExtra("menu", a.getMenu());
					i.putExtra("rating", a.getRating());
					i.putExtra("cafe", a.getCafe());
					i.putExtra("price", a.getPrice());
					i.putExtra("time", a.getTime());
					adapter3.notifyDataSetChanged();
					startActivity(i);
				}
				return false;
			}
		});

		return view;
	}

	public void onCreateData() {
		RES.clear();

		RES.add(0, "220��");
		RES.add(1, "301��");
		RES.add(2, "302��");
		RES.add(3, "����Ĵ�");
		RES.add(4, "����");
		RES.add(5, "�����(901��)");
		RES.add(6, "�����(919��)");
		RES.add(7, "������");
		RES.add(8, "���ȸ��");
		RES.add(9, "�����(�����)");
		RES.add(10, "���Ͽ�");
		RES.add(11, "������(���)");
		RES.add(12, "�л�ȸ��");
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
	public void onCheckedChanged(RadioGroup arg0, int arg1) { // ������ư
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
