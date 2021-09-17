package com.yc.music.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.yc.music.config.MusicConstant;
import com.yc.music.config.MusicPlayAction;
import com.yc.music.delegate.PlayAudioDelegate;
import com.yc.music.impl.PlayAudioImpl;
import com.yc.music.inter.EventCallback;
import com.yc.music.inter.InterPlayAudio;
import com.yc.music.inter.OnPlayerEventListener;
import com.yc.music.model.AudioBean;
import com.yc.music.tool.BaseAppHelper;
import com.yc.music.tool.QuitTimerHelper;
import com.yc.videotool.VideoLogUtils;


/**
 * <pre>
 *     @author yangchong
 *     blog  : https://www.jianshu.com/p/514eb6193a06
 *     time  : 2016/2/10
 *     desc  : Service就是用来在后台完成一些不需要和用户交互的动作
 *     revise: 在清科公司写的音频播放器，后期暂停维护
 * </pre>
 */
public class PlayAudioService extends AbsAudioService {

    /**
     * 是否锁屏了，默认是false
     */
    private boolean mIsLocked = false;
    private InterPlayAudio mDelegate;
    /**
     * 播放进度监听器
     */
    private OnPlayerEventListener mListener;
    /**
     * 更新播放进度的显示，时间的显示
     */
    public static final int UPDATE_PLAY_PROGRESS_SHOW = 520;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_PLAY_PROGRESS_SHOW:
                    mDelegate.updatePlayProgress();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 比如，广播，耳机声控，通知栏广播，来电或者拔下耳机广播开启服务
     * @param context       上下文
     * @param type          类型
     */
    public static void startCommand(Context context, String type) {
        Intent intent = new Intent(context, PlayAudioService.class);
        intent.setAction(type);
        context.startService(intent);
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    @Override
    public void onCreate() {
        super.onCreate();
        mDelegate = new PlayAudioDelegate(new PlayAudioImpl());
        mDelegate.init(this);
        initQuitTimer();
    }

    /**
     * 服务在销毁时调用该方法
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        //销毁handler
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        mDelegate.release();
        //设置service为null
        BaseAppHelper.get().setPlayService(null);
    }


    /**
     * 每次通过startService()方法启动Service时都会被回调。
     * @param intent                intent
     * @param flags                 flags
     * @param startId               startId
     * @return
     * onStartCommand方法返回值作用：
     * START_STICKY：粘性，service进程被异常杀掉，系统重新创建进程与服务，会重新执行onCreate()、onStartCommand(Intent)
     * START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
     * START_NOT_STICKY：非粘性，Service进程被异常杀掉，系统不会自动重启该Service。
     * START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            switch (intent.getAction()) {
                //上一首
                case MusicPlayAction.TYPE_PRE:
                    prev();
                    break;
                //下一首
                case MusicPlayAction.TYPE_NEXT:
                    next();
                    break;
                //播放或暂停
                case MusicPlayAction.TYPE_START_PAUSE:
                    playPause();
                    break;
                //添加锁屏界面
                case MusicConstant.LOCK_SCREEN_ACTION:
                    VideoLogUtils.e("PlayService"+"---LOCK_SCREEN"+mIsLocked);
                    break;
                //当屏幕灭了，添加锁屏页面
                case Intent.ACTION_SCREEN_OFF:
                    startLockAudioActivity();
                    VideoLogUtils.e("PlayService"+"---当屏幕灭了");
                    break;
                //当屏幕亮了
                case Intent.ACTION_SCREEN_ON:
                    VideoLogUtils.e("PlayService"+"---当屏幕亮了");
                    break;
                default:
                    break;
            }
        }
        return START_NOT_STICKY;
    }





    /**
     * 初始化计时器
     */
    private void initQuitTimer() {
        QuitTimerHelper.getInstance().init(this, handler, new EventCallback<Long>() {
            @Override
            public void onEvent(Long aLong) {
                if (mListener != null) {
                    mListener.onTimer(aLong);
                }
            }
        });
    }

    /**
     * 播放或暂停
     * 逻辑：
     * 1.如果正在准备，点击则是停止播放
     * 2.如果是正在播放，点击则是暂停
     * 3.如果是暂停状态，点击则是开始播放
     * 4.其他情况是直接播放
     */
    public void playPause() {
        mDelegate.playPause();
    }


    /**
     * 上一首
     * 记住有播放类型，单曲循环，顺序循环，随机播放
     * 逻辑：如果不是第一首，则还有上一首；如果没有上一首，则切换到最后一首
     */
    public void prev() {
        mDelegate.prev();
    }


    /**
     * 下一首
     * 记住有播放类型，单曲循环，顺序循环，随机播放
     * 逻辑：如果不是最后一首，则还有下一首；如果是最后一首，则切换回第一首
     */
    public void next() {
        mDelegate.next();
    }

    /**
     * 开始播放
     */
    public void start() {
        mDelegate.play();
    }


    /**
     * 暂停
     */
    public void pause() {
        mDelegate.pause();
    }


    /**
     * 停止播放
     */
    public void stop() {
        mDelegate.stop();
    }


    /**
     * 播放索引为position的音乐
     * @param position              索引
     */
    public void play(int position) {
        mDelegate.play(position);
    }


    /**
     * 拖动seekBar时，调节进度
     * @param progress          进度
     */
    public void seekTo(int progress) {
        mDelegate.seekTo(progress);
    }

    /**
     * 播放，这种是直接传音频实体类
     * @param music         music
     */
    public void play(AudioBean music) {
        mDelegate.play(music);
    }

    public boolean isPlaying(){
        return mDelegate.isPlaying();
    }

    public boolean isPreparing(){
        return mDelegate.isPreparing();
    }

    public long getCurrentPosition(){
        return mDelegate.getCurrentPosition();
    }

    public int getPlayingPosition(){
        return mDelegate.getPlayingPosition();
    }

    public AudioBean getPlayingMusic(){
        return mDelegate.getPlayingMusic();
    }

    public boolean isDefault(){
        return mDelegate.isDefault();
    }

    public boolean isPausing(){
        return mDelegate.isPausing();
    }
    /**
     * 退出时候调用
     */
    public void quit() {
        // 先停止播放
        stop();
        // 移除定时器
        QuitTimerHelper.getInstance().stop();
        // 当另一个组件（如 Activity）通过调用 startService() 请求启动服务时，系统将调用onStartCommand。
        // 一旦执行此方法，服务即会启动并可在后台无限期运行。 如果自己实现此方法，则需要在服务工作完成后，
        // 通过调用 stopSelf() 或 stopService() 来停止服务。
        stopSelf();
    }

//
//    /**
//     * 扫描音乐
//     */
//    @SuppressLint("StaticFieldLeak")
//    public void updateMusicList(final EventCallback<Void> callback) {
//        new AsyncTask<Void, Void, List<AudioBean>>() {
//            @Override
//            protected List<AudioBean> doInBackground(Void... params) {
//                return FileMusicScanManager.getInstance().scanMusic(PlayService.this);
//            }
//
//            @Override
//            protected void onPostExecute(List<AudioBean> musicList) {
//                //首先先清空
//                //然后添加所有扫描到的音乐
//                BaseAppHelper.get().setMusicList(musicList);
//
//                //如果获取音乐数据集合不为空
//                if (!BaseAppHelper.get().getMusicList().isEmpty()) {
//                    //音频的集合
//                    audioMusics = BaseAppHelper.get().getMusicList();
//                    //刷新正在播放的本地歌曲的序号
//                    updatePlayingPosition();
//                    //获取正在播放的音乐
//                    if(mPlayingPosition>=0){
//                        mPlayingMusic = BaseAppHelper.get().getMusicList().get(mPlayingPosition);
//                    }
//                }
//                if (callback != null) {
//                    callback.onEvent(null);
//                }
//            }
//        }.execute();
//    }



    /**
     * 获取播放进度监听器对象
     * @return                  OnPlayerEventListener对象
     */
    public OnPlayerEventListener getOnPlayEventListener() {
        return mListener;
    }

    /**
     * 设置播放进度监听器
     * @param listener          listener
     */
    public void setOnPlayEventListener(OnPlayerEventListener listener) {
        mListener = listener;
        mDelegate.setOnPlayEventListener(mListener);
    }


    /**-------------------------------------添加锁屏界面----------------------------------------*/


    /**
     * 打开锁屏页面，这块伤透了脑筋
     * 不管是播放状态是哪一个，只要屏幕灭了到亮了，就展现这个锁屏页面
     * 有些APP限制了状态，比如只有播放时才走这个逻辑
     */
    private void startLockAudioActivity() {
//        if(!mIsLocked && isPlaying()){
//            Intent lockScreen = new Intent(this, LockAudioActivity.class);
//            lockScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(lockScreen);
//            BaseConfig.INSTANCE.setLocked(true);
//        }
    }

}
