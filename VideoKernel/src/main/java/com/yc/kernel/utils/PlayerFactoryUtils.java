package com.yc.kernel.utils;

import com.yc.kernel.factory.PlayerFactory;
import com.yc.kernel.impl.exo.ExoPlayerFactory;
import com.yc.kernel.impl.ijk.IjkPlayerFactory;
import com.yc.kernel.impl.media.MediaPlayerFactory;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 工具类
 *     revise:
 * </pre>
 */
public final class PlayerFactoryUtils {

    /**
     * 获取PlayerFactory具体实现类，获取内核
     * TYPE_IJK                 IjkPlayer，基于IjkPlayer封装播放器
     * TYPE_NATIVE              MediaPlayer，基于原生自带的播放器控件
     * TYPE_EXO                 基于谷歌视频播放器
     * TYPE_RTC                 基于RTC视频播放器
     * @param type                              类型
     * @return
     */
    public static PlayerFactory getPlayer(@PlayerConstant.PlayerType int type){
        if (type == PlayerConstant.PlayerType.TYPE_EXO){
            return ExoPlayerFactory.create();
        } else if (type == PlayerConstant.PlayerType.TYPE_IJK){
            return IjkPlayerFactory.create();
        } else if (type == PlayerConstant.PlayerType.TYPE_NATIVE){
            return MediaPlayerFactory.create();
        } else if (type == PlayerConstant.PlayerType.TYPE_RTC){
            return IjkPlayerFactory.create();
        } else {
            return IjkPlayerFactory.create();
        }
    }

}
