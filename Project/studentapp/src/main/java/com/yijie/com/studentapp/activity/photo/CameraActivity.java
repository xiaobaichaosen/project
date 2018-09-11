package com.yijie.com.studentapp.activity.photo;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.yijie.com.studentapp.R;
import com.yijie.com.studentapp.utils.DisplayUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CameraActivity extends Activity implements View.OnTouchListener,OnClickListener {

	public static final String CAMERA_PATH_VALUE1 = "PHOTO_PATH";
	public static final String CAMERA_PATH_VALUE2 = "PATH";
	public static final String CAMERA_TYPE = "CAMERA_TYPE";
	public static final String CAMERA_RETURN_PATH = "return_path";
	public static String stringExtra;
	private int PHOTO_SIZE_W = 2000;
	private int PHOTO_SIZE_H = 2000;
	public static final int CAMERA_TYPE_1 = 1;
	public static final int CAMERA_TYPE_2 = 2;
	private final int PROCESS = 1;
	private CameraPreview preview;
	private Camera camera;
	private Context mContext;
	private View focusIndex;
	private ImageView flashBtn;
	private int mCurrentCameraId = 1; // 1是前置 0是后置
	private SurfaceView mSurfaceView;
	private CameraGrid mCameraGrid;

	private int type = 1;	//引用的矩形框
	private TextView tv_name;
	private TextView tv_address;
	private TextView tv_time;
	private TextView tv_date;
	private SharedPreferences sp;
	private String nameString;
    private String mPath;


    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;

		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//拍照过程屏幕一直处于高亮
		setContentView(R.layout.camera_home);
		initView();
		InitData();
		type = getIntent().getIntExtra(CAMERA_TYPE, CAMERA_TYPE_2);
		addressString = getIntent().getStringExtra("adress");
		tv_address.setText(addressString);


	}

	private void initView() {
		focusIndex = (View) findViewById(R.id.focus_index);
		flashBtn = (ImageView) findViewById(R.id.flash_view);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_address = (TextView) findViewById(R.id.tv_adress);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_date = (TextView) findViewById(R.id.tv_date);

		mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		mCameraGrid = (CameraGrid) findViewById(R.id.camera_grid);
	}


	private void InitData() {
		preview = new CameraPreview(this, mSurfaceView);
		preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		((RelativeLayout) findViewById(R.id.layout)).addView(preview);
		preview.setKeepScreenOn(true);
		mSurfaceView.setOnTouchListener(this);
		mCameraGrid.setType(type);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置时间格式
		time = df.format(new Date());
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy.MM.dd");//设置日期格式
		date = df1.format(new Date());
		tv_address.setText(addressString);
		tv_time.setText(time);
		tv_date.setText(date);
		sp = getSharedPreferences("dengInfo",Context.MODE_PRIVATE);
		nameString = sp.getString("name", "");
		tv_name.setText(nameString);
	}




	private Handler handler = new Handler();

	private void takePhoto() {
		try {

			camera.takePicture(shutterCallback, rawCallback, jpegCallback);

		} catch (Throwable t) {
			t.printStackTrace();
			Toast.makeText(getApplication(), "拍照失败，请重试！", Toast.LENGTH_LONG)
					.show();
			try {
				camera.startPreview();
			} catch (Throwable e) {

			}
		}
	}



	@Override
	protected void onResume() {
		super.onResume();
		int numCams = Camera.getNumberOfCameras();
		if (numCams > 0) {
			try {
				mCurrentCameraId = 1;
				camera = Camera.open(mCurrentCameraId);
				camera.startPreview();
				preview.setCamera(camera);
				preview.reAutoFocus();
			} catch (RuntimeException ex) {
				//华为手机进入相机后显示未发现相机
//				Toast.makeText(mContext, "未发现相机", Toast.LENGTH_LONG).show();
			}
		}

	}



	@Override
	protected void onPause() {
		if (camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.release();
			camera = null;
			preview.setNull();
		}
		super.onPause();

	}


	private void resetCam() {
		camera.startPreview();
		preview.setCamera(camera);
	}


	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
		}
	};


	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
		}
	};


	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {

			new SaveImageTask(data).execute();
			resetCam();
		}
	};


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
				preview.pointFocus(event);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
				focusIndex.getLayoutParams());
		layout.setMargins((int) event.getX() - 60, (int) event.getY() - 60, 0,0);

		focusIndex.setLayoutParams(layout);
		focusIndex.setVisibility(View.VISIBLE);

		ScaleAnimation sa = new ScaleAnimation(3f, 1f, 3f, 1f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
				ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
		sa.setDuration(800);
		focusIndex.startAnimation(sa);
		handler.postAtTime(new Runnable() {
			@Override
			public void run() {
				focusIndex.setVisibility(View.INVISIBLE);
			}
		}, 800);
		return false;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.camera_back:
				setResult(0);
				finish();
				break;

			case R.id.camera_flip_view:
				switchCamera();
				break;

			case R.id.flash_view:
				turnLight(camera);
				break;

			case R.id.action_button:
				takePhoto();
				break;


		}
	}

	private static String getCameraPath() {
		Calendar calendar = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append("IMG");
		sb.append(calendar.get(Calendar.YEAR));
		int month = calendar.get(Calendar.MONTH) + 1; // 0~11
		sb.append(month < 10 ? "0" + month : month);
		int day = calendar.get(Calendar.DATE);
		sb.append(day < 10 ? "0" + day : day);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		sb.append(hour < 10 ? "0" + hour : hour);
		int minute = calendar.get(Calendar.MINUTE);
		sb.append(minute < 10 ? "0" + minute : minute);
		int second = calendar.get(Calendar.SECOND);
		sb.append(second < 10 ? "0" + second : second);
		if (!new File(sb.toString() + ".jpg").exists()) {
			return sb.toString() + ".jpg";
		}

		StringBuilder tmpSb = new StringBuilder(sb);
		int indexStart = sb.length();
		for (int i = 1; i < Integer.MAX_VALUE; i++) {
			tmpSb.append('(');
			tmpSb.append(i);
			tmpSb.append(')');
			tmpSb.append(".jpg");
			if (!new File(tmpSb.toString()).exists()) {
				break;
			}

			tmpSb.delete(indexStart, tmpSb.length());
		}

		return tmpSb.toString();
	}



	//处理拍摄的照片
	private class SaveImageTask extends AsyncTask<Void, Void, String> {
		private byte[] data;

		SaveImageTask(byte[] data) {
			this.data = data;
		}

		@Override
		protected String doInBackground(Void... params) {
			// Write to SD Card
			String path = "";
			try {

				showProgressDialog("处理中");
				path = saveToSDCard(data);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
			}
			return path;
		}


		@Override
		protected void onPostExecute(String path) {
			super.onPostExecute(path);

			if (!TextUtils.isEmpty(path)) {

				Log.d("DemoLog", "path=" + path);
                mPath=path;
				dismissProgressDialog();
				Intent intent = new Intent();

				intent.putExtra("name", nameString);
				intent.putExtra("time", time);
				intent.putExtra("date", date);
				intent.putExtra("address", addressString);
				intent.setClass(CameraActivity.this, PhotoProcessActivity.class);
				intent.putExtra(CAMERA_PATH_VALUE1, path);
				startActivityForResult(intent, PROCESS);
			} else {
				Toast.makeText(getApplication(), "拍照失败，请稍后重试！",
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private AlertDialog mAlertDialog;
	private String addressString;
	private String date;
	private String time;


	private void dismissProgressDialog() {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mAlertDialog != null && mAlertDialog.isShowing()
						&& !CameraActivity.this.isFinishing()) {
					mAlertDialog.dismiss();
					mAlertDialog = null;
				}
			}
		});
	}

	private void showProgressDialog(final String msg) {
		this.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				if (mAlertDialog == null) {
					mAlertDialog = new GenericProgressDialog(
							CameraActivity.this,R.style.mystyle2);
				}
				mAlertDialog.setMessage(msg);
				((GenericProgressDialog) mAlertDialog)
						.setProgressVisiable(true);
				mAlertDialog.setCancelable(false);
				mAlertDialog.setOnCancelListener(null);
				mAlertDialog.show();
				mAlertDialog.setCanceledOnTouchOutside(false);
			}
		});
	}


	/**
	 * 将拍下来的照片存放在SD卡中
	 */
	public String saveToSDCard(byte[] data) throws IOException {
		Bitmap croppedImage;
		// 获得图片大小
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);
		// PHOTO_SIZE = options.outHeight > options.outWidth ? options.outWidth
		// : options.outHeight;
		PHOTO_SIZE_W = options.outWidth;
		PHOTO_SIZE_H = options.outHeight;
		options.inJustDecodeBounds = false;
		Rect r = new Rect(0, 0, PHOTO_SIZE_W, PHOTO_SIZE_H);
		try {
			croppedImage = decodeRegionCrop(data, r);
//			Bitmap	croppedImage2=drawTextToLeftTop(mContext, croppedImage1, "13:15","2017.07.08 星期一")	;
////			Bitmap croppedImage3=drawTextToLeftTop(mContext, croppedImage2, "2017.07.08 星期一", 18, Color.parseColor("#ffffff"), 18, 60)	;
//			croppedImage=drawTextToRightBottom(mContext, croppedImage2, "于冬", 18, Color.parseColor("#ff00ff"), 18, 18)	;

			//TODO
		} catch (Exception e) {
			return null;
		}
		String imagePath = "";
		try {
			imagePath = saveToFile(croppedImage);
		} catch (Exception e) {

		}
		croppedImage.recycle();
		return imagePath;
	}

	/**
	 * 给图片添加文字到左上角
	 *
	 * @param context
	 * @param bitmap
	 * @param text
	 * @return
	 */
	public static Bitmap drawTextToLeftTop(Context context, Bitmap bitmap, String text1,String text2
	) {
//        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setColor(color);
//        paint.setTextSize(DisplayUtil.dip2px(context, size));
//        Rect bounds = new Rect();
//        paint.getTextBounds(text, 0, text.length(), bounds);
//
//        return drawTextToBitmap(size,context, bitmap, text, paint, bounds,
//        		DisplayUtil.dip2px(context, paddingLeft),
//        		DisplayUtil.dip2px(context, paddingTop) + bounds.height());

		Bitmap.Config bitmapConfig = bitmap.getConfig();
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		Canvas canvas = new Canvas(bitmap);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Paint paint= new Paint();
		paint.setColor(Color.parseColor("#ffffff"));
		paint.setTextSize(DisplayUtil.dip2px(context, 30));
		canvas.drawText(text1, DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 30), paint);
		Paint paint1= new Paint();
		paint1.setColor(Color.parseColor("#ffffff"));
		paint1.setTextSize(DisplayUtil.dip2px(context, 15));
		canvas.drawText(text2, DisplayUtil.dip2px(context, 10), DisplayUtil.dip2px(context, 45), paint1);
		Paint paint2 = new Paint();
		paint2.setColor(Color.parseColor("#33E9E9E9"));
		canvas.drawRect(0, 0, w,DisplayUtil.dip2px(context, 80), paint2);


		return bitmap;
	}


	//图片上绘制文字
