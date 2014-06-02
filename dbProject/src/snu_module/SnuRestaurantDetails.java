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
        
        Button locbtn = (Button) findViewById(R.id.detail_snu_res_loc);
        locbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onCreateDialog(0);
				// TODO Auto-generated method stub
				
			}
		});
        
        // 由ъ뒪�몃럭���대뙌���곌껐
        ListView listView1 = (ListView)findViewById(R.id.detail_snu_res_morningmenu);
        listView1.setAdapter(madapter1);
        
        ListView listView2 = (ListView)findViewById(R.id.detail_snu_res_lunchmenu);
        listView2.setAdapter(madapter2);
        
        ListView listView3 = (ListView)findViewById(R.id.detail_snu_res_dinnermenu);
        listView3.setAdapter(madapter3);

        
        listView1.setOnItemClickListener(new OnItemClickListener() {

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
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog = null;
	    switch(id) {
	    case 0:
	        // do the work to define the pause Dialog
//	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	    	builder.setMessage("Are you sure you want to exit?")
//	    	        .setCancelable(false)
//	    	        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//	    	           public void onClick(DialogInterface dialog, int id) {
//	    	           }
//	    	       })
//	    	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
//	    	           public void onClick(DialogInterface dialog, int id) {
//	    	                dialog.cancel();
//	    	           }
//	    	       });
	    	
//	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
//	    	AlertDialog alertDialog;
//	    	Context mContext = getApplicationContext();
//	    	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//	    	View layout = inflater.inflate(R.layout.snuresloc_dialog, (ViewGroup) findViewById(R.layout.detail_snu_res_layout));
//	    	Button dismissbtn = (Button) layout.findViewById(R.id.snuresloc_dismissbtn);
//	    	ImageView image = (ImageView) layout.findViewById(R.id.snuresloc_image);
//	    	image.setImageResource(R.drawable.campusmap_resize);
//	    	builder = new AlertDialog.Builder(mContext);
//	    	builder.setView(layout);
//	    	alertDialog = builder.create();
//	    	alertDialog.show();
	    	
	        break;
	    case 1:
	        // do the work to define the game over Dialog
	        break;
	    default:
	        dialog = null;
	    }
	    return dialog;
	}
}
