package com.athapong.arcodeapp;

import android.app.Activity;  
import android.content.Intent;  
import android.graphics.Bitmap; 
import android.graphics.BitmapFactory;
import android.os.Bundle;  
import android.view.Menu;  
import android.view.View;  
import android.widget.Button;  
import android.widget.ImageView; 
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
  
public class CaptureActivity extends Activity {  
    private static final int CAMERA_REQUEST = 1888;  
    ImageView imageView; 
    TextView textTargetUri;
    
    public void onCreate(Bundle savedInstanceState) {  
 
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_capture);  
        
        imageView = (ImageView) this.findViewById(R.id.imageView1);  
        Button photoButton = (Button) this.findViewById(R.id.button1);  
        // createTextView
        textTargetUri = (TextView)findViewById(R.id.targeturi);
 
        photoButton.setOnClickListener(new View.OnClickListener() {  
 
        @Override  
        public void onClick(View v) {  
             Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);  
             startActivityForResult(cameraIntent, CAMERA_REQUEST);  
        }  
       });  
      }  
 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
     if (requestCode == CAMERA_REQUEST) {  
      Bitmap photo = (Bitmap) data.getExtras().get("data");  
      imageView.setImageBitmap(photo);  
     }
     super.onActivityResult(requestCode, resultCode, data);
  }  
    
}  
