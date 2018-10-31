#### 目录介绍
- 1.视频具有的功能
- 2.使用方法介绍
    - 2.1 关于gradle引用说明
    - 2.2 添加布局
    - 2.3 最简单的视频播放器参数设定
    - 2.4 优化代码
    - 2.5 注意问题
- 3.其他推荐说明
- 4.文档wiki说明
- 5.运行的效果展示
- 6.版本更新说明
    - 6.0.0 v0.0.0 写于2017年7月1日
    - 6.0.1 V1.0.0 更新于2017年9月4日
    - 6.0.2 V1.0.1 更新于2017年11月18日
    - 6.0.3 v1.1.0 更新于2018年1月15日
    - 6.0.4 v2.0.0 更新于2018年1月18日
    - 6.0.5 v2.4.5 更新于2018年4月21日
    - 6.0.6 v2.4.6 更新于2018年8月2日
    - 6.0.7 v2.4.8 更新于2018年8月12日
    - 6.0.8 v2.4.9 更新于2018年8月16日
    - 6.0.9 v2.5.0 更新与2018年8月20日
    - 6.1.0 v2.6.0 更新于2018年9月25日
- 7.性能优化和库大小
- 8.其他说明


- [老版本详细ReadMe，原文档](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/README.md)


### 1.视频具有的功能
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
- C.1.5 支持视频小窗口拖拽功能，可以在应用内随意拖拽，单击点击是播放和暂停切换；长按是拖动处理



### 2.使用方法介绍
#### 2.1 关于gradle引用说明
```
compile 'cn.yc:YCVideoPlayerLib:2.6.0' 
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
```
//设置播放类型
// IjkPlayer or MediaPlayer
videoPlayer.setPlayerType(VideoPlayer.TYPE_NATIVE);
//网络视频地址
String videoUrl = DataUtil.getVideoListData().get(0).getVideoUrl();
//设置视频地址和请求头部
videoPlayer.setUp(videoUrl, null);
//创建视频控制器
VideoPlayerController controller = new VideoPlayerController(this);
controller.setTitle("自定义视频播放器可以播放视频拉");
//设置视频控制器
videoPlayer.setController(controller);
```


#### 2.4 优化代码
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


#### 2.5 注意问题
##### 2.4.1如果是全屏播放，则需要在清单文件中设置当前activity的属性值**
- android:configChanges 保证了在全屏的时候横竖屏切换不会执行Activity的相关生命周期，打断视频的播放
- android:screenOrientation 固定了屏幕的初始方向
- 这两个变量控制全屏后和退出全屏的屏幕方向
```
	<activity android:name=".ui.test2.TestMyActivity"
		android:configChanges="orientation|keyboardHidden|screenSize"
		android:screenOrientation="portrait"/>
