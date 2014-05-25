package com.example.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

	//res
	private static final String RES_NAME = "resname";


	// Table Create Statements
	// Todo table create statement

	private static final String CREATE_TABLE_TODAYMENU = "CREATE TABLE "
			+ TABLE_TODAY_MENU + "(" + TODAY_CAFE + " TEXT,"
			+ TODAY_MENU + " TEXT,"
			+ TODAY_EVAL + " TEXT,"
			+ TODAY_PRICE + " INTEGER,"
			+ TODAY_TIME + " TEXT,"
			+ TODAY_MNO + " TEXT"
			+ ")";
	private static final String CREATE_TABLE_SEARCHMENU = "CREATE TABLE "
			+ TABLE_SEARCH_MENU + "(" + CAFE + " TEXT,"
			+ MENU + " TEXT,"
			+ EVAL + " TEXT,"
			+ PRICE + " INTEGER,"
			+ TIME + " TEXT,"
			+ MNO + " TEXT"
			+ ")";
	private static final String CREATE_TABLE_USERINFO = "CREATE TABLE "
			+ TABLE_USER_INFO + "(" + USER_NAME + " TEXT,"
			+ USER_ID + " TEXT" + ")";

	private static final String CREATE_TABLE_VISIBLERES = "CREATE TABLE "
			+ TABLE_VISIBLE_RES + "(" + RES_NAME + " TEXT" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_TODAYMENU);
		db.execSQL(CREATE_TABLE_SEARCHMENU);
		db.execSQL(CREATE_TABLE_USERINFO);
		db.execSQL(CREATE_TABLE_VISIBLERES);

		initable_VisibleRes(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODAY_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISIBLE_RES);
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
///////////////////////////////////////////////////////////////////////////
	private void initable_VisibleRes(SQLiteDatabase db){
		ContentValues values = new ContentValues();
		values.put(RES_NAME, "220��");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "301��");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "302��");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "����Ĵ�");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "����");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "�����(901��)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "�����(919��)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "������");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "���ȸ��");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "�����(�����)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "���Ͽ�");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "������(���)");
		db.insert(TABLE_VISIBLE_RES, null, values);
		values.put(RES_NAME, "�л�ȸ��");
		db.insert(TABLE_VISIBLE_RES, null, values);
	}
	public long createVisibleRes(String res_name){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(RES_NAME, res_name);
		long result = db.insert(TABLE_VISIBLE_RES, null, values);
		return result;
	}

	public boolean isVisibleRes(String res_name){
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_VISIBLE_RES + " WHERE " 
				+ RES_NAME + " = '" + res_name + "'";

		Cursor c = db.rawQuery(selectQuery, null);

		if(c != null){
			c.close();
			return true;
		}
		else return false;
	}

	public ArrayList<String> getVisibleResAll(){

		ArrayList<String> ret = new ArrayList<String>();
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_VISIBLE_RES;

		Cursor c = db.rawQuery(selectQuery, null);

		if(c != null){
			if(c.moveToFirst()){
				do{
					ret.add(c.getString(c.getColumnIndex(RES_NAME)));
				}
				while(c.moveToNext());
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

	public void deleteVisibleRes(String res_name){
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params=new String[] {res_name};
		db.delete(TABLE_VISIBLE_RES, RES_NAME + " = ?", params);
	}
///////////////////////////////////////////////////////////////////////////	
	//search
	public long createSearchMenu(SnuMenu snumenu){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(CAFE, snumenu.getCafe());
		values.put(MENU, snumenu.getMenu());
		values.put(EVAL, snumenu.getRating());
		values.put(PRICE, snumenu.getPrice());
		values.put(TIME, snumenu.getTime());
		values.put(MNO, snumenu.getMno());
		long result = db.insert(TABLE_SEARCH_MENU, null, values);
//		Log.d("SNUMENU createTM", "cafe : " + snumenu.getCafe() + " menu : " + snumenu.getMenu() + " eval : " + snumenu.getEval());
		return result;
	}

	public SnuMenu getSearchSnuMenu(String cafe, String menu){
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_SEARCH_MENU + " WHERE " 
				+ CAFE + " = '" + cafe + "' AND " + MENU + " = '" + menu + "'";

		Cursor c = db.rawQuery(selectQuery, null);

		if(c != null)
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
		String countQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}
	public ArrayList<SnuMenu> getSearchCafeMenus(String cafe){
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		String selectQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU + " WHERE " 
				+ CAFE + " = '" + cafe + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do{
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(MENU)));
				sm.setRating(c.getString(c.getColumnIndex(EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TIME)));
				sm.setMno(c.getString(c.getColumnIndex(MNO)));
				snumenus.add(sm);
//				Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			}
			while(c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public ArrayList<SnuMenu> getAllSearchSnuMenus(){
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		String selectQuery = "SELECT  * FROM " + TABLE_SEARCH_MENU;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do{
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(MENU)));
				sm.setRating(c.getString(c.getColumnIndex(EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TIME)));
				sm.setMno(c.getString(c.getColumnIndex(MNO)));
				snumenus.add(sm);
//				Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			}
			while(c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public int updateSearchSnuMenu(String cafe, String menu, String eval){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] params=new String[] {cafe, menu};
		values.put(EVAL, eval);
		return db.update(TABLE_SEARCH_MENU, values,
				CAFE + " = ? AND " + MENU + " = ?", params);
	}

	public void deleteSearchSnuMenuAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SEARCH_MENU, "", null);
	}

	public void deleteSearchSnuMenu(String cafe, String menu){
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params=new String[] {cafe, menu};
		db.delete(TABLE_SEARCH_MENU, CAFE + " = ? AND " + MENU + " = ?", params);
	}
///////////////////////////////////////////////////////////////////////////
	public long createTodayMenu(SnuMenu snumenu){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TODAY_CAFE, snumenu.getCafe());
		values.put(TODAY_MENU, snumenu.getMenu());
		values.put(TODAY_EVAL, snumenu.getRating());
		values.put(TODAY_PRICE, snumenu.getPrice());
		values.put(TODAY_TIME, snumenu.getTime());
		values.put(TODAY_MNO, snumenu.getMno());
		long result = db.insert(TABLE_TODAY_MENU, null, values);
//		Log.d("SNUMENU createTM", "cafe : " + snumenu.getCafe() + " menu : " + snumenu.getMenu() + " eval : " + snumenu.getEval());
		return result;
	}

	public SnuMenu getSnuMenu(String cafe, String menu){
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_TODAY_MENU + " WHERE " 
				+ TODAY_CAFE + " = '" + cafe + "' AND " + TODAY_MENU + " = '" + menu + "'";

		Cursor c = db.rawQuery(selectQuery, null);

		if(c != null)
			c.moveToFirst();

		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
		sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
		sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
		sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
//		Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
		c.close();
		return sm;
	}


	public int getSnuMenuCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TODAY_MENU;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}
	public ArrayList<SnuMenu> getCafeMenus(String cafe){
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		String selectQuery = "SELECT  * FROM " + TABLE_TODAY_MENU + " WHERE " 
				+ TODAY_CAFE + " = '" + cafe + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do{
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
				sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
				sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
				snumenus.add(sm);
//				Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			}
			while(c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public ArrayList<SnuMenu> getAllSnuMenus(){
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		String selectQuery = "SELECT  * FROM " + TABLE_TODAY_MENU;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		if(c.moveToFirst()){
			do{
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
				sm.setRating(c.getString(c.getColumnIndex(TODAY_EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
				sm.setTime(c.getString(c.getColumnIndex(TODAY_TIME)));
				sm.setMno(c.getString(c.getColumnIndex(TODAY_MNO)));
				snumenus.add(sm);
//				Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
			}
			while(c.moveToNext());
		}
		c.close();
		return snumenus;
	}

	public int updateSnuMenu(String cafe, String menu, String eval){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		String[] params=new String[] {cafe, menu};
		values.put(TODAY_EVAL, eval);
		return db.update(TABLE_TODAY_MENU, values,
				TODAY_CAFE + " = ? AND " + TODAY_MENU + " = ?", params);
	}

	public void deleteSnuMenuAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODAY_MENU, "", null);
	}

	public void deleteSnuMenu(String cafe, String menu){
		SQLiteDatabase db = this.getWritableDatabase();
		String[] params=new String[] {cafe, menu};
		db.delete(TABLE_TODAY_MENU, TODAY_CAFE + " = ? AND " + TODAY_MENU + " = ?", params);
	}

	

	// //////////////////////////////////////////
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}