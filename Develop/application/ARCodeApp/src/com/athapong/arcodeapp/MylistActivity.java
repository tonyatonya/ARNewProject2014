package com.athapong.arcodeapp;

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.app.ListActivity;
import com.athapong.arcodeapp.DataBaseHelper;
import com.athapong.arcodeapp.DataBaseHelper.busData;
import com.athapong.arcodeapp.MyAdapter;


public class MylistActivity extends Activity {
	
	private ArrayList<String> resultMatchId=new ArrayList<String>();
	private SQLiteDatabase db;
	final DataBaseHelper myDb = new DataBaseHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mylist);
		
		// Connect to Database 
  		myDb.getWritableDatabase();
      	if(myDb != null){
      		Log.d("DB Conect","Database Connected.");
      	}
      	
      //get Data from MyProcess
      	resultMatchId = getIntent().getStringArrayListExtra("resultMatchId");
		
		// 1. pass context and data to the custom adapter
        MyAdapter adapter = new MyAdapter(this, generateData(resultMatchId));
 
        // 2. Get ListView from activity
        ListView listView = (ListView) findViewById(R.id.list);
 
        // 3. setListAdapter
        listView.setAdapter(adapter);
		
      	
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

	private ArrayList<Item> generateData(ArrayList <String> resultMatchId){
		ArrayList<Item> items = new  ArrayList<Item>();
		//Log.d("resultMatchId.size()", "resultMatchId.size() = " + resultMatchId.size() );
			for(int i = 0;i<resultMatchId.size();i++){
				String getIndex = resultMatchId.get(i);
				List<busData> selectBusData = myDb.selectBusById(getIndex);
				for(busData mem :selectBusData){
					Log.d("get Data", "mem.gName() = " + mem.gName() + " mem.gUrl() = " + mem.gUrl() + " mem.gLogo() = " + mem.gLogo());
					String gName = mem.gName();
					String gUrl = mem.gUrl();
					String gLogo = mem.gLogo();
					String gDes = mem.gDes();
					items.add(new Item(gName,gUrl,gLogo));
				}
			}
			return items;
      	}
	
}
