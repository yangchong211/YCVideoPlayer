package org.yczbj.ycvideoplayerlib.tool.toast;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 20120/5/6
 *     desc  : 利用hook解决toast崩溃问题
 *     revise:
 * </pre>
 */
public class HookToast {

    private static Field sField_TN;
    private static Field sField_TN_Handler;

    static {
        try {
            Class<?> clazz =  Toast.class;
            sField_TN = clazz.getDeclaredField("mTN");
            sField_TN.setAccessible(true);
            sField_TN_Handler = sField_TN.getType().getDeclaredField("mHandler");
            sField_TN_Handler.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hook(Toast toast) {
        try {
            Object tn = sField_TN.get(toast);
            Handler preHandler = (Handler) sField_TN_Handler.get(tn);
            sField_TN_Handler.set(tn, new SafelyHandler(preHandler));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void showToast(Context context, CharSequence cs, int length) {
        Toast mToast = Toast.makeText(context, cs, length);
        hook(mToast);
        mToast.show();
    }*/

    public static class SafelyHandler extends Handler {

        private Handler impl;

        public SafelyHandler(Handler impl) {
            this.impl = impl;
        }

        public void dispatchMessage(Message msg) {
            try {
                super.dispatchMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handleMessage(Message msg) {
            //需要委托给原Handler执行
            impl.handleMessage(msg);
        }
    }

}
