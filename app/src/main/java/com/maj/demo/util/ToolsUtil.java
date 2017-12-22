package com.maj.demo.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.Toast;

import java.io.File;
import java.util.Locale;

/**
 * Created by maJian on 2017/12/4 0004 11:34.
 */

public class ToolsUtil {
    /**
     * 获得版本号
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionName;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 或得Mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        try {
            WifiManager wifi = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress();
        } catch (Exception ex) {
            return "00-00-00-00-00-00";
        }
    }

    /**
     * 获得版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pinfo.versionCode;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    /**
     * 获得语言信息
     *
     * @return
     */
    public static String getLanguage() {
        return Locale.getDefault().getLanguage() + "-"
                + Locale.getDefault().getCountry();
    }

    /**
     * 获得imei
     *
     * @param context
     * @return
     */
    public static String getImei(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = tm.getDeviceId();
        return imei;
    }

    /**
     * 获得imsi
     *
     * @param context
     * @return
     */
    public static String getImsi(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = tm.getSubscriberId();
        return imsi == null ? "" : imsi;
    }

    /**
     * 获得手机类型
     *
     * @return
     */
    public static String getPhoneType() {
        return Build.MODEL;
    }

    /**
     * 获得OS类型
     *
     * @return
     */
    public static String getOsType() {
        return "android_" + getSdkInfo();
    }

    /**
     * 获得sdk level
     *
     * @return
     */
    public static String getSdkLevel() {
        return Build.VERSION.SDK;
    }

    /**
     * 获得sdk信息
     *
     * @return
     */
    public static String getSdkInfo() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获得屏幕尺寸
     *
     * @param context
     * @return
     */
    public static String getScreen(Context context) {
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        return display.getWidth() + "*" + display.getHeight();
    }

    /**
     * 获得屏幕宽度
     *
     * @author maJian
     * @time 2015-3-30 上午10:42:11
     * @param context
     * @return
     */
    public static int getWindowWidth(Context context) {
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        return display.getWidth();
    }

    /**
     * 获得屏幕高度
     *
     * @author maJian
     * @time 2015-3-30 上午10:42:11
     * @param context
     * @return
     */
    public static int getWindowHeight(Context context) {
        Display display = ((Activity) context).getWindowManager()
                .getDefaultDisplay();
        return display.getHeight();
    }

    /**
     * 安装apk
     * @param filepath
     */
    public static void installApk(Context context , String filepath) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filepath)),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//			BaseApplication.getInstance().exit();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
    /**
     * 安装apk
     * @param uri
     */
    public static void installApk(Context context , Uri uri) {

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
//			BaseApplication.getInstance().exit();
        } catch (Exception e) {
            e.printStackTrace();
//			ToastUtils.showMessageL("尝试安装文件时出错！ 请使用其它方式安装："+uri);
        }

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void changeLanguage(Context context, String language){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Locale locale = new Locale(language);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale =locale;
        }
        LogUtils.e(null, "change language: "+language);
        resources.updateConfiguration(configuration,displayMetrics);
    }
    /*
    * 显示toast，自己定义显示长短。
    * param1:activity  传入context
	* param2:word   我们需要显示的toast的内容
	* param3:time length  long类型，我们传入的时间长度（如500）
     */
    public static void showToast(final Activity activity, final String word, final long time){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                final Toast toast = Toast.makeText(activity, word, Toast.LENGTH_LONG);
                toast.show();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        toast.cancel();
                    }
                }, time);
            }
        });
    }

    /**
     * 设置音量并弹出英两设置框
     * Author: maJian
     * Time: 2017/12/7 9:03
     * @param context
     * @param flag  0：只弹出音量设置框 -1：减小音量并弹出音量设置框 1：增加音量并弹出音量设置框
     */
    public static void showVoiceSet(Context context, int flag){
        AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        switch (flag){
            case 0:
                //弹出音量设置框
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,AudioManager.ADJUST_SAME,
                        AudioManager.FX_FOCUS_NAVIGATION_UP);
                break;
            case -1:
                //减小音量并弹出音量设置框
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,AudioManager.ADJUST_LOWER,
                        AudioManager.FX_FOCUS_NAVIGATION_UP);
                break;
            case 1:
                //增加音量并弹出音量设置框
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM,AudioManager.ADJUST_RAISE,
                        AudioManager.FX_FOCUS_NAVIGATION_UP);
                break;
        }
    }

    /**
     * 从资源文件raw中获取视频缩略图
     *
     * Author: maJian
     * Time: 2017/12/7  14:10
     * @param resId raw中资源ID
     * @return
     */
    public static Bitmap getBitmapFromRaw(Context context, int resId){
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/" + resId);
        mmr.setDataSource(context, uri);
        Bitmap bitmap = mmr.getFrameAtTime(0,
                MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
        return bitmap;
    }

    /**
     * 绘制多边形
     * Author: maJian
     * Time: 2017/12/14 0014 8:41
     * @param count             0:圆形，1:线形，2：椭圆 ，3：三角，4：矩形或者正方形 5及以上：正多边形
     * @param shapeWidth        图形的宽
     * @param shapeHeight       图形高
     * @param color              画笔颜色
     * @param shapeRadios       圆半径|椭圆
     * @return                  对应多边形的Bitmap
     */
    public static Bitmap getBitmapFromCanvasForMultiShape(int count, int shapeWidth, int shapeHeight, int color, int shapeRadios){
        Bitmap bitmap;
        Canvas canvas;
        Path path = new Path();
        Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //设置线宽
        paint.setStrokeWidth(1);
        paint.setColor(color);
        /*
         * Android在用画笔的时候有三种Style，分别是
         * Paint.Style.STROKE 只绘制图形轮廓（描边）
         * Paint.Style.FILL 只绘制图形内容
         * Paint.Style.FILL_AND_STROKE 既绘制轮廓也绘制内容
         */
        paint.setStyle(Paint.Style.STROKE);

        bitmap = Bitmap.createBitmap(shapeWidth, shapeHeight, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        canvas = new Canvas(bitmap);

        if(count == 0){
            //圆
            canvas.translate(shapeRadios,shapeRadios);//
            canvas.drawCircle(0,0, shapeRadios, paint);
            return bitmap;
        }
        if(count == 1){
            //线
            canvas.drawLine(0, 0, shapeWidth, 0, paint);
            return bitmap;
        }
        if(count == 2){
            //椭圆
            RectF rect = new RectF(0, 0, shapeWidth, shapeHeight);
            canvas.drawOval(rect, paint);
            return bitmap;
        }
        if(count == 4 && shapeWidth != shapeHeight){
            //矩形
            Rect rect = new Rect(0, 0, shapeWidth, shapeHeight);
            canvas.drawRect(rect, paint);
            return bitmap;
        }
        int radius = shapeWidth / 2;
        canvas.translate(radius,radius);//
        for (int i=0;i<count;i++){
            if (i==0){
                path.moveTo(radius*cos(360/count*i),radius*sin(360/count*i));//绘制起点
            }else{
                path.lineTo(radius*cos(360/count*i),radius*sin(360/count*i));
            }
        }
        path.close();
        if (count == 3){
            //三角形
            canvas.rotate(30);
        }
        if (count == 4){
            //正方形
            canvas.rotate(45);
        }
        canvas.drawPath(path,paint);
        return bitmap;
    }

    public static float sin(int num){
        return (float) Math.sin(num*Math.PI/180);
    }
    public static float cos(int num){
        return (float) Math.cos(num*Math.PI/180);
    }

}
