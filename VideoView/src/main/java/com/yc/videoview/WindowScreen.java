package com.yc.videoview;


import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class WindowScreen {
    public static final int WIDTH = 0;
    public static final int HEIGHT = 1;

    @IntDef({WIDTH, HEIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface screenType {}
}
