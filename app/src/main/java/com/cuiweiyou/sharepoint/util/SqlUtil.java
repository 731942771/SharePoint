package com.cuiweiyou.sharepoint.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Sqlite工具
 */
public class SqlUtil {

    private static SQLiteOpenHelper mSqlite;
    private static String TB_NAME_CHAPTER = "tb_think_chapter";
    private static String TB_NAME_CLASS = "tb_think_class";

    private SqlUtil() {
    }

    private static void initSqlite(Context ctx) {
        /** 1.创建数据库助手*/
        mSqlite = new SQLiteOpenHelper(ctx, ApkUtil.getPackageName(ctx).replace(".", "") + ".db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                // SimpleCursorAdapter只识别下划线首字符的主键。
                db.execSQL("CREATE TABLE " + TB_NAME_CHAPTER + " " +  // 表 章节
                        "(_id INTEGER PRIMARY KEY, " +         // 主键
                        "c_chapter VARCHAR, " +                // 章节号
                        "c_title VARCHAR" +                    // 标题
                        "c_version VARCHAR, " +                // 章节的版本。表示是否删除、更新
                        ");");

                db.execSQL("CREATE TABLE " + TB_NAME_CLASS + " " +    // 表 课程
                        "(_id INTEGER PRIMARY KEY, " +         // 主键
                        "c_chapter VARCHAR, " +                // 所属的章节号
                        "c_class VARCHAR, " +                  // 课程号
                        "c_version VARCHAR, " +                // 课程的版本。如果从json读到的版本更新，则更新db并重新下载
                        "c_title VARCHAR" +                    // 标题
                        "c_url VARCHAR" +                      // 课程url
                        ");");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (newVersion > oldVersion) {
                    db.execSQL("-- v");    // 备份原表tb的数据到临时表tmp，重构tb表，导入tmp数据到tb，删除tmp
                }
            }

            @Override
            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                if (oldVersion > newVersion) {
                    db.execSQL("-- v");    // 翻新表
                }
            }
        };
    }

    /**
     * 删除章节<br/>
     * 如果json里的版本号是0，就删除章节（及课程）
     *
     * @param index 章节号。执行条件
     */
    public static void deleteChapter(Context ctx, String index) {
        if (null == mSqlite)
            initSqlite(ctx);

        /* 可写的数据库 */
        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        //wdb.execSQL("delete from " + TB_NAME_CHAPTER + " where c_chapter = '" + index + "'");
        int count = wdb.delete(TB_NAME_CHAPTER, "c_chapter = ?", new String[]{index}); // 影响的行数量

        wdb.close();
    }

    /**
     * 插入章节<br/>
     *
     * @param index   章节号。执行条件
     * @param title   标题
     * @param version 版本号
     */
    public static void insertChapter(Context ctx, String index, String title, String version) {
        if (null == mSqlite)
            initSqlite(ctx);

        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        //wdb.execSQL("insert into " + TB_NAME_CHAPTER +
        //        " (c_chapter, c_title, c_version)" +
        //        " values" +
        //        " ('" + index + "', '" + title + "', '" + version + "')");

        ContentValues cv_insert = new ContentValues();
        cv_insert.put("c_chapter", index);
        cv_insert.put("c_title", title);
        cv_insert.put("c_version", version);
        long numb = wdb.insert(TB_NAME_CHAPTER, null, cv_insert);  // 返回最新插入一条的ID

        wdb.close();
    }

    /**
     * 更新章节<br/>
     * 如果json的版本号高于db里读到的，就更新。
     *
     * @param index   章节号。执行条件
     * @param title   标题
     * @param version 版本号
     */
    public static void updateChapter(Context ctx, String index, String title, String version) {
        if (null == mSqlite)
            initSqlite(ctx);

        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        //wdb.execSQL("update " + TB_NAME_CHAPTER +
        //        " set c_title = '" + title +
        //        "', c_version = '" + version +
        //        "' where c_chapter = " + index);

        ContentValues cv_update = new ContentValues();
        cv_update.put("c_title", title);
        cv_update.put("c_version", version);
        int count = wdb.update(TB_NAME_CHAPTER, cv_update, "c_chapter = ?", new String[]{index}); // 影响的行数量

        wdb.close();
    }

    /**
     * 删除课程<br/>
     *
     * @param index_chapter 章节号。执行条件
     * @param index_class   课程号。执行条件。如果是null或空，此章节下的课程全删
     */
    public static void deleteClass(Context ctx, String index_chapter, String index_class) {
        if (null == mSqlite)
            initSqlite(ctx);

        /* 可写的数据库 */
        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        if (null != index_class || !"".equals(index_class)) {
            int count = wdb.delete(TB_NAME_CLASS, "c_chapter = ? AND c_class = ?", new String[]{index_chapter, index_class}); // 影响的行数量
        } else {
            int count = wdb.delete(TB_NAME_CLASS, "c_chapter = ?", new String[]{index_chapter}); // 影响的行数量
        }

        wdb.close();
    }

    /**
     * 插入课程<br/>
     *
     * @param index_chapter 章节号
     * @param index_class   课程号
     * @param title         标题
     * @param url           课程url
     * @param version       版本号
     */
    public static void insertClass(Context ctx, String index_chapter, String index_class, String title, String url, String version) {
        if (null == mSqlite)
            initSqlite(ctx);

        /* 可写的数据库 */
        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        ContentValues cv_insert = new ContentValues();
        cv_insert.put("c_chapter", index_chapter);
        cv_insert.put("c_class", index_class);
        cv_insert.put("c_title", title);
        cv_insert.put("c_url", url);
        cv_insert.put("c_version", version);
        long numb = wdb.insert(TB_NAME_CLASS, null, cv_insert);  // 返回最新插入一条的ID

        wdb.close();
    }

    /**
     * 更新课程<br/>
     *
     * @param index_chapter 章节号。执行条件
     * @param index_class   课程号。执行条件
     * @param title         标题
     * @param url           课程url
     * @param version       版本号
     */
    public static void updateClass(Context ctx, String index_chapter, String index_class, String title, String url, String version) {
        if (null == mSqlite)
            initSqlite(ctx);

        /* 可写的数据库 */
        SQLiteDatabase wdb = mSqlite.getWritableDatabase();

        ContentValues cv_update = new ContentValues();
        cv_update.put("c_title", title);
        cv_update.put("c_url", url);
        cv_update.put("c_version", version);
        int count = wdb.update(TB_NAME_CLASS, cv_update, "c_chapter = ? AND c_class = ?", new String[]{index_chapter, index_class}); // 影响的行数量

        wdb.close();
    }

    /**
     * 提取（指定）章节<br/>
     *
     * @param index 查询条件，如果为null或空，则查询全部
     */
    public static String getChapter(Context ctx, String index) {
        if (null == mSqlite)
            initSqlite(ctx);

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        /** 只读类型的数据库 */
        SQLiteDatabase rdb = mSqlite.getReadableDatabase();

        // sql查询
        //Cursor cursor_sql = null;
        //if(null == index || "".equals(index)){
        //    cursor_sql = rdb.rawQuery("select * from " + TB_NAME_CHAPTER, null);
        //} else {
        //    cursor_sql = rdb.rawQuery("select * from " + TB_NAME_CHAPTER + " where c_capter = ?", new String[]{index});
        //}
        //while (cursor_sql.moveToNext()) {
        //    int _capter = cursor_sql.getColumnIndex("c_capter");
        //    int _title = cursor_sql.getColumnIndex("c_title");
        //    int _version = cursor_sql.getColumnIndex("c_version");
        //    String capter = cursor_sql.getString(_capter);
        //    String title = cursor_sql.getString(_title);
        //    String version = cursor_sql.getString(_version);
        //    Log.e("ard", "capter:" + capter + "title:" + title + ", version:" + version);
        //}
        //cursor_sql.close();

        // api形式。（表，列，where条件，条件值，groupby，having，orderby）
        Cursor cursor = null;
        if (null == index || "".equals(index)) {
            cursor = rdb.query(TB_NAME_CHAPTER, new String[]{"_id", "c_capter", "c_title", "c_version"}, null, null, null, null, null);
        } else {
            cursor = rdb.query(TB_NAME_CHAPTER, new String[]{"_id", "c_title", "c_version"}, "c_capter = ?", new String[]{index}, null, null, null);
        }

        while (cursor.moveToNext()) {
            int _id = cursor.getColumnIndex("_id");
            int _chapter = cursor.getColumnIndex("c_chapter");
            int _title = cursor.getColumnIndex("c_title");
            int _version = cursor.getColumnIndex("c_version");
            String id = cursor.getString(_id);
            String chapter = cursor.getString(_chapter);
            String title = cursor.getString(_title);
            String version = cursor.getString(_version);

            sb.append("{\"id\":\"" + id + "\", \"chapter\":\"" + chapter + "\", \"title\":\"" + title + "\", \"version\":\"" + version + "\"},");
        }

        cursor.close();
        rdb.close();

        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);

        sb.append("]");

        return sb.toString();
    }

    /**
     * 提取（指定）课程<br/>
     * 章节号或课程号有一个无效即查询全部
     *
     * @param index_chapter 章节号
     * @param index_class   课程号。为null或空即查询全部
     */
    public static String getClasss(Context ctx, String index_chapter, String index_class) {
        if (null == mSqlite)
            initSqlite(ctx);

        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"chapter\":\"" + index_chapter + "\",");
        sb.append("classes:[");

        SQLiteDatabase rdb = mSqlite.getReadableDatabase();

        Cursor cursor = null;
        if (null == index_class || "".equals(index_class)) {
            cursor = rdb.query(
                    TB_NAME_CLASS,
                    new String[]{"c_capter", "c_class", "c_title", "c_url", "c_version"},
                    "c_capter = ?",
                    new String[]{index_chapter},
                    null, null, null);
        } else {
            cursor = rdb.query(
                    TB_NAME_CLASS,
                    new String[]{"c_capter", "c_class", "c_title", "c_url", "c_version"},
                    "c_capter = ? AND c_class = ?",
                    new String[]{index_chapter, index_class},
                    null, null, null);
        }

        while (cursor.moveToNext()) {
            int _chapter = cursor.getColumnIndex("c_chapter");
            int _class = cursor.getColumnIndex("c_class");
            int _title = cursor.getColumnIndex("c_title");
            int _url = cursor.getColumnIndex("c_url");
            int _version = cursor.getColumnIndex("c_version");

            String chapter = cursor.getString(_chapter);
            String classs = cursor.getString(_class);
            String title = cursor.getString(_title);
            String url = cursor.getString(_url);
            String version = cursor.getString(_version);

            sb.append("{\"id\":\"" + id + "\", \"classs\":\"" + classs + "\", \"title\":\"" + title + "\", \"url\":\"" + url + "\", \"version\":\"" + version + "\"},");
        }

        cursor.close();
        rdb.close();

        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);

        sb.append("]");
        sb.append("}");

        return sb.toString();
    }
}
