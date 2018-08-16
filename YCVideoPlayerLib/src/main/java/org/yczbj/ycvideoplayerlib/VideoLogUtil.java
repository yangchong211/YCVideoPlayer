package org.yczbj.ycvideoplayerlib;

import android.util.Log;

/**
 * log工具
 */
public class VideoLogUtil {

    private static final String TAG = "YCVideoPlayer";
    private static boolean isLog = true;

    /**
     * 设置是否开启日志
     * @param isLog                 是否开启日志
     */
    public static void setIsLog(boolean isLog) {
        VideoLogUtil.isLog = isLog;
    }

    static void d(String message) {
        if(isLog){
            Log.d(TAG, message);
        }
    }

    static void i(String message) {
        if(isLog){
            Log.i(TAG, message);
        }

    }

    static void e(String message, Throwable throwable) {
        if(isLog){
            Log.e(TAG, message, throwable);
        }
    }

}
