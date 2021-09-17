package com.yc.music.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.yc.music.utils.NotificationHelper;

public class AbsAudioService extends Service {

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.get().init(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //结束notification通知
        NotificationHelper.get().onDestroy(true);
    }

    /**
     * 绑定服务时才会调用
     * 必须要实现的方法
     * @param intent        intent
     * @return              IBinder对象
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AbsAudioService.PlayBinder();
    }


    public class PlayBinder extends Binder {
        public AbsAudioService getService() {
            return AbsAudioService.this;
        }
    }



}
