package com.zz.recycleviewmvptest.network;

public class ApiConfig {
    public static final String APP_URL = "http://gank.io/api/"; //干货训练营
    public static final String APP_TL_URL = "http://openapi.tuling123.com/"; //图灵机器人

    public static final String APP_GITHUB_URL = "https://raw.githubusercontent.com/"; //github

    /**
     * 有分页的数据
     */
    public static final String APP_PATH_LIST_PAGE = "search/query/listview/category/Android/count/{limit}/page/{pageNumber}";

    /**
     * 福利
     */
    public static final String APP_PATH_LIST_FL = "data/福利/{limit}/{pageNumber}";

    /**
     * 图灵智能机器人
     */
    public static final String APP_TL_CHAT = "openapi/api/v2";

    /**
     * 每日推荐
     */
    public static final String APP_PATH_RECOMMEND = "day/2015/08/07";

    /**
     * app版本下载
     */
    public static final String APP_DOWNLOAD = "wuyangyi/RecycleVIewMVPTest/master/app-release.apk";

    /**
     * app版本信息
     */
    public static final String APP_INFO = "wuyangyi/RecycleVIewMVPTest/master/output.json";
}
