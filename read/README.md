#### **目录介绍**
- **1.关于此视频封装库介绍**
- 1.1 能够满足那些业务需求
- 1.2 对比同类型的库有哪些优势
- **2.关于使用方法说明**
- 2.1 关于gradle引用说明
- 2.2 添加布局
- 2.3 最简单的视频播放器参数设定
- 2.4 注意的问题，视频播放优化
- 2.5 关于开源库中的类说明
- 2.6 暴露各种监听事件接口
- **3.关于播放类型说明**
- 3.1 普通视频播放
- 3.2 list页面视频播放
- 3.3 小窗口视频播放
- 3.4 类似爱奇艺，优酷会员试看视频播放
- 3.5 设置竖屏全屏或者横屏全屏模式
- 3.6 关于封装库中日志打印
- **4.关于相关方法说明**
- 4.1 关于VideoPlayer类[播放器]中方法说明
- 4.2 关于VideoPlayerController类[控制器]中方法说明
- 4.3 关于对象的销毁
- **5.关于封装的思路**
- 5.1 参考的案例思路
- 5.2 封装的基本思路
- 5.3 关于窗口切换分析
- 5.4 关于VideoPlayerManager视频播放器管理器分析
- 5.5 关于VideoPlayerController视频控制器分析
- 5.6 关于InterVideoPlayer接口分析
- **6.关于如何自定义你想要的视频播放模式**
- 6.1 自定义视频播放器
- **7.关于效果图的展示**
- 7.1 效果图如下所示
- **8.关于遇到的问题说明**
- 8.1 视频难点
- 8.2 遇到的bug
- 8.3 后期需要实现的功能
- **9.关于版本更新说明**
- 9.0.0 v0.0.0 写于2017年7月1日
- 9.0.1 V1.0.0 更新于2017年9月4日
- 9.0.2 V1.0.1 更新于2017年11月18日
- 9.0.3 v1.1.0 更新于2018年1月15日
- 9.0.4 v2.0.0 更新于2018年1月18日
- 9.0.5 v2.4.5 更新于2018年4月21日
- 9.0.6 v2.4.6 更新于2018年8月2日
- 9.0.7 v2.4.8 更新于2018年8月12日
- 9.0.8 v2.4.9 更新于2018年8月16日
- 9.0.9 v2.5.0 更新与2018年8月20日
- 9.1.0 v2.6.0 更新于2018年9月25日
- **10.关于参考文档说明**
- 10.1 参考的项目
- 10.2 参考的博客
- **11.关其他说明**
- 11.1 目前市场流行的视频框架
- 11.2 如何选择合适的框架
- 11.3 关于我的个人博客和站点




### 0.备注
- 仿照爱奇艺，优酷播放器写的，十分感谢GitHub上大神前辈们的开源案例和思路。
- 支持插入广告，设置视频观看权限，观看完后登录或者购买会员。我看到在star较多的项目issues中，有些人正好需要这个案例，库集成后直接通过代码调用即可，灵活且拓展性强。
- 由于调到做视频的部门，因此此部分代码会持续更新，也欢迎同行提bug或者问题
- 如果你觉得还可以，给个star吧！我也在持续学习中！！！
- 项目地址：https://github.com/yangchong211/YCVideoPlayer


### 1.关于此视频封装库介绍
#### 1.1 能够满足那些业务需求
##### A基础功能
- A.1.1 能够自定义视频加载loading类型，设置视频标题，设置视频底部图片，设置播放时长等基础功能
- A.1.2 可以切换播放器的视频播放状态，播放错误，播放未开始，播放开始，播放准备中，正在播放，暂停播放，正在缓冲等等状态
- A.1.3 可以自由设置播放器的播放模式，比如，正常播放，全屏播放，和小屏幕播放。其中全屏播放支持旋转屏幕。
- A.1.4 可以支持多种视频播放类型，比如，原生封装视频播放器，还有基于ijkPlayer封装的播放器。
- A.1.5 可以设置是否隐藏播放音量，播放进度，播放亮度等，可以通过拖动seekBar改变视频进度。还支持设置n秒后不操作则隐藏头部和顶部布局功能
- A.1.6 可以设置竖屏模式下全屏模式和横屏模式下的全屏模式，方便多种使用场景
- A.1.7 top和bottom面版消失和显示：点击视频画面会显示、隐藏操作面板；显示后不操作会5秒后自动消失【也可以设置】
##### B高级功能
- B.1.1 支持一遍播放一遍缓冲的功能，其中缓冲包括两部分，第一种是播放过程中缓冲，第二种是暂停过程中缓冲
- B.1.2 基于ijkPlayer的封装播放器，支持多种格式视频播放
- B.1.3 可以设置是否记录播放位置，设置播放速度，设置屏幕比例
- B.1.4 支持滑动改变音量【屏幕右边】，改变屏幕亮度【屏幕左边】，屏幕底测左右滑动调节进度
- B.1.5 支持list页面中视频播放，滚动后暂停播放，播放可以自由设置是否记录状态。并且还支持删除视频播放位置状态。
- B.1.6 切换横竖屏：切换全屏时，隐藏状态栏，显示自定义top(显示电量)；竖屏时恢复原有状态
- B.1.7 支持切换视频清晰度模式
- B.1.8 添加锁屏功能，竖屏不提供锁屏按钮，横屏全屏时显示，并且锁屏时，屏蔽手势处理
##### C拓展功能【这块根据实际情况选择是否需要使用，一般视频付费App会有这个工鞥】
- **C1产品需求：类似优酷，爱奇艺视频播放器部分逻辑。比如如果用户没有登录也没有看视频权限，则提示试看视频[自定义布局]；如果用户没有登录但是有看视频权限，则正常观看；如果用户登录，但是没有充值会员，部分需要权限视频则进入试看模式，试看结束后弹出充值会员界面；如果用户余额不足，比如余额只有99元，但是视频观看要199元，则又有其他提示。**
- C2自身需求：比如封装好了视频播放库，那么点击视频上登录按钮则跳到登录页面；点击充值会员页面也跳到充值页面。这个通过定义接口，可以让使用者通过方法调用，灵活处理点击事件。
- C.1.1 可以设置试看模式，设置试看时长。试看结束后就提示登录或者充值……
- C.1.2 对于设置视频的宽高，建议设置成4：3或者16：9或者常用比例，如果不是常用比例，则可能会有黑边。其中黑边的背景可以设置
- C.1.3 可以设置播放有权限的视频时的各种文字描述，而没有把它写在封装库中，使用者自己设定
- C.1.4 锁定屏幕功能
##### D待添加功能
- D.1.1 可以支持屏幕截图功能，视频添加水印效果
- D.1.2 支持弹幕功能
- D.1.3 后期待定，视频拖拽



