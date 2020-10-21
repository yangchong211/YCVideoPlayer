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
package com.yc.video.old.listener;


import com.yc.video.config.ConstantKeys;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 视频顶部点击事件
 *     revise:
 * </pre>
 */
@Deprecated
public interface OnVideoControlListener {

    /**
     * 视频顶部点击事件【下载，切换音频，分享等】
     * @param type      类型
     *                  在竖屏模式下
     *                  1.DOWNLOAD，下载
     *                  2.AUDIO，切换音频
     *                  3.SHARE，分享
     *                  4.MENU，菜单
     *
     *                  在横屏模式下
     *                  5.TV，tv映射
     *                  6.HOR_AUDIO，音频
     */
    void onVideoControlClick(@ConstantKeys.VideoControlType int type);
    
}
