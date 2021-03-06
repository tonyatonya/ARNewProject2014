package com.athapong.arcodeapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import android.app.Activity;  
import android.content.Context;
import android.content.Intent;  
import android.database.Cursor;
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;  
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;  
import android.widget.Button;  
import android.widget.ImageView; 
import android.widget.TextView;
import android.widget.Toast;

  
public class CaptureActivity extends Activity {  
     private static final int CAMERA_REQUEST = 1888;  
     ImageView imageView;
     String currentPath;
     Bitmap photo,imgResult;
     
     public void onCreate(Bundle savedInstanceState) {  
  
         super.onCreate(savedInstanceState);  
         setContentView(R.layout.activity_capture);  
         imageView = (ImageView) this.findViewById(R.id.imageView1);  
         Button photoButton = (Button) this.findViewById(R.id.button1);
         Button sendButton = (Button) this.findViewById(R.id.button2);
         
         photoButton.setOnClickListener(new View.OnClickListener() {  
  
         @Override  
         public void onClick(View v) {  
              Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);              
              startActivityForResult(cameraIntent, CAMERA_REQUEST);  
         }  
        });
         
         sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startProcess();
			}
		}); 
       }  
  
     @Override
     protected void onSaveInstanceState(Bundle savedInstanceState) {
     super.onSaveInstanceState(savedInstanceState);
	    	 //save Path
	         String pathResult =  currentPath;
	         savedInstanceState.putString("resultPath", pathResult);
	         
	         //save ImageView
	         //Bitmap imgResult = imageView.getDrawingCache() ;
	         imgResult = BitmapFactory.decodeFile(currentPath);
	         savedInstanceState.putParcelable("imgResult", imgResult);
	         
	         imageView = (ImageView) this.findViewById(R.id.imageView1); 
	         imageView.setImageBitmap(imgResult);
     }
     
     @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
     super.onRestoreInstanceState(savedInstanceState);
     		//restore path
     		currentPath = savedInstanceState.getString("resultPath");
     		TextView myPath = (TextView) this.findViewById(R.id.targeturi);
     		myPath.setText(currentPath);
	
	        //restoreimg
     		imgResult = (Bitmap) savedInstanceState.getParcelable("imgResult");
	        imageView = (ImageView) this.findViewById(R.id.imageView1); 
	        imageView.setImageBitmap(imgResult);
     	
     }
    
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
      if (requestCode == CAMERA_REQUEST) {  
    	  Bitmap photo = (Bitmap) data.getExtras().get("data");  
    	  
    	  imageView.setImageBitmap(photo);
    	  
    	  // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
          Uri tempUri = getImageUri(getApplicationContext(), photo);

          // CALL THIS METHOD TO GET THE ACTUAL PATH
          File finalFile = new File(getRealPathFromURI(tempUri));
          currentPath = finalFile.toString();
    	  TextView myPath = (TextView) this.findViewById(R.id.targeturi);
          myPath.setText(finalFile.toString());
      }
      super.onActivityResult(requestCode, resultCode, data);
      
   }
     public Uri getImageUri(Context inContext, Bitmap inImage) {
    	    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    	    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    	    String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
    	    return Uri.parse(path);
    	}
     public String getRealPathFromURI(Uri uri) {
    	    Cursor cursor = getContentResolver().query(uri, null, null, null, null); 
    	    cursor.moveToFirst(); 
    	    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
    	    return cursor.getString(idx); 
    	}
     private void startProcess(){
    	 Intent myProcess = new Intent (this,MyProcess.class);
    	 if(currentPath == null){
    		//alert
    		 Toast toast = Toast.makeText ( this, "File Not Found Please Take a Photo", Toast.LENGTH_LONG );
    		 toast.show();
    	 }else{
    		//passing information
        	 myProcess.putExtra("currentPath", currentPath); 
        	 startActivity(myProcess);
    	 }
     } 
}  
