package login_module;

import java.util.ArrayList;

import com.example.test.Config_SnuResOnOff;
import com.example.test.R;
import com.example.test.R.id;
import com.example.test.R.layout;
import com.example.test.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MyInfoActivity extends Activity {

	Context mContext;
	MyApplication myApp;
	ArrayList<String> infoarr;
	ArrayAdapter<String> adapter;
	ListView list;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_info);
		myApp = (MyApplication)this.getApplicationContext();

		createArr();

		list = (ListView) findViewById(R.id.configlist);
		adapter = new ArrayAdapter<String>(this.mContext, R.layout.config_list_item_1, infoarr);
		list.setAdapter(adapter);

		list.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

				switch(position){
				//ºñ¹Ð¹øÈ£ º¯°æ
				case 0:
					
					break;
				//Å»Åð
				case 1:
					
					break;
				default:
					break;
				}
			}
		});
	}
	
	private void createArr() {
		infoarr = new ArrayList<String>();
		infoarr.add("°³ÀÎÁ¤º¸ º¯°æ");
		infoarr.add("È¸¿ø Å»Åð");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
}
