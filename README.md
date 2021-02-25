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
- 09.视频播放器优化处理
- 10.播放器问题记录说明
- 11.性能优化和库大小
- 13.查看视频播放器日志
- 14.该库异常code说明
- 15.该库系列wiki文档
- 16.版本更新文档记录



### 00.视频播放器通用框架
- 基础封装视频播放器player，可以在ExoPlayer、MediaPlayer，声网RTC视频播放器内核，原生MediaPlayer可以自由切换
- 对于视图状态切换和后期维护拓展，避免功能和业务出现耦合。比如需要支持播放器UI高度定制，而不是该lib库中UI代码
- 针对视频播放，音频播放，播放回放，音视频切换的功能。使用简单，代码拓展性强，封装性好，主要是和业务彻底解耦，暴露接口监听给开发者处理业务具体逻辑
- 该播放器整体架构：播放器内核(自由切换) +  视频播放器 + 边播边缓存 + 高度定制播放器UI视图层



### 01.该视频播放器介绍
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
|**视频播放位置本地记录**| 支持  |支持   |支持   |   |   |



### 02.视频播放器功能
|**类型** | 功能说明 |
|--------   |-----        |
|**项目结构** | VideoCache缓存lib，VideoKernel视频内核lib，VideoPlayer视频UI等lib ， VideoSqlLite视频位置二级缓存， VideoView视频悬浮lib ， VideoM3u8下载m3u8以及合成库lib |
|**内核** | MediaPlayer、ExoPlayer、IjkPlayer，后期接入Rtc和TXPlayer |
|**协议/格式** | http/https、concat、rtsp、hls、rtmp、file、m3u8、mkv、webm、mp3、mp4等 |
|**画面** | 调整显示比例:默认、16:9、4:3、填充；播放时旋转画面角度（0,90,180,270）；镜像旋转 |
|**布局** | 内核和UI分离，和市面GitHub上大多数播放器不一样，方便定制，通过addView添加 |
|**播放** | 正常播放，小窗播放，列表播放，仿抖音播放 |
|**自定义** | 可以自定义添加视频UI层，可以说UI和Player高度分离，支持自定义渲染层SurfaceView |
|**统一视频埋点** | 暴露用户播放视频开始，退出，异常，播放完成，以及退出视频时进度，点击广告，试看等多个统一埋点 |
|**视频播放位置本地记录** | 本地可以记录播放视频的播放位置，采用二级缓存模式：内存缓存 + 磁盘缓存 + key缓存 + 配置缓存大小和类型和路径|



