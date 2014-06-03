package snu_module;

import java.util.ArrayList;

import login_module.AuthKeyDialog;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;

import object.SnuMenu;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SnuRestaurantDetails extends Activity{
	// 식당 상세정보 화면
	// 식당의 이름과 아침,점심,저녁의 메뉴를 나누어 보여줌 
	DatabaseHelper db;
	ArrayList<SnuMenu> allcafemenu;
    ArrayList<SnuMenu> list1 = new ArrayList<SnuMenu>();
    ArrayList<SnuMenu> list2 = new ArrayList<SnuMenu>();
    ArrayList<SnuMenu> list3 = new ArrayList<SnuMenu>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.detail_snu_res_layout);
	
	    final String cafe = getIntent().getStringExtra("cafe");
//	    Toast.makeText(getApplicationContext(), "cafe : " + cafe, Toast.LENGTH_SHORT).show();
	    TextView cafename = (TextView) findViewById(R.id.detail_snu_res_name);
	    cafename.setText(cafe);
	    cafename.setTextSize(30);
	    if(cafe!=null){
	    	db = new DatabaseHelper(getApplicationContext());
	    	allcafemenu = db.getCafeMenus(cafe);
	    	System.out.println("SNUMENUCOUNT : " + Integer.toString(db.getSnuMenuCount()));
	    	
	    	for(int i=0; i<allcafemenu.size() ;i++){
	    		System.out.println("SNUMENU : " + allcafemenu.get(i).getMenu());
	    		System.out.println("SNUMENU : " + allcafemenu.get(i).getTime());
	    		
	    		if(allcafemenu.get(i).getTime().equals("아침")){
	        		list1.add(allcafemenu.get(i));
	        	}
	        	else if(allcafemenu.get(i).getTime().equals("점심")){
	        		list2.add(allcafemenu.get(i));
	        	}
	        	else if(allcafemenu.get(i).getTime().equals("저녁")){
	        		list3.add(allcafemenu.get(i));
	        	}
	        	else{
	        		System.out.println("Time ERROR in " + allcafemenu.get(i).getTime());
	        	}
	    		
	    	}
	    	db.closeDB();
	    }
	    


        ArrayAdapter<SnuMenu> madapter1;
        ArrayAdapter<SnuMenu> madapter2;
        ArrayAdapter<SnuMenu> madapter3;
        
        madapter1 = new CafeListAdapter(this, R.layout.snu_menu_list_row, R.id.row_title, list1);
        madapter2 = new CafeListAdapter(this, R.layout.snu_menu_list_row, R.id.row_title, list2);
        madapter3 = new CafeListAdapter(this, R.layout.snu_menu_list_row, R.id.row_title, list3);
        
        
        // 由ъ뒪�몃럭���대뙌���곌껐
        ListView listView1 = (ListView)findViewById(R.id.detail_snu_res_morningmenu);
        listView1.setAdapter(madapter1);
        
        ListView listView2 = (ListView)findViewById(R.id.detail_snu_res_lunchmenu);
        listView2.setAdapter(madapter2);
        
        ListView listView3 = (ListView)findViewById(R.id.detail_snu_res_dinnermenu);
        listView3.setAdapter(madapter3);

        
        listView1.setOnItemClickListener(new OnItemClickListener() {
        	// 각각의 list의 item들(SnuMenu)을 눌렀을때 메뉴 상세보기 화면으로 intent
			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				SnuMenu snumenu = (SnuMenu) list.getAdapter().getItem(position);
				Toast.makeText(getApplicationContext(), snumenu.getMenu(), Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), SnuMenuDetails.class);
				
				i.putExtra("cafe", snumenu.getCafe());
				i.putExtra("menu", snumenu.getMenu());
				i.putExtra("price", snumenu.getPrice());
				i.putExtra("time", snumenu.getTime());
				i.putExtra("eval", snumenu.getRating());
				
				startActivity(i);
			}
        	
		});
        listView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				SnuMenu snumenu = (SnuMenu) list.getAdapter().getItem(position);
				Toast.makeText(getApplicationContext(), snumenu.getMenu(), Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), SnuMenuDetails.class);
				
				i.putExtra("cafe", snumenu.getCafe());
				i.putExtra("menu", snumenu.getMenu());
				i.putExtra("price", snumenu.getPrice());
				i.putExtra("time", snumenu.getTime());
				i.putExtra("eval", snumenu.getRating());
				
				startActivity(i);
			}
        	
		});
        listView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> list, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				SnuMenu snumenu = (SnuMenu) list.getAdapter().getItem(position);
				Toast.makeText(getApplicationContext(), snumenu.getMenu(), Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(getApplicationContext(), SnuMenuDetails.class);
				
				i.putExtra("cafe", snumenu.getCafe());
				i.putExtra("menu", snumenu.getMenu());
				i.putExtra("price", snumenu.getPrice());
				i.putExtra("time", snumenu.getTime());
				i.putExtra("eval", snumenu.getRating());
				
				startActivity(i);
			}
        	
		});
        
	}
}