#### 1.2 对比同类型的库有哪些优势
##### 1.2.1目前仅仅查了下GitHub上项目
- 目前GitHub上比较流行的库
- 至于官方库就不说了，jiecao的库是基于ijkplayer视频框架，目前封装库有许多，下面几个只是star比较多，其中jiecao库比较类似。
    ```
    ijkplayer官方库
    https://github.com/Bilibili/ijkplayer
    Vitamio官方库
    https://github.com/yixia/VitamioBundle
    以jiecao为例的封装库
    https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
    https://github.com/open-android/JieCaoVideoPlayer                   1年前
    https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
    https://github.com/CarGuo/GSYVideoPlayer
    其他库
    https://github.com/danylovolokh/VideoPlayerManager
    ```

##### 1.2.2 具有的优势
- **A.代码布局更加简洁，而且无多余代码**
- **B.几乎没有多少淡黄色警告，关于注释，通过使用阿里编码插件检测后更加规范，我对代码有洁癖**
- **C.视频播放器[负责播放]，视频控制器[负责视频播放各种点击或者属性设置操作]，控制器抽象类[定义属性抽象类，供子类实现]，其他可以看代码。结构分层上比较清晰**
- **D.几乎所有的方法或者重要的成员或者局部变量都有相关的注释，注释的内容非常详细**
- **E.关于视频属性设置或者按钮点击事件，都可以通过设置相关方法灵活实现。**
- 首先这些库封装的思路和代码都不错，我也是借鉴他们的思路，在他们的思路上改进而封装的。
- **相比来说代码结构更加清晰，举几个例子**
- 针对视频播放页面布局，由于视频播放状态众多，我封装这库不同状态布局有十几种，许多库的视图布局没注释，显示比较臃肿，如果修改或者定位，不熟悉或者好久不操作，都要花时间找。展示我的布局代码
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!--https://github.com/yangchong211-->
        <!--如果你觉得好，请给个star，让更多人使用，避免重复造轮子-->
        <!--底图，主要是显示视频缩略图-->
        <ImageView
            android:id="@+id/image"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="fitXY"
            android:visibility="visible"/>
        <!--加载动画view-->
        <include layout="@layout/custom_video_player_loading"/>
        <!--改变播放位置-->
        <include layout="@layout/custom_video_player_change_position"/>
        <!--改变亮度-->
        <include layout="@layout/custom_video_player_change_brightness"/>
        <!--改变声音-->
        <include layout="@layout/custom_video_player_change_volume"/>
        <!--播放完成，你也可以自定义-->
        <include layout="@layout/custom_video_player_completed"/>
        <!--播放错误-->
        <include layout="@layout/custom_video_player_error"/>
        <!--顶部控制区-->
        <include layout="@layout/custom_video_player_top"/>
        <!--底部控制区-->
        <include layout="@layout/custom_video_player_bottom"/>
        <!--右下角初始显示的总时长-->
        <TextView
            android:id="@+id/length"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_alignParentEnd="true"
            android:layout_marginBottom="12dp"
            android:layout_marginEnd="8dp"
            android:padding="4dp"
            android:visibility="visible"
            android:text="00:00"
            android:textColor="@android:color/white"
            android:textSize="12sp"/>
        <!--中间开始播放按钮-->
        <ImageView
            android:id="@+id/center_start"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:src="@drawable/ic_player_center_start"
            android:visibility="visible"/>
        <!--试看按钮-->
        <ImageView
            android:id="@+id/iv_try_see"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:src="@drawable/selector_try_see"
            android:visibility="gone"/>
        <!--试看布局，非会员显示该布局-->
        <include layout="@layout/custom_video_player_try_see"/>
    </RelativeLayout>
    ```




### 2.关于使用方法说明
#### 2.1 关于gradle引用说明
##### 2.1.1直接引用这段代码就可以
```
compile 'cn.yc:YCVideoPlayerLib:2.4.9' 
```

#### 2.2 添加布局
- 注意，在实际开发中，由于Android手机碎片化比较严重，分辨率太多了，建议灵活设置布局的宽高比为4：3或者16：9或者你认为合适的，可以用代码设置。
- 如果宽高比变形，则会有黑边
    ```
    <org.yczbj.ycvideoplayerlib.VideoPlayer
        android:id="@+id/video_player"
        android:layout_width="match_parent"
        android:layout_height="240dp"/>
    ```


#### 2.3 最简单的视频播放器参数设定
##### 2.3.1 这个是最简单视频播放器的设置参数代码
```
//设置播放类型
// IjkPlayer or MediaPlayer
videoPlayer1.setPlayerType(VideoPlayer.TYPE_NATIVE);
//网络视频地址
String videoUrl = DataUtil.getVideoListData().get(0).getVideoUrl();
//设置视频地址和请求头部
videoPlayer1.setUp(videoUrl, null);
//是否从上一次的位置继续播放
videoPlayer1.continueFromLastPosition(true);
//设置播放速度
videoPlayer1.setSpeed(1.0f);
//创建视频控制器
VideoPlayerController controller = new VideoPlayerController(this);
controller.setTitle("办快来围观拉，自定义视频播放器可以播放视频拉");
//设置视频时长
controller.setLength(98000);
//设置5秒不操作后则隐藏头部和底部布局视图
controller.setHideTime(5000);
//controller.setImage(R.drawable.image_default);
ImageUtil.loadImgByPicasso(this, R.drawable.image_default, R.drawable.image_default, controller.imageView());
//设置视频控制器
videoPlayer1.setController(controller);
```

##### 2.3.2 关于模仿爱奇艺登录会员权限功能代码
```
//设置视频加载缓冲时加载窗的类型，多种类型
controller.setLoadingType(2);
ArrayList<String> content = new ArrayList<>();
content.add("试看结束，yc观看全部内容请开通会员1111。");
content.add("试看结束，yc观看全部内容请开通会员2222。");
content.add("试看结束，yc观看全部内容请开通会员3333。");
content.add("试看结束，yc观看全部内容请开通会员4444。");
controller.setMemberContent(content);
controller.setHideTime(5000);
//设置设置会员权限类型，第一个参数是否登录，第二个参数是否有权限看，第三个参数试看完后展示的文字内容，第四个参数是否保存进度位置
controller.setMemberType(false,false,3,true);
controller.imageView().setBackgroundResource(R.color.blackText);
//ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
//设置试看结束后，登录或者充值会员按钮的点击事件
controller.setOnMemberClickListener(new OnMemberClickListener() {
    @Override
    public void onClick(int type) {
        switch (type){
            case ConstantKeys.Gender.LOGIN:
                //调到用户登录页面
                startActivity(MeLoginActivity.class);
                break;
            case ConstantKeys.Gender.MEMBER:
                //调到用户充值会员页面
                startActivity(MeMemberActivity.class);
                break;
            default:
                break;
        }
    }
});
```

##### 2.3.3其他设置，让体验更好，视频播放优化
- **如果是在Activity中的话，建议设置下面这段代码**
    ```
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
    ```
- **如果是在Fragment中的话，建议设置下面这段代码**
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


#### 2.4 注意的问题
##### 2.4.1如果是全屏播放，则需要在清单文件中设置当前activity的属性值**
- android:configChanges 保证了在全屏的时候横竖屏切换不会执行Activity的相关生命周期，打断视频的播放
- android:screenOrientation 固定了屏幕的初始方向
- 这两个变量控制全屏后和退出全屏的屏幕方向
```
	<activity android:name=".ui.test2.TestMyActivity"
		android:configChanges="orientation|keyboardHidden|screenSize"
		android:screenOrientation="portrait"/>
