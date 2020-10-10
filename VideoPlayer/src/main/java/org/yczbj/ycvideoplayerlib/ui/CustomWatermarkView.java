package org.yczbj.ycvideoplayerlib.ui;

import android.content.Context;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 引导图
 *     revise:
 * </pre>
 */
public class CustomWatermarkView extends LinearLayout{

    //三个文字显示
    private TextView mBrightText, mProgressText, mVolumeText;

    public CustomWatermarkView(Context context) {
        super(context);
        init();
    }

    public CustomWatermarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWatermarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


}
