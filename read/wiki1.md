# 基础方法说明
#### 目录介绍
- 01.最简单的播放
- 02.竖屏全屏播放
- 03.横屏全屏播放
- 04.小窗口播放
- 05.全屏播放切换视频清晰度
- 06.在列表中播放
- 07.在activity播放视频处理home键逻辑
- 08.在fragment中播放
- 09.显示视频top[分享，下载，更多按钮控件]
- 10.全局悬浮播放视频
- 11.常见api说明




### 01.最简单的播放
- 必须需要的四步骤代码如下所示
    ```
    //设置播放类型
    videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
    //设置视频地址和请求头部
    videoPlayer.setUp(urls, null);
    //创建视频控制器
    controller = new VideoPlayerController(this);
    //设置视频控制器
    videoPlayer.setController(controller);
    ```
- 开始播放
    ```
    //播放视频
    videoPlayer.start();
    ```


### 02.竖屏全屏播放
- 如下所示
    ```
    //进入竖屏的全屏模式
    videoPlayer.enterVerticalScreenScreen();
    ```

### 03.横屏全屏播放
- 如下所示
    ```
    //进入全屏模式
    videoPlayer.enterFullScreen();
    ```


### 04.小窗口播放
- 如下所示
    ```
    //进入小窗口播放
    videoPlayer.enterTinyWindow();
    ```


### 05.全屏播放切换视频清晰度
- 代码如下所示
    ```
    mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
    VideoPlayerController controller = new VideoPlayerController(this);
    controller.setClarity(getClarites(), 0);
    mVideoPlayer.setController(controller);


    //设置清晰度的话，需要传递不同的视频链接
    public List<VideoClarity> getClarites() {
        List<VideoClarity> clarities = new ArrayList<>();
        clarities.add(new VideoClarity("标清", "270P", "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("高清", "480P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("超清", "720P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        clarities.add(new VideoClarity("蓝光", "1080P", "http://play.g3proxy.lecloud.com/vod/v2/MjQ5LzM3LzIwL2xldHYtdXRzLzE0L3Zlcl8wMF8yMi0xMTA3NjQxMzkwLWF2Yy00MTk4MTAtYWFjLTQ4MDAwLTUyNjExMC0zMTU1NTY1Mi00ZmJjYzFkNzA1NWMyNDc4MDc5OTYxODg1N2RjNzEwMi0xNDk4NTU3OTYxNzQ4Lm1wNA==?b=479&mmsid=65565355&tm=1499247143&key=98c7e781f1145aba07cb0d6ec06f6c12&platid=3&splatid=345&playid=0&tss=no&vtype=13&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super"));
        return clarities;
    }
    ```


### 06.在列表中播放
- 代码如下所示
    - 在recyclerView中
    ```
    recyclerView.setRecyclerListener(new RecyclerView.RecyclerListener() {
        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            VideoPlayer videoPlayer = ((VideoAdapter.VideoViewHolder) holder).mVideoPlayer;
            if (videoPlayer == VideoPlayerManager.instance().getCurrentVideoPlayer()) {
                VideoPlayerManager.instance().releaseVideoPlayer();
            }
        }
    });
    ```
    - 在adapter中，仅仅展示部分代码
    ```
    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_test_my_video, parent, false);
        VideoViewHolder holder = new VideoViewHolder(itemView);
        //创建视频播放控制器，主要只要创建一次就可以呢
        VideoPlayerController controller = new VideoPlayerController(mContext);
        holder.setController(controller);
        return holder;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public VideoPlayerController mController;
        public VideoPlayer mVideoPlayer;

        VideoViewHolder(View itemView) {
            super(itemView);
            mVideoPlayer = (VideoPlayer) itemView.findViewById(R.id.nice_video_player);
            // 将列表中的每个视频设置为默认16:9的比例
            ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
            // 宽度为屏幕宽度
            params.width = itemView.getResources().getDisplayMetrics().widthPixels;
            // 高度为宽度的9/16
            params.height = (int) (params.width * 9f / 16f);
            mVideoPlayer.setLayoutParams(params);
        }

        /**
         * 设置视频控制器参数
         * @param controller            控制器对象
         */
        void setController(VideoPlayerController controller) {
            mController = controller;
            mVideoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_IJK);
            mVideoPlayer.setController(mController);
        }

        void bindData(Video video) {
            mController.setTitle(video.getTitle());
            //mController.setLength(video.getLength());
            ImageUtil.loadImgByPicasso(itemView.getContext(),video.getImageUrl(),R.drawable.image_default,mController.imageView());
            mVideoPlayer.setUp(video.getVideoUrl(), null);
        }
    }
    ```


