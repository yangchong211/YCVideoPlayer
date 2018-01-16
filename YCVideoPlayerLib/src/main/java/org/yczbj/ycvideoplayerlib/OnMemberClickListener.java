package org.yczbj.ycvideoplayerlib;


/**
 * 点击事件抽象接口
 */
public interface OnMemberClickListener {
    /**
     * 点击事件
     * @param type      类型
     *                  1.是跳转登录页面
     *                  2.是跳转开通会员页面
     */
    void onClick(int type);
}
