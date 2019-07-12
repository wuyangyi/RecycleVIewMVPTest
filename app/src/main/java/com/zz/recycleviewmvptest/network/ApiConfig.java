package com.zz.recycleviewmvptest.network;

public class ApiConfig {
    public static final String APP_URL = "http://gank.io/api/"; //干货训练营

    /**
     * 有分页的数据
     */
    public static final String APP_PATH_LIST_PAGE = "search/query/listview/category/Android/count/{limit}/page/{pageNumber}";

    /**
     * 福利
     */
    public static final String APP_PATH_LIST_FL = "data/福利/{limit}/{pageNumber}";
}
