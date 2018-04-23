package com.yijie.com.studentapp.activity.photo;


import android.app.Activity;


		import java.io.File;
		import java.io.FileNotFoundException;
		import java.io.FileOutputStream;
		import java.io.IOException;
		import java.util.Calendar;


		import android.app.Activity;
		import android.content.Context;
		import android.content.Intent;
		import android.content.SharedPreferences;
		import android.graphics.Bitmap;
		import android.graphics.BitmapFactory;
		import android.graphics.Matrix;
		import android.graphics.Rect;
		import android.media.ExifInterface;
		import android.net.Uri;
		import android.os.Bundle;
		import android.os.Environment;
		import android.view.KeyEvent;
		import android.view.View;
		import android.view.Window;
		import android.view.WindowManager;
		import android.widget.ImageView;
		import android.widget.RelativeLayout;
		import android.widget.TextView;

import com.yijie.com.studentapp.R;


public class PhotoPProcessActivity extends Activity implements View.OnClickListener {

	private ImageView photoImageView;
	private String path = "";
	private TextView actionTextView;
	Bitmap bitmap = null;
	private RelativeLayout rl_layout;
	private TextView tv_name;
	private TextView tv_address;
	private TextView tv_time;
	private TextView tv_date;
	private String lastTime;
	private SharedPreferences sp;
	private String nameString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.pphoto_activity);
		path = getIntent().getStringExtra("filePath");
		lastTime = getIntent().getStringExtra("lastTime");
		initView();
		initData();
	}

	private void initData() {

		try {
//			String nameString = getIntent().getStringExtra("name");
//			String time = getIntent().getStringExtra("time");
//			String date = getIntent().getStringExtra("date");
//			String addressString = getIntent().getStringExtra("address");
			String lastTime = getIntent().getStringExtra("lastTime");
			sp = getSharedPreferences("dengInfo",Context.MODE_PRIVATE);
			nameString = sp.getString("name", "");
			String[] split = lastTime.split(" ");
//			tv_address.setText(addressString);
			tv_time.setText(split[1]);
			tv_date.setText(split[0]);
			tv_name.setText(nameString);
			bitmap = getImage(path);
			//bitmap = loadBitmap(path, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		photoImageView.setImageBitmap(bitmap);
		actionTextView.setOnClickListener(this);
	}

	private void initView() {
		photoImageView = (ImageView) findViewById(R.id.photo_imageview);
		actionTextView = (TextView) findViewById(R.id.photo_process_action);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_address = (TextView) findViewById(R.id.tv_address);
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_date = (TextView) findViewById(R.id.tv_date);
		rl_layout= (RelativeLayout) findViewById(R.id.rl_layout);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 从给定路径加载图片
	 */
	public static Bitmap loadBitmap(String imgpath) {
		return BitmapFactory.decodeFile(imgpath);
	}


	/**
	 * 从给定的路径加载图片，并指定是否自动旋转方向
	 */
	public static Bitmap loadBitmap(String imgpath, boolean adjustOritation) throws OutOfMemoryError {
		if (!adjustOritation) {
			return loadBitmap(imgpath);
		} else {
			Bitmap bm = loadBitmap(imgpath);
			int digree = 0;
			ExifInterface exif = null;
			try {
				exif = new ExifInterface(imgpath);
			} catch (IOException e) {
				e.printStackTrace();
				exif = null;
			}
			if (exif != null) {
				// 读取图片中相机方向信息
				int ori = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
						ExifInterface.ORIENTATION_UNDEFINED);
				// 计算旋转角度
				switch (ori) {
					case ExifInterface.ORIENTATION_ROTATE_90:
						digree = 90;
						break;
					case ExifInterface.ORIENTATION_ROTATE_180:
						digree = 180;
						break;
					case ExifInterface.ORIENTATION_ROTATE_270:
						digree = 270;
						break;
					default:
						digree = 0;
						break;
				}
			}
			if (digree != 0) {
				// 旋转图片
				Matrix m = new Matrix();
				m.postRotate(digree);
				bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
						bm.getHeight(), m, true);
			}
			return bm;
		}
	}


	private Bitmap getImage(String srcPath) throws OutOfMemoryError {
		BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);//此时返回bm为空

		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 800f;//这里设置高度为800f
		float ww = 480f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
		newOpts.inSampleSize = be;//设置缩放比例
		//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
		return bitmap;//压缩好比例大小后再进行质量压缩
	}

	private void refreshGallery(String file) {
		Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		mediaScanIntent.setData(Uri.fromFile(new File(file)));
		sendBroadcast(mediaScanIntent);
	}

	/**
	 * 截屏，这里就是截屏的地方了，我这里是截屏RelativeLayout，
	 * 只要你将需要的信息放到这个RelativeLayout里面去就可以截取下来了
	 *
	 * @param waterPhoto waterPhoto
	 * @return Bitmap
	 */
	public Bitmap getScreenPhoto(RelativeLayout waterPhoto) {
		View view = waterPhoto;
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
		int width = view.getWidth();
		int height = view.getHeight();
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap, 0, 0, width, height);
		view.destroyDrawingCache();
		bitmap = null;
		return bitmap1;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.iv_back:
				Intent intent = new Intent();
				intent.putExtra(CameraActivity.CAMERA_PATH_VALUE2, path);
				setResult(0, intent);
				finish();
				break;

			case R.id.photo_process_action:
				Bitmap screenPhoto = getScreenPhoto(rl_layout);
				try {
					String saveToSDCard = saveToSDCard(screenPhoto);
					refreshGallery(saveToSDCard);
					Intent intentOk = new Intent();
					intentOk.putExtra("photoPath", saveToSDCard);
					setResult(3, intentOk);
					finish();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
		}
	}

	/**
	 * 将拍下来的照片存放在SD卡中
	 */
	public String saveToSDCard(Bitmap croppedImage) throws IOException {

		String imagePath = "";
		try {
			imagePath = saveToFile(croppedImage);
		} catch (Exception e) {

		}
		croppedImage.recycle();
		return imagePath;
	}
	// 保存图片文件
	public static String saveToFile(Bitmap croppedImage)
			throws FileNotFoundException, IOException {
		File sdCard = Environment.getExternalStorageDirectory();
		File dir = new File(sdCard.getAbsolutePath() + "/DCIM/Camera/");
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			Intent intent = new Intent();
			intent.putExtra(CameraActivity.CAMERA_PATH_VALUE2, path);
			setResult(0, intent);
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
