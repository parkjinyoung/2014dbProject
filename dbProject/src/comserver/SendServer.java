package comserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import object.Comment;
import object.DeliveryRestaurant;
import object.MenuRecommend;
import object.RecComment;
import object.Search;
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
	private Search keyword;
	private DeliveryRestaurant del;
	private MenuRecommend menurec;
	
	private final String SEND_ERROR = "0";

	private final String SEARCH_SEND_NAME = "search";
	private final String USER_SEND_NAME = "signup";
	private final String MENU_SEND_NAME = "menupage";
	private final String INSERT_COMMENT_SEND_NAME = "insertcomment";
	private final String RECOM_COMMENT_SEND_NAME = "recomcomment";
	private final String TODAYMENU_REQUEST_NAME = "todaymenu";
	private final String TODAYMENU_RECOMMEND = "todaymenurec";
	private final String DELIVERY_ALL_REQUEST_NAME = "delivery_all_request";
	private final String DELIVERY_REQUEST_NAME = "one_res_request";
	private String DATA_NAME = null;
	// 평가등록 1, 평가받아옴 2, 평가삭제 3, 추천 4 / 배달음식 다받기 1 찾기 2 평가등록/수정 5 받아옴 6 삭제 7 
	private String identifier = null;



	//delivery
	public SendServer(DeliveryRestaurant del, String url){
		if(del.getCafe().equals("getAllDelivery")){
			this.DATA_NAME = DELIVERY_ALL_REQUEST_NAME;
			this.identifier = "1";
		}
		else {
			this.del = del;
			this.DATA_NAME = DELIVERY_REQUEST_NAME;
			this.identifier = "6";
		}
		this.SERVER_URL = url;
	}

	//snu menu
	public SendServer(String url) {
		this.SERVER_URL = url;
		this.DATA_NAME = TODAYMENU_REQUEST_NAME;
		this.identifier = "1";
	}
	public SendServer(SnuMenu snumenu, String url) {
		this.snumenu = snumenu;
		this.SERVER_URL = url;
		this.DATA_NAME = MENU_SEND_NAME;
		this.identifier = "2";
	}
	//search
	public SendServer(String url, String keyword) {
		this.SERVER_URL = url;
		this.DATA_NAME = SEARCH_SEND_NAME;
		this.identifier = "2";
		this.keyword = new Search(keyword);
	}
	//user
	public SendServer(UserInfo userinfo, String url) {
		this.userinfo = userinfo;
		this.SERVER_URL = url;
		this.DATA_NAME = USER_SEND_NAME;
	}

	public SendServer(UserInfo userinfo, String url, String identifier) {
		this.userinfo = userinfo;
		this.SERVER_URL = url;
		this.DATA_NAME = USER_SEND_NAME;
		this.identifier = identifier;
	}
	//comment
	public SendServer(Comment comment, String url, String identifier) {
		this.SERVER_URL = url;
		this.comment = comment;
		this.identifier = identifier;

		this.DATA_NAME = INSERT_COMMENT_SEND_NAME;
	}

	public SendServer(RecComment reccomment, String url) {
		this.SERVER_URL = url;
		this.reccomment = reccomment;
		this.identifier = "4";

		this.DATA_NAME = RECOM_COMMENT_SEND_NAME;
	}
	
	public SendServer(MenuRecommend menurec, String url) {
		this.SERVER_URL = url;
		this.menurec = menurec;
		this.identifier = "3";
		
		this.DATA_NAME = RECOM_COMMENT_SEND_NAME;
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

		else if (DATA_NAME.equals(TODAYMENU_REQUEST_NAME)) {
			json = "get today menu";
		}
		else if (DATA_NAME.equals(SEARCH_SEND_NAME)) {
			json = new Gson().toJson(keyword);

		}
		
		else if (DATA_NAME.equals(TODAYMENU_RECOMMEND)) {
			json = new Gson().toJson(menurec);

		}
		else if (DATA_NAME.equals(DELIVERY_ALL_REQUEST_NAME)) {
			json = "get delivery all";
		}
		else if (DATA_NAME.equals(DELIVERY_REQUEST_NAME)){
			json = new Gson().toJson(del);
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
