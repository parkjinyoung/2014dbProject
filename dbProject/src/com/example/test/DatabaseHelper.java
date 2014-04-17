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
	private static final String TODAY_MENU = "todaymenu";
	private static final String TODAY_CAFE = "todaycafe";
	private static final String TODAY_EVAL = "todayeval";
	
	private static final String USER_NAME = "user_name";
	private static final String TABLE_TODO = "todos";
	private static final String TABLE_SENDTODO = "sendtodos";
	private static final String KEY_DATE = "date";
	private static final String PKY = "pky";
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_CONTENT = "content";
	private static final String KEY_TODO_ID = "todo_id";
	private static final String GROUP_NAME = "group_name";
	private static final String GROUP_ID_LIST = "group_id_list";
	private static final String GROUP_NAME_LIST = "group_name_list";
	private static final String SYNC_LOG = "sync_log";
	private static final String IS_SHARED = "is_shared";
	private static final String USER_ID = "user_id";

	// Table Create Statements
	// Todo table create statement
	private static final String CREATE_TABLE_TODO = "CREATE TABLE "
			+ TABLE_TODO + "(" + KEY_DATE + " TEXT," + PKY
			+ " INTEGER PRIMARY KEY," + KEY_ID + " INTEGER," + KEY_TITLE
			+ " TEXT," + KEY_CONTENT + " TEXT," + GROUP_NAME + " TEXT,"
			+ GROUP_NAME_LIST + " TEXT," + GROUP_ID_LIST + " TEXT" + ")";
	private static final String CREATE_TABLE_SENDTODO = "CREATE TABLE "
			+ TABLE_SENDTODO + "(" + USER_ID + " TEXT," + KEY_DATE + " TEXT,"
			+ PKY + " INTEGER PRIMARY KEY," + KEY_ID + " INTEGER," + KEY_TITLE
			+ " TEXT," + KEY_CONTENT + " TEXT," + GROUP_ID_LIST + " TEXT,"
			+ IS_SHARED + " INTEGER," + SYNC_LOG + " INTEGER" + ")";
	private static final String CREATE_TABLE_USERINFO = "CREATE TABLE "
			+ TABLE_USER_INFO + "(" + USER_NAME + " TEXT," + USER_ID + " TEXT,"
			+ PKY + " INTEGER PRIMARY KEY" + ")";

	private static final String CREATE_TABLE_TODAYMENU = "CREATE TABLE "
			+ TABLE_TODAY_MENU + "(" + TODAY_MENU + " TEXT," + TODAY_CAFE + " TEXT" + ")";
	
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_TODO);
		db.execSQL(CREATE_TABLE_SENDTODO);
		db.execSQL(CREATE_TABLE_USERINFO);
		
		db.execSQL(CREATE_TABLE_TODAYMENU);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
		db.execSQL("DROP_TABLE IF EXISTS " + TABLE_SENDTODO);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
		
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODAY_MENU);
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

	// public long getLastpky(){
	// SQLiteDatabase db = this.getReadableDatabase();
	// String selectQuery = "SELECT last_insert_rowid() FROM " + TABLE_TODO;
	//
	// Cursor c = db.rawQuery(selectQuery, null);
	//
	// if (c != null)
	// c.moveToFirst();
	//
	// // int result = c.get
	//
	// c.close();
	// }
	//
	
	public void createTodayMenu(SnuMenu snumenu){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(TODAY_CAFE, snumenu.getCafe());
		values.put(TODAY_MENU, snumenu.getMenu());
		values.put(TODAY_EVAL, snumenu.getEval());
		db.insert(TABLE_TODAY_MENU, null, values);
	}
	
	public SnuMenu getSnuMenu(String cafe, String menu){
		SQLiteDatabase db = this.getReadableDatabase();
		
		String selectQuery = "SELECT * FROM " + TABLE_TODAY_MENU + " WHERE " 
				+ TODAY_CAFE + " = " + cafe + " AND " + TODAY_MENU + " = " + menu;
		
		Cursor c = db.rawQuery(selectQuery, null);
		
		if(c != null)
			c.moveToFirst();
		
		SnuMenu sm = new SnuMenu();
		sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
		sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
		sm.setEval(c.getString(c.getColumnIndex(TODAY_EVAL)));
		
		c.close();
		return sm;
	}
	
	public ArrayList<SnuMenu> getAllSnuMenus(){
		ArrayList<SnuMenu> snumenus = new ArrayList<SnuMenu>();
		String selectQuery = "SELECT * FROM " + TABLE_TODAY_MENU;
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		if(c.moveToFirst()){
			do{
				SnuMenu sm = new SnuMenu();
				sm.setCafe(c.getString(c.getColumnIndex(TODAY_CAFE)));
				sm.setMenu(c.getString(c.getColumnIndex(TODAY_MENU)));
				sm.setEval(c.getString(c.getColumnIndex(TODAY_EVAL)));
				
				snumenus.add(sm);
			}
			while(c.moveToNext());
		}
		c.close();
		return snumenus;
	}
	
	public int getToDoCount() {
		String countQuery = "SELECT  * FROM " + TABLE_TODO;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}
/*
	public int updateSnuMenu(SnuMenu snumenu, String cafe, String menu){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		
		return ;
	}
	*/
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

	/**
	 * get datetime
	 * */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}