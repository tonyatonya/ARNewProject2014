package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		//Create Button Listener
		View btnEditprofile = findViewById(R.id.editprofile);
		btnEditprofile.setOnClickListener((OnClickListener) this);
		
		View btnCapture = findViewById(R.id.capture);
		btnCapture.setOnClickListener((OnClickListener) this);
		
		View btnMylist = findViewById(R.id.mylist);
		btnMylist.setOnClickListener((OnClickListener) this);
		
		View btnLogout = findViewById(R.id.logout);
		btnLogout.setOnClickListener((OnClickListener) this);
	}
	
	public void onClick(View V){
		switch (V.getId()){
		case R.id.editprofile:
			Intent a = new Intent(this, EditprofileActivity.class);
			startActivity(a);
			break;
		case R.id.capture:
			Intent b = new Intent(this, CaptureActivity.class);
			startActivity(b);
			break;
		case R.id.mylist:
			Intent c = new Intent(this, MylistActivity.class);
			startActivity(c);
			break;
		case R.id.logout:
			Intent d = new Intent(this, MainActivity.class);
			startActivity(d);
			break;
		}
	}
	
}