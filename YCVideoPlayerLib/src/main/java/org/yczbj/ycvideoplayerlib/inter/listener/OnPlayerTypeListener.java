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
package org.yczbj.ycvideoplayerlib.inter.listener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/3/9
 *     desc  : 视频播放模式监听
 *     revise:
 * </pre>
 */
public interface OnPlayerTypeListener {

    /**
     * 切换到全屏播放监听
     */
    void onFullScreen();

    /**
     * 切换到小窗口播放监听
     */
    void onTinyWindow();

    /**
     * 切换到正常播放监听
     */
    void onNormal();

}
