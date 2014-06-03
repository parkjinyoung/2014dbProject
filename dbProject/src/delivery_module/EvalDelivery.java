package delivery_module;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import login_module.MyApplication;
import object.Comment;
import object.DeliveryRestaurant;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.DatabaseHelper;
import com.example.test.R;
import comserver.SendServer;
import comserver.SendServerURL;

//배달음식 평가 액티비티
public class EvalDelivery extends Activity {
	DatabaseHelper db;
	DeliveryRestaurant del;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.evaldelivery_layout);

		db = new DatabaseHelper(getApplicationContext());
		final String resname = getIntent().getStringExtra("resname");
		final String search = getIntent().getStringExtra("search");

		final String rating = getIntent().getStringExtra("rating");
		final String comment = getIntent().getStringExtra("comment");

		del = new DeliveryRestaurant();
		del = db.getDelivery(resname);

		TextView restxt = (TextView) findViewById(R.id.eval_delivery_res_name);
		Button evalbtn = (Button) findViewById(R.id.eval_delivery_btn);
		final EditText commenttext = (EditText) findViewById(R.id.eval_delivery_comment);
		final RatingBar ratingbar = (RatingBar) findViewById(R.id.eval_delivery_ratingbar);

		// 이전의 정보가 있을 경우 (수정) 그대로 보여준다.
		if (comment != null && rating != null) {
			commenttext.setText(comment);
			ratingbar.setRating(Float.parseFloat(rating));
		}

		restxt.setText(resname);

		evalbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String comment = commenttext.getText().toString();
				String eval = String.valueOf(ratingbar.getRating());

				// 내 정보를 가져옴
				MyApplication myApp = (MyApplication) getApplicationContext();
				String uno = myApp.getUno(); // get from db

				SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(
						"yyyy.MM.dd HH:mm:ss", Locale.KOREA);
				Date currentTime = new Date();
				String mTime = mSimpleDateFormat.format(currentTime);

				Comment com = new Comment(resname, "", uno, comment, eval,
						mTime, 0, 0);
				com.setDno(del.getDno());

				SendServer send = new SendServer(com, SendServerURL.commentURL,
						"5");
				String sendresult = send.send();

				// 코멘트 등록시 서버에서는 갱신된 평점을 보내준다. 이를 받아 디비에 업데이트
				String geteval = "0";
				if (sendresult != null && !sendresult.equals("")) {
					JSONObject jo = (JSONObject) JSONValue.parse(sendresult);
					geteval = (String) jo.get("rating");
				}

				db = new DatabaseHelper(getApplicationContext());
				db.updateDeliveryEval(resname, geteval);
				db.closeDB();

				Intent i = new Intent(getApplicationContext(),
						DeliveryDetails.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

				i.putExtra("resname", resname);

				startActivity(i);
			}
		});
	}
}
