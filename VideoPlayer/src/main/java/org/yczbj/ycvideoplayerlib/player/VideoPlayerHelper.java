package org.yczbj.ycvideoplayerlib.player;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import org.yczbj.ycvideoplayerlib.controller.BaseVideoController;
import org.yczbj.ycvideoplayerlib.tool.utils.PlayerUtils;

public class VideoPlayerHelper {

    private static VideoPlayerHelper sInstance;

    /**
     * 构造方法，避免直接new
     */
    private VideoPlayerHelper() {
        //避免初始化
    }


    /**
     * 一定要使用单例模式，保证同一时刻只有一个视频在播放，其他的都是初始状态
     * 单例模式
     * @return          VideoPlayerManager对象
     */
    public static VideoPlayerHelper instance() {
        if (sInstance == null) {
            synchronized (VideoPlayerHelper.class){
                if (sInstance == null){
                    sInstance = new VideoPlayerHelper();
                }
            }
        }
        return sInstance;
    }

    /**
     * 获取DecorView
     * @param context                               上下文
     * @param videoController                       controller
     */
    protected ViewGroup getDecorView(Context context , BaseVideoController videoController) {
        Activity activity = VideoPlayerHelper.instance().getActivity(context,videoController);
        if (activity == null) {
            return null;
        }
        return (ViewGroup) activity.getWindow().getDecorView();
    }

    /**
     * 获取activity中的content view,其id为android.R.id.content
     * @param context                               上下文
     * @param videoController                       controller
     */
    protected ViewGroup getContentView(Context context , BaseVideoController videoController) {
        Activity activity = VideoPlayerHelper.instance().getActivity(context,videoController);
        if (activity == null) {
            return null;
        }
        return activity.findViewById(android.R.id.content);
    }


    /**
     * 获取Activity，优先通过Controller去获取Activity
     * @param context                               上下文
     * @param videoController                       controller
     * @return
     */
    protected Activity getActivity(Context context , BaseVideoController videoController) {
        Activity activity;
        if (videoController != null) {
            activity = PlayerUtils.scanForActivity(videoController.getContext());
            if (activity == null) {
                activity = PlayerUtils.scanForActivity(context);
            }
        } else {
            activity = PlayerUtils.scanForActivity(context);
        }
        return activity;
    }


}
