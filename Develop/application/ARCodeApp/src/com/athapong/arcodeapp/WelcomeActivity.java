package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;

public class WelcomeActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		//Button EditProfile
		Button btnEditprofile = (Button) findViewById(R.id.editprofile);
		btnEditprofile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(WelcomeActivity.this,EditprofileActivity.class);
				startActivity(intent);
				
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
}