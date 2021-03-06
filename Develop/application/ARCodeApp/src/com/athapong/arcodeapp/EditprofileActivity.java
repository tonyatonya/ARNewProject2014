package com.athapong.arcodeapp;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import com.athapong.arcodeapp.DataBaseHelper.memberData;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class EditprofileActivity extends Activity {
	public String userId;
	private SQLiteDatabase db;
	final DataBaseHelper myDb = new DataBaseHelper(this);
	public String username,password,name,email,mtype,rdate;
	public boolean recStatus = false;
	
	
	//public String username,password,name,email,rdate;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editprofile);
		
		//edittext
		final EditText edittext1 = (EditText) findViewById(R.id.Profile_editText1);//username
		final EditText edittext2 = (EditText) findViewById(R.id.Profile_editText2);//password
		final EditText edittext3 = (EditText) findViewById(R.id.Profile_editText3);//name
		final EditText edittext4 = (EditText) findViewById(R.id.Profile_editText4);//email
		
		// get Data from mainActivity
		userId = getIntent().getStringExtra("userId");
		Log.d("editProfile userId","userId = "+userId);
		bindMemberData(edittext1,edittext2,edittext3,edittext4,userId,mtype,rdate);
		
		//button save
		Button btnSave = (Button) findViewById(R.id.savebtn);
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				recStatus = recordmemberData(edittext1,edittext2,edittext3,edittext4,userId,mtype,rdate);//Data recored
				if(recStatus == true){
					Log.d("next Activity","Start next activity");
					startProcess();
				}else{
					Log.d("next Activity","Can not start next activity");
				}
			}
		});
		
		//button cancel
		Button btnCancel = (Button) findViewById(R.id.cancelbtn);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancle();
			}
		});
		
	}
	
	public void bindMemberData(EditText edittext1,EditText edittext2,EditText edittext3,EditText edittext4,String userId,String mtype,String rdate){
		Log.d("bindMemberData","Start Bind");
		List<memberData> getMemberData = myDb.allMemberData(userId);
		for(memberData mem : getMemberData){
			username = mem.gUsername();
			password = mem.gPass();
			name = mem.gName();
			email = mem.gEmail();
			mtype = mem.gMtype();
			rdate = mem.gRdate();
			
			// set Data
			edittext1.setText(username);
			edittext2.setText(password);
			edittext3.setText(name);
			edittext4.setText(email);
		}
		
	}
	
	public boolean recordmemberData(EditText edittext1,EditText edittext2,EditText edittext3,EditText edittext4,String userId,String mtype,String rdate){
		boolean recorded = false;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy"); 
		String getCurdate = currentDate.format(c.getTime());
		
		//String getData[] = null;
		String[] getData = new String[7];
		//case admin
		if(mtype == "0"){
			mtype = "0";
		}else{
			mtype = "1";
		}
		// case non date
		if(rdate == null){
			rdate = getCurdate;
			Log.d("recordmemberData","getDate = " + rdate);
		}
		getData[0] = userId;//user Id
		getData[1] = edittext1.getText().toString(); // username
		getData[2] = edittext2.getText().toString(); // password
		getData[3] = edittext3.getText().toString(); // name
		getData[4] = edittext4.getText().toString(); // email
		getData[5] = mtype;
		getData[6] = rdate;
		recorded = myDb.updateMember(getData);
		return recorded;
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
    	//restore userID
    	//userId = savedInstanceState.getString("userId");
    		
    }
	
	private void startProcess(){
   	 	Intent myActivity = new Intent (this,WelcomeActivity.class);
   	 	myActivity.putExtra("userId", userId);
       	startActivity(myActivity);
    }
	
	private void cancle(){
   	 	Intent myActivity = new Intent (this,WelcomeActivity.class);
   	 	myActivity.putExtra("userId", userId);
       	startActivity(myActivity);
    }

}
