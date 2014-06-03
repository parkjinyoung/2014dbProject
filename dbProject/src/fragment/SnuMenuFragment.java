package fragment;

import java.util.ArrayList;

import object.SnuMenu;
import object.SnuRestaurant;
import snu_module.ExpandableAdapter;
import snu_module.SnuMenuDetails;
import snu_module.SnuRestaurantDetails;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.example.test.R;

@SuppressLint("ValidFragment")
public class SnuMenuFragment extends Fragment {

	ArrayList<String> RES = new ArrayList<String>();
	ArrayList<ArrayList<SnuMenu>> res = new ArrayList<ArrayList<SnuMenu>>();

	int DETAIL_SNU_MENU_REQUEST = 1;
	DatabaseHelper db;
	Context mContext;
	ArrayList<SnuRestaurant> SnuResList;
	ExpandableListView exList;
	ExpandableAdapter adapter;

	public SnuMenuFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_snu_menu, null);
		View tmpview = inflater.inflate(R.layout.snu_menu_group_row, container);
		SnuResList = new ArrayList<SnuRestaurant>();
		onCreateData();

		exList = (ExpandableListView) view.findViewById(R.id.expandablelist);
		Button detailresbtn = (Button) tmpview
				.findViewById(R.id.detail_snu_res_btn);

		adapter = new ExpandableAdapter(getActivity(), SnuResList);
		exList.setAdapter(adapter);

		exList.setItemsCanFocus(true);
		detailresbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(v.getContext(),
						SnuRestaurantDetails.class);
				startActivity(i);
			}
		});

		exList.setOnChildClickListener(new OnChildClickListener() {
			// 각 List의 item들을 눌렀을때 처리 
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				Intent i = new Intent(v.getContext(), SnuMenuDetails.class);

				SnuMenu a = SnuResList.get(groupPosition).getMymenu()
						.get(childPosition);
				SnuRestaurant b = SnuResList.get(groupPosition);

				i.putExtra("menu", a.getMenu());
				i.putExtra("eval", a.getRating());
				i.putExtra("cafe", a.getCafe());
				i.putExtra("price", a.getPrice());
				i.putExtra("time", a.getTime());
//				i.putExtra("search", "");
				
				startActivity(i);
				return false;
			}
		});

		return view;
	}

	protected void onCreateData() {

		// 미리 받아온 data를(MainActivity에서 받음) 
		// 조건에 맞게 list에 추가해줌(즐겨찾기한 식당만)
		db = new DatabaseHelper(getActivity());
		RES = db.getVisibleResAll();
		
		for (int j = 0; j < RES.size(); j++) {
			res.add(j, new ArrayList<SnuMenu>());
		}

		ArrayList<SnuMenu> allsnumenu = db.getAllSnuMenus();
		for (SnuMenu snumenu : allsnumenu) {
			for (int j = 0; j < RES.size(); j++) {
				if (snumenu.getCafe().equals(RES.get(j))) {
					res.get(j).add(snumenu);
					break;
				}
			}
		}
		for (int j = 0; j < RES.size(); j++)
			SnuResList.add(new SnuRestaurant(RES.get(j), res.get(j)));

		db.closeDB();

	}

	@Override
	public void onResume() {
		// 화면이 보일때 마다 List의 바뀐 값이 있으면 update해서 보여줌
		// 코멘트를 삭제하고 뒤로가기 버튼을 눌렀을때 바뀐 별점이 즉시 반영됨
		super.onResume();
		adapter.notifyDataSetChanged();
	}

}