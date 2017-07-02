package com.cuiweiyou.sharepoint.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeUtil {

	private DatetimeUtil() {
	}

	/** 获取格式为 yyyy-mm-dd 的当前日期 */
	public static String getNowDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	/** 获取格式为 yyyy-MM-dd_HH:mm:ss 的当前日期 */
	public static String getNowTime() {
		return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
	}

}
