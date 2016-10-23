package com.example.nguyendhoang.assignment_2.Common;

/**
 * Created by Nguyen.D.Hoang on 10/18/2016.
 */

public class Constant {
    public static String BASE_URL="http://api.nytimes.com/svc/search/v2/";
    public static String BASE_URL_IMAGE="http://www.nytimes.com/";

//    type of article
    public static int HAVE_IMAGE = 1;
    public static int NO_IMAGE = 0;

//    Search Key SearchSetting
    public static String SEARCH_REQUEST_PARAMS = "SEARCH_REQUEST_PARAMS";

    public static String PING_GOOGLE = "/system/bin/ping -c 1 8.8.8.8";

    public static String ARTICLE_DETAIL = "ARTICLE_DETAIL";

    public static String SAVE_INSTANCE_ARTICLE = "SAVE_INSTANCE_ARTICLE";
    public static String SAVE_INSTANCE_SEARCH_REQ = "SAVE_INSTANCE_SEARCH_REQ";
}
