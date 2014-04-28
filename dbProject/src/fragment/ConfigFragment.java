package fragment;

import com.example.test.LoginActivity;
import com.example.test.MainActivity;
import com.example.test.R;
import com.example.test.SnuRestaurantDetails;
import com.example.test.R.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ConfigFragment extends Fragment {

	Context mContext;
	
	public ConfigFragment(Context context){
		mContext = context;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_config, null);
		Button lgbu = (Button)view.findViewById(R.id.login_button);
		lgbu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(ConfigFragment.this.getActivity(),LoginActivity.class);
				startActivity(intent);
			}
		});
		return view;
	}
}
