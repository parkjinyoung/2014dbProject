package comserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import android.os.AsyncTask;

//서버와 통신
public class Network extends AsyncTask<String, Void, HttpResponse> {
	@Override
	protected void onPreExecute() {

		super.onPreExecute();

	}

	//url 과 인자들을 post 형식으로 보낸다.
	//utf-8로 인코딩
	@Override
	protected HttpResponse doInBackground(String... param) {

		String httpHost = param[0];
		String[] pm = param[1].split("&");
		
		List<BasicNameValuePair> postParam = new ArrayList<BasicNameValuePair>();

		UrlEncodedFormEntity entity = null;

		HttpResponse response = null;
		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000);
		HttpPost httpPost = new HttpPost(httpHost);

		for (int i = 0; i < pm.length; i++) {
			String[] newPm = pm[i].split("=");
			postParam.add(new BasicNameValuePair(newPm[0], newPm[1]));
		}

		try {
			entity = new UrlEncodedFormEntity(postParam, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		httpPost.setEntity(entity);

		try {
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}

	@Override
	protected void onPostExecute(HttpResponse result) {

		super.onPostExecute(result);

	}

}
