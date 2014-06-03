package delivery_module;

import java.util.ArrayList;

import login_module.MyApplication;
import object.Comment;
import object.RecComment;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.R;

import comserver.SendServer;
import comserver.SendServerURL;
//배달음식점 코멘트 리스트 어댑터
public class CommentListAdapter extends ArrayAdapter<Comment> {
	private ArrayList<Comment> items;
	private int rsrc;
	DatabaseHelper db;
	String search;

	public CommentListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<Comment> objects, String search) {
		super(context, resource, textViewResourceId, objects);
		this.items = objects;
		this.rsrc = resource;
		this.search = search;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;
		if (v == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			v = li.inflate(rsrc, null);
		}

		final TextView rectext = (TextView) v
				.findViewById(R.id.comment_up_count);
		final TextView unrectext = (TextView) v
				.findViewById(R.id.comment_down_count);
		final TextView commentdate = (TextView) v.findViewById(R.id.detail_comment_time);

		final ImageView[] image_eval = new ImageView[5];
		image_eval[0] = (ImageView) v.findViewById(R.id.comment_eval_stars1);
		image_eval[1] = (ImageView) v.findViewById(R.id.comment_eval_stars2);
		image_eval[2] = (ImageView) v.findViewById(R.id.comment_eval_stars3);
		image_eval[3] = (ImageView) v.findViewById(R.id.comment_eval_stars4);
		image_eval[4] = (ImageView) v.findViewById(R.id.comment_eval_stars5);

		Button deletebtn = (Button) v.findViewById(R.id.comment_delete_btn);
		Button modifybtn = (Button) v.findViewById(R.id.comment_modify_btn);

		LinearLayout mycommentlayout = (LinearLayout) v
				.findViewById(R.id.mycomment_btn_layout);

		final Comment e = items.get(position);
		if (e != null) {
			((TextView) v.findViewById(R.id.detail_comment_text))
			.setText(e.getComment());
			((TextView) v.findViewById(R.id.detail_comment_nickname))
			.setText(e.getNickname());

			db = new DatabaseHelper(v.getContext());
			String cafe = db.getDeliveryRes(e.getDno());
			e.setCafe(cafe);
			db.closeDB();
			
			//별점 등록
			String tmpeval = e.getRating();

			if (tmpeval != null) {
				float eval = Float.parseFloat(tmpeval);

				for (int j = 0; j < 5; j++) {
					if (eval >= 1)
						image_eval[j].setImageDrawable(v.getResources()
								.getDrawable(R.drawable.star25));
					else if (eval >= 0.5)
						image_eval[j].setImageDrawable(v.getResources()
								.getDrawable(R.drawable.halfstar25));
					else
						image_eval[j].setImageDrawable(v.getResources()
								.getDrawable(R.drawable.emptystar25));
					eval -= 1;
				}
			} else {
				for (int j = 0; j < 5; j++) {
					image_eval[j].setImageDrawable(v.getResources()
							.getDrawable(R.drawable.emptystar25));
				}
			}

			//내 정보
			MyApplication myApp = (MyApplication) v.getContext()
					.getApplicationContext();

			final String uno = myApp.getUno();

			//내 코멘트에만 수정/삭제 버튼이 보인다.
			mycommentlayout.setVisibility(View.GONE);
			modifybtn.setVisibility(View.GONE);
			deletebtn.setVisibility(View.GONE);

			if (e.getUno().equals(uno)) {
				modifybtn.setVisibility(View.VISIBLE);
				deletebtn.setVisibility(View.VISIBLE);
				mycommentlayout.setVisibility(View.VISIBLE);
			}
			commentdate.setText(e.getDate().substring(0, 4) + "-" + e.getDate().substring(5, 7) + "-" + e.getDate().substring(8, 10));

			rectext.setText(Integer.toString(e.getRecommend()));
			unrectext.setText(Integer.toString(e.getUnrecommend()));

			//추천
			ImageButton recommendbtn = (ImageButton) v.findViewById(R.id.comment_up_btn);
			recommendbtn.setFocusable(true);
			recommendbtn.setClickable(true);

			recommendbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					MyApplication myApp = (MyApplication) v.getContext()
							.getApplicationContext();

					final String uno = myApp.getUno();

					//추천 성공시 success, 실패시 fail 이 온다.
					if(!uno.equals("")){
						RecComment reccom = new RecComment(uno,
								e.getEno(), "true");
						SendServer send = new SendServer(reccom,
								SendServerURL.commentURL);
						String sendresult = send.send();
						String isValid = "";
						if (sendresult != null && !sendresult.equals("")) {
							JSONObject jo = (JSONObject) JSONValue
									.parse(sendresult);
							isValid = (String) jo.get("message");
							if (isValid.equals("success"))
								rectext.setText(Integer.toString(e.getRecommend() + 1));
						}

						System.out.println("recommend true : " + sendresult);
						if(uno.equals(e.getUno())){
							Toast.makeText(getContext(), "자신의 댓글은 추천할 수 없습니다." ,
									Toast.LENGTH_SHORT).show();
						}
						else if(isValid.equals("success")){
							Toast.makeText(getContext(), e.getNickname() + "님의 댓글을 추천하였습니다." ,
									Toast.LENGTH_SHORT).show();
						}
						else if(isValid.equals("fail")){
							Toast.makeText(getContext(), "이미 평가한 댓글입니다." ,
									Toast.LENGTH_SHORT).show();
						}
					}
					else{
						//로그인하지 않았을 경우 추천/비추천 할 수 없다.
						Toast.makeText(getContext(), "로그인해주세요", Toast.LENGTH_SHORT).show();
					}
				}
			});

			//비추천. 추천과 동일한 메커니즘
			ImageButton unrecommendbtn = (ImageButton) v
					.findViewById(R.id.comment_down_btn);
			unrecommendbtn.setFocusable(true);
			unrecommendbtn.setClickable(true);
			unrecommendbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					MyApplication myApp = (MyApplication) v.getContext()
							.getApplicationContext();

					final String uno = myApp.getUno();

					if(!uno.equals("")){

						RecComment reccom = new RecComment(uno,
								e.getEno(), "false");
						SendServer send = new SendServer(reccom,
								SendServerURL.commentURL);
						String sendresult = send.send();
						String isValid = "";
						if (sendresult != null && !sendresult.equals("")) {
							JSONObject jo = (JSONObject) JSONValue
									.parse(sendresult);
							isValid = (String) jo.get("message");
							if (isValid.equals("success"))
								unrectext.setText(Integer.toString(e
										.getUnrecommend() + 1));
						}

						System.out.println("recommend false : " + sendresult);
						if(uno.equals(e.getUno())){
							Toast.makeText(getContext(), "자신의 댓글은 비추천할 수 없습니다." ,
									Toast.LENGTH_SHORT).show();
						}
						else if(isValid.equals("success")){
							Toast.makeText(getContext(), e.getNickname() + "님의 댓글을 비추천하였습니다." ,
									Toast.LENGTH_SHORT).show();
						}
						else if(isValid.equals("fail")){
							Toast.makeText(getContext(), "이미 평가한 댓글입니다." ,
									Toast.LENGTH_SHORT).show();
						}
					}
					else{
						Toast.makeText(getContext(), "로그인해주세요", Toast.LENGTH_SHORT).show();
					}
				}
			});

			//수정
			modifybtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent i = new Intent(v.getContext(), EvalDelivery.class);
