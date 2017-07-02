package com.cuiweiyou.sharepoint.util;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import android.util.Log;

/**
 * <b>类名</b>: HttpRequestUtil.java，网络请求工具类 <br/>
 * <b>说明</b>: &emsp;&emsp;
 * <li>注意注册权限 &lt;uses-permission android:name="android.permission.INTERNET"
 * /&gt; &emsp;&emsp;
 * <li>网络请求是延时操作，在AsyncTask或子线程中调用执行 &emsp;&emsp;
 * <li>getRemoteVersion(String url)获取远程版本 <br/>
 * <br/>
 * <b>创建</b>: 2016-2016年6月22日_上午10:57:41 <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class HttpRequestAndPostUtil {

	private HttpRequestAndPostUtil() {
	}

	/**
	 * <b>功能</b>：getJsonObject，请求 JsonObject<br/>
	 * <b>说明</b>:
	 * <li>HttpURLConnection实现
	 * <li>如果网络状态不为200，返回null <br/>
	 * 
	 * @param url 远程地址
	 * @return jsonObject数据字串。如果网络非200 返回null
	 */
	public static String getJsonObject(String url) throws IOException, EOFException, SocketTimeoutException {

		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(10 * 1000);
		conn.setReadTimeout(10 * 1000);
		conn.setRequestMethod("GET");
		conn.setDoInput(true);
		conn.connect();

		if (conn.getResponseCode() == 200) {
			String buffer;
			StringBuilder sb = new StringBuilder();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			while ((buffer = br.readLine()) != null) {
				sb.append(buffer);
			}

			br.close();

			return sb.toString();
		}

		return null;
	}

	/**
	 * <b>功能</b>：postSpitslot，发表新口水 <br/>
	 * <b>说明</b>: <br/>
	 * 
	 * @return 是否成功 0-失败，1-成功
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static String post(String url, String parames) throws MalformedURLException, ConnectException, IOException {
//		Log.e("ard", "地址：" + url + "，参数：" + parames);
		
		String result = null;
		
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		conn.setConnectTimeout(30 * 1000);
		conn.setReadTimeout(30 * 1000);
		conn.setRequestMethod("POST");
		conn.setDoInput(true);
		conn.setDoOutput(true);
		OutputStream os = conn.getOutputStream();

		byte[] args = parames.getBytes();
		
//			conn.setRequestProperty("Accept", "application/json, text/javascript, */*; q=0.01");
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Charset", "UTF-8");
//			conn.setRequestProperty("Content-Length", "" + args.length);// 没问题
		
		os.write(args);
		
		if (200 == conn.getResponseCode()) {
			InputStream in = conn.getInputStream();
			InputStreamReader ir = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(ir);
			
			String strLine;
			StringBuffer buffer = new StringBuffer();
			while ((strLine = br.readLine()) != null) {
				buffer.append(strLine);
			}
			
			br.close();
			ir.close();
			in.close();
			
			result = buffer.toString();

		} else {
			result = "0";
		}
		
		os.close();
		conn.disconnect();

		return result;
	}
}
