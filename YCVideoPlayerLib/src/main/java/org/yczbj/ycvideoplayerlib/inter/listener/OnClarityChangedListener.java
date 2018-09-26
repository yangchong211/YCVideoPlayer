package org.yczbj.ycvideoplayerlib.inter.listener;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/21
 *     desc  : 清晰度监听接口
 *     revise:
 * </pre>
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
