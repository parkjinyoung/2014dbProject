package comserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import object.Comment;
import object.RecComment;
import object.SnuMenu;
import object.UserInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

import android.os.StrictMode;

import com.google.gson.Gson;

public class SendServer {

	private String SERVER_URL;

	private UserInfo userinfo;
	private Comment comment;
	private SnuMenu snumenu;
	private RecComment reccomment;

	private final String SEND_ERROR = "0";

	private final String USER_SEND_NAME = "signup";
	private final String MENU_SEND_NAME = "menupage";
	private final String INSERT_COMMENT_SEND_NAME = "insertcomment";
	private final String RECOM_COMMENT_SEND_NAME = "recomcomment";
	private final String TODAYMENU_SEND_NAME = "todaymenu";
	private String DATA_NAME = null;
	// 평가등록 1, 평가받아옴 2, 평가삭제 3, 추천 4
	private String identifier = null;

	public SendServer(String url) {
		this.SERVER_URL = url;
		this.DATA_NAME = TODAYMENU_SEND_NAME;
	}

	public SendServer(UserInfo userinfo, String url) {
		this.userinfo = userinfo;
		this.SERVER_URL = url;
		this.DATA_NAME = USER_SEND_NAME;
	}

	public SendServer(Comment comment, String url) {
		this.SERVER_URL = url;
		this.comment = comment;
		this.identifier = "1";

		this.DATA_NAME = INSERT_COMMENT_SEND_NAME;
	}

	public SendServer(RecComment reccomment, String url) {
		this.SERVER_URL = url;
		this.reccomment = reccomment;
		this.identifier = "4";

		this.DATA_NAME = RECOM_COMMENT_SEND_NAME;
	}

	public SendServer(SnuMenu snumenu, String url) {
		this.snumenu = snumenu;
		this.SERVER_URL = url;
		this.DATA_NAME = MENU_SEND_NAME;
		this.identifier = "2";
	}

	public String send() {
		StrictMode.enableDefaults();
		String json = "";
		String parm = "";
		if (identifier != null)
			parm += "identifier=" + identifier + "&";

		if (DATA_NAME.equals(USER_SEND_NAME)) {
			if (userinfo == null)
				return "";

			json = new Gson().toJson(userinfo);

		}

		else if (DATA_NAME.equals(MENU_SEND_NAME)) {
			if (snumenu == null)
				return "";

			json = new Gson().toJson(snumenu);
		}

		else if (DATA_NAME.equals(INSERT_COMMENT_SEND_NAME)) {
			if (comment == null)
				return "";

			json = new Gson().toJson(comment);
		}

		else if (DATA_NAME.equals(RECOM_COMMENT_SEND_NAME)) {
			if (reccomment == null)
				return "";

			json = new Gson().toJson(reccomment);
		}

		else if (DATA_NAME.equals(TODAYMENU_SEND_NAME)) {
			json = "get today menu";
		}

		else {
			return null;
		}

		try {

			HttpResponse result = null;
			parm += "data=" + json;
			System.out.println("parm = " + parm);
			result = new Network().execute(SERVER_URL, parm).get();
			return isSend(result);

		} catch (InterruptedException e) {

			e.printStackTrace();
			return null;

		} catch (ExecutionException e) {

			e.printStackTrace();
			return null;

		}

	}

	private String isSend(HttpResponse rtnResult)

	{
		if (rtnResult == null)
			return null;
		HttpEntity entity = rtnResult.getEntity();
		BufferedReader br;
		String line, result = "";

		try {
			br = new BufferedReader(new InputStreamReader(entity.getContent(),
					"UTF-8"));
			while ((line = br.readLine()) != null) {
				result += line;
			}
			br.close();
			if (!result.isEmpty()) {
				return result;
			} else {

				return null;
			}

		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();

		} catch (IllegalStateException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return null;
	}

	public ArrayList<String> stringtoList(String s) {
		ArrayList<String> ret = new ArrayList<String>();
		if (!s.equals("")) {
			String[] temp = s.split("[,]");
			for (int i = 0; i < temp.length; i++) {
				ret.add(temp[i].trim());
			}
		}

		return ret;
	}

}
