# 视频播放器介绍文档
#### 目录介绍
- 01.该视频播放器介绍
- 02.视频播放器功能
- 03.视频播放器结构说明
- 04.视频播放器如何使用
- 05.播放器详细Api文档
- 06.播放器封装思路
- 07.播放器示例展示图
- 08.添加自定义视图
- 09.视频优化处理
- 10.播放器问题记录说明
- 11.性能优化和库大小
- 12.视频缓存原理介绍
- 13.查看视频播放器日志
- 14.该库异常code说明
- 15.该库系列wiki文档
- 16.版本更新文档记录



### 01.该视频播放器介绍
#### 1.1 该库说明
|**播放器功能** | MediaPlayer | ExoPlayer | IjkPlayer | RTC | TXPlayer |
|--------   |-----        |---        |---        |---  |---     |
|**UI/Player/业务解耦**| 支持  |支持   |支持   |   |   |
|**切换视频播放模式** | 支持     |支持   |支持   |   |   |
|**视频无缝切换** | 支持     |支持   |支持   |   |   |
|**调节播放进度** | 支持     |支持   |支持   |   |   |
|**网络环境监听**| 支持  |支持   |支持   |   |   |
|**滑动改变亮度/声音** | 支持     |支持   |支持   |   |   |
|**设置视频播放比例** | 支持     |支持   |支持   |   |   |
|**自由切换视频内核** | 支持     |支持   |支持   |   |   |
|**记录播放位置** | 支持     |支持   |支持   |   |   |
|**清晰度模式切换** | 支持     |支持   |支持   |   |   |
|**重力感应自动进入** | 支持     |支持   |支持   |   |   |
|**锁定屏幕功能** | 支持     |支持   |支持   |   |   |
|**倍速播放** | 不支持  |支持   |支持   |   |   |
|**视频小窗口播放** | 支持  |支持   |支持   |   |   |
|**列表小窗口播放** | 支持  |支持   |支持   |   |   |
|**边播边缓存**| 支持  |支持   |支持   |   |   |
|**同时播放多个视频**| 支持  |支持   |支持   |   |   |
|**仿快手预加载**| 支持  |支持   |支持   |   |   |
|**基于内核无UI**| 支持  |支持   |支持   |   |   |
|**添加弹幕**| 支持  |支持   |支持   |   |   |
|**全屏显示电量**| 支持  |支持   |支持   |   |   |



### 1.2 该库功能说明
|**类型** | 功能说明 |
|--------   |-----        |
|**项目结构** | VideoCache缓存lib，VideoKernel视频内核lib，VideoPlayer视频UIlib |
|**内核** | MediaPlayer、ExoPlayer、IjkPlayer，后期接入Rtc和TXPlayer |
|**协议/格式** | http/https、concat、rtsp、hls、rtmp、file、m3u8、mkv、webm、mp3、mp4等 |
|**画面** | 调整显示比例:默认、16:9、4:3、填充；播放时旋转画面角度（0,90,180,270）；镜像旋转 |
|**布局** | 内核和UI分离，和市面GitHub上大多数播放器不一样，方便定制，通过addView添加 |
|**播放** | 正常播放，小窗播放，列表播放，仿抖音播放 |
|**自定义** | 可以自定义添加视频UI层，可以说UI和Player高度分离，支持自定义渲染层SurfaceView |





### 02.视频播放器功能
- A基础功能
    - A.1.1 能够自定义视频加载loading类型，设置视频标题，设置视频底部图片，设置播放时长等基础功能
    - A.1.2 可以切换播放器的视频播放状态，播放错误，播放未开始，播放开始，播放准备中，正在播放，暂停播放，正在缓冲等等状态
    - A.1.3 可以自由设置播放器的播放模式，比如，正常播放，全屏播放，和小屏幕播放。其中全屏播放支持旋转屏幕。
    - A.1.4 可以支持多种视频播放类型，比如，原生封装视频播放器，还有基于ijkPlayer封装的播放器。
    - A.1.5 可以设置是否隐藏播放音量，播放进度，播放亮度等，可以通过拖动seekBar改变视频进度。还支持设置n秒后不操作则隐藏头部和顶部布局功能
    - A.1.6 可以设置竖屏模式下全屏模式和横屏模式下的全屏模式，方便多种使用场景
    - A.1.7 top和bottom面版消失和显示：点击视频画面会显示、隐藏操作面板；显示后不操作会5秒后自动消失【也可以设置n秒消失时间】
