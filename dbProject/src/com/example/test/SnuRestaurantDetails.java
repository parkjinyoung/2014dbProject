package com.example.test;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SnuRestaurantDetails extends Activity{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.detail_snu_res_layout);
	    
	    ArrayList<String> list1 = new ArrayList<String>();
        list1.add("�좊쭏�좊냽��");
        
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("�좊쭏�좊냽��");
        list2.add("�μ닔�섎냽��");
        list2.add("�띿옣");
        
        // �대뙌�곗뿉 �곗씠���ы븿
        ArrayAdapter<String> adapter1;
        adapter1 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, list1);
        ArrayAdapter<String> adapter2;
        adapter2 = new ArrayAdapter<String>(this,  android.R.layout.simple_list_item_1, list2);
        
        // 由ъ뒪�몃럭���대뙌���곌껐
        ListView listView1 = (ListView)findViewById(R.id.detail_snu_res_todaymenu);
        listView1.setAdapter(adapter1);
        
        ListView listView2 = (ListView)findViewById(R.id.detail_snu_res_totalmenu);
        listView2.setAdapter(adapter2);
	}
}