### 07.在activity播放视频处理home键逻辑
- 代码如下所示
    ```
    private boolean pressedHome;
    private HomeKeyWatcher mHomeKeyWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeKeyWatcher = new HomeKeyWatcher(this);
        mHomeKeyWatcher.setOnHomePressedListener(new HomeKeyWatcher.OnHomePressedListener() {
            @Override
            public void onHomePressed() {
                pressedHome = true;
            }
        });
        pressedHome = false;
        mHomeKeyWatcher.startWatch();
    }

    @Override
    protected void onStop() {
        // 在OnStop中是release还是suspend播放器，需要看是不是因为按了Home键
        if (pressedHome) {
            VideoPlayerManager.instance().suspendVideoPlayer();
        } else {
            VideoPlayerManager.instance().releaseVideoPlayer();
        }
        super.onStop();
        mHomeKeyWatcher.stopWatch();
    }

    @Override
    protected void onRestart() {
        mHomeKeyWatcher.startWatch();
        pressedHome = false;
        super.onRestart();
        VideoPlayerManager.instance().resumeVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }
    ```


### 08.在fragment中播放
- 和activity中一样，不同点在于处理fragment返回键逻辑
    ```
    //在宿主Activity中设置代码如下
    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (VideoPlayerManager.instance().onBackPressed()) return;
        super.onBackPressed();
    }

    //--------------------------------------------------

    //在此Fragment中设置代码如下
    @Override
    public void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }
    ```



### 09.显示视频top[分享，下载，更多按钮控件]
- 默认是不显示这几个控件的，一般实际项目中，会对播放器做很多UI方面拓展
    ```
    controller.setTopVisibility(true);
    ```
- 给按钮设置点击事件
    ```
    controller.setOnVideoControlListener(new OnVideoControlListener() {
        @Override
        public void onVideoControlClick(int type) {
            switch (type){
                case ConstantKeys.VideoControl.DOWNLOAD:
                    ToastUtils.showShort("下载");
                    break;
                case ConstantKeys.VideoControl.AUDIO:
                    ToastUtils.showShort("转音频");
                    break;
                case ConstantKeys.VideoControl.SHARE:
                    ToastUtils.showShort("分享");
                    break;
                case ConstantKeys.VideoControl.MENU:
                    ToastUtils.showShort("更多");
                    break;
                case ConstantKeys.VideoControl.TV:
                    ToastUtils.showShort("tv投影");
                    break;
                case ConstantKeys.VideoControl.HOR_AUDIO:
                    ToastUtils.showShort("下载");
                    break;
                default:
                    break;
            }
        }
    });
    ```


