package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
//import android.view.View.OnClickListener;
import android.widget.Button;

public class MylistActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		
		//Button goCapture
				Button btnGoCapture = (Button) findViewById(R.id.capture);
				btnGoCapture.setOnClickListener(new View.OnClickListener() {
							
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(MylistActivity.this,CaptureActivity.class);
						startActivity(intent);
					}
				});
		
	}

}