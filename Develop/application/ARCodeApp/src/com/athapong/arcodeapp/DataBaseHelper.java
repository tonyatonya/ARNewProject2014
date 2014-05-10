package com.athapong.arcodeapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.athapong.arcodeapp.arDBClass.busData;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.athapong.arcodeapp/databases/";
 
    private static String DB_NAME = "ardb";
    
    // Table Name
 	private static final String TABLE_MEMBER = "members";
 	private static final String TABLE_BUS = "businesslist";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
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
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.
	
	
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