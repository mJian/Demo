package com.maj.demo.util;

import android.util.Log;

/**
 * Created by maJian on 2017/12/4 0004 8:41.
 */

public class LogUtils {
    //是否显示debug信息
    public static boolean isShow = true;
    public static String defaultTag = "MaJian";

    /**
     *  Author: maJian
     *  Time: 2017/12/4  8:48
     * @param tag        标识
     * @param str        要显示的信息
     */
    public static void d(String tag, String str){
        if (isShow){
            if (tag==null || tag.equals("")){
                tag = defaultTag;
            }
            Log.d(tag,str);
        }
    }

    /**
     *  Author: maJian
     *  Time: 2017/12/4  8:48
     * @param tag        标识
     * @param str        要显示的信息
     */
    public static void e(String tag, String str){
        if (isShow){
            if (tag==null || tag.equals("")){
                tag = defaultTag;
            }
            Log.e(tag,str);
        }
    }

    /**
     *  Author: maJian
     *  Time: 2017/12/4  8:48
     * @param tag        标识
     * @param str        要显示的信息
     */
    public static void i(String tag, String str){
        if (isShow){
            if (tag==null || tag.equals("")){
                tag = defaultTag;
            }
            Log.i(tag,str);
        }
    }

    public static boolean isShow() {
        return isShow;
    }

    /**
     * 设置是否显示debug信息
     * @param isShow
     */
    public static void setIsShow(boolean isShow) {
        LogUtils.isShow = isShow;
    }

    public static String getDefaultTag() {
        return defaultTag;
    }

    /**
     * 设置debug的默认标识
     * @param defaultTag
     */
    public static void setDefaultTag(String defaultTag) {
        LogUtils.defaultTag = defaultTag;
    }
}
