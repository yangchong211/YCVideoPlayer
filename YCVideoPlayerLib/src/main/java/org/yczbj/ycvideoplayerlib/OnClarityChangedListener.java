package org.yczbj.ycvideoplayerlib;

/**
 * 清晰度监听接口
 */

public interface OnClarityChangedListener {

    /**
     * 切换清晰度后回调
     *
     * @param clarityIndex 切换到的清晰度的索引值
     */
    void onClarityChanged(int clarityIndex);

    /**
     * 清晰度没有切换，比如点击了空白位置，或者点击的是之前的清晰度
     */
    void onClarityNotChanged();
    
}
