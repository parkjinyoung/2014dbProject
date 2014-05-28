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
	ArrayList<String> groupArr = new ArrayList<String>();
	ArrayList<ArrayList<DeliveryRestaurant>> resArr = new ArrayList<ArrayList<DeliveryRestaurant>>();

	DatabaseHelper db;
	ArrayList<Delivery_group> DelGroupList;
	ExpandableListView exList;
	ExpandableAdapter_delivery adapter;

	public DeliveryMenuFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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

				DeliveryRestaurant a = DelGroupList.get(groupPosition)
						.getMyres().get(childPosition);
				//Delivery_group b = DelGroupList.get(groupPosition);

				Intent i = new Intent(v.getContext(), DeliveryDetails.class);
				//i.putExtra("groupName", b.getGroup_name());
				i.putExtra("resname", a.getCafe());
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
			DelGroupList
					.add(new Delivery_group(groupArr.get(j), resArr.get(j)));

		db.closeDB();

	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}

}
