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

@SuppressLint("ValidFragment")
public class SnuMenuFragment extends Fragment {
	int DETAIL_SNU_MENU_REQUEST=1;
	
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
				Toast.makeText(getActivity(), "Button Click", Toast.LENGTH_SHORT);
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
				SnuRestaurant b = SnuResList.get(groupPosition);
				
				i.putExtra("menu", a.getMenu());
				i.putExtra("eval", a.getEval());
				i.putExtra("resname", b.getName());
				
				startActivity(i);
				// TODO Auto-generated method stub
				return false;
			}
		});

		
		
		return view;
	}
	protected void onCreateData() {
		
		ArrayList<SnuMenu> res1 = new ArrayList<SnuMenu>();
		ArrayList<SnuMenu> res2 = new ArrayList<SnuMenu>();
		SnuResList.add(new SnuRestaurant("restaurant 1", res1));
		SnuResList.add(new SnuRestaurant("restaurant 2", res2));
		res1.add(new SnuMenu("박진영", "4.5"));
		res1.add(new SnuMenu("Soup", "2.0"));
		
		res2.add(new SnuMenu("Pork cutlet", "4.0"));
		res2.add(new SnuMenu("Jajangmyeon", "3.5"));

	}

}
