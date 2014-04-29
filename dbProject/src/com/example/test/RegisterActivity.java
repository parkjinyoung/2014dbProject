package com.example.test;

import object.UserInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import comserver.SendServer;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
public class RegisterActivity extends Activity {

	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the register task to ensure we can cancel it if requested.
	 */
	private UserRegisterTask mAuthTask = null;

	// Values for email and password at the time of the register attempt.
	private String mKey;
	private String mId;
	private String mPassword;
	private String mRepeat;
	private String mNickname;
	// UI references.
	private EditText mIdView;
	private EditText mPasswordView;
	private EditText mRepeatView;
	private EditText mNicknameView;

	private View mRegisterFormView;
	private View mRegisterStatusView;
	private TextView mRegisterStatusMessageView;

	private String chkId;
	private String chkNick;

	private boolean nickChk;
	private boolean idChk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_register);
		setupActionBar();

		nickChk = false;
		idChk = false;

		mPasswordView = (EditText) findViewById(R.id.rpassword);

		mIdView = (EditText) findViewById(R.id.rid);

		mNicknameView = (EditText) findViewById(R.id.rnick);

		mRepeatView = (EditText) findViewById(R.id.rrepeat);

		mRegisterFormView = findViewById(R.id.register_form);
		mRegisterStatusView = findViewById(R.id.register_status);
		mRegisterStatusMessageView = (TextView) findViewById(R.id.register_status_message);

		findViewById(R.id.rregister_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptRegister();
					}
				});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	/**duplicate check*/
	public void idCheck(View view)
	{
		chkId = mIdView.getText().toString().toLowerCase();
		/**success*/
		String url = "http://laputan32.cafe24.com/User"; 
		UserInfo a = new UserInfo();
		a.setId(chkId);
		SendServer sender = new SendServer(a,url, "2");
		String sendresult = sender.send();
		JSONObject job = (JSONObject) JSONValue.parse(sendresult);
		String result = (String) job.get("message");
		if(result.equals("success")){

			new AlertDialog.Builder(RegisterActivity.this)
			.setTitle("You can use this Id")
			.setMessage("You can use this Id")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
			idChk = true;
		}
		else
		{
			new AlertDialog.Builder(RegisterActivity.this)
			.setTitle("You can't use this Id")
			.setMessage("Please change your id")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
		}
	}
	public void nickCheck(View view)
	{
		chkNick = mNicknameView.getText().toString().toLowerCase();

		String url = "http://laputan32.cafe24.com/User"; 
		UserInfo a = new UserInfo();
		a.setNickname(chkNick);
		SendServer sender = new SendServer(a,url, "3");
		String sendresult = sender.send();
		JSONObject job = (JSONObject) JSONValue.parse(sendresult);
		String result = (String) job.get("message");
		if(result.equals("success")){

			new AlertDialog.Builder(RegisterActivity.this)
			.setTitle("You can use this nickname")
			.setMessage("You can use this nickname")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
			nickChk = true;
		}
		else
		{
			new AlertDialog.Builder(RegisterActivity.this)
			.setTitle("You can't use this Nickname")
			.setMessage("Please change your Nickname")
			.setNeutralButton("Close", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			}).show();
		}
	}

	/**
	 * Attempts to sign in or register the account specified by the register form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual register attempt is made.
	 */
	public void attemptRegister() {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mPasswordView.setError(null);
		mRepeatView.setError(null);
		mIdView.setError(null);
		mNicknameView.setError(null);

		// Store values at the time of the register attempt.
		mId = mIdView.getText().toString().toLowerCase();
		mPassword = mPasswordView.getText().toString();
		mRepeat = mRepeatView.getText().toString();
		mNickname = mNicknameView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length()>20)
		{
			mPasswordView.setError("PASSWORD LENGTH>20");
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.		
		if (TextUtils.isEmpty(mId)) {
			mIdView.setError(getString(R.string.error_field_required));
			focusView = mIdView;
			cancel = true;
		} else if(!idChk || mId.compareTo(chkId)!=0){
			mIdView.setError("Please check duplication");
			idChk = false;
			focusView = mIdView;
			cancel = true;
		}

		if (TextUtils.isEmpty(mNickname)) {
			mNicknameView.setError(getString(R.string.error_field_required));
			focusView = mNicknameView;
			cancel = true;
		} else if(!nickChk || mNickname.compareTo(chkNick)!=0){
			mNicknameView.setError("Please check duplication");
			nickChk = false;
			focusView = mNicknameView;
			cancel = true;
		}

		if (TextUtils.isEmpty(mRepeat)) {
			mRepeatView.setError(getString(R.string.error_field_required));
			focusView = mRepeatView;
			cancel = true;
		} else if(mRepeat.compareTo(mPassword)!=0){
			mRepeatView.setError("passwords are incorrect");
			focusView = mRepeatView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt register and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user register attempt.
			mRegisterStatusMessageView.setText("Register now");
			showProgress(true);
			mAuthTask = new UserRegisterTask();
			mAuthTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}

	/**
	 * Shows the progress UI and hides the register form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mRegisterStatusView.setVisibility(View.VISIBLE);
			mRegisterStatusView.animate().setDuration(shortAnimTime)
			.alpha(show ? 1 : 0)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mRegisterStatusView.setVisibility(show ? View.VISIBLE
							: View.GONE);
				}
			});

			mRegisterFormView.setVisibility(View.VISIBLE);
			mRegisterFormView.animate().setDuration(shortAnimTime)
			.alpha(show ? 0 : 1)
			.setListener(new AnimatorListenerAdapter() {
				@Override
				public void onAnimationEnd(Animator animation) {
					mRegisterFormView.setVisibility(show ? View.GONE
							: View.VISIBLE);
				}
			});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mRegisterStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous register/registration task used to authenticate
	 * the user.
	 */
	public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {


		String errorTitle;
		String errorMessage;
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.
			errorTitle = "";
			errorMessage = "";
			mKey = keygen();
			Gmail m = new Gmail(mId+"@snu.ac.kr",mKey);
			// TODO: register the new account here.
			if(!m.send()){
				errorTitle = "Fail to send e-mail";
				errorMessage = "Please check your internet connection or e-mail address";
				return false;
			}
			else
			{
				String url = "http://laputan32.cafe24.com/User";
				UserInfo a = new UserInfo(mId,mNickname,mPassword,mKey);
				SendServer send = new SendServer(a,url,"4");
				String sendresult = send.send();
				JSONObject job = (JSONObject) JSONValue.parse(sendresult);
				String result = (String) job.get("message");
				if(result.equals("success"))
				{
					return true;
				}
				else{
					errorTitle = "Fail to connect server";
					errorMessage = "Please check your internet connection";
					return false;
				}
			}
		}

		@SuppressLint("ShowToast")
		@Override
		protected void onPostExecute(final Boolean success) {

			mAuthTask = null;
			showProgress(false);

			if (success) {
				MyApplication myApp = (MyApplication)getApplicationContext();
				myApp.setLoginStatus(true);
				myApp.setAuth(false);
				myApp.setNickName(mNickname);
				myApp.setId(mId);
				new AlertDialog.Builder(RegisterActivity.this)
				.setTitle("Success!")
				.setMessage("Succeed to register. please authorize key in your e-mail to key tab")
				.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				})
				.setNegativeButton("Authenticate", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(RegisterActivity.this,AuthkeyActivity.class);
						startActivity(intent);
						finish();
					}
				})
				.show();
			} else {
				new AlertDialog.Builder(RegisterActivity.this)
				.setTitle(errorTitle)
				.setMessage(errorMessage)
				.setNeutralButton("Close", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				}).show();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}

		private String keygen()
		{
			Integer a1 = (int)(Math.random()*10);
			Integer a2 = (int)(Math.random()*10);
			Integer a3 = (int)(Math.random()*10);
			Integer a4 = (int)(Math.random()*10);
			Integer a5 = (int)(Math.random()*10);
			Integer a6 = (int)(Math.random()*10);
			return a1.toString()+a2.toString()+a3.toString()+a4.toString()+a5.toString()+a6.toString();
		}
	}
}
