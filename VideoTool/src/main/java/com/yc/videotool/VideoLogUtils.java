/*
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package com.yc.videotool;

import android.util.Log;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : log工具
 *     revise:
 * </pre>
 */
public final class VideoLogUtils {

    private static final String TAG = "YCVideoPlayer";
    private static boolean isLog = false;

    /**
     * 设置是否开启日志
     * @param isLog                 是否开启日志
     */
    public static void setIsLog(boolean isLog) {
        VideoLogUtils.isLog = isLog;
    }

    public static boolean isIsLog() {
        return isLog;
    }

    /**
     * Log.v 的输出颜色为黑色的，输出大于或等于VERBOSE日志级别的信息，也就是可见级别，一般是最低的信息提示
     * @param message                       message
     */
    public static void v(String message) {
        if(isLog){
            Log.v(TAG, message);
        }
    }

    /**
     * Log.d的输出颜色是蓝色的，也就是调式级别，一般不会中止程序，一般是程序员为了调试而打印的log
     * @param message                       message
     */
    public static void d(String message) {
        if(isLog){
            Log.d(TAG, message);
        }
    }

    /**
     * Log.d的输出颜色是蓝色的，也就是调式级别，一般不会中止程序，一般是程序员为了调试而打印的log
     * @param message                       message
     */
    public static void d(Object message){
        if(isLog){
            //这个方法 建议 Debug 进入不执行，因为 object 会进行字符串+拼接，产生大量内存对象。
            //Log.d(TAG, object.toString());
            Log.d(TAG, " log : " + message);
        }
    }

    /**
     * Log.i的输出为绿色，输出大于或等于INFO日志级别的信息，也就是信息界级别，不会中止程序，一般是系统中执行操作的信息提示
     * @param message                       message
     */
    public static void i(String message) {
        if(isLog){
            Log.i(TAG, message);
        }

    }

    /**
     * Log.e的输出为红色，仅输出ERROR日志级别的信息，也就是错误级别，一般会中止程序运行，是最严重的Log级别。
     * @param message                       message
     */
    public static void e(String message) {
        if (isLog) {
            Log.e(TAG, message);
        }
    }

    public static void e(String message, Throwable throwable) {
        if(isLog){
            Log.e(TAG, message, throwable);
        }
    }

}
