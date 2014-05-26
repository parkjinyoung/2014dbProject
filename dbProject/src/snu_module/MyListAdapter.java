package snu_module;

import java.util.ArrayList;

import login_module.MyApplication;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import com.example.test.R.drawable;
import com.example.test.R.id;

import object.Comment;
import object.RecComment;
import object.Search;
import object.SendServerURL;
import object.SnuMenu;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import comserver.SendServer;

public class MyListAdapter extends ArrayAdapter<Comment> {
	private ArrayList<Comment> items;
	private int rsrc;
	DatabaseHelper db;
	String search;

	public MyListAdapter(Context context, int resource, int textViewResourceId,
			ArrayList<Comment> objects, String search) {
		super(context, resource, textViewResourceId, objects);
		this.items = objects;
		this.rsrc = resource;
		this.search = search;
		// TODO Auto-generated constructor stub
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
				// No Comment Image
				for (int j = 0; j < 5; j++) {
					image_eval[j].setImageDrawable(v.getResources()
							.getDrawable(R.drawable.emptystar25));
				}
			}

			MyApplication myApp = (MyApplication) v.getContext()
					.getApplicationContext();

			final String user_id = myApp.getNickName();

			mycommentlayout.setVisibility(View.GONE);
			modifybtn.setVisibility(View.GONE);
			deletebtn.setVisibility(View.GONE);

			if (e.getNickname().equals(user_id)) {
				modifybtn.setVisibility(View.VISIBLE);
				deletebtn.setVisibility(View.VISIBLE);
				mycommentlayout.setVisibility(View.VISIBLE);
				System.out.println("NICKNAME : " + e.getNickname());
			}

			rectext.setText(Integer.toString(e.getRecommend()));
			unrectext.setText(Integer.toString(e.getUnrecommend()));

			Button recommendbtn = (Button) v.findViewById(R.id.comment_up_btn);
			recommendbtn.setFocusable(true);
			recommendbtn.setClickable(true);

			recommendbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					RecComment reccom = new RecComment(user_id,
							e.getNickname(), "true", e.getMenu(), e.getCafe());
					// String url = "http://laputan32.cafe24.com/Eval";
					SendServer send = new SendServer(reccom,
							SendServerURL.commentURL);
					String sendresult = send.send();

					if (sendresult != null && !sendresult.equals("")) {
						JSONObject jo = (JSONObject) JSONValue
								.parse(sendresult);
						String isValid = (String) jo.get("message");
						if (isValid.equals("success"))
							rectext.setText(Integer.toString(e.getRecommend() + 1));
					}

					System.out.println("recommend true : " + sendresult);
					Toast.makeText(getContext(), "��õ " + e.getNickname(),
							Toast.LENGTH_SHORT).show();
				}
			});

			Button unrecommendbtn = (Button) v
					.findViewById(R.id.comment_down_btn);
			unrecommendbtn.setFocusable(true);
			unrecommendbtn.setClickable(true);
			unrecommendbtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					RecComment reccom = new RecComment(user_id,
							e.getNickname(), "false", e.getMenu(), e.getCafe());
					// String url = "http://laputan32.cafe24.com/Eval";
					SendServer send = new SendServer(reccom,
							SendServerURL.commentURL);
					String sendresult = send.send();

					if (sendresult != null && !sendresult.equals("")) {
						JSONObject jo = (JSONObject) JSONValue
								.parse(sendresult);
						String isValid = (String) jo.get("message");
						if (isValid.equals("success"))
							unrectext.setText(Integer.toString(e
									.getUnrecommend() + 1));
					}

					System.out.println("recommend false : " + sendresult);
					Toast.makeText(getContext(), "����õ", Toast.LENGTH_SHORT)
							.show();
				}
			});

			modifybtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					Intent i = new Intent(v.getContext(), EvalSnuMenu.class);

					i.putExtra("cafe", e.getCafe());
					i.putExtra("menu", e.getMenu());
					i.putExtra("comment", e.getComment());
					i.putExtra("rating", e.getRating());
					i.putExtra("search", search);
					Toast.makeText(getContext(), "����", Toast.LENGTH_SHORT)
							.show();

					v.getContext().startActivity(i);
				}
			});

			deletebtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Toast.makeText(getContext(), "����", Toast.LENGTH_SHORT)
							.show();

					// String url = "http://laputan32.cafe24.com/Eval";
					SendServer send = new SendServer(e,
							SendServerURL.commentURL, "3");
					String sendresult = send.send();
					System.out.println("delete send = " + sendresult);

					String tmpeval = null;
					if (sendresult != null && !sendresult.equals("")) {
						JSONObject jo = (JSONObject) JSONValue
								.parse(sendresult);
						tmpeval = (String) jo.get("rating");
					}

					Intent i = new Intent(v.getContext(), SnuMenuDetails.class);

					db = new DatabaseHelper(v.getContext());

					if (search != null && search.equals("true")) {
						if (db.getSearchSnuMenu(e.getCafe(), e.getMenu()) != null) {
							db.updateSearchSnuMenu(e.getCafe(), e.getMenu(),
									tmpeval); // ������
												// eval
							i.putExtra("menu", e.getMenu());
							i.putExtra("cafe", e.getCafe());
							i.putExtra("search", search);

							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							v.getContext().startActivity(i);
						} else {
							i.putExtra("menu", e.getMenu());
							i.putExtra("cafe", e.getCafe());
							i.putExtra("rating", tmpeval);
							i.putExtra("search", search);

						}

					} else {
						if (db.getSnuMenu(e.getCafe(), e.getMenu()) != null) {
							db.updateSnuMenu(e.getCafe(), e.getMenu(), tmpeval); // ������
																					// eval
																					// �־���

							i.putExtra("menu", e.getMenu());
							i.putExtra("cafe", e.getCafe());
							i.putExtra("search", search);

							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							v.getContext().startActivity(i);
						} else {
							i.putExtra("menu", e.getMenu());
							i.putExtra("cafe", e.getCafe());
							i.putExtra("rating", tmpeval);
							i.putExtra("search", search);

						}
					}
					db.closeDB();
				}
			});
		}

		return v;
	}
}