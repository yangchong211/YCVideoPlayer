package cn.jzvd;


public class JZVideoPlayerManager {

    private static JZVideoPlayer FIRST_FLOOR_JZVD;
    private static JZVideoPlayer SECOND_FLOOR_JZVD;

    static JZVideoPlayer getFirstFloor() {
        return FIRST_FLOOR_JZVD;
    }

    static void setFirstFloor(JZVideoPlayer jzVideoPlayer) {
        FIRST_FLOOR_JZVD = jzVideoPlayer;
    }

    static JZVideoPlayer getSecondFloor() {
        return SECOND_FLOOR_JZVD;
    }

    static void setSecondFloor(JZVideoPlayer jzVideoPlayer) {
        SECOND_FLOOR_JZVD = jzVideoPlayer;
    }

    public static JZVideoPlayer getCurrentJzvd() {
        if (getSecondFloor() != null) {
            return getSecondFloor();
        }
        return getFirstFloor();
    }

    static void completeAll() {
        if (SECOND_FLOOR_JZVD != null) {
            SECOND_FLOOR_JZVD.onCompletion();
            SECOND_FLOOR_JZVD = null;
        }
        if (FIRST_FLOOR_JZVD != null) {
            FIRST_FLOOR_JZVD.onCompletion();
            FIRST_FLOOR_JZVD = null;
        }
    }
}
