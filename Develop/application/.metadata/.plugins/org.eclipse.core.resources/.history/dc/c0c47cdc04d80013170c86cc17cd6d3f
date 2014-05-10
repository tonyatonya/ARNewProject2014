package com.athapong.arcodeapp;

import java.io.File;
import java.lang.reflect.Array;

import android.app.Activity;  
import android.graphics.Bitmap;
import android.os.Bundle; 
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;

import android.database.sqlite.SQLiteDatabase;


public class MyProcess extends Activity {
		
		private SQLiteDatabase db;
		
		String getPath;
		String TAG = null;
		Mat image,gImg,bImg,tImg,inputMat = null;
		ImageView myIV;
		Bitmap tempImg;
		 private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

		        @Override
		        public void onManagerConnected(int status) {
		            switch (status) {
		                case LoaderCallbackInterface.SUCCESS:
		                {
		                    Log.i(TAG, "OpenCV loaded successfully");
		                    //get Image
		                    getMyMat(getPath);
		                    
		                    //Image Gray Scale
		                    if(image!=null){
		                    	gImg = (Mat) convertGray(image);
		                    	Log.i(TAG, "getMat successfully");
		                    	//Blur Image
		                    	if(gImg != null){
			                    	bImg = (Mat)imgBlur(gImg);
			                    	Log.i(TAG, "Mat is Gray successfully");
			                    	//AdaptiveThreshold -> classify as either black or white
			                    	if(bImg !=null){
			                    		tImg = (Mat)imgThres(bImg);
			                    		Log.i(TAG, "Mat is Threshold successfully");
			                    	}
		                    	}
		                    }
		                    
	                    	
		                } break;
		                default:
		                {
		                    super.onManagerConnected(status);
		                } break;
		            }
		            
		        }
		    };
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_myprocess);

        
        // get Data from CaptureActivity
        getPath = getIntent().getStringExtra("currentPath");
        Toast myToast = Toast.makeText(this,getPath, Toast.LENGTH_LONG);
		myToast.show();
		
	}
	
	@Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }
	
	public Mat getMyMat(String inputPath){
		image = Highgui.imread(inputPath);
		if(image==null){
			Toast myToast = Toast.makeText(this,"image is null", Toast.LENGTH_LONG);
			myToast.show();
		}else{
			Toast myToast = Toast.makeText(this,"image is not null", Toast.LENGTH_LONG);
			myToast.show();
		}
		return image;
		
	}
	
	public Mat convertGray(Mat inputMat){
		Imgproc.cvtColor(inputMat, inputMat, Imgproc.COLOR_RGB2GRAY);
		
		// convert mat 
		tempImg = Bitmap.createBitmap(inputMat.cols(), inputMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(inputMat, tempImg);
		// show image
		myIV = (ImageView) this.findViewById(R.id.myIV);
		myIV.setImageBitmap(tempImg);
		Toast myToast = Toast.makeText(this,"image is grayed", Toast.LENGTH_LONG);
		myToast.show();
		return inputMat;
	}
	
	public Mat imgBlur(Mat inputMat){
		org.opencv.core.Size s = new Size(5,5);
		Imgproc.GaussianBlur(inputMat,inputMat,s,0);
		// convert mat 
		tempImg = Bitmap.createBitmap(inputMat.cols(), inputMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(inputMat, tempImg);
		// show image
		myIV = (ImageView) this.findViewById(R.id.myIV);
		myIV.setImageBitmap(tempImg);
		Toast myToast = Toast.makeText(this,"image is blured", Toast.LENGTH_LONG);
		myToast.show();
		return inputMat;
	}
	
	
	
	public Mat imgThres(Mat inputMat){
		Imgproc.adaptiveThreshold(inputMat, inputMat, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 5, 2);
		// convert mat 
		tempImg = Bitmap.createBitmap(inputMat.cols(), inputMat.rows(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(inputMat, tempImg);
		// show image
		myIV = (ImageView) this.findViewById(R.id.myIV);
		myIV.setImageBitmap(tempImg);
		Toast myToast = Toast.makeText(this,"image is Thresholded", Toast.LENGTH_LONG);
		myToast.show();
		return inputMat;
	}
	
}