```


### 3.其他推荐说明
- 1.[技术博客汇总](https://www.jianshu.com/p/614cb839182c)
- 2.[开源项目汇总](https://blog.csdn.net/m0_37700275/article/details/80863574)
- 3.[生活博客汇总](https://blog.csdn.net/m0_37700275/article/details/79832978)
- 4.[喜马拉雅音频汇总](https://www.jianshu.com/p/f665de16d1eb)
- 5.[其他汇总](https://www.jianshu.com/p/53017c3fc75d)
- 6.[重点推荐：博客笔记大汇总，开源文件都是md格式](https://github.com/yangchong211/YCBlogs)



### 4.文档wiki说明[待更新]
- [Home]()
- [基础方法说明]()
- [视频优化处理]()
- [视频全局悬浮窗播放]()
- [视频封装思路]()
- [视频问题处理]()
- [音视频博客学习]()
- [详细ReadMe，原文档](https://github.com/yangchong211/YCVideoPlayer/blob/master/read/README.md)



### 5.运行的效果展示
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
![image](https://upload-images.jianshu.io/upload_images/4432347-c2b61a83c1aaecba.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image](https://upload-images.jianshu.io/upload_images/4432347-f4776dfc42c94ebd.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![image](https://upload-images.jianshu.io/upload_images/4432347-1b5870de5a3d3318.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)


### 6.版本更新说明
##### 6.1 V1.0.0 更新于2017年10月4日
> 初期最简单功能
- 6.0.1.1 支持最简单视频播放，暂停，缓冲，全屏播放等基础功能。
- 6.0.1.2 支持滑动改变音量，改变声音大小的功能
- 6.0.1.3 还有其他基本功能
##### 9.2 V1.0.1 更新于2017年11月18日
- 最简单的封装，并且阅读相关视频案例，借鉴了相关思路和复用了部分代码
- 测试环节
##### 9.3 v1.1.0 更新于2018年1月15日
- 6.0.3.1 添加了设置视频播放权限的功能，用户可以自由设置权限，不过目前只是设置了用户是否登录，和登录用户是否有观看权限，因为公司需求是这样的，所以只有这两个。后期遇到其他需求再添加。逻辑已经在库中写好了，用户自己实现就可以呢。
- 6.0.3.2 关于权限肯定有话术内容，那么用户可以通过调用接口直接设置展示在播放器试看结束后的内容。十分方便，这块参考了优酷和爱奇艺视频
- 6.0.3.3 添加了用户多久不操作视频界面后，自动隐藏底部和头部布局视图。如果不设置，默认时间为5秒
- 6.0.3.4 添加了多种视频加载时候的加载效果，目前有两种，一种是转圈效果，一种是帧动画效果。当然你可以自己添加动画加载效果
##### 9.4 v1.1.1 更新于2018年1月18日
- 6.0.4.1 修改了视频横向播放时，点击手机物理返回键，画面展示状态栏问题
- 6.0.4.2 修改了在list页面(recyclerView)的视频，当上拉加载更多时，加载十几次会导致崩溃问题
- 6.0.4.3 精简了布局文件，方便修改定制和阅读
- 6.0.4.4 修改了在网络不好或者飞行模式下，用户播放视频，应该是播放错误而不是一直转圈加载问题
- 6.0.4.5 完善了代码的注释，现在几乎所有的方法都有相关注释，方便阅读和理解。去掉了无用的代码
- 6.0.4.6 添加了暴露接口之用户登录和用户购买会员的接口，用户可以自己实现监听之后的操作或者跳转页面
- 6.0.4.7 添加了视频左上方的返回键监听，用户可以自己实现返回逻辑
- 6.0.4.8 添加了锁定屏幕方向的功能，还在测试中，有点问题
##### 9.5 v2.4.5 更新于2018年4月21日
- 6.0.5.0 说明：全屏模式下，滑动屏幕左边改变亮度，滑动屏幕右边改变声音
- 6.0.5.1 触摸滑动事件中，优化了只有全屏的时候才能拖动位置、亮度、声音
- 6.0.5.2 优化了只有在播放，暂停，缓冲的时候才能改变亮度，声音，和拖动位置
- 6.0.5.3 滑动改变亮度，声音和拖动位置时，隐藏控制器中间播放位置变化图，亮度变化视图和音量变化视图
##### 9.6 v2.4.6 更新于2018年8月2日
- 6.0.6.1 添加了竖屏下的全屏播放模式
- 6.0.6.2 解决了横屏下全屏播放模式的导航栏显示问题
##### 9.7 v2.4.7 更新于2018年8月12日
- 6.0.7.1 添加了锁屏的功能，锁屏时，返回键不做任何处理，并且隐藏top和bottom面版控件
- 6.0.7.2 优化了全屏播放视频时，左右滑动可以设置快进和快退的功能
- 6.0.7.3 优化了播放视频中，没有网络，点击重试按钮提示用户检查网络是否异常吐司
- 6.0.7.4 注册一个网络变化监听广播，在网络变更时进行对应处理，从有网切换到没有网络时，切换播放状态
- 6.0.7.5 修改播放异常条件下，还有声音播放的问题
##### 6.0.9 v2.5.0 更新与2018年8月20日
- 6.0.9.1 通过设置注解限制部分方法传入值类型，避免用户传入值导致意外情况
- 6.0.9.2 初步写了小窗口视频拖拽功能，在下一个版本上该功能
- 6.0.9.3 修改了正常窗口和全屏切换时，状态栏显示的问题
- 6.0.9.4 优化了播放和暂停的监听事件，将listener暴露给开发者，可以让开发者处理某些逻辑，比如暂停时弹出广告
##### 6.1.0 v2.6.0 更新于2018年9月25日
- 6.1.0.1 优化了视频全屏播放时锁屏的功能
- 6.1.0.2 添加了视频在应用内小窗口拖动的功能



### 7.性能优化和库大小




### 8.其他说明
- ![image](https://upload-images.jianshu.io/upload_images/4432347-7100c8e5a455c3ee.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 8.1 关于LICENSE
```
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







