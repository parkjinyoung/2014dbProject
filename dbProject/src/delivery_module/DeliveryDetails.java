package delivery_module;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import login_module.LoginActivity;
import login_module.MyApplication;
import object.Comment;
import object.DeliveryRestaurant;
import snu_module.MyListAdapter;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.google.gson.Gson;
import comserver.SendServer;
import comserver.SendServerURL;

public class DeliveryDetails extends Activity{
	DatabaseHelper db;
	private AlertDialog mDialog = null;
	String tmpeval = "";
	String time = "";
	String search = "false";
	String group="";
	String menu = "";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		am.restartPackage(getPackageName()); 

		setContentView(R.layout.detail_delivery_layout);

		TextView text_name = (TextView) findViewById(R.id.detail_delivery_name);

		ImageView[] image_eval = new ImageView[5];
		image_eval[0] = (ImageView) findViewById(R.id.detail_del_eval_img1);
		image_eval[1] = (ImageView) findViewById(R.id.detail_del_eval_img2);
		image_eval[2] = (ImageView) findViewById(R.id.detail_del_eval_img3);
		image_eval[3] = (ImageView) findViewById(R.id.detail_del_eval_img4);
		image_eval[4] = (ImageView) findViewById(R.id.detail_del_eval_img5);

		TextView float_eval = (TextView) findViewById(R.id.detail_del_eval_float);

		final String resname = getIntent().getStringExtra("resname");
		/*search = getIntent().getStringExtra("search");
		System.out.println("search = " + search);*/

			db = new DatabaseHelper(getApplicationContext());
			DeliveryRestaurant delres = new DeliveryRestaurant(resname);
			/*if(search!=null && search.equals("true")) 
				snumenu = db.getSearchSnuMenu(cafe, menu);
			else*/
				
			
			delres = db.getDelivery(resname);
			
			db.closeDB();

			tmpeval = delres.getRating();
			time = delres.getHours();
			group = delres.getGrouping();
			menu = delres.getMenu_url();
			
