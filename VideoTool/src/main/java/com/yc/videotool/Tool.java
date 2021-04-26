package com.yc.videotool;

import android.util.Log;

public final class Tool {

    /**
     * 工具基础库介绍
     */

    private void test(){
        boolean fastDoubleClick = ClickUtils.isFastDoubleClick();
        //boolean fastDoubleClick = ClickUtils.isFastDoubleClick(800);
        if (fastDoubleClick){
            return;
        }
        //todo
        Log.i("dianji","");
    }

}
