package cn.jzvd.CustomView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import cn.jzvd.JZVideoPlayerStandard;


public class JZVideoPlayerStandardShowTextureViewAfterAutoComplete extends JZVideoPlayerStandard {

    public JZVideoPlayerStandardShowTextureViewAfterAutoComplete(Context context) {
        super(context);
    }

    public JZVideoPlayerStandardShowTextureViewAfterAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        thumbImageView.setVisibility(View.GONE);
    }

}
