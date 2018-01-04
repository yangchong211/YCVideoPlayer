package cn.jzvd.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 这里可以监听到视频播放的生命周期和播放状态
 * 所有关于视频的逻辑都应该写在这里
 * yc补充：也可以自己定义该控件，继承JZVideoPlayerStandard即可
 */
public class MyJZVideoPlayerStandard extends JZVideoPlayerStandard {

    public MyJZVideoPlayerStandard(Context context) {
        super(context);
    }

    public MyJZVideoPlayerStandard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 这是播放控件初始化的时候最先调用的
     */
    @Override
    public void init(Context context) {
        super.init(context);
    }

    /**
     * 这是控件里所有控件的onClick响应函数，比如监听开始按钮点击，全屏按钮点击，空白点击，retry按钮点击等。
     * 如果你想拦截这些点击的响应或者继承这些点击的响应，那么复写此函数
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    /**
     * 在JCVideoPlayer中此函数主要响应了全屏之后的手势控制音量和进度
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return super.onTouch(v, event);
    }

    /**
     * 用户触发的视频开始播放
     */
    @Override
    public void startVideo() {
        super.startVideo();
    }

    /**
     * 控件进入普通的未播放状态
     */
    @Override
    public void onStateNormal() {
        super.onStateNormal();
    }

    /**
     * 进入preparing状态，正在初始化视频
     */
    @Override
    public void onStatePreparing() {
        super.onStatePreparing();
    }

    /**
     * preparing之后进入播放状态
     */
    @Override
    public void onStatePlaying() {
        super.onStatePlaying();
    }

    /**
     * 暂停视频，进入暂停状态
     */
    @Override
    public void onStatePause() {
        super.onStatePause();
    }

    /**
     * 进入错误状态
     */
    @Override
    public void onStateError() {
        super.onStateError();
    }

    /**
     * 进入视频自动播放完成状态
     */
    @Override
    public void onStateAutoComplete() {
        super.onStateAutoComplete();
    }

    /**
     * android.media.MediaPlayer回调的info
     */
    @Override
    public void onInfo(int what, int extra) {
        super.onInfo(what, extra);
    }

    /**
     * android.media.MediaPlayer回调的error
     */
    @Override
    public void onError(int what, int extra) {
        super.onError(what, extra);
    }

    /**
     * 进入全屏
     */
    @Override
    public void startWindowFullscreen() {
        super.startWindowFullscreen();
    }

    /**
     * 退出全屏
     */
    @Override
    public void startWindowTiny() {
        super.startWindowTiny();
    }

}
