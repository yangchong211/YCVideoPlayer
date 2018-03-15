package org.yczbj.ycvideoplayer.api.constant;

import org.yczbj.ycvideoplayer.R;

/**
 * Description:
 * Update:
 * CreatedTime:2018/01/03
 * Author:yc
 */

public class Constant {


    public interface viewType{
        int typeView = 0;           //自定义
        int typeBanner = 1;         //轮播图
        int typeGv = 2;             //九宫格
        int typeTitle = 3;          //标题
        int typeMore = 4;           //更多
        int typeAd = 5;             //广告
        int typeList2 = 6;          //list2
        int typeAd2 = 7 ;           //广告2
        int typeGv3 = 8;            //list3
        int typeList4 = 9;          //list4
        int typeGvBottom = 10;      //九宫格
        int typeList5 = 11;          //list4
    }

    public static final int SLIDABLE_DISABLE = 0;
    public static final int SLIDABLE_EDGE = 1;
    public static final int SLIDABLE_FULL = 2;

    public static final long CLICK_TIME = 500;

    public static final int[] ICONS_DRAWABLES = new int[]{
            R.mipmap.ic_launcher_circle,
            R.mipmap.ic_launcher_rect,
            R.mipmap.ic_launcher_square};

    public static final String[] ICONS_TYPE = new String[]{"circle", "rect", "square"};
    public static final String SP_NAME = "yc";
    public static final String KEY_IS_LOGIN = "is_login";
    public static final String KEY_NIGHT_STATE = "night_state";
    public static final String LOCK_SCREEN_ACTION = "cn.ycbjie.lock";



}
