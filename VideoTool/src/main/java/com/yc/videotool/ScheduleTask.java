package com.yc.videotool;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://github.com/yangchong211
 *     time  : 2017/10/21
 *     desc  : 线程工具类
 *     revise:
 * </pre>
 */
public class ScheduleTask {

    private final ExecutorService mExecutors;

    private ScheduleTask() {
        this.mExecutors = Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(@NonNull Runnable r) {
                //创建线程
                Thread scheduleTask = new Thread(r);
                scheduleTask.setName("ScheduleTask");
                return scheduleTask;
            }
        });
    }

    public static ScheduleTask getInstance() {
        return ScheduleTask.Holder.INSTANCE;
    }

    public void schedule(Runnable runnable) {
        if (mExecutors != null){
            this.mExecutors.execute(runnable);
        }
    }

    private static class Holder {
        // 单例模式
        private static final ScheduleTask INSTANCE = new ScheduleTask();

        private Holder() {

        }
    }

}
