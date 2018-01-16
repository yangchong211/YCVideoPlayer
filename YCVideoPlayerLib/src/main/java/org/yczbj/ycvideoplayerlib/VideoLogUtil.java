package org.yczbj.ycvideoplayerlib;

import android.util.Log;

/**
 * log工具
 */
public class VideoLogUtil {

    private static final String TAG = "YCVideoPlayer";
    public static boolean isLog = true;

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
