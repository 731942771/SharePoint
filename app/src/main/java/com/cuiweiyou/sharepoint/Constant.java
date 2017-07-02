package com.cuiweiyou.sharepoint;

import android.content.Context;

/**
 * 初始的，固定的
 */
public class Constant {
    private static final String HOST = "你的HOST";

    private Constant() {
    }

    /** 设置页配置webview中是否屏蔽超链接的key，布尔型，true屏蔽，false不。直接作为sp的key使用 */
    public static final String KEY_SETTING_WEBVIEW_LINK_SHIELDING = "key_setting_webview_link_shielding";

    /** 首页默认的网页，布尔型，true使用历史页面，false使用自定义页面。直接作为sp的key使用 **/
    public static final String KEY_SETTING_DEFAULT_URL_PAGE = "key_setting_default_url_page";

    /** 设置页配置用户自定义目录url的key，布尔型，true默认的，false自定义的。直接作为sp的key使用 **/
    public static final String KEY_SETTING_MENU_DEFINE_URL = "key_setting_menu_define_url";

    /** 本地存储的用户url的版本 **/
    public static final  String KEY_INT_LOCAL_URL_VERSION = "key_int_local_url_version";

    //////////////////////////////////////////////////////////////////////////

    /** 浏览历史页 启动即访问的网页。 */
    public static String KEY_URL_PAGE_HISTORY = HOST;

    /** 用户自定义页 启动即访问的网页。 */
    public static String KEY_URL_MENU_DEFINE = HOST + "opensource/sharepoint/menu_list.json";

    /** 用户自定义目录url。 */
    public static String KEY_URL_PAGE_USER_DEFAULT = HOST;

    /** 默认页 启动即访问的网页。 */
    public static final String URL_PAGE_DEFAULT = HOST;

    /** 网友提供的目录url **/
    public static final String URL_NETPAL_URL= HOST + "opensource/sharepoint/netpal_list.json";

    /**
     * 目录json永久请求地址<br/>
     * 如果这个url不可用，弹窗联系qq或电话啥的
     **/
    public static final String URL_DEFAULT_MENU_CONSTANT = HOST + "opensource/sharepoint/menu_list.json";

    /**
     * 关于地址<br/>
     **/
    public static final String URL_ABOUT = HOST + "opensource/sharepoint/about.html";

    /** 条件自定义目录url的地址 **/
    public static String URL_POST_NETPAL_MENUURL = HOST + "opensource/sharepoint/upload_netpal_menuurl.php";

}
