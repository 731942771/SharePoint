package com.cuiweiyou.sharepoint.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * <b>类名</b>: IntentStateUtil.java，网络状态判断 <br/>
 * <b>说明</b>: 
 * <li>权限 &lt;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
 * <li>getWifiState()，获取wifi状态 
 * <br/>
 * <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午11:06:48 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class IntentStateUtil {
	
	private static ConnectivityManager manager;

	private IntentStateUtil(){}

	/**
	 * <b>功能</b>：getWifiState，获取wifi状态 <br/>
	 * 
	 * @return true wifi可用<br/>
	 */
	public static boolean getWifiState(Context ctx){
		if(null == manager)
			manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// Wifi网络信息对象
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(null == networkInfo)
			return false;
		
		if(networkInfo.isConnected())
			return true;
		
		return false;
	}
	
	/**
	 * <b>功能</b>：get3G4GState，获取3G4G状态 <br/>
	 * 
	 * @return true 3G4G可用<br/>
	 */
	public static boolean get3G4GState(Context ctx){
		if(null == manager)
			manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// Wifi网络信息对象
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if(null == networkInfo)
			return false;
		
		if(networkInfo.isConnected())
			return true;
		
		return false;
	}

	/**
	 * <b>功能</b>：getNetState，无论wifi或234g，只要一个能联网就返回true <br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @return
	 */
	public static boolean getNetState(Context ctx){
		if(getWifiState(ctx))
			return true;
		
		if(get3G4GState(ctx))
			return true;
		
		return false;
	}
}
