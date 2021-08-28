package com.yc.videoview.abs;

import android.view.View;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 抽象view类
 *     revise: 定义抽象方法和普通方法
 * </pre>
 */
public abstract class AbsFloatView {

    public abstract void setSize(int width, int height);

    public abstract void setView(View view);

    public abstract void setGravity(int gravity, int xOffset, int yOffset);

    public abstract void init();

    public abstract void dismiss();

    public void updateXY(int x, int y) {}

    public void updateX(int x) {}

    public void updateY(int y) {}

    public int getX() {
        return 0;
    }

    public int getY() {
        return 0;
    }
}
