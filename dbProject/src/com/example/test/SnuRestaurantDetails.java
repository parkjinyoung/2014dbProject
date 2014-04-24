package com.example.test;

import java.util.ArrayList;
import object.SnuMenu;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SnuRestaurantDetails extends Activity{
	DatabaseHelper db;
	ArrayList<SnuMenu> allcafemenu;
    ArrayList<SnuMenu> list1 = new ArrayList<SnuMenu>();
    ArrayList<SnuMenu> list2 = new ArrayList<SnuMenu>();
    ArrayList<SnuMenu> list3 = new ArrayList<SnuMenu>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.detail_snu_res_layout);
	
	    String cafe = getIntent().getStringExtra("cafe");
	    Toast.makeText(getApplicationContext(), "cafe : " + cafe, Toast.LENGTH_SHORT).show();
	    if(cafe!=null){
	    	db = new DatabaseHelper(getApplicationContext());
	    	allcafemenu = db.getCafeMenus(cafe);
	    	System.out.println("SNUMENUCOUNT : " + Integer.toString(db.getSnuMenuCount()));
	    	
	    	for(int i=0; i<allcafemenu.size() ;i++){
	    		System.out.println("SNUMENU : " + allcafemenu.get(i).getMenu());
	    		System.out.println("SNUMENU : " + allcafemenu.get(i).getClassify());
	    		
	    		if(allcafemenu.get(i).getClassify().equals("아침")){
	        		list1.add(allcafemenu.get(i));
	        	}
	        	else if(allcafemenu.get(i).getClassify().equals("점심")){
	        		list2.add(allcafemenu.get(i));
	        	}
	        	else if(allcafemenu.get(i).getClassify().equals("저녁")){
	        		list3.add(allcafemenu.get(i));
	        	}
	        	else{
	        		System.out.println("Classify ERROR in " + allcafemenu.get(i).getClassify());
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
	}
}
