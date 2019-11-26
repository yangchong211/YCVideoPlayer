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
package org.yczbj.ycvideoplayerlib.inter.player;

import java.util.Map;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : VideoPlayer抽象接口
 *     revise: 负责播放模式切换和销毁逻辑的接口
 * </pre>
 */
public interface InterScreenVideoPlayer {

    /**------------------以下3个方法是播放器的模式----------------------------*/
    /**
     * 是否是全屏播放
     */
    boolean isFullScreen();
    /**
     * 是否是小窗口播放
     */
    boolean isTinyWindow();
    /**
     * 是否是正常播放
     */
    boolean isNormal();

    /**------------------以下方法是切换播放器的模式----------------------------*/

    /**
     * 进入全屏模式
     * 这个是横屏模式
     */
    void enterVerticalScreenScreen();

    /**
     * 进入全屏模式
     */
    void enterFullScreen();

    /**
     * 退出全屏模式
     *
     * @return true 退出
     */
    boolean exitFullScreen();

    /**
     * 进入小窗口模式
     */
    void enterTinyWindow();

    /**
     * 退出小窗口模式
     *
     * @return true 退出小窗口
     */
    boolean exitTinyWindow();

    /**------------------以下方法是销毁视频播放器资源----------------------------*/

    /**
     * 此处只释放播放器（如果要释放播放器并恢复控制器状态需要调用{@link #release()}方法）
     * 不管是全屏、小窗口还是Normal状态下控制器的UI都不恢复初始状态
     * 这样以便在当前播放器状态下可以方便的切换不同的清晰度的视频地址
     */
    void releasePlayer();

    /**
     * 释放INiceVideoPlayer，释放后，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
     * 并且控制器的UI也应该恢复到最初始的状态.
     */
    void release();
}
