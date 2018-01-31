package com.yijie.com.studentapp.utils;


import android.util.Log;

public class LogUtil {

	private static final String TAG="--LogUtil--";
	public static final boolean DEBUG=true;  
	
	public static void i(Object message){
		if(DEBUG){
			Log.i(TAG, message.toString());
		}
	}
	public static void i(String tag,Object message){
		if(DEBUG){
			Log.i(tag, message.toString());
		}
	}
	
	public static void e(Object message){
		if(DEBUG){
			Log.e(TAG, message.toString());
		}
	}
	public static void e(String tag,Object message){
		if(DEBUG){
			Log.e(tag, message.toString());
		}
	}
	
	public static void d(Object message){
		if(DEBUG){
			Log.d(TAG, message.toString());
		}
	}
	public static void d(String tag,Object message){
		if(DEBUG){
			Log.d(tag, message.toString());
		}
	}
	
	public static void w(Object message){
		if(DEBUG){
			Log.w(TAG, message.toString());
		}
	}
	public static void w(String tag,Object message){
		if(DEBUG){
			Log.w(tag, message.toString());
		}
	}
	
	public static void w(Object message,Throwable tr){
		if(DEBUG){
			Log.w(TAG, message.toString(),tr);
		}
	}
	public static void w(String tag,Object message,Throwable tr){
		if(DEBUG){
			Log.w(tag, message.toString(),tr);
		}
	}
}
