package com.cuiweiyou.sharepoint.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

/**
 * <b>类名</b>: PhoneinfoUtil.java，获取手机的一些基本信息 <br/>
 * <b>说明</b>: <br/>
 * &lt; uses-permission android:name="android.permission.READ_PHONE_STATE" /&gt;
 * &lt;!-- 获取手机信息权限 --&gt;
 * 
 * @author cuiweiyou.com <br/>
 */
public class PhoneinfoUtil {

	private static TelephonyManager tm;

	private PhoneinfoUtil() {
	}

	private static void getTM(Context ctx) {
		tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
	}

	/** 基本信息：IMEI号，IESI号，手机型号，品牌，号码 */
	public static String getBaseinfo(Context ctx) {
		if (null == tm)
			getTM(ctx);

		String imei = tm.getDeviceId(); // IMEI
		String imsi = tm.getSubscriberId(); // IESI号
		String mtyb = android.os.Build.BRAND; // 品牌
		String mtype = android.os.Build.MODEL; // 型号
		String numer = tm.getLine1Number(); // 手机号码，有的可得，有的不可得

		return new StringBuilder()//
				.append("IMEI:" + imei)//
				.append("\nIMSI:" + imsi)//
				.append("\n品牌:" + mtyb)//
				.append("\n型号:" + mtype)//
				.append("\n号码:" + numer)//
				.toString();//
	}

	/** 其它信息 */
	public static String getOtherInfo(Context ctx) {
		if (null == tm)
			getTM(ctx);

		StringBuilder sb = new StringBuilder();

		sb.append("DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
		sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
		sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
		sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
		sb.append("\nNetworkType = " + tm.getNetworkType());
		sb.append("\nPhoneType = " + tm.getPhoneType());
		sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
		sb.append("\nSimOperator = " + tm.getSimOperator());
		sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
		sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
		sb.append("\nSimState = " + tm.getSimState());
		sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
		
		return sb.toString();
	}

	/** 通讯服务商信息 */
	public static String getProvidersName(Context ctx) {
		if (null == tm)
			getTM(ctx);

		String ProvidersName = "N/A";
		try {
			String IMSI = tm.getSubscriberId();
			// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			System.out.println(IMSI);
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				ProvidersName = "中国移动";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "中国联通";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "中国电信";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ProvidersName;
	}

	/** 获取android当前可用内存大小 */
	public static String getAvailMemory(Context ctx) {// 获取android当前可用内存大小

		ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
		MemoryInfo mi = new MemoryInfo();
		am.getMemoryInfo(mi);
		// mi.availMem; 当前系统的可用内存

		return Formatter.formatFileSize(ctx, mi.availMem);// 将获取的内存大小规格化
	}

	/** 获得系统总内存 */
	public static String getTotalMemory(Context ctx) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}

		return Formatter.formatFileSize(ctx, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	/** 手机CPU信息。1-cpu型号，2-cpu频率 */
	public static String[] getCpuInfo() {
		String str1 = "/proc/cpuinfo";
		String str2 = "";
		String[] cpuInfo = { "", "" }; // 1-cpu型号 //2-cpu频率
		String[] arrayOfString;
		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr, 8192);
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			for (int i = 2; i < arrayOfString.length; i++) {
				cpuInfo[0] = cpuInfo[0] + arrayOfString[i] + " ";
			}
			str2 = localBufferedReader.readLine();
			arrayOfString = str2.split("\\s+");
			cpuInfo[1] += arrayOfString[2];
			localBufferedReader.close();
		} catch (IOException e) {
		}

		return cpuInfo;
	}
}