```

#### 2.5 关于开源库中的类说明
- ![image](http://p2mqszpjf.bkt.clouddn.com/ycVideoPlayer1.png)

#### 2.6 暴露各种监听事件接口
##### 2.6.1 返回键监听[有哥们建议加上这个，让用户自己实现返回键逻辑]
```
controller.setOnVideoBackListener(new OnVideoBackListener() {
            @Override
            public void onBackClick() {
                onBackPressed();
            }
        });
```
##### 2.6.2 清晰度切换的监听事件
##### 2.6.3 播放或者暂停的监听事件[比如用户自己处理视频暂停时弹窗广告弹窗]
```
controller.setOnPlayOrPauseListener(new OnPlayOrPauseListener() {
            @Override
            public void onPlayOrPauseClick(boolean isPlaying) {
                
            }
        });
```
##### 2.6.4 会员监听事件[只有设置会员权限才生效，默认没有，可以让用户自己处理登录或者充值会员]
```
controller.setOnMemberClickListener(new OnMemberClickListener() {
            @Override
            public void onClick(int type) {
                switch (type){
                    case ConstantKeys.Gender.LOGIN:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"登录");
                        break;
                    case ConstantKeys.Gender.MEMBER:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"充值会员");
                        break;
                    default:
                        break;
                }
            }
        });
```
##### 2.6.5 顶部栏多功能监听[比如切换音频，分享，下载，更多等按钮的逻辑处理让用户自己处理]
```
controller.setOnVideoControlListener(new OnVideoControlListener() {
            @Override
            public void onVideoControlClick(int type) {
                switch (type){
                    case ConstantKeys.VideoControl.DOWNLOAD:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"下载音视频");
                        break;
                    case ConstantKeys.VideoControl.AUDIO:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"切换音频");
                        break;
                    case ConstantKeys.VideoControl.SHARE:
                        ToastUtil.showToast(VideoPlayerMeActivity.this,"分享内容");
                        break;
                    default:
                        break;
                }
            }
        });
```


### 3.关于播放类型说明
#### 3.1 普通视频播放
- 3.1.1 这一步操作可以直接看第二部分内容——关于使用方法说明

#### 3.2 list页面视频播放
##### 3.2.1如何在list页面设置视频
- 第一步：在activity或者fragment中
    ```
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);
    VideoAdapter adapter = new VideoAdapter(this, DataUtil.getVideoListData());
    recyclerView.setAdapter(adapter);
    //注意：下面这个方法不能漏掉
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


- 第二步：在RecyclerView的适配器Adapter中

    ```
    public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    
        private Context mContext;
        private List<Video> mVideoList;
    
        VideoAdapter(Context context, List<Video> videoList) {
            mContext = context;
            mVideoList = videoList;
        }
    
        @Override
        public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_test_my_video, parent, false);
            VideoViewHolder holder = new VideoViewHolder(itemView);
            //创建视频播放控制器，主要只要创建一次就可以呢
            VideoPlayerController controller = new VideoPlayerController(mContext);
            holder.setController(controller);
            return holder;
        }
    
        @Override
        public void onBindViewHolder(VideoViewHolder holder, int position) {
            Video video = mVideoList.get(position);
            holder.bindData(video);
        }
    
        @Override
        public int getItemCount() {
            return mVideoList==null ? 0 : mVideoList.size();
        }
    
        class VideoViewHolder extends RecyclerView.ViewHolder {
    
            VideoPlayerController mController;
            VideoPlayer mVideoPlayer;
    
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
                mVideoPlayer.setController(mController);
            }
    
            void bindData(Video video) {
                mController.setTitle(video.getTitle());
                mController.setLength(video.getLength());
                Glide.with(itemView.getContext())
                        .load(video.getImageUrl())
                        .placeholder(R.drawable.image_default)
                        .crossFade()
                        .into(mController.imageView());
                mVideoPlayer.setUp(video.getVideoUrl(), null);
            }
        }
    }
    ```

#### 3.3 小窗口视频播放
##### 3.3.1建议在设置小窗口先先判断视频播放器是否开始播放
```
    if (videoPlayer.isIdle()) {
        Toast.makeText(this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
    } else {
        videoPlayer.enterTinyWindow();
    }
```


#### 3.4 类似爱奇艺，优酷会员试看视频播放
##### 3.4.1 可以参考——2.3.2 关于模仿爱奇艺登录会员权限功能代码


