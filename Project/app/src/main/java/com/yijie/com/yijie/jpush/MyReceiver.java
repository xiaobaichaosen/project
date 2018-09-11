package com.yijie.com.yijie.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.yijie.com.yijie.MainActivity;
import com.yijie.com.yijie.R;
import com.yijie.com.yijie.activity.NewNoTrainActivity;
import com.yijie.com.yijie.activity.SchoolProjectDetailActivity;
import com.yijie.com.yijie.activity.TempActivity;
import com.yijie.com.yijie.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JIGUANG-Example";
	private String map,mapvalue;
	JSONObject obj;
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Bundle bundle = intent.getExtras();
			map = bundle.getString(JPushInterface.EXTRA_EXTRA);

			if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
				String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
				LogUtil.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
				//send the Registration Id to your server...

			} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
				LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
//				processCustomMessage(context, bundle);

			} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
				LogUtil.d(TAG, "[MyReceiver] 接收到推送下来的通知");

					int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
					Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

			} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
					Log.d(TAG, "[MyReceiver] 用户点击打开了通知");
						Intent goAcitvityIntent = new Intent();
					try {
						obj = new JSONObject(map);
						mapvalue=obj.getString("type");

					} catch (JSONException e) {
						e.printStackTrace();
					}
					if(mapvalue.equals("0")){
						//打开自定义的Activity
                        String schoolId = obj.getString("schoolId");
                        String schoolName = obj.getString("schoolName");
							goAcitvityIntent.putExtra("page",3);
                        goAcitvityIntent.putExtra("schoolId",schoolId);
                        goAcitvityIntent.putExtra("schoolName",schoolName);
                        goAcitvityIntent.setClass(context, TempActivity.class);
                        goAcitvityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							context.startActivity(goAcitvityIntent);

					}else 	if(mapvalue.equals("1")){
						//打开自定义的Activity
						goAcitvityIntent.setClass(context, NewNoTrainActivity.class);
						goAcitvityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(goAcitvityIntent);

					}

			}
		} catch (Exception e){

		}

	}



}
