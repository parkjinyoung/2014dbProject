package delivery_module;

import java.util.ArrayList;

import object.DeliveryRestaurant;
import object.Delivery_group;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.DatabaseHelper;
import com.example.test.R;

public class ExpandableAdapter_delivery extends BaseExpandableListAdapter {

	Context mContext;
	DatabaseHelper db;
	ArrayList<Delivery_group> mDelGroupList;
	private LayoutInflater inflater = null;
	private ViewHolder viewHolder = null;
	private ViewTitleHolder viewTitleHolder = null;

	public ExpandableAdapter_delivery(Context c,
			ArrayList<Delivery_group> DelGroupList) {
		super();
		this.inflater = LayoutInflater.from(c);
		this.mContext = c;
		this.mDelGroupList = DelGroupList;
	}

	@Override
	public DeliveryRestaurant getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mDelGroupList.get(groupPosition).getMyres().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = convertView;

		if (v == null) {
			viewHolder = new ViewHolder();
			v = inflater.inflate(R.layout.del_res_list_row, parent, false);
			viewHolder.title = (TextView) v.findViewById(R.id.row_title);
			// viewHolder.image = (ImageView) v.findViewById(R.id.eval_stars);

			viewHolder.image_eval[0] = (ImageView) v
					.findViewById(R.id.del_eval_stars1);
			viewHolder.image_eval[1] = (ImageView) v
					.findViewById(R.id.del_eval_stars2);
			viewHolder.image_eval[2] = (ImageView) v
					.findViewById(R.id.del_eval_stars3);
			viewHolder.image_eval[3] = (ImageView) v
					.findViewById(R.id.del_eval_stars4);
			viewHolder.image_eval[4] = (ImageView) v
					.findViewById(R.id.del_eval_stars5);

			v.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) v.getTag();
		}

		db = new DatabaseHelper(v.getContext());
		String resname = getChild(groupPosition, childPosition).getCafe();

		DeliveryRestaurant del = new DeliveryRestaurant();
		del = db.getDelivery(resname);

		viewHolder.title.setText(getChild(groupPosition, childPosition)
				.getCafe());

		// String tmpeval = getChild(groupPosition, childPosition).getRating();
		String tmpeval = del.getRating();
		db.closeDB();
		if (tmpeval != null) {
			float eval = Float.parseFloat(tmpeval);

			for (int j = 0; j < 5; j++) {
				if (eval >= 1)
					viewHolder.image_eval[j].setImageDrawable(v.getResources()
							.getDrawable(R.drawable.star25));
				else if (eval >= 0.5)
					viewHolder.image_eval[j].setImageDrawable(v.getResources()
							.getDrawable(R.drawable.halfstar25));
				else
					viewHolder.image_eval[j].setImageDrawable(v.getResources()
							.getDrawable(R.drawable.emptystar25));
				eval -= 1;
			}
		} else {
			for (int j = 0; j < 5; j++) {
				viewHolder.image_eval[j].setImageDrawable(v.getResources()
						.getDrawable(R.drawable.emptystar25));
			}
		}

		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mDelGroupList.get(groupPosition).getMyres().size();
	}

	@Override
	public Delivery_group getGroup(int groupPosition) {
		return mDelGroupList.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mDelGroupList.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		View v = convertView;
		final int grppos = groupPosition;

		if (v == null) {
			viewTitleHolder = new ViewTitleHolder();
			v = inflater.inflate(R.layout.del_menu_group_row, parent, false);
			viewTitleHolder.title = (TextView) v.findViewById(R.id.group_title);
			viewTitleHolder.group_image = (ImageView) v
					.findViewById(R.id.group_image);

			v.setTag(viewTitleHolder);
		} else {
			viewTitleHolder = (ViewTitleHolder) v.getTag();
		}

		viewTitleHolder.title.setText(getGroup(groupPosition).getGroup_name());
		findGroupImage(getGroup(groupPosition).getGroup_name());

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
		public ImageView[] image_eval = new ImageView[5];
	}

	class ViewTitleHolder {
		public TextView title;
		public ImageView group_image;
	}

	private void findGroupImage(String gName) {
		if (gName.equals("피자")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.pizza1);
		} else if (gName.equals("치킨")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.chicken1);
		} else if (gName.equals("한식·분식")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.flour1);
		} else if (gName.equals("중식")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.china1);
		} else if (gName.equals("돈까스·일식")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.pork1);
		} else if (gName.equals("냉면")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.naengmyeon1);
		} else if (gName.equals("도시락")) {
			viewTitleHolder.group_image.setImageResource(R.drawable.dosirak1);
		}

	}

}
