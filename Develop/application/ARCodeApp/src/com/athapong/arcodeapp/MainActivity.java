package com.athapong.arcodeapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.text.Editable;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//SignIn Button
		View btnSignin = findViewById(R.id.SignIn);
		btnSignin.setOnClickListener((OnClickListener) this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void onClick(View V){
		switch (V.getId()){
		case R.id.SignIn:
			checkMember();
			Intent a = new Intent(this, WelcomeActivity.class);
			startActivity(a);
			break;
		case R.id.SignUp:
			Intent b = new Intent(this, RegisterActivity.class);
			startActivity(b);
			break;
		}
	}
	public void checkMember(){
		Editable textUser = (Editable) findViewById(R.id.editText1);
		Editable textPasssword = (Editable) findViewById(R.id.editText2);
		Editable myUser = ((EditText) textUser).getText();
		Editable myPassword = ((EditText) textPasssword).getText();
	}
}
