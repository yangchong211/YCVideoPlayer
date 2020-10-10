package org.yczbj.ycvideoplayerlib.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.yczbj.ycvideoplayerlib.R;

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
public class BasisBottomView extends RelativeLayout {

    private LinearLayout mBottom;
    private ImageView mRestartOrPause;
    private TextView mPosition;
    private TextView mDuration;
    private SeekBar mSeek;
    private TextView mClarity;
    private ImageView mFullScreen;

    public BasisBottomView(Context context) {
        super(context);
        init(context);
    }

    public BasisBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BasisBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_video_player_bottom, null);
        initFindViewById(view);
    }

    private void initFindViewById(View view) {
        mBottom = view.findViewById(R.id.bottom);
        mRestartOrPause = view.findViewById(R.id.restart_or_pause);
        mPosition = view.findViewById(R.id.position);
        mDuration = view.findViewById(R.id.duration);
        mSeek = view.findViewById(R.id.seek);
        mClarity = view.findViewById(R.id.clarity);
        mFullScreen = view.findViewById(R.id.full_screen);
    }

}
