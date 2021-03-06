package com.athapong.arcodeapp;

import java.io.IOException;
import java.util.List;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log; 
import com.athapong.arcodeapp.DataBaseHelper;
import com.athapong.arcodeapp.DataBaseHelper.memberData;

public class MainActivity extends Activity {
	//private SQLiteDatabase db;
	String username,password,userId;
	final DataBaseHelper myDb = new DataBaseHelper(this);
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
        	Log.d("DB","Database Created");
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
				EditText username_box = (EditText) findViewById(R.id.editText1);
				EditText password_box = (EditText) findViewById(R.id.editText2);
				username = username_box.getText().toString();
				password = password_box.getText().toString();
				boolean getStatus = false;
				
				if(username != "" && password !="" && username.length() != 0 && password.length() != 0){
					getStatus = myDb.selectLogIn(username,password);
						if(getStatus == true){
							userId = getId(username);
							startProcess();
						}else{
							Toast myAlert = Toast.makeText (getApplicationContext(), "User Not Found", Toast.LENGTH_LONG );
							myAlert.show();
						}
				}else{
					Toast myAlert = Toast.makeText (getApplicationContext(), "Please Enter Username and Password", Toast.LENGTH_LONG );
					myAlert.show();
				}
			}
		});
		
		Button btnSignUp = (Button) findViewById(R.id.SignUp);
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
	    	 //save Path
    	savedInstanceState.putString("userId", userId);
    }
    
	@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    	//restore userneme
    	userId = savedInstanceState.getString("userId");
    		
    }
	
	public String getId(String username){
		String userId =null;
		List <memberData> getId = myDb.getUserId(username);
		for(memberData mem:getId){
			userId = mem.gId();
		}
		return userId;
	}
	
	
    private void startProcess(){
   	 	Intent myActivity = new Intent (this,WelcomeActivity.class);
   	 	myActivity.putExtra("userId", userId);
       	startActivity(myActivity);
    } 
	
	
}
