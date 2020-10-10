package org.yczbj.ycvideoplayerlib.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import org.yczbj.ycvideoplayerlib.R;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/11/9
 *     desc  : 引导图
 *     revise:
 * </pre>
 */
public class CustomGuideView extends LinearLayout{

    public CustomGuideView(Context context) {
        super(context);
        init();
    }

    public CustomGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //设置页面布局
        setBackgroundColor(Color.parseColor("#88000000"));
        //设置居中显示
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.custom_view_guide, this, true);
        //默认是隐藏的
        hide();
    }

    /**
     * 设置当前屏幕的模式
     * @param mode                              全屏，小屏
     */
    public void setScreenMode(int mode) {
        if (mode == 1) {
            //小屏时隐藏
            hide();
            return;
        }
        //只有第一次进入全屏的时候显示。通过SharedPreferences记录这个值。
        SharedPreferences spf = getContext().getSharedPreferences("alivc_guide_record", Context.MODE_PRIVATE);
        boolean hasShown = spf.getBoolean("has_shown", false);
        //如果已经显示过了，就不接着走了
        if (hasShown) {
            return;
        } else {
            setVisibility(VISIBLE);
        }
        //记录下来
        SharedPreferences.Editor editor = spf.edit();
        editor.putBoolean("has_shown", true);
        editor.apply();
    }

    /**
     * 隐藏不显示
     */
    public void hide() {
        setVisibility(GONE);
    }

    /**
     * 手势点击到了就隐藏
     * @param event                             触摸事件
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hide();
        return true;
    }

}
