package org.yczbj.ycvideoplayer.util;


import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;

/**
 * <pre>
 *     @author yangchong
 *     blog  :
 *     time  : 2018/9/14
 *     desc  : 头条适配方案简易版，测试用，注意不要用到正式版
 *     revise:
 * </pre>
 */
public class ScreenDensityUtils {


    /*
     * 1.先在application中使用setup()方法初始化一下
     * 2.手动在Activity中调用match()方法做适配，必须在setContentView()之前
     * 3.建议使用dp做宽度适配，大多数时候宽度适配才是主流需要
     * 4.个人觉得在写布局的时候，可以多用dp，如果是使用px，建议转化成dp
     * 5.入侵性很低，不需要改动原来的代码
     */


    /**
     * 屏幕适配的基准
     */
    public static final int MATCH_BASE_WIDTH = 0;
    public static final int MATCH_BASE_HEIGHT = 1;
    /**
     * 适配单位
     */
    public static final int MATCH_UNIT_DP = 0;
    public static final int MATCH_UNIT_PT = 1;

    // 适配信息
    private static MatchInfo sMatchInfo;
    // Activity 的生命周期监测
    private static Application.ActivityLifecycleCallbacks mActivityLifecycleCallback;

    private ScreenDensityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化
     * @param application                   需要在application中初始化
     */
    public static void setup(@NonNull final Application application) {

        /*
        //获取屏幕分辨率信息的三种方法
        //第一种
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = activity.getWindowManager().getDefaultDisplay();
        display.getMetrics(metrics);
        //第二种
        DisplayMetrics metrics= activity.getResources().getDisplayMetrics();
        //第三种
        Resources.getSystem().getDisplayMetrics();
        */

        //注意这个是获取系统的displayMetrics
        final DisplayMetrics displayMetrics = application.getResources().getDisplayMetrics();
        if (sMatchInfo == null) {
            // 记录系统的原始值
            sMatchInfo = new MatchInfo();
            sMatchInfo.setScreenWidth(displayMetrics.widthPixels);
            sMatchInfo.setScreenHeight(displayMetrics.heightPixels);
            sMatchInfo.setAppDensity(displayMetrics.density);
            sMatchInfo.setAppDensityDpi(displayMetrics.densityDpi);
            sMatchInfo.setAppScaledDensity(displayMetrics.scaledDensity);
            sMatchInfo.setAppXdpi(displayMetrics.xdpi);
        }
        // 添加字体变化的监听
        // 调用 Application#registerComponentCallbacks 注册下 onConfigurationChanged 监听即可。
        application.registerComponentCallbacks(new ComponentCallbacks() {
            @Override
            public void onConfigurationChanged(Configuration newConfig) {
                // 字体改变后,将 appScaledDensity 重新赋值
                if (newConfig != null && newConfig.fontScale > 0) {
                    float scaledDensity = displayMetrics.scaledDensity;
                    sMatchInfo.setAppScaledDensity(scaledDensity);
                }
            }

            @Override
            public void onLowMemory() {

            }
        });
    }