- B高级功能
    - B.1.1 支持一遍播放一遍缓冲的功能，其中缓冲包括两部分，第一种是播放过程中缓冲，第二种是暂停过程中缓冲
    - B.1.2 基于ijkPlayer，ExoPlayer，Rtc，原生MediaPlayer等的封装播放器，支持多种格式视频播放
    - B.1.3 可以设置是否记录播放位置，设置播放速度，设置屏幕比例
    - B.1.4 支持滑动改变音量【屏幕右边】，改变屏幕亮度【屏幕左边】，屏幕底测左右滑动调节进度
    - B.1.5 支持list页面中视频播放，滚动后暂停播放，播放可以自由设置是否记录状态。并且还支持删除视频播放位置状态。
    - B.1.6 切换横竖屏：切换全屏时，隐藏状态栏，显示自定义top(显示电量)；竖屏时恢复原有状态
    - B.1.7 支持切换视频清晰度模式
    - B.1.8 添加锁屏功能，竖屏不提供锁屏按钮，横屏全屏时显示，并且锁屏时，屏蔽手势处理
- C拓展功能【这块根据实际情况选择是否需要使用，一般视频付费App会有这个工鞥】
    - C1产品需求：类似优酷，爱奇艺视频播放器部分逻辑。比如如果用户没有登录也没有看视频权限，则提示试看视频[自定义布局]；如果用户没有登录但是有看视频权限，则正常观看；如果用户登录，但是没有充值会员，部分需要权限视频则进入试看模式，试看结束后弹出充值会员界面；如果用户余额不足，比如余额只有99元，但是视频观看要199元，则又有其他提示。
    - C2自身需求：比如封装好了视频播放库，那么点击视频上登录按钮则跳到登录页面；点击充值会员页面也跳到充值页面。这个通过定义接口，可以让使用者通过方法调用，灵活处理点击事件。
    - C.1.1 可以设置试看模式，设置试看时长。试看结束后就提示登录或者充值……
    - C.1.2 对于设置视频的宽高，建议设置成4：3或者16：9或者常用比例，如果不是常用比例，则可能会有黑边。其中黑边的背景可以设置
    - C.1.3 可以设置播放有权限的视频时的各种文字描述，而没有把它写在封装库中，使用者自己设定
    - C.1.4 锁定屏幕功能，这个参考大部分播放器，只有在全屏模式下才会有




### 03.视频播放器结构说明
- 视频常见的布局视图
    - 视频底图(用于显示初始化视频时的封面图)，视频状态视图【加载loading，播放异常，加载视频失败，播放完成等】
    - 改变亮度和声音【改变声音视图，改变亮度视图】，改变视频快进和快退，左右滑动快进和快退视图(手势滑动的快进快退提示框)
    - 顶部控制区视图(包含返回健，title等)，底部控制区视图(包含进度条，播放暂停，时间，切换全屏等)
    - 锁屏布局视图(全屏时展示，其他隐藏)，底部播放进度条视图(很多播放器都有这个)，清晰度列表视图(切换清晰度弹窗)
- 后期可能涉及的布局视图
    - 手势指导页面(有些播放器有新手指导功能)，离线下载的界面(该界面中包含下载列表, 列表的item编辑(全选, 删除))
    - 用户从wifi切换到4g网络，提示网络切换弹窗界面(当网络由wifi变为4g的时候会显示)
    - 图片广告视图(带有倒计时消失)，开始视频广告视图，非会员试看视图
    - 弹幕视图(这个很重要)，水印显示视图，倍速播放界面(用于控制倍速)，底部视频列表缩略图视图
    - 投屏视频视图界面，视频直播间刷礼物界面，老师开课界面，展示更多视图(下载，分享，切换音频等)
- 需要达到的目的和效果
    - 基础封装视频播放器player，可以在ExoPlayer、MediaPlayer，声网RTC视频播放器内核，原生MediaPlayer可以自由切换
    - 对于视图状态切换和后期维护拓展，避免功能和业务出现耦合。比如需要支持播放器UI高度定制，而不是该lib库中UI代码
    - 针对视频播放，视频投屏，音频播放，播放回放，以及视频直播的功能
