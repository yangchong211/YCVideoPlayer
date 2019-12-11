# 认识SurfaceView
#### 目录介绍
- 01.SurfaceView有何特点



### 01.SurfaceView有何特点
- Android中 View是通过刷新来重绘视图，系统通过发出VSYNC信号来进行屏幕的重绘，刷新的时间间隔是16ms,如果我们可以在16ms以内将绘制工作完成，则没有任何问题，如果我们绘制过程逻辑很复杂，并且我们的界面更新还非常频繁，这时候就会造成界面的卡顿，影响用户体验，为此Android提供了SurfaceView来解决这一问题.
- SurfaceView 继承自View，是 Android 中一种比较特殊的视图（View）
    - 它跟普通View最大的区别是它有自己的Surface，在WMS中有对应的WindowState，在SurfaceFlinger中有Layer
    - 一般的Activity包含的多个View会组成View hierachy的树形结构，只有最顶层的DecorView，也就是根结点视图，才是对WMS可见的。这个DecorView在WMS中有一个对应的WindowState。相应地，在SF中对应的Layer。
    - SurfaceView自带一个Surface，这个Surface在WMS中有自己对应的WindowState，在SF中也会有自己的Layer。虽然在App端它仍在View hierachy中，但在Server端（WMS和SF）中，它与宿主窗口是分离的。这样的好处是对这个Surface的渲染可以放到单独线程去做，渲染时可以有自己的GL context。这对于一些游戏、视频等性能相关的应用非常有益，因为它不会影响主线程对事件的响应。
- SurfaceView 应用场景
    - 综合这些特点，SurfaceView 一般用在游戏、视频、摄影等一些复杂 UI 且高效的图像的显示，这类的图像处理都需要开单独的线程来处理。
- SurfaceView 优点如下
    - SurfaceView 通过子线程中进行画面更新，View 则在主线程中进行画面更新。
    - SurfaceView 用于被动更新，如频繁画面更新，View 则用于主动更新，如触摸点击等事件响应等。
    - SurfaceView 在底层实现了双缓冲机制，效率大大提升了，View 则没有。















