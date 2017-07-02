package com.cuiweiyou.sharepoint.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.util.Log;

public class Md5Tool {
	
	private Md5Tool(){}

	/** 加密次数 **/
	private static int step = 0;
	private static int step_count = 5;
	/** 加密结果 **/
	private static String md5pswd;

	/**  
	 * <b>功能</b>：getMd5Pswd，执行加密，返回密文字符串 <br/>
	 * <b>说明</b>: 5次加密<br/>
	 * 
	 * @param pd 密码明文
	 * @return String 加密后文本
	 */
	public static String getMd5Pswd(String pd) {

		/** 控制加密次数，小于3，限制在2次 **/ // 一般加密3次，cmd5网站破解即收费。银行密码一般加密15-30次
		if (step < step_count) {
			try {
				// 信息摘要算法器
				MessageDigest digest = MessageDigest.getInstance("md5");

				// 加密明文的字节码
				byte[] bs = digest.digest(pd.getBytes());

				// 动态字符串
				StringBuffer buffer = new StringBuffer();

				// 动态字符串
				for (byte b : bs) {
					// 与 运算
					int nub = b & 0xff; // 加盐：int nub = b &
										// 0xfffffffffffffffffffff;//多加几个f

					// 返回字符串表示的无符号整数所表示以十六进制值
					String string = Integer.toHexString(nub);

					// 如果不足8位，补0
					if (string.length() == 1) {
						buffer.append("0");
					}

					// 保存加密后的十六进制密文
					buffer.append(string);

				}
				// 转文本
				md5pswd = buffer.toString();
		
				step++;

				// 递归，多次加密
				getMd5Pswd(md5pswd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else {
		
			Log.e("ard", "step:" + step + "， 密文：" + md5pswd);
			step = 0; // 静态的，务必复位
		}
		
		return md5pswd;
	}
}