package fragment;

import object.SendServerURL;

import com.example.test.R;
import com.example.test.R.layout;

import comserver.SendServer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

@SuppressLint("ValidFragment")
public class RecommandMenuFragment extends Fragment {
	
	Context mContext;
	EditText searchtext;
	Button searchbtn;
	Button recommbtn;
	ExpandableListView exList;
	
	public RecommandMenuFragment(Context context){
		mContext = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_recommand_menu, null);
		
		searchtext = (EditText) view.findViewById(R.id.recommenu_fragment_search);
		searchbtn = (Button) view.findViewById(R.id.recommenu_fragment_searchbtn);
		recommbtn = (Button) view.findViewById(R.id.recommenu_fragment_recombtn);
		exList = (ExpandableListView) view.findViewById(R.id.recommenu_exlist);
		
		searchbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keyword = searchtext.getText().toString();
				if(keyword.equals("")){
					System.out.println("ÀÔ·ÂÇØ");
				}
				else{
					SendServer send = new SendServer(SendServerURL.getMenuURL, keyword);
					String result = send.send();
					System.out.println("sendresult : " + result);
					searchtext.setText("");
				}
			}
		});
		
		return view;
	}
}