### 03.视频播放器结构说明
#### 3.1 视频播放器架构图
![image](https://img-blog.csdnimg.cn/20201016173604612.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)


#### 3.2 播放器视图分类
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


#### 3.3 播放器痛点
- 播放器内核难以切换
    - 不同的视频播放器内核，由于api不一样，所以难以切换操作。要是想兼容内核切换，就必须自己制定一个视频接口+实现类的播放器
- 播放器内核和UI层耦合
    - 也就是说视频player和ui操作柔和到了一起，尤其是两者之间的交互。比如播放中需要更新UI进度条，播放异常需要显示异常UI，都比较难处理播放器状态变化更新UI操作
- UI难以自定义或者修改麻烦
    - 比如常见的视频播放器，会把视频各种视图写到xml中，这种方式在后期代码会很大，而且改动一个小的布局，则会影响大。这样到后期往往只敢加代码，而不敢删除代码……
    - 有时候难以适应新的场景，比如添加一个播放广告，老师开课，或者视频引导业务需求，则需要到播放器中写一堆业务代码。迭代到后期，违背了开闭原则，视频播放器需要做到和业务分离
- 视频播放器结构不清晰
    - 这个是指该视频播放器能否看了文档后快速上手，知道封装的大概流程。方便后期他人修改和维护，因此需要将视频播放器功能分离。比如切换内核+视频播放器(player+controller+view)



#### 3.4 播放器达到的目的
- 需要达到的目的和效果
    - 基础封装视频播放器player，可以在ExoPlayer、MediaPlayer，声网RTC视频播放器内核，原生MediaPlayer可以自由切换
    - 对于视图状态切换和后期维护拓展，避免功能和业务出现耦合。比如需要支持播放器UI高度定制，而不是该lib库中UI代码
    - 针对视频播放，视频投屏，音频播放，播放回放，以及视频直播的功能
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
    //视频UI层，必须要有
    implementation 'cn.yc:VideoPlayer:3.1.0'
    //视频缓存，如果不需要则可以不依赖
    implementation 'cn.yc:VideoCache:3.0.5'
    //视频内核层，必须有
    implementation 'cn.yc:VideoKernel:3.0.6'
    //视频播放器播放位置记录，选择性添加
    implementation 'cn.yc:VideoSqlLite:1.0.2'
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


#### 4.5 关于视频播放位置本地记录
- 播放视频位置常见操作
    - 最好是服务端记录播放位置……常见的视频播放器把播放位置保存到服务端，这样用户换了设备则也可以拉下数据，或者卸载再安装也可以拉下服务端播放位置数据。
- 为何有该需求
    - 主要是公司开发多个定制平板教育app，由于服务端没有做视频播放位置存储功能，而且教育类卖的是设备+教育app(launcher应用)，更换设备可能性小，为完成任务最后采用本地记录视频播放位置。
- **如何做技术选型**
    - 采用二级缓存，内存缓存和磁盘缓存。关于磁盘缓存，刚开始想着使用sql或者greenDao或者realm数据库，考虑到做成封装库，故要求体积小，尽量不依赖三方库还要效率高，因此磁盘缓存采用DiskLruCache。具体使用看api文档……



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
- 具体看这篇文档：[视频播放器Api说明](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/03.%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E5%99%A8Api%E8%AF%B4%E6%98%8E.md)



### 06.播放器封装思路
#### 6.1视频层级示例图
![image](https://img-blog.csdnimg.cn/20201012215233584.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)


#### 6.2 视频播放器流程图
- 待完善


#### 6.3 视频播放器lib库
![image](https://img-blog.csdnimg.cn/20201013092150588.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)


#### 6.4 视频内核lib库介绍
![image](https://img-blog.csdnimg.cn/2020101309293329.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/2020101321464162.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)




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
![image](https://img-blog.csdnimg.cn/20201016132752350.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)
![image](https://img-blog.csdnimg.cn/20201016132752342.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L20wXzM3NzAwMjc1,size_16,color_FFFFFF,t_70#pic_center)



### 08.添加自定义视图
- 如何兼容不同内核播放器
    - 具体看这篇文章：[06.播放器UI抽取封装](https://juejin.im/post/6884028627697500167#heading-9)



### 09.视频播放器优化处理
- 如何兼容不同内核播放器
    - 具体看这篇文章：[05.视频播放器内核切换封装](https://juejin.im/post/6884023527473856520)
- 播放器UI抽取封装优化
    - 具体看这篇文章：[06.播放器UI抽取封装](https://juejin.im/post/6884028627697500167)
- 视频缓存原理介绍
    - 具体看这篇文章：[12.视频边播边缓存分析](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/12.%E8%A7%86%E9%A2%91%E8%BE%B9%E6%92%AD%E8%BE%B9%E7%BC%93%E5%AD%98%E5%88%86%E6%9E%90.md)
- 如何全局监控视频埋点
    - 具体看这篇文章：[29.视频播放器埋点监听](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/29.%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E5%99%A8%E5%9F%8B%E7%82%B9%E7%9B%91%E5%90%AC.md)
- 代码方面优化措施
    - 具体看这篇文章：[08.视频播放器优化处理](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/08.%E8%A7%86%E9%A2%91%E6%92%AD%E6%94%BE%E5%99%A8%E4%BC%98%E5%8C%96%E5%A4%84%E7%90%86.md)



### 10.播放器问题记录说明
- 关于如何调整视频的播放填充类型。在该库中提供了6中不同类型供你选择，即正常默认类型；16：9类型，4：3类型；充满整个控件视图；剧中裁剪类型等类型，就是模仿了图片设置缩放的方式。其实这个就是设置SurfaceView的宽高……
    - 这里播放正常视频建议选择16：9类型的，缩放后会有留黑；针对类似快手抖音视频，一个页面一个视频建议选择充满整个控件视图，会裁剪但是会铺满视频。
- 关于前后台切换视频问题
    - 从前台切到后台，当视频正在播放或者正在缓冲时，调用该方法暂停视频。从后台切换到前台，当视频暂停时或者缓冲暂停时，调用该方法重新开启视频播放。也可以让用户手动去点击播放视频。
- 播放器在正常播放和全屏模式切换状态栏问题
    - 待完善，需要处理刘海



### 11.性能优化和库大小



### 13.查看视频播放器日志
- 统一管理视频播放器封装库日志，方便后期排查问题
    - 比如，视频内核，日志过滤则是：aaa
    - 比如，视频player，日志过滤则是：bbb
    - 比如，缓存模块，日志过滤则是：VideoCache



### 14.该库异常code说明
- 日志做到过滤
    - 比如，视频内核，日志过滤则是：VideoKernel；比如，视频player，日志过滤则是：VideoPlayer；比如，缓存模块，日志过滤则是：VideoCache
- 该库异常code说明
    - 针对视频封装库，统一处理抛出的异常，为了方便开发者快速知道异常的来由，则可以查询约定的code码。这个在sdk中特别常见，因此该库一定程度是借鉴腾讯播放器……


### 15.该库系列wiki文档



### 16.版本更新文档记录



### 17.其他说明
- ![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


#### 17.1 其他推荐说明
- 其他项目推荐
    - [1.开源博客汇总](https://github.com/yangchong211/YCBlogs)
    - [2.降低Crash崩溃库](https://github.com/yangchong211/YCAndroidTool)
    - [3.视频播放器封装库](https://github.com/yangchong211/YCVideoPlayer)
    - [4.状态切换管理器封装库](https://github.com/yangchong211/YCStateLayout)
    - [5.复杂RecyclerView封装库](https://github.com/yangchong211/YCRefreshView)
    - [6.弹窗封装库](https://github.com/yangchong211/YCDialog)
    - [7.版本更新封装库](https://github.com/yangchong211/YCUpdateApp)
    - [8.状态栏封装库](https://github.com/yangchong211/YCStatusBar)
    - [9.轻量级线程池封装库](https://github.com/yangchong211/YCThreadPool)
    - [10.轮播图封装库](https://github.com/yangchong211/YCBanner)
    - [11.音频播放器](https://github.com/yangchong211/YCAudioPlayer)
    - [12.画廊与图片缩放控件](https://github.com/yangchong211/YCGallery)
    - [13.Python多渠道打包](https://github.com/yangchong211/YCWalleHelper)
    - [14.整体侧滑动画封装库](https://github.com/yangchong211/YCSlideView)
    - [15.Python爬虫妹子图](https://github.com/yangchong211/YCMeiZiTu)
    - [17.自定义进度条](https://github.com/yangchong211/YCProgress)
    - [18.自定义折叠和展开布局](https://github.com/yangchong211/YCExpandView)
    - [19.商品详情页分页加载](https://github.com/yangchong211/YCShopDetailLayout)
    - [20.在任意View控件上设置红点控件](https://github.com/yangchong211/YCRedDotView)
    - [21.仿抖音一次滑动一个页面播放视频库](https://github.com/yangchong211/YCScrollPager)


#### 17.2 感谢参考案例和博客
- exo播放器
    - https://github.com/google/ExoPlayer
- ijk播放器
    - https://github.com/bilibili/ijkplayer
- 阿里云播放器
    - https://help.aliyun.com/document_detail/51992.html?spm=a2c4g.11186623.2.24.37131bc7j1PoVK#topic2415
- GSY播放器
    - https://github.com/CarGuo/GSYVideoPlayer
- 饺子播放器
    - https://github.com/lipangit/JiaoZiVideoPlayer



#### 17.2 关于LICENSE
```
Copyright 2017 yangchong211（github.com/yangchong211）

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```