- 视频分层
    - 播放器内核
        - 可以切换ExoPlayer、MediaPlayer，IjkPlayer，声网视频播放器，这里使用工厂模式Factory + AbstractVideoPlayer + 各个实现AbstractVideoPlayer抽象类的播放器类
        - 定义抽象的播放器，主要包含视频初始化，设置，状态设置，以及播放监听。由于每个内核播放器api可能不一样，所以这里需要实现AbstractVideoPlayer抽象类的播放器类，方便后期统一调用
        - 为了方便创建不同内核player，所以需要创建一个PlayerFactory，定义一个createPlayer创建播放器的抽象方法，然后各个内核都实现它，各自创建自己的播放器
    - VideoPlayer播放器
        - 可以自由切换视频内核，Player+Controller。player负责播放的逻辑，Controller负责视图相关的逻辑，两者之间用接口进行通信
        - 针对Controller，需要定义一个接口，主要负责视图UI处理逻辑，支持添加各种自定义视图View【统一实现自定义接口Control】，每个view尽量保证功能单一性，最后通过addView形式添加进来
        - 针对Player，需要定义一个接口，主要负责视频播放处理逻辑，比如视频播放，暂停，设置播放进度，设置视频链接，切换播放模式等操作。需要注意把Controller设置到Player里面，两者之间通过接口交互
    - UI控制器视图
        - 定义一个BaseVideoController类，这个主要是集成各种事件的处理逻辑，比如播放器状态改变，控制视图隐藏和显示，播放进度改变，锁定状态改变，设备方向监听等等操作
        - 定义一个view的接口InterControlView，在这里类里定义绑定视图，视图隐藏和显示，播放状态，播放模式，播放进度，锁屏等操作。这个每个实现类则都可以拿到这些属性呢
        - 在BaseVideoController中使用LinkedHashMap保存每个自定义view视图，添加则put进来后然后通过addView将视图添加到该控制器中，这样非常方便添加自定义视图
        - 播放器切换状态需要改变Controller视图，比如视频异常则需要显示异常视图view，则它们之间的交互是通过ControlWrapper(同时实现Controller接口和Player接口)实现


### 04.视频播放器如何使用
#### 4.1 关于gradle引用说明
- 如下所示
    ```
    
    ```

#### 4.2 在xml中添加布局
- 注意，在实际开发中，由于Android手机碎片化比较严重，分辨率太多了，建议灵活设置布局的宽高比为4：3或者16：9或者你认为合适的，可以用代码设置。
- 如果宽高比变形，则会有黑边
    ```
    <org.yczbj.ycvideoplayerlib.player.VideoPlayer
        android:id="@+id/video_player"
        android:layout_width="match_parent"
        android:layout_height="240dp"/>
    ```

#### 4.3 最简单的视频播放器参数设定
- 如下所示
    ```
    //创建基础视频播放器，一般播放器的功能
    BasisVideoController controller = new BasisVideoController(this);
    //设置控制器
    mVideoPlayer.setVideoController(controller);
    //设置视频播放链接地址
    mVideoPlayer.setUrl(url);
    //开始播放
    mVideoPlayer.start();
    ```

#### 4.4 注意问题
- 如果是全屏播放，则需要在清单文件中设置当前activity的属性值
    - android:configChanges 保证了在全屏的时候横竖屏切换不会执行Activity的相关生命周期，打断视频的播放
    - android:screenOrientation 固定了屏幕的初始方向
    - 这两个变量控制全屏后和退出全屏的屏幕方向
        ```
            <activity android:name=".VideoActivity"
                android:configChanges="orientation|keyboardHidden|screenSize"
                android:screenOrientation="portrait"/>
        ```
- 如何一进入页面就开始播放视频，稍微延时一下即可
    - 代码如下所示，注意避免直接start()，因为有可能视频还没有初始化完成……
        ```
        mVideoPlayer.postDelayed(new Runnable() {
            @Override
            public void run() {
                mVideoPlayer.start();
            }
        },300);
        ```



### 05.播放器详细Api文档
- 01.最简单的播放
- 02.如何切换视频内核
- 03.切换视频模式
- 04.切换视频清晰度
- 05.视频播放监听
- 06.列表中播放处理
- 07.悬浮窗口播放
- 08.其他重要功能Api
- 09.播放多个视频
- 10.VideoPlayer相关Api
- 11.Controller相关Api
- 12.仿快手播放视频
- 具体看这篇文档：[视频播放器Api说明]()



