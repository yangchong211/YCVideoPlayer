package org.yczbj.ycvideoplayerlib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import java.util.Formatter;
import java.util.Locale;

/**
 * 工具类.
 */
public class VideoPlayerUtils {

    /**
     * Get activity from context object
     *
     * @param context       上下文
     * @return              对象的活动对象，如果它不是活动对象，则为空。
     */
    static Activity scanForActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }
        return null;
    }

    /**
     * 判断某Activity是否挂掉
     * @param activity      activity
     * @return
     */
    public static boolean isActivityLiving(Activity activity) {
        if (activity == null) {
            return false;
        }
        if (activity.isFinishing()) {
            return false;
        }
        return true;
    }



    /**
     * Get AppCompatActivity from context
     * @param context           上下文
     * @return AppCompatActivity if it's not null
     */
    private static AppCompatActivity getAppCompActivity(Context context) {
        if (context == null) {
            return null;
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        } else if (context instanceof ContextThemeWrapper) {
            return getAppCompActivity(((ContextThemeWrapper) context).getBaseContext());
        }
        return null;
    }

    @SuppressLint("RestrictedApi")
    static void showActionBar(Context context) {
        ActionBar ab = getAppCompActivity(context).getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);
            ab.show();
        }
        scanForActivity(context).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @SuppressLint("RestrictedApi")
    static void hideActionBar(Context context) {
        ActionBar ab = getAppCompActivity(context).getSupportActionBar();
        if (ab != null) {
            ab.setShowHideAnimationEnabled(false);
            ab.hide();
        }
        scanForActivity(context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取屏幕宽度
     */
    static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * dp转px
     */
    static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 将毫秒数格式化为"##:##"的时间
     * @param milliseconds 毫秒数
     * @return ##:##
     */
    static String formatTime(long milliseconds) {
        if (milliseconds <= 0 || milliseconds >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = milliseconds / 1000;
        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    /**
     * 保存播放位置，以便下次播放时接着上次的位置继续播放.
     * @param context           上下文
     * @param url               视频链接url
     */
    static void savePlayPosition(Context context, String url, long position) {
        context.getSharedPreferences("VIDEO_PLAYER_PLAY_POSITION", Context.MODE_PRIVATE).edit().putLong(url, position).apply();
    }

    /**
     * 取出上次保存的播放位置
     * @param context           上下文
     * @param url               视频链接url
     * @return 上次保存的播放位置
     */
    static long getSavedPlayPosition(Context context, String url) {
        return context.getSharedPreferences("VIDEO_PLAYER_PLAY_POSITION", Context.MODE_PRIVATE).getLong(url, 0);
    }

    /**
     * 清楚播放位置的痕迹
     * @param context           上下文
     */
    public static void clearPlayPosition(Context context){
        context.getSharedPreferences("VIDEO_PLAYER_PLAY_POSITION", Context.MODE_PRIVATE).getAll().clear();
    }

}
