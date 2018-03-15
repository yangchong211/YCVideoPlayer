package org.yczbj.ycvideoplayerlib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 常量
 */
public class ConstantKeys {

    static final String NO_LOGIN_VIP_TEXT = "试看结束，观看全部内容请开通会员。\n已是会员/已购买可登陆观看";
    static final String NO_LOGIN_TEXT = "试看结束，观看全部内容请开通会员/购买。\n已是会员/已购买可登陆观看";
    static final String LOGIN_TEXT = "试看结束，观看全部内容请开通会员。";
    static final String NO_LOGIN = "试看结束, 登录后即可观看全部免费课程。";

    @Retention(RetentionPolicy.SOURCE)
    public @interface Gender {
        int LOGIN = 1001;
        int MEMBER = 1002;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VideoControl {
        int DOWNLOAD = 1005;
        int AUDIO = 1006;
        int SHARE = 1007;
    }


}
