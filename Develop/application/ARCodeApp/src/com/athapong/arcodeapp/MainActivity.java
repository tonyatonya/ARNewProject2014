package com.athapong.arcodeapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; 
import com.athapong.arcodeapp.DataBaseHelper;
import com.athapong.arcodeapp.DataBaseHelper.*;

public class MainActivity extends Activity {
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//First Run
		DataBaseHelper myDbHelper = new DataBaseHelper(this);
        myDbHelper = new DataBaseHelper(this);
	
        try {
        	Log.d("DB Create","Database Now Created.");
        	myDbHelper.createDataBase();
 
        } catch (IOException ioe) {
 
        	throw new Error("Unable to create database");
 
        }
 
        try {
 
        	myDbHelper.openDataBase();
        	Log.d("DB","Open Database");
        	
 
        }catch(SQLException sqle){
 
        	throw sqle;
 
        }
		
		// Conect to Database 
        
 		final DataBaseHelper myDb = new DataBaseHelper(this);
 		myDb.getWritableDatabase();
 		if(myDb != null){
 			Log.d("DB Conect","Database Connected.");
 		}
 		
		
		
		Button btnSignin = (Button) findViewById(R.id.SignIn);
		btnSignin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,WelcomeActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnSignUp = (Button) findViewById(R.id.SignUp);
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
				//startActivity(intent);
				
				//test Query
				Log.d("check btn","Clicked");
				
				List<busData> busAllList = myDb.selectAllbus();
				if(busAllList == null){
					Toast.makeText(MainActivity.this,"Not found Data!",Toast.LENGTH_LONG).show();
					Log.e("check Queryed","No Query");
				}else{
					
					//for(busData mem : allLogo){
					Log.e("check Queryed","Start Query");
					for(int i=0;i<=busAllList.size();i++){
						//Toast.makeText(MainActivity.this,"Bus logopath = "+ mem.gLogo() ,Toast.LENGTH_LONG).show();
						Log.e("check Queryed","Get Query");
					}
				}
				//end Test Query
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
