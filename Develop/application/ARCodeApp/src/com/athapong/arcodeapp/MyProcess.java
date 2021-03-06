package com.athapong.arcodeapp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;  
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

import org.opencv.core.Point;
import org.opencv.core.Core.MinMaxLocResult;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import com.athapong.arcodeapp.DataBaseHelper;
import com.athapong.arcodeapp.DataBaseHelper.busData;


public class MyProcess extends Activity {
		
		private SQLiteDatabase db;
		final DataBaseHelper myDb = new DataBaseHelper(this);
		final String TAG ="Image Processing";
		String getPath,logoId;
		//String TAG = null;
		Mat firstImage,SecondImage,image,gImg,bImg,tImg,matchResult,inputMat = null;
		ImageView myIV;
		Bitmap tempImg;
		
		ArrayList<String> resultMatchId=new ArrayList<String>();
		 private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

		        @Override
		        public void onManagerConnected(int status) {
		            switch (status) {
		                case LoaderCallbackInterface.SUCCESS:
		                {
		                    Log.i(TAG, "OpenCV loaded successfully");
		                  //get Image 1
		                   
		                    firstImage = startProcess(getPath);
		              	
                    		// Start Query Image For Compare
		                    List<busData> allLogoList = myDb.selectAllLogo();
		        			if(firstImage != null && allLogoList != null){
		        				Log.e("check Queryed","Start Query");
		        				for(busData mem : allLogoList){
		        						//query image list
		        						Log.e("check Queryed","Get Query");
		        						SecondImage = startProcess(mem.gLogo());
		        						

		        						
		        						//start Template Match
		        						logoId = matchId(mem.gId(),SecondImage,firstImage,Imgproc.TM_CCOEFF);
		        						
		        							   resultMatchId.add(logoId); //  Add Logo id to Array
		        							   Log.e("check resultMatchId",resultMatchId.toString());
		        					}
		        				startList();
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
        myIV = (ImageView) this.findViewById(R.id.myIV);
        
        // Conect to Database 
      		myDb.getWritableDatabase();
	      	if(myDb != null){
	      		Log.d("DB Conect","Database Connected.");
	      	}
	     // get Data from CaptureActivity
	        getPath = getIntent().getStringExtra("currentPath");
	        
			
	}
	
	 @Override
     protected void onSaveInstanceState(Bundle savedInstanceState) {
     super.onSaveInstanceState(savedInstanceState);
	    	 //get Value of Activity
     		savedInstanceState.putStringArrayList("BizId", resultMatchId);
	       
     }
	 
	 @Override
     protected void onRestoreInstanceState(Bundle savedInstanceState) {
     super.onRestoreInstanceState(savedInstanceState);
     		//restore Value
     		resultMatchId = savedInstanceState.getStringArrayList("BizId");
     }
	
	 private void startList(){

		 Intent MylistActivity = new Intent(this,MylistActivity.class);
    	 if(resultMatchId == null){
    		//alert
    		 Toast toast = Toast.makeText ( this, "File Not Found Please Take a Photo", Toast.LENGTH_LONG );
    		 toast.show();
    	 }else{
    		//passing information
    		 MylistActivity.putExtra("resultMatchId", resultMatchId); 
        	 startActivity(MylistActivity);
    	 }
     } 
	 
	
	@Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_8, this, mLoaderCallback);
    }
	
	// Image Process 
	public Mat startProcess(String inputPath){
		//load image;
		image = Highgui.imread(inputPath);
		
		//convert to Gray
		Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
		
		//Add Blur
		org.opencv.core.Size s = new Size(5,5);
		Imgproc.GaussianBlur(image,image,s,0);
		
		//AdaptiveThreshold -> classify as either black or white
		Imgproc.adaptiveThreshold(image, image, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 25, 2);
		

		
		
		Log.i(TAG, "Complete Image Process");
		return image;
	}
	
	public Mat resizeMat(Mat inputImage){
		//Resize image
		Size sz = new Size(200,200);
		Imgproc.resize( inputImage, inputImage, sz );
		return inputImage;
	}

	
	public String matchId(String logoId,Mat masterImage,Mat templImage,int matchMethod){
		String getLogoId = null;
			getLogoId = logoId;
		//Create the result matrix
		int result_cols = masterImage.cols() - templImage.cols();
		int result_rows = masterImage.rows() - templImage.rows();
		Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
		
		// Do the Matching and Normalize
		Imgproc.matchTemplate(masterImage, templImage, result,matchMethod);
	    Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
	  
	    //Localizing the best match with minMaxLoc
	    MinMaxLocResult mmr = Core.minMaxLoc(result);
	    Point matchLoc;
      
      if (matchMethod == Imgproc.TM_SQDIFF || matchMethod == Imgproc.TM_SQDIFF_NORMED){
          matchLoc = mmr.minLoc;
          Log.d("matchLoc = mmr.minLoc;",matchLoc.toString());
      } else {
          matchLoc = mmr.maxLoc;
          Log.d("matchLoc = mmr.maxLoc;",matchLoc.toString());
      }
    	
      Log.d("mmr","id = " +logoId+ "mmr.minLoc" +(mmr.minLoc).toString() +"mmr.maxLoc = "+(mmr.maxLoc).toString());
      Log.d("Result","Result = " + result.toString());
      
      
		return getLogoId;
	}
	
}