### 06.播放器封装思路
#### 6.1视频层级示例图
![image](https://img-blog.csdnimg.cn/20201012215233584.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)

#### 6.2 视频播放器流程图
- 待完善

#### 6.3 视频播放器lib库
![image](https://img-blog.csdnimg.cn/20201013092150588.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)

#### 6.4 视频内核lib库介绍
![image](https://img-blog.csdnimg.cn/2020101309293329.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)

#### 6.5视频播放器UI库介绍
![image](https://img-blog.csdnimg.cn/20201013094115174.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)


### 07.播放器示例展示图
![image](https://img-blog.csdnimg.cn/20201013091432693.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432695.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432667.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432667.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432625.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432602.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432603.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432616.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432581.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201013091432668.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)



### 08.添加自定义视图
- 比如，现在有个业务需求，需要在视频播放器刚开始添加一个广告视图，等待广告倒计时120秒后，直接进入播放视频逻辑。相信这个业务场景很常见，大家都碰到过，使用该播放器就特别简单，代码如下所示：
- 首先创建一个自定义view，需要实现InterControlView接口，重写该接口中所有抽象方法，这里省略了很多代码，具体看demo。
    ``` java
    public class AdControlView extends FrameLayout implements InterControlView, View.OnClickListener {
    
        private ControlWrapper mControlWrapper;
        public AdControlView(@NonNull Context context) {
            super(context);
            init(context);
        }
    
        private void init(Context context){
            LayoutInflater.from(getContext()).inflate(R.layout.layout_ad_control_view, this, true);
        }
       
        @Override
        public void onPlayStateChanged(int playState) {
            switch (playState) {
                case ConstantKeys.CurrentState.STATE_PLAYING:
                    mControlWrapper.startProgress();
                    mPlayButton.setSelected(true);
                    break;
                case ConstantKeys.CurrentState.STATE_PAUSED:
                    mPlayButton.setSelected(false);
                    break;
            }
        }
    
        @Override
        public void onPlayerStateChanged(int playerState) {
            switch (playerState) {
                case ConstantKeys.PlayMode.MODE_NORMAL:
                    mBack.setVisibility(GONE);
                    mFullScreen.setSelected(false);
                    break;
                case ConstantKeys.PlayMode.MODE_FULL_SCREEN:
                    mBack.setVisibility(VISIBLE);
                    mFullScreen.setSelected(true);
                    break;
            }
            //暂未实现全面屏适配逻辑，需要你自己补全
        }
    }
    ```
- 然后该怎么使用这个自定义view呢？很简单，在之前基础上，通过控制器对象add进来即可，代码如下所示
    ``` java
    controller = new BasisVideoController(this);
    AdControlView adControlView = new AdControlView(this);
    adControlView.setListener(new AdControlView.AdControlListener() {
        @Override
        public void onAdClick() {
            BaseToast.showRoundRectToast( "广告点击跳转");
        }
    
        @Override
        public void onSkipAd() {
            playVideo();
        }
    });
    controller.addControlComponent(adControlView);
    //设置控制器
    mVideoPlayer.setController(controller);
    mVideoPlayer.setUrl(proxyUrl);
    mVideoPlayer.start();
    ```


### 09.视频优化处理
- **如果是在Activity中的话，建议设置下面这段代码**
    ```
    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoPlayer != null) {
            //从后台切换到前台，当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放
            mVideoPlayer.resume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoPlayer != null) {
            //从前台切到后台，当视频正在播放或者正在缓冲时，调用该方法暂停视频
            mVideoPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoPlayer != null) {
            //销毁页面，释放，内部的播放器被释放掉，同时如果在全屏、小窗口模式下都会退出
            mVideoPlayer.release();
        }
    }

    @Override
    public void onBackPressed() {
        //处理返回键逻辑；如果是全屏，则退出全屏；如果是小窗口，则退出小窗口
        if (mVideoPlayer == null || !mVideoPlayer.onBackPressed()) {
            super.onBackPressed();
        }
    }
    ```


### 10.播放器问题记录说明


### 11.性能优化和库大小


### 12.视频缓存原理介绍


### 13.查看视频播放器日志
- 统一管理视频播放器封装库日志，方便后期排查问题
    - 比如，视频内核，日志过滤则是：aaa
    - 比如，视频player，日志过滤则是：bbb



### 14.该库异常code说明
- 针对视频封装库，统一处理抛出的异常，为了方便开发者快速知道异常的来由，则可以查询约定的code码。这个在sdk中特别常见，因此该库一定程度是借鉴腾讯播放器……



### 15.该库系列wiki文档



### 16.版本更新文档记录