//    private static Bitmap drawTextToBitmap(int size,Context context, Bitmap bitmap, String text,
//                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
//        Bitmap.Config bitmapConfig = bitmap.getConfig();
//
//        paint.setDither(true); // 获取跟清晰的图像采样
//        paint.setFilterBitmap(true);// 过滤一些
//        if (bitmapConfig == null) {
//            bitmapConfig = Bitmap.Config.ARGB_8888;
//        }
//        bitmap = bitmap.copy(bitmapConfig, true);
//        Canvas canvas = new Canvas(bitmap);
//        int w = bitmap.getWidth();
//		int h = bitmap.getHeight();
//		int markTextLen = (int)paint.measureText(text);
//        Paint paint2 = new Paint();
//        paint2.setColor(Color.parseColor("#33E9E9E9"));
//        canvas.drawRect(0, 0, w, markTextLen +paddingTop, paint2);
//        canvas.drawText(text, paddingLeft, paddingTop, paint);
//        return bitmap;
//    }
	public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
											   int size, int color, int paddingRight, int paddingBottom) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setTextSize(DisplayUtil.dip2px(context, size));
		Rect bounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), bounds);
		return drawTextToBitmapRight(context, bitmap, text,size, paint, bounds,
				DisplayUtil.dip2px(context, paddingRight),
				DisplayUtil.dip2px(context, paddingBottom) + bounds.height());
	}
	//图片上绘制文字
	private static Bitmap drawTextToBitmapRight(Context context, Bitmap bitmap, String text,  int size,
												Paint paint, Rect bounds, int paddingRight, int paddingBottom) {
		Bitmap.Config bitmapConfig = bitmap.getConfig();

		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		if (bitmapConfig == null) {
			bitmapConfig = Bitmap.Config.ARGB_8888;
		}
		bitmap = bitmap.copy(bitmapConfig, true);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int markTextLen = (int)paint.measureText(text);
		Canvas canvas = new Canvas(bitmap);
		Paint paint2 = new Paint();
		paint2.setColor(Color.parseColor("#33E9E9E9"));
		canvas.drawRect(0, h- markTextLen - size, w, h, paint2);
		canvas.drawText(text,  w - markTextLen - size, h - size, paint);

		return bitmap;
	}
	private Bitmap decodeRegionCrop(byte[] data, Rect rect) {
		InputStream is = null;
		System.gc();
		Bitmap croppedImage = null;
		try {
			is = new ByteArrayInputStream(data);
			BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(is,false);
			try {
				croppedImage = decoder.decodeRegion(rect,
						new BitmapFactory.Options());
			} catch (IllegalArgumentException e) {
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
		Matrix m = new Matrix();
		m.setRotate(90, PHOTO_SIZE_W / 2, PHOTO_SIZE_H / 2);
		if (mCurrentCameraId == 1) {
			m.postScale(1, -1);
		}
		Bitmap rotatedImage = Bitmap.createBitmap(croppedImage, 0, 0,
				PHOTO_SIZE_W, PHOTO_SIZE_H, m, true);
		if (rotatedImage != croppedImage)
			croppedImage.recycle();
		return rotatedImage;
	}



	// 保存图片文件
	public  String saveToFile(Bitmap croppedImage)
			throws FileNotFoundException, IOException {

		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/DCIM/Camera/");
//
//        File externalCacheDir = getExternalCacheDir();
//        File dir2 = new File(externalCacheDir + "/DCIM/Camera/");


        if (!dir.exists()) {
			dir.mkdirs();
		}
		String fileName = getCameraPath();
		File outFile = new File(dir, fileName);
		FileOutputStream outputStream = new FileOutputStream(outFile); // 文件输出流
		croppedImage.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
		outputStream.flush();
		outputStream.close();
		return outFile.getAbsolutePath();
	}


	/**
	 * 闪光灯开关 开->关->自动
	 *
	 * @param mCamera
	 */
	private void turnLight(Camera mCamera) {
		if (mCamera == null || mCamera.getParameters() == null
				|| mCamera.getParameters().getSupportedFlashModes() == null) {
			return;
		}
		Camera.Parameters parameters = mCamera.getParameters();
		String flashMode = mCamera.getParameters().getFlashMode();
		List<String> supportedModes = mCamera.getParameters()
				.getSupportedFlashModes();
		if (Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)
				&& supportedModes.contains(Camera.Parameters.FLASH_MODE_ON)) {// 关闭状态
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			mCamera.setParameters(parameters);
			flashBtn.setImageResource(R.mipmap.camera_flash_on);
		} else if (Camera.Parameters.FLASH_MODE_ON.equals(flashMode)) {// 开启状态
			if (supportedModes.contains(Camera.Parameters.FLASH_MODE_AUTO)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
				flashBtn.setImageResource(R.mipmap.camera_flash_auto);
				mCamera.setParameters(parameters);
			} else if (supportedModes
					.contains(Camera.Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				flashBtn.setImageResource(R.mipmap.camera_flash_off);
				mCamera.setParameters(parameters);
			}
		} else if (Camera.Parameters.FLASH_MODE_AUTO.equals(flashMode)
				&& supportedModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(parameters);
			flashBtn.setImageResource(R.mipmap.camera_flash_off);
		}
	}


	// 切换前后置摄像头
	private void switchCamera() {
		mCurrentCameraId = (mCurrentCameraId + 1) % Camera.getNumberOfCameras();
		if (camera != null) {
			camera.stopPreview();
			preview.setCamera(null);
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
		try {
			camera = Camera.open(mCurrentCameraId);
//			Camera.Parameters parameters=camera.getParameters();
//			// 选择合适的预览尺寸
//			List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
//			if (sizeList.size()>0) {
//				Camera.Size cameraSize=sizeList.get(0);
//				//预览图片大小
//				parameters.setPreviewSize(cameraSize.width, cameraSize.height);
//			}
//			//设置生成的图片大小
//			sizeList = parameters.getSupportedPictureSizes();
//			if (sizeList.size()>0) {
//				Camera.Size cameraSize=sizeList.get(0);
//				for (Camera.Size size : sizeList) {
//					//小于100W像素
//					if (size.width*size.height<100*10000) {
//						cameraSize=size;
//						break;
//					}
//				}
//				parameters.setPictureSize(cameraSize.width, cameraSize.height);
//			}
//			//设置图片格式
//			parameters.setPictureFormat(ImageFormat.JPEG);
//			parameters.setJpegQuality(100);
//			parameters.setJpegThumbnailQuality(100);
//			//自动聚焦模式
//			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			camera.setPreviewDisplay(mSurfaceView.getHolder());
			preview.setCamera(camera);
			camera.startPreview();
		} catch (Exception e) {
			Toast.makeText(mContext, "未发现相机", Toast.LENGTH_LONG).show();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			stringExtra =null;
			setResult(0);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == PROCESS) {
			stringExtra = data.getStringExtra(CAMERA_PATH_VALUE2);
			if (resultCode == RESULT_OK) {
				Intent intent = new Intent();
				if (data != null) {
					intent.putExtra(CAMERA_RETURN_PATH,
							data.getStringExtra(CAMERA_PATH_VALUE2));
				}
				setResult(RESULT_OK, intent);
				finish();
			} else {
				if (data != null) {
					File dir = new File(data.getStringExtra(CAMERA_PATH_VALUE2));
					if (dir != null) {
						dir.delete();
					}
				}
			}
            File dir = new File(mPath);
            if (dir != null) {
                dir.delete();
            }
		}
	}
}
