package delivery_module;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import login_module.LoginActivity;
import login_module.MyApplication;
import object.Comment;
import object.DeliveryRestaurant;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.google.gson.Gson;

import comserver.SendServer;
import comserver.SendServerURL;
//배달음식 상세 액티비티
public class DeliveryDetails extends Activity {
	DatabaseHelper db;
	private AlertDialog mDialog = null;
	String tmpeval = "";
	String time = "";
	String search = "false";
	String group = "";
	String menu = "";
	DeliveryRestaurant delres;
	static int displayHeight;

	String imgcacheFile = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		am.restartPackage(getPackageName());

		//스마트폰 화면의 높이 구함
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
				.getDefaultDisplay();
		displayHeight = display.getHeight();

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

		db = new DatabaseHelper(getApplicationContext());
		delres = new DeliveryRestaurant(resname);
		delres = db.getDelivery(resname);

		db.closeDB();

		tmpeval = delres.getRating();
		time = delres.getHours();
		group = delres.getGrouping();
		menu = delres.getMenu_url();

		//별점 등록
		if (tmpeval != null) {
			float eval = Float.parseFloat(tmpeval);

			for (int j = 0; j < 5; j++) {
				if (eval >= 1)
					image_eval[j].setImageDrawable(getResources().getDrawable(
							R.drawable.star25));
				else if (eval >= 0.5)
					image_eval[j].setImageDrawable(getResources().getDrawable(
							R.drawable.halfstar25));
				else
					image_eval[j].setImageDrawable(getResources().getDrawable(
							R.drawable.emptystar25));
				eval -= 1;
			}
		} else {
			for (int j = 0; j < 5; j++) {
				image_eval[j].setImageDrawable(getResources().getDrawable(
						R.drawable.emptystar25));
			}
		}

		float teval = 0, eval = 0;
		if (tmpeval != null) {
			teval = Float.parseFloat(tmpeval);
			eval = (float) Math.floor(teval * 10f) / 10f;
		}
		float_eval.setText(Float.toString(eval));
		text_name.setText(resname);

		//메뉴 보기 / 닫기
		final ToggleButton menubtn = (ToggleButton) findViewById(R.id.detail_delivery_menu_btn);

		final ImageView imgView = (ImageView) findViewById(R.id.delmenuimage);
		imgView.setAdjustViewBounds(true);
		
		//이미지 저장할 캐시파일 경로
		imgcacheFile = getApplicationContext().getCacheDir().getAbsolutePath() + "/" + delres.getCafe() + ".png";
		menubtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Bitmap bitmap = null;
				
				//메뉴보기
				if (menubtn.isChecked()) {
					File file = new File(imgcacheFile);
					//처음 보는 메뉴라면 로컬 캐시파일 경로에 저장
					if (!file.exists()) {
						bitmap = getImageFromURL(delres.getMenu_url());
						try {
							FileOutputStream fOut = new FileOutputStream(file);
							bitmap.compress(Bitmap.CompressFormat.PNG, 85, fOut);
							fOut.flush();
							fOut.close();
						} catch (Exception e) {
							e.printStackTrace();
							Log.d(null, "Save file error!");
						}
						//캐시파일이 있다면 디코드해서 보여줌
					} else {
						bitmap = BitmapFactory.decodeFile(imgcacheFile);
					}
				}
				//메뉴닫기 시 null 이 들어감
				imgView.setImageBitmap(bitmap);
			}
		});

		Button sortdatebtn = (Button) findViewById(R.id.comment_sortbydate);
		Button sortrecbtn = (Button) findViewById(R.id.comment_sortbyrec);

		//서버로부터 해당 음식점의 코멘트 리스트 받아옴
		DeliveryRestaurant del = new DeliveryRestaurant();
		del = db.getDelivery(resname);
		SendServer send = new SendServer(del, SendServerURL.commentURL);
		String sendresult = send.send();

		ArrayAdapter<Comment> madapter1;
		final ListView listView1 = (ListView) findViewById(R.id.detail_delivery_comment_listview);
		final TextView emptytext = (TextView) findViewById(R.id.detail_delivery_comment_emptytext);

		Comment[] com_arr = null;
		ArrayList<Comment> comarrlist = null;

		if (sendresult != null && !sendresult.equals("")) {
			com_arr = new Gson().fromJson(sendresult, Comment[].class);
			
			if (com_arr.length != 0) {
				emptytext.setVisibility(View.GONE);
				listView1.setVisibility(View.VISIBLE);
				comarrlist = new ArrayList<Comment>(Arrays.asList(com_arr));

				madapter1 = new CommentListAdapter(this,
						R.layout.comment_list_item, R.id.detail_comment_text,
						comarrlist, search);

				listView1.setAdapter(madapter1);
				setListViewHeightBasedOnChildren(listView1);
				//코멘트가 하나도 없다면 하나도 없다는 텍스트를 보여줌
			} else{
				emptytext.setVisibility(View.VISIBLE);
				listView1.setVisibility(View.GONE);
			}
		}

		//날짜순 정렬
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

					com_arr = new Gson().fromJson(sendresult, Comment[].class);

					if (com_arr.length != 0) {
						emptytext.setVisibility(View.GONE);
						listView1.setVisibility(View.VISIBLE);
						comarrlist = new ArrayList<Comment>(Arrays
								.asList(com_arr));
						Collections.sort(comarrlist, dateComparator);
						ArrayAdapter<Comment> madapter2 = new CommentListAdapter(
								v.getContext(), R.layout.comment_list_item,
								R.id.detail_comment_text, comarrlist, search);
						listView1.setAdapter(madapter2);
						setListViewHeightBasedOnChildren(listView1);
					}
					else{
						emptytext.setVisibility(View.VISIBLE);
						listView1.setVisibility(View.GONE);
					}
					
				}
			}
		});
