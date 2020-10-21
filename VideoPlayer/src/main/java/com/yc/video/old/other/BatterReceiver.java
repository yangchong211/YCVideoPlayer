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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;

import com.yc.video.config.ConstantKeys;
import com.yc.video.old.controller.AbsVideoPlayerController;
import com.yc.video.old.player.OldVideoPlayer;

import com.yc.kernel.utils.VideoLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/9/17
 *     desc  : 电量状态监听广播
 *     revise:
 * </pre>
 */
@Deprecated
public class BatterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        VideoLogUtils.i("电量状态监听广播接收到数据了");
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                BatteryManager.BATTERY_STATUS_UNKNOWN);
        OldVideoPlayer mVideoPlayer = VideoPlayerManager.instance().getCurrentVideoPlayer();
        if (mVideoPlayer!=null){
            AbsVideoPlayerController controller = mVideoPlayer.getController();
            if (controller!=null){
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    // 充电中
                    controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_CHARGING);
                } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                    // 充电完成
                    controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_FULL);
                } else {
                    // 当前电量
                    int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                    // 总电量
                    int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                    int percentage = (int) (((float) level / scale) * 100);
                    VideoLogUtils.i("广播NetworkReceiver------当前电量"+level);
                    VideoLogUtils.i("广播NetworkReceiver------总电量"+scale);
                    VideoLogUtils.i("广播NetworkReceiver------百分比"+percentage);
                    if (percentage <= 10) {
                        controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_10);
                    } else if (percentage <= 20) {
                        controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_20);
                    } else if (percentage <= 50) {
                        controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_50);
                    } else if (percentage <= 80) {
                        controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_80);
                    } else if (percentage <= 100) {
                        controller.onBatterStateChanged(ConstantKeys.BatterMode.BATTERY_100);
                    }
                }
            }
        }
    }

}
