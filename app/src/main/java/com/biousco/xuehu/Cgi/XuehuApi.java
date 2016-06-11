package com.biousco.xuehu.Cgi;

/**
 * Created by Biousco on 6/9.
 */
public class XuehuApi {
    private static String apiDomain = "http://115.28.188.150";
    public static String LOGIN_URL = apiDomain + "/GDUFS/auth/Login/checklogin";
    public static String GETARTICLE_URL = apiDomain + "/GDUFS/XHBBS/Essay/getArticle";
    public static String GETARTICLE_DETAIL_URL = apiDomain + "/GDUFS/XHBBS/Essay/getDetails";
    public static String POST_COMMENT_URL = apiDomain + "/GDUFS/XHBBS/Essay/comment";
    public static String POST_ARTICLE_URL = apiDomain + "/GDUFS/XHBBS/Essay/publish";
    public static String POST_REGIST_URL = apiDomain + "/GDUFS/auth/Register/doRegister";

    public static String POST_UPLOADFACE_URL=apiDomain+"/GDUFS/auth/Modify/upload";
    public static  String ImgDomain="http://115.28.188.150/GDUFS/Uploads/userface/";
    public static String POST_MODIFY_URL= apiDomain+"/GDUFS/auth/Modify/modifyInfo";

}
