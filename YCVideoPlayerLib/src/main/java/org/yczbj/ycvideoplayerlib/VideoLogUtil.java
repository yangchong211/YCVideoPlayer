package org.yczbj.ycvideoplayerlib;

import android.util.Log;

/**
 * log工具
 */
public class VideoLogUtil {

    private static final String TAG = "NiceVideoPlayer";

    static void d(String message) {
        Log.d(TAG, message);
    }

    static void i(String message) {
        Log.i(TAG, message);
    }

    static void e(String message, Throwable throwable) {
        Log.e(TAG, message, throwable);
    }
}