### 10.全局悬浮播放视频
- 代码如下所示
    ```
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
         * 这里在返回主页的时候销毁了，因为不想和DEMO中其他页面冲突
         */
        VideoPlayerManager.instance().releaseVideoPlayer();
        FloatWindow.destroy();
    }


    private void startWindow() {
        if (FloatWindow.get() != null) {
            return;
        }
        String url = "http://play.g3proxy.lecloud.com/vod/v2/MjUxLzE2LzgvbGV0di11dHMvMTQvdmVyXzAwXzIyLTExMDc2NDEzODctYXZjLTE5OTgxOS1hYWMtNDgwMDAtNTI2MTEwLTE3MDg3NjEzLWY1OGY2YzM1NjkwZTA2ZGFmYjg2MTVlYzc5MjEyZjU4LTE0OTg1NTc2ODY4MjMubXA0?b=259&mmsid=65565355&tm=1499247143&key=f0eadb4f30c404d49ff8ebad673d3742&platid=3&splatid=345&playid=0&tss=no&vtype=21&cvid=2026135183914&payff=0&pip=08cc52f8b09acd3eff8bf31688ddeced&format=0&sign=mb&dname=mobile&expect=1&tag=mobile&xformat=super";
        FloatPlayerView.setUrl(url);
        FloatPlayerView floatPlayerView = new FloatPlayerView(getApplicationContext());
        floatPlayerView.setCompletedListener(new FloatPlayerView.CompletedListener() {
            @Override
            public void Completed() {
                FloatWindow.get().hide();
            }
        });
        FloatWindow
                .with(getApplicationContext())
                .setView(floatPlayerView)
                //.setWidth(WindowScreen.width, 0.4f)
                //.setHeight(WindowScreen.width, 0.3f)
                .setX(WindowScreen.width, 0.8f)             //这个是设置位置
                .setY(WindowScreen.height, 0.3f)
                .setMoveType(MoveType.slide)
                .setFilter(false)
                //.setFilter(true, WindowActivity.class, EmptyActivity.class)
                .setMoveStyle(500, new BounceInterpolator())
                .build();
        FloatWindow.get().show();
    }
    ```



### 11.常见api说明
#### 11.1 关于VideoPlayer中设置属性方法
- 如下所示
    ```
    //进入全屏模式
    videoPlayer.enterFullScreen();
    //进入竖屏的全屏模式
    videoPlayer.enterVerticalScreenScreen();
    //进入小窗口播放
    //注意：小窗口播放视频比例是        16：9
    videoPlayer.enterTinyWindow();

    //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
    videoPlayer.release();
    //释放播放器，注意一定要判断对象是否为空，增强严谨性
    videoPlayer.releasePlayer();

    //设置播放器类型，必须设置
    //输入值：ConstantKeys.IjkPlayerType.TYPE_IJK   或者  ConstantKeys.IjkPlayerType.TYPE_NATIVE
    videoPlayer.setPlayerType(ConstantKeys.IjkPlayerType.TYPE_NATIVE);
    //设置播放位置
    videoPlayer.seekTo(100);
    //设置播放速度，不必须
    videoPlayer.setSpeed(100);
    //设置视频链接
    videoPlayer.setUp("",null);
    //设置音量
    videoPlayer.setVolume(50);

    //是否从上一次的位置继续播放
    videoPlayer.continueFromLastPosition(true);
    ```


#### 11.2 关于VideoPlayer中获取属性方法
- 如下所示
    ```
    //关于视频播放相关api
    //获取缓冲区百分比
    int bufferPercentage = videoPlayer.getBufferPercentage();
    //获取播放位置
    long currentPosition = videoPlayer.getCurrentPosition();
    //获取当前播放模式
    int currentState = videoPlayer.getCurrentState();
    //获取持续时长
    long duration = videoPlayer.getDuration();
    //获取最大音量
    int maxVolume = videoPlayer.getMaxVolume();
    //获取当前播放状态
    int playType = videoPlayer.getPlayType();
    //获取播放速度
    long tcpSpeed = videoPlayer.getTcpSpeed();
    //获取音量值
    int volume = videoPlayer.getVolume();
    ```

