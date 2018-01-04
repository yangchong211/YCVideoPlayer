package cn.jzvd.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;

/**
 * 全屏展示后，展示标题
 */
public class JZVideoPlayerStandardShowTitleAfterFullscreen extends JZVideoPlayerStandard {

    public JZVideoPlayerStandardShowTitleAfterFullscreen(Context context) {
        super(context);
    }

    public JZVideoPlayerStandardShowTitleAfterFullscreen(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setUp(String url, int screen, Object... objects) {
        super.setUp(url, screen, objects);
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            titleTextView.setVisibility(View.VISIBLE);
        } else {
            titleTextView.setVisibility(View.INVISIBLE);
        }
    }
}
