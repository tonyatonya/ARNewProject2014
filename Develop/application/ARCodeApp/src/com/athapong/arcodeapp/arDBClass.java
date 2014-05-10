package com.athapong.arcodeapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class arDBClass extends SQLiteOpenHelper{
		
		
		
		//database version
		private static final int DATABASE_VERSION = 1;
		
		//database name
		private static final String DATABASE_NAME = "ardb";
		
		// Table Name
		private static final String TABLE_MEMBER = "members";
		private static final String TABLE_BUS = "businesslist";
	
		
		public arDBClass(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		/*
		String CREATE_MEMBER_TABLE = "CREATE TABLE member (" + "id INTEGER PRIMARY KEY," + " name TEXT(100)," + " email TEXT(100))";
		String CREATE_BUS_TABLE = "CREATE TABLE businesslist (" + "id INTEGER PRIMARY KEY," + "bus_name CHAR," + "bus_url CHAR," + "bus_logo CHAR )";
		db.execSQL(CREATE_MEMBER_TABLE);
		db.execSQL(CREATE_BUS_TABLE);
		
		Log.d("CREATE TABLE","Create Table Successfully.");
		*/
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/*
		db.execSQL("DROP TABLE IF EXISTS");
		this.onCreate(db);
		*/
	}
	
	//select All bus data
	public class busData {
		String id,bus_name,bus_url,bus_logo;
		//set value
		public void allBusID (String setBusID){
			this.id = setBusID;
		}
		public void allBusName (String setBusName){
			this.bus_name = setBusName;
		}
		public void allBusUrl (String setBusUrl){
			this.bus_url = setBusUrl;
		}
		public void allBusLogo (String setBusLogo){
			this.bus_logo = setBusLogo;
		}
		
		// get Value
		public String gid(){
			return id;
		}
		public String gName(){
			return bus_name;
		}
		public String gUrl(){
			return bus_url;
		}
		public String gLogo(){
			return bus_logo;
		}
	}
	
	//Query All Bus Data
	public List<busData> selectAllbus(){
		try{
			List<busData> busAllList = new ArrayList<busData>(); 
			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data
			String strSQL = "SELECT * FROM " + TABLE_BUS;
			Cursor cursor = db.rawQuery(strSQL, null);
			if(cursor != null){
				if(cursor.moveToFirst()){
					do{
						busData allBusData = new busData();
						allBusData.allBusID(cursor.getString(0));
						allBusData.allBusName(cursor.getString(1));
						allBusData.allBusUrl(cursor.getString(2));
						allBusData.allBusLogo(cursor.getString(3));
						busAllList.add(allBusData);
						
					}while(cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return busAllList;
		}catch(Exception e){
			return null;
		}
	}

	// Query All bus Logo
	public List<busData> selectAllLogo(){
		try{
			List<busData> allLogo = new ArrayList<busData>();
			SQLiteDatabase db;
			db = this.getReadableDatabase(); // Read Data
			String strSQL = "SELECT bus_logo FROM " + TABLE_BUS;
			Cursor cursor = db.rawQuery(strSQL, null);
			if(cursor != null){
				if(cursor.moveToFirst()){
					do{
						busData logoData = new busData();
						logoData.allBusLogo(cursor.getString(3));
						allLogo.add(logoData);
					}while(cursor.moveToNext());
				}
			}
			cursor.close();
			db.close();
			return allLogo;
			}catch(Exception e){
				return null;
		}
		
	}
}
