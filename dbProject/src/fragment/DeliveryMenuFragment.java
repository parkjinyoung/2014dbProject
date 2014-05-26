package fragment;

import java.util.ArrayList;

import object.DeliveryRestaurant;
import object.Delivery_group;
import object.SnuMenu;
import object.SnuRestaurant;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.example.test.DatabaseHelper;
import com.example.test.R;

import delivery_module.DeliveryDetails;
import delivery_module.ExpandableAdapter_delivery;

@SuppressLint("ValidFragment")
public class DeliveryMenuFragment extends Fragment {

	Context mContext;
	//ArrayList<DeliveryRestaurant> deliveryRestaurantarr;
	//ArrayAdapter<CharSequence> adspin;
	//String[] delgrouparr;
	//////////////////////////////////////////////////////
	ArrayList<String> groupArr = new ArrayList<String>();
	ArrayList<ArrayList<DeliveryRestaurant>> resArr = new ArrayList<ArrayList<DeliveryRestaurant>>();

	DatabaseHelper db;
	ArrayList<Delivery_group> DelGroupList;
	ExpandableListView exList;
	ExpandableAdapter_delivery adapter;
//////////////////////////////////////////////////////
	
	public DeliveryMenuFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
///spinner start///
/*		Spinner spinner = (Spinner) view.findViewById(R.id.delgroup_spinner);
		spinner.setPrompt("종류를 선택하세요.");

		// adspin = ArrayAdapter.createFromResource(mContext, R.array.selected,
		// R.layout.spinner_item);

		// adspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		createGroup();
		SpinnerAdapter sAdapter = new SpinnerAdapter(mContext,
				android.R.layout.simple_spinner_item, delgrouparr);
		spinner.setAdapter(sAdapter);
		// spinner.setAdapter(adspin);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// Toast.makeText(mContext, adspin.getItem(arg2) +
				// "을/를 선택 했습니다.", 1).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});*/
///spinner end///

		/*ListView list = (ListView) view.findViewById(R.id.list);
		ArrayAdapter<DeliveryRestaurant> adapter = new DeliveryListAdapter(
				this.mContext, R.layout.delivery_list_item_1, R.id.row_title,
				deliveryRestaurantarr);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				String deliveryName = deliveryRestaurantarr.get(position)
						.getCafe();

				Intent i = new Intent(v.getContext(), DeliveryDetails.class);
				i.putExtra("deliveryName", deliveryName);
				startActivity(i);
			}
		});
*/
		View view = inflater.inflate(R.layout.activity_delivery_menu, null);
		View tmpview = inflater.inflate(R.layout.del_menu_group_row, container);
		DelGroupList = new ArrayList<Delivery_group>();
		onCreateData();

		exList = (ExpandableListView) view.findViewById(R.id.expandablelist);
		adapter = new ExpandableAdapter_delivery(getActivity(), DelGroupList);
		exList.setAdapter(adapter);

		exList.setItemsCanFocus(true);
		exList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {


				DeliveryRestaurant a = DelGroupList.get(groupPosition).getMyres().get(childPosition);
				Delivery_group b = DelGroupList.get(groupPosition);

				Intent i = new Intent(v.getContext(), DeliveryDetails.class);
				i.putExtra("groupName", b.getGroup_name());
				i.putExtra("deliveryName", a.getCafe());
				startActivity(i);
				return false;
			}
		});

		return view;
		
	}

	protected void onCreateData() {

		db = new DatabaseHelper(getActivity());
		groupArr = db.getAllDeliveryGroupName();
		
		for (int j = 0; j < groupArr.size(); j++) {
			resArr.add(j, new ArrayList<DeliveryRestaurant>());
		}

		ArrayList<DeliveryRestaurant> alldelres = db.getAllDelivery();
		for (DeliveryRestaurant del : alldelres) {
			for (int j = 0; j < groupArr.size(); j++) {
				if (del.getGrouping().equals(groupArr.get(j))) {
					resArr.get(j).add(del);
					break;
				}
			}
		}
		for (int j = 0; j < groupArr.size(); j++)
			DelGroupList.add(new Delivery_group(groupArr.get(j), resArr.get(j)));

		db.closeDB();

	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}
		
	

	/*private void createGroup() {
		delgrouparr = new String[3];
		delgrouparr[0] = "전체";
		delgrouparr[1] = "피자";
		delgrouparr[2] = "치킨";
	}

	private void createArr() {
		deliveryRestaurantarr = new ArrayList<DeliveryRestaurant>();
		DeliveryRestaurant a = new DeliveryRestaurant("피자헤븐");
		DeliveryRestaurant b = new DeliveryRestaurant("가마솥도시락");
		DeliveryRestaurant c = new DeliveryRestaurant("파파스치킨");
		deliveryRestaurantarr.add(a);
		deliveryRestaurantarr.add(b);
		deliveryRestaurantarr.add(c);
	}*/
	
/////////////////////////////////////////////////////////////////////////////////////////////
	/*public class SpinnerAdapter extends ArrayAdapter<String> {
		Context context;
		String[] items = new String[] {};

		public SpinnerAdapter(final Context context,
				final int textViewResourceId, final String[] objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
			this.context = context;
		}

		*//**
		 * 스피너 클릭시 보여지는 View의 정의
		 *//*
		@Override
		public View getDropDownView(int position, View convertView,
				ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_dropdown_item, parent,
						false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(20);
			tv.setHeight(50);
			return convertView;
		}

		*//**
		 * 기본 스피너 View 정의
		 *//*
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(context);
				convertView = inflater.inflate(
						android.R.layout.simple_spinner_item, parent, false);
			}

			TextView tv = (TextView) convertView
					.findViewById(android.R.id.text1);
			tv.setText(items[position]);
			tv.setTextColor(Color.BLACK);
			tv.setTextSize(20);
			return convertView;
		}
	}*/
}