//입력된 정보를 가지고 평가 액티비티로 인탠트한다.
					i.putExtra("resname", e.getCafe());
					i.putExtra("comment", e.getComment());
					i.putExtra("rating", e.getRating());
					i.putExtra("search", search);

					v.getContext().startActivity(i);
				}
			});

			//삭제
			deletebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Toast.makeText(getContext(), "삭제", Toast.LENGTH_SHORT)
					.show();

					SendServer send = new SendServer(e,
							SendServerURL.commentURL, "7");
					String sendresult = send.send();

					//삭제시 서버에서는 갱신된 평점을 보내준다. 이를 받아 디비에 저장
					String tmpeval = null;
					if (sendresult != null && !sendresult.equals("")) {
						JSONObject jo = (JSONObject) JSONValue
								.parse(sendresult);
						tmpeval = (String) jo.get("rating");
					}

					Intent i = new Intent(v.getContext(), DeliveryDetails.class);

					db = new DatabaseHelper(v.getContext());

					if (db.getDelivery(e.getCafe()) != null) {
						db.updateDeliveryEval(e.getCafe(), tmpeval);
						i.putExtra("resname", e.getCafe());
						i.putExtra("search", search);

						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						v.getContext().startActivity(i);
					} else {
						i.putExtra("resname", e.getCafe());
						i.putExtra("search", search);

					}
					db.closeDB();
				}
			});
		}

		return v;
	}
}
