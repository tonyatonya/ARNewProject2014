package com.athapong.arcodeapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.athapong.arcodeapp.DataBaseHelper;
import com.athapong.arcodeapp.DataBaseHelper.memberData;

public class WelcomeActivity extends Activity {
	String getUser,getId;
	public String userId;
	//private SQLiteDatabase db;
	final DataBaseHelper myDb = new DataBaseHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		//get data from editProfile
		getId = getIntent().getStringExtra("userId");
		final String userId= getId;
		Log.d("userId",getId);
		
		//Button EditProfile
		Button btnEditprofile = (Button) findViewById(R.id.editprofile);
		btnEditprofile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//Intent intent = new Intent(WelcomeActivity.this,EditprofileActivity.class);
				//startActivity(intent);
				
				Intent myActivity = new Intent (WelcomeActivity.this,EditprofileActivity.class);
		   	 	myActivity.putExtra("userId", userId);
		       	startActivity(myActivity);
				
				
			}
		});
		
		//Button goCapture
		Button btnGoCapture = (Button) findViewById(R.id.capture);
		btnGoCapture.setOnClickListener(new View.OnClickListener() {
					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,CaptureActivity.class);
				startActivity(intent);
			}
		});
		//Button MyList
		Button btnMyList = (Button) findViewById(R.id.mylist);
		btnMyList.setOnClickListener(new View.OnClickListener() {
							
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,MylistActivity.class);
				startActivity(intent);
			}
		});
		
		//Button logout
		Button btnLogout = (Button) findViewById(R.id.logout);
		btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
				startActivity(intent);
				
			}
		});	
	}
	
	
	@Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
		//save userId
    	savedInstanceState.putString("userId", userId);
    }
	@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    	//restore userId
    	userId = savedInstanceState.getString("userId");
    		
    }
	
//	public String getId(String username){
//		String userId =null;
//		List <memberData> getId = myDb.getUserId(username);
//		for(memberData mem:getId){
//			userId = mem.gId();
//		}
//		return userId;
//	}

}