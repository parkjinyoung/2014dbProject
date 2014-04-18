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
	
	// user
	private static final String USER_NAME = "user_name";
	
	// menu
	private static final String TODAY_MENU = "todaymenu";
	private static final String TODAY_CAFE = "todaycafe";
	private static final String TODAY_EVAL = "todayeval";
	private static final String TODAY_PRICE = "todayprice";
	private static final String TODAY_CLASSIFY = "todayclassify";
	
	private static final String USER_ID = "user_id";

	// Table Create Statements
	// Todo table create statement

	private static final String CREATE_TABLE_TODAYMENU = "CREATE TABLE "
			+ TABLE_TODAY_MENU + "(" + TODAY_CAFE + " TEXT,"
			+ TODAY_MENU + " TEXT,"
			+ TODAY_EVAL + " TEXT,"
			+ TODAY_PRICE + " INTEGER,"
			+ TODAY_CLASSIFY + " TEXT"
			+ ")";
	private static final String CREATE_TABLE_USERINFO = "CREATE TABLE "
			+ TABLE_USER_INFO + "(" + USER_NAME + " TEXT,"
			+ USER_ID + " TEXT" + ")";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// creating required tables
		db.execSQL(CREATE_TABLE_TODAYMENU);
		db.execSQL(CREATE_TABLE_USERINFO);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODAY_MENU);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		// create new tables
		onCreate(db);
	}

	// ///////////////////////////////////////////

	// //////////////////////////////////////////////////

	// ------------------------ "todos" table methods ----------------//

	/**
	 * Creating a todo
	 */
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

	public long createTodayMenu(SnuMenu snumenu){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(TODAY_CAFE, snumenu.getCafe());
		values.put(TODAY_MENU, snumenu.getMenu());
		values.put(TODAY_EVAL, snumenu.getEval());
		values.put(TODAY_PRICE, snumenu.getPrice());
		values.put(TODAY_CLASSIFY, snumenu.getClassify());
		long result = db.insert(TABLE_TODAY_MENU, null, values);
		Log.d("SNUMENU createTM", "cafe : " + snumenu.getCafe() + " menu : " + snumenu.getMenu() + " eval : " + snumenu.getEval());
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
		sm.setEval(c.getString(c.getColumnIndex(TODAY_EVAL)));
		sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
		sm.setClassify(c.getString(c.getColumnIndex(TODAY_CLASSIFY)));
		Log.d("SNUMENU getsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
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
				sm.setEval(c.getString(c.getColumnIndex(TODAY_EVAL)));
				sm.setPrice(c.getInt(c.getColumnIndex(TODAY_PRICE)));
				sm.setClassify(c.getString(c.getColumnIndex(TODAY_CLASSIFY)));
				snumenus.add(sm);
				Log.d("SNUMENU getallsnumenus", "cafe : " + sm.getCafe() + " menu : " + sm.getMenu() + " eval : " + sm.getEval());
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
	
	/*
	public int updateIdTodo(Todo todo, long pky) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_ID, todo.getId());

		System.out.println("updatetodo : " + todo.getPky() + " pky = " + pky
				+ " id = " + todo.getId());

		// updating row
		return db.update(TABLE_TODO, values, PKY + " = ?",
				new String[] { String.valueOf(pky) });
	}

	public int updateToDo(Todo todo, long pky) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		// values.put(KEY_ID, todo.getId());
		values.put(KEY_DATE, todo.getDate());
		values.put(KEY_TITLE, todo.getTitle());
		values.put(KEY_CONTENT, todo.getContent());
		values.put(GROUP_ID_LIST, todo.getGroup_id_list());
		values.put(GROUP_NAME_LIST, todo.getGroup_name_list());
		values.put(GROUP_NAME, todo.getGroup_name());

		// updating row
		return db.update(TABLE_TODO, values, KEY_ID + " = ?",
				new String[] { String.valueOf(pky) });
	}

	public void deleteToDo(long todo_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		System.out.println("todo id : " + todo_id);
		ArrayList<Todo> a = getAllToDos();
		for (int i = 0; i < a.size(); i++) {
			System.out.println("pky : " + a.get(i).getId());
		}
		db.delete(TABLE_TODO, KEY_ID + " = ?",
				new String[] { String.valueOf(todo_id) });
	}

	public void deleteToDoAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODO, "", null);
	}
*/

	// //////////////////////////////////////////
	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

}