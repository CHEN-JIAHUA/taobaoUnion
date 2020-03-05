package com.chenjiahua.taobaounion.utils;

public class UrlUtils {
    public static String createHomePageUrl(int materialId,int page){
        return "discovery/"+materialId + "/ "+ page;
    }

    public static String getSetUrlPath(String pict_url) {
        return "https:" + pict_url;
    }

    public static String getPicSizeUrl(String pict_url, int coverSize) {
        return pict_url + "_" + coverSize + "x" +coverSize + ".jpg";
    }
}
