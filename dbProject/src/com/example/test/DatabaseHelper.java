package com.example.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import object.DeliveryRestaurant;
import object.Delivery_group;
import object.SnuMenu;
import object.SnuRestaurant;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat tag
	private static final String LOG = DatabaseHelper.class.getName();

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "todayMenu";

	// Table Names
	private static final String TABLE_USER_INFO = "userinfos";
	private static final String TABLE_TODAY_MENU = "todaymenutable";
	private static final String TABLE_SEARCH_MENU = "searchmenutable";
	private static final String TABLE_VISIBLE_RES = "visibleres";
	private static final String TABLE_DELIVERY = "delivery";
	private static final String TABLE_DELIVERY_GROUP = "delivery_group";

	// user
	private static final String USER_NAME = "user_name";
	private static final String USER_ID = "user_id";

	// menu
	private static final String TODAY_MENU = "todaymenu";
	private static final String TODAY_CAFE = "todaycafe";
	private static final String TODAY_EVAL = "todayeval";
	private static final String TODAY_PRICE = "todayprice";
	private static final String TODAY_TIME = "todaytime";
	private static final String TODAY_MNO = "todaymno";

	private static final String MENU = "menu";
	private static final String CAFE = "cafe";
	private static final String EVAL = "eval";
	private static final String PRICE = "price";
	private static final String TIME = "time";
	private static final String MNO = "mno";
	// delivery
	private static final String DEL_NO = "dno";
	private static final String DEL_RES = "cafe";
	private static final String DEL_GROUP = "grouping";
	private static final String DEL_TIME = "hours";
	private static final String DEL_MENU = "menu_url";
	private static final String DEL_RATING = "rating";

	private static final String DEL_GROUP_NAME = "del_group_name";
	// res
	private static final String RES_NAME = "resname";

	// Table Create Statements

	
	private static final String CREATE_TABLE_DELIVERY_GROUP = "CREATE TABLE "
			+ TABLE_DELIVERY_GROUP + "(" + DEL_GROUP_NAME + " TEXT" + ")";
	private static final String CREATE_TABLE_DELIVERY = "CREATE TABLE "
			+ TABLE_DELIVERY + "(" + DEL_NO + " TEXT," + DEL_RES + " TEXT,"
			+ DEL_GROUP + " TEXT," + DEL_TIME + " TEXT," + DEL_MENU + " TEXT,"
			+ DEL_RATING + " TEXT" + ")";
	private static final String CREATE_TABLE_TODAYMENU = "CREATE TABLE "
			+ TABLE_TODAY_MENU + "(" + TODAY_CAFE + " TEXT," + TODAY_MENU
			+ " TEXT," + TODAY_EVAL + " TEXT," + TODAY_PRICE + " INTEGER,"
			+ TODAY_TIME + " TEXT," + TODAY_MNO + " TEXT" + ")";
	private static final String CREATE_TABLE_SEARCHMENU = "CREATE TABLE "
			+ TABLE_SEARCH_MENU + "(" + CAFE + " TEXT," + MENU + " TEXT,"
			+ EVAL + " TEXT," + PRICE + " INTEGER," + TIME + " TEXT," + MNO
			+ " TEXT" + ")";
	private static final String CREATE_TABLE_USERINFO = "CREATE TABLE "
			+ TABLE_USER_INFO + "(" + USER_NAME + " TEXT," + USER_ID + " TEXT"
			+ ")";

	private static final String CREATE_TABLE_VISIBLERES = "CREATE TABLE "
			+ TABLE_VISIBLE_RES + "(" + RES_NAME + " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		// 여러개의 table을 생성
		// 오늘의 메뉴 / 검색한 메뉴 / 유저정보 / 즐겨찾기한 식당
		// 배달음식점 / 배달음식점그룹 ( 피자, 치킨, ...)
		db.execSQL(CREATE_TABLE_TODAYMENU);
		db.execSQL(CREATE_TABLE_SEARCHMENU);
		db.execSQL(CREATE_TABLE_USERINFO);
		db.execSQL(CREATE_TABLE_VISIBLERES);
		db.execSQL(CREATE_TABLE_DELIVERY);
		db.execSQL(CREATE_TABLE_DELIVERY_GROUP);

		initable_delGroup(db);
		initable_VisibleRes(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODAY_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISIBLE_RES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY_GROUP);
		// create new tables
		onCreate(db);
	}

	public String listtoString(ArrayList<String> arr) {
		String ret = "";
		if (arr != null && arr.size() > 0) {
			ret = arr.get(0);
			for (int i = 1; i < arr.size(); i++) {
				ret += ", " + arr.get(i);
			}
		}
		return ret;
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

	// /////////////////////////////////////////////////////////////////////////
	private void initable_VisibleRes(SQLiteDatabase db) {
		// 식당을 초기화 ( 모든 식당을 다 즐겨찾기로 선택 )
		ContentValues values = new ContentValues();
		values.put(RES_NAME, "220동");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "301동");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "302동");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "감골식당");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "공깡");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "기숙사(901동)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "기숙사(919동)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "동원관");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "상아회관");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "서당골(사범대)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "자하연");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "전망대(농대)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "학생회관");
		db.insert(TABLE_VISIBLE_RES, null, values);
	}

	public long createVisibleRes(String res_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(RES_NAME, res_name);
		long result = db.insert(TABLE_VISIBLE_RES, null, values);
		return result;
	}

	public boolean isVisibleRes(String res_name) {
		SQLiteDatabase db = this.getReadableDatabase();

		// 직접비교하지않고 preparedstatement를 이용하여 처리 (내부의 모든 db에서 이를 이용)
		// 즐겨찾기된 식당만 보이게 함
		Cursor c = db.query(TABLE_VISIBLE_RES, null, RES_NAME + "=?",
				new String[] { res_name }, null, null, null, null);
		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_VISIBLE_RES + " WHERE "
		 * + RES_NAME + " = '" + res_name + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */
		if (c != null) {
			c.close();
			return true;
		} else
			return false;
	}

	public ArrayList<String> getVisibleResAll() {

		ArrayList<String> ret = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_VISIBLE_RES, null, null, null, null, null,
				null, null);

		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_VISIBLE_RES;
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */

		if (c != null) {
			if (c.moveToFirst()) {
				do {
					ret.add(c.getString(c.getColumnIndex(RES_NAME)));
				} while (c.moveToNext());
			}
		}
		c.close();
		return ret;
	}

	public void deleteVisibleResAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_VISIBLE_RES, "", null);
		System.out.println("delete all");
	}

	public void deleteVisibleRes(String res_name) {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params = new String[] { res_name };
		db.delete(TABLE_VISIBLE_RES, RES_NAME + " = ?", params);
	}

	// /////////////////////////////////////////////////////////////////////////
	// search
	public long createSearchMenu(SnuMenu snumenu) {
		// 검색한 Menu를 table에 저장
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CAFE, snumenu.getCafe());
		values.put(MENU, snumenu.getMenu());
		values.put(EVAL, snumenu.getRating());
		values.put(PRICE, snumenu.getPrice());
		values.put(TIME, snumenu.getTime());
		values.put(MNO, snumenu.getMno());
		long result = db.insert(TABLE_SEARCH_MENU, null, values);
		// Log.d("SNUMENU createTM", "cafe : " + snumenu.getCafe() + " menu : "
		// + snumenu.getMenu() + " eval : " + snumenu.getEval());
		return result;
	}

	public SnuMenu getSearchSnuMenu(String Mno) {
		// Mno (메뉴의 고유값)으로 메뉴를 검색함
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_SEARCH_MENU, null, MNO + "= ?",
				new String[] { Mno }, null, null, null, null);
		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_SEARCH_MENU + " WHERE "
		 * + CAFE + " = '" + cafe + "' AND " + MENU + " = '" + menu + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */

		if (c != null)
			c.moveToFirst();

		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(MENU)));
		sm.setRating(c.getString(c.getColumnIndex(EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
		sm.setTime(c.getString(c.getColumnIndex(TIME)));
		sm.setMno(c.getString(c.getColumnIndex(MNO)));
		c.close();
		return sm;
	}
	
	public SnuMenu getSearchSnuMenu(String cafe, String menu) {
		// 고유값을 지정하기 전 primary key로 사용하였던 (menu,cafe) pair로 검색하는 것
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_SEARCH_MENU, null, CAFE + "= ? AND " + MENU
				+ "= ?", new String[] { cafe, menu }, null, null, null, null);
		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_SEARCH_MENU + " WHERE "
		 * + CAFE + " = '" + cafe + "' AND " + MENU + " = '" + menu + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */

		if (c != null)
			c.moveToFirst();

		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(MENU)));
		sm.setRating(c.getString(c.getColumnIndex(EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
		sm.setTime(c.getString(c.getColumnIndex(TIME)));
		sm.setMno(c.getString(c.getColumnIndex(MNO)));
		c.close();
		return sm;
	}

	public int getSearchSnuMenuCount() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_SEARCH_MENU, null, null, null, null, null,
				null, null);
		/*
		 * String countQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU;
		 * 
		 * Cursor cursor = db.rawQuery(countQuery, null);
		 */

		int count = c.getCount();
		c.close();

		// return count
		return count;
	}

	public ArrayList<SnuMenu> getSearchCafeMenus(String cafe) {
		// 하나의 음식점이 가지고 있는 모든 메뉴를 arraylist로 돌려줌
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		/*
		 * String selectQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU +
		 * " WHERE " + CAFE + " = '" + cafe + "'";
		 */
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_SEARCH_MENU, null, CAFE + "= ?",
				new String[] { cafe }, null, null, null, null);

		/* Cursor c = db.rawQuery(selectQuery, null); */

		if (c.moveToFirst()) {
			do {
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(MENU)));
				sm.setRating(c.getString(c.getColumnIndex(EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TIME)));
				sm.setMno(c.getString(c.getColumnIndex(MNO)));
				snumenus.add(sm);
				// Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() +
				// " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			} while (c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public ArrayList<SnuMenu> getAllSearchSnuMenus() {
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		/*
		 * String selectQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU;
		 */
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.query(TABLE_SEARCH_MENU, null, null, null, null, null,
				null, null);
		/* Cursor c = db.rawQuery(selectQuery, null); */

		if (c.moveToFirst()) {
			do {
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(MENU)));
				sm.setRating(c.getString(c.getColumnIndex(EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TIME)));
				sm.setMno(c.getString(c.getColumnIndex(MNO)));
				snumenus.add(sm);
				// Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() +
				// " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			} while (c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public int updateSearchSnuMenu(String cafe, String menu, String eval) {
		// 메뉴의 평점이 바뀌었을때 ( 평가정보가 바뀌었을때) update
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] params = new String[] { cafe, menu };
		values.put(EVAL, eval);
		return db.update(TABLE_SEARCH_MENU, values, CAFE + " = ? AND " + MENU
				+ " = ?", params);
	}

	public void deleteSearchSnuMenuAll() {
		// 모든 메뉴 삭제
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SEARCH_MENU, "", null);
	}

	public void deleteSearchSnuMenu(String cafe, String menu) {
		// 해당 메뉴 삭제 ( 내부에서 사용되고 있지는 않음 )
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params = new String[] { cafe, menu };
		db.delete(TABLE_SEARCH_MENU, CAFE + " = ? AND " + MENU + " = ?", params);
	}

	// /////////////////////////////////////////////////////////////////////////
	// 오늘의 메뉴 Table / 모든 기능이 위의 SearchTable과 동일
	public long createTodayMenu(SnuMenu snumenu) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TODAY_CAFE, snumenu.getCafe());
		values.put(TODAY_MENU, snumenu.getMenu());
		values.put(TODAY_EVAL, snumenu.getRating());
		values.put(TODAY_PRICE, snumenu.getPrice());
		values.put(TODAY_TIME, snumenu.getTime());
		values.put(TODAY_MNO, snumenu.getMno());
		long result = db.insert(TABLE_TODAY_MENU, null, values);
		// Log.d("SNUMENU createTM", "cafe : " + snumenu.getCafe() + " menu : "
		// + snumenu.getMenu() + " eval : " + snumenu.getEval());
		return result;
	}

	public SnuMenu getSnuMenu(String Mno) {
		SQLiteDatabase db = this.getReadableDatabase();

		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_TODAY_MENU + " WHERE "
		 * + TODAY_CAFE + " = '" + cafe + "' AND " + TODAY_MENU + " = '" + menu
		 * + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */
		Cursor c = db.query(TABLE_TODAY_MENU, null, TODAY_MNO + "= ?",
				new String[] { Mno }, null, null, null, null);

		if (c != null)
			c.moveToFirst();

		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
		sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
		sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
		sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
		// Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " +
		// sm.getMenu() + " eval : " + sm.getEval());
		c.close();
		return sm;
	}
	
	public SnuMenu getSnuMenu(String cafe, String menu) {
		SQLiteDatabase db = this.getReadableDatabase();

		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_TODAY_MENU + " WHERE "
		 * + TODAY_CAFE + " = '" + cafe + "' AND " + TODAY_MENU + " = '" + menu
		 * + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */
		Cursor c = db.query(TABLE_TODAY_MENU, null, TODAY_CAFE + "= ? AND "
				+ TODAY_MENU + "= ?", new String[] { cafe, menu }, null, null,
				null, null);

		if (c != null)
			c.moveToFirst();

		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
		sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
		sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
		sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
		// Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " +
		// sm.getMenu() + " eval : " + sm.getEval());
		c.close();
		return sm;
	}

	public int getSnuMenuCount() {
		/* String countQuery = "SELECT  * FROM " + TABLE_TODAY_MENU; */
		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor cursor = db.rawQuery(countQuery, null); */

		Cursor c = db.query(TABLE_TODAY_MENU, null, null, null, null, null,
				null, null);

		int count = c.getCount();
		c.close();

		// return count
		return count;
	}

	public ArrayList<SnuMenu> getCafeMenus(String cafe) {
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		/*
		 * String selectQuery = "SELECT  * FROM " + TABLE_TODAY_MENU + " WHERE "
		 * + TODAY_CAFE + " = '" + cafe + "'";
		 */
		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor c = db.rawQuery(selectQuery, null); */
		Cursor c = db.query(TABLE_TODAY_MENU, null, TODAY_CAFE + "= ?",
				new String[] { cafe }, null, null, null, null);

		if (c.moveToFirst()) {
			do {
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
				sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
				sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
				snumenus.add(sm);
				// Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() +
				// " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			} while (c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public ArrayList<SnuMenu> getAllSnuMenus() {
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		/*
		 * String selectQuery = "SELECT  * FROM " + TABLE_TODAY_MENU;
		 */
		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor c = db.rawQuery(selectQuery, null); */
		Cursor c = db.query(TABLE_TODAY_MENU, null, null, null, null, null,
				null, null);

		if (c.moveToFirst()) {
			do {
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
				sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
				sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
				snumenus.add(sm);
				// Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() +
				// " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			} while (c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public int updateSnuMenu(String cafe, String menu, String eval) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] params = new String[] { cafe, menu };
		values.put(TODAY_EVAL, eval);
		return db.update(TABLE_TODAY_MENU, values, TODAY_CAFE + " = ? AND "
				+ TODAY_MENU + " = ?", params);
	}

	public void deleteSnuMenuAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODAY_MENU, "", null);
	}

	public void deleteSnuMenu(String cafe, String menu) {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params = new String[] { cafe, menu };
		db.delete(TABLE_TODAY_MENU, TODAY_CAFE + " = ? AND " + TODAY_MENU
				+ " = ?", params);
	}

	// //////////////////////////////////////////////////
	// delivery
	// 배달음식점 그룹
	private void initable_delGroup(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(DEL_GROUP_NAME, "피자");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "치킨");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "한식·분식");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "중식");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "돈까스·일식");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "냉면");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
		values.put(DEL_GROUP_NAME, "도시락");
		db.insert(TABLE_DELIVERY_GROUP, null, values);
	}

	public ArrayList<String> getAllDeliveryGroupName() {
		ArrayList<String> delarr = new ArrayList<String>();
		/* String selectQuery = "SELECT  * FROM " + TABLE_DELIVERY_GROUP; */

		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor c = db.rawQuery(selectQuery, null); */
		Cursor c = db.query(TABLE_DELIVERY_GROUP, null, null, null, null, null,
				null, null);

		if (c.moveToFirst()) {
			do {
				Delivery_group delg = new Delivery_group();
				delg.setGroup_name(c.getString(c.getColumnIndex(DEL_GROUP_NAME)));
				delarr.add(delg.getGroup_name());
			} while (c.moveToNext());
		}
		c.close();
		return delarr;
	}

	public long createDelivery(DeliveryRestaurant del) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DEL_NO, del.getDno());
		values.put(DEL_GROUP, del.getGrouping());
		values.put(DEL_MENU, del.getMenu_url());
		values.put(DEL_RATING, del.getRating());
		values.put(DEL_RES, del.getCafe());
		values.put(DEL_TIME, del.getHours());
		long result = db.insert(TABLE_DELIVERY, null, values);
		return result;
	}
	public String getDeliveryRes(String dno) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_DELIVERY, null, DEL_NO + "=?",
				new String[] { dno }, null, null, null, null);

		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_DELIVERY + " WHERE " +
		 * DEL_RES + " = '" + resname + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */

		if (c != null)
			c.moveToFirst();

		DeliveryRestaurant del = new DeliveryRestaurant();
		del.setCafe(c.getString(c.getColumnIndex(DEL_RES)));
		del.setDno(c.getString(c.getColumnIndex(DEL_NO)));
		del.setGrouping(c.getString(c.getColumnIndex(DEL_GROUP)));
		del.setMenu_url(c.getString(c.getColumnIndex(DEL_MENU)));
		del.setRating(c.getString(c.getColumnIndex(DEL_RATING)));
		del.setHours(c.getString(c.getColumnIndex(DEL_TIME)));

		// Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " +
		// sm.getMenu() + " eval : " + sm.getEval());
		c.close();
		return del.getCafe();
	}
	public DeliveryRestaurant getDelivery(String resname) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor c = db.query(TABLE_DELIVERY, null, DEL_RES + "=?",
				new String[] { resname }, null, null, null, null);

		/*
		 * String selectQuery = "SELECT * FROM " + TABLE_DELIVERY + " WHERE " +
		 * DEL_RES + " = '" + resname + "'";
		 * 
		 * Cursor c = db.rawQuery(selectQuery, null);
		 */

		if (c != null)
			c.moveToFirst();

		DeliveryRestaurant del = new DeliveryRestaurant();
		del.setCafe(c.getString(c.getColumnIndex(DEL_RES)));
		del.setDno(c.getString(c.getColumnIndex(DEL_NO)));
		del.setGrouping(c.getString(c.getColumnIndex(DEL_GROUP)));
		del.setMenu_url(c.getString(c.getColumnIndex(DEL_MENU)));
		del.setRating(c.getString(c.getColumnIndex(DEL_RATING)));
		del.setHours(c.getString(c.getColumnIndex(DEL_TIME)));

		// Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " +
		// sm.getMenu() + " eval : " + sm.getEval());
		c.close();
		return del;
	}

	public int getDeliveryCount() {
		/* String countQuery = "SELECT  * FROM " + TABLE_DELIVERY; */
		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor cursor = db.rawQuery(countQuery, null); */
		Cursor c = db.query(TABLE_DELIVERY, null, null, null, null, null, null,
				null);

		int count = c.getCount();
		c.close();
		db.close();
		// return count
		return count;
	}

	// 배달 음식점 각각의 정보를 받아옴
	public ArrayList<DeliveryRestaurant> getAllDelivery() {
		ArrayList<DeliveryRestaurant> delarr = new ArrayList<DeliveryRestaurant>();
		/* String selectQuery = "SELECT  * FROM " + TABLE_DELIVERY; */

		SQLiteDatabase db = this.getReadableDatabase();
		/* Cursor c = db.rawQuery(selectQuery, null); */
		Cursor c = db.query(TABLE_DELIVERY, null, null, null, null, null, null,
				null);

		if (c.moveToFirst()) {
			do {
				DeliveryRestaurant del = new DeliveryRestaurant();
				del.setDno(c.getString(c.getColumnIndex(DEL_NO)));
				del.setCafe(c.getString(c.getColumnIndex(DEL_RES)));
				del.setGrouping(c.getString(c.getColumnIndex(DEL_GROUP)));
				del.setMenu_url(c.getString(c.getColumnIndex(DEL_MENU)));
				del.setRating(c.getString(c.getColumnIndex(DEL_RATING)));
				del.setHours(c.getString(c.getColumnIndex(DEL_TIME)));
				delarr.add(del);
				// Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() +
				// " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			} while (c.moveToNext());
		}
		c.close();
		db.close();
		return delarr;
	}

	public int updateDeliveryEval(String resname, String eval) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] params = new String[] { resname };
		values.put(DEL_RATING, eval);
		return db.update(TABLE_DELIVERY, values, DEL_RES + " = ? ", params);
	}

	public void deleteDeliveryAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_DELIVERY, "", null);
	}

	public void deleteDelivery(String resname) {
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params = new String[] { resname };
		db.delete(TABLE_DELIVERY, DEL_RES + " = ? ", params);
	}

	// //////////////////////////////////////////
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}