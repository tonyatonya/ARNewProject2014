package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class ImageProcess extends Activity {
	String getPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_imageprocess);
		TextView myTV = (TextView)this.findViewById(R.id.imagePath);
		getPath = getIntent().getStringExtra("keyID");
		myTV.setText(getPath);
	}
}