    /**
     * 在 application 中全局激活适配（也可单独使用 match() 方法在指定页面中配置适配）
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void register(@NonNull final Application application, final float designSize, final int matchBase, final int matchUnit) {
        if (mActivityLifecycleCallback == null) {
            mActivityLifecycleCallback = new Application.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    if (activity != null) {
                        match(activity, designSize, matchBase, matchUnit);
                    }
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {

                }
            };
            application.registerActivityLifecycleCallbacks(mActivityLifecycleCallback);
        }
    }

    /**
     * 全局取消所有的适配
     */
    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static void unregister(@NonNull final Application application, @NonNull int... matchUnit) {
        if (mActivityLifecycleCallback != null) {
            application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallback);
            mActivityLifecycleCallback = null;
        }
        for (int unit : matchUnit) {
            cancelMatch(application, unit);
        }
    }


    /**
     * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
     *
     * @param context                               上下文
     * @param designSize                            设计图的尺寸
     */
    public static void match(@NonNull final Context context, final float designSize) {
        match(context, designSize, MATCH_BASE_WIDTH, MATCH_UNIT_DP);
    }


    /**
     * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
     *
     * @param context                               上下文
     * @param designSize                            设计图的尺寸
     * @param matchBase                             适配基准
     */
    public static void match(@NonNull final Context context, final float designSize, int matchBase) {
        match(context, designSize, matchBase, MATCH_UNIT_DP);
    }

    /**
     * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
     *
     * @param context                               上下文
     * @param designSize                            设计图的尺寸
     * @param matchBase                             适配基准
     * @param matchUnit                             使用的适配单位
     */
    private static void match(@NonNull final Context context, final float designSize, int matchBase, int matchUnit) {
        if (designSize == 0) {
            throw new UnsupportedOperationException("The designSize cannot be equal to 0");
        }
        if (matchUnit == MATCH_UNIT_DP) {
            matchByDP(context, designSize, matchBase);
        } else if (matchUnit == MATCH_UNIT_PT) {
            matchByPT(context, designSize, matchBase);
        }
    }

    /**
     * 重置适配信息，取消适配
     */
    public static void cancelMatch(@NonNull final Context context) {
        cancelMatch(context, MATCH_UNIT_DP);
        cancelMatch(context, MATCH_UNIT_PT);
    }

    /**
     * 重置适配信息，取消适配
     *
     * @param context                       上下文
     * @param matchUnit                     需要取消适配的单位
     */
    private static void cancelMatch(@NonNull final Context context, int matchUnit) {
        if (sMatchInfo != null) {
            final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            if (matchUnit == MATCH_UNIT_DP) {
                if (displayMetrics.density != sMatchInfo.getAppDensity()) {
                    displayMetrics.density = sMatchInfo.getAppDensity();
                }
                if (displayMetrics.densityDpi != sMatchInfo.getAppDensityDpi()) {
                    displayMetrics.densityDpi = (int) sMatchInfo.getAppDensityDpi();
                }
                if (displayMetrics.scaledDensity != sMatchInfo.getAppScaledDensity()) {
                    displayMetrics.scaledDensity = sMatchInfo.getAppScaledDensity();
                }
            } else if (matchUnit == MATCH_UNIT_PT) {
                if (displayMetrics.xdpi != sMatchInfo.getAppXdpi()) {
                    displayMetrics.xdpi = sMatchInfo.getAppXdpi();
                }
            }
        }
    }


    public static MatchInfo getMatchInfo() {
        return sMatchInfo;
    }


    /**
     * 使用 dp 作为适配单位（适合在新项目中使用，在老项目中使用会对原来既有的 dp 值产生影响）
     * <br>
     * <ul>
     * dp 与 px 之间的换算:
     * <li> px = density * dp </li>
     * <li> density = dpi / 160 </li>
     * <li> px = dp * (dpi / 160) </li>
     * </ul>
     *
     * @param context                       上下文
     * @param designSize                    设计图的宽/高（单位: dp）
     * @param base                          适配基准
     */
    private static void matchByDP(@NonNull final Context context, final float designSize, int base) {
        final float targetDensity;
        if (base == MATCH_BASE_WIDTH) {
            targetDensity = sMatchInfo.getScreenWidth() * 1f / designSize;
        } else if (base == MATCH_BASE_HEIGHT) {
            targetDensity = sMatchInfo.getScreenHeight() * 1f / designSize;
        } else {
            targetDensity = sMatchInfo.getScreenWidth() * 1f / designSize;
        }
        final int targetDensityDpi = (int) (targetDensity * 160);
        final float targetScaledDensity = targetDensity * (sMatchInfo.getAppScaledDensity() / sMatchInfo.getAppDensity());
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        displayMetrics.density = targetDensity;
        displayMetrics.densityDpi = targetDensityDpi;
        displayMetrics.scaledDensity = targetScaledDensity;
    }


    /**
     * 使用 pt 作为适配单位（因为 pt 比较冷门，新老项目皆适合使用；也可作为 dp 适配的补充，
     * 在需要同时适配宽度和高度时，使用 pt 来适配 dp 未适配的宽度或高度）
     * <br/>
     * <p> pt 转 px 算法: pt * metrics.xdpi * (1.0f/72) </p>
     *
     * @param context                       上下文
     * @param designSize                    设计图的宽/高（单位: pt）
     * @param base                          适配基准
     */
    private static void matchByPT(@NonNull final Context context, final float designSize, int base) {
        final float targetXdpi;
        if (base == MATCH_BASE_WIDTH) {
            targetXdpi = sMatchInfo.getScreenWidth() * 72f / designSize;
        } else if (base == MATCH_BASE_HEIGHT) {
            targetXdpi = sMatchInfo.getScreenHeight() * 72f / designSize;
        } else {
            targetXdpi = sMatchInfo.getScreenWidth() * 72f / designSize;
        }
        final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        displayMetrics.xdpi = targetXdpi;
    }

    /**
     * 适配信息
     */
    private static class MatchInfo {

        private int screenWidth;
        private int screenHeight;
        private float appDensity;
        private float appDensityDpi;
        private float appScaledDensity;
        private float appXdpi;

        int getScreenWidth() {
            return screenWidth;
        }

        void setScreenWidth(int screenWidth) {
            this.screenWidth = screenWidth;
        }

        int getScreenHeight() {
            return screenHeight;
        }

        void setScreenHeight(int screenHeight) {
            this.screenHeight = screenHeight;
        }

        float getAppDensity() {
            return appDensity;
        }

        void setAppDensity(float appDensity) {
            this.appDensity = appDensity;
        }

        float getAppDensityDpi() {
            return appDensityDpi;
        }

        void setAppDensityDpi(float appDensityDpi) {
            this.appDensityDpi = appDensityDpi;
        }

        float getAppScaledDensity() {
            return appScaledDensity;
        }

        void setAppScaledDensity(float appScaledDensity) {
            this.appScaledDensity = appScaledDensity;
        }

        float getAppXdpi() {
            return appXdpi;
        }

        void setAppXdpi(float appXdpi) {
            this.appXdpi = appXdpi;
        }
    }

}
