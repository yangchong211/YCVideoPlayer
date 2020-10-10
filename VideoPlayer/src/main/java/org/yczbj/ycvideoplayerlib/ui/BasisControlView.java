package org.yczbj.ycvideoplayerlib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 控制条界面。包括了顶部的标题栏，底部 的控制栏，锁屏按钮等等。
 *             是界面的主要组成部分。
 *     revise:
 * </pre>
 */
public class BasisControlView extends RelativeLayout {

    private Context context;

    public BasisControlView(Context context) {
        super(context);
        init(context);
    }

    public BasisControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BasisControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
    }

}