#### 11.3 关于VideoPlayer中设置播放状态方法
- 如下所示
    ```
    //判断是否是否缓冲暂停
    boolean bufferingPaused = videoPlayer.isBufferingPaused();
    //判断视频是否正在缓冲(播放器正在播放时，缓冲区数据不足，进行缓冲，缓冲区数据足够后恢复播放)
    boolean bufferingPlaying = videoPlayer.isBufferingPlaying();
    //判断视频是否播放完成
    boolean completed = videoPlayer.isCompleted();
    //判断视频是否播放错误
    boolean error = videoPlayer.isError();
    //判断视频是否播放全屏
    boolean fullScreen = videoPlayer.isFullScreen();
    //判断是否开始播放
    boolean idle = videoPlayer.isIdle();
    //判断视频是否正常播放
    boolean normal = videoPlayer.isNormal();
    //判断视频是否暂停播放
    boolean paused = videoPlayer.isPaused();
    //判断视频是否正在播放
    boolean playing = videoPlayer.isPlaying();
    //判断视频是否准备就绪
    boolean prepared = videoPlayer.isPrepared();
    //判断视频是否播放准备中
    boolean preparing = videoPlayer.isPreparing();
    //判断视频是否播放小窗口
    boolean tinyWindow = videoPlayer.isTinyWindow();


    //开始播放
    videoPlayer.start();
    //暂停播放
    videoPlayer.pause();
    //开始播放
    videoPlayer.start(100);
    //重新播放
    videoPlayer.restart();
    ```


#### 11.4 关于controller控制器api
- 如下所示
    ```
    //设置是否显示视频头部的下载，分享，其他等控件是否显示
    controller.setTopVisibility(true);
    controller.setTop(20);
    //设置top到顶部的距离
    controller.setTopPadding(30);
    //设置加载loading类型
    controller.setLoadingType(ConstantKeys.Loading.LOADING_RING);
    //设置不操作后，多久自动隐藏头部和底部布局
    controller.setHideTime(8000);
    //设置中间播放按钮是否显示，并且支持设置自定义图标
    controller.setCenterPlayer(true,R.drawable.image_default);
    //获取ImageView的对象
    ImageView imageView = controller.imageView();
    //重新设置
    controller.reset();
    //设置图片
    controller.setImage(R.drawable.ic_back_right);
    //设置视频时长
    controller.setLength(1000);
    //设置视频标题
    controller.setTitle("小杨逗比");
    boolean lock = controller.getLock();
    //设置横屏播放时，tv和audio图标是否显示
    controller.setTvAndAudioVisibility(true,true);
    //让用户自己处理返回键事件的逻辑
    controller.setOnVideoBackListener(new OnVideoBackListener() {
        @Override
        public void onBackClick() {

        }
    });
    //播放暂停监听事件
    controller.setOnPlayOrPauseListener(new OnPlayOrPauseListener() {
        @Override
        public void onPlayOrPauseClick(boolean isPlaying) {

        }
    });
    //监听视频播放完成事件
    controller.setOnCompletedListener(new OnCompletedListener() {
        @Override
        public void onCompleted() {

        }
    });
    //设置视频分享，下载，音视频转化点击事件
    controller.setOnVideoControlListener(new OnVideoControlListener() {
        @Override
        public void onVideoControlClick(int type) {

        }
    });

    //视频播放模式监听
    controller.setOnPlayerTypeListener(new OnPlayerTypeListener() {
        /**
         * 切换到全屏播放监听
         */
        @Override
        public void onFullScreen() {
            LogUtils.e("setOnPlayerTypeListener"+"onFullScreen");
        }
        /**
         * 切换到小窗口播放监听
         */
        @Override
        public void onTinyWindow() {
            LogUtils.e("setOnPlayerTypeListener"+"onTinyWindow");
        }
        /**
         * 切换到正常播放监听
         */
        @Override
        public void onNormal() {
            LogUtils.e("setOnPlayerTypeListener"+"onNormal");
        }
    });
    ```

#### 11.5 关于视频播放器管理器api
- 如下所示
    ```
    //VideoPlayerManager对象
    VideoPlayerManager instance = VideoPlayerManager.instance();
    //当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放
    instance.resumeVideoPlayer();
    //当视频正在播放或者正在缓冲时，调用该方法暂停视频
    instance.suspendVideoPlayer();
    //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
    instance.releaseVideoPlayer();
    //处理返回键逻辑
    //如果是全屏，则退出全屏
    //如果是小窗口，则退出小窗口
    instance.onBackPressed();
    //获取对象
    VideoPlayer currentVideoPlayer = instance.getCurrentVideoPlayer();
    //设置VideoPlayer
    instance.setCurrentVideoPlayer(videoPlayer);
    ```



