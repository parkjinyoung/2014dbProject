
package snu_module;

import java.util.ArrayList;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.R.drawable;
import com.example.test.R.id;
import com.example.test.R.layout;

import object.SnuMenu;
import object.SnuRestaurant;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ExpandableAdapter extends BaseExpandableListAdapter{
	// TODAY MENU LIST를 표현하기 위해 사용된 CustomAdapter
	// ExpandableAdapterForSearch와 비슷하지만 여기서는 TODAY MENU TABLE에만 접근함
	Context mContext;
	DatabaseHelper db;
	ArrayList<SnuRestaurant> mSnuResList;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	private ViewTitleHolder viewTitleHolder = null;

	public ExpandableAdapter(Context c, ArrayList <SnuRestaurant> SnuResList){
		super();
		this.inflater = LayoutInflater.from(c);
		this.mContext = c;
		this.mSnuResList = SnuResList;
	}

	@Override
	public SnuMenu getChild(int groupPosition, int childPosition) {
		// 각 Child에 대해 SnuMenu 정보를 받아옴(해당 위치에 대해)
		// TODO Auto-generated method stub
		return mSnuResList.get(groupPosition).getMymenu().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// 각 Child를 어떻게 보여줄지 설정(각각의 메뉴)
		// viewHolder를 매달아줌
		View v = convertView;

		if(v == null){
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.snu_menu_list_row, parent, false);
			viewHolder.title = (TextView) v.findViewById(R.id.row_title);
			//viewHolder.image = (ImageView) v.findViewById(R.id.eval_stars);

			viewHolder.image_eval[0] = (ImageView) v.findViewById(R.id.eval_stars1);
			viewHolder.image_eval[1] = (ImageView) v.findViewById(R.id.eval_stars2);
			viewHolder.image_eval[2] = (ImageView) v.findViewById(R.id.eval_stars3);
			viewHolder.image_eval[3] = (ImageView) v.findViewById(R.id.eval_stars4);
			viewHolder.image_eval[4] = (ImageView) v.findViewById(R.id.eval_stars5);



			viewHolder.price = (TextView) v.findViewById(R.id.snu_todaymenu_price);
			v.setTag(viewHolder);
		}
		else{
			viewHolder = (ViewHolder)v.getTag();
		}
		
		db = new DatabaseHelper(v.getContext());
		String menu = getChild(groupPosition, childPosition).getMenu();
		String cafe = getChild(groupPosition, childPosition).getCafe();
		SnuMenu smenu = new SnuMenu();
		smenu = db.getSnuMenu(cafe, menu);
		
		viewHolder.title.setText(getChild(groupPosition, childPosition).getMenu());
		viewHolder.price.setText(Integer.toString(getChild(groupPosition, childPosition).getPrice()));

		//String tmpeval = getChild(groupPosition, childPosition).getRating();
		String tmpeval = smenu.getRating();
		db.closeDB();
		if(tmpeval!=null){
			float eval = Float.parseFloat(tmpeval);

			for(int j=0; j<5; j++){
				if(eval >= 1) viewHolder.image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.star25));
				else if (eval >= 0.5) viewHolder.image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.halfstar25));
				else viewHolder.image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
				eval -= 1;
			}
			System.out.println("EVAL : " + tmpeval);
		}
		else{
			//				No Comment Image
			System.out.println("EVAL : null");
			for(int j=0; j<5; j++){
				viewHolder.image_eval[j].setImageDrawable(v.getResources().getDrawable(R.drawable.emptystar25));
			}
		}

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mSnuResList.get(groupPosition).getMymenu().size();
	}

	@Override
	public SnuRestaurant getGroup(int groupPosition) {
		return mSnuResList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mSnuResList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}


	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// 식당이 어떻게 보일지 설정
		// 식당의 이름과 식당 상세보기 버튼을 선택
		// 리스트 속에 버튼이 있을시 리스트 클릭과 버튼 클릭이 동시에 안되는 문제를 전부다 해결

		View v = convertView;
		final int grppos = groupPosition;

		if(v == null){
			viewTitleHolder = new ViewTitleHolder();
			v = inflater.inflate(R.layout.snu_menu_group_row, parent, false);
			viewTitleHolder.title = (TextView) v.findViewById(R.id.group_title);


			v.setTag(viewTitleHolder);
		}else{
			viewTitleHolder = (ViewTitleHolder)v.getTag();
		}

		viewTitleHolder.title.setText(getGroup(groupPosition).getName());
		Button detailresbtn = (Button) v.findViewById(R.id.detail_snu_res_btn);
		detailresbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SnuRestaurant a = mSnuResList.get(grppos);

				Intent i = new Intent(v.getContext(), SnuRestaurantDetails.class);

				i.putExtra("cafe", a.getName());

				v.getContext().startActivity(i);

			}
		});

		return v;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

	class ViewHolder {
		public TextView title;
		public TextView price;
		public ImageView[] image_eval = new ImageView[5];
	}

	class ViewTitleHolder{
		public TextView title;
	}


}