#### 3.5 设置竖屏全屏或者横屏全屏模式
```
//设置竖屏方法上的全屏模式
mVideoPlayer.enterVerticalScreenScreen();
//设置横屏方向上的全屏模式
mVideoPlayer.enterFullScreen();
```


#### 3.6 关于封装库中日志打印
##### 3.6.1关于封装库中日志打印设置
- 如果上线产品后不想打印日志，可以在初始化时设置，注意需要在初始化播放器之前设置
    ```
    //如果不想打印库中的日志，可以设置
    VideoLogUtil.isLog = false;
    ```


### 4.关于相关方法说明
#### 4.1 关于VideoPlayer类中方法说明
##### 4.1.1 关于一定需要这四步
```
//设置播放类型
// IjkPlayer or MediaPlayer
videoPlayer1.setPlayerType(VideoPlayer.TYPE_NATIVE);
//设置视频地址和请求头部
videoPlayer1.setUp(videoUrl, null);
//创建视频控制器
VideoPlayerController controller = new VideoPlayerController(this);
//设置视频控制器
videoPlayer1.setController(controller);
```

##### 4.1.2 关于VideoPlayer中设置属性方法
```
//设置播放类型
// MediaPlayer
videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
// IjkPlayer
videoPlayer.setPlayerType(VideoPlayer.TYPE_IJK);
//网络视频地址
String videoUrl = DataUtil.getVideoListData().get(1).getVideoUrl();
//设置视频地址和请求头部
videoPlayer.setUp(videoUrl, null);
//是否从上一次的位置继续播放
videoPlayer.continueFromLastPosition(false);
//设置播放速度
videoPlayer.setSpeed(1.0f);
//设置播放位置
//videoPlayer.seekTo(3000);
//设置音量
videoPlayer.setVolume(50);
//设置竖屏全屏播放
videoPlayer.enterVerticalScreenScreen();
//设置横屏全屏播放
videoPlayer.enterFullScreen();
//设置小屏幕播放
videoPlayer.enterTinyWindow();
//退出全屏
videoPlayer.exitFullScreen();
//退出小窗口播放
videoPlayer.exitTinyWindow();
//释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
videoPlayer.release();
//释放播放器，注意一定要判断对象是否为空，增强严谨性
videoPlayer.releasePlayer();
```

##### 4.1.3 关于VideoPlayer中获取属性方法
```
//是否从上一次的位置继续播放，不必须
videoPlayer.continueFromLastPosition(false);
//获取最大音量
int maxVolume = videoPlayer.getMaxVolume();
//获取音量值
int volume = videoPlayer.getVolume();
//获取持续时长
long duration = videoPlayer.getDuration();
//获取播放位置
long currentPosition = videoPlayer.getCurrentPosition();
//获取缓冲区百分比
int bufferPercentage = videoPlayer.getBufferPercentage();
//获取播放速度
float speed = videoPlayer.getSpeed(1);
```

##### 4.1.4 关于VideoPlayer中设置播放状态方法
```
//开始播放
videoPlayer.start();
//开始播放，从某位置播放
videoPlayer.start(3000);
//重新播放
videoPlayer.restart();
//暂停播放
videoPlayer.pause();
```

##### 4.1.5 关于VideoPlayer中获取播放状态方法
```
//判断是否开始播放
boolean idle = videoPlayer.isIdle();
//判断视频是否播放准备中
boolean preparing = videoPlayer.isPreparing();
//判断视频是否准备就绪
boolean prepared = videoPlayer.isPrepared();
//判断视频是否正在缓冲
boolean bufferingPlaying = videoPlayer.isBufferingPlaying();
//判断是否是否缓冲暂停
boolean bufferingPaused = videoPlayer.isBufferingPaused();
//判断视频是否暂停播放
boolean paused = videoPlayer.isPaused();
//判断视频是否正在播放
boolean playing = videoPlayer.isPlaying();
//判断视频是否播放错误
boolean error = videoPlayer.isError();
//判断视频是否播放完成
boolean completed = videoPlayer.isCompleted();
//判断视频是否播放全屏
boolean fullScreen = videoPlayer.isFullScreen();
//判断视频是否播放小窗口
boolean tinyWindow = videoPlayer.isTinyWindow();
//判断视频是否正常播放
boolean normal = videoPlayer.isNormal();
```

#### 4.2 关于VideoPlayerController类[控制器]中方法说明
##### 4.2.1 关于控制器方法

```
//创建视频控制器
VideoPlayerController controller = new VideoPlayerController(this);
//设置视频标题
controller.setTitle("高仿优酷视频播放页面");
//设置视频时长
//controller.setLength(98000);
//设置视频加载缓冲时加载窗的类型，多种类型
controller.setLoadingType(2);
ArrayList<String> content = new ArrayList<>();
content.add("试看结束，观看全部内容请开通会员1111。");
content.add("试看结束，观看全部内容请开通会员2222。");
content.add("试看结束，观看全部内容请开通会员3333。");
content.add("试看结束，观看全部内容请开通会员4444。");
//设置会员权限话术内容
controller.setMemberContent(content);
//设置不操作后，5秒自动隐藏头部和底部布局
controller.setHideTime(5000);
//设置设置会员权限类型，第一个参数是否登录，第二个参数是否有权限看，第三个参数试看完后展示的文字内容，第四个参数是否保存进度位置
controller.setMemberType(false,false,3,true);
//设置背景图片
controller.imageView().setBackgroundResource(R.color.blackText);
//ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
//设置试看结束后，登录或者充值会员按钮的点击事件
controller.setOnMemberClickListener(new OnMemberClickListener() {
    @Override
    public void onClick(int type) {
        switch (type){
            case ConstantKeys.Gender.LOGIN:
                //调到用户登录也米娜
                startActivity(MeLoginActivity.class);
                break;
            case ConstantKeys.Gender.MEMBER:
                //调到用户充值会员页面
                startActivity(MeMemberActivity.class);
                break;
            default:
                break;
        }
    }
});
//设置视频清晰度
//videoPlayer.setClarity(list,720);
//设置视频控制器
videoPlayer.setController(controller);
```