		if(tmpeval!=null){
			float eval = Float.parseFloat(tmpeval);

			for(int j=0; j<5; j++){
				if(eval >= 1) image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.star25));
				else if (eval >= 0.5) image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.halfstar25));
				else image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.emptystar25));
				eval -= 1;
			}
		}
		else{
			for(int j=0; j<5; j++){
				image_eval[j].setImageDrawable(getResources().getDrawable(R.drawable.emptystar25));
			}
		}

		float teval = 0, eval = 0;
		if(tmpeval != null){
			teval = Float.parseFloat(tmpeval);
			eval = (float) Math.floor(teval * 10f) / 10f;
		}
		float_eval.setText(Float.toString(eval));
		text_name.setText(resname);

		Button menubtn = (Button) findViewById(R.id.detail_delivery_menu_btn);
		
		menubtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//메뉴 버튼 클릭했을때 메뉴 보여주기
				
			}
		});
		
		Button sortdatebtn = (Button) findViewById(R.id.comment_sortbydate);
		Button sortrecbtn = (Button) findViewById(R.id.comment_sortbyrec);

		DeliveryRestaurant del = new DeliveryRestaurant();
		del = db.getDelivery(resname);
		SendServer send = new SendServer(del, SendServerURL.commentURL);
		String sendresult = send.send();
		System.out.println("comment sendresult : " + sendresult);
		ArrayAdapter<Comment> madapter1;
		final ListView listView1 = (ListView)findViewById(R.id.detail_delivery_comment_listview);
		Comment[] com_arr = null;
		ArrayList<Comment> comarrlist = null;

		if (sendresult != null && !sendresult.equals("")) {
			com_arr = new Gson().fromJson(sendresult, Comment[].class);
			if(com_arr.length!=0){
				comarrlist = new ArrayList<Comment>(Arrays.asList(com_arr));

				madapter1 = new CommentListAdapter(this, R.layout.comment_list_item, R.id.detail_comment_text, comarrlist, search);
				listView1.setAdapter(madapter1);
			}
		}

		sortdatebtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				DeliveryRestaurant del = new DeliveryRestaurant();
				del = db.getDelivery(resname);
				SendServer send = new SendServer(del, SendServerURL.commentURL);
				String sendresult = send.send();
				
				Comment[] com_arr = null;
				ArrayList<Comment> comarrlist = null;

				if (sendresult != null && !sendresult.equals("")) {

					//json array parsing
					com_arr = new Gson().fromJson(sendresult, Comment[].class);

					if(com_arr.length!=0){

						comarrlist = new ArrayList<Comment>(Arrays.asList(com_arr));
						Collections.sort(comarrlist , dateComparator);
						ArrayAdapter<Comment> madapter2 = new CommentListAdapter(v.getContext(), R.layout.comment_list_item, R.id.detail_comment_text, comarrlist, search);
						listView1.setAdapter(madapter2);

					}
				}
			}
		});

		sortrecbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				DeliveryRestaurant del = new DeliveryRestaurant();
				del = db.getDelivery(resname);
				SendServer send = new SendServer(del, SendServerURL.commentURL);
				String sendresult = send.send();
				
				Comment[] com_arr = null;
				ArrayList<Comment> comarrlist = null;

				if (sendresult != null && !sendresult.equals("")) {

					com_arr = new Gson().fromJson(sendresult, Comment[].class);

					if(com_arr.length!=0){

						comarrlist = new ArrayList<Comment>(Arrays.asList(com_arr));
						Collections.sort(comarrlist , recComparator);
						ArrayAdapter<Comment> madapter2 = new CommentListAdapter(v.getContext(), R.layout.comment_list_item, R.id.detail_comment_text, comarrlist, search);
						listView1.setAdapter(madapter2);

					}
				}
			}
		});
		//////////////////////

		Button evalbtn = (Button) findViewById(R.id.detail_delivery_do_eval_btn);
		evalbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MyApplication myApp=(MyApplication) getApplicationContext();
				if(myApp.getLoginStatus()){

					Intent i = new Intent(v.getContext(), EvalDelivery.class);
					i.putExtra("resname", resname);
				//	i.putExtra("search", search);
					
					startActivity(i);
				}
				else{
					mDialog = createDialog();
					mDialog.show();
				}
			}
		});

	}



	private final static Comparator<Comment> recComparator= new Comparator<Comment>() {
		@Override
		public int compare(Comment object1,Comment object2) {

			int result = 0;
			int i1 = object1.getRecommend() - object1.getUnrecommend();
			int i2 = object2.getRecommend() - object2.getUnrecommend();
			if(i1 > i2){
				result = -1;
			}else if(i1 < i2){
				result = 1;
			}else{
				if(object1.getRecommend() > object2.getRecommend()){
					result = -1;
				}
				else if(object1.getRecommend() < object2.getRecommend())
					result = 1;
				else result = 0;
			}
			return result;
		}
	};

	private final static Comparator<Comment> dateComparator= new Comparator<Comment>() {
		private final Collator collator = Collator.getInstance();
		@Override
		public int compare(Comment object1,Comment object2) {
			System.out.println("comment date compare : o1 = " + object1.getDate() + " o2 = " + object2.getDate() + " ans = " + collator.compare(object2.getDate(), object1.getDate()));
			return collator.compare(object2.getDate(), object1.getDate());

		}
	};

	private AlertDialog createDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("알림");
		ab.setMessage("로그인하신 후 코멘트를 작성하실 수 있습니다.");
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setDismiss(mDialog);
				Intent i = new Intent(getApplicationContext(), LoginActivity.class);
				startActivity(i);

			}
		});

		ab.setNegativeButton("취소", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setDismiss(mDialog);
			}
		});

		return ab.create();
	}
	private void setDismiss(Dialog dialog){
		if(dialog != null && dialog.isShowing())
			dialog.dismiss();
	}
}
