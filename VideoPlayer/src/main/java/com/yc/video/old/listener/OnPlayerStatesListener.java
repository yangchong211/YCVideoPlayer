package com.yc.video.old.listener;

import com.yc.video.config.ConstantKeys;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/3/9
 *     desc  : 视频播放状态抽象接口
 *     revise:
 * </pre>
 */
@Deprecated
public interface OnPlayerStatesListener {

    /**
     * 视频播放状态监听，暴露给外部开发者调用
     * int COMPLETED = 101; 播放完成
     * int PLAYING = 102; 正在播放
     * int PAUSE = 103; 暂停状态
     * int BACK_CLICK = 104; 用户点击back。当视频退出全屏或者退出小窗口后，再次点击返回键，让用户自己处理返回键事件的逻辑
     * @param states                            状态
     */
    void onPlayerStates(@ConstantKeys.PlayerStatesType int states);

}