#### 4.3 关于对象的销毁
##### 4.3.1在VideoPlayer中如何释放资源的呢？源代码如下所示
```
@Override
public void release() {
    // 保存播放位置
    if (isPlaying() || isBufferingPlaying() || isBufferingPaused() || isPaused()) {
        VideoPlayerUtils.savePlayPosition(mContext, mUrl, getCurrentPosition());
    } else if (isCompleted()) {
        //如果播放完成，则保存播放位置为0，也就是初始位置
        VideoPlayerUtils.savePlayPosition(mContext, mUrl, 0);
    }
    // 退出全屏或小窗口
    if (isFullScreen()) {
        exitFullScreen();
    }
    if (isTinyWindow()) {
        exitTinyWindow();
    }
    mCurrentMode = MODE_NORMAL;

    // 释放播放器
    releasePlayer();

    // 恢复控制器
    if (mController != null) {
        mController.reset();
    }
    // gc回收
    Runtime.getRuntime().gc();
}
//释放播放器，注意一定要判断对象是否为空，增强严谨性
@Override
public void releasePlayer() {
    if (mAudioManager != null) {
        //放弃音频焦点。使以前的焦点所有者(如果有的话)接收焦点。
        mAudioManager.abandonAudioFocus(null);
        //置空
        mAudioManager = null;
    }
    if (mMediaPlayer != null) {
        //释放视频焦点
        mMediaPlayer.release();
        mMediaPlayer = null;
    }
    //从视图中移除TextureView
    mContainer.removeView(mTextureView);
    if (mSurface != null) {
        mSurface.release();
        mSurface = null;
    }
    //如果SurfaceTexture不为null，则释放
    if (mSurfaceTexture != null) {
        mSurfaceTexture.release();
        mSurfaceTexture = null;
    }
    //设置状态
    mCurrentState = STATE_IDLE;
}
```


### 5.关于封装的思路
#### 5.1 参考的案例思路
##### 5.1.1目前参考的案例有
- 可以直接看下面的参考案例，有记录
##### 5.1.2针对jiaozi代码简单分析
- JZVideoPlayer为继承自FrameLayout实现的一个组合自定义View来实现了视频播放器的View相关的内容。
- JZVideoPlayerStandard则是继承自JZVideoPlayer实现了一些自身的功能。
- JZMediaManager是用来对于MediaPlayer的管理，对于MediaPlayer的一些监听器方法的回调和TextrueView的相关回调处理。
- JZVideoPlayerManager管理JZVideoPlayer
- 和自定义相关的工作，最主要是先继承JCVideoPlayerStandard
- JZMediaSystem主要是实现系统的播放引擎
- 不得不说，大神封装代码的思路以及代码逻辑的确很强
- **关于封装库其他感受**
- 第一：不过，感觉大神更新频率大高，而且没有找到每次更新的日志说明，不知道大神又解决了那些bug
- 第二：黄色警告多，而且注释少，因为视频封装库不像一般库，有时候需求不同，可拓展性要求高。除了自己继承JCVideoPlayerStandard创建视频播放器，其他如果想改代码，还是有点复杂的。
- 第三：关于使用虽然很简单，但是在JZVideoPlayerStandard这个方法中，布局的对象都是用public修饰，如果你要想自己甚至某个控件背景或者图标等等，则要这样应用。如果你不去看看源代码中布局名称，你根本就不知道这个对象对应的是什么东西。对于不同修饰符，要合适的，如果不合适，那么就会有淡黄色警告。我看了buttonKnife，retrofit，阿里vlayout等等，可以说黄色警告很少……

```
  Picasso.with(this)
                .load("http://jzvd-pic.nathen.cn/jzvd-pic/1bb2ebbe-140d-4e2e-abd2-9e7e564f71ac.png")
                .into(jzVideo.thumbImageView);
```


#### 5.2 封装的基本思路
##### 5.2.1关于简单的思路分析
- a1.可以把视频播放和设置视频属性控制器分离，对于VideoPlayer中，各种UI状态和操作反馈都封装到VideoPlayerController控制器里面。如果需要根据不同的项目需求来修改播放器的功能，就只重写VideoPlayerController就可以了。
- a2.对于VideoPlayer这个类，可以先创建一个帧布局容器，然后在初始化的时候将视频播放器控制器放到里面，然后通过设置控制器来进行视频播放
- a3.当调用了开始播放的方法后，就初始化播放器，包括原生的，还有IjkMediaPlayer
- a4.而基于IjkMediaPlayer的视频播放，需要添加各种监听事件，通过阅读IMediaPlayer源码可以知道：可以在这些监听事件中添加各种对视频的操作逻辑，具体可以看代码。
```
    void setOnPreparedListener(IMediaPlayer.OnPreparedListener var1);
    void setOnCompletionListener(IMediaPlayer.OnCompletionListener var1);
    void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener var1);
    void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener var1);
    void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener var1);
    void setOnErrorListener(IMediaPlayer.OnErrorListener var1);
    void setOnInfoListener(IMediaPlayer.OnInfoListener var1);
    void setOnTimedTextListener(IMediaPlayer.OnTimedTextListener var1);
```
- a5.定义好了监听事件后，就创建了播放，重置播放，暂停等各种方法

#### 5.3 关于窗口切换分析
##### 5.3.1 关于窗口切换调用的代码

```
	//设置全屏播放
	videoPlayer.enterFullScreen();
	//设置小屏幕播放
	videoPlayer.enterTinyWindow();
	//退出全屏
	videoPlayer.exitFullScreen();
	//退出小窗口播放
	videoPlayer.exitTinyWindow();
	//释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
	videoPlayer.release();
	//释放播放器，注意一定要判断对象是否为空，增强严谨性
	videoPlayer.releasePlayer();
```


####  5.4 关于VideoPlayerManager视频播放器管理器分析
##### 5.4.1可以直接看源代码，我对每个方法都有详细的注释

