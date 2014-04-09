package fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.test.DeliveryDetails;
import com.example.test.R;

@SuppressLint("ValidFragment")
public class DeliveryMenuFragment extends Fragment {

	Context mContext;
	String[] deliveryRestaurantarr;

	public DeliveryMenuFragment(Context context) {
		mContext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_delivery_menu, null);

		createArr();

		ListView list = (ListView) view.findViewById(R.id.list);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.mContext,
				R.layout.delivery_list_item_1, deliveryRestaurantarr);
		list.setAdapter(adapter);
		
		list.setOnItemClickListener(new OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	
            	String deliveryName = deliveryRestaurantarr[position];
            	
            	Intent i = new Intent(v.getContext(), DeliveryDetails.class);
            	i.putExtra("deliveryName", deliveryName);
            	startActivity(i);
            }
        });
	
		return view;
	}

	private void createArr() {
		deliveryRestaurantarr[0] = "pizza heaven";
		deliveryRestaurantarr[1] = "pizza maru";
		deliveryRestaurantarr[2] = "pizza school";
	}
}
