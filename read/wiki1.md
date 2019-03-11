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



### 11.常见api说明

