```
public class VideoPlayerManager {

    private VideoPlayer mVideoPlayer;
    private static VideoPlayerManager sInstance;
    private VideoPlayerManager() {}
    //一定要使用单例模式，保证同一时刻只有一个视频在播放，其他的都是初始状态
    public static synchronized VideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new VideoPlayerManager();
        }
        return sInstance;
    }

    public VideoPlayer getCurrentVideoPlayer() {
        return mVideoPlayer;
    }

    void setCurrentVideoPlayer(VideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }
    //当视频正在播放或者正在缓冲时，调用该方法暂停视频
    public void suspendVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }
    //当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放
    public void resumeVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }
    //释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
    public void releaseVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }
     //处理返回键逻辑.如果是全屏，则退出全屏 如果是小窗口，则退出小窗口
    public boolean onBackPressed() {
        if (mVideoPlayer != null) {
            if (mVideoPlayer.isFullScreen()) {
                return mVideoPlayer.exitFullScreen();
            } else if (mVideoPlayer.isTinyWindow()) {
                return mVideoPlayer.exitTinyWindow();
            }
        }
        return false;
    }
}
```

#### 5.5 关于VideoPlayerController视频控制器分析
##### 5.5.1VideoPlayerController的作用**
- 播放控制界面上，播放、暂停、播放进度、缓冲动画、全屏/小屏等触发都是直接调用播放器对应的操作的。
##### 5.5.2VideoPlayerController的方法如下所示

```
	//创建视频控制器
	VideoPlayerController controller = new VideoPlayerController(this);
	//设置视频标题
	controller.setTitle("高仿优酷视频播放页面");
	//设置视频时长
	//controller.setLength(98000);
	//设置视频加载缓冲时加载窗的类型，多种类型
	controller.setLoadingType(2);
	ArrayList<String> content = new ArrayList<>();
	content.add("试看结束，观看全部内容请开通会员1111。");
	content.add("试看结束，观看全部内容请开通会员2222。");
	content.add("试看结束，观看全部内容请开通会员3333。");
	content.add("试看结束，观看全部内容请开通会员4444。");
	//设置会员权限话术内容
	controller.setMemberContent(content);
	//设置不操作后，5秒自动隐藏头部和底部布局
	controller.setHideTime(5000);
	//设置设置会员权限类型，第一个参数是否登录，第二个参数是否有权限看，第三个参数试看完后展示的文字内容，第四个参数是否保存进度位置
	controller.setMemberType(false,false,3,true);
	//设置背景图片
	controller.imageView().setBackgroundResource(R.color.blackText);
	//ImageUtil.loadImgByPicasso(this, R.color.blackText, R.drawable.image_default, controller.imageView());
	//设置试看结束后，登录或者充值会员按钮的点击事件
	controller.setOnMemberClickListener(new OnMemberClickListener() {
		@Override
		public void onClick(int type) {
			switch (type){
				case ConstantKeys.Gender.LOGIN:
					//调到用户登录也米娜
					startActivity(MeLoginActivity.class);
					break;
				case ConstantKeys.Gender.MEMBER:
					//调到用户充值会员页面
					startActivity(MeMemberActivity.class);
					break;
				default:
					break;
			}
		}
	});
	//设置视频清晰度
	//videoPlayer.setClarity(list,720);
	//设置视频控制器
	videoPlayer.setController(controller);
```

#### 5.6 关于InterVideoPlayer接口分析
##### 5.6.1关于此接口方法有[跟jiaozi代码类似]
```
    /**
     * 设置视频Url，以及headers
     *
     * @param url           视频地址，可以是本地，也可以是网络视频
     * @param headers       请求header.
     */
    void setUp(String url, Map<String, String> headers);

    /**
     * 开始播放
     */
    void start();

    /**
     * 从指定的位置开始播放
     *
     * @param position      播放位置
     */
    void start(long position);

    /**
     * 重新播放，播放器被暂停、播放错误、播放完成后，需要调用此方法重新播放
     */
    void restart();

    /**
     * 暂停播放
     */
    void pause();

    /**
     * seek到制定的位置继续播放
     *
     * @param pos 播放位置
     */
    void seekTo(long pos);

    /**
     * 设置音量
     *
     * @param volume 音量值
     */
    void setVolume(int volume);

    /**
     * 设置播放速度，目前只有IjkPlayer有效果，原生MediaPlayer暂不支持
     *
     * @param speed 播放速度
     */
    void setSpeed(float speed);

    /**
     * 开始播放时，是否从上一次的位置继续播放
     *
     * @param continueFromLastPosition true 接着上次的位置继续播放，false从头开始播放
     */
    void continueFromLastPosition(boolean continueFromLastPosition);
```


### 6.关于如何自定义你想要的视频播放模式
#### 6.1 自定义视频播放器
##### 6.1.1如何自定义自己的播放器
- 第一步：首先继承VideoPlayer这个类
- 第二步：然后重写部分你需要更改功能的方法，只需要选择你需要重写的方法即可。


