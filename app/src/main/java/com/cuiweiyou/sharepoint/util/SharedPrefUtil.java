package com.cuiweiyou.sharepoint.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Objects;
import java.util.Set;

/**
 * <b>类名</b>: SharedPrefUtil.java，小数据存储SP工具 <br/>
 * <b>说明</b>: <br/>
 * 
 * @author cuiweiyou.com <br/>
 */
public class SharedPrefUtil {

	private static SharedPreferences mSP;

	private SharedPrefUtil(){
	}

	private static void getSP(Context ctx) {
		mSP = ctx.getSharedPreferences(ApkUtil.getPackageName(ctx).replace(".", ""), Context.MODE_PRIVATE);
	}

	/** 保存 k-v <br/>
	 * String，Integer，Float，Long，Boolean
	 **/
	public static void setValue(Context ctx, String key, Object value) {
		if(null == mSP)
			getSP(ctx);

		String name = value.getClass().getSimpleName();
		if("String".equals(name)){
			setStringValue(ctx, key, value.toString());
		} else if("Integer".equals(name)){
			Integer i = Integer.valueOf(value.toString());
			setIntegerValue(ctx, key, i);
		} else if("Float".equals(name)){
			Float f = Float.valueOf(value.toString());
			setFloatValue(ctx, key, f);
		} else if("Long".equals(name)){
			Long l = Long.valueOf(value.toString());
			setLongValue(ctx, key, l);
		} else if("Boolean".equals(name)){
			Boolean b = Boolean.valueOf(value.toString());
			setBooleanValue(ctx, key, b);
		}
	}

	/** 保存字符串k-v */
	public static void setStringValue(Context ctx, String key, String value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putString(key, value);
		edit.commit();
	}

	/** 保存Set字符串集合k-v */
	public static void setStringSetValue(Context ctx, String key, Set<String> value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putStringSet(key, value);
		edit.commit();
	}

	/** 保存布尔型k-v */
	public static void setBooleanValue(Context ctx, String key, Boolean value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	/** 保存int数字k-v */
	public static void setIntegerValue(Context ctx, String key, int value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	/** 保存Float型k-v */
	public static void setFloatValue(Context ctx, String key, Float value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putFloat(key, value);
		edit.commit();
	}

	/** 保存Long型k-v */
	public static void setLongValue(Context ctx, String key, Long value) {
		if(null == mSP)
			getSP(ctx);

		Editor edit = mSP.edit();
		edit.putLong(key, value);
		edit.commit();
	}

	public static Boolean getBooleanValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getBoolean(key, false);
    }

    public static Float getFloatValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getFloat(key, -1f);
    }

    public static int getIntValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getInt(key, -1);
    }

    public static Long getLongValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getLong(key, -1);
    }

    public static String getStringValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getString(key, "");
    }

    public static Set<String> getStringSetValue(Context ctx, String key){
        if(null == mSP)
            getSP(ctx);

        return mSP.getStringSet(key, null);
    }
}
