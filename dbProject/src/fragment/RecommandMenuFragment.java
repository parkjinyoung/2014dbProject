package fragment;

import java.util.ArrayList;
import java.util.Arrays;

import object.Comment;
import object.SendServerURL;
import object.SnuMenu;
import object.SnuRestaurant;

import com.example.test.ExpandableAdapter;
import com.example.test.MyListAdapter;
import com.example.test.R;
import com.example.test.R.layout;
import com.google.gson.Gson;

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
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class RecommandMenuFragment extends Fragment {
	
	Context mContext;
	EditText searchtext;
	Button searchbtn;
	Button recommbtn;
	ExpandableListView exList;
	ExpandableAdapter adapter;
	ArrayList<SnuRestaurant> SnuResList;
	
	ArrayList<String> RES = new ArrayList<String>();
	ArrayList<ArrayList<SnuMenu>> res = new ArrayList<ArrayList<SnuMenu>>();
	
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
					Toast.makeText(mContext, "검색어를 입력해주세요", Toast.LENGTH_SHORT).show();
				}
				else{
					SendServer send = new SendServer(SendServerURL.getMenuURL, keyword);
					String sendresult = send.send();
					System.out.println("sendresult : " + sendresult);
					searchtext.setText("");
					
					SnuMenu[] snumenu_arr = null;
					ArrayList<SnuMenu> snumenulist = null;
					
					if (sendresult != null && !sendresult.equals("")) {

						//json array parsing
						snumenu_arr = new Gson().fromJson(sendresult, SnuMenu[].class);
						for(int ii=0; ii<snumenu_arr.length; ii++){
							System.out.println("comment arr [" + Integer.toString(ii) + "] : " + snumenu_arr[ii].getMenu());
						}

						if(snumenu_arr.length!=0){
							
							snumenulist = new ArrayList<SnuMenu>(Arrays.asList(snumenu_arr));

							SnuResList = new ArrayList<SnuRestaurant>();
							
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
							for (int j = 0; j < RES.size(); j++)
								SnuResList.add(new SnuRestaurant(RES.get(j), res.get(j)));

							
							adapter = new ExpandableAdapter(getActivity(), SnuResList);
							exList.setAdapter(adapter);

						}

						//			System.out.println("comment arr [ Id : " + com_arr[0].getId() + " cafe : " + com_arr[0].getCafe() + " menu : " + com_arr[0].getMenu()
						//					+ " Date : " + com_arr[0].getDate() + " Rating : " + com_arr[0].getRating() + " recommend : " + com_arr[0].getRecommend());
					}
				}
			}
		});
		
		return view;
	}
}