//추천순 정렬
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

					if (com_arr.length != 0) {
						emptytext.setVisibility(View.GONE);
						listView1.setVisibility(View.VISIBLE);
						comarrlist = new ArrayList<Comment>(Arrays
								.asList(com_arr));
						Collections.sort(comarrlist, recComparator);
						ArrayAdapter<Comment> madapter2 = new CommentListAdapter(
								v.getContext(), R.layout.comment_list_item,
								R.id.detail_comment_text, comarrlist, search);
						listView1.setAdapter(madapter2);
						setListViewHeightBasedOnChildren(listView1);
					}
					else{

						emptytext.setVisibility(View.VISIBLE);
						listView1.setVisibility(View.GONE);
					}
				}
			}
		});

		//나도 평가하기
		Button evalbtn = (Button) findViewById(R.id.detail_delivery_do_eval_btn);
		evalbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MyApplication myApp = (MyApplication) getApplicationContext();
				if (myApp.getLoginStatus()) {

					Intent i = new Intent(v.getContext(), EvalDelivery.class);
					i.putExtra("resname", resname);

					startActivity(i);
					//로그인되어있지 않으면 평가를 등록할 수 없다.
				} else {
					mDialog = createDialog();
					mDialog.show();
				}
			}
		});

	}

	private final static Comparator<Comment> recComparator = new Comparator<Comment>() {
		@Override
		public int compare(Comment object1, Comment object2) {

			int result = 0;
			int i1 = object1.getRecommend() - object1.getUnrecommend();
			int i2 = object2.getRecommend() - object2.getUnrecommend();
			if (i1 > i2) {
				result = -1;
			} else if (i1 < i2) {
				result = 1;
			} else {
				if (object1.getRecommend() > object2.getRecommend()) {
					result = -1;
				} else if (object1.getRecommend() < object2.getRecommend())
					result = 1;
				else
					result = 0;
			}
			return result;
		}
	};

	private final static Comparator<Comment> dateComparator = new Comparator<Comment>() {
		private final Collator collator = Collator.getInstance();

		@Override
		public int compare(Comment object1, Comment object2) {
			return collator.compare(object2.getDate(), object1.getDate());

		}
	};

	//나도 평가하기 클릭 시 로그인되어있지 않을 때 뜨는 다이얼로그
	private AlertDialog createDialog() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("알림");
		ab.setMessage("로그인하신 후 코멘트를 작성하실 수 있습니다.");
		ab.setIcon(getResources().getDrawable(R.drawable.ic_launcher));

		ab.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				setDismiss(mDialog);
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
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

	private void setDismiss(Dialog dialog) {
		if (dialog != null && dialog.isShowing())
			dialog.dismiss();
	}

//해당 url 의 이미지를 비트맵으로 바꿔줌
	public Bitmap getImageFromURL(String imageURL) {
		Bitmap imgBitmap = null;
		HttpURLConnection conn = null;
		BufferedInputStream bis = null;

		try {
			URL url = new URL(imageURL);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();

			int nSize = conn.getContentLength();
			bis = new BufferedInputStream(conn.getInputStream(), nSize);
			imgBitmap = BitmapFactory.decodeStream(bis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return imgBitmap;
	}

	//스크롤뷰 안에 리스트뷰가 있으면 리스트뷰의 높이가 아이템 하나만큼만 보여지는 버그가 있음
	//리스트뷰의 전체 높이와 스마트폰 화면의 0.6 비율을 비교하여 작은 것으로 리스트뷰의 높이를 정함
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int h1 = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1))
				+ 10;
		int h2 = (int) (displayHeight * 0.6);
		params.height = Math.min(h1, h2);
		listView.setLayoutParams(params);
		listView.requestLayout();
	}

}
