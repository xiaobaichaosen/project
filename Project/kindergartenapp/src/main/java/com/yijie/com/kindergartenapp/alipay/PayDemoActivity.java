package com.yijie.com.kindergartenapp.alipay;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;

import com.yijie.com.kindergartenapp.R;
import com.yijie.com.kindergartenapp.alipay.util.OrderInfoUtil2_0;

import java.util.Map;

/**
 *  重要说明:
 *  
 *  这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
 *  真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
 *  防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
 */
public class PayDemoActivity extends FragmentActivity {
	
	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2XaoN1FxeQITJO/X2A+Kn5ji+9oouDDFgEe4/VAmY/5z4f0UP668g0FkwqnPtCTEvTbTMA+IhTapgDJRCmKNn7QsMUdmMj7AkgKJ//gN7mignOzn1D8JkqDTtK5O7N1/GAJq3fE12kqnPWUZwrDv092YLdiPSJ36P4DjZtvz2b2fNu408j0D8a5hDzSf4KEMscO5/Z48tAZZ388Yz3Zlr89goCGptfaSiA9bPFQZoLoTZIAZJ5K5HQ9GluNO6YpsYnIMlRnEwUpBgASVj4PkNG7mh9Tam8MZ5H/UrH9L/xkoZwgqBMuX+nsLXQKb493Xht5x2q09s4ENCgpsQGsLBAgMBAAECggEAWLDq4Hj0Rz53lercbz5qgB+eX3lFsKlrZf6ycQgs9sTyEYTMELcd4AaUKe/sG07KJehT0pjc5DeCcd4MSOs1JXkKU36HIC5NiyMoZLkj9nv9pT7PrBhzAUMxIlt8KKQApypqFGyWu+EORvJzKJ8LNdMQROi6+R0OF2kEHRY6ppqrCO9IsDvMkcgBxFEkcgPV0rPFKUs5eqbi+IlIp8za+F06HY+BSsbIg4bZa0d4oCJtyJvJOuFygCKCvwPrew4vCrZAbWB+V/CHVguZ5Z18+pxehTPaN+4xSk/+eq42En/DlnUMjxFdMiZXIKdjSs3NyisTSdH+jVXjYSsWx4m71QKBgQDpMYRw1A6JjEc69CT0FfzRPrg4onYYYaw8j5Ws8WZAbk8GutcscMiBmpV/Y265T1r9vjQaBVaRg9WC7UYWd0HIVVKeC827H1oDZOKukoz2YhTwTk4QvK8foxA/03wZeIX3B/nClE5+k2U1WKnDD+CSemD+GvsxfIiUvmwSVrCsAwKBgQDIM5LMQQOhM38rIig9n/f+f4kVHNFQc3pRIivC6INbbDWEOdDDsQfWtza4nwnXSeBjL7IJ2hRB+GzUobqMX1SxPyVXOjfXCJ5oIEZkHBX1m4GRlLlkpVDIMEHOCwB6k6ltzkkOT9A725xNao3EOSRw+1W1Erlf7RP5q3bsz/706wKBgQDHesJuuhgNdeBhAAS0gDKa1GpGmEMprDPoQnOpU39C0pnrid0TeQ8sPw5vwKExCD8wHIr5T2O0avA0ZcC06yNsugBIjpRY5ALeMGjxKRBIm7KJtpJ1ItMz3wvBbaoVJ9KBdWMkHjXfdTr4nXXrFSPoaaOa+32x4XP8ucA9F8dd/QKBgQCkvENUDT9WWxCORpoEM2iWIw2CsObPo2YH8u09gaPtC3px5j0Rec9wb+v4OO2ZGHIqS6ndj3RR4IB06uuc7Ci9fK9WOlKOf8oQ9lXVtIKhSidwyodO+PUi9QqA5+6p7m1hLHhS7Xv0dT4FOeOTjk2dJkMdL14YP/XK4LuIG5K4pwKBgCz5yk/FwsF6gtnCQNC0at/QcuEepXA+7Iwf9sprnAi7egm+7e5QuoSWoq3pS8GiJmW5UOx9nb9AWwHSycTdJ2LRBi1wcQL81AguoyxkHaQwgXCPAJ7Ey0ZWdC7Vw7K6++Y76wp+Od3Q8V4+JxhyZ5hiJOztLG80iroW7HZNTg3q";
	public static final String RSA_PRIVATE = "";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@SuppressWarnings("unused")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				@SuppressWarnings("unchecked")
				PayResult payResult = new PayResult((Map<String, String>) msg.obj);
				/**
				 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
				 */
				String resultInfo = payResult.getResult();// 同步返回需要验证的信息
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为9000则代表支付成功
				if (TextUtils.equals(resultStatus, "9000")) {
					// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
				} else {
					// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
					Toast.makeText(PayDemoActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
				}
				break;
			}
			case SDK_AUTH_FLAG: {
				@SuppressWarnings("unchecked")
				AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
				String resultStatus = authResult.getResultStatus();

				// 判断resultStatus 为“9000”且result_code
				// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
				if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
					// 获取alipay_open_id，调支付时作为参数extern_token 的value
					// 传入，则支付账户为该授权账户
					Toast.makeText(PayDemoActivity.this,
							"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
							.show();
				} else {
					// 其他状态值则为授权失败
					Toast.makeText(PayDemoActivity.this,
							"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

				}
				break;
			}
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pay_main);
	}
	
	/**
	 * 支付宝支付业务
	 * 
	 * @param v
	 */
	public void payV2(View v) {
		if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
							//
							finish();
						}
					}).show();
			return;
		}
	
		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * orderInfo的获取必须来自服务端；
		 */
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
		String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
		final String orderInfo = orderParam + "&" + sign;
		
		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(PayDemoActivity.this);
				Map<String, String> result = alipay.payV2(orderInfo, true);
				Log.i("msp", result.toString());
				
				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 支付宝账户授权业务
	 * 
	 * @param v
	 */
	public void authV2(View v) {
		if (TextUtils.isEmpty(PID) || TextUtils.isEmpty(APPID)
				|| (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))
				|| TextUtils.isEmpty(TARGET_ID)) {
			new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER |APP_ID| RSA_PRIVATE| TARGET_ID")
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface, int i) {
						}
					}).show();
			return;
		}

		/**
		 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
		 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
		 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
		 * 
		 * authInfo的获取必须来自服务端；
		 */
		boolean rsa2 = (RSA2_PRIVATE.length() > 0);
		Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(PID, APPID, TARGET_ID, rsa2);
		String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);
		
		String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
		String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
		final String authInfo = info + "&" + sign;
		Runnable authRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造AuthTask 对象
				AuthTask authTask = new AuthTask(PayDemoActivity.this);
				// 调用授权接口，获取授权结果
				Map<String, String> result = authTask.authV2(authInfo, true);

				Message msg = new Message();
				msg.what = SDK_AUTH_FLAG;
				msg.obj = result;
				mHandler.sendMessage(msg);
			}
		};

		// 必须异步调用
		Thread authThread = new Thread(authRunnable);
		authThread.start();
	}
	
	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
	 * 
	 * @param v
	 */
	public void h5Pay(View v) {
		Intent intent = new Intent(this, H5PayDemoActivity.class);
		Bundle extras = new Bundle();
		/**
		 * url 是要测试的网站，在 Demo App 中会使用 H5PayDemoActivity 内的 WebView 打开。
		 *
		 * 可以填写任一支持支付宝支付的网站（如淘宝或一号店），在网站中下订单并唤起支付宝；
		 * 或者直接填写由支付宝文档提供的“网站 Demo”生成的订单地址
		 * （如 https://mclient.alipay.com/h5Continue.htm?h5_route_token=303ff0894cd4dccf591b089761dexxxx）
		 * 进行测试。
		 * 
		 * H5PayDemoActivity 中的 MyWebViewClient.shouldOverrideUrlLoading() 实现了拦截 URL 唤起支付宝，
		 * 可以参考它实现自定义的 URL 拦截逻辑。
		 */
		String url = "http://m.taobao.com";
		extras.putString("url", url);
		intent.putExtras(extras);
		startActivity(intent);
	}

}
