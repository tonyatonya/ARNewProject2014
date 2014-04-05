package com.athapong.arcodeapp;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;  
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Toast;

public class CaptureActivity extends Activity implements SurfaceHolder.Callback{
	private static final String TAG = "CaptureActivity";
	private LayoutInflater mInflater = null;
	Camera mCamera;
	byte[] tempdata;
	boolean mPreviewRunning = false;
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView mSurfaceView;
	Button takepicture;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_capture);
		
		mSurfaceView = (SurfaceView) findViewById(R.id.mySurface);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(this);
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
		mInflater = LayoutInflater.from(this);
		View overView = mInflater.inflate(R.layout.cameraoverlay, null);
		this.addContentView(overView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
		takepicture = (Button) findViewById(R.id.button1);
		takepicture.setOnClickListener(new OnClickListener (){
			public void onClick (View v){
				mCamera.takePicture(mShutterCallback,mPictureCallback,mjpeg);
			}
		});
		
	}
	
	ShutterCallback mShutterCallback = new ShutterCallback(){
		@Override
		public void onShutter(){}
	};
	PictureCallback mPictureCallback = new PictureCallback(){
		public void onPictureTaken(byte[] data,Camera c){}
	};
	PictureCallback mjpeg = new PictureCallback(){
		public void onPictureTaken(byte[] data,Camera c){
			if(data !=null){
				tempdata = data;
				done();
			}
		}
	};
	
	void done(){
		Bitmap bm = BitmapFactory.decodeByteArray(tempdata,0, tempdata.length);
		String url=Images.Media.insertImage(getContentResolver(), bm, null, null);
		bm.recycle();
		Bundle bundle = new Bundle();
		if(url !=null){
			bundle.putString("url", url);
			
			Intent mIntent = new Intent();
			mIntent.putExtras(bundle);
			setResult(RESULT_OK,mIntent);
		}else{
			Toast.makeText(this,"Picture can not be saved", Toast.LENGTH_SHORT).show();
		}
		finish();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder,int format,int w, int h){
		Log.e(TAG,"surfaceChanged");
		try{
			if (mPreviewRunning) {
				mCamera.stopPreview();
				mPreviewRunning = false;
			}
			Camera.Parameters p = mCamera.getParameters();
			p.setPreviewSize(w, h);
			mCamera.setParameters(p);
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			mPreviewRunning = true;
		} catch(Exception e){
			Log.d("",e.toString());
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder){
		Log.e(TAG,"surfaceCreated");
		mCamera = Camera.open();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		Log.e(TAG,"surfaceDestroyed");
		mCamera.stopPreview();
		mPreviewRunning = false;
		mCamera.release();
		mCamera = null;
	}
	
}

