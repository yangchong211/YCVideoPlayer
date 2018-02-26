package org.yczbj.ycvideoplayer.util;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;

import org.yczbj.ycvideoplayer.R;
import org.yczbj.ycvideoplayer.api.constant.Constant;


public class SettingUtil {

    public static SettingUtil getInstance() {
        return SettingsUtilInstance.instance;
    }

    /**
     * 获取是否开启无图模式
     */
    public boolean getIsNoPhotoMode() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("switch_noPhotoMode", false) && NetworkUtils.isConnected();
    }

    /**
     * 获取主题颜色
     */
    public int getColor() {
        int defaultColor = Utils.getContext().getResources().getColor(R.color.colorPrimary);
        int color = SPUtils.getInstance(Constant.SP_NAME).getInt("color", defaultColor);
        if ((color != 0) && Color.alpha(color) != 255) {
            return defaultColor;
        }
        return color;
    }

    /**
     * 设置主题颜色
     */
    public void setColor(int color) {
        SPUtils.getInstance(Constant.SP_NAME).put("color", color);
    }

    /**
     * 获取是否开启夜间模式
     */
    public boolean getIsNightMode() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("switch_nightMode", false);
    }

    /**
     * 设置夜间模式
     */
    public void setIsNightMode(boolean flag) {
        SPUtils.getInstance(Constant.SP_NAME).put("switch_nightMode", flag);
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    public boolean getIsAutoNightMode() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("auto_nightMode", false);
    }

    /**
     * 获取是否开启自动切换夜间模式
     */
    public void setIsAutoNightMode(boolean flag) {
        SPUtils.getInstance(Constant.SP_NAME).put("auto_nightMode", flag);
    }

    public String getNightStartHour() {
        return SPUtils.getInstance(Constant.SP_NAME).getString("night_startHour", "22");
    }

    public void setNightStartHour(String nightStartHour) {
        SPUtils.getInstance(Constant.SP_NAME).put("night_startHour", nightStartHour);
    }

    public String getNightStartMinute() {
        return SPUtils.getInstance(Constant.SP_NAME).getString("night_startMinute", "00");
    }

    public void setNightStartMinute(String nightStartMinute) {
        SPUtils.getInstance(Constant.SP_NAME).put("night_startMinute", nightStartMinute);
    }

    public String getDayStartHour() {
        return SPUtils.getInstance(Constant.SP_NAME).getString("day_startHour", "06");
    }

    public void setDayStartHour(String dayStartHour) {
        SPUtils.getInstance(Constant.SP_NAME).put("day_startHour", dayStartHour);
    }

    public String getDayStartMinute() {
        return SPUtils.getInstance(Constant.SP_NAME).getString("day_startMinute", "00");
    }

    public void setDayStartMinute(String dayStartMinute) {
        SPUtils.getInstance(Constant.SP_NAME).put("day_startMinute", dayStartMinute);
    }

    /**
     * 获取是否开启导航栏上色
     */
    public boolean getNavBar() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("nav_bar", false);
    }

    /**
     * 获取是否开启视频强制横屏
     */
    public boolean getIsVideoForceLandscape() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("video_force_landscape", false);
    }

    /**
     * 获取图标值
     */
    public int getCustomIconValue() {
        String s = SPUtils.getInstance(Constant.SP_NAME).getString("custom_icon", "0");
        return Integer.parseInt(s);
    }

    /**
     * 获取滑动返回值
     */
    public int getSlidable() {
        String s = SPUtils.getInstance(Constant.SP_NAME).getString("slidable", "1");
        return Integer.parseInt(s);
    }

    /**
     * 获取是否开启视频自动播放
     */
    public boolean getIsVideoAutoPlay() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("video_auto_play", false) && NetworkUtils.isWifiConnected();
    }

    /**
     * 获取字体大小
     */
    public int getTextSize() {
        return SPUtils.getInstance(Constant.SP_NAME).getInt("textsize", 16);
    }

    /**
     * 设置字体大小
     */
    public void setTextSize(int textSize) {
        SPUtils.getInstance(Constant.SP_NAME).put("textsize", textSize);
    }

    public boolean getIsFirstTime() {
        return SPUtils.getInstance(Constant.SP_NAME).getBoolean("first_time", true);
    }

    public void setIsFirstTime(boolean flag) {
        SPUtils.getInstance(Constant.SP_NAME).put("first_time", flag);
    }

    private static final class SettingsUtilInstance {
        private static final SettingUtil instance = new SettingUtil();
    }
}
