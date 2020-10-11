package org.yczbj.ycvideoplayerlib.tool;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2018/11/9
 *     desc  : 播放器异常
 *     revise:
 * </pre>
 */
public class VideoException extends RuntimeException {

    private int mCode = 0;

    public VideoException(int code, String msg) {
        super(msg);
        mCode = code;
    }

    public VideoException(String msg) {
        super(msg);
    }

    public VideoException(Throwable throwable) {
        super(throwable);
        if (throwable instanceof VideoException) {
            mCode = ((VideoException) throwable).getCode();
        }
    }

    public int getCode() {
        return mCode;
    }

    public String getMsg() {
        return getMessage();
    }

    //VideoPlayer，需要在start播放前给设置控制器Controller
    public static final int CODE_NOT_SET_CONTROLLER = 21;

}
