package com.yc.video.controller;

import com.yc.video.ui.view.InterControlView;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 视频控制器负责自定义视图add/remove操作
 *     revise:
 * </pre>
 */
public interface InterViewController {

    /**
     * 添加控制组件，最后面添加的在最下面，合理组织添加顺序，可让ControlComponent位于不同的层级
     * @param controlViews                         view
     */
    void addControlComponent(InterControlView... controlViews);

    /**
     * 添加控制组件，最后面添加的在最下面，合理组织添加顺序，可让InterControlView位于不同的层级
     * @param controlView                           view
     * @param isPrivate                             是否为独有的组件，如果是就不添加到控制器中
     */
    void addControlComponent(InterControlView controlView, boolean isPrivate);

    /**
     * 移除控制组件
     * @param controlView                           view
     */
    void removeControlComponent(InterControlView controlView);

    /**
     * 移除所有的组件
     */
    void removeAllControlComponent();

    /**
     * 移除所有独有的组件
     */
    void removeAllPrivateComponents();
}
