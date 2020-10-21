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
package com.yc.video.old.other;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yc.video.config.ConstantKeys;
import com.yc.video.old.controller.AbsVideoPlayerController;
import com.yc.video.old.player.OldVideoPlayer;
import com.yc.video.tool.NetworkUtils;

import com.yc.kernel.utils.VideoLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2019/5/21
 *     desc  : 网络状态监听广播
 *     revise:
 * </pre>
 */
@Deprecated
public class NetChangedReceiver extends BroadcastReceiver {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        VideoLogUtils.i("网络状态监听广播接收到数据了");
        OldVideoPlayer mVideoPlayer = VideoPlayerManager.instance().getCurrentVideoPlayer();
        if (mVideoPlayer!=null){
            AbsVideoPlayerController controller = mVideoPlayer.getController();
            switch (NetworkUtils.getConnectState(context)) {
                case MOBILE:
                    VideoLogUtils.i("当网络状态监听前连接了移动数据");
                    break;
                case WIFI:
                    VideoLogUtils.i("网络状态监听当前连接了Wifi");
                    break;
                case UN_CONNECTED:
                    VideoLogUtils.i("网络状态监听当前没有网络连接");
                    if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                        VideoLogUtils.i("网络状态监听当前没有网络连接---设置暂停播放");
                        mVideoPlayer.pause();
                    }
                    if (controller!=null){
                        controller.onPlayStateChanged(ConstantKeys.CurrentState.STATE_ERROR);
                    }
                    break;
                default:
                    VideoLogUtils.i("网络状态监听其他情况");
                    break;
            }
        }
    }
}
