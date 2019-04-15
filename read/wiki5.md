# 视频问题处理
#### 目录介绍
- 01.视频全屏播放崩溃


#### 01.视频全屏播放崩溃
- 解决办法
    - android:configChanges 保证了在全屏的时候横竖屏切换不会执行Activity的相关生命周期，打断视频的播放
    - android:screenOrientation 固定了屏幕的初始方向
    - 这两个变量控制全屏后和退出全屏的屏幕方向
    ```
        <activity android:name=".ui.VideoActivity"
            android:configChanges="orientation|keyboardHidden|screenSize"
            android:screenOrientation="portrait"/>
    ```
- 为何不配置该属性会崩溃
    - 该配置作用是固定屏幕初始方向，如果不配置则会导致屏幕旋转重走activity的生命周期导致崩溃。
- 如果是其他崩溃，可以查看下日志
































