package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MylistActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		
		//Create Button Listener
		View btnGoCapture = findViewById(R.id.Capture);
		btnGoCapture.setOnClickListener((OnClickListener) this);
	}
	public void onClick(View V){
		switch (V.getId()){
		case R.id.Capture:
			Intent a = new Intent(this, CaptureActivity.class);
			startActivity(a);
			break;
		}
	}

}
