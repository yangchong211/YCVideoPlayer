package com.yc.videotool;

import android.os.Looper;

public final class VideoToolUtils {

    /**
     * 判断是否是主线程
     * @return                      true，表示主线程
     */
    private static boolean isMainThread(){
        //判断是否是主线程
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /**
     * 检查是否在主线程，如果不是则抛出异常
     */
    public static void checkMainThread(){
        if (!isMainThread()){
            throw new IllegalStateException("请不要在子线程中做弹窗操作");
        }
    }

}
