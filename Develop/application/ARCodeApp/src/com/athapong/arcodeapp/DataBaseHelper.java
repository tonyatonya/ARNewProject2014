package com.athapong.arcodeapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.athapong.arcodeapp/databases/";
 
    private static String DB_NAME = "ardb.sqlite";
 
    private SQLiteDatabase myDB; 
 
    private final Context myContext;
    
    // Table Name
    	private static final String TABLE_ANDROID ="android_metadata";
 		private static final String TABLE_MEMBER = "member";
 		private static final String TABLE_BUS = "businesslist";
 		
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDB != null)
    		    myDB.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDB.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	
	//memberTable
	public class memberData{
		String _id,name,email,mtype,rdate,username,pass;
		//set value
		public void set_id (String set_id){
			this._id = set_id;
		}
		public void set_name (String set_name){
			this.name = set_name;
		}
		public void set_email (String set_email){
			this.email = set_email;
		}
		public void set_mtype (String set_mtype){
			this.mtype = set_mtype;
		}
		public void set_rdate (String set_rdate){
			this.rdate = set_rdate;
		}
		public void set_username (String set_username){
			this.username = set_username;
		}
		public void set_pass (String set_pass){
			this.pass = set_pass;
		}
		
		// get value
		public String gId(){
			return _id;
		}
		public String gName(){
			return name;
		}
		public String gEmail(){
			return email;
		}
		public String gMtype(){
			return mtype;
		}
		public String gRdate(){
			return rdate;
		}
		public String gUsername(){
			return username;
		}
		public String gPass(){
			return pass;
		}
	}
	
	//select All bus data
		public class busData {
			String _id,bus_name,bus_url,bus_logo,bus_des,bus_member;
			//set value
			public void allBusID (String setBusID){
				this._id = setBusID;
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
			public void allBusDes (String setBusDes){
				this.bus_des = setBusDes;
			}
			public void allBusMem (String setBusMem){
				this.bus_member = setBusMem;
			}
			
			// get Value
			public String gId(){
				return _id;
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
			public String gDes(){
				return bus_des;
			}
			public String gMem(){
				return bus_member;
			}
		}
		
		//Query All Bus Data
		public List<busData> selectAllbus(){
			try{
				List<busData> busAllList = new ArrayList<busData>(); 
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT * FROM " + TABLE_BUS;
				Cursor cursor = myDB.rawQuery(strSQL, null);
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							busData allBusData = new busData();
							allBusData.allBusID(cursor.getString(0));
							allBusData.allBusName(cursor.getString(1));
							allBusData.allBusUrl(cursor.getString(2));
							allBusData.allBusLogo(cursor.getString(3));
							allBusData.allBusDes(cursor.getString(4));
							allBusData.allBusMem(cursor.getString(5));
							busAllList.add(allBusData);
						}while(cursor.moveToNext());
					}
				}
				cursor.close();
				myDB.close();
				return busAllList;
			}catch(Exception e){
				return null;
			}
		}
		

		// Query All bus Logo
		public List<busData> selectAllLogo(){
			try{
				List<busData> allLogo = new ArrayList<busData>();
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT bus_logo , _id FROM " + TABLE_BUS;
				Cursor cursor = myDB.rawQuery(strSQL, null);
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							busData logoData = new busData();
							logoData.allBusLogo(cursor.getString(0));
							logoData.allBusID(cursor.getString(1));
							allLogo.add(logoData);
						}while(cursor.moveToNext());
					}
				}
				cursor.close();
				myDB.close();
				return allLogo;
				}catch(Exception e){
					return null;
			}
			
		}
		
		
		//Query All Bus select by id
		public List<busData> selectBusById(String inputId){
			try{
				List<busData> busAllList = new ArrayList<busData>();
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT * FROM " + TABLE_BUS + " WHERE _id =" + inputId;
				Log.d("selectBusById","strSQL = " + strSQL);
				Cursor cursor = myDB.rawQuery(strSQL, null);
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							busData allBusData = new busData();
							allBusData.allBusID(cursor.getString(0));
							allBusData.allBusName(cursor.getString(1));
							allBusData.allBusUrl(cursor.getString(2));
							allBusData.allBusLogo(cursor.getString(3));
							allBusData.allBusDes(cursor.getString(4));
							//allBusData.allBusMem(cursor.getString(5));
							busAllList.add(allBusData);
						}while(cursor.moveToNext());
					}
				}
				cursor.close();
				myDB.close();
				return busAllList;
				}catch(Exception e){
					return null;
			}
			
		}
		
		//Query All Bus select by id
		public List<busData> selectBusByMemberId(String inputId){
			try{
				List<busData> busAllList = new ArrayList<busData>();
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT * FROM " + TABLE_BUS + " WHERE _idmember =" + inputId;
				Log.d("selectBusById","strSQL = " + strSQL);
				Cursor cursor = myDB.rawQuery(strSQL, null);
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							busData allBusData = new busData();
							allBusData.allBusID(cursor.getString(0));
							allBusData.allBusName(cursor.getString(1));
							allBusData.allBusUrl(cursor.getString(2));
							allBusData.allBusLogo(cursor.getString(3));
							allBusData.allBusDes(cursor.getString(4));
							busAllList.add(allBusData);
						}while(cursor.moveToNext());
					}
				}
				cursor.close();
				myDB.close();
				return busAllList;
				}catch(Exception e){
					return null;
			}
			
		}

		// get user id
		public List<memberData> getUserId(String username){
			if(username != ""){
				try{					
					List<memberData> member = new ArrayList<memberData>();
					SQLiteDatabase myDB;
					myDB = this.getReadableDatabase();
					String strSQL = "SELECT * FROM " + TABLE_MEMBER + " WHERE username = '" + username + "'"; 
					Log.d("getUserId","strSQL = "+ strSQL);
					Cursor cursor = myDB.rawQuery(strSQL, null);
					if(cursor != null){
						if(cursor.moveToFirst()){
							do{
								memberData getId = new memberData();
								getId.set_id(cursor.getString(0));
								member.add(getId);
							}while(cursor.moveToNext());
						}
					}
					cursor.close();
					myDB.close();
					return member;
				}catch(Exception e){
					return null;
				}
			}else{
				return null;
			}
		}
		
		//Bind member Data from id
		public List<memberData> allMemberData(String id){
			if(id != ""){
				try{
					List<memberData> member = new ArrayList<memberData>();
					SQLiteDatabase myDB;
					myDB = this.getReadableDatabase();
					String strSQL = "SELECT * FROM " + TABLE_MEMBER + " WHERE _id = '" + id + "'";
					Cursor cursor = myDB.rawQuery(strSQL,null);
					if(cursor != null){
						if(cursor.moveToFirst()){
							do{
								memberData getAll = new memberData();
								getAll.set_id(cursor.getString(0));
								getAll.set_name(cursor.getString(1));
								getAll.set_email(cursor.getString(2));
								getAll.set_mtype(cursor.getString(3));
								getAll.set_rdate(cursor.getString(4));
								getAll.set_username(cursor.getString(5));
								getAll.set_pass(cursor.getString(6));
								member.add(getAll);
							}while(cursor.moveToNext());
						}
					}
					cursor.close();
					myDB.close();
					return member;
				}catch(Exception e){
					return null;
				}
			}
			return null;
		}
		//Query Log in
		public boolean selectLogIn(String username,String password){
			boolean status;
			if(username != "" && password != "" ){
			try{
				List<memberData> member = new ArrayList<memberData>();
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT _id,pass FROM " + TABLE_MEMBER + " WHERE username = '" + username +"' and pass = '" + password + "'";
				Log.d("selectLogIn","strSQL = " + strSQL);
				Cursor cursor = myDB.rawQuery(strSQL, null);
				
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							memberData getUser = new memberData();
							getUser.set_id(cursor.getString(0));
							getUser.set_pass(cursor.getString(1));
							member.add(getUser);
						}while(cursor.moveToNext());
						status = true;
					}else{
						status = false;
					}
					
				}else{
					status = false;
				}
				cursor.close();
				myDB.close();
				return status;
			}catch(Exception e){
				return (Boolean) null;
			}
			}else{
				return status = false; 
			}
		}
		
		//update Member Data 
		public boolean updateMember(String arg[]){
			Log.d("updateMember","updateMember Start");
				try{
					Log.d("updateMember","Start Query");
					SQLiteDatabase myDB;
					myDB = this.getWritableDatabase();
					ContentValues Val = new ContentValues();
					// set Value
					Val.put("name", arg[3]);
					Val.put("email", arg[4]);
					Val.put("mtype", arg[5]);
					Val.put("rdate", arg[6]);
					Val.put("username", arg[1]);
					Val.put("pass", arg[2]);
					String userId = arg[0];
					myDB.update(TABLE_MEMBER, Val, " _id = ?",new String[] { String.valueOf(userId)});//select row
					Log.d("updateMember","complete Query");
					myDB.close();
					
				}catch(Exception e){
					return false;
				}
				return true;
		}
		
		
		 // insertMember
		public boolean insertMember(String arg[]){
			Log.d("insertMember","insertMember Start");
			//check user
			try{
				List<memberData> member = new ArrayList<memberData>();
				SQLiteDatabase myDB;
				myDB = this.getReadableDatabase(); // Read Data
				String strSQL = "SELECT username FROM " + TABLE_MEMBER + " WHERE username = '" +arg[0]+"'";
				Log.d("selectLogIn","strSQL = " + strSQL);
				myDB.rawQuery(strSQL, null);
				Cursor cursor = myDB.rawQuery(strSQL, null);
				if(cursor != null){
					if(cursor.moveToFirst()){
						do{
							memberData getUser = new memberData();
							//getAll.set_id(cursor.getString(0));
							//getAll.set_name(cursor.getString(1));
							//getAll.set_email(cursor.getString(2));
							//getAll.set_mtype(cursor.getString(3));
							//getAll.set_rdate(cursor.getString(4));
							getUser.set_username(cursor.getString(5));
							//getAll.set_pass(cursor.getString(6));
							member.add(getUser);
						}while(cursor.moveToNext());
					}
				}
			}catch(Exception e){
				return false;
			}
			
			
			
//				try{
//					Log.d("insertMember","Start Query");
//					SQLiteDatabase myDB;
//					myDB = this.getWritableDatabase();
//					ContentValues Val = new ContentValues();
//					// set Value
//					Val.put("name", arg[3]);
//					Val.put("email", arg[4]);
//					Val.put("mtype", arg[5]);
//					Val.put("rdate", arg[6]);
//					Val.put("username", arg[1]);
//					Val.put("pass", arg[2]);
//					String userId = arg[0];
//					//myDB.insert(TABLE_MEMBER," _id = ?",new String[] { String.valueOf(userId)});//select row
//					Log.d("updateMember","complete Query");
//					myDB.close();
//					
//				}catch(Exception e){
//					return false;
//				}
				return true;
		}
	
	
 
}