###  7.关于效果图的展示
#### 7.1 效果图如下所示
![image](http://p2mqszpjf.bkt.clouddn.com/ycVideoPlayer2.png)
![image](http://p0u62g00n.bkt.clouddn.com/Screenshot_2018-01-05-13-21-49.jpg)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_2018-01-16-11-22-43.jpg)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_2018-01-16-11-24-31.jpg)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113446.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113706.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113721.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113732.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113802.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-113840.png)
![image](http://p2mqszpjf.bkt.clouddn.com/Screenshot_20180116-135824.png)




### 8.关于遇到的问题说明
#### 8.1 视频难点
- 8.1.1 当视频切换全屏或者从全屏切换到正常小屏幕时，如何管理activity的生命周期
- 8.1.2 在列表list页面，滑动显示小窗口，那么什么时候显示小窗口呢？关于RecyclerView的滑动位移超出屏幕有没有更好的解决办法？
- 8.1.2 当屏幕从全屏退出时，播放位置要滑到记录的位置，代码逻辑复杂，如何避免耦合度太高

#### 8.2 遇到的bug
- 8.2.1 当视频切花时，如何避免视频不卡顿
- 8.2.2 在fragment中，当左右滑动出另一个fragment中，视频还在播放，怎么样处理这部分逻辑
- 8.2.3 在显示缓冲比时，网络不好或者暂停缓冲时有问题，所以暂停还没有添加该功能
- 8.2.4 【已经解决，30毫秒更新一次进度条，只有视频时长很短时才有这种现象】播放进度条seekBar跳动问题，有人反映不是那么顺畅
- 8.2.6 在拖动时显示当前帧的画面图片，类似优酷那个功能，最终还是没有实现



#### 8.3 后期需要实现的功能
- 8.3.1 如果有多集视频，则添加上一集和下一集的功能
- 8.3.2 拖动滑动条，显示帧画面
- 8.3.3 实现弹幕功能
- 8.4.4 有些手机播放有问题，测试找问题
- 8.5.5 切换视频清晰度有问题，是重新开始播放，因为切换清晰度时，调用的视频链接是不同的。比如高清视频和标准视频链接是不同的，所以难以实现切换后记录位置播放。但是看了下优酷，爱奇艺视频，切换后是接着之前观看的位置播放，这个需要思考下怎么实现。欢迎同行给出好的建议。
- 8.5.6 待定


### 9.关于版本更新说明
##### 9.1 V1.0.0 更新于2017年10月4日
> 初期最简单功能
- 9.1.1 支持最简单视频播放，暂停，缓冲，全屏播放等基础功能。
- 9.1.2 支持滑动改变音量，改变声音大小的功能
- 9.1.3 还有其他基本功能
##### 9.2 V1.0.1 更新于2017年11月18日
- 最简单的封装，并且阅读相关视频案例，借鉴了相关思路和复用了部分代码
- 测试环节
##### 9.3 v1.1.0 更新于2018年1月15日
- 9.3.1 添加了设置视频播放权限的功能，用户可以自由设置权限，不过目前只是设置了用户是否登录，和登录用户是否有观看权限，因为公司需求是这样的，所以只有这两个。后期遇到其他需求再添加。逻辑已经在库中写好了，用户自己实现就可以呢。
- 9.3.2 关于权限肯定有话术内容，那么用户可以通过调用接口直接设置展示在播放器试看结束后的内容。十分方便，这块参考了优酷和爱奇艺视频
- 9.3.3 添加了用户多久不操作视频界面后，自动隐藏底部和头部布局视图。如果不设置，默认时间为5秒
- 9.3.4 添加了多种视频加载时候的加载效果，目前有两种，一种是转圈效果，一种是帧动画效果。当然你可以自己添加动画加载效果
##### 9.4 v1.1.1 更新于2018年1月18日
- 9.4.1 修改了视频横向播放时，点击手机物理返回键，画面展示状态栏问题
- 9.4.2 修改了在list页面(recyclerView)的视频，当上拉加载更多时，加载十几次会导致崩溃问题
- 9.4.3 精简了布局文件，方便修改定制和阅读
- 9.4.4 修改了在网络不好或者飞行模式下，用户播放视频，应该是播放错误而不是一直转圈加载问题
- 9.4.5 完善了代码的注释，现在几乎所有的方法都有相关注释，方便阅读和理解。去掉了无用的代码
- 9.4.6 添加了暴露接口之用户登录和用户购买会员的接口，用户可以自己实现监听之后的操作或者跳转页面
- 9.4.7 添加了视频左上方的返回键监听，用户可以自己实现返回逻辑
- 9.4.8 添加了锁定屏幕方向的功能，还在测试中，有点问题
##### 9.5 v2.4.5 更新于2018年4月21日
- 9.5.0 说明：全屏模式下，滑动屏幕左边改变亮度，滑动屏幕右边改变声音
- 9.5.1 触摸滑动事件中，优化了只有全屏的时候才能拖动位置、亮度、声音
- 9.5.2 优化了只有在播放，暂停，缓冲的时候才能改变亮度，声音，和拖动位置
- 9.5.3 滑动改变亮度，声音和拖动位置时，隐藏控制器中间播放位置变化图，亮度变化视图和音量变化视图
##### 9.6 v2.4.6 更新于2018年8月2日
- 9.6.1 添加了竖屏下的全屏播放模式
- 9.6.2 解决了横屏下全屏播放模式的导航栏显示问题
##### 9.7 v2.4.7 更新于2018年8月12日
- 9.7.1 添加了锁屏的功能，锁屏时，返回键不做任何处理，并且隐藏top和bottom面版控件
- 9.7.2 优化了全屏播放视频时，左右滑动可以设置快进和快退的功能
- 9.7.3 优化了播放视频中，没有网络，点击重试按钮提示用户检查网络是否异常吐司
- 9.7.4 注册一个网络变化监听广播，在网络变更时进行对应处理，从有网切换到没有网络时，切换播放状态
- 9.7.5 修改播放异常条件下，还有声音播放的问题
##### 9.0.9 v2.5.0 更新与2018年8月20日
- 9.0.9.1 通过设置注解限制部分方法传入值类型，避免用户传入值导致意外情况
- 9.0.9.2 初步写了小窗口视频拖拽功能，在下一个版本上该功能
- 9.0.9.3 修改了正常窗口和全屏切换时，状态栏显示的问题
- 9.0.9.4 优化了播放和暂停的监听事件，将listener暴露给开发者，可以让开发者处理某些逻辑，比如暂停时弹出广告
##### 9.1.0 v2.6.0 更新于2018年9月25日




### 10.关于参考文档说明
#### 10.1 参考的项目
##### 10.1.1参考的开源项目有
```
https://github.com/CarGuo/GSYVideoPlayer
https://github.com/danylovolokh/VideoPlayerManager
https://github.com/HotBitmapGG/bilibili-android-client
https://github.com/jjdxmashl/jjdxm_ijkplayer
https://github.com/JasonChow1989/JieCaoVideoPlayer-develop          2年前
https://github.com/open-android/JieCaoVideoPlayer                   1年前
https://github.com/lipangit/JiaoZiVideoPlayer                       4个月前
https://github.com/xiaoyanger0825/NiceVieoPlayer
https://github.com/dueeeke/dkplayer
https://github.com/curtis2/SuperVideoPlayer
https://github.com/tcking/GiraffePlayer
https://github.com/jiajunhui/PlayerBase
```

#### 10.2 参考的博客
##### 10.2.1参考的博客有
```
https://segmentfault.com/a/1190000011959615
http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1213/2153.html
http://blog.csdn.net/junwang19891012/article/details/8444743
https://www.jianshu.com/p/420f7b14d6f6
http://blog.csdn.net/candicelijx/article/details/39495271
```


### 11.关其他说明
#### 11.1 目前市场流行的视频框架
- 1.Android原生VideoView
- 2.Google 开源视频播放框架 ExoPlayer
- 3.Vitamio 视频播放框架
- 4.Bilibili 开源视频播放框架ijkplayer


#### 11.2 如何选择
- **11.2.1.Android原生VideoView**
* 1.1 VideoView 的使用非常简单，播放视频的步骤：
	* 在界面布局文件中定义 VideoView 组件，或在程序中创建 VideoView 组件
	* 调用 VideoView 的如下两个方法来加载指定的视频：
		* setVidePath(String path)：加载 path 文件代表的视频
		* setVideoURI(Uri uri)：加载 uri 所对应的视频
	* 调用 VideoView 的 start()、stop()、psuse() 方法来控制视频的播放


- **11.2.2.Google 开源视频播放框架 ExoPlayer**
* 2.1 框架地址：https://github.com/google/ExoPlayer
* 2.2 用法
	* ExoPlayer 开源项目包含了 library 和 示例：
		* ExoPlayer library – 这部分是核心的库
		* Demo app – 这部分是演示怎么使用 ExoPlayer 的 Demo
	* ExoPlayer 库的核心类是 ExoPlayer 类。该类维护了播放器的全局状态 。比如如何获取媒体数据，如何缓冲以及是怎样的编码格式。
	* ExoPlayer 基于 MediaCodec 和 AudioTrack 提供了默认的音视频的 TrackRenderer 实现。所有的 renderers 都需要 SampleSource 对象，ExoPlayer 从 SampleSource 获得 media samples 用于播放。下图展示了 ExoPlayer 是如何配置组合这些组件用于播放音视频的。
	* standard-model
	* ExoPlayer 库提供了一些不同类型的 SampleSource 实例：
	* ExtractorSampleSource – 用于 MP3，M4A，WebM，MPEG-TS 和 AAC；
		* ChunkSampleSource – 用于 DASH 和平滑流的播放；
		* HlsSampleSource – 用于 HLS 播放；
	* 在 ExoPlayer 的 Dome 中使用 DemoPlayer 对 ExoPlayer 进行了封装，并提供了使用上述几种 SampleSource 构建 TrackRenderer 的 Builder。
		* SmoothStreamingRendererBuilder
		* DashRendererBuilder
		* ExtractorRendererBuilder
	* 在使用的时候我们根据不同的需求创建对应的 RendererBuilder，然后将 RendererBuilder 传递给 DemoPlayer 然后调用 DemoPlayer 的 setPlayWhenReady 方法。
* 2.3 优缺点
* ExoPlayer 相较于 MediaPlayer 有很多很多的优点：
	* 支持动态的自适应流 HTTP (DASH) 和 平滑流，任何目前 MediaPlayer 支持的视频格式（同时它还支持 HTTP 直播(HLS)，MP4，MP3，WebM，M4A，MPEG-TS 和 AAC）。
	* 支持高级的 HLS 特性，例如正确处理 EXT-X-DISCONTINUITY 标签；
	* 支持自定义和扩治你的使用场景。ExoPlayer 专门为此设计；
	* 便于随着 App 的升级而升级。因为 ExoPlayer 是一个包含在你的应用中的库，对于你使用哪个版本有完全的控制权，并且你可以简单的跟随应用的升级而升级；
	* 更少的适配性问题。
* ExoPlayer 的缺点：
	* ExoPlayer 的音频和视频组件依赖 Android 的 MediaCodec 接口，该接口发布于 Android4.1（API 等级 16）。因此它不能工作于之前的Android 版本。



- **11.2.3.Vitamio 视频播放框架**
* 3.1 用法
* 官网：https://www.vitamio.org
* Vitamio 的使用步骤：
	* 1.下载 Vitamio 库，并作为工程依赖。
	* 2.在 Activity 的 onCreate 方法中添加如下代码，初始化 Vitamio 的解码器
* 3.2 优点
	* 强大，支持超多格式视频和网络视频播放。
	* 使用简单。调用非常简单，方便使用。
	* 其官方还给出了其他很多优点，但是个人觉得不足以成为优点。


- **11.2.4.Bilibili 开源视频播放框架ijkplayer**
* 4.1 特点
	* HTTPS支持
	* 支持弹幕
	* 支持基本的拖动，声音、亮度调节
	* 支持边播边缓存
	* 支持视频本身自带rotation的旋转（90,270之类），重力旋转与手动旋转的同步支持
	* 支持列表播放，直接添加控件为封面，列表全屏动画，视频加载速度，列表小窗口支持拖动
	* 5.0的过场效果，调整比例，多分辨率切换
	* 支持切换播放器，进度条小窗口预览
	* 其他一些小动画效果，rtsp、concat、mpeg
* 4.2  优缺点
	* ijkplayer 最大的优点就是可以根据需要编译需要的解码器。在编译的时候通过 ln -s module-default.sh module.sh 选择要编译的解码器。ijkplayer 在 config 目录下提供了三种 module.sh 。也可自己修改 module.sh 。
	* ijkplayer 的缺点是库太大。加入项目后会大大增加你的 APP 的大小。


#### 11.3 关于我的个人博客和站点
- **github：** [https://github.com/yangchong211](https://github.com/yangchong211)
- **知乎：** [https://www.zhihu.com/people/yang-chong-69-24/pins/posts](https://www.zhihu.com/people/yang-chong-69-24/pins/posts)
- **简书：** [http://www.jianshu.com/u/b7b2c6ed9284](http://www.jianshu.com/u/b7b2c6ed9284)
- **csdn：** [http://my.csdn.net/m0_37700275](http://my.csdn.net/m0_37700275)
- **喜马拉雅听书：** [http://www.ximalaya.com/zhubo/71989305/](http://www.ximalaya.com/zhubo/71989305/)
- 泡在网上的日子：[http://www.jcodecraeer.com/member/content_list.php?channelid=1](http://www.jcodecraeer.com/member/content_list.php?channelid=1)
- 邮箱：yangchong211@163.com
- 阿里云博客：[https://yq.aliyun.com/users/article?spm=5176.100239.headeruserinfo.3.dT4bcV](https://yq.aliyun.com/users/article?spm=5176.100239.headeruserinfo.3.dT4bcV)


