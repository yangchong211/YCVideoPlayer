package org.yczbj.ycvideoplayerlib;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量
 */
public class ConstantKeys {


    @Retention(RetentionPolicy.SOURCE)
    public @interface IjkPlayerType {
        int TYPE_IJK = 111;
        int TYPE_NATIVE = 222;
    }

    /**
     * 通过注解限定类型
     * TYPE_IJK                 IjkPlayer，基于IjkPlayer封装播放器
     * TYPE_NATIVE              MediaPlayer，基于原生自带的播放器控件
     */
    @IntDef({IjkPlayerType.TYPE_IJK,IjkPlayerType.TYPE_NATIVE})
    @interface PlayerType{}

    static final String NO_LOGIN_VIP_TEXT = "试看结束，观看全部内容请开通会员。\n已是会员/已购买可登陆观看";
    static final String NO_LOGIN_TEXT = "试看结束，观看全部内容请开通会员/购买。\n已是会员/已购买可登陆观看";
    static final String LOGIN_TEXT = "试看结束，观看全部内容请开通会员。";
    static final String NO_LOGIN = "试看结束, 登录后即可观看全部免费课程。";

    /**
     * LOGIN        需要登录情况
     * MEMBER       普通情况，也是默认
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        int LOGIN = 1101;
        int MEMBER = 1102;
    }

    /**
     * 控制器上的视频顶部View点击事件
     *                  在竖屏模式下
     *                  1.DOWNLOAD，下载
     *                  2.AUDIO，切换音频
     *                  3.SHARE，分享
     *                  4.MENU，菜单
     *
     *                  在横屏模式下
     *                  5.TV，点击投影到电视上
     *                  6.HOR_AUDIO，音频
     */
    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoControl {
        int DOWNLOAD = 2005;
        int AUDIO = 2006;
        int SHARE = 2007;
        int MENU = 2008;
        int TV = 2009;
        int HOR_AUDIO = 2010;
    }

    /**
     * 播放模式
     * -1               播放错误
     * 0                播放未开始
     * 1                播放准备中
     * 2                播放准备就绪
     * 3                正在播放
     * 4                暂停播放
     * 5                正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
     * 6                正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，此时暂停播放器，继续缓冲，缓冲区数据足够后恢复暂停
     * 7                播放完成
     */
    protected @interface CurrentState{
        int STATE_ERROR = -1;
        int STATE_IDLE = 0;
        int STATE_PREPARING = 1;
        int STATE_PREPARED = 2;
        int STATE_PLAYING = 3;
        int STATE_PAUSED = 4;
        int STATE_BUFFERING_PLAYING = 5;
        int STATE_BUFFERING_PAUSED = 6;
        int STATE_COMPLETED = 7;
    }

    /**
     * 播放模式，普通模式，小窗口模式，正常模式三种其中一种
     */
    @Retention(RetentionPolicy.SOURCE)
    protected @interface PlayMode {
        int MODE_NORMAL = 1001;
        int MODE_FULL_SCREEN = 1002;
        int MODE_TINY_WINDOW = 1003;
    }


    @Retention(RetentionPolicy.SOURCE)
    public @interface Loading {
        int LOADING_RING = 1;
        int LOADING_QQ = 2;
    }


    /**
     * 通过注解限定类型
     * 加载loading的类型
     * 1，是仿腾讯加载loading，其实是帧动画
     * 2，是转圈加载loading，是补间动画
     */
    @IntDef({Loading.LOADING_RING,Loading.LOADING_QQ})
    @interface LoadingType{}


    @Retention(RetentionPolicy.SOURCE)
    public  @interface TrySee {
        int LOADING_RING = 1;
        int LOADING_QQ = 2;
    }

    /**
     * 视频试看结束后的类型，主要用于是看结束后展示不同的布局
     * 如果不设置该方法，那么默认视频都是可以看的，默认是0
     * 1，试看结束，观看全部内容请开通会员/购买。已是会员/已购买可登陆观看
     * 2，试看结束，观看全部内容请开通会员。已是会员/已购买可登陆观看
     * 3，试看结束, 登录后即可观看全部免费课程。
     */
    @IntDef({Loading.LOADING_RING,Loading.LOADING_QQ})
    @interface TrySeeType{}

}
