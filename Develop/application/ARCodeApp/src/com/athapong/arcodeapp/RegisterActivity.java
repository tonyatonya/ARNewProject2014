package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	public boolean regStatus = false;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		//edittext
		final EditText edittext1 = (EditText) findViewById(R.id.Profile_editText1);//username
		final EditText edittext2 = (EditText) findViewById(R.id.Profile_editText2);//password
		final EditText edittext3 = (EditText) findViewById(R.id.Profile_editText3);//name
		final EditText edittext4 = (EditText) findViewById(R.id.Profile_editText4);//email
		
		Button btnCancel = (Button) findViewById(R.id.cancelbtn);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
		
		Button btnReg = (Button) findViewById(R.id.regbtn);
		btnReg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				regStatus = register(edittext1,edittext2,edittext3,edittext4);
				if(regStatus ==true){
					Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
					startActivity(intent);
				}else{
					Toast myAlert = Toast.makeText (getApplicationContext(), "Can not Register", Toast.LENGTH_LONG );
					myAlert.show();
				}
			}
		});
	}
	
	public boolean register(EditText edittext1,EditText edittext2,EditText edittext3,EditText edittext4){
		boolean status = false;
		String[] arg = new String[3];
		arg[0]=edittext1.getText().toString();
		arg[1]=edittext2.getText().toString();
		arg[2]=edittext3.getText().toString();
		arg[3]=edittext4.getText().toString();
		
		return status;
	}
}
